import React, { Component } from 'react';
import ReactDOM from 'react-dom';
var _ = require('underscore');
import "./Signup.css";
import {Form, Button, FormGroup, ControlLabel, FormControl, Col} from 'react-bootstrap'
import { Redirect } from 'react-router'

class Signup extends Component {



	constructor(props) {
	    super(props);
	    this.state = {
	    		/* initial state */
	    		email: '',
	    		confirmemail:'',
	    		first_name:'',
	    		last_name: '',
			username: '',
			password: '',
			passwordconfirm: '',
			fireRedirect: false
			};
	    this.onChange = this.onChange.bind(this);
	    this.onSubmit = this.onSubmit.bind(this);
	  }

	loadFromServer() {
		fetch('/api/users')
	      .then((response) => response.json())
	      .then((responseData) => {
	          this.setState({
	              users: responseData._embedded.users,
	          });
	      });
	}

	onCreate(newUser) {
		fetch('api/users',
			      {   method: 'POST',
			          headers: {
			            'Content-Type': 'application/json',
			          },
			          body: JSON.stringify(newUser)
			      })
			      .then(
			          res => this.loadFromServer()
			      )
			      .catch( err => console.error(err))
	}

	componentDidMount() {
		this.loadFromServer();
	}

	onChange(e) {
		this.setState({[e.target.name]: e.target.value });
	}

	validateEmail(event) {
		// regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(event);
	}


	onSubmit(e) {
		e.preventDefault();
		console.log(this.state);

		var newUser = {
				first_name: this.state.first_name,
				last_name: this.state.last_name,
				email: this.state.email,
				username: this.state.username,
				password: this.state.password
				};
	    this.onCreate(newUser);
	    this.setState({ fireRedirect: true })

	}

	render() {
		return(
				<div className="create_account_screen">
				<div className="create_account_form">
				<h1>Create account</h1>
				<p>Create a user account in Spoiled Tomatillos</p>

				<Form horizontal onSubmit={this.onSubmit}>


				<FormGroup controlId="formHorizontalEmail">
				 <Col componentClass={ControlLabel} sm={2}>
			      Email
			    </Col>
			      <Col sm={10}>
			      <input name = "email" placeholder = "Email" ref="email" type="text"
						value={this.state.email}
						onChange={this.onChange}
					/>
			    </Col>
				</FormGroup>


				 <FormGroup controlId="formHorizontalConfirmEmail">
				 <Col componentClass={ControlLabel} sm={2}>
			      Confirm Email
			    </Col>
			      <Col sm={10}>
				<input name = "confirmemail" placeholder = "Confirm Email" ref="confirmemail" type="text"
					value={this.state.confirmemail}
					onChange={this.onChange}
				/>
				</Col>
				</FormGroup>

				 <FormGroup controlId="formHorizontalFirstName">
				 <Col componentClass={ControlLabel} sm={2}>
			      First Name
			    </Col>
			      <Col sm={10}>
				<input name = "first_name" placeholder = "First Name" ref="first_name" type="text"
					value={this.state.first_name}
					onChange={this.onChange}
				/>
				</Col>
				</FormGroup>

				 <FormGroup controlId="formHorizontalLastName">
				 <Col componentClass={ControlLabel} sm={2}>
				 Last Name
				 </Col>
			      <Col sm={10}>
				<input name = "last_name" placeholder = "Last Name" ref="last_name" type="text"
					value={this.state.last_name}
					onChange={this.onChange}
				/>
				</Col>
				</FormGroup>


				 <FormGroup controlId="formHorizontalUserName">
				 <Col componentClass={ControlLabel} sm={2}>
				 User Name
				 </Col>
			      <Col sm={10}>
				<input name = "username" placeholder = "Username" ref="username"
					value={this.state.username}
					onChange={this.onChange}
				/>
				</Col>
				</FormGroup>

				 <FormGroup controlId="formHorizontalPassword">
				 <Col componentClass={ControlLabel} sm={2}>
				 Password
				 </Col>
			      <Col sm={10}>
				<input name= "password" placeholder = "Password" type="password"
					ref="password" value={this.state.passsword}
					onChange={this.onChange}
				/>
				</Col>
				</FormGroup>

				 <FormGroup controlId="formHorizontalConfirmpassword">
				 <Col componentClass={ControlLabel} sm={2}>
				Confirm Password
				 </Col>
			      <Col sm={10}>
				<input name = "passwordconfirm" ref="passwordconfirm" placeholder = "Confirm Password"
					type="password" value={this.state.passwordconfirm}
					onChange={this.onChange}
				/>
				</Col>
				</FormGroup>

				<FormGroup>
				 <Col smOffset={2} sm={10}>
				<Button type="submit" className="button button_wide">
				CREATE ACCOUNT
				</Button>
				</Col>
				</FormGroup>


				</Form>
				{this.state.fireRedirect && (
				          <Redirect to="/Login"/>
				        )}

				</div>
				</div>
		)
	}
}

export default Signup;
