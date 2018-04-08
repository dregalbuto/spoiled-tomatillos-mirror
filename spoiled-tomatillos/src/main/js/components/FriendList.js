import React, { Component }  from 'react'
import cookie from 'react-cookies'
import { Button, Image, List, Container, Divider,
Grid, Header,Icon, Menu, Segment, Item, Label, Table, Statistic, Pagination} from 'semantic-ui-react'
import NavigationBar from './NavigationBar.js';
import { Link } from 'react-router-dom';

class Request extends Component {
  constructor() {
    super();
    this.state={};
  }
  render(){
    return (
      <Table.Row>
      <Table.Cell><div>{this.state.request.name}</div></Table.Cell>
      <Table.Cell><div>{this.state.request.creator.id}</div></Table.Cell>
      <Table.Cell><div>{this.state.request.topic.id}</div></Table.Cell>
      <Table.Cell>
      <ManageGroup />
      <Modal
      open={open} basic size='small'
        onOpen={this.open}
      onClose={this.close}
      style={{height: 300}}
      trigger={<Button icon><Icon link name='delete' /></Button>}>

      <Header icon='archive' content='Are you sure to delete this group?' />
        <Modal.Content>
      <p>Once you confirm, you will lose the members and reviews in this request.</p>
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
    )
  }
}
{/*
class FriendRequests extends Component {
  constructor() {
    super();
    this.state={};
  }
  render() {
    const requests = this.state.requests.map((request) =>
			<Request key={request.id} request={request} />
		);

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
            this.state.friends.push(res.username);
          })}

      });
    }

  componentWillMount(){
    console.log("hello");
    console.log(this.state.friends);

  }

  render() {
      console.log(this.state.friends);

      const listItem = this.state.friends.map((friend) =>
      <List.Item>
      <List.Content floated='right'>
      <Button>Remove</Button>
      </List.Content>
      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/lena.png' />
      <List.Content>
      {friend}
      </List.Content>
      </List.Item>

    );
    return (

      <div>
      <h3>Friend List</h3>
      <List divided verticalAlign='middle' size='massive'>

    <div>{listItem}</div>
      </List>
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

    )
  }
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
