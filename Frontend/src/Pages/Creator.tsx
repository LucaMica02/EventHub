import React, { useEffect, useState } from "react";
import { CreatorType, Event, FeedbackType } from "../Types";
import { useParams, Link } from "react-router-dom";
import axios from "axios";
import "../Styles/PagesStyle/Creator.css";

const Creator = () => {
  const { id } = useParams<{ id: string }>();
  const [creator, setCreator] = useState<CreatorType | null>(null);
  const [events, setEvents] = useState<Event[]>([]);
  const [feedbacks, setFeedbacks] = useState<FeedbackType[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>("");

  const formatDate = (isoString: string) => {
    const date = new Date(isoString);
    return (
      date.toLocaleDateString("it-IT") + " " + date.toLocaleTimeString("it-IT")
    );
  };

  // Get the creator by the id
  useEffect(() => {
    console.log("id" + id);
    axios
      .get(`http://localhost:8080/api/creator/${id}`)
      .then((response) => {
        setCreator(response.data);
        setLoading(false);
        console.log(response.data);
      })
      .catch((err) => {
        setError(true);
        setErrorMessage("Error getting the creator");
        setLoading(false);
      });
  }, [id]);

  // Get the creator events
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/creator/${id}/events`)
      .then((response) => {
        setEvents(response.data);
        console.log(response.data);
      })
      .catch((err) => {
        setError(true);
        setErrorMessage("Error getting the creator events");
      });
  }, [id]);

  // Get the creator feedbacks
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/feedback/get_by_creator/${id}`)
      .then((response) => {
        const sortedFeedbacks = response.data.sort(
          (a: FeedbackType, b: FeedbackType) => {
            return (
              new Date(b.dateTime).getTime() - new Date(a.dateTime).getTime()
            );
          }
        );
        setFeedbacks(sortedFeedbacks);
      })
      .catch((err) => {
        setError(true);
        setErrorMessage("Error getting the feedbacks");
      });
  }, [id]);

  // handle loading
  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  // handle error
  if (error) {
    return <div className="error">{errorMessage}</div>;
  }

  return (
    <div className="creator-page">
      {creator && (
        <div className="creator-info">
          <img
            src={`http://localhost:8080/api/images/user/${creator.id}`}
            alt={creator.username}
            className="creator-image"
          />
          <h1>@{creator.username}</h1>
          <p>
            {" "}
            <strong>Email: </strong>
            {creator.email}
          </p>
          <p>
            <strong>Rating: </strong>{" "}
            {creator.rating === 0 ? "N.A" : creator.rating + "/5" + "★"}
          </p>
          <p>
            <strong>Events Count: </strong> {creator.eventsCount}
          </p>
          <p>
            {" "}
            <strong>Feedbacks Count: </strong> {creator.feedbacksCount}
          </p>
        </div>
      )}

      <div className="creator-events">
        <h3>Events</h3>
        <div className="event-grid">
          {events.length > 0 ? (
            events.map((event) => (
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
                        ? `${event.city}, ${event.nation}`
                        : "Online"}
                    </p>
                  </div>
                </div>
              </Link>
            ))
          ) : (
            <p>No events available.</p>
          )}
        </div>
      </div>

      {/* Sezione Feedbacks del Creator */}
      <div className="creator-feedbacks">
        <h3>Feedbacks</h3>
        {feedbacks.length > 0 ? (
          feedbacks.map((feedback, index) => (
            <div key={index} className="feedback-card">
              <p>
                <strong>Date:</strong> {formatDate(feedback.dateTime)}
              </p>
              <p>
                <strong>Stars:</strong> {feedback.stars} ★
              </p>
              <p>
                <strong>Description:</strong> {feedback.description}
              </p>
            </div>
          ))
        ) : (
          <p>No feedbacks available.</p>
        )}
      </div>
    </div>
  );
};

export default Creator;
