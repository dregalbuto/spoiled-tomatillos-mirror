/**
 * Implements SearchBar component with text box and search functionality to
 * backend.
 */
import Search from 'react-search';
import ReactDOM from 'react-dom';
import React, {Component, PropTypes} from 'react';
import { Link } from 'react-router-dom';

/**
 * Represents one result in list of results in SearchList.
 * Uses props.id with id of movie to display.
 */
class SearchElement extends Component {
  constructor(props) {
    super(props);

    this.state = {data:{}};
    fetch("/api/movies/info?id=" + this.props.id)
        .then(res=>res.json())
        .then(json=>this.setState({data:json}));
  }

  /**
   * Render this with nothing if this.state.data.title is not set. <li>
   * otherwise.
   */
  render() {
    if (this.state.data.title == {}) {
      return null;
    }
    return <li><Link style={{"color":"inherit","text-decoration":"inherit",}}
                     to={"/Movie/" + this.state.data.id}>{this.state.data.title}</Link></li>
  }
}

/**
 * Represent a list of search results from search query.
 * Uses props.result which should be a list of id for movies.
 */
class SearchList extends Component {
  constructor(props) {
    super(props);

    this.state = {titles:{}};
  }

  /**
   * Render a list of search results with SearchElements.
   */
  render() {
    const listItem = this.props.result.map((id) =>
      <SearchElement key={"keyofelementwithidof" + id} id={id} />
    );
    return <ul>{listItem.slice(0, Math.min(10, listItem.length))}</ul>;
  }
}

/**
 * Represent a functional search bar that renders a list of results underneath.
 */
class SearchBar extends Component {
  constructor(props) {
    super(props);

    this.handleType = this.handleType.bind(this);
    this.searchBackend = this.searchBackend.bind(this);
    this.date = new Date();
    this.state = {results:[]};
  }

  /**
   * Takes in a list of ids for movies and a time. Will render if time is the
   * latest time received by this function.
   */
  renderAnswer(lop, d) {
    if(d > this.date) {
      this.date = d;
      this.setState({results: lop});
    }
  }

  /**
   * Search the backend with values of this SearchBar and render the results.
   */
  searchBackend() {
    var s = this.searchBox.value;
    if (s != "") {
      var d = new Date();
      fetch("/api/movies/search?s=" + s)
          .then(res=>res.json())
          .then(json=>this.renderAnswer(json, d));
    } else {
      this.setState({results: []});
      this.date = new Date();
    }
  }

  /**
   * Handing typing event on the SearchBar.
   */
  handleType(e) {
    this.searchBackend();
  }

  /**
   * Handling keyboard event on SearchBar.
   */
  handleKeyPress(e) {
    if (e.key === 'Enter') {
      this.searchBackend();
      e.preventDefault();
    }
  }

  /**
   * Render the SearchBar with callbacks to events.
   */
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
