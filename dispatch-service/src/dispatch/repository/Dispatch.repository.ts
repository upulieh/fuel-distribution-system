import { Dispatch, DispatchDocument } from './../schemas/Dispatch.schema';
import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { DispatchCreateDto } from '../dto/DispatchCreate.dto';

@Injectable()
export class DispatchRepository {
  constructor(
    @InjectModel(Dispatch.name) private dispatchModel: Model<DispatchDocument>,
  ) {}

  //create a dispatch record on db
  async create(dispatchCreateDto: DispatchCreateDto): Promise<Dispatch> {
    let newDispatch = new this.dispatchModel(dispatchCreateDto);
    return await newDispatch.save();
  }

  async findAll(): Promise<Dispatch[]> {
    return await this.dispatchModel.find();
  }
}
