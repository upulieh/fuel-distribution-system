package com.cpc.scheduleservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cpc.scheduleservice.model.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, String>{

}
