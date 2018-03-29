import React, { Component } from 'react';
import './Movie.css';
import MovieCard from './MovieCard.js';
import { Rating } from 'semantic-ui-react';
import { Button, Comment, Form, Header } from 'semantic-ui-react'
import {
	  Container,
	  Icon,
	  Image,
	  List,
	  Segment,
	  Item,
	  Label,
	  Modal,
	  Table
	} from 'semantic-ui-react';


class AddReview extends Component {
	constructor() {
		super();
		this.state = {
				open:false,
        review:''
		};

		this.open = this.open.bind(this);
	    this.close = this.close.bind(this);
	  }

	  open() {
		  this.setState({ open: true })
	  }
	  close() {
		  this.setState({ open: false })
	  }

    handleClick(event){
      event.preventDefault;
      var rev = this.review.value;

      if(rev.length <= 0) {
          alert("empty field");
          return;
      }

    }

	render() {
		  const { open } = this.state
		return (
				<Modal
					dimmer={false}
				 	open={open}
					onOpen={this.open}
		        		onClose={this.close}
					trigger={<Button floated='right' basic color='blue'>Add</Button>}
		 			style={{height: 500}} >
						<Modal.Header>Add a Review</Modal.Header>
						<Modal.Content>
		      <Modal.Description>
		        <Header>Add a review</Header>
          <label>Add a review</label>
          <textarea
          rows="5"
          className="review"
          ref= {(input) => {this.review = input;}}
          style={divStyle}
          placeholder="Write your review here"></textarea>

		      </Modal.Description>
		    </Modal.Content>
		    <Modal.Actions>
		    		<Button basic color='red' onClick={this.close}>
				<Icon name='remove' /> Cancel
			</Button>
		      <Button primary onClick={this.handleClick.bind(this)}>
		        Add <Icon name='right chevron' />
		      </Button>
		    </Modal.Actions>
		  </Modal>
		)
	}

}


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
						review:data.reviews
          });
        });
  }
  handleChange(e) {
	  this.setState({ rating: e.target.value })
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
        	  <div>
            <MovieCard data={this.state}/>
          </div>
          <div>Rating: {rating}</div>
          <br />
          <div>
          		<Rating icon='star' size='massive' defaultRating={3} maxRating={5} />
               <AddReview />
          </div>
          <Comment.Group>
            <Header as='h3' dividing>Comments</Header>


            <Comment>
              <Comment.Avatar src="https://materiell.com/wp-content/uploads/2015/03/rebecca-full.png" />
              <Comment.Content>
                <Comment.Author as='a'>Veronica</Comment.Author>
                <Comment.Metadata>
                  <div>Today at 5:42PM</div>
                </Comment.Metadata>
                <Comment.Text>How artistic!</Comment.Text>
                <Comment.Actions>
                  <Comment.Action>Reply</Comment.Action>
                </Comment.Actions>
              </Comment.Content>
            </Comment>


            <Comment>
            <Comment.Avatar src='https://materiell.com/wp-content/uploads/2015/03/mikec_full.png' />
                <Comment.Content>
                  <Comment.Author as='a'>Elliot Fu</Comment.Author>
                  <Comment.Metadata>
                    <div>Yesterday at 12:30AM</div>
                  </Comment.Metadata>
                  <Comment.Text>
                    <p>I am very inspired by this movie!! Thanks as well!</p>
                  </Comment.Text>
                  <Comment.Actions>
                    <Comment.Action>Reply</Comment.Action>
                  </Comment.Actions>
                </Comment.Content>

                <Comment.Group>
                <Comment>
                  <Comment.Avatar src='https://materiell.com/wp-content/uploads/2015/03/liz_full1.png' />
                  <Comment.Content>
                    <Comment.Author as='a'>Jenny Hess</Comment.Author>
                    <Comment.Metadata>
                      <div>Just now</div>
                    </Comment.Metadata>
                    <Comment.Text>
                      Elliot you are always so right :)
                    </Comment.Text>
                    <Comment.Actions>
                      <Comment.Action>Reply</Comment.Action>
                    </Comment.Actions>
                  </Comment.Content>
                </Comment>
              </Comment.Group>
            </Comment>


            <Comment>
            <Comment.Avatar src='https://materiell.com/wp-content/uploads/2015/03/bill-small.png' />
            <Comment.Content>
              <Comment.Author as='a'>Joe Henderson</Comment.Author>
              <Comment.Metadata>
                <div>5 days ago</div>
              </Comment.Metadata>
              <Comment.Text>
                Dude, this is awesome. Thanks so much
              </Comment.Text>
              <Comment.Actions>
                <Comment.Action>Reply</Comment.Action>
              </Comment.Actions>
            </Comment.Content>
          </Comment>


          <Form reply>
            <Form.TextArea />
            <Button content='Add Reply' labelPosition='left' icon='edit' primary />
          </Form>

          </Comment.Group>
          </div>
      )
    }
  }
}
export default Movie;


var divStyle = {
  color: 'blue'
};
