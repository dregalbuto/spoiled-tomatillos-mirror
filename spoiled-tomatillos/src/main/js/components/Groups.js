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
	Radio,
	Grid,
	Dropdown, Pagination
} from 'semantic-ui-react'
import {Navbar, NavItem, NavDropdown, Nav, MenuItem, ListGroup, ListGroupItem} from 'react-bootstrap';
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

const options = [
	{ key: 1, text: 'Public', value: 1 },
	{ key: 2, text: 'Private', value: 2 }
	]

// Dropdown menu to select group privacy settings
// true: Public
// false: Private
class GroupPrivacy extends Component {
	constructor(props) {
		super(props);
		this.state = {}
	}

	render() {
		const { value } = this.state
		return (
				<Grid.Column>
				<Dropdown
				onChange={this.props.onChangeValue}
				options={options}
				placeholder='Group Privacy'
					selection
					value={value}
				/>
				</Grid.Column>
		)
	}
}

// Add Group Form; user fill in inputs here
class GroupForm extends Component {
	constructor(props) {
		super(props);
		this.state= {
				email: props.cookies.email,
				token: props.cookies.user_token,
				groupName: '',
				blacklist: 'true',  // save as 'blacklist' in JSON msg; default to be public (true)
				movieId: '',
				fireRedirect: false
		};

		console.log("GroupForm cookies :");
		console.log(props.cookies);

		this.handleChange = this.handleChange.bind(this);
		this.onSubmit = this.onSubmit.bind(this);
		this.handlePrivacyChange = this.handlePrivacyChange.bind(this);
	}
	handleChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		this.setState({ [name]:value })
	}

	handlePrivacyChange(e) {
		if (e.target.value === 1) {
			this.setState( {blacklist: 'true'});
		}
		else if (e.target.value === 2) {
			this.setState( {blacklist: 'false'});
		}
	}

	onSubmit(e){
		e.preventDefault();
		console.log(this.state);

		var fetchedData = {};
		var url = '/api/groups/create';
		var newGroup = {
				email: this.state.email,
				token: this.state.token,
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
			body: JSON.stringify(newGroup)})
				.then(response=>response.json())
				.then(data =>{
      		if(data.hasOwnProperty("groupId")){
				var gruopId = data.groupId;
        alert("add successfully");
        fetch("/api/user/email/" + this.state.email + "/groups")
            .then(res=>res.json())
            .then(res=>{
              fetchedData = res;
              this.props.cookies.groups = fetchedData.groups;
							this.props.onClose;
            });

      } else {
        var error = data.message;
        alert(error);
      }
    })
	}


	render() {
		return (
				<Form>

				<Form.Field>
				<label>Group Name (Must be a movie ID for current production)</label>
				<input name="groupName"  placeholder='group name' ref="groupName" type="text"
					value={this.state.groupName}
					onChange={this.handleChange}
				/>
				</Form.Field>
				<Form.Field>
				<label>Movie Name</label>
				<input name="movieId" type="text" placeholder='movie name' ref="movieId"
					value={this.state.movieId}
					onChange={this.handleChange}
				/>
				</Form.Field>

				<GroupPrivacy
					blacklist={this.state.blacklist} onChangeValue={this.handlePrivacyChange} />

				<Form.TextArea label='Group Description' placeholder='Tell your members more about this group...' />
					<Form.Button>Add friends</Form.Button>

					<Button basic color='red' onClick={this.props.onClose}>
					<Icon name='remove' /> Cancel
					</Button>

					<Button primary onClick={this.onSubmit}>
					Add <Icon name='right chevron' />
						</Button>

						{this.state.fireRedirect && (
											<Redirect to={"/Profile"}/>
										)}
					</Form>
		)
	}
}

// modal for adding a group
class NestedModal extends Component {
	constructor(props) {
		super(props);
		this.state = {
				open:false,
				cookies: props.cookies,
				groups: props.groups
		};

		console.log("NestedModal cookies :");
		console.log(this.state.cookies);
		this.open = this.open.bind(this);
		this.close = this.close.bind(this);
	}

	open() { this.setState({ open: true }) }
	close() { this.setState({ open: false }) }

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
				</Modal.Description>
				<GroupForm cookies={this.state.cookies} groups={this.state.groups}
						onClose={this.close}/>

				</Modal.Content>
				</Modal>
		)
	}
}


{/* Render a group element in a row */}
class Group extends Component {
	constructor(props) {
		super(props);
		this.state={
				open: false,
				group: props.group
		};
		this.open = this.open.bind(this);
		this.close = this.close.bind(this);
	}
	open(e) { this.setState({ open: true }) }
	close(e){ this.setState({ open: false }) }

	render() {
		const { open } = this.state
		return (
				<Table.Row>
				<Table.Cell><div>{this.state.group.name}</div></Table.Cell>
				<Table.Cell><div>{this.state.group.creator.id}</div></Table.Cell>
				<Table.Cell><div>{this.state.group.topic.id}</div></Table.Cell>
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
				cookies: cookie.load('user'),
				groups:[]
		};

		console.log(this.state.cookies);
		/*
		Groups data fetched from API
		*/
		fetch("api/user/email/"+this.state.cookies.email+"/groups").then(res=>res.json()).
		then((res)=>{
			console.log(res);
				this.setState({ groups: res })
				console.log(this.state.groups);
		}
	);

		this.open = this.open.bind(this);
		this.close = this.close.bind(this);

	console.log("Groups cookies :");
	console.log(this.state.cookies);
	}

	open(e) { this.setState({ open: true }) }
	close(e){ this.setState({ open: false }) }


	render() {
		const groups = this.state.groups.map((group) =>
			<Group key={group.id} group={group} />
		);

		return(
				<div>
				<NavigationBar />
				<Link to="/user/:id"><Button basic inverted color='blue'>Back</Button></Link>
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
				<NestedModal cookies={this.state.cookies} groups={this.state.groups}/>

				</Segment>
				<footer><Pagination defaultActivePage={5} totalPages={10} /></footer>
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
