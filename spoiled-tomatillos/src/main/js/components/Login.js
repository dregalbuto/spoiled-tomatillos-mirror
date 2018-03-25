import React, { Component } from 'react';
import './Login.css';
import axios from 'axios';
import {Button} from 'react-bootstrap';
import { Redirect } from 'react-router';

class Login extends Component{
  constructor(props) {
    super(props);
    this.state={
      username:'',
      password:'',
      id:null,
      fireRedirect: false
    };
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(event){
    event.preventDefault;
    var name = this.username.value;
    var pass = this.password.value;

    if(name.length <= 0 || pass.length <= 0) {
      alert("empty fields");
      return;
    }

console.log(name);
console.log(pass);
    var data = {
      "email":name,
      "password":pass
    }
    fetch('/api/user/login', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
  .then(data => {
    console.log(data);
    if(data.hasOwnProperty("token")) {
      var token = data.token;
      fetch("/api/user/email/" + name)
          .then(res=>res.json())
          .then(res=>{console.log(res);
            json=>this.setState({id:res.id})});


      this.setState({ fireRedirect: true })
    }
    else {
      var error = data.message;
      alert(error);
    }
  },
  (error : any) => {
      let errorData = error.json().errors.children;
      for(let key in errorData) {
        errorData[key].errors ? this.formErrors[key]=errorData[key].errors[0] : this.formErrors[key] = null
      }
  });
}
  render() {
    return(
      <div className="Login">
      <div className="container-fluid">
      <h1>Login</h1>

      <div style={margin} className="input-group">
      <input
      type="text"
      className="username"
      ref= {(input) => {this.username = input;}}
        placeholder="email address"/>

        </div>

        <div style={margin} className="input-group">
        <input
        type="password"
        className="password"
        ref= {(input) => {this.password = input;}}
          placeholder="password"/>

          </div>

          <div className="input-group">
          <div className="checkbox">
          <label>
          <input id="login-remember" type="checkbox" name="remember" value="1"/> Remember me

          </label>
          </div>
          </div>

          <div style={smallMargin} className="form-group">
          <div className="col-sm-12 controls">

          <Button
          bsStyle="primary"
          onClick={(event)=>this.handleClick(event)}
          bsSize="large">Login</Button>

  				{this.state.fireRedirect && (
  				          <Redirect to={"/User/"+this.state.id}/>
  				        )}



          </div>
          </div>


          <div className="form-group">
          <div className="col-md-12 control">
          <div style={styles}>
          Do not have an account?

          <Button
          bsStyle="primary"
          href="/Signup"
          bsSize="large">Sign up here</Button>


          </div>
          </div>
          </div>

          </div>
          </div>

        );
      }
    }

    const styles = {
      borderWidth: '1px solid#888',
      paddingTop:'15px',
      fontSize:'85%'
    };

    const smallMargin = {
      margin:'10px',
      text: 'black'
    };

    const margin = {
      margin:'25px',
      color: 'black'
    };

    const fbStyle = {
      color: 'white',
      textAlign: 'center',
      fontSize: '18px'
    };

    export default Login;
