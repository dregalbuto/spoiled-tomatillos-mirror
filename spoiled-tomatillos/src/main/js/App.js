import React, { Component } from 'react';
import {Navbar, NavItem, NavDropdown, MenuItem, Nav, Carousel} from 'react-bootstrap';


class App extends Component {
  constructor() {
    super();
    this.name = "Spoiled Tomatillos";
  }
  render() {
    return (
    		 <div className="Homepage">
      <title>
      Spoiled Tomatillos
      </title>


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
		  </Navbar.Collapse>
		</Navbar>

		</div>
		</nav>

</div>

    );
  }
}

export default App;
