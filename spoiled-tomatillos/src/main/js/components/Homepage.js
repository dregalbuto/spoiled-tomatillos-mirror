import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Login from './Login.js';
import Signup from './Signup.js';
import SearchBar from './SearchBar.js';
import {Navbar, NavItem, NavDropdown, MenuItem, Nav, Carousel} from 'react-bootstrap';
import { Image, Button, Segment, Header, List } from 'semantic-ui-react'

class Movies extends Component {
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
			return(
				<Segment vertical>
				<h2>Explore Movies</h2>
				<Carousel>
				<Carousel.Item>
				<img width={450} height={250} alt="450x250" src={this.state.data} />

				</Carousel.Item>
				<Carousel.Item>
				<img width={450} height={250} alt="450x250" src="https://scontent.fzty2-1.fna.fbcdn.net/v/t31.0-8/26685925_10157113267033289_1787495231864394864_o.jpg?oh=8aa055ad21fa2e951bc9ceadc14d0f0d&oe=5B3B73AC" />

				</Carousel.Item>
				<Carousel.Item>
				<img width={450} height={250} alt="450x250" src="https://i.pinimg.com/736x/6d/e0/ce/6de0ce72ce84604a73bf90cf0192a7c4--s-movies-good-movies.jpg" />

				</Carousel.Item>
				</Carousel>
			 </Segment>
			);
		}
	}

	class Celebs extends Component {
		render() {
			return(
				 <Segment vertical>
				<div className="Title">
				<h2>Explore Celebrities</h2>

				<div className="ui small images">
				<div>
					<Image size='medium' src="https://ia.media-imdb.com/images/M/MV5BMTcwOTg3NzgyNF5BMl5BanBnXkFtZTgwNzAyNDkyOTE@._V1_.jpg"/>

				</div>
				<div>
					<Image size='medium' src="https://ia.media-imdb.com/images/M/MV5BMjk2NTIyNjU2NF5BMl5BanBnXkFtZTgwNjQ0NjMzMjE@._V1_.jpg"/>

					</div>
					<div>
				<Image size='medium' src="https://ia.media-imdb.com/images/M/MV5BMjI3NzMyOTY5M15BMl5BanBnXkFtZTcwMjEwOTk2Nw@@._V1_.jpg"/>

				</div>
				</div>

				<Button floated='right' basic inverted color='grey'>More</Button>
				</div>
				 </Segment>
			);
		}
	}
	class Reviews extends Component {
		render() {
			return(
					 <Segment vertical>
				<div className="Title">
				<h2>Explore Reviews</h2>

				<Segment inverted>
				<Header as='h3' inverted style={{ fontSize: '2em' }}>My Reviews</Header>
				<List divided inverted relaxed>
				<List.Item>
				<List.Content>
				<List.Header>Lady Bird</List.Header>
				a touching throwback to the adolescent years
				</List.Content>
				</List.Item>
				<List.Item>
				<List.Content>
				<List.Header>
				Pacific Rim: Uprising</List.Header>
				A well done sequel
				</List.Content>
				</List.Item>
				<List.Item>
				<List.Content>
				<List.Header>Unsane</List.Header>
				The best film of Berlinale was shot on an iPhone!
				</List.Content>
				</List.Item>
				</List>
				<Link to="/Reviews"><Button floated='right' basic inverted color='grey'>More</Button></Link>
				</Segment>

				</div>
				</Segment>
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

			}}

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


					<Movies />
					<Celebs />
					<Reviews/>

					</div>
				);
			}
		}

		export default Homepage;
