import React, { Component }  from 'react'
import cookie from 'react-cookies'
import { Button, Image, List, Container, Divider,
Grid, Header,Icon, Menu, Segment, Item, Label, Table, Statistic, Pagination} from 'semantic-ui-react'
import NavigationBar from './NavigationBar.js';
import { Link } from 'react-router-dom';


class UserHeading extends Component {
  constructor(props) {
    super(props);
    this.state={
      cookies:cookie.load('user'),
      friends:[],
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
    });

{/*
    fetch("api/user/email/"+this.state.cookies.email).then(res=>res.json()).
    then((res)=>{
      console.log(res);
    }
    */}

  }

  componentWillMount() {
    {/*
    this.setState({cookies:cookie.load('user')});
    console.log(this.state.cookies);
    var userEmail = this.state.cookies.email;
    fetch("/api/user/email" + userEmail)
        .then(res=>res.json())
        .then(json=>{console.log(json)});*/}
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
        {' '}Patrick
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
    this.state={};
  }

  render() {
    return (
      <div>
      <NavigationBar />
      <Link to="/user/:id"><Button basic inverted color='blue'>Back</Button></Link>
      <UserHeading />

      <h3>Friend List</h3>
      <List divided verticalAlign='middle' size='massive'>
      <List.Item>
      <List.Content floated='right'>
      <Button>Remove</Button>
      </List.Content>
      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/lena.png' />
      <List.Content>
      Lena
      </List.Content>
      </List.Item>
      <List.Item>
      <List.Content floated='right'>
      <Button>Remove</Button>
      </List.Content>
      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/lindsay.png' />
      <List.Content>
      Lindsay
      </List.Content>
      </List.Item>
      <List.Item>
      <List.Content floated='right'>
      <Button>Remove</Button>
      </List.Content>
      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/mark.png' />
      <List.Content>
      Mark
      </List.Content>
      </List.Item>
      <List.Item>
      <List.Content floated='right'>
      <Button>Remove</Button>
      </List.Content>
      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/molly.png' />
      <List.Content>
      Molly
      </List.Content>
      </List.Item>
      </List>
      <footer> <Pagination defaultActivePage={5} totalPages={10} /></footer>
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
