Inventory Management System API
1. Project Description

The Inventory Management System API is a backend application designed to track products in database . 
It provides full CRUD operations for managing products, along with logic for stock handling.

Features:

Product Management:

Create, Read, Update, Delete products.

Each product has a name, description, and stock_quantity.

Inventory Logic:

Stock quantity cannot go below zero.
Endpoints to increase or decrease stock.
Error handling for insufficient stock .
2. Setup & Run Locally
Prerequisites:
Java 11 or greater  , 
Maven 3.6+  ,
MySQL Database  ,  
Git

Steps:

Clone the repository:

git clone https://github.com/Snikita2003/Inventory_Management_System.git
cd Inventory_Management_System

Configure the database:
Update src/main/resources/application.properties with your database credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

Build and run the project:->

mvn clean install
mvn spring-boot:run


------------ Test API's---------------

1. get All Products
   http://localhost:8080/api/products    ->  GET Method
   
2. Save new Product
   http://localhost:8080/api/products     ->  POST Method
   Request Body->
   
{
    "prodName": "Laptop Pro",
    "description": "Upgraded Laptop",
    "stockQuantity": 15
}


3. Delete Product By Id
   http://localhost:8080/api/products/7    ->  DELETE Method

4. Update Product
   http://localhost:8080/api/products/7   -> PUT Method
   Request Body->
   {
    "prodName": "Laptop Pro",
    "description": "Upgraded Laptop",
    "stockQuantity": 15
   }

5. Increase Stock Quantity
 http://localhost:8080/api/products/7/increase?quantity=3   -> PUT Method

6. Decrease Stock Quantity

   http://localhost:8080/api/products/7/decrease?quantity=3     ->    PUT  Method

7.  Get all Low stocks quantity
   http://localhost:8080/api/products/getLowStocks    ->   GET Method

   

  
   
















