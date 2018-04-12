import React, { Component }  from 'react'
import { Link } from 'react-router-dom';
import cookie from 'react-cookies'

// currently take in an request ID, and only return email
class IDConverter extends Component {
  constructor(props) {
    super(props);
    this.state={
      id: this.props.id,
      email: '',
      username: ''
    };
    console.log(this.state);
    if (this.state.id !== undefined) {
      var form = new FormData();

      fetch("api/user/id/" + this.state.id,{
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: form

      }).then((res)=> {
        return res.text();
      }).then((data)=> {
        try {
          data = JSON.parse(data);
        } catch (e) {
          return;
        }

        this.setState({
          email: data.email,
          username: data.username
        });
      });
    } else if (props.data !== undefined) {
      this.state = props.data;
    }
  }
  render() {
    if (this.state.id == null) {
     return (<p>Request not found</p>);
   }

   let id = this.state.id;
   let email = this.state.email;
   let username = this.state.username;

   return (
     <div>
     {email}
     </div>
   )

  }
}
