import React, { Component } from 'react';
import cookie from 'react-cookies'
import './UserHome.css';
import { Link } from 'react-router-dom';
import NavigationBar from './NavigationBar.js';
import SearchBar from './SearchBar.js';
import { Embed, Header, Statistic, Grid, Container, Image, Segment, Icon, List, Item, Button, Label } from 'semantic-ui-react'
import { UserReviews } from './ReviewCard.js';

class GroupList extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    return (
      <Segment style={{ padding: '8em 0em' }} vertical>

       <Header as='h3' inverted style={{ fontSize: '2em' }}>Groups</Header>
       <Container>
         <Item.Group divided>
           <Item>
             <Item.Image src='https://ia.media-imdb.com/images/M/MV5BNDI5NzU2OTM1MV5BMl5BanBnXkFtZTgwNzU3NzM3MzI@._V1_SY1000_CR0,0,675,1000_AL_.jpg' />
             <Item.Content>
               <Item.Header as='a' style={{color:'white'}}>Group A</Item.Header>
               <Item.Meta style={{color:'white'}}>
                 <span>Drama</span>
                 <span>Mystery</span>
               </Item.Meta>
               <Item.Description style={{color:'white'}}>
               A blind woman's relationship with her husband changes when she regains her sight and discovers disturbing details about themselves.
               </Item.Description>
               <Item.Extra style={{color:'white'}}>
                 <Image
                   avatar
                   circular
                   src='https://www.shareicon.net/data/128x128/2016/06/26/786570_people_512x512.png'
                 />
                 Emily B.
               </Item.Extra>
             </Item.Content>
           </Item>

           <Item>
             <Item.Image src='https://ia.media-imdb.com/images/M/MV5BNTA1OTAzNTExOF5BMl5BanBnXkFtZTgwNjQ1ODY3MzI@._V1_SY1000_CR0,0,649,1000_AL_.jpg' />
             <Item.Content>
               <Item.Header as='a' style={{color:'white'}}>Group B</Item.Header>
               <Item.Meta style={{color:'white'}}>
                 <span>Date</span>
                 <span>Category</span>
               </Item.Meta>
               <Item.Description style={{color:'white'}}>
                 A description which may flow for several lines and give context to the content.
               </Item.Description>
               <Item.Extra>
                 <Button floated='right' primary>
                   Primary
                   <Icon name='right chevron' />
                 </Button>
                 <Label>Limited</Label>
               </Item.Extra>
             </Item.Content>
           </Item>
           <Item>
             <Item.Image src='https://ia.media-imdb.com/images/M/MV5BMTYyOTUwNjAxM15BMl5BanBnXkFtZTgwODcyMzE0NDM@._V1_.jpg' />
             <Item.Content>
               <Item.Header as='a' style={{color:'white'}}>Group C</Item.Header>
               <Item.Meta style={{color:'white'}}>
                 <span>Animation</span>
                 <span>Adventure</span>
                 <span>Comedy</span>
               </Item.Meta>
               <Item.Description style={{color:'white'}}>
                 A description which may flow for several lines and give context to the content.
               </Item.Description>
               <Item.Extra>
                 <Button primary floated='right'>
                   Primary
                   <Icon name='right chevron' />
                 </Button>
               </Item.Extra>
             </Item.Content>
           </Item>
         </Item.Group>
       </Container>

       <Link to="/Groups"><Button floated='right' basic inverted color='grey'>More</Button></Link>
       </Segment>

    )
  }
}

class FriendList extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <Segment style={{ padding: '8em 0em' }} vertical>
		    <div>
		    <Header as='h3' inverted style={{fontSize:'2em'}} icon>
		      <Icon name='users' circular />
		      <Header.Content>
		        Friends
		      </Header.Content>

		    </Header>

		    <List relaxed>
		    <List.Item>
		      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/daniel.jpg' />
		      <List.Content>
		        <List.Header as='a'>Daniel Louise</List.Header>
		        <List.Description style={{color:'white'}}>Last seen watching <a><b>Arrested Development</b></a> just now.</List.Description>
		      </List.Content>
		    </List.Item>
		    <List.Item>
		      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/stevie.jpg' />
		      <List.Content>
		        <List.Header as='a'>Stevie Feliciano</List.Header>
		        <List.Description style={{color:'white'}}>Last seen watching <a><b>Bob's Burgers</b></a> 10 hours ago.</List.Description>
		      </List.Content>
		    </List.Item>
		    <List.Item>
		      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/elliot.jpg' />
		      <List.Content>
		        <List.Header as='a'>Elliot Fu</List.Header>
		        <List.Description style={{color:'white'}}>Last seen watching <a><b>The Godfather Part 2</b></a> yesterday.</List.Description>
		      </List.Content>
		    </List.Item>
		  </List>

		  </div>
		   <Link to="/FriendList"><Button floated='right' basic inverted color='grey'>More</Button></Link>
		    </Segment>

    )
  }
}


