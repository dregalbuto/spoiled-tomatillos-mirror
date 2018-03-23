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
  Step, Table, Statistic
} from 'semantic-ui-react'

class UserProfile extends Component {
	constructor(props) {
		super(props);
		this.state = {};
	}
	
	render() {
		return(
			<div>
			
			
			<Container text>
		    <Header
		      as='h2'
		      content='username'
		      inverted
		      style={{
		        fontSize: '1.7em',
		        fontWeight: 'normal',
		        marginTop: '1.5em',
		      }}
		    >
		    <Grid>
		    <Grid.Column width={4}>
		    <Image circular src='https://react.semantic-ui.com/assets/images/avatar/large/patrick.png' />
		    {' '}Patrick
		    </Grid.Column>
		    <Grid.Column width={9}>
		    
		    <Segment inverted>
		    <Statistic.Group inverted>
		      <Statistic color='blue' size='small'>
		        <Statistic.Value>39</Statistic.Value>
		        <Statistic.Label>Movies Watched</Statistic.Label>
		      </Statistic>
		      <Statistic color='blue' size='small'>
		        <Statistic.Value>562</Statistic.Value>
		        <Statistic.Label>Friends</Statistic.Label>
		      </Statistic>
		      <Statistic color='blue' size='small'>
		        <Statistic.Value>7</Statistic.Value>
		        <Statistic.Label>Groups</Statistic.Label>
		      </Statistic>
		    </Statistic.Group>
		    </Segment>
		    
		    
		    </Grid.Column>
		    <Grid.Column width={3}>
		     

		    <Header as='h3' icon inverted>
		    <Icon name='settings' />
		    Account Settings
		    
		  </Header>
		  
		    </Grid.Column>
		  </Grid>
		  
		    
		</Header>
		    
		   </Container>
		   
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
		    
		    <Segment style={{ padding: '8em 0em' }} vertical>
		    <Header as='h3' inverted style={{ fontSize: '2em' }}>My Reviews</Header>
		    <Container>
		      <Segment.Group>
		        <Segment as='a'>Lady Bird: a touching throwback to the adolescent years
		        	</Segment>
		        <Segment>Content</Segment>
		        <Segment>Content</Segment>
		        <Segment>Content</Segment>
		      </Segment.Group>
		    </Container>
		    <Button floated='right' basic inverted color='grey'>More</Button>
		    </Segment>
		    
		    
		    <Segment style={{ padding: '8em 0em' }} vertical>
		 
		    <Header as='h3' inverted style={{ fontSize: '2em' }}>Groups</Header>
		    <Container>
		      <Item.Group divided>
		        <Item>
		          <Item.Image src='https://ia.media-imdb.com/images/M/MV5BNDI5NzU2OTM1MV5BMl5BanBnXkFtZTgwNzU3NzM3MzI@._V1_SY1000_CR0,0,675,1000_AL_.jpg' />
		          <Item.Content>
		            <Item.Header as='a' style={{color:'white'}}>Group A</Item.Header>
		            <Item.Meta style={{color:'white'}}>
		              <span>Drama</span>
		              <span>Mystery</span>
		            </Item.Meta>
		            <Item.Description style={{color:'white'}}>
		            A blind woman's relationship with her husband changes when she regains her sight and discovers disturbing details about themselves.
		            </Item.Description>
		            <Item.Extra style={{color:'white'}}>
		              <Image
		                avatar
		                circular
		                src='https://www.shareicon.net/data/128x128/2016/06/26/786570_people_512x512.png'
		              />
		              Emily B.
		            </Item.Extra>
		          </Item.Content>
		        </Item>

		        <Item>
		          <Item.Image src='https://ia.media-imdb.com/images/M/MV5BNTA1OTAzNTExOF5BMl5BanBnXkFtZTgwNjQ1ODY3MzI@._V1_SY1000_CR0,0,649,1000_AL_.jpg' />
		          <Item.Content>
		            <Item.Header as='a' style={{color:'white'}}>Group B</Item.Header>
		            <Item.Meta style={{color:'white'}}>
		              <span>Date</span>
		              <span>Category</span>
		            </Item.Meta>
		            <Item.Description style={{color:'white'}}>
		              A description which may flow for several lines and give context to the content.
		            </Item.Description>
		            <Item.Extra>
		              <Button floated='right' primary>
		                Primary
		                <Icon name='right chevron' />
		              </Button>
		              <Label>Limited</Label>
		            </Item.Extra>
		          </Item.Content>
		        </Item>
		        <Item>
		          <Item.Image src='https://ia.media-imdb.com/images/M/MV5BMTYyOTUwNjAxM15BMl5BanBnXkFtZTgwODcyMzE0NDM@._V1_.jpg' />
		          <Item.Content>
		            <Item.Header as='a' style={{color:'white'}}>Group C</Item.Header>
		            <Item.Meta style={{color:'white'}}>
		              <span>Animation</span>
		              <span>Adventure</span>
		              <span>Comedy</span>
		            </Item.Meta>
		            <Item.Description style={{color:'white'}}>
		              A description which may flow for several lines and give context to the content.
		            </Item.Description>
		            <Item.Extra>
		              <Button primary floated='right'>
		                Primary
		                <Icon name='right chevron' />
		              </Button>
		            </Item.Extra>
		          </Item.Content>
		        </Item>
		      </Item.Group>
		    </Container>

		    <Button floated='right' basic inverted color='grey'>More</Button>
		    </Segment>
		    
		    <Segment style={{ padding: '8em 0em' }} vertical>
		    <div>
		    <Header as='h3' inverted style={{fontSize:'2em'}} icon>
		      <Icon name='users' circular />
		      <Header.Content>
		        Friends
		      </Header.Content>
		        
		    </Header>
		    
		    <List relaxed>
		    <List.Item>
		      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/daniel.jpg' />
		      <List.Content>
		        <List.Header as='a'>Daniel Louise</List.Header>
		        <List.Description style={{color:'white'}}>Last seen watching <a><b>Arrested Development</b></a> just now.</List.Description>
		      </List.Content>
		    </List.Item>
		    <List.Item>
		      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/stevie.jpg' />
		      <List.Content>
		        <List.Header as='a'>Stevie Feliciano</List.Header>
		        <List.Description style={{color:'white'}}>Last seen watching <a><b>Bob's Burgers</b></a> 10 hours ago.</List.Description>
		      </List.Content>
		    </List.Item>
		    <List.Item>
		      <Image avatar src='https://react.semantic-ui.com/assets/images/avatar/small/elliot.jpg' />
		      <List.Content>
		        <List.Header as='a'>Elliot Fu</List.Header>
		        <List.Description style={{color:'white'}}>Last seen watching <a><b>The Godfather Part 2</b></a> yesterday.</List.Description>
		      </List.Content>
		    </List.Item>
		  </List>
		   
		  </div>
		  <Button floated='right' basic inverted color='grey'>More</Button>
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