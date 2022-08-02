package com.cpc.scheduleservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document("schedule")
public class Schedule {
	
	@Id
	private String timeStamp;
	
	private String orderId;
	private LocalDateTime scheduledDate;
	
	public Schedule() {
		
	}
	
	@JsonProperty("timeStamp")
	public String getTimeStamp() {
		return timeStamp;
	}
	
	@JsonProperty("timeStamp")
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@JsonProperty("orderId")
	public String getOrderId() {
		return orderId;
	}
	
	@JsonProperty("orderId")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@JsonProperty("scheduledDate")
	public LocalDateTime getScheduledDate() {
		return scheduledDate;
	}
	
	@JsonProperty("scheduledDate")
	public void setScheduledDate(LocalDateTime scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	
	public Schedule(String timeStamp, String orderId, LocalDateTime scheduledDate) {
		this.timeStamp = timeStamp;
		this.orderId = orderId;
		this.scheduledDate = scheduledDate;
	}

	@Override
	public String toString() {
		return "Schedule [timeStamp=" + timeStamp + ", orderId=" + orderId + ", scheduledDate=" + scheduledDate + "]";
	}
}
