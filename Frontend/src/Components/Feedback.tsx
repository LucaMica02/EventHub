import axios from "axios";
import React, { useEffect, useState } from "react";

interface FeedbackProps {
  userId: number;
  eventId: number;
}

const Feedback: React.FC<FeedbackProps> = ({ userId, eventId }) => {
  const [rating, setRating] = useState<number>(0);
  const [comment, setComment] = useState<string>("");
  const [submitted, setSubmitted] = useState<boolean>(false);
  const [feedbackMessage, setFeedbackMessage] = useState<string | null>(null);

  const formatDateTime = (dateString: string) => {
    const localDate = new Date(dateString);
    localDate.setMinutes(
      localDate.getMinutes() - localDate.getTimezoneOffset()
    );
    return localDate.toISOString().slice(0, 16); // Formato corretto per datetime-local
  };

  const handleRatingChange = (newRating: number) => {
    setRating(newRating);
  };

  const handleSubmit = async () => {
    if (rating > 0 && comment.trim()) {
      // POST request
      try {
        const response = await axios.post(
          "http://localhost:8080/api/feedback",
          {
            event: eventId,
            stars: rating,
            description: comment,
            user: userId,
            dateTime: formatDateTime(new Date().toISOString()),
          },
          { withCredentials: true }
        );
        // response status check
        if (response.status == 201) {
          setSubmitted(true);
          setFeedbackMessage("Feedback successfully submitted");
          setTimeout(() => setSubmitted(false), 3000);
        } else {
          setSubmitted(true);
          setFeedbackMessage(
            "Problems occurs sending feedback, please retry :("
          );
          setTimeout(() => setSubmitted(false), 3000);
        }
        // error check
      } catch (error) {
        setSubmitted(false);
        setFeedbackMessage("Problems occurs sending feedback, please retry :(");
      }
      // empty fields
    } else {
      alert("Please provide a rating and comment");
    }
  };

  if (submitted) {
    return <div>Thanks for your feedback!</div>;
  }

  return (
    <div className="feedback-component">
      <h3>Feedback</h3>

      {/* Selecting the number of stars */}
      <div className="stars">
        {Array.from({ length: 5 }, (_, index) => (
          <Star
            key={index + 1}
            selected={index < rating}
            onClick={() => handleRatingChange(index + 1)}
          />
        ))}
      </div>

      {/* Text area for the comment */}
      <div>
        <textarea
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          placeholder="Enter your description"
          rows={4}
          cols={50}
        />
      </div>

      {/* Button to send feedback */}
      <div>
        <button onClick={handleSubmit}>Submit</button>
      </div>

      {/* Show feedback messagge */}
      {feedbackMessage && <div>{feedbackMessage}</div>}
    </div>
  );
};

interface StarProps {
  selected: boolean;
  onClick: () => void;
}

const Star: React.FC<StarProps> = ({ selected, onClick }) => {
  return (
    <span
      onClick={onClick}
      style={{ cursor: "pointer", color: selected ? "gold" : "gray" }}
    >
      ★
    </span>
  );
};

export default Feedback;
