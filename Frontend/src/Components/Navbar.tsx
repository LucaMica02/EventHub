import React, { useState, useEffect } from "react";
import { User } from "../Types";
import { Link } from "react-router-dom"; // Import Link from react-router-dom
import "../Styles/ComponentsStyle/Navbar.css";
import { useAuth } from "../UserAuth";

const Navbar: React.FC = () => {
  const { user } = useAuth();

  return (
    <nav className="navbar">
      <div className="logo">
        <Link to="/" className="nav-link">
          EventHub
        </Link>
      </div>
      <Link to="/creators" className="nav-link">
        Creators
      </Link>
      <div className="profile-icon">
        <Link to="/profile" className="nav-link">
          Profile
        </Link>
        {user && user?.isCreator && (
          <Link to="/create-new-event" className="nav-link">
            Create New Event
          </Link>
        )}
      </div>
      <div className="nav-links">
        <Link to="/your-events" className="nav-link">
          Your Events
        </Link>
        <Link to="/login" className="nav-link">
          Login
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
