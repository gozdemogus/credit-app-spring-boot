<a name="readme-top"></a>
# TÖDEB Java Spring Bootcamp Graduation Project :mortar_board:
The aim of this project is writing a restful application for a loan application system, which will take the loan application requests and return the loan result to the customer according to the relevant criteria, using the Spring Boot framework and optionally developing the frontend side.

![3](https://user-images.githubusercontent.com/107196935/184552418-d0d0d1ce-0f25-4cf0-bdb5-386d700044cb.jpeg)

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#scenario">Scenario</a>
    </li>
    <li>
      <a href="#back-end">Backend</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
                <li><a href="#getting-started">Getting Started</a></li>
      </ul>
    </li>
    <li><a href="#front-end">Frontend</a>
      <ul>
    <li><a href="#used">Used</a></li>
         <li><a href="#includes">Includes</a></li>
         <li><a href="#user-application">User Application</a></li>
         <li><a href="#admin-application">Admin Application</a></li>
      </ul>
    </li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

# Scenario
The user's identity number, name-surname, monthly income and telephone information are obtained, the credit score service is assumed to be written with the identity number before, and the credit score of the relevant person is obtained and the credit result is shown to the user according to the following rules.
(There may be two options as **Approval** or **Rejection**.)

**Rules:**
- New users can be defined in the system, existing customers can be updated or deleted. 
- If the credit score is below 500, the user will be rejected. (Credit result: Rejected) 
- If the credit score is between 500 points and 1000 points and monthly income is below 5000 TL, the user's loan application is approved and a limit of 10,000 TL is assigned to the user. (Credit Result: Approved)
- If the credit score is between 500 points and 1000 points and if the monthly income is above 5000 TL, the user's loan application is approved and a limit of 20,000 TL is assigned to the user. (Credit Result: Approved)
- If the credit score is equal to or above 1000 pointsthe user is assigned a limit equal to the result of the MONTHLY INCOME INFORMATION * CREDIT LIMIT MULTIPLIER. (Credit Result: Approved)
- As a result of the conclusion of the loan, the relevant application is recorded in the database. Afterwards, an informative SMS is sent to the relevant phone number and the approval status information (rejection or approval), limit information is returned from the endpoint.
- A completed loan application can only be queried with identification number

Note: The credit limit multiplier is **4** by default.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Backend
## Built With

- Java Spring Boot
- PostgreSQL
- Hibernate
- Maven
- MapStruct
- SLF4J
- JUnit
- Swagger
- JWT
- Mockito
- Postman
- Intellij Idea

## Getting Started

:books: [Swagger UI Documentation](http://localhost:2020/swagger-ui/index.html?configUrl=/openapi/swagger-config) :books:
![image](https://user-images.githubusercontent.com/107196935/184547558-f7130145-4083-4799-8868-1b613b2aa14c.png)

- Clone the repo:
```
$ git clone https://github.com/gozdemogus/credit-app-spring-boot.git
```
- Open project in your IDE.

- A database called ```gradProj``` must be defined in PostgreSQL.

- In ```application.properties``` file, configurations for PostgreSQL connection must be done.

- Project is available on port ```2020```.

- Notice that the project contains ```Spring Security``` feature. In order to access the endpoints firstly you need to visit:

| Type | Method |
| ------ | ------ |
| POST | http://localhost:2020/login |

Then login with the following credentials: 
```
{
    "username" : "user",
    "password" : "123"
}
```
You are free to reach other endpoints with bearer token.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Frontend

## Used
- Thymeleaf
- HTML
- CSS
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Includes

Two separate applications were developed for admin and applicants. 

- [USER](http://localhost:2020/credit/ui/main) is provided for applicants where they can apply for loans and inquire about their applications.

- [ADMIN](http://localhost:2020/credit-admin/ui/main) is designed for CRUD operations on user and credit records.
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## User Application
```Applicant``` application contains the following pages:

:desktop_computer: ```Main``` page that gives the user options to submit an application or query the outcome of an existing application. :point_down:
![image](https://user-images.githubusercontent.com/107196935/183636225-cade30b6-f189-4a0b-93f5-73a4ee7c5a75.png)
:desktop_computer: ```Apply``` page that allows user to fill the required information for credit inquiry and apply. :point_down:
![image](https://user-images.githubusercontent.com/107196935/184553073-84de652a-04f8-4256-9f1a-11f63e993a03.png)
![image](https://user-images.githubusercontent.com/107196935/183638031-16ffd6e6-e0d2-48f7-8654-ab09b52854c0.png)

In case of user with an existing credit application tries to apply again:
![image](https://user-images.githubusercontent.com/107196935/183637461-13fa4b94-15c4-4517-b039-b47dba73ba98.png)
:desktop_computer: ```Query``` page that provides user to get the result of his/her existing application. A completed loan application can only be queried with identification number :point_down:
![image](https://user-images.githubusercontent.com/107196935/183636989-31764f55-4705-4c24-b0c6-49924a734369.png)
![image](https://user-images.githubusercontent.com/107196935/184073790-e766b24b-5172-4d54-9a4f-6f9550fb06a9.png)
If there is not related application :point_down:
![image](https://user-images.githubusercontent.com/107196935/184073566-fceb71cb-f30d-4221-a5fa-bc8dca468893.png)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Admin Application
```Admin``` application contains following pages:

![image](https://user-images.githubusercontent.com/107196935/184548506-4585c1fe-0e65-4aa5-b561-54da36b74bac.png)
![image](https://user-images.githubusercontent.com/107196935/184324932-58ab9068-1ad8-4cf9-9e5e-2ede0b87e818.png)
![image](https://user-images.githubusercontent.com/107196935/184323072-414bfd8a-6814-4e53-a380-68165dd43c6c.png)
:desktop_computer: The administrator's intervention in the credit result can be disabled according to the scenario. It has been left active to show that insert, delete, update and view operations can be performed here.
![image](https://user-images.githubusercontent.com/107196935/184324970-410a2b89-a266-4de3-a6b6-cd2e712cd820.png)
:desktop_computer: Admin can also perform CRUD operations with user entity.
![image](https://user-images.githubusercontent.com/107196935/184327031-e9c807d3-c679-48f5-ab0d-c4ceca994114.png)
![image](https://user-images.githubusercontent.com/107196935/184338787-ca77f515-6616-4d73-80b9-a697ad9da10c.png)
![image](https://user-images.githubusercontent.com/107196935/184357676-ea737172-2cb5-4d9f-a600-5a1539df3455.png)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Acknowledgments

* [Baeldung](https://www.baeldung.com/rest-with-spring-series)
* [MapStruct](https://mapstruct.org/)
* [Thymeleaf](https://www.thymeleaf.org/documentation.html)
* [Spring.io](https://spring.io/guides/gs/maven/)
* [w3schools](https://www.w3schools.com/)
* [Stackoverflow](https://stackoverflow.com/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Contact

Gözdem Oğuş - [@Linkedin](https://www.linkedin.com/in/gozdemogus/) 

<p align="right">(<a href="#readme-top">back to top</a>)</p>
