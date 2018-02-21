import React, { Component } from 'react';
import './Login.css'

class Signup extends Component {
  render() {
    return(
      <div class="container">
      <h1>Register</h1>

      <input ng-model = "firstname"
      type="text"
      class="form-control"
      placeholder="firstname"/>
      <input ng-model = "lastname"
      type="text"
      class="form-control"
      placeholder="lastname"/>
      <input ng-model = "email"
      type="text"
      class="form-control"
      placeholder="email"/>
      <input ng-model = "password"
      type="password"
      class="form-control"
      placeholder="password"/>
      <input ng-model = "confirmPassword"
      type="password"
      class="form-control"
      placeholder="verify password"/>

      <a href = "/User"
      class="btn btn-block btn-primary">Register</a>
      <a href="/Login" class="btn btn-block btn-danger">Cancel</a>
      </div>
    );
  }
}

export default Signup;
