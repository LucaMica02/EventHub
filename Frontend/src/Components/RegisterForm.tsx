import React, { useState, FormEvent, ChangeEvent } from "react";
import { Container, Form, Button, Card, Alert } from "react-bootstrap";
import axios from "axios";
import "../Styles/ComponentsStyle/RegisterForm.css";

interface FormValues {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  street: string;
  zipcode: string;
  city: string;
  nation: string;
  taxID?: string; // Campo opzionale per il taxID
}

interface LoginFormProps {
  onLogin: (
    username: string,
    password: string,
    flagRegistered: boolean
  ) => void;
}

const RegisterForm: React.FC<LoginFormProps> = ({ onLogin }) => {
  const [formValues, setFormValues] = useState<FormValues>({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
    email: "",
    street: "",
    zipcode: "",
    city: "",
    nation: "",
    taxID: "", // Inizialmente vuoto
  });
  const [error, setError] = useState<string>("");
  const [success, setSuccess] = useState<string>("");
  const [isCreator, setIsCreator] = useState<boolean>(false); // Stato per gestire la modalità creator

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  const handleSelectChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    const {
      username,
      password,
      firstName,
      lastName,
      email,
      street,
      zipcode,
      city,
      nation,
      taxID,
    } = formValues;

    if (
      !username ||
      !password ||
      !firstName ||
      !lastName ||
      !email ||
      !street ||
      !zipcode ||
      !city ||
      !nation ||
      (isCreator && !taxID)
    ) {
      setError("Please fill in all fields");
      return;
    }
    setError("");

    try {
      const response = await axios.post(
        `http://localhost:8080/api/appUser/register`,
        {
          name: firstName,
          lastname: lastName,
          username: username,
          email: email,
          password: password,
          street: street,
          zipcode: zipcode,
          city: city,
          nation: nation,
          isCreator: isCreator,
          taxId: isCreator ? taxID : undefined, // Include taxID solo se è creator
        }
      );

      if (response.status === 201) {
        setSuccess("Registration successful!");
        await handleAutoLogin(username, password);
      } else {
        throw new Error("Registration failed");
      }
    } catch (err) {
      console.error("Registration error:", err);
      setError("Registration failed. Please try again.");
    }
  };

  // Funzione per gestire il login automatico dopo la registrazione
  const handleAutoLogin = async (username: string, password: string) => {
    onLogin(username, password, true);
  };

  return (
    <Container className="register-container">
      <Card className="register-card">
        <Card.Body>
          <Card.Title className="register-title">Register</Card.Title>
          {error && <Alert variant="danger">{error}</Alert>}
          {success && <Alert variant="success">{success}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formBasicFirstName">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter first name"
                name="firstName"
                value={formValues.firstName}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicLastName">
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter last name"
                name="lastName"
                value={formValues.lastName}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicUsername">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                name="username"
                value={formValues.username}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                name="password"
                value={formValues.password}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicEmail">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter email"
                name="email"
                value={formValues.email}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicStreet">
              <Form.Label>Street</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter street"
                name="street"
                value={formValues.street}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicZipcode">
              <Form.Label>Zipcode</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter zipcode"
                name="zipcode"
                value={formValues.zipcode}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicCity">
              <Form.Label>City</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter city"
                name="city"
                value={formValues.city}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicNation">
              <Form.Label>Nation</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter nation"
                name="nation"
                value={formValues.nation}
                onChange={handleInputChange}
              />
            </Form.Group>

            {isCreator && (
              <Form.Group controlId="formBasicTaxID">
                <Form.Label>Tax ID</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter tax ID"
                  name="taxID"
                  value={formValues.taxID || ""}
                  onChange={handleInputChange}
                />
              </Form.Group>
            )}

            <Button
              variant="secondary"
              onClick={() => setIsCreator(!isCreator)} // Toggle creator mode
              className="mt-3"
            >
              {isCreator ? "Switch to Regular User" : "Switch to Creator"}
            </Button>

            <Button
              variant="primary"
              type="submit"
              className="register-button mt-3"
            >
              Register
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default RegisterForm;
