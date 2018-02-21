import React, { Component } from 'react';
import '../App.css'
import { Link } from 'react-router-dom';
import Login from './Login';
import Signup from './Signup';

const helloAPI = 'http://www.omdbapi.com/?i=tt3896198&apikey=1c821225' ;
console.log("Hello from omdb");

class Homepage extends Component {
  constructor() {
    super();
    this.name = "Spoiled Tomatillos";
    this.state = {
      data:[],
    }}

    componentDidMount() {
      fetch('http://www.omdbapi.com/?i=tt3896198&apikey=1c821225').
      then((response) => response.json()).
      then((findresponse) => {
        console.log(findresponse.Poster)
        this.setState({
          data:findresponse.Poster,
        })
      })
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
      <div id='search-box'>
      <form action='/search' id='search-form' method='get' target='_top'>
      <input id='search-text' name='q' placeholder='Search' type='text'/>
      <button id='search-button' type='submit'>
      <span>Search</span>
      </button>
      </form>
      </div>

      <div className = "movie-items">
      {
        <img src = {this.state.data}></img>
      }
      </div>

      </div>
    );
  }
}

export default Homepage;
