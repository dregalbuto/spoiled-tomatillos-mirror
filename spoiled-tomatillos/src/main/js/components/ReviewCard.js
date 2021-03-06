import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import cookie from 'react-cookies';
import { Rating } from 'semantic-ui-react';

class ReviewCard extends Component {

  constructor(props) {
    super(props);
    this.state = {
          id: null,
          rating: 0,
          critic: false,
          text: "",
          userId: null,
    };
    console.log(this.state);
    if (props.id !== undefined) {
      fetch("/api/reviews/get", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({reviewId: props.id})
      }).then((res) => {
                return res.text();
              }).then((data) => {
                try {
                  data = JSON.parse(data);
                } catch (e) {
                  return;
                }

                if (data.critic) {
                  this.setState({
                    id: data.id,
                    rating: data.rating,
                    critic: data.critic,
                    text: data.text,
                    userId: null,
                  });
                  return;
                }
                // update state with API data
                this.setState({
                  id: data.id,
                  rating: data.rating,
                  critic: data.critic,
                  text: data.text,
                  userId: data.userId,
                });
              });
    } else if (props.data !== undefined) {
      this.state = props.data;
    }
  }

  render() {
    if (this.state.id == null) {
      return (<p>Review not found</p>);
    }

    let id = this.state.id;
    let rating = this.state.rating;
    let critic = this.state.critic;
    let text = this.state.text;
    let userId = this.state.userId;

    let cri = (<div className="row"><h1>Review</h1>
               <small><Link to={"/user/" + userId}>User</Link></small></div>);
    if (this.props.hidden == true || this.props.hidden == "true") {
      cri = (<div><small><Link to={"/user/" + userId}>User</Link></small></div>);
    }
    if(critic) {
      if (this.props.hidden == true || this.props.hidden == "true") {
        cri = (<div><span className="tagline">Critic</span></div>);
      } else {
        cri = (<div><div className="row"><h1>Review</h1></div><span className="tagline">Critic</span></div>);
      }
    }

    return (
	  <div>
	    {cri}
	    <div><Rating icon='star' size='massive' defaultRating={rating} maxRating={5} /></div>
	    <div>{text}</div>
	  </div>
    );
  }
}

class MovieReviews extends Component {
  constructor(props) {
    super(props);
    this.state = {
          movieID: null,
          original_title: "",
          release: 0,
          genre: "",
          actors: "",
          overview: "",
          runtime: -1,
          poster: null,
          reviews: [],
    };
    console.log(this.state);
    if (props.id !== undefined) {
      fetch("/api/movies/info?id=" + props.id)
              .then((res) => {
                return res.text();
              }).then((data) => {
                try {
                  data = JSON.parse(data);
                } catch (e) {
                  return;
                }

                // update state with API data
                this.setState({
                  movieID: data.id,
                  original_title: data.title,
                  release: data.releaseDate,
                  genre: data.genres,
                  actors: data.actors,
                  overview: data.description,
                  runtime: data.runtimeMinuets,
                  poster: data.imgURL,
                  reviews: data.reviews,
                });
              });
    } else if (props.data !== undefined) {
      this.state = props.data;
    }
  }

  render() {
    const listItem = this.state.reviews.map((rev) =>
      (<li><ReviewCard key={"keyofreivewofelementwithidof" + rev.id} id={rev.id} hidden={true} /></li>)
    );
    return (<div><h1>Reviews</h1><ul>{listItem}</ul></div>);
  }
}

class UserReviews extends Component {
  constructor(props) {
      super(props);
      var id = cookie.load('user').id;
      if (props.id !== undefined) {
         id = props.id;
      }

      this.state = {
            userId: id,
            email: "",
            reviews: [],
      };
        fetch("/api/user/id/" + this.state.userId)
                .then((res) => {
                  return res.text();
                }).then((data) => {
                  try {
                    data = JSON.parse(data);
                  } catch (e) {
                    return;
                  }

                  // update state with API data
                  this.setState({
                    userId: this.state.userId,
                    email: data.email,
                    reviews: data.reviews,
                  });
                });
    }

    deleteReview(id, e) {
      let token = cookie.load('user').user_token;
      fetch("/api/reviews/delete", {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({"email": this.state.email, "token": token, "reviewId": id})
      }).then((e) => {
        fetch("/api/user/id/" + this.state.userId)
                .then((res) => {
                  return res.text();
                }).then((data) => {
                  try {
                    data = JSON.parse(data);
                  } catch (e) {
                    return;
                  }

                  // update state with API data
                  this.setState({
                    userId: this.state.userId,
                    email: data.email,
                    reviews: data.reviews,
                  });
                });});
      return false;
    }

    render() {
      const listItem = this.state.reviews.map((rev) =>
        (<li><ReviewCard key={"keyofreivewofelementwithidof" + rev} id={rev} hidden={true} />
        <button onClick={this.deleteReview.bind(this, rev)}>Delete</button></li>)
      );
      return (<div><h1>Reviews</h1><ul>{listItem}</ul></div>);
    }
}

export default ReviewCard;
export { MovieReviews, UserReviews };