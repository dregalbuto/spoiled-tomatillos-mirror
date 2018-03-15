import React, { Component } from 'react';
import './Login.css';
import FacebookLogin from 'react-facebook-login';
import {Form, Button, FormGroup, ControlLabel, FormControl, Col} from 'react-bootstrap'

const responseFacebook = (response) => {
  console.log(response);
}

class Login extends Component{
  constructor(props) {
    super(props);
    this.state={
      username:"",
      password:""
    };

    this.onSubmit = this.onSubmit.bind(this);
  }

  onSubmit(event){
    event.preventDefault;

    var apiBaseUrl = "http://localhost:8080/api/";
    var self = this;

    if(this.state.username.length <= 0 || this.state.password.length <= 0) {
      alert("empty fields");
      return;
    }

    post('http://localhost:8080/api/users',{
      username:this.state.username,
      password:this.state.password
    })


  }

  render() {
    return(
      <div className="Login">
      <div className="container-fluid">
      <h1>Login</h1>
      
      <Form horizontal onSubmit={this.onSubmit}>
      
      
      <FormGroup controlId="formHorizontalUsername">
		 <Col componentClass={ControlLabel} sm={2}>
	      Username
	    </Col>
	      <Col sm={10}>
	      <input
	        type="text"
	        floatingLabelText="username"
	        onChange = {(event, newValue) => this.setState({username:newValue})}
	        placeholder="username"/>
	    </Col>
		</FormGroup>
		
		
		<FormGroup controlId="formHorizontalPassword">
		 <Col componentClass={ControlLabel} sm={2}>
		 Password
		 </Col>
	      <Col sm={10}>
	      <input
	        type="password"
	        floatingLabelText="password"
	        onChange = {(event, newValue) => this.setState({password:newValue})}
	        placeholder="password"/>
		</Col>
		</FormGroup>
       
		
		<FormGroup>
		 <Col smOffset={2} sm={10}>
		<Button type="submit" className="button button_wide">
		Login
		</Button>
		</Col>
		</FormGroup>
	

        <FacebookLogin
          appId="1229282497194175"
          autoLoad
          callback={responseFacebook}
          render={renderProps => (
            <button onClick={renderProps.onClick}>This is my custom FB button</button>
          )}
        />

        <a className="btn btn-success btn-block"
        href="/Signup">Register</a>
        
        </Form>
        </div>
        </div>

      
      );
    }
  }

  export default Login;
