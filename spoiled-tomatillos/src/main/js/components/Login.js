import React, { Component } from 'react';
import './Login.css';
import FacebookLogin from 'react-facebook-login/dist/facebook-login-render-props';
import {Button} from 'react-bootstrap'

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
      
      <div style={margin} className="input-group">
      <input
      type="text"
      className="username"
      ref= {(input) => {this.username = input;}}
      onChange = {(event, newValue) => this.setState((prev, props) =>
      {prev.username = newValue; return prev;})}
      placeholder="username"/>

    	  </div>
      
      <div style={margin} className="input-group">
      <input
      type="password"
      className="password"
      ref= {(input) => {this.password = input;}}
      onChange = {(event, newValue) => this.setState((prev, props) =>
      {prev.password = newValue; console.log(newValue); return prev;})}
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

    

    <FacebookLogin
    appId="1229282497194175"
    autoLoad
    callback={this.responseFacebook}
    render={renderProps => (
      <Button bsStyle="info"
    	  style={fbStyle}
      bsSize="large"
      onClick={renderProps.onClick}>Login with facebook</Button>
    )}
    />
    
    </div>
    </div>
      
     
    <div className="form-group">
    <div className="col-md-12 control">
        <div style={styles}>
            Don't have an account?
                
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
		margin:'10px'
};

const margin = {
		margin:'25px'
};

const fbStyle = {
		color: 'white',
		textAlign: 'center',
		fontSize: '18px'
};

export default Login;
