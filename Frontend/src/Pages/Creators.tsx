import { useEffect, useState } from "react";
import { CreatorType } from "../Types";
import axios from "axios";
import CreatorCard from "../Components/CreatorCard";

function Creators() {
  const [creators, setCreators] = useState<CreatorType[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<boolean>(false);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/creator")
      .then((response) => {
        const sortedCreators = response.data.sort(
          (a: CreatorType, b: CreatorType) => {
            return b.rating - a.rating; // sort by desc order
          }
        );
        setCreators(sortedCreators);
        setLoading(false);
      })
      .catch((err) => {
        setError(true);
        setLoading(false);
      });
  }, []);

  // handle loading
  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  // handle error
  if (error) {
    return <div className="error">Error finding the creators</div>;
  }

  return (
    <div className="creators-container">
      {creators.length > 0 ? (
        creators.map((creator) => (
          <CreatorCard key={creator.id} creator={creator} />
        ))
      ) : (
        <p> No Creators Available </p>
      )}
    </div>
  );
}

export default Creators;
