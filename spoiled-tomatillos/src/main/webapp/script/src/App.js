import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Route } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Login from './pages/Login';
import Signup from './pages/Signup';


class App extends Component {
  constructor() {
    super();
    this.name = "Spoiled Tomatillos";

  }
  render() {
    return (
      <div className="App">

        {/* Route Configuration */}
        <Route path = "/Login" exact component = {Login}/>
        <Route path = "/Signup" exact component = {Signup}/>

        <nav id="topNav" class="navbar navbar-fixed-top">
          <div className="container-fluid">
            <div className="navbar-header">
              <a className="navbar-brand page-scroll" href=""><i class="glyphicon glyphicon-globe"></i>Spoiled Tomatillos</a>
            </div>
            <ul class="nav navbar-nav navbar-right">
            <li><Link to="/Signup"><a><span class="glyphicon glyphicon-user"></span> Sign Up</a></Link></li>
            <li>  <Link to="/Login"><a><span class="glyphicon glyphicon-log-in"></span> Login</a></Link></li>
            </ul> </div></nav>
            <header className="App-header">
              <h1 className="App-title">Welcome to {this.name}</h1>
              <p className="App-intro">
                Find Movies, TV shows, Celebrities and more ...
              </p>
            </header>
            <div id='search-box'>
              <form action='/search' id='search-form' method='get' target='_top'>
                <input id='search-text' name='q' placeholder='Search' type='text'/>
                <button id='search-button' type='submit'>
                  <span>Search</span>
                </button>
              </form>
            </div>

          </div>
        );
      }
    }

    export default App;
