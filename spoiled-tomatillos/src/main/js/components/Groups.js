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
  Modal,
  Form
} from 'semantic-ui-react'
import {Navbar, NavItem, NavDropdown, Nav, MenuItem, ListGroup, ListGroupItem} from 'react-bootstrap';
import Profile from './UserProfile.js';
import { Link } from 'react-router-dom';


class ManageGroup extends Component {
	constructor() {
		super();
		this.state= {
			open:false,
		};
		this.open = this.open.bind(this);
	    this.close = this.close.bind(this);
	}
	  open() {
		  this.setState({ open: true })
	  }
	  close() {
		  this.setState({ open: false })
	  }
	  
	
	render() {
		const { open } = this.state
		return (
				<Modal 
				dimmer={false}
			 	open={open}
				onOpen={this.open}
	        		onClose={this.close}
				trigger={
						<Button icon>
							<Icon floated='right' link name='settings' />
						</Button>
						}
	 			style={{height: 400}} >
					<Modal.Header>Edit Group Settings</Modal.Header>
					<Modal.Content image scrolling>
						<Image
						size='medium'
							src='https://react.semantic-ui.com/assets/images/wireframe/image.png'
								wrapped
							/>

	      <Modal.Description>
	        <Header>Placeholder - Group Name</Header>
	        
	        This will be added later to manage group settings.

	        
	      </Modal.Description>
	    </Modal.Content>
	    <Modal.Actions>
	    		<Button basic color='red' onClick={this.close}>
			<Icon name='remove' /> Cancel
		</Button>
	      <Button primary onClick={this.close}>
	        Add <Icon name='right chevron' />
	      </Button>
	    </Modal.Actions>
	  </Modal>
	  
	  
	  
			
		)
	}
	
}


class GroupForm extends Component {
	constructor() {
		super();
		this.state= {
				
		};
		this.handleChange = this.handleChange.bind(this);
	}
	handleChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		this.setState({ [name]:value })
	}
	
	render() {
		const { value } = this.state
		return (
				<Form>
		        <Form.Group widths='equal'>
		        	  <Form.Input fluid label='Group name' placeholder='Group name' />
		          <Form.Input fluid label='Movie name' placeholder='Movie name' />
		        
		        </Form.Group>
		          
		        <Form.Group inline>
		          <label>Who can see this group?</label>
		          <Form.Radio label='Public' value='public' checked={value === 'public'} onChange={this.handleChange} />
		          <Form.Radio label='Private' value='private' checked={value === 'private'} onChange={this.handleChange} />
		        </Form.Group>
		        <Form.TextArea label='Group Description' placeholder='Tell your members more about this group...' />
		        	<Form.Button>Add friends</Form.Button>
		      </Form>
		)
	}
}


class NestedModal extends Component {
	constructor() {
		super();
		this.state = {
				open:false,
		};
		
		this.open = this.open.bind(this);
	    this.close = this.close.bind(this);
	    }
		

	  open() {
		  this.setState({ open: true })
	  }
	  close() {
		  this.setState({ open: false })
	  }
	  
	render() {
		  const { open } = this.state
		return (
				<Modal 
					dimmer={false}
				 	open={open}
					onOpen={this.open}
		        		onClose={this.close}
					trigger={<Button floated='right' basic color='blue'>Add</Button>}
		 			style={{height: 500}} >
						<Modal.Header>Add a Group</Modal.Header>
						<Modal.Content image scrolling>
							<Image
							size='medium'
								src='https://react.semantic-ui.com/assets/images/wireframe/image.png'
									wrapped
								/>

		      <Modal.Description>
		        <Header>Group Description</Header>
		        <GroupForm />

		        
		      </Modal.Description>
		    </Modal.Content>
		    <Modal.Actions>
		    		<Button basic color='red' onClick={this.close}>
				<Icon name='remove' /> Cancel
			</Button>
		      <Button primary onClick={this.close}>
		        Add <Icon name='right chevron' />
		      </Button>
		    </Modal.Actions>
		  </Modal>
		  
		  
		)
	}
}

class Groups extends Component {
	constructor() {
		super();
		this.state = {
				open: false,
		};
		this.open = this.open.bind(this);
	    this.close = this.close.bind(this);
	    
	}

	
	open(e) {
		this.setState({ open: true })
	}
	close(e){
		this.setState({ open: false })
	}

