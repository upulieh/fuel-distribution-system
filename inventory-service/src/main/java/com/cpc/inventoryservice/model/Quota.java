package com.cpc.inventoryservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document("quota") // mongodb collection name
public class Quota {

	@Id
	private String timeStamp;

	private String orderId;
	private Integer allocatedQuotaSum;
	private Integer availableQuantity;

	public Quota() {
	}

	public Quota(String timeStamp, String orderId) {
		this.timeStamp = timeStamp; //when the quota record was created
		this.orderId = orderId; //id of the order associated with it
	}

	// id
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

	@JsonProperty("allocatedQuotaSum")
	public Integer getAllocatedQuotaSum() {
		return allocatedQuotaSum;
	}

	@JsonProperty("allocatedQuotaSum")
	public void setAllocatedQuotaSum(Integer allocatedQuotaSum) {
		this.allocatedQuotaSum = allocatedQuotaSum;
	}

	@JsonProperty("availableQuantity")
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	@JsonProperty("availableQuantity")
	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	@Override
	public String toString() {
		return "Quota [timeStamp=" + timeStamp + ", allocatedQuotaSum=" + allocatedQuotaSum + ", availableQuotaSum="
				+ availableQuantity + "]";
	}
}
