import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

export type DispatchDocument = Dispatch & Document;

@Schema()
export class Dispatch {
  @Prop()
  orderId: string;
  @Prop()
  station: string;
  @Prop()
  amount: string;
}

export const DispatchSchema = SchemaFactory.createForClass(Dispatch);
