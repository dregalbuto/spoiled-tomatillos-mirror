import ReactDOM from 'react-dom';
import { Redirect } from 'react-router'
import PropTypes from 'prop-types'
import React, { Component } from 'react'
import {
	  Button,
	  Container,
	  Header,
	  Icon,
	  Image,
	  List,
	  Segment,
	  Item,
	  Label,
	  Modal,
	  Form,
	  Table, 
	  Rating
	} from 'semantic-ui-react'
import {Navbar, NavItem, NavDropdown, Nav, MenuItem} from 'react-bootstrap';

class ReviewTable extends Component {
	constructor() {
		super();
		this.state = {
			
		};
		
	}
	render() {
		return(
				<Table celled padded>
			    <Table.Header>
			      <Table.Row>
			        <Table.HeaderCell singleLine>Movie Name</Table.HeaderCell>
			        <Table.HeaderCell>Date</Table.HeaderCell>
			        <Table.HeaderCell>Your Rating</Table.HeaderCell>
			        <Table.HeaderCell>Movie Rating</Table.HeaderCell>
			        <Table.HeaderCell>Comments</Table.HeaderCell>
			      </Table.Row>
			    </Table.Header>

			    <Table.Body>
			      <Table.Row>
			        <Table.Cell>
			          <Header as='h2' textAlign='center'>Avengers: Infinity War(2018) </Header>
			        </Table.Cell>
			        <Table.Cell singleLine>Action, Adventure, Fantasy</Table.Cell>
			        <Table.Cell>
			          <Rating icon='star' defaultRating={4} maxRating={5} />
			        </Table.Cell>
			        <Table.Cell textAlign='right'>
			            4.3/5.0 <br />
			          <a href='#'>23 Reviews</a>
			        </Table.Cell>
			        <Table.Cell>
			        As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality.
			        </Table.Cell>
			      </Table.Row>
			      <Table.Row>
			        <Table.Cell>
			          <Header as='h2' textAlign='center'>A</Header>
			        </Table.Cell>
			        <Table.Cell singleLine>Weight</Table.Cell>
			        <Table.Cell>
			          <Rating icon='star' defaultRating={3} maxRating={3} />
			        </Table.Cell>
			        <Table.Cell textAlign='right'>
			            100% <br />
			          <a href='#'>65 studies</a>
			        </Table.Cell>
			        <Table.Cell>
			            Creatine is the reference compound for power improvement, with numbers from one meta-analysis to assess
			            potency
			        </Table.Cell>
			      </Table.Row>
			    </Table.Body>
			  </Table>
		)
	}
}

	
class NavBar extends Component {
	constructor() {
		super();
		this.state = {
			
		};
		
	}
	render() {
		return(
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
				    		<Icon link name='user outline' />
				        
				    </NavItem>
				    <NavItem>
				    		<Icon link name='empty heart' />
				    </NavItem>
				    		<NavItem>
				    			<Icon link name='discussions'/>
				    		</NavItem>
				    </Nav>
				    
				  </Navbar.Collapse>
				</Navbar>
				</div>
				</nav>	
		)
	}
}

class Reviews extends Component {
	constructor() {
		super();
		this.state = {
				
		};
		
	}
	render() {
		return (
			<div>
			<NavBar />	
			<ReviewTable />
			
			</div>
		)
		
	}
}


export default Reviews;