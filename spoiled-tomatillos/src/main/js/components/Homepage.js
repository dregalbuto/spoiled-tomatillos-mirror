import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Login from './Login.js';
import Signup from './Signup.js';
import SearchBar from './SearchBar.js';
import {Navbar, NavItem, NavDropdown, MenuItem, Nav} from 'react-bootstrap';

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
				
				<nav id="topNav" className="navbar navbar-fixed-top">
				<div className="container-fluid">
				
				<Navbar inverse collapseOnSelect>
				  <Navbar.Header>
				    <Navbar.Brand>
				      <a href="#brand">Spoiled Tomatillos</a>
				    </Navbar.Brand>
				    <Navbar.Toggle />
				  </Navbar.Header>
				  <Navbar.Collapse>
				    <Nav>
				      <NavItem eventKey={1} href="#">
				        Movies
				      </NavItem>
				      <NavItem eventKey={2} href="#">
				        Reviews
				      </NavItem>
				        <NavItem eventKey={3} href="#">
				        News
				      </NavItem>
				      <NavDropdown eventKey={3} title="Categories" id="basic-nav-dropdown">
				        <MenuItem eventKey={3.1}>Comedy</MenuItem>
				        <MenuItem eventKey={3.2}>Sci-Fi</MenuItem>
				        <MenuItem eventKey={3.3}>Horror</MenuItem>
				        <MenuItem eventKey={3.4}>Romance</MenuItem>
				        <MenuItem eventKey={3.5}>Action</MenuItem>
				        <MenuItem eventKey={3.6}>Drama</MenuItem>
		
				        <MenuItem divider />
				        <MenuItem eventKey={3.7}>More</MenuItem>
				      </NavDropdown>
				    </Nav>
				    <Nav pullRight>
				    <NavItem>
				    <li><Link to="/Signup"><span className="glyphicon glyphicon-user"></span> Sign Up</Link></li>
				    </NavItem>
				    <NavItem>
				    <li><Link to="/Login"><span className="glyphicon glyphicon-log-in"></span> Login</Link></li>
				    </NavItem>
				    </Nav>
				  </Navbar.Collapse>
				</Navbar>
				
				</div>
				</nav>


				<header className="header">

				<h1 className="App-title">Welcome to {this.name}</h1>
				<p className="App-intro">
				Find Movies, TV shows, Celebrities and more ...
				</p>
				</header>
				<SearchBar />

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
