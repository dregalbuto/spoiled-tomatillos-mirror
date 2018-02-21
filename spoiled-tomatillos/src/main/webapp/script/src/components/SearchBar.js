import Search from 'react-search';
import ReactDOM from 'react-dom';
import React, {Component, PropTypes} from 'react';

class SearchElement extends Component {
  constructor(props) {
    super(props);

    this.state = {data:{}};
    fetch("http://localhost:8080/api/movies/info?id=" + this.props.id)
        .then(res=>res.json())
        .then(json=>this.setState({data:json}));
  }

  render() {
    if (this.state.data.title == {}) {
      return null;
    }
    return <li>{this.state.data.title}</li>
  }
}

class SearchList extends Component {
  constructor(props) {
    super(props);

    this.state = {titles:{}};
  }

  render() {
    const listItem = this.props.result.map((id) =>
      <SearchElement key={"keyofelementwithidof" + id} id={id} />
    );
    return <ul>{listItem}</ul>;
  }
}

class SearchBar extends Component {
  constructor(props) {
    super(props);

    this.handleType = this.handleType.bind(this);
    this.searchBackend = this.searchBackend.bind(this);
    this.date = new Date();
    this.state = {results:[]};
  }

  renderAnswer(lop, d) {
    if(d > this.date) {
      this.date = d;
      this.setState({results: lop});
    }
  }

  searchBackend() {
    var s = this.searchBox.value;
    if (s != "") {
      var d = new Date();
      fetch("http://localhost:8080/api/movies/search?s=" + s)
          .then(res=>res.json())
          .then(json=>this.renderAnswer(json, d));
    } else {
      this.setState({results: []});
    }
  }

  handleType(e) {
    this.searchBackend();
  }

  handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      this.searchBackend();
      e.preventDefault();
    }
  }

  render() {
    return (
      <div>
      <div id='search-box'>
        <form /*action='/search'*/ id='search-form' method='get' target='_top'>
          <input id='search-text' name='s' ref={(input) => {this.searchBox = input;}}
            placeholder='Search' type='text' onInput={this.handleType} onKeyPress={this.handleKeyPress}/>
          <button id='search-button' /*type='submit'*/>
            <span>Search</span>
          </button>
        </form>
      </div>
      <div id='result-box'>
        <SearchList result={this.state.results} />
      </div>
      </div>
    );
  }


}

export default SearchBar;
