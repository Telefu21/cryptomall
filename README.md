<a id="readme-top"></a>

<br />
<div align="center">
  <a href="https://github.com/Telefu21/cryptomall">
    <img src="src/main/resources/images/cryptomall617p.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">CryptoMall</h3>

  <p align="center">
    Classic and Post Quantum Cryptography Calculation Tool
    <br />
    <a href="https://github.com/Telefu21/cryptomall/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/Telefu21/cryptomall/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
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
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
</p>

CryptoMall is a comprehensive cryptographic calculator built as a Java-based UI and RESTful application. It streamlines core crypto operations through a user-friendly interface and API endpoints.

## Features

🔑 Generate public/private key pairs (RSA, EC, DSA, DH etc.)

📜 Generate and verify digital signatures

🛡️ Encrypt and decrypt data (symmetric & asymmetric)

🔁 Perform hashing operations (SHA-256, MD5, etc.)

🔏 Compute CMAC/HMAC for message authentication

📊 Generate CRC checksums for integrity verification

🔄 Support file format conversion (e.g., PEM ↔ DER, View Certificates, View Key Files etc.)

📁 Manage and validate X.509 certificates

🧠 Explore Post-Quantum Cryptography

&nbsp; &nbsp;&nbsp;&nbsp;Selected Algorithms: Public-key Encryption and Key-establishment
* CRYSTALS-KYBER (3 parameter sets)
* HQC (3 parameter sets)

&nbsp; &nbsp;&nbsp;&nbsp;Selected Algorithms: Digital Signature
* CRYSTALS-DILITHIUM (3 parameter sets)
* FALCON (2 parameter sets)
* SPHINCS+ (24 parameter sets)

&nbsp; &nbsp;&nbsp;&nbsp;Round 4 Submissions: Public-key Encryption and Key-establishment
* BIKE (3 parameter sets)
* Classic McEliece (6 parameter sets)

🧪 Includes Postman-ready templates for easy API testing

Built with Spring Boot and JavaFx, CryptoMall is ideal for developers, researchers, and security enthusiasts seeking a modular and extensible cryptographic toolkit. It also includes Postman-ready templates to simplify endpoint testing.

Whether you're prototyping secure APIs, exploring post-quantum algorithms, or building your own crypto toolkit, CryptoMall offers a clean and extensible starting point.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Built With

Cryptomall is built as a Maven project, with all dependencies listed in the  [pom.xml](https://github.com/Telefu21/cryptomall/blob/main/pom.xml) file. It integrates several key frameworks and libraries:

* **Spring Boot** – Powers the REST API backend.
* **JavaFX** – Provides the graphical user interface (GUI).
* **Bouncy Castle** – Implements Post-Quantum Cryptography.
* **OpenSSL** – Enables Classic Cryptography via system calls to the executable.
* **Log4j** – Handles application logging.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

Cryptomall is a Maven project.

### Prerequisites
<a id="Prerequisites"></a>
🖥️ Windows Installation Requirements for Cryptomall

To run and update the Cryptomall application on Windows platforms, the following packages must be installed on your system.

* [Install Java](https://www.oracle.com/java/technologies/downloads/)
* [Install Maven](https://maven.apache.org/install.html) 
* [Install OpenSSL](https://slproweb.com/products/Win32OpenSSL.html) 

🐧 Linux Users: No installation is necessary—most Linux distributions already include these packages by default.

### Running the Application

The application can be executed by applying the below steps if the given  <a href="#readme-top">Prerequisites</a> are fulfilled. 

1. This step is optional. Skip this step If Cryptomall is used with GUI and RestAPI functionality.
    * Remove the comment symbol (e.g. "#") at below line from [application.properties](https://github.com/Telefu21/cryptomall/blob/main/src/main/resources/application.properties) file to disable the RestAPI functionality:

      * #spring.main.web-application-type=none

    * Assign "false" to property given below from [application.properties](https://github.com/Telefu21/cryptomall/blob/main/src/main/resources/application.properties) file to disable the GUI functionality:

      * javafxenabled=false

2. * Clone the repo if you have git installed:
   ```sh
   git clone https://github.com/Telefu21/cryptomall.git
   ```
   * Or [Dowload](https://github.com/Telefu21/cryptomall/archive/refs/heads/main.zip) and unzip the project folder if you are only a user.

3. Run the application from command line under the project folder.
   ```sh
   mvn spring-boot:run
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Cryptomall features a user-friendly GUI that makes it easy to use, as demonstrated in the screenshots below. For API integration, please refer to the [Postman-ready templates](https://github.com/Telefu21/cryptomall/blob/main/api/CryptoMall.postman_collection.json).

<div align="center">
  <a href="https://github.com/Telefu21/cryptomall/blob/main/GUIScreenShots/KeygenFileConvertScreenShot.png">
    <img src="GUIScreenShots/KeygenFileConvertScreenShot.png" alt="Logo" width="200" height="200">
  </a>
  <a href="https://github.com/Telefu21/cryptomall/blob/main/GUIScreenShots/EncryptDecryptScreenshot.png">
    <img src="GUIScreenShots/EncryptDecryptScreenshot.png" alt="Logo" width="200" height="200">
  </a>
  
  <a href="https://github.com/Telefu21/cryptomall/blob/main/GUIScreenShots/SignVerifyScreenShot.png">
    <img src="GUIScreenShots/SignVerifyScreenShot.png" alt="Logo" width="200" height="200">
  </a>
</div> 

<div align="center">
  <a href="https://github.com/Telefu21/cryptomall/blob/main/GUIScreenShots/CertificateScreenShot.png">
    <img src="GUIScreenShots/CertificateScreenShot.png" alt="Logo" width="200" height="200">
  </a>
  <a href="https://github.com/Telefu21/cryptomall/blob/main/GUIScreenShots/PostQuantumScreenShot.png">
    <img src="GUIScreenShots/PostQuantumScreenShot.png" alt="Logo" width="200" height="200">
  </a>
  <a href="https://github.com/Telefu21/cryptomall/blob/main/GUIScreenShots/CRCHexViewScreenShot.png">
    <img src="GUIScreenShots/CRCHexViewScreenShot.png" alt="Logo" width="200" height="200">
  </a>
</div>

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Add CRC Calculation to API
- [ ] Add Encrypt/Decrypt functionality to API
- [ ] Add Changelog
- [ ] Add Additional Templates w/ Examples
- [ ] Add Api Documentation

See the [open issues](https://github.com/Telefu21/cryptomall/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/SomeFeature`)
3. Commit your Changes (`git commit -m 'Add some Feature'`)
4. Push to the Branch (`git push origin feature/SomeFeature`)
5. Open a Pull Request

<!-- LICENSE -->
## License

CryptoMall is MIT Licensed. See [`LICENSE.txt`](./LICENSE) for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Ozgur Erdener: zgrrdnr@gmail.com

Project Link: [https://github.com/Telefu21/cryptomall](https://github.com/Telefu21/cryptomall)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Spring Boot](https://spring.io/projects/spring-boot)
* [JavaFx](https://openjfx.io/)
* [NIST Post Quntum Cryptography](https://csrc.nist.gov/projects/post-quantum-cryptography)
* [OpenSSL Documentation](https://docs.openssl.org/master/)
* [Maven Repository](https://mvnrepository.com/)
* [Bouncy Castle](https://www.bouncycastle.org/)
* [Postman](https://www.postman.com/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
