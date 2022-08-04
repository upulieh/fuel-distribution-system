import { IsNotEmpty } from 'class-validator';

export class DispatchCreateDto {
  @IsNotEmpty()
  orderId: string;
  @IsNotEmpty()
  station: string;
  @IsNotEmpty()
  amount: string;
}
