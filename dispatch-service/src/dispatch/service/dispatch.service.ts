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
    return await this.dispatchRepository.create(dispatchCreateDto);
  }
}
