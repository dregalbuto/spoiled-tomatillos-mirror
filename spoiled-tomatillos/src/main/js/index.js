import ReactDOM from 'react-dom';
import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { Rating } from 'semantic-ui-react';
import App from './App.js';
import registerServiceWorker from './registerServiceWorker.js';
import Login from './components/Login.js';
import Signup from './components/Signup.js';
import Homepage from './components/Homepage.js';
import UserHome from './components/UserHome.js';
import Movie from './components/Movie.js';
import Profile from './components/UserProfile.js';
import Groups from './components/Groups.js';
import Reviews from './components/Reviews.js';

ReactDOM.render(
  <BrowserRouter>
    <div>
      {/* Route Configuration */}
      <Route path = "/" component = {App}/>
      <Route path = "/Home" component = {Homepage}/>
      <Route path = "/Login" component = {Login}/>
      <Route path = "/Signup" component = {Signup}/>
      <Route path = "/Movie/:id" component = {Movie}/>
      <Route path = "/user/:id" component = {UserHome}/>
      <Route path = "/Profile" component = {Profile}/>
      <Route path = "/Groups" component={Groups}/>
      <Route path = "/Reviews" component={Reviews}/>
      </div>
    </BrowserRouter>
    , document.getElementById('react'));


    registerServiceWorker();
