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
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>
    &middot;
    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
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

Selected Algorithms: Public-key Encryption and Key-establishment
* CRYSTALS-KYBER (3 parameter sets)
* HQC (3 parameter sets)

Selected Algorithms: Digital Signature
* CRYSTALS-DILITHIUM (3 parameter sets)
* FALCON (2 parameter sets)
* SPHINCS+ (24 parameter sets)

Round 4 Submissions: Public-key Encryption and Key-establishment
* BIKE (3 parameter sets)
* Classic McEliece (6 parameter sets)

🧪 Includes Postman-ready templates for easy API testing

Built with Spring Boot, CryptoMall is ideal for developers, researchers, and security enthusiasts seeking a modular and extensible cryptographic toolkit. It also includes Postman-ready templates to simplify endpoint testing.

Whether you're prototyping secure APIs, exploring post-quantum algorithms, or building your own crypto toolkit, CryptoMall offers a clean and extensible starting point.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [![Spring][spring-boot]][Next-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/github_username/repo_name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```
5. Change git remote url to avoid accidental pushes to base project
   ```sh
   git remote set-url origin github_username/repo_name
   git remote -v # confirm the changes
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

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



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/Telefu21/cryptomall.svg?style=for-the-badge
[license-url]: https://github.com/Telefu21/cryptomall/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/ozgur-erdener-phd/
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://spring.io
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
