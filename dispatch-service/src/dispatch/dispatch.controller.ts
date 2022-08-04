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

@Controller('dispatch')
export class DispatchController {
  constructor(private dispatchService: DispatchService) {}

  @Get()
  @UsePipes(ValidationPipe)
  async getAllDispatches(): Promise<Dispatch[]> {
    console.log('Creating a Dispatch');
    return await this.dispatchService.getAll();
  }

  @Post()
  @UsePipes(ValidationPipe)
  async createDispatch(
    @Body() dispatchCreateDto: DispatchCreateDto,
  ): Promise<Dispatch> {
    return this.dispatchService.create(dispatchCreateDto);
  }
}
