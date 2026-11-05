# ICT E-Commerce Backend API

A Spring Boot REST API for an E-Commerce Management System.

This project was developed as a final project for ICT using Spring Boot, Spring Security, JWT Authentication, PostgreSQL, JPA/Hibernate, and Maven.

---

# Features

## Authentication

- User Registration
- User Login
- JWT Token Generation
- JWT Token Validation
- BCrypt Password Encryption
- Current User Information

## Categories

- Create Category
- Get All Categories
- Get Category By Id
- Update Category
- Delete Category

## Products

- Create Product
- Get All Products
- Get Product By Id
- Update Product
- Delete Product

## Shopping Cart

- Add Product To Cart
- View Cart
- Update Cart Item Quantity
- Delete Cart Item

## Orders

- Create Order From Cart
- View My Orders
- View Order Details
- Order Status Management

## Dashboard

- Total Users
- Total Products
- Total Orders
- Pending Orders
- Total Revenue

## Exception Handling

Global exception handling has been implemented for:

- UserNotFoundException
- ProductNotFoundException
- CartNotFoundException
- CartItemNotFoundException
- OrderNotFoundException
- EmptyOrderException
- InvalidOrderStatusTransitionException
- UnauthorizedOrderAccessException
- UnauthorizedCartAccessException
- InsufficientStockException

---

# Technologies

- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok
- Postman

---

# Database Schema

## Users

| Field | Type |
|---------|---------|
| id | Long |
| full_name | String |
| email | String |
| password | String |
| enabled | Boolean |

## Categories

| Field | Type |
|---------|---------|
| id | Long |
| name | String |
| description | String |

## Products

| Field | Type |
|---------|---------|
| id | Long |
| name | String |
| description | String |
| price | BigDecimal |
| stock_quantity | Integer |
| category_id | Long |

## Carts

| Field | Type |
|---------|---------|
| id | Long |
| user_id | Long |

## Cart Items

| Field | Type |
|---------|---------|
| id | Long |
| cart_id | Long |
| product_id | Long |
| quantity | Integer |

## Orders

| Field | Type |
|---------|---------|
| id | Long |
| order_number | String |
| user_id | Long |
| total_amount | Double |
| status | OrderStatusEnum |
| created_at | LocalDateTime |

## Order Items

| Field | Type |
|---------|---------|
| id | Long |
| order_id | Long |
| product_id | Long |
| product_name | String |
| price | BigDecimal |
| quantity | Integer |

---

# Relationships

- One Category has many Products
- One User has one Cart
- One Cart has many Cart Items
- One User has many Orders
- One Order has many Order Items
- One Product can appear in many Cart Items
- One Product can appear in many Order Items

The schema contains users, categories, products, carts, cart_items, orders, and order_items. 【1-a01cbc】

---

# Business Rules

- Email addresses must be unique.
- Product price must be greater than zero.
- Product stock cannot be negative.
- Customers cannot order more items than available stock.
- Only Admin can manage products and categories.
- Customers can access only their own cart and orders.
- Order total is calculated by the backend.
- Product stock is automatically updated after checkout.
- Passwords are encrypted using BCrypt.
- Invalid requests return meaningful error messages.

---

# Authentication Flow

## Register

```http
POST /api/v1/auth/register
```

## Login

```http
POST /api/v1/auth/login
```

Returns JWT Token.

## Authorization Header

```http
Authorization: Bearer <JWT_TOKEN>
```

Authentication uses Spring Security AuthenticationManager, UserDetailsService, PasswordEncoder and JWT. 【2-88f22f】【3-c5a220】

---

# API Endpoints

## Authentication

```http
POST /api/v1/auth/register
POST /api/v1/auth/login
GET  /api/v1/auth/me
```

---

## Categories

```http
GET    /api/v1/categories
GET    /api/v1/categories/{id}

POST   /api/v1/admin/categories
PUT    /api/v1/admin/categories/{id}
DELETE /api/v1/admin/categories/{id}
```

---

## Products

```http
GET    /api/v1/products
GET    /api/v1/products/{id}

POST   /api/v1/admin/products
PUT    /api/v1/admin/products/{id}
DELETE /api/v1/admin/products/{id}
```

---

## Shopping Cart

```http
GET    /api/v1/cart
POST   /api/v1/cart/items
PUT    /api/v1/cart/items/{itemId}
DELETE /api/v1/cart/items/{itemId}
```

These shopping cart endpoints were implemented according to the project requirements. 【4-daef3a】

---

## Orders

```http
POST /api/v1/orders
GET  /api/v1/orders/my-orders
GET  /api/v1/orders/{id}
```

---

## Admin Orders

```http
GET   /api/v1/admin/orders
PATCH /api/v1/admin/orders/{id}/status
```

---

## Dashboard

```http
GET /api/v1/admin/dashboard
```

---

# Order Status Lifecycle

```text
PENDING
    ↓
CONFIRMED
    ↓
COMPLETED
```

or

```text
PENDING
    ↓
CANCELLED
```

or

```text
CONFIRMED
    ↓
CANCELLED
```

Invalid transitions are rejected.

---

# Setup Instructions

## Clone Repository

```bash
git clone https://github.com/your-username/your-repository.git
```

---

## Create PostgreSQL Database

```sql
CREATE DATABASE ecommerce_db;
```

---

## Configure application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_db
    username: postgres
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Project

```bash
mvn spring-boot:run
```

or

Run the main Spring Boot application class directly from your IDE.

---

# Postman Collection

The project includes a Postman Collection containing:

- Authentication APIs
- Category APIs
- Product APIs
- Cart APIs
- Order APIs
- Dashboard API

Postman was used to test all endpoints during development. 【5-01f040】【6-01fc2e】

---

# Project Structure

```text
config/
controller/
dto/
entity/
exception/
mapper/
repository/
security/
service/
serviceimpl/
```

The project contains dedicated controller, service, repository, mapper and exception layers. 【7-8293d7】

---

# Author: Ann Mengkorng

ICT Final Project

Spring Boot E-Commerce Management System
