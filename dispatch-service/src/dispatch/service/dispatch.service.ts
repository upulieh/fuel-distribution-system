import { Dispatch } from './../schemas/Dispatch.schema';
import { DispatchCreateDto } from './../dto/DispatchCreate.dto';
import { DispatchRepository } from './../repository/Dispatch.repository';
import { Injectable } from '@nestjs/common';

@Injectable()
export class DispatchService {
  constructor(private dispatchRepository: DispatchRepository) {}

  async getAll(): Promise<Dispatch[]> {
    return await this.dispatchRepository.findAll();
  }

  async create(dispatchCreateDto: DispatchCreateDto): Promise<Dispatch> {
    const dispatch = new Dispatch();
    dispatch.id = dispatchCreateDto.id;
    dispatch.stationId = dispatchCreateDto.stationId;
    dispatch.quantity = dispatchCreateDto.quantity;
    dispatch.scheduledDate = dispatchCreateDto.scheduledDate;

    return await this.dispatchRepository.create(dispatch);
  }

  async setDispatchStatus(id: string): Promise<Dispatch> {
    return await this.dispatchRepository.setDispatchStatus(id);
  }

  setDateValues(d: number[]): Date {
    //[ 2022, 8, 4, 19, 25, 21, 764327200 ]
    let date: Date = new Date();
    date.setFullYear(d[0]);
    date.setMonth(d[1]);
    date.setDate(d[2]);
    date.setHours(d[3]);
    date.setMinutes(d[4] + 1);
    date.setSeconds(d[5]);
    date.setMilliseconds(d[6]);
    return date;
  }
}
