<a id="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]

<!-- PROJECT LOGO -->
<div align="center">
  <h3 align="center">CodeKick FC</h3>

  <p align="center">
    A Springboot based Java Web Application to find, sign up and join football matches in your area!
 <br />
    <a href="https://github.com/MAdem01/el-proyecte-grande-sprint-1"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/MAdem01/el-proyecte-grande-sprint-1">View Demo</a>
    &middot;
    <a href="https://github.com/MAdem01/el-proyecte-grande-sprint-1/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/MAdem01/el-proyecte-grande-sprint-1/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>

</p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
         <li>
            <a href="#main-features">Main Features</a>
         </li>
         <li>
            <a href="#built-with">Built With</a>
         </li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
         <li>
            <a href="#backend">Backend</a>
         <ul>
            <li><a href="#backend-prerequisites">Prerequisites</a></li>
            <li><a href="#backend-installation">Installation</a></li>
         </ul>
         </li>
         <li>
            <a href="#frontend">Frontend</a>
         <ul>
            <li><a href="#frontend-prerequisites">Prerequisites</a></li>
            <li><a href="#frontend-installation">Installation</a></li>
         </ul>
         </li>
      </ul>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

Welcome to **CodeKick FC**,

a Spring Boot-based Java web application that lets users search for local football matches by area. Users can view match
details, including the number of players signed up, match timing, and location.

By clicking on a game, they can access more information such as the players' profiles and match rules. If they've
registered, users can also book their spot in the game.

