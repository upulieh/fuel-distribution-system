import { Controller } from '@nestjs/common';
import { DispatchService } from './dispatch.service';

@Controller('dispatch')
export class DispatchController {
  
    constructor(private dispatchService: DispatchService) {}

    
}
