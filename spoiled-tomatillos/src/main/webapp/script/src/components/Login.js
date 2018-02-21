import React, { Component } from 'react';
import './Login.css'

class Login extends Component{
  render() {
    return(
      <div className="Login">
      <div class="container-fluid">
      <h1>Login</h1>
      {/*
        <div class="alert alert-info">
        </div>*/}
        <input
        type="text"
        className="form-control"
        placeholder="username"/>

        <input
        type="password"
        className="form-control"
        placeholder="password"/>
        {/*    <div class="alert alert-danger">
        invalid password
        </div> */}
        <a href= "/User"
        class="btn btn-block btn-primary">Login</a>

        <a href="/auth/google" class="btn btn-danger btn-block">
        <span class="fa fa-google-plus"></span>
        Google
        </a>

        <a class="btn btn-success btn-block"
        href="/Signup">Register</a>
        </div>
        </div>

      );
    }
  }

  export default Login;
