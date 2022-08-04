import { NestFactory } from '@nestjs/core';
import { Transport, MicroserviceOptions } from '@nestjs/microservices'; //for specifying kafka configs
import { appendFile } from 'fs';
import { AppModule } from './app.module';

//starting point of the server
async function bootstrap() {
  //kafka configs
  const kafkaHost = process.env.KAFKA_HOST || 'localhost';
  const kafkaPort = process.env.KAFKA_PORT || '9092'; //port to listen for kafka msgs to

  // const app = await NestFactory.createMicroservice<MicroserviceOptions>(
  //   AppModule,
  //   {
  //     transport: Transport.KAFKA,
  //     options: {
  //       client: {
  //         brokers: [`${kafkaHost}:${kafkaPort}`],
  //       },
  //       consumer: {
  //         groupId: 'cpc',
  //       },
  //     },
  //   },
  // );

  // await app.listen(3000);

  const app = await NestFactory.create(AppModule);
  app.connectMicroservice({
    transport: Transport.KAFKA,
    options: {
      client: {
        brokers: [`${kafkaHost}:${kafkaPort}`],
      },
      consumer: {
        groupId: 'cpc',
      },
    },
  });
  await app.startAllMicroservices();
  await app.listen(8194, () => 'dispatch-service started.....'); //port to make the REST request
}
bootstrap();
