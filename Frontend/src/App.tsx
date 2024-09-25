import "./Styles/App.css";
import Navbar from "./Components/Navbar";
import Footer from "./Components/Footer";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Pages/Home"; // Create this new Home component
import EventDetails from "./Pages/Event"; // Create this new EventDetails component
import Login from "./Pages/Login";
import Profile from "./Pages/Profile";
import YourEvents from "./Pages/YourEvents";
import EventForm from "./Pages/CreateNewEvent";
import Creators from "./Pages/Creators";
import Creator from "./Pages/Creator";
import { AuthProvider } from "./UserAuth";

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="app">
          <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/event/:id" element={<EventDetails />} />
            <Route path="/login" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/your-events" element={<YourEvents />} />
            <Route path="/create-new-event" element={<EventForm />} />
            <Route path="/creators" element={<Creators />} />
            <Route path="/creators/:id" element={<Creator />} />
          </Routes>
          <Footer />
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
