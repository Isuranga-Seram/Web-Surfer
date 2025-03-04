<br>
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
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#Version">Version</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
<h1 align="center">Web-Surfer</h1>
<br>

![Media Fun Screen Shot1][product-screenshot-1]
screenshot - web-Surfer-app testing1
<br><br>

![Media Fun Screen Shot2][product-screenshot-2]

screenshot - web-Surfer-app testing2
<br><br>

## Project Description ℹ️
This project is a lightweight web browser application that can send HTTP requests, process responses, and render web content. It includes basic functionalities such as handling redirects, reading server responses, and displaying error messages in a simple web view.
<br><br>

---
## Features of the Web-Surfer Application 🚀


- **Basic Web Navigation**: Users can enter URLs and load web pages.


- **Custom User-Agent Support**: The browser sets a specific User-Agent for HTTP requests.


- **Error Handling**: Displays user-friendly error messages for bad requests or connection issues.


- **HTTP Request Handling**: Supports sending HTTP GET requests to retrieve web content.


- **Redirect Handling**: Detects redirection responses and processes them accordingly.


<br>

## Technologies Used 👩🏻‍💻

### 1. **Programming Language** :
    * Java


### 2. **Networking** :
    * Java Sockets (for HTTP requests).


### 3. UI Framework:
    * JavaFX WebView.


### 4. **Multi-threading** :
    * Used for handling server responses without freezing the UI.


<p align="right">(<a href="#readme-top">back to top</a>)</p>


### <p align="center">**_Client-Server Architecture_**

This web server follows a client-server model, where:

1. **The server (ServerSocket)** listens on port 80 for incoming connections.


2. A **client** (e.g., browser, Postman, or curl) initiates an HTTP request.


3. The server **accepts the connection** (accept()) and spawns a new thread to handle the request.


4. The server reads the **HTTP request headers** from the client using BufferedReader


5. (Optional) It can process different **HTTP methods** (GET, POST, etc.) and respond accordingly.


6. Once processing is complete, the server sends back an HTTP response and closes the connection.

<br>
<hr>


### Built With 🛠️

The Web-Surfer App was developed basically with Java language.

- Java
- JavaFX (for UI & WebView)

![java-url]  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![javafx][javafx-url]
<br><br>



<p align="right">(<a href="#readme-top">back to top</a>)</p>

<hr>

<!-- GETTING STARTED -->
## Getting Started

Let's surf into the Web-Surfer.

To contribute or understand this project, one should have:
* **Networking in Java**
    * How Domain Name System (DNS) works,
    * How HTTP Request/ Response are working


- **Java Programming** (Core concepts, object-oriented principles)


- **JavaFX** (UI and WebView for rendering pages)


- **Multi-threading** (Handling network responses efficiently)


- **HTTP Protocols** (GET requests, headers, response handling)

<br>

### Installation 💻

_Below is an example of how you can installing and setting up the Web-Surfer._

1. Clone the repo
   ```bash
   git clone https://github.com/Isuranga-Seram/Web-Surfer.git
   cd Web-Surfer
   ```

2. Change git remote url to avoid accidental pushes to base project
   ```sh
   git remote set-url origin github_username/repo_name  #optional
   git remote -v # confirm the changes
   ```

3. Now you can run the app

   Enjoy !!


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<hr>

<!-- USAGE EXAMPLES -->
## Usage

The Web-Surfer app have a variety of uses. Here are some of the main use cases:

1. **Personal Browser 🌐**
    * Can be extended with additional features like bookmarks or history.

   
2. **Development Testing 🛠**
    * Helps developers understand network programming, multi-threading, and HTTP request/ Response handling.
    * Can be used to test custom web requests with different User-Agent headers.


3. **Educational Purpose 📚**
    * Learn how web browsers work internally.
    * Provides a practical way to experiment with sockets, HTTP headers, and request handling.



<p align="right">(<a href="#readme-top">back to top</a>)</p>

<hr>

<!-- CONTRIBUTING -->
## Contributing 👪

🚀 "Great projects thrive on collaboration! Join us in building something amazing—contribute your skills, ideas, and code to make this web browser even better!" 💡✨

<br>

### Potential Enhancements :

Here are some ideas,
* Enhance UI for better user experience.
* Improve request handling (e.g., adding support for POST requests).
* Implement JavaScript and CSS support for modern web standards.
* Add tabbed browsing functionality.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<hr>

<!-- Version -->
## Version 📳

0.0.1

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<hr>

<!-- LICENSE -->
## License

Copyright © 2025 Web-Surfer Application. All Rights Reserved This project is licensed under the [MIT License](LICENSE.txt).
See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<hr>

<!-- CONTACT -->
## Contact ☎

Isuranga Seram [email] - **_isurangaseram@gmail.com_**

LinkedIn: [linkedin.com/in/isuranga-seram](linkedin.com/in/isuranga-seram)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<hr>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* "I sincerely appreciate the support and contributions from everyone who helped shape this web server project. 
* Special thanks to the open-source community for inspiring innovation and collaboration." 🚀
  - **Oracle & Java Community**: For JavaFX and networking documentation.
  - **Mozilla Developer Network (MDN)**: For HTTP and browser-related insights.
  - **Open-source contributors**: For networking and web rendering inspirations.


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[product-screenshot-1]: img/screenshot1.png
[product-screenshot-2]: img/screenshot2.png
[java]: https://www.java.com/en/
[java-url]: https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Java_programming_language_logo.svg/121px-Java_programming_language_logo.svg.png
[javafx]: https://openjfx.io/
[javafx-url]: https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/JavaFX_Logo.png/220px-JavaFX_Logo.png
 