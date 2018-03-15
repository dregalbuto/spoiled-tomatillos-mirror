import React, { Component } from 'react';
import './Login.css';
import FacebookLogin from 'react-facebook-login';

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
  }

  validateForm() {
    return this.state.username.length > 0 && this.state.password.length > 0;
  }

  render() {
    return(
      <div className="Login">
      <div className="container-fluid">
      <h1>Login</h1>
        <input
        type="text"
        floatingLabelText="username"
        onChange = {(event, newValue) => this.setState({username:newValue})}
        placeholder="username"/>

        <input
        type="password"
        floatingLabelText="password"
        onChange = {(event, newValue) => this.setState({password:newValue})}
        placeholder="password"/>
        <a href= "/User"
        className="btn btn-block btn-primary">Login</a>

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
        </div>
        </div>

      );
    }
  }

  export default Login;
