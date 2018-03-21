import React, { Component } from 'react';
import './Movie.css';
import MovieCard from './MovieCard.js';
import { Rating } from 'semantic-ui-react';


/**
 * The movie page to show information and action on a movie. The state stores the information about
 * the movie. Mapped to page /Movie/:id where id is the id of the movie.
 */
class Movie extends Component {
  /**
   * Constructs a movie page where state is the information fetched from the server for given id.
   */
  constructor(props) {
    super(props);
    this.state = { rating: 0 }
    
    fetch("/api/movies/info?id=" + props.match.params.id)
        .then((res) => {
          return res.text();
        }).then((data) => {
          try {
            data = JSON.parse(data);
          } catch (e) {
            this.setState({});
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
          });
        });
  }
  handleChange(e) {
	  this.setState({ this.rating: e.target.value })
  }

  /**
   * Renders a movie page with all the information and actions for given movie.
   */
  render() {
	const { rating } = this.state.rating
	
    console.log(this.state);
    if (this.state == null) {
      return <div><p>Loading...</p></div>
    } else if (this.state.movieID == null) {
      return <p>ERROR {this.props.match.params.id} not found</p>
    } else {
      return (
        <div>
          <MovieCard data={this.state}/>
          <br />
          <div>Rating: {rating}</div>
          <br />
          <div>
          		<Rating icon='star' size='massive' defaultRating={3} maxRating={5} />
          </div>
          </div>
      )
    }
  }
}
export default Movie;
