import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';

import Navigation from './components/subcomponents/Navigation';
import Footer from './components/subcomponents/Footer';
import Home from './components/Home';
import About from './components/About'
import Profile from './components/Profile'
import Plate from './components/Plate'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <Router>
          <Navigation />
          <Switch>
            <Route path="/" exact component={() => <Home />} />
            <Route path="/about" exact component={() => <About />} />
            <Route path="/profile" exact component={() => <Profile />} />
            <Route path="/plate" exact component={() => <Plate />} />
          </Switch>

          <Footer/>
        </Router>
      </header>
    </div>
  );
}

export default App;
