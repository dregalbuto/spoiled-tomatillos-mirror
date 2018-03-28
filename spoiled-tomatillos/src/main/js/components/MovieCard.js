import React, { Component } from 'react';
import './Movie.css';

/**
 * Displays a movie with information about the movie.
 * Takes in an id and fetch information from server or takes in data with all the information in a
 * format as shown below.
 */
//    Takes in the following object as props.data
//   {movieID: data.id,
//    original_title: data.title,
//    release: data.releaseDate,
//    genre: data.genres,
//    actors: data.actors,
//    overview: data.description,
//    runtime: data.runtimeMinuets,
//    poster: data.imgURL,}
class MovieCard extends Component {
  /**
   * Constructor that takes in id or data. If not found, sets everything as empty or 0 or null.
   */
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
                  reviews: []
                });
              });
    } else if (props.data === undefined) {
      return;
    } else {
      this.state = props.data;
    }
  }

  /**
   * Render this as defined with the data from the structure as shown above as this.state.
   */
  render() {
    let data = this.state;

    let posterIMG = data.poster,
        production = data.production,
        productionCountries = data.production_countries,
        genres = data.genre,
        noData = '-';

    // conditional statements for no data
    if (data.vote === undefined || data.vote === 0) {
      data.vote = noData;
    } else {
      data.vote = data.vote + ' / 10';
    }

    if(data.poster== null){
        posterIMG = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSols5HZxlQWyS9JY5d3_L9imbk0LiziHiyDtMZLHt_UNzoYUXs2g';
    }

    return (
      <div className="col-xs-12 cardcont nopadding">

         <div className="meta-data-container col-xs-12 col-md-8 push-md-4 col-lg-7 push-lg-5">
           <h1>{data.original_title}</h1>

           <span className="tagline">{data.tagline}</span>
           <p>{data.overview}</p>
           <div className="additional-details">
             <div className="row nopadding release-details">
               <div className="col-xs-6"> Original Release: <span className="meta-data">{data.release}</span></div>
               <div className="col-xs-6"> Running Time: <span className="meta-data">{data.runtime}</span> </div>
               <div className="col-xs-6"> Vote Average: <span className="meta-data">{data.vote}</span></div>
             </div>
           </div>
         </div>
         <div className="poster-container nopadding col-xs-12 col-md-4 pull-md-8 col-lg-5 pull-lg-7 ">
           <img id="postertest" className='poster' src={posterIMG}/>
         </div>

         <div className="reviews">
         {this.data.reviews}
         </div>

         </div>
       </div>
    );

  }
}

export default MovieCard;
