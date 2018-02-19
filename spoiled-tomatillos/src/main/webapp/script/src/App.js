import React, { Component } from 'react';
import './App.css';
import { Route } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Login from './components/Login';
import Signup from './components/Signup';
import Homepage from './components/Homepage';


class App extends Component {
  constructor() {
    super();
    this.name = "Spoiled Tomatillos";
  }
  render() {
    return (

      <title>
      Spoiled Tomatillos
      </title>



    );
  }
}

export default App;
