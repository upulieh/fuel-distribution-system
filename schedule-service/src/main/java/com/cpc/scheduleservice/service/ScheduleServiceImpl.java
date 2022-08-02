package com.cpc.scheduleservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.scheduleservice.model.Schedule;
import com.cpc.scheduleservice.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	public Schedule submitScheduleRecord(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}
}
