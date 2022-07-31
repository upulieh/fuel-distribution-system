# fuel-distribution-system
A sample fuel distribution system for placing, managing and checking status on fuel orders placed by fuel stations.

## 4 services
1. Order service
2. Inventory service 
3. Schedule service
4. Dispatch service


## User Interfaces (using React)
1. To submit an order request -> to order service endpoint1
2. To check the status of a particular order by giving the reference id 
3. To submit the confimation of the order receival
4. To view all the arrived orders and their details


### Order Service
* Submit orders, save the details in the db

### Inventory Service
* Check the stock and gives the confirmation
* Emit an event based on the event

### Schedule Service
* Receive the event emitted by Inventory service and give a date (attach the schedule date to the order)

### Dispatch Service
* Make Allocation quantity to 0 and remove from the available quantity


