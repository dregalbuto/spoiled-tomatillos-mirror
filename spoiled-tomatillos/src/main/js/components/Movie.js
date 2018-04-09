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
import cookie from 'react-cookies'
import ReviewCard, { MovieReviews } from './ReviewCard.js';


class Recomendation extends Component {
	constructor(props){
		super(props);
		this.state={
			open:false,
			recipient:'',
			msg:'',
		};
		console.log(this.props.data);
		this.open = this.open.bind(this);
		this.close = this.close.bind(this);
	}

	open() {
		this.setState({ open: true })
	}
	close() {
		this.setState({ open: false })
	}

	handleClick(){
		console.log("hekll;l'a");
	}


		render() {
			const { open } = this.state
			return (
			<Modal
				dimmer={false}
				open={open}
				onOpen={this.open}
				onClose={this.close}
				trigger={<Button floated='right' basic color='blue'>Send</Button>}
				style={{height: 500}} >
					<Modal.Header>
						Recommend this movie to your friend!
					</Modal.Header>
					<Modal.Content>
						<Modal.Description>
						<Form>
						<Form.Field>
							<label>Share to:</label>
							<input placeholder="recipient email address"
							ref= {(input) => {this.recipient = input;}}
							style={divStyle}/>
							</Form.Field>
							<Form.TextArea label = "Leave a message"
							ref= {(input) => {this.msg = input;}}
							style={divStyle}
							placeholder="leave your message here"></Form.TextArea>

							</Form>
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

class AddReview extends Component {
	constructor(props) {
		super(props);
		this.state = {
			open:false,
			review:''
		};

		console.log(this.props.data);

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
		var token = this.props.data.cookies.user_token;
		var email = this.props.data.cookies.email;
		var movieId = this.props.data.movieID;
		var rev = this.review.value;
		var rating = 0;
		var fetchedData = {};
		if(rev.length <= 0) {
			alert("empty field");
			return;
		}

		var data = {
			"token":token,
			"rating":rating,
			"email":email,
			"text":rev,
			"movieId":movieId
		}

		fetch('/api/reviews/post', {
				method: 'POST',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(data)
		}).then(response=>response.json()).then(data =>{
      if(data.hasOwnProperty("reviewId")){
				var reviewId = data.reviewId;
        alert("add successfully");
        fetch("/api/movies/info?id=" + movieId)
            .then(res=>res.json())
            .then(res=>{
              fetchedData = res;
              this.props.data.cookies.reviews = fetchedData.reviews;
							this.close();
            });

      } else {
        var error = data.message;
        alert(error);
      }
    })

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
				<Modal.Header>
					Add a Review
				</Modal.Header>
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
		this.state = {
			rating: 0,
			cookies: '',
			reviews:[],
		}

		fetch("/api/movies/info?id=" + props.match.params.id)
		.then((res) => {
			console.log(res);
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
				reviews: data.reviews,
			});
		});
	}
	handleChange(e) {
		this.setState({ rating: e.target.value })
	}

	componentWillMount(){
		this.setState( {cookies: cookie.load('user')} );
		console.log(this.state.cookies);
	}

	/**
	* Renders a movie page with all the information and actions for given movie.
	*/
	render() {

		console.log(this.state.cookies);
		const { rating } = this.state.rating;
		const listItem = this.state.reviews.map((review) =>

		<ul>
		<li>
		<div>
		{review.text}
		</div>
		</li>
		</ul>
	);

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
					<Recomendation data = {this.state}/>
					<MovieReviews id={this.state.movieID}/>
				</div>

				<div>
					Rating: {rating}
				</div>

			<br />

			<div>
				<Rating icon='star' size='massive' defaultRating={3} maxRating={5} />
				<AddReview data={this.state}/>
			</div>

			<div>
				{listItem}
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
