import React, { Component } from 'react';
import './Home.css';
import Logo from './../images/logo.svg'
import { Link } from 'react-router-dom';

///////////////// Navigation /////////////////////
class Navigation extends Component {
  render() {
    return (
      <div id="navigation" className="Navigation">
      <nav>
      <ul>
      <li>Saved Movies</li>
      <li>Reviews</li>
      <li>Groups</li>
      <li>Help</li>
      <li>Profile</li>
      </ul>
      </nav>
      </div>
    );
  }
}

///////////////// User Profle /////////////////////
class UserProfile extends Component {
  render() {
    return (
      <div className="UserProfile">
      <div className="User">
      <div className="name">John Doe</div>
      <div className="image"><img src="http://freapp.com/apps/android/com.profile.visitors/" alt="profile" /></div>
      </div>
      </div>
    );
  }
}

class FeaturesButton extends Component {
  render() {
    return (
      <button className="Button" data-primary={this.props.primary}>{this.props.text}</button>
    );
  }
}
///////////////// Features /////////////////////
class Features extends Component {

  _handleClick() {
    return (
      console.log("CLICKED!!!!!!!"),
      <Link to="/Movie">TESTING</Link>
    );
  }

  render() {
    return (
      <div id="features" className="Features" style={{backgroundImage: 'url(https://therealsasha.wordpress.com/2015/03/01/interstellar-review/)'}}>
      <div className="content">
      <img className="logo" src="http://entjunkie.blogspot.com/2014/11/interstellar-2014-film-review.html" alt="movie-bg" />
      <h2>Watch interstellar now</h2>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Doloremque id quam sapiente unde voluptatum alias vero debitis, magnam quis quod.</p>
      <div className="button-wrapper">

      <FeaturesButton onClick={this._handleClick} primary={true} text="Watch now">
      </FeaturesButton>

      <FeaturesButton primary={false} text="+ My list" />
      </div>
      </div>
      <div className="overlay"></div>
      </div>
    );
  }
}


class TitleList extends Component {
  render() {
    return(
      <div className="Title">
      <h2>{this.props.title}</h2>
      </div>
    );
  }
}

///////////////// Home /////////////////////
class Home extends Component {
  render() {
    return (

      //      <div className="Home">

      //        {/* Route Configuration */}
      /*      <Route path = "/Login" exact component = {Login}/>
      <Route path = "/Signup" exact component = {Signup}/>

      <nav id="topNav" class="navbar navbar-fixed-top">
      <div className="container-fluid">
      <div className="navbar-header">
      <a className="navbar-brand page-scroll" href=""><i class="glyphicon glyphicon-globe"></i>Spoiled Tomatillos</a>
      </div>
      <ul class="nav navbar-nav navbar-right">
      <li><Link to="/Signup"><a><span class="glyphicon glyphicon-user"></span> Sign Up</a></Link></li>
      <li>  <Link to="/Login"><a><span class="glyphicon glyphicon-log-in"></span> Login</a></Link></li>
      </ul> </div></nav>
      <header className="App-header">
      <h1 className="App-title">Welcome to {this.name}</h1>
      <p className="App-intro">
      Find Movies, TV shows, Celebrities and more ...
      </p>
      </header>
      <div id='search-box'>
      <form action='/search' id='search-form' method='get' target='_top'>
      <input id='search-text' name='q' placeholder='Search' type='text'/>
      <button id='search-button' type='submit'>
      <span>Search</span>
      </button>
      </form>
      </div>

      </div>
      */
      <div>
      <header className="Header">
      <img src={Logo} alt="logo"/>
      <Navigation />
      <div id="search" className="Search">
      <form action='/search' id='search-form' method='get' target='_top'>
      <input id='search-text' name='q' placeholder='Search' type='text'/>
      <button id='search-button' type='submit'>
      <span>Search</span>
      </button>
      </form>
      </div>
      <UserProfile />
      </header>
      <Features />

      <div className="TitleList">
      <div className="Title">
      <h1>Top Picks </h1>
      </div>
      <div className="Title">
      <h1>Recommended For Me</h1>
      </div>
      </div>
      </div>
    );
  }
}
export default Home;
