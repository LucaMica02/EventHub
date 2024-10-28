# EventHub

![Docker](https://img.shields.io/badge/Docker-20.10-blue?logo=docker&logoColor=white)
![PostgreSQL Version](https://img.shields.io/badge/PostgreSQL-14%2B-blue)
![Java Version](https://img.shields.io/badge/Java-17%2B-green)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5-green)
![TypeScript](https://img.shields.io/badge/TypeScript-4.5-blue)
![React](https://img.shields.io/badge/React-17%2B-blue)
![HTML](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white)
![Work in Progress](https://img.shields.io/badge/Work%20in%20Progress-yellow)

## Description 📝
**EventHub** is a full stack web application that allows users to create and attend events. With EventHub, you can discover new events, join existing ones, or organize your own, all through a simple and intuitive interface!

## Features 🚀
- **Creator:** register as a creator to create new events.
- **User:** register as a user to join existing events.
- **Events:** search for events and filter them by status, location or name.
- **Creators:** view the list of available creators by rank and view their events.
- **Feedback:** you can leave feedback for all events you attend.

## Installation 📦

### What you need
- **Docker:** [Get Docker](https://docs.docker.com/get-started/get-docker/)
- **Docker Compose:** [Get Docker Compose](https://docs.docker.com/compose/install/)

### Run the application
If you have docker and docker-compose in your system follow this steps to run the application:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/LucaMica02/EventHub.git
   ```
   
2. **Navigate to the project directory**
    ```bash
    cd path/to/EventHub
    ```
    
3. **Run docker compose**
The first time you run the project can take some minutes to download all the dependencies
   ```bash
   docker-compose up --build
   ```
In the future execution if you don't make change to the code or to the Dockerfile you can run just:
   ```bash
   docker-compose up
   ```

4. **Enojoy the application**
You can go on "http://localhost:5173/" from your browser and see the application running.

5. **Close the application**
To stop the application and remove the containers, you can use:
   ```bash
   docker-compose down
   ```
If you simply want to stop the containers without removing them, you can use:
   ```bash
   docker-compose stop
   ```
You can then restart the application later with:
   ```bash
   docker-compose start
   ```
   
## Conclusion 🔚
**EventHub** is a platform that simplifies the creation and participation of events, making it easy for users to discover new opportunities for socialization and networking. Developed using a modern full stack architecture, with technologies such as Java, Spring Boot, React, TypeScript, and Docker, the application is designed to be scalable and easy to maintain.

If you have suggestions or want to contribute to the project, feel free to open a ticket or create a pull request. We are excited to see how EventHub can evolve with the contribution of the community!
