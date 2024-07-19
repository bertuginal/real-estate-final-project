<div align="center"><h1> :sparkles: REAL ESTATE FINAL PROJECT :sparkles: </h1></div>
<div align="center"><h2> 🌐 FMSS & Patika+ Fullstack Web Bootcamp 🌐 </h2> </div>

## ❓ What is this project?
This project is a real estate website completion project developed as a full stack using Java, Spring Boot in the back-end and React and Next.js in the front-end.

&nbsp; 

## 🔎 Overview

The real estate website project is designed to allow the user to create advertisements by communicating with each other through many microservices.

* User can check balance and usage time by looking at profile details.

* User can create, edit, delete advertisement and access its details.

* The user can turn his ads, which are initially IN_REVIEW, into ACTIVE and PASSIVE.

* Can make package purchases and payments.

* Can view the details of the packages purchased.

&nbsp; 

## ⚙️ BACK-END

* [![Java][Java-logo]][Java-url]
* [![Spring][Spring-logo]][Spring-url]
* [![Maven][Maven-logo]][Maven-url]
* [![Postgresql][Postgresql-logo]][Postgresql-url]
* [![Postman][Postman-logo]][Postman-url]
* [![Swagger][Swagger-logo]][Swagger-url]
       

### 🔒 Dependencies
 
<b>✔️ Spring Web </b>

<b>✔️ Spring Data JPA </b>

<b>✔️ Lombok </b>

<b>✔️ RabbitMQ </b>

<b>✔️ Hibernate </b>

<b>✔️ Model Mapper </b>

<b>✔️ Open Feign </b>

<b>✔️ Service Discovery </b>

&nbsp; 

<!-- FEATURES -->
## ✨ Features

❶ Users can register to the system.
  
❷ The user can log in to the system with the email and password he/she registered with. If the login is successful, a token belonging to the user is created and he can perform authorized transactions with this token.

❸ Each user's initial right to post ads is "0". In order to post an ad, he/she must purchase a package.
  
❹ The right to publish advertisements and the validity period of the purchased package are defined in the user model.

❺ The package includes the right to publish 10 advertisements and the usage period of the package is 30 days.

❻ When the user wants to buy a package, his ad balance and usage time are checked.

* If the package has expired, the remaining rights will be reset and you will need to buy the package again.
  * If the user purchases a package and makes the payment, the purchased package is defined to the user's profile via rabbitMQ.
  * The right to publish 10 advertisements and a 30-day usage period are added based on the day of purchase.

* If the usage period has not expired, the balance will not be reset.
  * If the user purchases a new package and the payment transaction is confirmed, 10 advertising rights will be added to the remaining usage rights and the usage period will be increased by 30 days.

❼ The purchase is made according to the ID of the user logged into the system. 

(This process is performed according to the information received from the token in the header.)

❽ When the user wants to create an advertisement, advertisement rights and usage time are checked.

 * If the package has expired or there are no ad rights left, the ad publishing process will be canceled and an exception error message will be returned to the user.

* If the package has not expired and there is a right to advertise, the ad creation process will be successful. The cost of the package is deducted from the user's balance.

❾ When the ad is created, the ad status is IN_REVIEW. The user can change the status of the ad to ACTIVE or PASSIVE from another service.

✔️ See the [open issues](https://github.com/bertuginal/real-estate-final-project/issues) for a full list of proposed features (and known issues)!

&nbsp; 

##  `📈 UML Diagram` 

![real-estate-uml-diagram](https://github.com/bertuginal/real-estate-final-project/assets/73167951/8d488562-b396-4a8b-b785-a82d253e7da8)

≫ There are 6 microservices that communicate with each other. These microservices are managed by the gateway. Instance management is provided with service discovery.

* Advert status changing is added to the queue by AdvertService and performed asynchronously by AdvertStatusService.
* The package identification process is added to the queue by PurchaseService and performed asynchronously by UserService.

&nbsp; 

## 💱 API Structure

### ⟶ [Postman API Documentation](https://documenter.getpostman.com/view/27348572/2sA3e498aj) ⟵

&nbsp; 

## 🌱 Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
⭐ Don't forget to give the project a star! Thanks again! ⭐

❶ Fork the Project

❷ Create your Feature Branch (`git checkout -b feature/real-estate-final-project`)

❸  Commit your Changes (`git commit -m 'Add some real-estate-final-project'`)

❹  Push to the Branch (`git push origin feature/real-estate-final-project`)

❺ Open a Pull Request
   

&nbsp; 

<!-- CONTACT -->
## ☎️ Contact

📧 E-mail: [bertuginal@yahoo.com](mailto:bertuginal@yahoo.com)

📱 Mobile Phone: [(+90) 507 038 33 23](mailto:+905070383323)

📋 Project Link: [https://github.com/bertuginal/real-estate-final-project](https://github.com/bertuginal/real-estate-final-project)



&nbsp; 
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
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png

[Java-logo]: https://img.shields.io/badge/java-000000?style=for-the-badge&logo=spring&logoColor=white
[Java-url]: https://www.java.com/tr/
[Spring-logo]: https://img.shields.io/badge/Spring_Boot-DD0031?style=for-the-badge&logo=springboot&logoColor=white
[Spring-url]: https://spring.io/
[Maven-logo]: https://img.shields.io/badge/maven-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[Maven-url]: https://maven.apache.org
[Mongodb-logo]: https://img.shields.io/badge/Mongo_DB_(Database)-4A4A55?style=for-the-badge&logo=mongodb&logoColor=FF3E00
[Mongodb-url]: https://www.mongodb.com
[Postman-logo]: https://img.shields.io/badge/Postman-FF2D20?style=for-the-badge&logo=postman&logoColor=white
[Postman-url]: https://swagger.io
[Swagger-logo]: https://img.shields.io/badge/Swagger-563D7C?style=for-the-badge&logo=swagger&logoColor=white
[Swagger-url]: https://swagger.io
[Rest-logo]: https://img.shields.io/badge/Rest_Template-563D7C?style=for-the-badge&logo=rest&logoColor=white
[Rest-url]: https://img.shields.io/badge/Rest_Template-563D7C?style=for-the-badge&logo=rest&logoColor=white
[React-logo]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vite-logo]: https://img.shields.io/badge/vite.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=906DFE
[Vite-url]: https://vitejs.dev
[Ant-Design-logo]: https://img.shields.io/badge/ant_design_(UI)-35495E?style=for-the-badge&logo=antdesign&logoColor=F7515E
[Ant-Design-url]: https://ant.design
[Postgresql-logo]: https://img.shields.io/badge/PostgreSQL-4A4A55?style=for-the-badge&logo=postgresql&logoColor=FF3E00
[Postgresql-url]: https://www.postgresql.org

[Vue-logo]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
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


