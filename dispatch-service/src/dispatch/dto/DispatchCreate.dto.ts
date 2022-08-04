import { IsNotEmpty } from 'class-validator';

export class DispatchCreateDto {
  constructor() {}

  @IsNotEmpty()
  orderId: string;
  @IsNotEmpty()
  station: string;
  @IsNotEmpty()
  amount: string;
}
