import ReactDOM from 'react-dom';
import { Redirect } from 'react-router'
import PropTypes from 'prop-types'
import React, { Component } from 'react'

import {
  Button,
  Container,
  Divider,
  Grid,
  Header,
  Icon,
  Image,
  List,
  Menu,
  Responsive,
  Segment,
  Sidebar,
  Visibility,
  Item,
  Label,
  Step, Table
} from 'semantic-ui-react'
import { Link } from 'react-router-dom';

class UserProfile extends Component {
	constructor(props) {
		super(props);
		this.state = {};
	}

	render() {
		return(
			<div>
		    <Segment style={{ padding: '8em 0em' }} vertical>
		    <Header as='h3' inverted style={{ fontSize: '2em' }}>Favourite Movies</Header>
		    <Grid container columns={3} doubling stackable>
	    			<Grid.Column>
	    		        <Image
	    		          bordered
	    		          rounded
	    		          size='large'
	    		          	src='https://ia.media-imdb.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SY1000_SX677_AL_.jpg'/>
	    		    </Grid.Column>
	    		          <Grid.Column>
	    		          <Image
	    		          	bordered
	    		          	rounded
	    		          	size='large'
	    		          		src='https://ia.media-imdb.com/images/M/MV5BMjI3Nzg0MTM5NF5BMl5BanBnXkFtZTgwOTE2MTgwNTM@._V1_.jpg'/>
	    		          </Grid.Column>
	    		          <Grid.Column >
	    		          <Image
	    		          	bordered
	    		          	rounded
	    		          	size='large'
	    		          		src='https://ia.media-imdb.com/images/M/MV5BMTg1MTY2MjYzNV5BMl5BanBnXkFtZTgwMTc4NTMwNDI@._V1_SY1000_CR0,0,674,1000_AL_.jpg'/>
	    		          </Grid.Column>
	    		   </Grid>
	    		<Button floated='right' basic inverted color='grey'>More</Button>
		    </Segment>

		    <Segment inverted>
		    <Header as='h3' inverted style={{ fontSize: '2em' }}>My Reviews</Header>
		    <List divided inverted relaxed>
		      <List.Item>
		        <List.Content>
		          <List.Header>Lady Bird</List.Header>
		          a touching throwback to the adolescent years
		        </List.Content>
		      </List.Item>
		      <List.Item>
		        <List.Content>
		          <List.Header>
		          Pacific Rim: Uprising</List.Header>
		          A well done sequel
		        </List.Content>
		      </List.Item>
		      <List.Item>
		        <List.Content>
		          <List.Header>Unsane</List.Header>
		          The best film of Berlinale was shot on an iPhone!
		        </List.Content>
		      </List.Item>
		    </List>
		    <Link to="/Reviews"><Button floated='right' basic inverted color='grey'>More</Button></Link>
		  </Segment>

		   
			</div>
		)
	}
}

const style = {
		h3: {
		    marginTop: '2em',
		    	padding: '2em 0em',
		}

}


export default UserProfile;
