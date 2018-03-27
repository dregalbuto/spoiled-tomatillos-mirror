import React, { Component } from 'react';
import './UserHome.css';
import { Link } from 'react-router-dom';
import NavigationBar from './NavigationBar.js';


class FeaturesButton extends Component {
  render() {
    return (
      <button className="Button" data-primary={this.props.primary}>{this.props.text}</button>
    );
  }
}
///////////////// Features /////////////////////
class Features extends Component {

  _handleClick() {
    return (
      console.log("CLICKED!!!!!!!"),
      <Link to="/Movie">TESTING</Link>
    );
  }

  render() {
    return (
      <div id="features" className="Features" style={{backgroundImage: 'url(https://therealsasha.wordpress.com/2015/03/01/interstellar-review/)'}}>
      <div className="content">
      <img className="logo" src="http://entjunkie.blogspot.com/2014/11/interstellar-2014-film-review.html" alt="movie-bg" />
      <h2>Watch interstellar now</h2>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Doloremque id quam sapiente unde voluptatum alias vero debitis, magnam quis quod.</p>
      <div className="button-wrapper">

      <FeaturesButton onClick={this._handleClick} primary={true} text="Watch now">
      </FeaturesButton>

      <FeaturesButton primary={false} text="+ My list" />
      </div>
      </div>
      <div className="overlay"></div>
      </div>
    );
  }
}


class TitleList extends Component {
  render() {
    return(
      <div className="Title">
      <h2>{this.props.title}</h2>
      </div>
    );
  }
}

///////////////// Home /////////////////////
class Home extends Component {

  constructor(props){
    super(props);
    console.log(this.props.match.params.id);
    fetch("/api/user/id/" + props.match.params.id)
        .then(response => response.json()).then(response=>{console.log(response)});

  }

  render() {
    return (    
      <div>
      
      <header>
      <NavigationBar />
      </header>
      <Features />

      <div className="TitleList">
      <div className="Title">
      <h1>Top Picks </h1>
      </div>
      <div className="Title">
      <h1>Recommended For Me</h1>
      </div>
      </div>
      </div>
    );
  }
}
export default Home;
