import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import axios from "axios";
import { User } from "../Types";
import "../Styles/PagesStyle/Event.css";
import Feedback from "../Components/Feedback";
import { FeedbackType } from "../Types";

const formatDate = (isoString: string) => {
  const date = new Date(isoString);
  return (
    date.toLocaleDateString("it-IT") + " " + date.toLocaleTimeString("it-IT")
  );
};

function EventDetails() {
  const { id } = useParams<{ id: string }>(); // Specifica il tipo per useParams
  const [event, setEvent] = useState<any | null>(null); // Stato per i dati dell'evento
  const [loading, setLoading] = useState<boolean>(true); // Stato di caricamento
  const [error, setError] = useState<string | null>(null); // Stato per gli errori
  const [user, setUser] = useState<User | null>(null);
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [feedbackMessage, setFeedbackMessage] = useState<string>(""); // Stato per il feedback
  const [showMessage, setShowMessage] = useState<boolean>(false);
  const [isJoined, setIsJoined] = useState<boolean>(false);
  const [feedbackGiven, setFeedbackGiven] = useState<boolean>(false);
  const [feedback, setFeedback] = useState<FeedbackType | null>(null);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }
  }, []);

  useEffect(() => {
    const fetchEvent = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/event/${id}`,
          { withCredentials: true }
        );
        setEvent(response.data);
      } catch (err) {
        setError("Evento non trovato");
      } finally {
        setLoading(false);
      }
    };
    fetchEvent();
  }, [id]);

  useEffect(() => {
    if (event && user) {
      // check if the user has joined the event
      const checkUserEvent = async () => {
        try {
          const response = await axios.get(
            `http://localhost:8080/api/userEvent/${event.id}/${user.id}`,
            { withCredentials: true }
          );
          setIsJoined(response.data);
        } catch (error) {
          console.log("Error checking userEvent", error);
        }
      };
      checkUserEvent();

      // check if the user has already gived the feedback
      const checkFeedback = async () => {
        try {
          const response = await axios.get(
            `http://localhost:8080/api/feedback/exists/${event.id}/${user.id}`,
            { withCredentials: true }
          );
          setFeedbackGiven(response.data);
        } catch (error) {
          console.log("Error checking feedback", error);
        }
      };
      checkFeedback();

      // if the user has already gived the feedback set it
      const getFeedback = async () => {
        if (feedbackGiven) {
          try {
            const response = await axios.get(
              `http://localhost:8080/api/feedback/get/${event.id}/${user.id}`,
              { withCredentials: true }
            );
            setFeedback(response.data);
          } catch (error) {
            console.log("Error checking feedback", error);
          }
        }
      };
      getFeedback();
    }
  }, [event, user]);

  const handleJoinEvent = async () => {
    if (user) {
      try {
        const response = await axios.post(
          `http://localhost:8080/api/userEvent/${event.id}/${user.id}`,
          {},
          { withCredentials: true }
        );
        if (response.status == 201) {
          setFeedbackMessage("Event Booked Successfully!");
          setShowMessage(true);
          setTimeout(() => setShowMessage(false), 3000);
          setIsJoined(true);
        } else {
          setFeedbackMessage("Some problems occurred, please retry :(");
          setShowMessage(true);
          setTimeout(() => setShowMessage(false), 3000);
        }
      } catch (error) {
        setFeedbackMessage("Some problems occoured, please retry :(");
        setShowMessage(true);
        setTimeout(() => setShowMessage(false), 3000);
      }
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  if (!event) {
    return <div>Event not found</div>;
  }

  return (
    <div className="event-details">
      <h1>{event.title}</h1>
      <h5>{event.description}</h5>
      <img
        src={`http://localhost:8080/api/images/event/${event.id}`}
        alt={event.title}
        className="event-image"
      />
      <div className="event-info">
        <p>
          <strong>Price:</strong> ${event.price}
        </p>
        <p>
          <strong>Free Spots:</strong> {event.freeSpots}
        </p>
        <p>
          <strong>Rating:</strong> {event.ranking == 0 ? "N.A" : event.ranking}
        </p>
        <p>
          <strong>Booking start date:</strong> {formatDate(event.startBooking)}
        </p>
        <p>
          <strong>Booking end date:</strong> {formatDate(event.endBooking)}
        </p>
        <p>
          <strong>Event start date:</strong> {formatDate(event.startEvent)}
        </p>
        <p>
          <strong>Event end date:</strong> {formatDate(event.endEvent)}
        </p>
        {!event.online && (
          <p>
            <strong>Location:</strong> {event.city}, {event.nation}
          </p>
        )}
        {!event.online && isJoined && (
          <p>
            <strong>Address:</strong> {event.street}, {event.zipcode}
          </p>
        )}
        {event.online && isJoined && (
          <p>
            <strong>Url:</strong> {event.url}
          </p>
        )}
        <p>
          <strong>State:</strong> {event.state}
        </p>
      </div>
      {showMessage && <div className="feedback-message">{feedbackMessage}</div>}
      <div className="event-actions">
        {event.state == "Open" &&
          isLoggedIn &&
          !user?.isCreator &&
          !isJoined && (
            <button className="book-now-button" onClick={handleJoinEvent}>
              Book Now!
            </button>
          )}
        <Link to="/" className="back-link">
          Back to the events list
        </Link>
      </div>
      <br />
      {!feedbackGiven &&
        isJoined &&
        event.state == "Finished" &&
        user !== null && <Feedback userId={user.id} eventId={event.id} />}
      {feedbackGiven && (
        <p>
          <strong>Your Rating:</strong> {feedback?.stars}
          <br />
          <strong>Your Feedback:</strong> {feedback?.description}
        </p>
      )}
    </div>
  );
}

export default EventDetails;
