import React from "react";
import { CreatorType } from "../Types";
import "../Styles/ComponentsStyle/CreatorCard.css";

interface CreatorCardProps {
  creator: CreatorType;
}

const CreatorCard: React.FC<CreatorCardProps> = ({ creator }) => {
  const handleClick = () => {
    window.location.href = `http://localhost:5173/creators/${creator.id}`;
  };

  return (
    <div className="creator-card" onClick={handleClick}>
      <img
        src={`http://localhost:8080/api/images/user/${creator.id}`}
        alt={"No Image"}
        className="profile-img"
      />
      <div className="card-body">
        <h6 className="card-title">@{creator.username}</h6>
        <p className="card-text">
          <strong>Email:</strong> {creator.email}
        </p>
        <p className="card-text">
          <strong>Rating:</strong>{" "}
          {creator.rating === 0 ? "N.A" : creator.rating + "/5" + "★"}
        </p>
        <p className="card-text">
          <strong>Events:</strong> {creator.eventsCount}
        </p>
        <p className="card-text">
          <strong>Feedbacks:</strong> {creator.feedbacksCount}
        </p>
      </div>
    </div>
  );
};

export default CreatorCard;
