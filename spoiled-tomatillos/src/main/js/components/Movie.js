import React, { Component } from 'react';
import './Movie.css';
import MovieCard from './MovieCard.js';


class Movie extends Component {
  constructor(props) {
    super(props)

    this.state = {
      movieID: null
    }
  }
  // the api request function
  fetchApi(url) {

    fetch(url).then((res) => res.json()).then((data) => {
      // update state with API data
      this.setState({
        movieID: data.id,
        original_title: data.original_title,
        overview: data.overview,
        poster: data.poster_path,
        production: data.production_companies,
        production_countries: data.production_countries,
        genre: data.genres,
        release: data.release_date,
        vote: data.vote_average,
        runtime: data.runtime,

      })
    })

    // .catch((err) => console.log('Movie not found!'))

  }
  render() {
    return (
      <div>
        <MovieCard data={this.state}/>
      </div>
    )
  }
}
export default Movie;
