import { Dispatch, DispatchSchema } from './schemas/Dispatch.schema';
import { MongooseModule } from '@nestjs/mongoose';
import { Module } from '@nestjs/common';
import { DispatchController } from './dispatch.controller';
import { DispatchService } from './service/dispatch.service';
import { DispatchRepository } from './repository/Dispatch.repository';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: Dispatch.name, schema: DispatchSchema },
    ]),
  ],
  controllers: [DispatchController],
  providers: [DispatchService, DispatchRepository],
})
export class DispatchModule {}
