import { DispatchCreateDto } from './dto/DispatchCreate.dto';
import {
  Body,
  Controller,
  Get,
  Param,
  Post,
  Query,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { DispatchService } from './service/dispatch.service';
import { Dispatch } from './schemas/Dispatch.schema';
import { MessagePattern, Payload } from '@nestjs/microservices';

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
    //next thing to do
    //on dispatch db
    return await this.dispatchService.setDispatchStatus(body.stationId);
  }

  //kafka listener
  @MessagePattern('dispatchCreateTopic') // topic name
  scheduleListener(@Payload() message) {
    message.scheduledDate = this.dispatchService.setDateValues(
      message.scheduledDate,
    );
    console.log('Creating a Dispatch for ' + JSON.stringify(message));
    return this.dispatchService.create(message);
  }
}
