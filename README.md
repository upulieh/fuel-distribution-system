# fuel-distribution-system
A sample fuel distribution system for placing, managing and checking status on fuel orders placed by fuel stations.

4 services
1. Order service
2.  
3.
4. Dispatch service

UI
1. to submit an order request -> to order service endpoint1
2. 
3.
4.

Front end is created using React

Order Service
* endpoint1 - submit an order request and save it in the db

Allocation Service
* endpoint1 - check the stock and gives the confirmation
* emits an event based on the event

Schedule Service
* Receives the event emitted by allocation service and gives a date (attach the schedule date to the order)

Dispatch Service
* Make Allocation quantity to 0 and remove from the available quantity