![alt text](https://i.imgur.com/Yp6e8p8.png)

### Main Features:

🔹 <u><i>Discover Local Football Matches:</i></u> Search for nearby games and explore match details such as timing,
location, and player count.

⚽ <u><i>Join Football Games:</i></u> Sign up for games and book your spot — available only to players who are logged in.

📝 <u><i>Match Details:</i></u> Click on _DETAILS_ to view the match information, including the players signed up, match
timing, picture and detailed location with map of the pitch, rules of the game and the facility.

👤 <u><i>Player Profiles:</i></u> Logged-in players can view detailed profiles of other players, including the games
they’ve signed up for.

🔑 <u><i>Admin Game Creation:</i></u> Admins can log in and create new football matches, setting up all game details for
players to join.

💳 <u><i>Seamless Payment Integration:</i></u> Players are redirected to a secure payment page when joining a match. Once
payment is successful, they receive a confirmation email.
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* [![Java][Java]][Java-url]
* [![JavaScript][Javascript-shield]][JavaScript-url]
* [![React][React-shield]][React-url]
* ![HTML5][HTML5-url]
* [![Springboot][boot-shield]][boot-url]
* [![Postgres][Postgre-shield]][postgre-url]
* [![SpringSecurity][security-shield]][security-url]
* ![CSS3][CSS3-url]
* [![Docker][Docker-shield]][Docker-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

# Getting Started

To get a local copy up and running follow these simple steps.

## Backend

<h3 id="backend-prerequisites">Prerequisites</h3>

Before you begin, ensure you have met the following requirements:

* IDE with JDK version of 17.
* SQL capable Database (Postgres recommended, otherwise change the driver in _application.properties_)
* <a href="#using-docker">Using Docker?</a>

<h3 id="backend-installation">Installation</h3>

1. Clone the repo:
   ```sh
   git clone https://github.com/MAdem01/el-proyecte-grande-sprint-1.git
   ```

2. Open the pom.xml File in your IDE as a project

3. Create a database

4. Set the environment variables:

* **DATABASE_URL** — URL to connect by to your database
* **DATABASE_USERNAME** — Username used to log into the database
* **DATABASE_PASSWORD** — Database password
* **EMAIL_USERNAME** — Used for _spring.mail_, provide an email you'd like to recieve bug reports from customers
* **EMAIL_PASSWORD** — Password to the email used for the _Spring.mail_ service
* **(OPTIONAL BUT RECOMMENDED)** — Set your jwt-secret in the application.properties

5. Launch the application! (_CodeKickFcApplication.class_)

### Using Docker

1. Clone the repo:
   ```sh
   git clone https://github.com/MAdem01/el-proyecte-grande-sprint-1.git
   ```

2. Open the pom.xml File in your IDE as a project


3. Set the environment variables in **_docker-compose.yml_**:

* **SPRING_DATASOURCE_URL** — URL to connect by to your database
* **SPRING_DATASOURCE_USERNAME** — Username used to log into the database
* **SPRING_DATASOURCE_PASSWORD** — Database password
* **EMAIL_USERNAME** — Used for _spring.mail_, provide an email you'd like to recieve bug reports from customers
* **EMAIL_PASSWORD** — Password to the email used for the _Spring.mail_ service
* **(OPTIONAL BUT RECOMMENDED)** — Set your jwt-secret in the application.properties


4. Build and start the containers:

```sh
   mvn clean install
   docker-compose up --build
```

5. To stop running the container:

```sh
   docker-compose down
```

## Frontend


<h3 id="frontend-prerequisites">Prerequisites</h3>

- Before you begin, ensure that you have Node.js and npm installed. If not, you can download and install them
  from <a href="https://nodejs.org/en/download">nodejs.org</a>.
- You will need an API Key for Google Maps. <a href="https://www.youtube.com/watch?v=hsNlz7-abd0&t=3s">Watch this simple tutorial to get one!</a>
- You will also need a MAP ID for the visual display. <a href="https://developers.google.com/maps/documentation/javascript/map-ids/get-map-id#create_map_ids">Follow this steps</a>.

<h3 id="frontend-installation">Installation</h3>

1. Once you have a cloned version of the repo navigate to the _frontend_ folder


2. Install the npm packages:

```sh
   npm install
```

3. Create a _.env_ file in _frontend_ folder and set the environment variable:

```
VITE_GOOGLE_API_KEY=YOUR_API_KEY
VITE_GOOGLE_MAP_ID=YOUR_MAP_ID
```

4. Start the development server:

```sh
npm run dev
```

5. Open your browser and go to http://localhost:5173/ to access the website!

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

This project is currently read-only and not open to external contributions.
However, feel free to fork the repository for personal use or exploration.

Don't forget to give the project a star if you like it! 🌟

### Top contributors:


<a href="https://github.com/MAdem01/el-proyecte-grande-sprint-1/graphs/contributors">
<img src="https://contrib.rocks/image?repo=MAdem01/el-proyecte-grande-sprint-1" />
</a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

Ákos Horváth - [GitHub Profile](https://github.com/Akoss08)

György Kocsis - [GitHub Profile](https://github.com/Scroll120)

Adem Mustajbasic - [GitHub Profile](https://github.com/MAdem01)

Project
Link: [https://github.com/MAdem01/el-proyecte-grande-sprint-1/tree/main](https://github.com/MAdem01/el-proyecte-grande-sprint-1/tree/main)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/MAdem01/el-proyecte-grande-sprint-1.svg?style=for-the-badge

[contributors-url]: https://github.com/MAdem01/el-proyecte-grande-sprint-1/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/MAdem01/el-proyecte-grande-sprint-1.svg?style=for-the-badge

[forks-url]: https://github.com/MAdem01/el-proyecte-grande-sprint-1/network/members

[stars-shield]: https://img.shields.io/github/stars/MAdem01/el-proyecte-grande-sprint-1.svg?style=for-the-badge

[stars-url]: https://github.com/MAdem01/el-proyecte-grande-sprint-1/stargazers

[issues-shield]: https://img.shields.io/github/issues/MAdem01/el-proyecte-grande-sprint-1.svg?style=for-the-badge

[issues-url]: https://github.com/MAdem01/el-proyecte-grande-sprint-1/issues

[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/en/

[React-shield]: https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black
[React-url]: https://react.dev/

[JavaScript-shield]: https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black
[JavaScript-url]: https://www.javascript.com/

[boot-shield]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
[boot-url]: https://spring.io/projects/spring-boot

[postgre-shield]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[postgre-url]: https://www.postgresql.org/

[HTML5-url]: https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white

[security-shield]: https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white
[security-url]: https://spring.io/projects/spring-security

[CSS3-url]: https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white

[Docker-shield]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/

[google-url]: https://developers.google.com/maps/apis-by-platform
