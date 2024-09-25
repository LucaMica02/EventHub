import React, { useEffect, useState } from "react";
import axios from "axios";
import { Button } from "react-bootstrap";
import { User } from "../Types";
import "../Styles/PagesStyle/CreateNewEvent.css"; // Import del file CSS

interface EventFormState {
  title: string;
  description: string;
  maxpeople: number;
  startbooking: string;
  endbooking: string;
  startevent: string;
  endevent: string;
  price: number;
  isOnline: boolean;
  url?: string;
  street?: string;
  zipcode?: string;
  city?: string;
  nation?: string;
}

const EventForm: React.FC = () => {
  const formatDateTime = (dateString: string) => {
    const localDate = new Date(dateString);
    localDate.setMinutes(
      localDate.getMinutes() - localDate.getTimezoneOffset()
    );
    return localDate.toISOString().slice(0, 16); // Formato corretto per datetime-local
  };

  const [feedbackMessage, setFeedbackMessage] = useState<string>(""); // Stato per il feedback
  const [showMessage, setShowMessage] = useState<boolean>(false); // Stato per la visibilità del messaggio
  const [isOnline, setIsOnline] = useState<boolean>(false);
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [user, setUser] = useState<User | null>(null);
  const [image, setImage] = useState<File | null>(null); // Stato per l'immagine
  const [formData, setFormData] = useState<EventFormState>({
    title: "",
    description: "",
    maxpeople: 1,
    startbooking: "",
    endbooking: "",
    startevent: "",
    endevent: "",
    price: 0,
    isOnline: isOnline,
    url: "",
    street: "",
    zipcode: "",
    city: "",
    nation: "",
  });

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

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]; // Prendi il file selezionato
    if (file) {
      setImage(file);
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/event/create",
        {
          creator: user?.id,
          title: formData.title,
          description: formData.description,
          maxpeople: formData.maxpeople,
          startbooking: formatDateTime(
            new Date(formData.startbooking).toISOString()
          ),
          endbooking: formatDateTime(
            new Date(formData.endbooking).toISOString()
          ),
          startevent: formatDateTime(
            new Date(formData.startevent).toISOString()
          ),
          endevent: formatDateTime(new Date(formData.endevent).toISOString()),
          price: formData.price,
          isOnline: isOnline,
          url: formData.url,
          street: formData.street,
          zipcode: formData.zipcode,
          city: formData.city,
          nation: formData.nation,
        }
      );
      if (response.status == 201) {
        setFeedbackMessage("Event successfully created");
        setFormData({
          title: "",
          description: "",
          maxpeople: 1,
          startbooking: "",
          endbooking: "",
          startevent: "",
          endevent: "",
          price: 0,
          isOnline: isOnline,
          url: "",
          street: "",
          zipcode: "",
          city: "",
          nation: "",
        });
        if (image != null) {
          const data = new FormData();
          data.append("file", image);
          const response1 = await axios.post(
            `http://localhost:8080/api/images/event/${response.data.id}`,
            data,
            {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            }
          );
        }
      } else {
        setFeedbackMessage("Error creating the event");
      }
      setShowMessage(true);
      setTimeout(() => setShowMessage(false), 3000);
    } catch (error) {
      setFeedbackMessage("Error creating the event");
      setShowMessage(true);
      setTimeout(() => setShowMessage(false), 3000);
    }
  };

  return (
    <>
      {" "}
      {isLoggedIn && user?.isCreator ? (
        <form onSubmit={handleSubmit} className="event-form">
          {showMessage && (
            <div className="feedback-message">{feedbackMessage}</div>
          )}
          <div className="form-field">
            <label htmlFor="title">Title:</label>
            <input
              type="text"
              id="title"
              name="title"
              value={formData.title}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-field">
            <label htmlFor="description">Description:</label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-field">
            <label htmlFor="max-people">Max People:</label>
            <input
              type="number"
              id="maxpeople"
              name="maxpeople"
              value={formData.maxpeople}
              onChange={handleChange}
              required
              min="1"
            />
          </div>
          <div className="form-field">
            <label htmlFor="date">Start Booking Date</label>
            <input
              type="datetime-local"
              id="start-booking-date"
              name="startbooking"
              value={
                formData.startbooking
                  ? formatDateTime(formData.startbooking)
                  : ""
              }
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-field">
            <label htmlFor="date">End Booking Date</label>
            <input
              type="datetime-local"
              id="end-booking-date"
              name="endbooking"
              value={
                formData.endbooking ? formatDateTime(formData.endbooking) : ""
              }
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-field">
            <label htmlFor="date">Start Event Date</label>
            <input
              type="datetime-local"
              id="start-event-date"
              name="startevent"
              value={
                formData.startevent ? formatDateTime(formData.startevent) : ""
              }
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-field">
            <label htmlFor="date">End Event Date</label>
            <input
              type="datetime-local"
              id="end-event-date"
              name="endevent"
              value={formData.endevent ? formatDateTime(formData.endevent) : ""}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-field">
            <label htmlFor="price">Price:</label>
            <input
              type="number"
              id="price"
              name="price"
              value={formData.price}
              onChange={handleChange}
              required
              min="0"
            />
          </div>
          {isOnline && (
            <div className="form-field">
              <label htmlFor="title">Url: </label>
              <input
                type="text"
                id="url"
                name="url"
                value={formData.url}
                onChange={handleChange}
                required
              />
            </div>
          )}
          {!isOnline && (
            <div className="form-field">
              <label htmlFor="title">Street: </label>
              <input
                type="text"
                id="street"
                name="street"
                value={formData.street}
                onChange={handleChange}
                required
              />
            </div>
          )}
          {!isOnline && (
            <div className="form-field">
              <label htmlFor="title">Zipcode: </label>
              <input
                type="text"
                id="zipcode"
                name="zipcode"
                value={formData.zipcode}
                onChange={handleChange}
                required
              />
            </div>
          )}
          {!isOnline && (
            <div className="form-field">
              <label htmlFor="title">City: </label>
              <input
                type="text"
                id="city"
                name="city"
                value={formData.city}
                onChange={handleChange}
                required
              />
            </div>
          )}
          {!isOnline && (
            <div className="form-field">
              <label htmlFor="title">Nation: </label>
              <input
                type="text"
                id="nation"
                name="nation"
                value={formData.nation}
                onChange={handleChange}
                required
              />
            </div>
          )}
          <div className="form-field">
            <label htmlFor="image">Upload Image:</label>
            <input
              type="file"
              id="image"
              name="image"
              accept="image/*"
              onChange={handleImageChange} // Gestione dell'input immagine
            />
          </div>
          <Button
            variant="secondary"
            onClick={() => setIsOnline(!isOnline)} // Toggle creator mode
            className="mt-3"
          >
            {isOnline ? "Switch to in person event" : "Switch to online event"}
          </Button>

          <button type="submit" className="submit-btn">
            Create New Event
          </button>
        </form>
      ) : (
        <div className="not-logged-in-message">
          <h1>Nice Try!</h1>
          <h2>You're not a Creator</h2>
        </div>
      )}
    </>
  );
};

export default EventForm;
