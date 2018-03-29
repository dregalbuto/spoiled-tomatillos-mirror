import React, { Component } from 'react';
import cookie from 'react-cookies'
import './UserHome.css';
import { Link } from 'react-router-dom';
import NavigationBar from './NavigationBar.js';
import SearchBar from './SearchBar.js';
import { Embed, Header } from 'semantic-ui-react'
import {Button} from 'react-bootstrap';

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


class TitleList extends Component {
  render() {
    return(
      <div className="Title">
      <h2>{this.props.title}</h2>
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

    <ul>
      <li>
        <div>{review.movie.title}
          <div>{review.text}
          {review.id}
              <Button onClick={(e)=>this.handleDelete(review.id,e)}>
                delete
              </Button>
          </div>
        </div>
      </li>
    </ul>
  );




  return (
    <div>
      <header>
        <NavigationBar />
          <SearchBar />
            <Header as='h4' inverted color='blue' size='huge'>Hi, {this.state.cookies.username}</Header>
            <h1>My review</h1>
            <div>{listItem}</div>
      </header>

      <Features />

      <div className="TitleList">
        <div className="Title">
          <h1>Top Picks </h1>
        </div>
        <div className="Title">
          <h1>Recommended For Me</h1>
        </div>
      </div>
    </div>
  );
}
}
export default Home;