	render() {
		const { open } = this.state
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

		    <ManageGroup />
		     
		    	 <Modal 
		 	 	open={open}
		 		onOpen={this.open}
		 		onClose={this.close}
		 		style={{height: 300}} 
		 		trigger={  
		 	 	<Button icon>
		 	 		<Icon floated='right' link name='delete' />
		 	 			</Button>
		 		} 
		 		basic 
		 		size='small'>
		    
		 		<Header icon='archive' content='Are you sure to delete this group?' />
		 			<Modal.Content>
		 				<p>Once you confirm, you will lose the members and reviews in this group.</p>
		 			</Modal.Content>
		 			<Modal.Actions>
		 				<Button basic color='red' inverted onClick={this.close}>
		 					<Icon name='remove' /> No
		 				</Button>
		 					
		 				<Button color='green' inverted onClick={this.close}>
		 					<Icon name='checkmark' /> Yes
		 				</Button>
		 			</Modal.Actions>
		 		</Modal>
		 		
		   
		    	 </ListGroupItem>
		     
		    	 <ListGroupItem header="Group B" href="#">
		     Description B
		     <ManageGroup />
		     		
			    	 <Modal 
			    	 		open={open}
				 		onOpen={this.open}
				 		onClose={this.close}
			 		style={{height: 300}} 
			 		trigger={  
			 	 	<Button icon>
			 	 		<Icon floated='right' link name='delete' />
			 	 			</Button>
			 		} 
			 		basic 
			 		size='small'>
			    
			 		<Header icon='archive' content='Are you sure to delete this group?' />
			 			<Modal.Content>
			 				<p>Once you confirm, you will lose the members and reviews in this group.</p>
			 			</Modal.Content>
			 			<Modal.Actions>
			 				<Button basic color='red' inverted onClick={this.close}>
			 					<Icon name='remove' /> No
			 				</Button>
			 					
			 				<Button color='green' inverted onClick={this.close}>
			 					<Icon name='checkmark' /> Yes
			 				</Button>
			 			</Modal.Actions>
			 		</Modal>
			 		
			 		
		     </ListGroupItem>
		     <ListGroupItem header="Group C" href="#">
		     Description C
		     
		     <ManageGroup />
		    	 <Modal 
		 	 	open={open}
		 		onOpen={this.open}
		 		onClose={this.close}
		 		style={{height: 300}} 
		 		trigger={  
		 	 	<Button icon>
		 	 		<Icon floated='right' link name='delete' />
		 	 			</Button>
		 		} 
		 		basic 
		 		size='small'>
		    
		 		<Header icon='archive' content='Are you sure to delete this group?' />
		 			<Modal.Content>
		 				<p>Once you confirm, you will lose the members and reviews in this group.</p>
		 			</Modal.Content>
		 			<Modal.Actions>
		 				<Button basic color='red' inverted onClick={this.close}>
		 					<Icon name='remove' /> No
		 				</Button>
		 					
		 				<Button color='green' inverted onClick={this.close}>
		 					<Icon name='checkmark' /> Yes
		 				</Button>
		 			</Modal.Actions>
		 		</Modal>
		 		
		   </ListGroupItem>
		    
		   <ListGroupItem header="Group D" href="#">
		     Description D
		     <ManageGroup />
		    	 
		    	 <Modal 
		 	 	open={open}
		 		onOpen={this.open}
		 		onClose={this.close}
		 		style={{height: 300}} 
		 		trigger={  
		 	 	<Button icon>
		 	 		<Icon floated='right' link name='delete' />
		 	 			</Button>
		 		} 
		 		basic 
		 		size='small'>
		    
		 		<Header icon='archive' content='Are you sure to delete this group?' />
		 			<Modal.Content>
		 				<p>Once you confirm, you will lose the members and reviews in this group.</p>
		 			</Modal.Content>
		 			<Modal.Actions>
		 				<Button basic color='red' inverted onClick={this.close}>
		 					<Icon name='remove' /> No
		 				</Button>
		 					
		 				<Button color='green' inverted onClick={this.close}>
		 					<Icon name='checkmark' /> Yes
		 				</Button>
		 			</Modal.Actions>
		 		</Modal>
		 		
		     </ListGroupItem>
		 </ListGroup>
		 
		 
		 
		 <Segment inverted>
		 <NestedModal />
	      
	      
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