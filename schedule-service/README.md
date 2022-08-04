Handles Schedule related services

Acts as both a producer and consumer in Kafka
Is dependent on both order service and inventory service to receive an inventory request through kafka
If this service doesn't exist, the process stops at inventory service

### Running the app
1. run the Kafka server
2. Run this service