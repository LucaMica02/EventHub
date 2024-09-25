import React, { useState, useEffect } from "react";
import LoginForm from "../Components/LoginForm";
import RegisterForm from "../Components/RegisterForm";
import { Button } from "react-bootstrap";
import "../Styles/PagesStyle/Login.css";
import { useAuth } from "../UserAuth";
import axios from "axios";

interface LoginFormProps {
  onLogin: (
    username: string,
    password: string,
    flagRegistered: boolean
  ) => void;
}

const LoginPage: React.FC = () => {
  const { user, login, logout } = useAuth(); // Usa il contesto di autenticazione
  const [showRegister, setShowRegister] = useState<boolean>(false);
  const [welcomeMessage, setWelcomeMessage] = useState<string>("");

  const handleLogin = async (
    username: string,
    password: string,
    flagRegistered: boolean
  ) => {
    try {
      if (!user) {
        const response = await axios.post(
          "http://localhost:8080/login",
          {},
          {
            params: {
              username,
              password,
            },
            withCredentials: true,
          }
        );
        if (response.status == 202) {
          const userData = response.data;
          login(userData);
          if (flagRegistered) {
            setWelcomeMessage("Welcome To Event Hub!");
          } else {
            setWelcomeMessage("Welcome Back!");
          }
        } else {
          alert("Bad Credential");
        }
      }
    } catch (error) {
      console.error("Login error: ", error);
    }
  };

  const handleLogout = async () => {
    try {
      await axios.post(
        "http://localhost:8080/logout",
        {},
        { withCredentials: true }
      );
      logout();
    } catch (error) {
      console.error("Error during logout: ", error);
    }
  };

  const handleToggleForm = () => {
    setShowRegister(!showRegister);
  };

  return (
    <div className="page-container">
      {user ? (
        <>
          <h1 className="welcome-message">{welcomeMessage}</h1>
          <Button onClick={handleLogout}>Logout</Button>
        </>
      ) : (
        <>
          {showRegister ? (
            <RegisterForm onLogin={handleLogin} />
          ) : (
            <LoginForm onLogin={handleLogin} />
          )}
          <Button
            variant="link"
            className="toggle-button"
            onClick={handleToggleForm}
          >
            {showRegister
              ? "Already have an account? Log in"
              : "Don't have an account? Register"}
          </Button>
        </>
      )}
    </div>
  );
};

export default LoginPage;
