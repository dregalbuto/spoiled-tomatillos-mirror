import React, { Component } from 'react';
import './Movie.css';

class Card extends Component {
  render() {
    // props.data = Movie ID
    let data = this.props.data;

    let posterIMG = data.poster,
        production = data.production,
        productionCountries = data.production_countries,
        genres = data.genre,
        noData = '-';

    // conditional statements for no data
    if (data.vote === 'undefined' || data.vote === 0) {
      data.vote = noData
    } else {
      data.vote = data.vote + ' / 10'
    };

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
               <div className="col-xs-6"> Running Time: <span className="meta-data">{data.runtime} mins</span> </div>
               <div className="col-xs-6"> Vote Average: <span className="meta-data">{data.vote}</span></div>
             </div>
           </div>
         </div>
         <div className="poster-container nopadding col-xs-12 col-md-4 pull-md-8 col-lg-5 pull-lg-7 ">
           <img id="postertest" className='poster' src={posterIMG}/>
         </div>
       </div>

    );

  }
}


class Movie extends Component {
  constructor(props) {
    super(props)

    this.state = {
      movieID: null
    }
  }
  render() {
    return (
      <div>
        <Card data={this.state}/>
      </div>
    )
  } // END render

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
}
export default { Card, Movie };
