import ReactDOM from 'react-dom';
import { Redirect } from 'react-router'
import PropTypes from 'prop-types'
import React, { Component } from 'react'
import {
  Button,
  Container,
  Divider,
  Grid,
  Header,
  Icon,
  Image,
  List,
  Menu,
  Responsive,
  Segment,
  Sidebar,
  Visibility,
  Item,
  Label,
  Step, Table, Statistic
} from 'semantic-ui-react'
import {Navbar, NavItem, NavDropdown, Nav, MenuItem, ListGroup, ListGroupItem} from 'react-bootstrap';

class Groups extends Component {
	constructor(props) {
		super(props);
		this.state = {
			
		}
//		 this.handleItemClick = this.handleItemClick.bind(this);
	}
	
//	handleItemClick(e) {
//		this.setState({activeItem: e.target.value });
//		
//	}
	render() {
		return(
			<div>
			
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
			
		
		   
		   <Header
			as='h2'
				content='My Groups'
					inverted
					style={{
						fontSize: '1.7em',
						fontWeight: 'normal',
						marginTop: '1.5em',
					}}
		>
				
		</Header>
		   
		   <ListGroup>
		  
		   <ListGroupItem header="Group A" href="#">
		     Description A
		     	<Icon floated='right' link name='settings' />
		   </ListGroupItem>
		     <ListGroupItem header="Group B" href="#">
		     Description B

		     	<Icon floated='right' link name='settings' />
		     </ListGroupItem>
		     <ListGroupItem header="Group C" href="#">
		     Description C
		     <Icon floated='right' link name='settings' />
		    
		   </ListGroupItem>
		     <ListGroupItem header="Group D" href="#">
		     Description D
		     <Icon floated='right' link name='settings' />	    
		   </ListGroupItem>
		 </ListGroup>
		 
		 
		 <Segment inverted>
	      <Button floated='right' basic inverted color='blue'>Add</Button>
	      <Button floated='right' basic inverted color='red'>Delete</Button>
	      
	    </Segment>
	     
		    
			</div>
		)
	}
}

const style = {
		h3: {
		    marginTop: '2em',
		    	padding: '2em 0em',
		}
		    		  
}


export default Groups;