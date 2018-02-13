import React, { Component } from 'react';
import logo from './logo.svg';
import Home from './components/Home'

class App extends Component {
  constructor() {
    super();
    this.name = "Spoiled Tomatillos";
  }
  render() {
    return (
      <div className="App">
        <Home />
      </div>
    )
  }
}
export default App;
