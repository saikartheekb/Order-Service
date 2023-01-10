# Order Service

### Overview

The application will allow customers to place orders, each of which can consist of multiple items or multiple pieces of the same item. The application will apply discounts to orders based on the number of orders placed by the customer and the time of year (during the annual Black Friday and Cyber Monday event).

### Requirements 

* Customers should be able to place an order by selecting items and specifying the quantity of each item.
* The application should track the number of orders placed by each customer and apply the appropriate discount based on the customer's order history. 
* Customers who have placed more than 10 orders are eligible for a 10% discount on all subsequent orders. Customers who have placed more than 20 orders are eligible for a 20% discount on all subsequent orders.
* During the annual Black Friday and Cyber Monday event, all customers are eligible for an additional 15% discount on their orders. The application should apply this discount to all orders placed during this time period.
* The application should display the total price of an order, including any applicable discounts.


### Constraints

* The application should be implemented using Spring Boot.
* The application should store customer and order data in a database.
* The application should provide a REST API for placing orders, querying order history and creating new offers.



### List of REST APIs

#### customer-controller

* GET /customers getCustomers
* POST /customers createCustomer
* GET /customers/{id} getCustomerById
* PUT /customers/{id} updateCustomer
* DELETE /customers/{id} deleteCustomer

#### item-controller

* GET /items getAllItems
* POST /items createItem
* GET /items/{id} getItemById
* PUT /items/{id} updateItem
* DELETE /items/{id} deleteItem


#### order-controller

* GET /orders getAllOrders
* POST /orders createOrder
* GET /orders/{id} getOrderById

#### offer-controller

* GET /offers getAllOffers
* POST /offers createOffer
* GET /offers/{id} getOfferById


### Scope for reporting APIs
Using the relationship between the entities in the application, following information can be queried by creating more reporting APIs

* Total revenue in a given time period
* Total number of customers and their order details utilised a given offer
* Total number of orders placed in a given time period
* All the orders placed by a customer