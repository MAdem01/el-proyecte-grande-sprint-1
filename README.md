<!-- PROJECT LOGO -->
<div align="center">
  <h3 align="center">CodeKick FC</h3>

  <p align="center">
    A Springboot based Java Web Application to find, sign up join football matches in your area!
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
      <a href="#running-the-app">Runnig the app</a>
      <ul>
        <li><a href="#with-docker">With Docker</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Welcome to **CodeKick FC**, a Springboot based Java Web Application, hosting a site that allows users to post football games, search for them and join other's!

![alt text](https://i.imgur.com/Yp6e8p8.png)

### Built With

* ![JavaScript][JavaScript-url]
* ![React][React-url]
* ![HTML5][HTML5-url]
* ![Springboot][boot-url]
* ![Postgre][postgre-url]
* ![SpringSecurity][security-url]
* ![CSS3][CSS3-url]
* ![Docker][Docker-url]



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

Before you begin, ensure you have met the following requirements:

* IDE with JDK version of 17.
* SQL cabale Database (Postgre recommended)
* Maps Platform API [google-url]

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/MAdem01/el-proyecte-grande-sprint-1.git
   ```

2. Open the pom.xml with your IDE application and set the environment variables!
   
    * DATABASE_PASSWORD | Database password
    * DATABASE_URL | URL to connect by to your database
    * DATABASE_USERNAME | Username used to log into the database

    * EMAIL_PASSWORD | Password to the email used for the Spring.mail service
    * EMAIL_USERNAME | Used for spring.mail, provide an email you'd like to recieve bug reports from customers

    * GOOGLE_CLIENT_ID | The client ID provuded by the Maps Platform API
    * GOOGLE_CLIENT_SERVICE | The client service (key) provided by the Maps Platform API
    

4. (Optional but recommended) Set your jwt-secret in the application.properties.
   ```sh
    codekick.app.jwtSecret= #64 character long#
   ```

5. Navigate to the frontend folder and install the npm packages.
```sh
    npm install
```
   
<!-- RUNNING THE APP -->
## Running the app
1. Start the backend by launching the CodeKickFcApplication

2. Launch the backend
```sh
    npm run dev
```
   
### With Docker
1. placeholder - dockerization is in process, not fully implemented
   ```sh
   placeholder
   ```

<!-- FEATURES -->
## Features
- Creating and logging into own account securely.
- Searching and joining football matches in your area.
- Responsible single-screen React frontend.

<!-- CONTACT -->
## Contact

Ákos Horváth - [GitHub Profile](https://github.com/Akoss08)

György Kocsis - [GitHub Profile](https://github.com/Scroll120)

Adem Mustajbasic - [GitHub Profile](https://github.com/MAdem01)


Project Link: [https://github.com/MAdem01/el-proyecte-grande-sprint-1/tree/main](https://github.com/MAdem01/el-proyecte-grande-sprint-1/tree/main)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[React-url]: https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black
[JavaScript-url]: https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black
[boot-url]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
[postgre-url]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[HTML5-url]: https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white
[security-url]: https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white
[CSS3-url]: https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white
[Docker-url]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[google-url]: https://developers.google.com/maps/apis-by-platform
