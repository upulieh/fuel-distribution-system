import { Module } from '@nestjs/common';
import { DispatchModule } from './dispatch/dispatch.module';

@Module({
  imports: [DispatchModule],
  controllers: [],
  providers: [],
})
export class AppModule {}
