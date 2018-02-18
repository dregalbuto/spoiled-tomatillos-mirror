import ReactDOM from 'react-dom';
import React, { Component } from 'react';
import { BrowserRouter,Route, Router,browserHistory } from 'react-router-dom';
//import { render } from 'react-dom';
//import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import Login from './components/Login';
import Signup from './components/Signup';
import Homepage from './components/Homepage'
import UserHome from './components/Home'

ReactDOM.render(
  <BrowserRouter>
    <div>
      {/* Route Configuration */}
      <Route path = "/" component = {App}></Route>
      <Route path = "/Home" component = {Homepage}></Route>
      <Route path = "/Login" component = {Login}/>
      <Route path = "/Signup" component = {Signup}/>
      <Route path = "/User" component = {UserHome}/></div>
    </BrowserRouter>
    , document.getElementById('root'));

//       render(
//     <Router history={browserHistory}>
//         <Route component={App}>
//             <Route path="/" component={Homepage}/>
//             <Route path="/Login" component={Login}/>
//             {/* Parameter route*/}
//             <Route path="/Signup" component={Signup}/>
//         </Route>
//     </Router>,
//     document.getElementById('root')
// );


    registerServiceWorker();
