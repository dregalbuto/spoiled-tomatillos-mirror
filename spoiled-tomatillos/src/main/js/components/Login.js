import React, { Component } from 'react';
import './Login.css';
<<<<<<< HEAD
import FacebookLogin from 'react-facebook-login/dist/facebook-login-render-props';
=======
import FacebookLogin from 'react-facebook-login';
import {Form, Button, FormGroup, ControlLabel, FormControl, Col} from 'react-bootstrap'
>>>>>>> dcffb4919877a79960570a2427b5880cfef282e7

const responseFacebook = (response) => {
  console.log(response);
}

class Login extends Component{
  constructor(props) {
    super(props);
    this.state={
      username:'',
      password:'',
      fb_username:'',
      fb_firstname:'',
      fb_lastname:'',
      fb_email:'',
      fb_password:''
    };

<<<<<<< HEAD
    this.handleClick = this.handleClick.bind(this);

  }

  responseFacebook(response){
    console.log(response);
=======
    this.onSubmit = this.onSubmit.bind(this);
  }

  onSubmit(event){
    event.preventDefault;
>>>>>>> dcffb4919877a79960570a2427b5880cfef282e7

    var newUser = {
      first_name: response.name,
      last_name: response.name,
      email: "TEST@facebook.com",
      username: response.name,
      password: response.id
    };


    fetch('http://localhost:8080/api/users',
    {   method: 'POST',
      headers: {
      'Content-Type': 'application/json',
      },
      body: JSON.stringify(newUser)
    })
    .then(
      console.log("POST success")
    )
    .catch( err => console.error(err))
  }

handleClick(event){
  event.preventDefault;

<<<<<<< HEAD
  var apiBaseUrl = "http://localhost:8080/api/";
  var self = this;

  if(this.state.username.length <= 0 || this.state.password.length <= 0) {
    alert("empty fields");
    return;
  }

  get('http://localhost:8080/api/users',{
    username:this.state.username,
    password:this.state.password
  }).then((res)=>{

  })
}

render() {
  return(
    <div className="Login">
    <div className="container-fluid">
    <h1>Login</h1>
    <input
    type="text"
    className="username"
    onChange = {(event, newValue) => this.setState({username:newValue})}
    placeholder="username"/>

    <input
    type="password"
    className="password"
    onChange = {(event, newValue) => this.setState({password:newValue})}
    placeholder="password"/>

    <a className="btn btn-success btn-block" onClick={(event)=>this.handleClick(event)}
    >Login</a>

    <FacebookLogin
    appId="1229282497194175"
    autoLoad
    callback={this.responseFacebook}
    render={renderProps => (
      <button onClick={renderProps.onClick}>This is my custom FB button</button>
    )}
    />

    <a className="btn btn-success btn-block"
    href="/Signup">Register</a>
    </div>
    </div>

  );
}
}

export default Login;
=======
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
>>>>>>> dcffb4919877a79960570a2427b5880cfef282e7
