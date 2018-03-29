import ReactDOM from 'react-dom';
import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import App from './App.js';
import registerServiceWorker from './registerServiceWorker.js';
import Login from './components/Login.js';
import Signup from './components/Signup.js';
import Homepage from './components/Homepage.js';
import UserHome from './components/Home.js';
import Movie from './components/Movie.js';

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
    </div>
    </BrowserRouter>
    , document.getElementById('react'));


    registerServiceWorker();
