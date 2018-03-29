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
      // username:'',
      // password:'',
      // first_name:'',
      fireRedirect: false,
      id:0,
      mounted:false,
      token:0,
      // reviews: [],
      // friends: [],
      // groups: [],
      cookies: ''
    };
  }

  handleClick(event){
    event.preventDefault();
    var name = this.username.value;
    var pass = this.password.value;
    var fetchedData = {};
    var fetchedGroups ={};

    if(name.length <= 0 || pass.length <= 0) {
      alert("empty fields");
      return;
    }

    var data = {
      "email":name,
      "password":pass
    }

    // post request to log in
    fetch("/api/user/login", {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
      console.log(data);
      if(data.hasOwnProperty("token")) {
        var token = data.token;

        // receive user token from response msg: two fetches from here
        fetch("/api/user/email/" + name)
        .then(data=> {
          console.log(data);
          fetchedData = data;

          // this.setState({
          //   id: fetchedData.id,
          //   first_name: fetchedData.first_name,
          //   reviews: fetchedData.reviews,
          //   friends: fetchedData.friends
          // });
          cookie.save('user',
          {
            id: fetchedData.id,
            email: name,
            username: fetchedData.first_name,
            reviews: fetchedData.reviews,
            friends: fetchedData.friends
          } );

          fetch("api/user/email/"+name+"/groups")
          .then(data=>{
            console.log("SECOND fetch ");
            console.log(data);
            fetchedGroups = data;

            cookie.save('user',
            {
              groups: fetchedGroups.groups
            });
          }
        );

        this.setState({ fireRedirect: true});
        console.log(cookie.load('user'));
      });

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
