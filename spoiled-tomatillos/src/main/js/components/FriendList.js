import React, { Component }  from 'react';
import { Button, Modal, Image, List, Container, Divider, Grid, Header,Icon, Menu, Segment, Item, Label, Table, Statistic, Pagination} from 'semantic-ui-react';
import NavigationBar from './NavigationBar.js';
import { Link } from 'react-router-dom';
import cookie from 'react-cookies';
import UserIDConverter from './UserIDConverter.js';

class FriendRequests extends Component {
  constructor(props) {
    super(props);
    this.state={};
  }
  render() {
    // request list here is a list of request IDs
    const requests = this.props.requests.map((request) =>
    (<Request key={request.id} request={request} />));

  return (
    <div>
    <h3>Friend Requests</h3>
    <Table color='grey' key='grey'>
    <Table.Header>
    <Table.Row>
    <Table.HeaderCell>User</Table.HeaderCell>
    <Table.HeaderCell>Actions</Table.HeaderCell>
    </Table.Row>
    </Table.Header>
    <Table.Body>
    {requests}
    </Table.Body>
    </Table>
    </div>
  )}
}

class Request extends Component {
  constructor(props) {
    super(props);
    this.state={
      request: props.request
    };
  }
  render() {
    return (
      <Table.Row>
      <Table.Cell>test</Table.Cell>
      <Table.Cell>
      <Modal
      open={open} basic size='small'
      onOpen={this.open}
      onClose={this.close}
      style={{height: 300}}
      trigger={<Button icon><Icon link name='reject' /></Button>}>

      <Header icon='archive' content='Are you sure to reject this request?' />
      <Modal.Content>
      <p>Once you confirm, you will reject this friend request.</p>
      </Modal.Content>
      <Modal.Actions>
      <Button basic color='red' inverted onClick={this.close}>
      <Icon name='remove' /> No
      </Button>

      <Button color='green' inverted onClick={this.close}>
      <Icon name='checkmark' /> Yes
      </Button>
      </Modal.Actions>
      </Modal>

      </Table.Cell>
      </Table.Row>
    )}
}

class FriendList extends Component {
  constructor() {
    super();
    this.state={
      cookies: cookie.load('user'),
      requests: []
    };
    console.log("FRIENDLIST  cookies");
    console.log(this.state.cookies);

    var usertoken = this.state.cookies.user_token;
    var useremail = this.state.cookies.email;

    var data = {
      "email":useremail,
      "token":usertoken
    }

    var body = JSON.stringify(data);

    fetch('/api/friend/requests', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: body
    }).then(res=>res.json())
    .then((res)=>{
      console.log(res);
      this.setState({ requests: res })
      console.log("FRIENDLIST  fetch requests");
      console.log(this.state.requests);
    });
  }
  render() {
    return (
      <div>
      <NavigationBar />
      <Link to="/user/:id"><Button basic inverted color='blue'>Back</Button></Link>
      <UserHeading />
      <ConnectedFriends />
      <FriendRequests requests={this.state.requests}/>
      <footer> <Pagination defaultActivePage={5} totalPages={10} /></footer>
      </div>
    )
  }
}

*/}



class ConnectedFriends extends Component {
  constructor(props) {
      super(props);
      this.state={
        cookies:cookie.load('user'),
        friends:[],
        fetchedFriends:[],
      };
      console.log(this.state.cookies);
      var email = this.state.cookies.email;
      var token = this.state.cookies.user_token;
      var data = {
        "email":email,
        "token":token
      }

      fetch('/api/friend/friends', {
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data)
      })
      .then(response => response.json())
      .then(data=>{
        console.log(data);
        var i;
        for(i=0;i<data.length;i++){
          fetch('api/user/id/'+data[i]).then(res=>res.json())
          .then((res)=>{
            console.log(res);
            this.state.friends.push(res);
          })}

      });
    }

  componentWillMount(){
    console.log("hello");
    console.log(this.state.friends);

  }

  render() {
      console.log(this.state.friends);
      var users=[];
      users=this.state.friends;
      console.log(users);

      var friendElements=[];
      for(var i = 0; i < users.length; i++){
        console.log("hrllp");
        friendElements.push(
          <div>users[i].username</div>
        )
      }
      console.log(friendElements);

    return (

      <div>
        <h3>Friend List</h3>
        <div> {this.state.friends}</div>
        <footer> <Pagination defaultActivePage={5} totalPages={10} /></footer>
      </div>
    )
  }
}

class UserHeading extends Component {
  constructor() {
     super();
      this.state={};
    }

  render() {
    return (
      <Container text>
      <Header
      as='h2'
      content='username'
      inverted
      style={{
        fontSize: '1.7em',
        fontWeight: 'normal',
        marginTop: '1.5em',
      }}
      >
      <Grid>
      <Grid.Column width={4}>
      <Image circular src='https://react.semantic-ui.com/assets/images/avatar/large/patrick.png' />
      </Grid.Column>
      <Grid.Column width={9}>

      <Segment inverted>
      <Statistic.Group inverted>
      <Statistic color='blue' size='small'>
      <Statistic.Value>39</Statistic.Value>
      <Statistic.Label>Movies Watched</Statistic.Label>
      </Statistic>
      <Statistic color='blue' size='small'>
      <Statistic.Value>562</Statistic.Value>
      <Statistic.Label>Friends</Statistic.Label>
      </Statistic>
      <Statistic color='blue' size='small'>
      <Statistic.Value>7</Statistic.Value>
      <Statistic.Label>Groups</Statistic.Label>
      </Statistic>
      </Statistic.Group>
      </Segment>
      </Grid.Column>
      <Grid.Column width={3}>
      <Header as='h3' icon inverted>
      <Icon name='settings' />
      Account Settings
      </Header>
      </Grid.Column>
      </Grid>
      </Header>

      </Container>

  }
  render() {
    return (
      <div>
      <NavigationBar />
      <Link to="/user/:id"><Button basic inverted color='blue'>Back</Button></Link>
      <UserHeading />
      <ConnectedFriends />
{/*      <FriendRequests requests={this.state.requests}/>*/}
      </div>
    )
  }
}

const style = {
  h3: {
    marginTop: '2em',
    padding: '2em 0em',
  }

}

export default FriendList;
