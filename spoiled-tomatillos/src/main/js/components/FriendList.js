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
                   <button onClick={this.requestAction.bind(this, "accept", this.state.user.email)}>Accept</button>
                   <button onClick={this.requestAction.bind(this, "reject", this.state.user.email)}>Reject</button>
                 </div>);
    } else {
      buttons = (<div>
                   <button onClick={this.requestAction.bind(this, "unfriend", this.state.user.email)}>Unfriend</button>
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


//class FriendRequests extends Component {
//  constructor(props) {
//    super(props);
//    this.state={};
//  }
//  render() {
//    // request list here is a list of request IDs
//    const requests = this.props.requests.map((request) =>
//    (<Request key={request.id} request={request} />));
//
//  return (
//    <div>
//    <h3>Friend Requests</h3>
//    <Table color='grey' key='grey'>
//    <Table.Header>
//    <Table.Row>
//    <Table.HeaderCell>User</Table.HeaderCell>
//    <Table.HeaderCell>Actions</Table.HeaderCell>
//    </Table.Row>
//    </Table.Header>
//    <Table.Body>
//    {requests}
//    </Table.Body>
//    </Table>
//    </div>
//  )}
//}
//
//class Request extends Component {
//  constructor(props) {
//    super(props);
//    this.state={
//      request: props.request
//    };
//  }
//  render() {
//    return (
//      <Table.Row>
//      <Table.Cell>test</Table.Cell>
//      <Table.Cell>
//      <Modal
//      open={open} basic size='small'
//      onOpen={this.open}
//      onClose={this.close}
//      style={{height: 300}}
//      trigger={<Button icon><Icon link name='reject' /></Button>}>
//
//      <Header icon='archive' content='Are you sure to reject this request?' />
//      <Modal.Content>
//      <p>Once you confirm, you will reject this friend request.</p>
//      </Modal.Content>
//      <Modal.Actions>
//      <Button basic color='red' inverted onClick={this.close}>
//      <Icon name='remove' /> No
//      </Button>
//
//      <Button color='green' inverted onClick={this.close}>
//      <Icon name='checkmark' /> Yes
//      </Button>
//      </Modal.Actions>
//      </Modal>
//
//      </Table.Cell>
//      </Table.Row>
//    )}
//}
//
//class FriendList extends Component {
//  constructor(props) {
//    super(props);
//    this.state={
//      cookies: cookie.load('user'),
//      requests: []
//    };
//    console.log("FRIENDLIST  cookies");
//    console.log(this.state.cookies);
//
//    var usertoken = this.state.cookies.user_token;
//    var useremail = this.state.cookies.email;
//
//    var data = {
//      "email":useremail,
//      "token":usertoken
//    }
//
//    var body = JSON.stringify(data);
//
//    fetch('/api/friend/requests', {
//      method: 'POST',
//      headers: {
//        'Accept': 'application/json',
//        'Content-Type': 'application/json',
//      },
//      body: body
//    }).then(res=>res.json())
//    .then((res)=>{
//      console.log(res);
//      this.setState({ requests: res })
//      console.log("FRIENDLIST  fetch requests");
//      console.log(this.state.requests);
//    });
//  }
//  render() {
//    return (
//      <div>
//      /*<NavigationBar />
//      <Link to="/user/:id"><Button basic inverted color='blue'>Back</Button></Link>
//      <UserHeading />
//      <ConnectedFriends />
//      <FriendRequests requests={this.state.requests}/>
//      <footer> <Pagination defaultActivePage={5} totalPages={10} /></footer>*/
//      </div>
//    )
//  }
//}
//
//
//
//class ConnectedFriends extends Component {
//  constructor(props) {
//      super(props);
//      this.state={
//        cookies:cookie.load('user'),
//        friends:[],
//        fetchedFriends:[],
//      };
//      console.log(this.state.cookies);
//      var email = this.state.cookies.email;
//      var token = this.state.cookies.user_token;
//      var data = {
//        "email":email,
//        "token":token
//      }
//
//      fetch('/api/friend/friends', {
//          method: 'POST',
//          headers: {
//            'Accept': 'application/json',
//            'Content-Type': 'application/json',
//          },
//          body: JSON.stringify(data)
//      })
//      .then(response => response.json())
//      .then(data=>{
//        console.log(data);
//        var i;
//        for(i=0;i<data.length;i++){
//          fetch('api/user/id/'+data[i]).then(res=>res.json())
//          .then((res)=>{
//            console.log(res);
//            this.state.friends.push(res);
//          })}
//
//      });
//    }
//
//  componentWillMount(){
//    console.log("hello");
//    console.log(this.state.friends);
//
//  }
//
//  render() {
//      console.log(this.state.friends);
//      var users=[];
//      users=this.state.friends;
//      console.log(users);
//
//      var friendElements=[];
//      for(var i = 0; i < users.length; i++){
//        console.log("hrllp");
//        friendElements.push(
//          <div>users[i].username</div>
//        )
//      }
//      console.log(friendElements);
//
//    return (
//
//      <div>
//        <h3>Friend List</h3>
//        <div> {this.state.friends}</div>
//        <footer> <Pagination defaultActivePage={5} totalPages={10} /></footer>
//      </div>
//    )
//  }
//}
//
//class UserHeading extends Component {
//  constructor() {
//     super();
//      this.state={};
//    }
//
///*
//  render() {
//    return (
//      <Container text>
//      <Header
//      as='h2'
//      content='username'
//      inverted
//      style={{
//        fontSize: '1.7em',
//        fontWeight: 'normal',
//        marginTop: '1.5em',
//      }}
//      >
//      <Grid>
//      <Grid.Column width={4}>
//      <Image circular src='https://react.semantic-ui.com/assets/images/avatar/large/patrick.png' />
//      </Grid.Column>
//      <Grid.Column width={9}>
//
//      <Segment inverted>
//      <Statistic.Group inverted>
//      <Statistic color='blue' size='small'>
//      <Statistic.Value>39</Statistic.Value>
//      <Statistic.Label>Movies Watched</Statistic.Label>
//      </Statistic>
//      <Statistic color='blue' size='small'>
//      <Statistic.Value>562</Statistic.Value>
//      <Statistic.Label>Friends</Statistic.Label>
//      </Statistic>
//      <Statistic color='blue' size='small'>
//      <Statistic.Value>7</Statistic.Value>
//      <Statistic.Label>Groups</Statistic.Label>
//      </Statistic>
//      </Statistic.Group>
//      </Segment>
//      </Grid.Column>
//      <Grid.Column width={3}>
//      <Header as='h3' icon inverted>
//      <Icon name='settings' />
//      Account Settings
//      </Header>
//      </Grid.Column>
//      </Grid>
//      </Header>
//
//      </Container>)
//
//  }
//*/
//  render() {
//    return (
//      <div>
//      <NavigationBar />
//      <Link to="/user/:id"><Button basic inverted color='blue'>Back</Button></Link>
//      <UserHeading />
//      <ConnectedFriends />
//{/*      <FriendRequests requests={this.state.requests}/>*/}
//      </div>
//    )
//  }
//}
//
//const style = {
//  h3: {
//    marginTop: '2em',
//    padding: '2em 0em',
//  }
//
//}

export default FriendList;
