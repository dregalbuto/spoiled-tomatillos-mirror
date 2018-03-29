import React, { Component } from 'react';
import cookie from 'react-cookies'
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
      first_name:'',
      fireRedirect: false,
      id:0,
      mounted:false,
      token:0,
      reviews: [],
<<<<<<< HEAD
      friends: [],
      groups: [],
      cookies: ''
=======
      friends: []
>>>>>>> 85fe113d385bde8327fd98a885829522dbc4d755
    };
  }

  handleClick(event){
    event.preventDefault;
    var name = this.username.value;
    var pass = this.password.value;
    var fetchedData = {};
<<<<<<< HEAD
    var fetchedGroups ={};
=======
>>>>>>> 85fe113d385bde8327fd98a885829522dbc4d755

    if(name.length <= 0 || pass.length <= 0) {
      alert("empty fields");
      return;
    }

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
          .then(res=>{
            fetchedData = res;
            this.state.id = fetchedData.id;
            this.state.first_name = fetchedData.first_name;
            this.state.reviews = fetchedData.reviews;
            this.state.friends = fetchedData.friends;
<<<<<<< HEAD

            fetch("api/user/email/"+name+"/groups").then(res=>res.json()).
            then(res=>{
              fetchedGroups = res;
              this.state.groups = fetchedGroups;
            }

          );

                cookie.save('user',
                	{
                		user_token: token,
                		id: this.state.id,
                		email: name,
                		username: this.state.first_name,
                		reviews: this.state.reviews,
                		friends: this.state.friends,
                    groups: this.state.groups
                } );
                this.setState({ fireRedirect: true});
                console.log(cookie.load('user'));
              });
=======
            {/*
            Save user information in a cookie
            */}
            cookie.save('user',
            	{
            		user_token: token,
            		id: this.state.id,
            		email: name,
            		username: this.state.first_name,
            		reviews: this.state.reviews,
            		friends: this.state.friends

            } );
            this.setState({ fireRedirect: true});
            });
>>>>>>> 85fe113d385bde8327fd98a885829522dbc4d755
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
          onClick={this.handleClick.bind(this)}
          bsSize="large">Login</Button>

  				{this.state.fireRedirect && (
  				          <Redirect to={"/user/"+this.state.id}/>
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
