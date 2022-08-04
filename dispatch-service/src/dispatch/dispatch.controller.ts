import { DispatchCreateDto } from './dto/DispatchCreate.dto';
import {
  Body,
  Controller,
  Get,
  Post,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { DispatchService } from './service/dispatch.service';
import { Dispatch } from './schemas/Dispatch.schema';
import { MessagePattern, Payload } from '@nestjs/microservices';

@Controller('dispatch')
export class DispatchController {
  constructor(private dispatchService: DispatchService) {}

  @Get()
  @UsePipes(ValidationPipe)
  async getAllDispatches(): Promise<Dispatch[]> {
    console.log('Fetching all Dispatches');
    return await this.dispatchService.getAll();
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
