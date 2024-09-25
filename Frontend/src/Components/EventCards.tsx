import React from "react";
import "../Styles/ComponentsStyle/EventCards.css"; // Aggiungi il file CSS per lo stile

interface EventCardProps {
  title: string;
  price: number;
  freeSpots: number;
  rating: number;
  imgSrc: string;
  startDate: string;
  endDate: string;
  location: string;
  online: boolean;
  state: string;
}

const EventCard: React.FC<EventCardProps> = ({
  title,
  price,
  freeSpots,
  rating,
  imgSrc,
  startDate,
  endDate,
  location,
  online,
  state,
}) => {
  return (
    <div className="event-card">
      <img src={imgSrc} alt={title} className="event-image" />
      <div className="event-details">
        <h2 className="event-title">{title}</h2>
        <p className="event-price">Price: €{price}</p>
        <p className="event-free-spots">Free Spots: {freeSpots}</p>
        <p className="event-rating">Rating: {rating == 0 ? "N.A" : rating}</p>
        <p className="event-dates">
          <strong>Start:</strong> {startDate} <br />
          <strong>End:</strong> {endDate}
        </p>
        {!online && (
          <p className="event-location">
            <strong>Location:</strong> {location}
          </p>
        )}

        <p className={`event-online ${online ? "online" : "offline"}`}>
          {online ? "Online" : "In Person"}
        </p>
        <p className="event-state">{state}</p>
      </div>
    </div>
  );
};

export default EventCard;
