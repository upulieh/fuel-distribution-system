package com.cpc.inventoryservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.inventoryservice.model.Quota;
import com.cpc.inventoryservice.repository.QuotaRepository;
import com.cpc.orderservice.models.Quantity;

@Service
public class QuotaServiceImpl implements QuotaService {
	@Autowired
	QuotaRepository quotaRepository;

	public Quota submitQuotaRecord(Quota newQuota, Quantity quantityEnum, Integer latestAvailableQuantity) {

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

		System.out.println("previous quota :" + previousQuota);

		// calculate and assign the quota values
		newQuota.setAllocatedQuotaSum(previousQuota.getAllocatedQuotaSum() + quantityInL);
		newQuota.setAvailableQuantity(previousQuota.getAvailableQuantity() - quantityInL);

		System.out.println("current quota :" + newQuota);

		return quotaRepository.save(newQuota);
	}
	
	public void initializeInventory() {
		Quota initialQuota=new Quota(LocalDateTime.now().toString(),"00000000");
		
		//	setting the initial allocatedQuotaSum and availableQuantity
		initialQuota.setAllocatedQuotaSum(0);
		initialQuota.setAvailableQuantity(50_000);
		
		Quota q = quotaRepository.save(initialQuota);
	}
}
