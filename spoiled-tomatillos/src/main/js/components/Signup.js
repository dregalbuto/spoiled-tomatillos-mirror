import React, { Component } from 'react';
import './Login.css';
var _ = require('underscore');

class Signup extends Component {
	constructor(props) {
	    super(props);
	    this.state = { 
	    		/* initial state */
	    		email: '',
			username: '',
			password: '',
			passwordconfirm: ''};
	    this.onChange = this.onChange.bind(this);
	    this.onSubmit = this.onSubmit.bind(this);
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
	}

	render() {
		return(
				<div className="create_account_screen">
				<div className="create_account_form">
				<h1>Create account</h1>
				<p>Create a user account in Spoiled Tomatillos</p>
				<form onSubmit={this.onSubmit}>
				
				<input name = "email" placeholder = "Email" ref="email" type="text"
					value={this.state.email} 
					onChange={this.onChange} 
				/>

				<input name = "username" placeholder = "Username" ref="username"
					value={this.state.username}
					onChange={this.onChange} 
				/> 
				
				<input name= "password" placeholder = "Password" type="password"
					ref="password" value={this.state.passsword}
					onChange={this.onChange} 
				/> 

				<input name = "passwordconfirm" ref="passwordconfirm" placeholder = "Confirm Password"
					type="password" value={this.state.passwordconfirm}
					onChange={this.onChange} 
				/> 

				<button type="submit" className="button button_wide">
				CREATE ACCOUNT
				</button>

				</form>
				</div>
				</div>
		);
	}
}

export default Signup;
