import { HashRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import './i18n';
import { AuthProvider } from './services/authContext';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
