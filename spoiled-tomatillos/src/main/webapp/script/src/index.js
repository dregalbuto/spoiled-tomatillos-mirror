import ReactDOM from 'react-dom';
import React, { Component } from 'react';
import { BrowserRouter,Route, Router,browserHistory } from 'react-router-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import Login from './components/Login';
import Signup from './components/Signup';
import Homepage from './components/Homepage';
import UserHome from './components/Home';
import Movie from './components/Movie';

ReactDOM.render(
  <BrowserRouter>
    <div>
      {/* Route Configuration */}
      <Route path = "/" component = {App}/>
      <Route path = "/Home" component = {Homepage}/>
      <Route path = "/Login" component = {Login}/>
      <Route path = "/Signup" component = {Signup}/>
      <Route path = "/Movie" component = {Movie}/>
      <Route path = "/User" component = {UserHome}/>
    </div>
    </BrowserRouter>
    , document.getElementById('root'));


    registerServiceWorker();
