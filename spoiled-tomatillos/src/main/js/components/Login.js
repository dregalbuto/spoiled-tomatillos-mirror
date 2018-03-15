import React, { Component } from 'react';
import './Login.css';
import FacebookLogin from 'react-facebook-login/dist/facebook-login-render-props';

class Login extends Component{
  constructor(props) {
    super(props);
    this.state={
      username:'',
      password:''
    };
    this.handleClick = this.handleClick.bind(this);

  }

  responseFacebook(response){
    console.log("FACEBOOK RESPONSE");
    console.log(response);

    var newUser = {
      first_name: response.name,
      last_name: response.name,
      email: response.name + "@facebook.com",
      username: response.name,
      password: response.id
    };


    fetch('/api/users',
    {   method: 'POST',
      headers: {
      'Content-Type': 'application/json',
      },
      body: JSON.stringify(newUser)
    })
    .then( () => {
        console.log("POST success");
        window.location = "/Home";
      }
    )
    .catch( err => console.error(err))
  }

  handleClick(event){
    event.preventDefault;
    var apiBaseUrl = "/api/";
    var name = this.username.value;
    var pass = this.password.value;

    if(name.length <= 0 || pass.length <= 0) {
      alert("empty fields");
      return;
    }

    fetch('/api/users',{
      username:name,
      password:pass
    }).then((res)=>{
      return res.text();
    }).then((tex) => {
      var data = JSON.parse(tex);
      var users = data["_embedded"].users;
      for (var i = 0; i < users.length; i++) {
        if (users[i].username == name) {
          window.location = "/Home";
        }
      }
      alert("User not found");
    });
  }

  render() {
    return(
      <div className="Login">
      <div className="container-fluid">
      <h1>Login</h1>
      <input
      type="text"
      className="username"
      ref= {(input) => {this.username = input;}}
      onChange = {(event, newValue) => this.setState((prev, props) =>
      {prev.username = newValue; return prev;})}
      placeholder="username"/>

      <input
      type="password"
      className="password"
      ref= {(input) => {this.password = input;}}
      onChange = {(event, newValue) => this.setState((prev, props) =>
      {prev.password = newValue; console.log(newValue); return prev;})}
      placeholder="password"/>

      <a className="btn btn-success btn-block" onClick={(event)=>this.handleClick(event)}
      >Login</a>

      <FacebookLogin
      appId="1229282497194175"
      autoLoad
      callback={this.responseFacebook}
      render={renderProps => (
        <button onClick={renderProps.onClick}>Login with facebook</button>
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
