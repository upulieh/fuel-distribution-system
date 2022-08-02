package com.cpc.inventoryservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.inventoryservice.InventoryServiceApplication;
import com.cpc.inventoryservice.model.Quota;
import com.cpc.inventoryservice.repository.QuotaRepository;
import com.cpc.orderservice.models.Quantity;

@Service
public class QuotaServiceImpl implements QuotaService {

	@Autowired
	QuotaRepository quotaRepository;

	public Quota submitQuotaRecord(Quota newQuota, Quantity quantityEnum) {

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

		// calculate and assign the quota values
		Integer allocatedSum = previousQuota.getAllocatedQuotaSum();
		Integer availableQuantity = previousQuota.getAvailableQuantity();

		if (availableQuantity < quantityInL) {
			// can't allocate if not enough stock
			return null; // check for null in controller
		} else {
			newQuota.setAllocatedQuotaSum(allocatedSum + quantityInL);
			newQuota.setAvailableQuantity(availableQuantity - quantityInL);

			InventoryServiceApplication.logger
					.info("inventory-service :Quota changed from " + previousQuota + " to " + newQuota);

			return quotaRepository.save(newQuota);
		}
	}

	public void initializeInventory(int initialQuantity,int emergencyAllocation) {
		Quota initialQuota = new Quota(LocalDateTime.now().toString(), "00000000");

		// setting the initial allocatedQuotaSum and availableQuantity
		initialQuota.setAllocatedQuotaSum(emergencyAllocation);
		initialQuota.setAvailableQuantity(initialQuantity);

		Quota q = quotaRepository.save(initialQuota);
	}
}
