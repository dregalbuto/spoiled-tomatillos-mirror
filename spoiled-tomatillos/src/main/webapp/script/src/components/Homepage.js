import React, { Component } from 'react';
import './Home.css'
import { Link } from 'react-router-dom';
import Login from './Login';
import Signup from './Signup';
import Logo from './../images/logo.svg'

class Movies extends Component {
  constructor(props) {
    super(props);
}
  render() {
    return(
        <div className="Title">
          <h2>Explore Movies</h2>
        </div>
    );
  }
}
class Celebs extends Component {
  constructor(props) {
    super(props);
}
  render() {
    return(
        <div className="Title">
          <h2>Explore Celebrities</h2>
        </div>
    );
  }
}
class Reviews extends Component {
  constructor(props) {
    super(props);
}
  render() {
    return(
        <div className="Title">
          <h2>Explore Reviews</h2>
        </div>
    );
  }
}


class Homepage extends Component {
  constructor() {
    super();
    this.name = "Spoiled Tomatillos";
  }
  render() {
    return (
      <div>
      <header className="Header">
      <img src={Logo} />
      <h1 className="App-title">Welcome to {this.name}</h1>
      <p className="App-intro">
      Find Movies, TV shows, Celebrities and more ...
      </p>
      <div id='Search'>
          <form action='/search' id='search-form' method='get'>
            <input id='search-text' name='q' placeholder='Search' type='text'/>
<div id='search-button'>
            <button id='search-button' type='submit'>
              <span>Search</span>
            </button>
            </div>
          </form>
      </div>
      </ header>

      <div className="Homepage">
        <nav id="topNav" class="navbar navbar-fixed-top">
        <div className="container-fluid">
        <ul class="nav navbar-nav navbar-right">
        <li><Link to="/Signup"><a><span class="glyphicon glyphicon-user"></span> Sign Up</a></Link></li>
        <li>  <Link to="/Login"><a><span class="glyphicon glyphicon-log-in"></span> Login</a></Link></li>
        </ul>
        </div>
      </nav>

      </div>

      <Movies/>
      <Celebs/>
      <Reviews/>

      </div>
    );
  }
}

export default Homepage;
