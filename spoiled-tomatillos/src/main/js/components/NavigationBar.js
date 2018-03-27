import ReactDOM from 'react-dom';
import { Redirect } from 'react-router'
import PropTypes from 'prop-types'
import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import Reviews from './Reviews.js';
import SearchBar from './SearchBar.js';
import {
	  Button, 
	  Header,
	  Icon,
	  Image,
	  List,
	  Menu,	 
	  Segment,
	  Item,
	  Label,
	  Modal,
	  Form
	} from 'semantic-ui-react'

import {Navbar, NavItem, NavDropdown, Nav, MenuItem} from 'react-bootstrap';

class NavigationBar extends Component {
	render() {
		return(
				<div>
				<nav id="topNav" className="navbar navbar-fixed-top">
				<div className="container-fluid">
				
				<Navbar inverse collapseOnSelect>
				  <Navbar.Header>
				    <Navbar.Brand>
				      <a href="/Home">Spoiled Tomatillos</a>
				    </Navbar.Brand>
				    <Navbar.Toggle />
				  </Navbar.Header>
				  <Navbar.Collapse>
				    <Nav>
				      <NavItem eventKey={1} href="#">
				        Movies
				      </NavItem>
				      <NavItem eventKey={2} href="/Reviews">
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
				    
					<SearchBar />
					
				    <Nav pullRight>
				    <NavItem>
				    		<Link to="/Profile"><Icon link name='user outline' /></Link>
				    		
				    </NavItem>
				    <NavItem>
				    		<Icon link name='empty heart' />
				    </NavItem>
				    		<NavItem>
				    		<Link to="/Groups"><Icon link='/Groups' name='discussions'/></Link>
				    		</NavItem>
				    </Nav>
				    
				  </Navbar.Collapse>
				</Navbar>
				
				</div>
				</nav>
				</div>
					
		)
	}
	
	
}

export default NavigationBar;