class UserStats extends Component {
  constructor(props) {
    super(props);
    this.state = {};
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

class FeaturesButton extends Component {
  render() {
    return (
      <button className="Button" data-primary={this.props.primary}>{this.props.text}</button>
    );
  }
}
///////////////// Features /////////////////////
class Features extends Component {

  render() {
    return (
      <div id="features" className="Features" style={{backgroundImage: 'url(https://therealsasha.wordpress.com/2015/03/01/interstellar-review/)'}}>
      <div className="content">

      <Embed
      id='2LqzF5WauAw'
      placeholder='https://ia.media-imdb.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SY1000_SX675_AL_.jpg'
      source='youtube'

      />
      <a href="http://www.imdb.com/title/tt0816692/?ref_=tt_mv_close"> <h2>Watch interstellar now</h2> </a>

      <div className="button-wrapper">

      <FeaturesButton primary={true} text="Watch now">
      </FeaturesButton>

      <FeaturesButton primary={false} text="+ My list" />
      </div>
      </div>
      <div className="overlay"></div>
      </div>
    );
  }
}


///////////////// Home /////////////////////
class Home extends Component {
  constructor(props){
    super(props);
    this.state = {
      cookies: '',
      reviews:[],
    };
    //console.log(this.props.match.params.id);
    fetch("/api/user/id/" + props.match.params.id)
    .then(response => response.json()).then(response=>{
      //console.log(response.reviews)
      for(var i = 0; i<response.reviews.length;i++){
        //console.log(response.reviews[i]);
      }
      this.state.reviews = response.reviews;
      console.log(this.state.reviews);

    });

     this.handleDelete = this.handleDelete.bind(this);
  }


  componentWillMount() {
    {
      /* Load cookie from login page
      * user:  user_token,	id, email, username
      * */
    }
    this.setState( {cookies: cookie.load('user')} );
    //console.log("UserHome: ");
  }

  handleDelete(id,e){
    e.preventDefault;
    console.log(this.state.cookies);
    console.log(this.state.cookies.user_token)
    var usertoken = this.state.cookies.user_token;
    var useremail = this.state.cookies.email;
    var reviewid = id;
    var userId = this.state.cookies.id;
    var fetchedData = {};

    console.log(id);
    console.log(usertoken);

    var data = {
      "token":usertoken,
      "postId":reviewid,
      "email":useremail
    }

    var body = JSON.stringify(data);

    fetch('/api/reviews/delete', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: body
    }).then(response=>response.json()).then(data =>{
      if(data.hasOwnProperty("deleted")){
        alert("delete successfully");
        fetch("/api/user/email/" + useremail)
            .then(res=>res.json())
            .then(res=>{
              fetchedData = res;
              this.state.cookies.reviews = fetchedData.reviews;
              this.setState(this.state);
            });



      } else {
        var error = data.message;
        alert(error);
      }
    });
  }

  render() {
    console.log(this.state.cookies);
    console.log(this.state.cookies.reviews);


    const listItem = this.state.cookies.reviews.map((review) =>

    // <ul>
    //   <li>
    //     <div>{review.movie.title}
    //       <div>{review.text}
    //       {review.id}
    //           <Button onClick={(e)=>this.handleDelete(review.id,e)}>
    //             delete
    //           </Button>
    //       </div>
    //     </div>
    //   </li>
    // </ul>
    <div>TEST - movie review </div>
  );


  return (
    <div>
      <header>
        <NavigationBar />
          <SearchBar />
          <UserStats />
            <Header as='h4' inverted color='blue' size='huge'>Hi, {this.state.cookies.username}</Header>
            <h1>My review</h1>
            <UserReviews />
            <div>{listItem}</div>
      </header>

      <Features />

      <GroupList />
      <FriendList />
    </div>
  );
}
}
export default Home;
