package com.cpc.inventoryservice.service;

import com.cpc.inventoryservice.model.Quota;
import com.cpc.orderservice.models.FuelType;
import com.cpc.orderservice.models.Quantity;

public interface QuotaService {
	Quota submitQuotaRecord(String id, Quantity quantityEnum, FuelType fuelType);

	public void initializeInventory(int initialQuantityO92, int emergencyAllocationO92, int initialQuantityO95,
			int emergencyAllocationO95, int initialQuantityRD, int emergencyAllocationRD, int initialQuantitySD,
			int emergencyAllocationSD);

	Quota updateQuantities(String id);
}
