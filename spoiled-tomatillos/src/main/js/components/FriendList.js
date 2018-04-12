import React, { Component }  from 'react';
import { Button, Modal, Image, List, Container, Divider, Grid, Header,Icon, Menu, Segment, Item, Label, Table, Statistic, Pagination} from 'semantic-ui-react';
import NavigationBar from './NavigationBar.js';
import { Link } from 'react-router-dom';
import cookie from 'react-cookies';
import UserIDConverter from './UserIDConverter.js';

class UserElement extends Component {
  constructor(props) {
    super(props);
    this.state = {
      type: props.type,
      user: null,
    };
    fetch("/api/user/id/" + props.id).then((res) => {
                    return res.text();
                  }).then((data) => {
                    try {
                      data = JSON.parse(data);
                    } catch (e) {
                      return;
                    }

                    // update state with API data
                    this.setState({
                      type: props.type,
                      user: data,
                    });
                  });
  }

  requestAction(action, target) {
    var user = cookie.load('user');
    fetch("/api/friend/" + action, {
              method: 'POST',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({email: user.email,token: user.user_token,targetEmail:target})
            }).then(() => {this.props.updateHandler()});
  }

  render() {
    if (this.state.user == null) {
      return (<div></div>);
    }
    var user = this.state.user;

    var buttons;
    if (this.state.type == "requests") {
      buttons = (<div>
              {/*    <button onClick={this.requestAction.bind(this, "accept", this.state.user.email)}>Accept</button>*/}
                   <Button floated='left' basic inverted color='grey'
                    onClick={this.requestAction.bind(this, "accept", this.state.user.email)}>Accept</Button>
              {/*      <button onClick={this.requestAction.bind(this, "reject", this.state.user.email)}>Reject</button>*/}
                   <Button floated='left' basic inverted color='grey'
                    onClick={this.requestAction.bind(this, "reject", this.state.user.email)}>Reject</Button>
                 </div>);
    } else {
      buttons = (<div>
  {/*                    <button onClick={this.requestAction.bind(this, "unfriend", this.state.user.email)}>Unfriend</button>*/}
                   <Button floated='left' basic inverted color='grey'
                    onClick={this.requestAction.bind(this, "unfriend", this.state.user.email)}>Unfriend</Button>
                 </div>);

    }

    return (<div>
               <Link to={"/user/" + user.id}>{user.username + "<" +user.email + ">"}</Link>
               {buttons}
            </div>);
  }
}

class RequestElement extends Component {
  constructor(props) {
    super(props);
    this.handleType = this.handleType.bind(this);
    this.handleKeyPress = this.handleKeyPress.bind(this);
    this.requestAction = this.requestAction.bind(this);
  }

  requestAction() {
    var user = cookie.load('user');
    var target = this.searchBox.value;
    fetch("/api/friend/send", {
              method: 'POST',
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({email: user.email,token: user.user_token,targetEmail:target})
            }).then(() => {this.props.updateHandler()});
  }

  /**
   * Handing typing event on the SearchBar.
   */
  handleType(e) {

  }

  /**
   * Handling keyboard event on SearchBar.
   */
  handleKeyPress(e) {
    if (e.key === 'Enter') {
      e.preventDefault();
      this.requestAction();
    }
  }

  render() {
    return (<div>
               <input id='search-text' name='s' ref={(input) => {this.searchBox = input;}}
                           style={{"color":"black",}}
                           placeholder='Exact email of user to Friend' type='text' onInput={this.handleType} onKeyPress={this.handleKeyPress}/>
               <button id='search-button' style={{"color":"black",}} type='button' onClick={this.requestAction}>
                 <span>Add</span>
               </button>
            </div>);
  }
}

class UserList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      type: props.type,
      users:[],
    };
    var user = cookie.load('user');
    fetch("/api/friend/" + props.type, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({email: user.email,token: user.user_token,})
          }).then((res) => {
                    return res.text();
                  }).then((data) => {
                    try {
                      data = JSON.parse(data);
                    } catch (e) {
                      return;
                    }

                    // update state with API data
                    this.setState({
                      type: props.type,
                      users: data,
                    });
                  });
  }

  render() {

    const listItem = this.state.users.map((userId) =>
      (<li><UserElement key={"keyofuserfriendofelementwithidof" + this.state.type + userId}
                        type={this.state.type}
                        id={userId}
                        updateHandler={this.props.updateHandler}/></li>)
    );
    return (<ul>{listItem}</ul>);
  }
}

class FriendList extends Component {
  constructor(props) {
    super(props);
    this.state = { num: 1 }
  }

  update(){
    console.log("Update");
    console.log(this.state);
    this.setState({ num: this.state.num + 2 });
  }

  render() {
    var update = this.update;
    return (<div>
              <RequestElement updateHandler= {update.bind(this)}/>
              <h1>Requests</h1>
              <UserList type="requests" updateHandler= {update.bind(this)} key={this.state.num}/>
              <h1>Friends</h1>
              <UserList type="friends" updateHandler= {update.bind(this)} key={this.state.num + 1}/>
            </div>);
  }
}




export default FriendList;
