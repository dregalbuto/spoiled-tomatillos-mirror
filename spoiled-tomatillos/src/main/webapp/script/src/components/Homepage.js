import React, { Component } from 'react';
import './Home.css'
import { Link } from 'react-router-dom';

class Movies extends Component {

  render() {
    return(
        <div className="Title">
          <h2>Explore Movies</h2>
        </div>
    );
  }
}
class Celebs extends Component {
  render() {
    return(
        <div className="Title">
          <h2>Explore Celebrities</h2>
        </div>
    );
  }
}
class Reviews extends Component {
  render() {
    return(
        <div className="Title">
          <h2>Explore Reviews</h2>
        </div>
    );
  }
}

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
      fetch('http://www.omdbapi.com/?i=tt3896198&apikey=1c821225')
      .then((response) => response.json())
      .then((findresponse) => {
        console.log(findresponse.Poster)
        this.setState({
          data:findresponse.Poster,
        })
      })
    }


  render() {
    return (
      <div className="Homepage">
      <header className="header">

      <nav id="topNav" className="navbar navbar-fixed-top">
      <div className="container-fluid">
      <ul className="nav navbar-nav navbar-right">
      <li><Link to="/Signup"><a><span className="glyphicon glyphicon-user"></span> Sign Up</a></Link></li>
      <li> <Link to="/Login"><a><span className="glyphicon glyphicon-log-in"></span> Login</a></Link></li>
      </ul>
      </div>
      </nav>

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
        <img src = {this.state.data} alt="movie-item"></img>
      }
      </div>
      <Movies />
      <Celebs />
      <Reviews/>

      </div>
    );
  }
}

export default Homepage;
