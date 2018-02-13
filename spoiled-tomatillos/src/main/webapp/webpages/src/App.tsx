import * as React from 'react';
import './App.css';
import HomeScreen from './components/HomeScreen';

class App extends React.Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Welcome to Spoiled Tomatillos</h1>
        </header>
        <p className="App-intro">
          You can check out movies here!
        </p>
      </div>
    );
  }
}

export default App;
