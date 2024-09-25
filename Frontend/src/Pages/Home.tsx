import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import EventCard from "../Components/EventCards";
import { Event } from "../Types";
import axios from "axios";
import Searchbar from "../Components/Searchbar";
import "../Styles/PagesStyle/Home.css";
import { User } from "../Types";

interface Props {
  events: Event[];
}

const formatDate = (isoString: string) => {
  const date = new Date(isoString);
  return (
    date.toLocaleDateString("it-IT") + " " + date.toLocaleTimeString("it-IT")
  );
};

function Home() {
  const [events, setEvents] = useState<Event[]>([]); // event
  const [loading, setLoading] = useState(true); // loading state
  const [error, setError] = useState(""); // error state
  const [filterOnline, setFilterOnline] = useState("all"); // 'all', 'online', 'in person'
  const [filterState, setFilterState] = useState("all"); // 'all', 'Open', 'Closed', 'Finished'
  const [searchTerm, setSearchTerm] = useState(""); // search bar state
  const [filterCityOnly, setFilterCityOnly] = useState(false); // city filter
  const [filterNationOnly, setFilterNationOnly] = useState(false); // nation filter
  const [user, setUser] = useState<User | null>(null); // store the current user
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false); // flag for user login

  // Get all the Events
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/event")
      .then((response) => {
        setEvents(response.data);
        setLoading(false);
      })
      .catch((err) => {
        setError("Error loading events");
        setLoading(false);
      });
  }, []);

  // Get the user if logged in
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

  // Online filter
  const handleOnlineFilterChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setFilterOnline(event.target.value);
  };

  // State filter
  const handleStateFilterChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setFilterState(event.target.value);
  };

  // Searchbar filter
  const handleSearch = (searchTerm: string) => {
    setSearchTerm(searchTerm.toLowerCase());
  };

  // City filter
  const handleCityFilterChange = () => {
    if (filterCityOnly) {
      setFilterCityOnly(false);
    } else {
      setFilterCityOnly(true);
      setFilterNationOnly(true);
    }
  };

  // Nation filter
  const handleNationFilterChange = () => {
    if (filterNationOnly) {
      setFilterNationOnly(false);
    } else {
      setFilterNationOnly(true);
    }
  };

  // Clear all the filters
  const clearFilters = () => {
    setFilterOnline("all");
    setFilterState("all");
    setFilterCityOnly(false);
    setFilterNationOnly(false);
  };

  // Apply the filters to the Event
  const filteredEvents = events.filter((event) => {
    const matchesOnlineFilter =
      filterOnline === "all" ||
      (filterOnline === "online" && event.online) ||
      (filterOnline === "in person" && !event.online);

    const matchesStateFilter =
      filterState === "all" || event.state === filterState;

    const matchesSearchTerm = event.title.toLowerCase().includes(searchTerm);

    const matchesCityFilter =
      event.online ||
      !filterCityOnly ||
      (filterCityOnly && event.city === user?.city);

    const matchesNationFilter =
      event.online ||
      !filterNationOnly ||
      (filterNationOnly && event.nation === user?.nation);

    if (isLoggedIn) {
      return (
        matchesOnlineFilter &&
        matchesStateFilter &&
        matchesSearchTerm &&
        matchesCityFilter &&
        matchesNationFilter
      );
    } else {
      return matchesOnlineFilter && matchesStateFilter && matchesSearchTerm;
    }
  });

  // handle loading
  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  // handle error
  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div>
      <Searchbar onSearch={handleSearch} />

      {/* Filter Bar */}
      <div className="filter-bar">
        <select value={filterOnline} onChange={handleOnlineFilterChange}>
          <option value="all">All</option>
          <option value="online">Online</option>
          <option value="in person">In Person</option>
        </select>

        <select value={filterState} onChange={handleStateFilterChange}>
          <option value="all">All</option>
          <option value="Open">Open</option>
          <option value="Closed">Closed</option>
          <option value="Finished">Finished</option>
        </select>

        {isLoggedIn && (
          <label className="custom-checkbox">
            <input
              type="checkbox"
              checked={filterCityOnly}
              onChange={handleCityFilterChange}
            />
            Only events in my city
          </label>
        )}

        {isLoggedIn && (
          <label className="custom-checkbox">
            <input
              type="checkbox"
              checked={filterNationOnly}
              onChange={handleNationFilterChange}
            />
            Only events in my nation
          </label>
        )}

        <button type="button" className="btn btn-danger" onClick={clearFilters}>
          Clear
        </button>
      </div>

      {/* List of Events */}
      <div className="event-container">
        {filteredEvents.length > 0 ? (
          filteredEvents.map((event) => (
            <Link
              key={event.id}
              to={`/event/${event.id}`}
              style={{ textDecoration: "none" }}
            >
              {/* Event Card */}
              <EventCard
                title={event.title}
                price={event.price}
                freeSpots={event.freeSpots}
                rating={event.ranking}
                imgSrc={`http://localhost:8080/api/images/event/${event.id}`}
                startDate={formatDate(event.startEvent)}
                endDate={formatDate(event.endEvent)}
                location={`${event.city}, ${event.nation}`}
                online={event.online}
                state={event.state}
              />
            </Link>
          ))
        ) : (
          <p> No Events Available </p>
        )}
      </div>
    </div>
  );
}

export default Home;
