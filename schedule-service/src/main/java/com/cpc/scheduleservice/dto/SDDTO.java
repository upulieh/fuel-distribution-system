package com.cpc.scheduleservice.dto;

import java.time.LocalDateTime;

public class SDDTO {

	private String id;
	private LocalDateTime scheduledDate;
	private String stationId;
	private int quantity;

	public LocalDateTime getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDateTime scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getId() {
		return id;
	}

	public void setOrderId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public SDDTO() {
	}

	public SDDTO(LocalDateTime scheduledDate, String id, String stationId, int quantity) {
		this.scheduledDate = scheduledDate;
		this.id = id;
		this.stationId = stationId;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "SDDTO [id=" + id + ", scheduledDate=" + scheduledDate + ", stationId=" + stationId + ", amount="
				+ quantity + "]";
	}
}
