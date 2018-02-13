import * as React from 'react';
import * as ReactDOM from 'react-dom';
// import App from './App';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import Hello from './components/Hello';

// TODO Change display hello back to app.tsx
/*ReactDOM.render(
  <App />,
  document.getElementById('root') as HTMLElement
);*/
ReactDOM.render(
  <Hello name="TypeScript" enthusiasmLevel={10} />,
  document.getElementById('root') as HTMLElement
);

registerServiceWorker();
