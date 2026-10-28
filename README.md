
# Digital Banking System

A Java-based Digital Banking System built using Spring Boot and a microservices architecture. The project separates banking functionalities into independent services to improve modularity and maintainability.

## Project Overview

The system is organized into multiple services that handle different banking operations.

## Microservices

- **API Gateway** – Entry point for routing requests to backend services.
- **Customer Service** – Manages customer information and customer-related operations.
- **Account Service** – Handles account-related operations.
- **Transaction Service** – Manages banking transactions.
- **Loan Service** – Handles loan-related operations.

## Technologies Used

- Java
- Spring Boot
- Spring Cloud Gateway
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Jakarta Bean Validation
- Lombok
- Postman

## Customer Service Features

The Customer Service provides REST APIs for managing customer records.

- Create a customer
- Retrieve all customers
- Retrieve a customer by ID
- Update customer details
- Delete a customer
- Validate customer input
- Handle customer-not-found errors

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/customers` | Create a customer |
| GET | `/customers` | Retrieve all customers |
| GET | `/customers/{id}` | Retrieve a customer by ID |
| PUT | `/customers/{id}` | Update a customer |
| DELETE | `/customers/{id}` | Delete a customer |

## Validation and Error Handling

The Customer Service uses validation to check customer details, including:

- Customer name must not be blank.
- Email must have a valid format.
- Mobile number must contain 10 digits.
- Aadhaar number must contain 12 digits.
- Customer-not-found errors return HTTP 404.
- Invalid request data returns HTTP 400.

## API Testing

The REST API endpoints were tested using Postman.

**Add your existing Postman screenshots below this heading.**

## Project Structure

```text
Digital_Banking_System/
├── API-Gateway/
├── account-service/
├── customer-service/
├── loan-service/
└── transaction-service/
```

## Future Improvements

- Add authentication and authorization.
- Improve centralized exception handling.
- Add automated unit and integration tests.
- Document API requests and responses.
- Improve service configuration and deployment.

## Author

**Mohamed Shiaf N**

B.Tech – Artificial Intelligence and Machine Learning







## API Testing



### Customer Service

<img width="1917" height="1021" alt="Screenshot 2026-07-23 191203" src="https://github.com/user-attachments/assets/c2d24629-e641-4ecd-a920-8b169f7f0646" />

<img width="1917" height="1015" alt="Screenshot 2026-07-23 191232" src="https://github.com/user-attachments/assets/cd8c62b8-4fb2-4906-b34c-ec9fecb462c4" />

<img width="1917" height="1025" alt="Screenshot 2026-07-23 191418" src="https://github.com/user-attachments/assets/b27d861d-4be4-41c0-a4b6-4ade0702d4fe" />

<img width="1917" height="1015" alt="Screenshot 2026-07-23 191456" src="https://github.com/user-attachments/assets/baa54794-c032-4bc7-af8c-b2e5faed4587" />

<img width="1916" height="1025" alt="Screenshot 2026-07-23 191511" src="https://github.com/user-attachments/assets/20ce9a36-7a3e-465c-8b95-b6dd3e42fc2e" />

<img width="1917" height="1015" alt="Screenshot 2026-07-23 191636" src="https://github.com/user-attachments/assets/be4ab814-4837-4f5c-8b51-1607ed7d8358" />

<img width="1917" height="1023" alt="Screenshot 2026-07-23 193928" src="https://github.com/user-attachments/assets/2c0e0b73-35b2-4083-b2f2-ef68829d5624" />

<img width="1917" height="1026" alt="Screenshot 2026-07-23 193940" src="https://github.com/user-attachments/assets/d835ed0d-e080-4465-a20e-b658a09a04ea" />





### ACCOUNT SERVICE:


<img width="1917" height="1018" alt="Screenshot 2026-07-23 191808" src="https://github.com/user-attachments/assets/17cbfd0b-e7d2-48e3-b6dd-5419b57078ce" />

<img width="1917" height="1018" alt="Screenshot 2026-07-23 191856" src="https://github.com/user-attachments/assets/1316487e-d458-46de-9d04-a20da6fab7c0" />

<img width="1917" height="1017" alt="Screenshot 2026-07-23 191921" src="https://github.com/user-attachments/assets/79f9e671-423b-428a-a0f5-35e92a82d642" />

<img width="1917" height="1018" alt="Screenshot 2026-07-23 192041" src="https://github.com/user-attachments/assets/cbbc969d-5b05-4520-b934-8343bbf417fe" />

<img width="1912" height="1011" alt="Screenshot 2026-07-23 192056" src="https://github.com/user-attachments/assets/04c28561-c714-4fef-8a81-d49879ff8724" />

<img width="1917" height="1020" alt="Screenshot 2026-07-23 192146" src="https://github.com/user-attachments/assets/a753ea32-0b06-400e-8420-c9aa490f546a" />

<img width="1917" height="1021" alt="Screenshot 2026-07-23 192311" src="https://github.com/user-attachments/assets/7df284ec-93ce-4a5e-93a5-9eaa55c7d4c9" />

<img width="1917" height="1022" alt="Screenshot 2026-07-23 194115" src="https://github.com/user-attachments/assets/e108eff6-d1c9-4123-8616-e7c389f05ddb" />

<img width="1917" height="1020" alt="Screenshot 2026-07-23 194141" src="https://github.com/user-attachments/assets/9cdcb93d-a2ac-4b02-8931-a216215fac21" />





### TRANSACTION SERVICE:


<img width="1917" height="1022" alt="Screenshot 2026-07-23 192405" src="https://github.com/user-attachments/assets/addf97a4-d1f0-46b7-b054-a0810c9e39ed" />

<img width="1917" height="1020" alt="Screenshot 2026-07-23 192429" src="https://github.com/user-attachments/assets/5b48e60c-b63f-4db8-af60-d82884a13605" />

<img width="1917" height="1020" alt="Screenshot 2026-07-23 192505" src="https://github.com/user-attachments/assets/91b1eea7-8d42-452f-9621-703a8057cd12" />

<img width="1917" height="1018" alt="Screenshot 2026-07-23 192547" src="https://github.com/user-attachments/assets/89ac6494-7398-40c6-9e88-9302f67107fb" />





### LOAN SERVICE:


<img width="1917" height="1022" alt="Screenshot 2026-07-23 192635" src="https://github.com/user-attachments/assets/65338528-ba27-4496-93ca-9aa5b89678b9" />

<img width="1916" height="1017" alt="Screenshot 2026-07-23 192702" src="https://github.com/user-attachments/assets/ea63d05a-32f0-449f-9b79-fea90458cafd" />

<img width="1916" height="1025" alt="Screenshot 2026-07-23 192742" src="https://github.com/user-attachments/assets/104c5669-2a2c-4167-b2cf-0209a374cb37" />

<img width="1917" height="1025" alt="Screenshot 2026-07-23 192849" src="https://github.com/user-attachments/assets/1e655190-1b9f-45e5-8f97-6a26736de8ea" />











