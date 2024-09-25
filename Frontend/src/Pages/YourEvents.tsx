import React, { useState, useEffect } from "react";
import axios from "axios";
import { Alert } from "react-bootstrap";
import { Event, User } from "../Types";
import "../Styles/PagesStyle/YourEvents.css";
import { Link } from "react-router-dom";
import { useAuth } from "../UserAuth";

const formatDate = (isoString: string) => {
  const date = new Date(isoString);
  return (
    date.toLocaleDateString("it-IT") + " " + date.toLocaleTimeString("it-IT")
  );
};

const YourEvents: React.FC = () => {
  const { logout } = useAuth();
  const [user, setUser] = useState<User | null>(null);
  const [userEvents, setUserEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const userData = localStorage.getItem("user");
        if (userData) {
          setUser(JSON.parse(userData));
        } else {
          setError("You are not logged in");
        }
      } catch (err) {
        setError("Failed to retrieve user data");
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

  useEffect(() => {
    if (user) {
      const fetchUserEvents = async () => {
        try {
          console.log("USER: " + user.id);
          const response = await axios.get(
            `http://localhost:8080/api/appUser/${user.id}/events`,
            { withCredentials: true }
          );
          if (response.status == 202) {
            setUserEvents(response.data);
          } else if (response.status == 200) {
            setError("You must log in again");
            logout();
          }
        } catch (err) {
          setError("Failed to fetch user events");
        } finally {
          setLoading(false);
        }
      };

      fetchUserEvents();
    }
  }, [user, logout]);

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="your-events-container">
      {user ? (
        <>
          <h1>{user.name}'s Events</h1>
          {userEvents.length > 0 ? (
            <div className="event-grid">
              {userEvents.map((event) => (
                <Link
                  key={event.id}
                  to={`/event/${event.id}`}
                  style={{ textDecoration: "none" }}
                >
                  <div className="event-card">
                    <img
                      src={`http://localhost:8080/api/images/event/${event.id}`}
                      alt={event.title}
                      className="event-image"
                    />
                    <div className="event-info">
                      <h2>{event.title}</h2>
                      <p>
                        <strong>Start Date:</strong>{" "}
                        {formatDate(event.startEvent)}
                      </p>
                      <p>
                        <strong>End Date:</strong> {formatDate(event.endEvent)}
                      </p>
                      <p>
                        <strong>Location: </strong>
                        {!event.online
                          ? event.city + ", " + event.nation
                          : "Online"}
                      </p>
                    </div>
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <p>No events found for {user.name}.</p>
          )}
        </>
      ) : (
        <div className="text-center mt-4">
          <Alert variant="warning">You are not logged in</Alert>
        </div>
      )}
    </div>
  );
};

export default YourEvents;
