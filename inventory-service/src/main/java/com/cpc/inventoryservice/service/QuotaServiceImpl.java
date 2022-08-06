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

	public Quota submitQuotaRecord(String id, Quantity quantityEnum, FuelType fuelType) {

		// issue in this method, not checking null for all values before saving in the
		// inventory
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

		// update the previousQuota to create a new quota
		previousQuota.setOrderId(id);
		previousQuota.setTimeStamp(LocalDateTime.now().toString());
		previousQuota.setTransactionQuantity(quantityInL);

		// calculate and assign the quota values according to the fuel type
		Integer allocatedSum = 0; // stays the same for initial record
		Integer availableQuantity = 0; // stays the same for initial record

		switch (fuelType) {
		case OCTANE92: {
			allocatedSum = previousQuota.getAllocatedQuotaSumO92();
			availableQuantity = previousQuota.getAvailableQuantityO92(); // is null??

			if ((availableQuantity - allocatedSum) < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				previousQuota.setAllocatedQuotaSumO92(allocatedSum + quantityInL); // creating order only impacts
																					// allocatedQuantity

				InventoryServiceApplication.logger
						.info("inventory-service : AllocatedQuantitySum of Octane 92 changed from " + allocatedSum
								+ " to " + (allocatedSum + quantityInL));

				return quotaRepository.save(previousQuota);
			}
		}
		case OCTANE95: {
			allocatedSum = previousQuota.getAllocatedQuotaSumO95();
			availableQuantity = previousQuota.getAvailableQuantityO95();

			if ((availableQuantity - allocatedSum) < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				previousQuota.setAllocatedQuotaSumO95(allocatedSum + quantityInL);

				InventoryServiceApplication.logger
						.info("inventory-service : AllocatedQuantitySum of Octane 95 changed from " + allocatedSum
								+ " to " + (allocatedSum + quantityInL));

				return quotaRepository.save(previousQuota);
			}
		}
		case REGULAR_DIESEL: {
			allocatedSum = previousQuota.getAllocatedQuotaSumRD();
			availableQuantity = previousQuota.getAvailableQuantityRD();

			if ((availableQuantity - allocatedSum) < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				previousQuota.setAllocatedQuotaSumRD(allocatedSum + quantityInL);

				InventoryServiceApplication.logger
						.info("inventory-service : AllocatedQuantitySum of Regular Diesel changed from " + allocatedSum
								+ " to " + (allocatedSum + quantityInL));

				return quotaRepository.save(previousQuota);
			}
		}
		case SUPER_DIESEL: {
			allocatedSum = previousQuota.getAllocatedQuotaSumSD();
			availableQuantity = previousQuota.getAvailableQuantitySD();

			if ((availableQuantity - allocatedSum) < quantityInL) {
				// can't allocate if not enough stock
				return null; // check for null in controller
			} else {
				previousQuota.setAllocatedQuotaSumSD(allocatedSum + quantityInL);

				InventoryServiceApplication.logger
						.info("inventory-service : AllocatedQuantitySum of Super Diesel changed from " + allocatedSum
								+ " to " + (allocatedSum + quantityInL));

				return quotaRepository.save(previousQuota);
			}
		}
		default: {
			return null;
		}
		}
	}

	// updates the allocatedQuantity and availableQuantity fields after the dispatch
	public Quota updateQuantities(String id) {
		// retrieve the specific quota, given the orderId
		return quotaRepository.retrieveSpecificRecord(id);
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
