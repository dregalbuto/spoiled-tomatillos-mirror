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
			fireRedirect: false,
			formErrors: {email: '', password: '', confirmemail:'', passwordconfirm:''},
		    emailValid: false,
		    passwordValid: false,
		    confirmEmailValid: false,
			confirmPasswordValid: false,
		    formValid: false
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
//		this.setState({[e.target.name]: e.target.value });
		const name = e.target.name;
		const value = e.target.value;
		this.setState({[name]: value}, 
                () => { this.validateField(name, value) });
	}
	
	validateField(fieldName, value) {
		  let fieldValidationErrors = this.state.formErrors;
		  let emailValid = this.state.emailValid;
		  let passwordValid = this.state.passwordValid;
		  let confirmEmailValid = this.state.confirmEmailValid;
		  let confirmPasswordValid = this.state.confirmPasswordValid;

		  switch(fieldName) {
		    case 'email':
		      emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
		      fieldValidationErrors.email = emailValid ? '' : ' is invalid';
		      break;
		    case 'password':
		      passwordValid = value.length >= 6;
		      fieldValidationErrors.password = passwordValid ? '': ' is too short';
		      break;
		    case 'confirmemail':
		    	  confirmEmailValid = (value == this.state.email);
		    	  fieldValidationErrors.confirmEmail = confirmEmailValid ? '': 'please enter the same email twice';
		    	  break;
		    case 'passwordconfirm':
		    	  confirmPasswordValid = (value == this.state.password);
		    	  fieldValidationErrors.confirmPassword = confirmPasswordValid ? '': 'password different from the first one';
		    	  break;
		    default:
		      break;
		  }
		  this.setState({formErrors: fieldValidationErrors,
		                  emailValid: emailValid,
		                  passwordValid: passwordValid,
		                  confirmEmailValid: confirmEmailValid,
		                  confirmPasswordValid: confirmPasswordValid
		                }, this.validateForm);
		}

		validateForm() {
		  this.setState({formValid: this.state.emailValid && this.state.passwordValid
			  && this.state.confirmEmailValid && this.state.confirmPasswordValid});
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

				<div className="panel panel-default" style={errorBox}>
				 <FormErrors formErrors={this.state.formErrors} />
				</div>
				 
				<FormGroup controlId="formHorizontalEmail">
				 <Col componentClass={ControlLabel} sm={2}>
			      Email
			    </Col>
			      <Col sm={10}>
			      <input name = "email" placeholder = "Email" ref="email" type="text"
						value={this.state.email}
						onChange={this.onChange}
			      		style={inputBox}
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
					style={inputBox}
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
				style={inputBox}
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
				style={inputBox}
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
				style={inputBox}
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
				style={inputBox}
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
				style={inputBox}
				/>
				</Col>
				</FormGroup>

				<FormGroup>
				 <Col smOffset={2} sm={10}>
				<Button type="submit" className="button button_wide"
					disabled={!this.state.formValid}>
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

const inputBox = {
		color: 'black',
		fontSize: '13px'
};

const errorBox = {
		color: 'black',
		fontSize: '15px'
};


export const FormErrors = ({formErrors}) =>
<div className='formErrors'>
  {Object.keys(formErrors).map((fieldName, i) => {
    if(formErrors[fieldName].length > 0){
      return (
        <p key={i}>{fieldName} {formErrors[fieldName]}</p>
      )        
    } else {
      return '';
    }
  })}
</div>

export default Signup;
