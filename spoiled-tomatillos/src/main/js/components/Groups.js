import ReactDOM from 'react-dom';
import { Redirect } from 'react-router'
import PropTypes from 'prop-types'
import React, { Component } from 'react'
import { Button, Container, Header, Icon, Image, List, Table,
  Menu,
  Segment,
  Item,
  Label,
  Modal,
  Form, 
  Radio
} from 'semantic-ui-react'
import {Navbar, NavItem, NavDropdown, Nav, MenuItem, ListGroup, ListGroupItem} from 'react-bootstrap';
import Profile from './UserProfile.js';
import { Link } from 'react-router-dom';
import NavigationBar from './NavigationBar.js';
import cookie from 'react-cookies'

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

/*{/*state: Privacy 
		 * 
		 * True: public;  
		 * False: private*/
class GroupForm extends Component {
	constructor(props) {
		super(props);
		this.state= {
				email: '',
				token: '',
				groupName: '',
				privacy: true,
				movieID: '',
				cookies: props.cookies
		};
		this.handleChange = this.handleChange.bind(this);
		this.onSubmit = this.onSubmit.bind(this);
	}
	handleChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		this.setState({ [name]:value })
	}
	
	onSubmit(e){
		e.preventDefault();
		console.log(this.state);
		
		var url = '/api/groups/create';
		var newGroup = {
				email: this.state.email,
				token: this.state.cookies.token,
				groupName: this.state.groupName,
				blacklist: this.state.blacklist,
				movieId: this.state.movieId
		};		
		this.setState({ fireRedirect: true })
	  
	    fetch(url, {
	    		method: 'POST',
	    		headers: {
	    			'Accept': 'application/json',
	    			'Content-Type': 'application/json',
	    		},
	    		body: JSON.stringify(newGroup)
	    }).then(res => {
	        return res;
	    }).catch(err => err);
	}
	
	render() {
		const { value } = this.state
		return (
				<Form onSubmit={this.onSubmit}>
							
					<Form.Field>				
					 	<label>Group Name</label>
						<input name="groupName"  placeholder='group name' ref="groupName" type="text"
							value={this.state.groupName} 
						onChange={this.handleChange} 
						/>
					</Form.Field>
					<Form.Field>                  {/*TODO convert this movie name to movieID*/}	
						<label>Movie Name</label>
						<input name="movieID" type="text" placeholder='movie name' ref="movieID"
							value={this.state.movieID} 
							onChange={this.handleChange}
						/>
					</Form.Field>
		          
		        <Form.Group inline>
		          <label>Who can see this group?</label>
		          
		          <Form.Field>
		          <Radio
		            label='Public'
		            name='radioGroup'
		            value='public'
		            checked={this.state.value === 'public'}
		            onChange={this.handleChange}
		          />
		        </Form.Field>
		        <Form.Field>
		          <Radio
		            label='Private'
		            name='radioGroup'
		            value='private'
		            checked={this.state.value === 'private'}
		            onChange={this.handleChange}
		          />
		        </Form.Field>
		          
		          
		        </Form.Group>
		        <Form.TextArea label='Group Description' placeholder='Tell your members more about this group...' />
		        	<Form.Button>Add friends</Form.Button>
		      </Form>
		)
	}
}


class NestedModal extends Component {
	constructor(props) {
		super(props);
		this.state = {
				open:false,
				cookies: props.cookies
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
		        <GroupForm cookies={this.state.cookies}/>

		        
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


{/* Render a group element in a row */}
class Group extends Component {
	constructor() {
		super();
		this.state={
			open: false
		};
		this.open = this.open.bind(this);
	    this.close = this.close.bind(this);  
	}
	open(e) { this.setState({ open: true }) }
	close(e){ this.setState({ open: false }) }
	
	
	render() {
		const { open } = this.state
		return(
        		<Table.Row>
        			<Table.Cell>{this.props.group.name}</Table.Cell>
        			<Table.Cell>{this.props.group.creator}</Table.Cell>
        			<Table.Cell>{this.props.group.topic}</Table.Cell>
        			<Table.Cell>
        			 	<ManageGroup />		     
        			 	<Modal 
        			 		open={open} basic size='small'
        			 		onOpen={this.open}
        			 		onClose={this.close}
        			 		style={{height: 300}} 
        			 		trigger={<Button icon><Icon link name='delete' /></Button>}>
    		    
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
        			
        			</Table.Cell>
        		</Table.Row>
        	)
    }	
		
}


{/* Render List of Groups in a Table*/}
class Groups extends Component {
	constructor() {
		super();
		this.state = {
			open: false,
			cookies: '',
			groups: []
		};
		this.open = this.open.bind(this);
	    this.close = this.close.bind(this);    
	    this.loadCookies = this.loadCookies.bind(this);   
	}
	open(e) { this.setState({ open: true }) }
	close(e){ this.setState({ open: false }) }
	

	loadCookies() {
			  /* Load cookie from login page
			   * user:  user_token,	id, email, username
			   * */
		 
		  this.setState({ 
			  cookies: cookie.load('user'),
		  })
		  
		  console.log("Reviews ");
		  console.log(this.state.cookies);
	}
	
	componentDidMount() {
		this.loadCookies();
		{/*fetch groups data*/}
	    fetch("/api/groups/")
	    .then((response) => response.json())
		.then((data) => {
			
			this.setState({
				groups:data._embedded.groups,
				
			})
			console.log("GROUPS TESTING")
			console.log(this.state.groups)
		})  
	  }
	
	render() {
		const { open } = this.state
		const groups = this.state.groups.map((group) =>
			<Group key={group._links.self.href} group={group}/>
		);
		
		return(
			<div>	
			<NavigationBar />
			<Link to="/Profile"><Button basic inverted color='blue'>Back</Button></Link>
		    <Header as='h2' content='My Groups' inverted style={{ fontSize: '1.7em', fontWeight: 'normal', marginTop: '1.5em',}} />
			
		    <Table color='grey' key='grey'>
	        <Table.Header>
	          <Table.Row>
	            <Table.HeaderCell>Group Name</Table.HeaderCell>
	            <Table.HeaderCell>Creator ID</Table.HeaderCell>
	            <Table.HeaderCell>Movie ID</Table.HeaderCell>
	            <Table.HeaderCell>Actions</Table.HeaderCell>
	          </Table.Row>
	        </Table.Header>

	        <Table.Body>
	        		{groups}
	        </Table.Body>
	        </Table>
	        
		 <Segment inverted>
		 
		 {/*Add button to add a group*/}
		 <NestedModal cookies={this.state.cookies}/>
	           
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