package com.cpc.inventoryservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document("quota") // mongodb collection name
public class Quota {

	@Id
	private String timeStamp;

	private String orderId;
//	OCTANE92
	private Integer allocatedQuotaSumO92;
	private Integer availableQuantityO92;
//	OCTANE95
	private Integer allocatedQuotaSumO95;
	private Integer availableQuantityO95;
//	REGULAR_DIESEL
	private Integer allocatedQuotaSumRD;
	private Integer availableQuantityRD;
//	SUPER_DIESEL
	private Integer allocatedQuotaSumSD;
	private Integer availableQuantitySD;

	public Quota() {
	}

	public Quota(String timeStamp, String orderId) {
		this.timeStamp = timeStamp; // when the quota record was created
		this.orderId = orderId; // id of the order associated with it
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

	// O92
	@JsonProperty("allocatedQuotaSumO92")
	public Integer getAllocatedQuotaSumo92() {
		return allocatedQuotaSumO92;
	}

	@JsonProperty("allocatedQuotaSumO92")
	public void setAllocatedQuotaSumO92(Integer allocatedQuotaSumO92) {
		this.allocatedQuotaSumO92 = allocatedQuotaSumO92;
	}

	@JsonProperty("availableQuantityO92")
	public Integer getAvailableQuantityO92() {
		return availableQuantityO92;
	}

	@JsonProperty("availableQuantityO92")
	public void setAvailableQuantityO92(Integer availableQuantityO92) {
		this.availableQuantityO92 = availableQuantityO92;
	}

	// O95
	@JsonProperty("allocatedQuotaSumO95")
	public Integer getAllocatedQuotaSumO95() {
		return allocatedQuotaSumO95;
	}

	@JsonProperty("allocatedQuotaSumO95")
	public void setAllocatedQuotaSumO95(Integer allocatedQuotaSumO95) {
		this.allocatedQuotaSumO95 = allocatedQuotaSumO95;
	}

	@JsonProperty("availableQuantityO95")
	public Integer getAvailableQuantityO95() {
		return availableQuantityO95;
	}

	@JsonProperty("availableQuantityO95")
	public void setAvailableQuantityO95(Integer availableQuantityO95) {
		this.availableQuantityO95 = availableQuantityO95;
	}

	// RD
	@JsonProperty("allocatedQuotaSumRD")
	public Integer getAllocatedQuotaSumRD() {
		return allocatedQuotaSumRD;
	}

	@JsonProperty("allocatedQuotaSumRD")
	public void setAllocatedQuotaSumRD(Integer allocatedQuotaSumRD) {
		this.allocatedQuotaSumRD = allocatedQuotaSumRD;
	}

	@JsonProperty("availableQuantityRD")
	public Integer getAvailableQuantityRD() {
		return availableQuantityRD;
	}

	@JsonProperty("availableQuantityRD")
	public void setAvailableQuantityRD(Integer availableQuantityRD) {
		this.availableQuantityRD = availableQuantityRD;
	}

	// SD
	@JsonProperty("allocatedQuotaSumSD")
	public Integer getAllocatedQuotaSumSD() {
		return allocatedQuotaSumSD;
	}

	@JsonProperty("allocatedQuotaSumSD")
	public void setAllocatedQuotaSumSD(Integer allocatedQuotaSumSD) {
		this.allocatedQuotaSumSD = allocatedQuotaSumSD;
	}

	@JsonProperty("availableQuantitySD")
	public Integer getAvailableQuantitySD() {
		return availableQuantitySD;
	}

	@JsonProperty("availableQuantitySD")
	public void setAvailableQuantitySD(Integer availableQuantitySD) {
		this.availableQuantitySD = availableQuantitySD;
	}

	public Quota(String timeStamp, String orderId, Integer allocatedQuotaSumO92, Integer availableQuantityO92,
			Integer allocatedQuotaSumO95, Integer availableQuantityO95, Integer allocatedQuotaSumRD,
			Integer availableQuantityRD, Integer allocatedQuotaSumSD, Integer availableQuantitySD) {
		this.timeStamp = timeStamp;
		this.orderId = orderId;
		this.allocatedQuotaSumO92 = allocatedQuotaSumO92;
		this.availableQuantityO92 = availableQuantityO92;
		this.allocatedQuotaSumO95 = allocatedQuotaSumO95;
		this.availableQuantityO95 = availableQuantityO95;
		this.allocatedQuotaSumRD = allocatedQuotaSumRD;
		this.availableQuantityRD = availableQuantityRD;
		this.allocatedQuotaSumSD = allocatedQuotaSumSD;
		this.availableQuantitySD = availableQuantitySD;
	}

}
