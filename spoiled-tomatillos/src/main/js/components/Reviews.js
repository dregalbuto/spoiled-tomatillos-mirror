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


class AddReview extends Component {
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
						<Modal.Header>Add a Review</Modal.Header>
						<Modal.Content image scrolling>
							<Image
							size='medium'
								src='https://react.semantic-ui.com/assets/images/wireframe/image.png'
									wrapped
								/>

		      <Modal.Description>
		        <Header>Add a review</Header>
		        
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


class DeleteReview extends Component {
	constructor() {
		super();
	this.state = {
			open:false,	};
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
		 			
		)
	}
	
}


class ManageReview extends Component {
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
					<Modal.Header>Edit Review </Modal.Header>
					<Modal.Content image scrolling>
						<Image
						size='medium'
							src='https://react.semantic-ui.com/assets/images/wireframe/image.png'
								wrapped
							/>

	      <Modal.Description>
	        <Header>Edit Review</Header>
	        
	        This will be added later to manage review settings.

	        
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


class ReviewList extends Component {
	constructor() {
		super();
		this.state = {
			
		};
		
	}
	render() {
		return(
				<div>
				 <Table celled padded>
				    <Table.Header>
				      <Table.Row>
				        <Table.HeaderCell singleLine>Movie Name</Table.HeaderCell>
				        <Table.HeaderCell>Category</Table.HeaderCell>
				        <Table.HeaderCell>Your Rating</Table.HeaderCell>
				        <Table.HeaderCell>Movie Rating</Table.HeaderCell>
				        <Table.HeaderCell>Comments</Table.HeaderCell>
				        <Table.HeaderCell>Actions</Table.HeaderCell>
				      </Table.Row>
				    </Table.Header>

				    <Table.Body>
				      <Table.Row>
				        <Table.Cell>
				          <Header as='h2' textAlign='center'>Avengers: Age of Altron(2015)</Header>
				        </Table.Cell>
				        <Table.Cell singleLine>Action, Fantasy, Sci-fi</Table.Cell>
				        <Table.Cell>
				          <Rating icon='star' defaultRating={6.8} maxRating={10.0} />
				        </Table.Cell>
				        <Table.Cell textAlign='right'>
				            7.0/10.0 <br />
				          <a href='#'>125 reviews</a>
				        </Table.Cell>
				        <Table.Cell>
				        I'll say this: If you don't mind more of the same you'll probably love this movie. If you wanted something different, some change to the Marvel formula, you'll be disappointed. Age of Ultron is pretty much the same movie as the first one. Especially the ending which feels like it follows the first movie beat by beat.

				        </Table.Cell>
				        <Table.Cell>
				        
				        <ManageReview />
				        <DeleteReview />
				        
				        
				        </Table.Cell>
				      </Table.Row>
				      <Table.Row>
				        <Table.Cell>
				          <Header as='h2' textAlign='center'>X-Men: Apocalypse (2016)</Header>
				        </Table.Cell>
				        <Table.Cell singleLine> Action, Adventure, Sci-Fi </Table.Cell>
				        <Table.Cell>
				          <Rating icon='star' defaultRating={7.0} maxRating={10.0} />
				        </Table.Cell>
				        <Table.Cell textAlign='right'>
				        8.0/10.0 <br />
				          <a href='#'>243 reviews</a>
				        </Table.Cell>
				        <Table.Cell>
				        X-Men Apocalypse may not be the best X-Men movie made but it is still enjoyable if you're an X-Men fan!
				        </Table.Cell>
				        <Table.Cell>
				        
				        		<ManageReview />
				        		<DeleteReview />
	        
				        </Table.Cell>
				      </Table.Row>
				    </Table.Body>
				  </Table>
				  
				  <AddReview />
				  </div>
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
			<ReviewList />
			</div>
		)
		
	}
}


export default Reviews;