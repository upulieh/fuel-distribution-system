import { IsNotEmpty } from 'class-validator';

export class DispatchCreateDto {
  constructor() {}

  @IsNotEmpty()
  orderId: string;
  @IsNotEmpty()
  stationId: string;
  @IsNotEmpty()
  quantity: string;
  @IsNotEmpty()
  scheduledDate: Date;
}
