import { DispatchCreateDto } from './dto/DispatchCreate.dto';
import {
  Body,
  Controller,
  Get,
  Injectable,
  Param,
  Inject,
  Post,
  Query,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { DispatchService } from './service/dispatch.service';
import { Dispatch } from './schemas/Dispatch.schema';
import {
  Client,
  ClientKafka,
  MessagePattern,
  Payload,
  ClientProxy,
  Transport,
} from '@nestjs/microservices';

const kafkaHost = process.env.KAFKA_HOST || 'localhost';
const kafkaPort = process.env.KAFKA_PORT || '9092';

// @Injectable()
@Controller('dispatch')
export class DispatchController {
  constructor(private dispatchService: DispatchService) {}

  //get - http://localhost:8194/dispatch
  @Get()
  @UsePipes(ValidationPipe)
  async getAllDispatches(): Promise<Dispatch[]> {
    console.log('Fetching all Dispatches');
    return await this.dispatchService.getAll();
  }

  //post - http://localhost:8194/dispatch with JSON payload with id field
  @Post()
  async setOrderStatus(@Body() body) {
    //to kafka (for order service)
    //body.scheduledDate, body.quantity, body.stationId, body.id,
    this.updateOrderStatus(body.id);
    //on dispatch db
    return await this.dispatchService.setDispatchStatus(body.id);
  }

  //kafka consumer
  @MessagePattern('dispatchCreateTopic') // topic name
  scheduleListener(@Payload() message) {
    message.scheduledDate = this.dispatchService.setDateValues(
      message.scheduledDate,
    );
    console.log('Creating a Dispatch for ' + JSON.stringify(message));
    return this.dispatchService.create(message);
  }

  //kafka producer
  @Client({
    transport: Transport.KAFKA,
    options: {
      client: {
        clientId: 'dispatch-service', //what's the deal with this?
        brokers: [`${kafkaHost}:${kafkaPort}`],
      },
      consumer: {
        groupId: 'cpc',
      },
    },
  })
  client: ClientKafka;

  //kafka producer config
  async onModuleInit() {
    // Need to subscribe to topic
    // so that we can get the response from kafka microservice
    this.client.subscribeToResponseOf('dispatchSubmitTopic');
    await this.client.connect();
  }

  //sending to kafka
  // @Get()
  async updateOrderStatus(id) {
    this.client.emit('dispatchSubmitTopic', id);
  }
}
