import React, { useState, FormEvent } from "react";
import { Container, Form, Button, Card, Alert } from "react-bootstrap";
import "../Styles/ComponentsStyle/LoginForm.css";

interface FormValues {
  username: string;
  password: string;
}

interface LoginFormProps {
  onLogin: (
    username: string,
    password: string,
    flagRegistered: boolean
  ) => void; // Modificato per includere password
}

const LoginForm: React.FC<LoginFormProps> = ({ onLogin }) => {
  const [formValues, setFormValues] = useState<FormValues>({
    username: "",
    password: "",
  });
  const [error, setError] = useState<string>("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    const { username, password } = formValues;

    if (!username || !password) {
      setError("Please fill in all fields");
      return;
    }

    setError("");
    onLogin(username, password, false);
  };

  return (
    <Container className="login-container">
      <Card className="login-card">
        <Card.Body>
          <Card.Title className="login-title">Login</Card.Title>
          {error && <Alert variant="danger">{error}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formBasicUsername">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                name="username"
                value={formValues.username}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                name="password"
                value={formValues.password}
                onChange={handleChange}
              />
            </Form.Group>

            <Button variant="primary" type="submit" className="login-button">
              Log In
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default LoginForm;
