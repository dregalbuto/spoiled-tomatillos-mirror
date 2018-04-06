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

    let cri = "";
    if(critic) {
      cri = <span className="tagline">Critic</span>;
    }

    return (
	  <div>
	    <div className="row"><h1><Link to={"/user/" + userId}>Review</Link></h1></div>
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
      (<li><ReviewCard key={"keyofreivewofelementwithidof" + rev.id} id={rev.id} /></li>)
    );
    return (<ul>{listItem}</ul>);
  }
}

export default ReviewCard;
export { MovieReviews };