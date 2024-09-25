import "../Styles/ComponentsStyle/Searchbar.css";
import React, { ChangeEvent } from "react";

interface Props {
  onSearch: (searchTerm: string) => void;
}

const Searchbar: React.FC<Props> = ({ onSearch }) => {
  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    onSearch(event.target.value);
  };
  return (
    <div className="search-bar">
      <div className="filter-icon">
        <i className="fas fa-sliders-h"></i>
      </div>
      <input
        type="text"
        className="search-input"
        placeholder="Search events..."
        onChange={handleChange}
      />
      <div className="search-icon">
        <i className="fas fa-times"></i>
      </div>
    </div>
  );
};

export default Searchbar;
