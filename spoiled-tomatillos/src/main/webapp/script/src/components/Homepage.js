import React, { Component } from 'react';
import '../App.css'
import { Link } from 'react-router-dom';
import Login from './Login';
import Signup from './Signup';
import SearchBar from './SearchBar';


class Homepage extends Component {
  constructor() {
    super();
    this.name = "Spoiled Tomatillos";
  }
  render() {
    return (
      <div className="Homepage">
        <nav id="topNav" class="navbar navbar-fixed-top">
          <div className="container-fluid">
            <ul class="nav navbar-nav navbar-right">
       <li><Link to="/Signup"><a><span class="glyphicon glyphicon-user"></span> Sign Up</a></Link></li>
              <li>  <Link to="/Login"><a><span class="glyphicon glyphicon-log-in"></span> Login</a></Link></li>
            </ul>
          </div>
        </nav>

        <header className="App-header">
          <h1 className="App-title">Welcome to {this.name}</h1>
          <p className="App-intro">
            Find Movies, TV shows, Celebrities and more ...
          </p>
        </header>
        <SearchBar />

      </div>
    );
  }
}

export default Homepage;
