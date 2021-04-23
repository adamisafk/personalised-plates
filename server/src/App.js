import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';

import Navigation from './components/subcomponents/Navigation';
import Home from './components/Home';
import About from './components/About'
import Profile from './components/Profile'
import Plate from './components/Plate'
import Payment from'./components/Payment'
import LoginForm from './components/subcomponents/LoginForm';
import RegisterForm from './components/subcomponents/RegisterForm';
import Logout from './components/subcomponents/Logout';

// Router DOM is used to return certain components if the URL matches

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
            <Route path="/login" exact component={(props) => <LoginForm {...props} />} />
            <Route path="/register" exact component={() => <RegisterForm />} />
            <Route path="/logout" exact component={() => <Logout />} />
            <Route path="/plate/:id" exact render={(props) => <Plate id={props} />} />
            <Route path="/payment" exact render={(props) => <Payment {...props} />} />
          </Switch>
        </Router>
      </header>
    </div>
  );
}

export default App;
