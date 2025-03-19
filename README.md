# Inventory Manager - Microservices Architecture

## Overview
The **Inventory Manager** application follows a **microservices architecture** and consists of multiple services, each handling a specific function. The services communicate with each other through an API Gateway and are registered with a Discovery Service to support load balancing and scaling.

## Architecture Components

### 1. User Service
Manages user authentication and account details.

#### Endpoints:
- **User Registration (`/register`)** - Registers a new user with a hashed password.
- **User Login (`/login`)** - Authenticates users and generates a JWT token.
- **Fetch User Details (`/user-details`)** - Retrieves user information for authenticated users.
- **Update User (`/update-user`)** - Allows updates to username, password, display name, and email (ensuring uniqueness).
- **Forgot Password (`/forgot-password`)** - Generates and sends an OTP for password reset.
- **Reset Password (`/reset-password`)** - Validates OTP and resets the user’s password.

#### Security:
- Uses **JWT** for authentication.
- JWT filter is added to the security filter chain.
- Custom exceptions and a global exception handler for error handling.

### 2. Inventory Service
Handles inventory-related operations with **JWT token authentication**.

#### Endpoints:
- **Add Inventory (`/add-inventory`)** - Adds a new inventory item.
- **Update Inventory (`/update-inventory`)** - Updates inventory details.
- **Delete Inventory (`/delete-inventory?inventoryId={id}`)** - Deletes an inventory item.
- **Search Inventory (`/search-inventory?page={page}&size={size}&itemName={name}&itemType={type}&brands={brandIds}`)**
    - Implements a custom query method in the repository interface.
    - Allows filtering based on **name, type, and brands**.
- **Get All Inventories (`/get-all-inventories?page={page}&size={size}`)** - Retrieves paginated inventory data.

### 3. Database Design
The database follows **normalization principles** with multiple related tables. Key relationships:
- **User Table** - Stores user details.
- **Inventory Table** - Stores inventory details.
- **Brand Table** - Stores brand details related to inventory.
- **Item Type Table** - Categorizes inventory items.

### 4. API Gateway Service
Acts as a **single entry point** for all microservices, forwarding requests to the appropriate service.

### 5. Discovery Service
Uses **Eureka Server** to register and manage multiple instances of each microservice.

## Security
- **JWT Authentication** ensures secure communication.
- **Global Exception Handling** improves error handling.
- **Role-Based Access Control** can be implemented for enhanced security.

