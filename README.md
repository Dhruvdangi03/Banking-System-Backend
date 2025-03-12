# Banking System Backend

This project is a Banking System Backend API developed using Spring Boot. It provides essential banking functionalities and services.

## Features

- **Account Management**: Create, update, and delete customer accounts.
- **Transaction Processing**: Handle deposits, withdrawals, and transfers between accounts.
- **Balance Inquiry**: Check current account balances.
- **Transaction History**: Retrieve detailed transaction histories for accounts.

## Technologies Used

- **Java**: Primary programming language.
- **Spring Boot**: Framework for building the backend API.
- **Maven**: Build and dependency management tool.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher.
- Maven 3.6.0 or higher.

## Getting Started

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Dhruvdangi03/Banking-System-Backend.git
   ```


2. **Navigate to the Project Directory**:

   ```bash
   cd Banking-System-Backend
   ```


3. **Build the Project**:

   ```bash
   mvn clean install
   ```


4. **Run the Application**:

   ```bash
   mvn spring-boot:run
   ```


   Alternatively, you can run the generated jar file:

   ```bash
   java -jar target/banking-system-backend-0.0.1-SNAPSHOT.jar
   ```


5. **Access the API**:

   The application will be accessible at `http://localhost:8080/`.

## API Endpoints

- **Account Management**:
  - `POST /accounts` - Create a new account.
  - `GET /accounts/{id}` - Retrieve account details.
  - `PUT /accounts/{id}` - Update account information.
  - `DELETE /accounts/{id}` - Delete an account.

- **Transactions**:
  - `POST /transactions/deposit` - Deposit funds into an account.
  - `POST /transactions/withdraw` - Withdraw funds from an account.
  - `POST /transactions/transfer` - Transfer funds between accounts.

- **Balance Inquiry**:
  - `GET /accounts/{id}/balance` - Retrieve the current balance of an account.

- **Transaction History**:
  - `GET /accounts/{id}/transactions` - Retrieve the transaction history for an account.

## Configuration

Configuration settings, such as database connections and server ports, can be adjusted in the `application.properties` file located in the `src/main/resources` directory.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
