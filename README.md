# fuel-distribution-system
A sample fuel distribution system for placing, managing and checking status on fuel orders placed by fuel stations.

## 4 services
1. Order service
2. Inventory service 
3. Schedule service
4. Dispatch service


## User Interfaces (cpc-client)
* A React frontend project
1. To submit an order request -> to order service endpoint1
2. To check the status of a particular order by giving the reference id 
3. To submit the confimation of the order receival
4. To view all the arrived orders and their details

Sample Order payload
{
    "id": "KadaK165945", //custom generated reference Id for each order
    "stationId": "Kadawatha_K", //station which submitted the order
    "fuelType": "REGULAR_DIESEL", //the type of fuel ordered
    "quantity": "L13_200", //amount in litres
    "reserved": true, //if the reservation was complete
    "reservedTime": "2022-08-02T20:45:13.3983277", //datetime which the reservation occurred
    "allocated": false, //if the allocation was complete
    "allocatedTime": null, ////datetime which the allocation occurred
    "scheduled": false, //if the scheduling was complete
    "scheduledTime": null, ////datetime when the dispatch message will initialize
    "dispatched": false, //if the dispatching was complete
    "dispatchedTime": null, ////datetime when the dispatch was actually done (clicking the button)
    "delivered": false, //if the delivery was complete
    "deliveredTime": null ////datetime which the delivery was made to the station
}

### Order Service
* Submit orders, save the details in the db

### Inventory Service
* Check the stock and gives the confirmation
* Emit an event based on the event

### Schedule Service
* Receive the event emitted by Inventory service and give a date (attach the schedule date to the order)

### Dispatch Service
* Make Allocation quantity to 0 and remove from the available quantity


