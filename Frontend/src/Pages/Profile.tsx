import React, { useEffect, useState, ChangeEvent, FormEvent } from "react";
import {
  Container,
  Card,
  ListGroup,
  Alert,
  Button,
  Form,
} from "react-bootstrap";
import axios from "axios";
import "../Styles/PagesStyle/Profile.css";
import { User } from "../Types";

const Profile: React.FC = () => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [selectedFile, setSelectedFile] = useState<File | null>(null); // State for the selected image file
  const [profileImageUrl, setProfileImageUrl] = useState<string | null>(null); // To store the preview or updated image
  const [uploadStatus, setUploadStatus] = useState<string | null>(null); // Per memorizzare lo stato dell'upload
  const [showStatus, setShowStatus] = useState<boolean>(false);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
      setProfileImageUrl(
        `http://localhost:8080/api/images/user/${parsedUser.id}`
      );
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }
  }, []);

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setSelectedFile(e.target.files[0]);

      // Preview the selected image before uploading
      const fileReader = new FileReader();
      fileReader.onload = () => {
        setProfileImageUrl(fileReader.result as string);
      };
      fileReader.readAsDataURL(e.target.files[0]);
    }
  };

  const handleImageUpload = async (e: FormEvent) => {
    e.preventDefault();
    if (!selectedFile || !user) return;

    const formData = new FormData();
    formData.append("file", selectedFile); // Aggiungi il file al FormData

    try {
      const response = await axios.post(
        `http://localhost:8080/api/images/user/${user.id}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      if (response.status === 200) {
        setProfileImageUrl(`http://localhost:8080/api/images/user/${user.id}`);
        setUser({ ...user, imageUrl: response.data.imageUrl }); // Aggiorna i dati dell'utente
        localStorage.setItem(
          "user",
          JSON.stringify({ ...user, imageUrl: response.data.imageUrl })
        ); // Aggiorna localStorage

        setUploadStatus("Image uploaded successfully!"); // Mostra messaggio di successo
        setShowStatus(true);
        setTimeout(() => {
          setShowStatus(false);
        }, 3000);
      } else {
        throw new Error("Image upload failed");
      }
    } catch (err) {
      console.error("Error uploading image", err);
      setUploadStatus("Error uploading image. Please try again."); // Mostra messaggio di errore
      setShowStatus(true);
      setTimeout(() => {
        setShowStatus(false);
      }, 3000);
    }
  };

  // Gestisci la rimozione dell'immagine del profilo
  const handleRemoveImage = async () => {
    if (!user) return;

    try {
      const response = await axios.get(
        `http://localhost:8080/api/images/user/remove/${user.id}`
      );

      if (response.status === 200) {
        setProfileImageUrl(null); // Rimuove l'immagine dal profilo
        setUser({ ...user, imageUrl: "" }); // Aggiorna i dati dell'utente
        localStorage.setItem(
          "user",
          JSON.stringify({ ...user, imageUrl: null })
        ); // Aggiorna localStorage
        setUploadStatus("Profile image removed successfully!");
        setShowStatus(true);
        setTimeout(() => {
          setShowStatus(false);
        }, 3000);
      } else {
        throw new Error("Failed to remove profile image");
      }
    } catch (err) {
      console.error("Error removing profile image", err);
      setUploadStatus("Error removing profile image. Please try again.");
      setShowStatus(true);
      setTimeout(() => {
        setShowStatus(false);
      }, 3000);
    }
  };

  return (
    <Container className="profile-container">
      {isLoggedIn ? (
        <Card className="profile-card">
          <Card.Body>
            <div className="profile-image-container">
              {profileImageUrl ? (
                <img
                  src={profileImageUrl}
                  alt={`${user?.name} ${user?.lastname}`}
                  className="profile-image"
                />
              ) : (
                <div className="profile-placeholder-image">No Image</div>
              )}
            </div>
            <div className="profile-info">
              {user?.name} {user?.lastname}
              <br /> <strong> {user?.isCreator ? "#Creator" : "#User"} </strong>
              <br /> @{user?.username}
            </div>
            <ListGroup variant="flush">
              <ListGroup.Item>
                <strong>Email:</strong> {user?.email}
              </ListGroup.Item>
              <ListGroup.Item>
                <strong>Address:</strong> {user?.street}, {user?.zipcode},{" "}
                {user?.city}, {user?.nation}
              </ListGroup.Item>
            </ListGroup>

            {/* Sezione per il caricamento dell'immagine */}
            <Form onSubmit={handleImageUpload}>
              <Form.Group controlId="formFile" className="mt-3">
                <Form.Label>Change Profile Image</Form.Label>
                <Form.Control type="file" onChange={handleFileChange} />
              </Form.Group>
              <Button type="submit" variant="primary" className="mt-3">
                Upload Image
              </Button>

              {/* Mostra il pulsante "Remove Profile Image" solo se c'è un'immagine */}
              {profileImageUrl && (
                <Button
                  variant="danger"
                  className="mt-3 ml-2"
                  onClick={handleRemoveImage}
                >
                  Remove Profile Image
                </Button>
              )}

              {/* Mostra il messaggio di upload */}
              {showStatus && (
                <Alert
                  className="mt-3"
                  variant={
                    uploadStatus?.includes("successfully")
                      ? "success"
                      : "danger"
                  }
                >
                  {uploadStatus}
                </Alert>
              )}
            </Form>
          </Card.Body>
        </Card>
      ) : (
        <div className="text-center mt-4">
          <Alert variant="warning">You are not logged in</Alert>
        </div>
      )}
    </Container>
  );
};

export default Profile;
