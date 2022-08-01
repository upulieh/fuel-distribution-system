package com.cpc.inventoryservice.service;

import com.cpc.inventoryservice.model.Quota;
import com.cpc.orderservice.models.Quantity;

public interface QuotaService {
	Quota submitQuotaRecord(Quota quota, Quantity quantityEnum, Integer latestAvailableQuantity);
	public void initializeInventory();
}
