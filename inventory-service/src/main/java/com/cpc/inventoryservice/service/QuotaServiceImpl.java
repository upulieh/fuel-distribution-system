package com.cpc.inventoryservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.inventoryservice.InventoryServiceApplication;
import com.cpc.inventoryservice.model.Quota;
import com.cpc.inventoryservice.repository.QuotaRepository;
import com.cpc.orderservice.models.FuelType;
import com.cpc.orderservice.models.Quantity;

@Service
public class QuotaServiceImpl implements QuotaService {

	@Autowired
	QuotaRepository quotaRepository;

	public Quota submitQuotaRecord(Quota newQuota, Quantity quantityEnum, FuelType fuelType) {

		//issue in this method, not checking null for all values before saving in the inventory
		// set quota's quantity
		Integer quantityInL = 0;

		switch (quantityEnum) {
		case L3_300:
			quantityInL = 3300;
			break;
		case L6_600:
			quantityInL = 6600;
			break;
		case L13_200:
			quantityInL = 13200;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + quantityEnum);
		}

		// return the final record's value
		Quota previousQuota = quotaRepository.retrieveFinalQuota();

		// calculate and assign the quota values according to the fuel type
		Integer allocatedSum = 0;
		Integer availableQuantity = 0;

		switch (fuelType) {
		case OCTANE92: {
			allocatedSum = previousQuota.getAllocatedQuotaSumo92();
			availableQuantity = previousQuota.getAvailableQuantityO92();

			if (availableQuantity < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				newQuota.setAllocatedQuotaSumO92(allocatedSum + quantityInL);
				newQuota.setAvailableQuantityO92(availableQuantity - quantityInL);

				InventoryServiceApplication.logger.info("inventory-service : Quota of Octane 92 changed from "
						+ availableQuantity + " to " + (availableQuantity - quantityInL));

				return quotaRepository.save(newQuota);
			}
		}
		case OCTANE95: {
			allocatedSum = previousQuota.getAllocatedQuotaSumO95();
			availableQuantity = previousQuota.getAvailableQuantityO95();

			if (availableQuantity < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				newQuota.setAllocatedQuotaSumO95(allocatedSum + quantityInL);
				newQuota.setAvailableQuantityO95(availableQuantity - quantityInL);

				InventoryServiceApplication.logger.info("inventory-service : Quota of Octane 92 changed from "
						+ availableQuantity + " to " + (availableQuantity - quantityInL));

				return quotaRepository.save(newQuota);
			}
		}
		case REGULAR_DIESEL: {
			allocatedSum = previousQuota.getAllocatedQuotaSumRD();
			availableQuantity = previousQuota.getAvailableQuantityRD();

			if (availableQuantity < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				newQuota.setAllocatedQuotaSumRD(allocatedSum + quantityInL);
				newQuota.setAvailableQuantityRD(availableQuantity - quantityInL);

				InventoryServiceApplication.logger.info("inventory-service : Quota of Regular Diesel changed from "
						+ availableQuantity + " to " + (availableQuantity - quantityInL));

				return quotaRepository.save(newQuota);
			}
		}
		case SUPER_DIESEL: {
			allocatedSum = previousQuota.getAllocatedQuotaSumSD();
			availableQuantity = previousQuota.getAvailableQuantitySD();

			if (availableQuantity < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				newQuota.setAllocatedQuotaSumSD(allocatedSum + quantityInL);
				newQuota.setAvailableQuantitySD(availableQuantity - quantityInL);

				InventoryServiceApplication.logger.info("inventory-service : Quota of Super Diesel changed from "
						+ availableQuantity + " to " + (availableQuantity - quantityInL));

				return quotaRepository.save(newQuota);
			}
		}
		default: {
			return null;
		}
		}
	}

	public void initializeInventory(int initialQuantityO92, int emergencyAllocationO92, int initialQuantityO95,
			int emergencyAllocationO95, int initialQuantityRD, int emergencyAllocationRD, int initialQuantitySD,
			int emergencyAllocationSD) {
		Quota initialQuota = new Quota(LocalDateTime.now().toString(), "00000000");

		// setting the initial allocatedQuotaSum and availableQuantity
		// O92
		initialQuota.setAllocatedQuotaSumO92(emergencyAllocationO92);
		initialQuota.setAvailableQuantityO92(initialQuantityO92);
		// O95
		initialQuota.setAllocatedQuotaSumO95(emergencyAllocationO95);
		initialQuota.setAvailableQuantityO95(initialQuantityO95);
		// RD
		initialQuota.setAllocatedQuotaSumRD(emergencyAllocationRD);
		initialQuota.setAvailableQuantityRD(initialQuantityRD);
		// SD
		initialQuota.setAllocatedQuotaSumSD(emergencyAllocationSD);
		initialQuota.setAvailableQuantitySD(initialQuantitySD);

		Quota q = quotaRepository.save(initialQuota);
	}
}
