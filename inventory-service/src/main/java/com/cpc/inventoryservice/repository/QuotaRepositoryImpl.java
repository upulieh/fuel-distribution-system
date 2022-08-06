package com.cpc.inventoryservice.repository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cpc.inventoryservice.model.Quota;

public class QuotaRepositoryImpl implements CustomQuotaRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Quota retrieveFinalQuota() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "timeStamp")).limit(1);
		return mongoTemplate.findOne(query, Quota.class);
	}

	@Override
	public Quota retrieveSpecificRecord(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(id));
		Quota quotaById = mongoTemplate.findOne(query, Quota.class); // fetch the quotas

		Quota quotaByTimestamp = this.retrieveFinalQuota();

		// update the quotas
		// from finalQuota
		Quota newQuota = quotaByTimestamp.clone();
		// from the specific quota
		int quantity = quotaById.getTransactionQuantity();
		newQuota.setTransactionQuantity(quantity);
		// from current data
		newQuota.setTimeStamp(LocalDateTime.now().toString());
		newQuota.setOrderId(id);

		// update the allocatedQuantity and scheduledQuantity according to the fuel type
		String fuelTypeString = id.substring(id.length() - 2);
		updateQuantities(newQuota, fuelTypeString, quantity);
		System.out.println("Created new quota record :" + newQuota);
		return mongoTemplate.save(newQuota);
	}

	private void updateQuantities(Quota q, String fuelTypeString, int quantity) {
		if (fuelTypeString.equals("92")) {
			q.setAllocatedQuotaSumO92(q.getAllocatedQuotaSumO92() - quantity);
			q.setAvailableQuantityO92(q.getAvailableQuantityO92() - quantity);
		} else if (fuelTypeString.equals("95")) {
			q.setAllocatedQuotaSumO95(q.getAllocatedQuotaSumO95() - quantity);
			q.setAvailableQuantityO95(q.getAvailableQuantityO95() - quantity);
		} else if (fuelTypeString.equals("RD")) {
			q.setAllocatedQuotaSumRD(q.getAllocatedQuotaSumRD() - quantity);
			q.setAvailableQuantityRD(q.getAvailableQuantityRD() - quantity);
		} else if (fuelTypeString.equals("SD")) {
			q.setAllocatedQuotaSumSD(q.getAllocatedQuotaSumSD() - quantity);
			q.setAvailableQuantitySD(q.getAvailableQuantitySD() - quantity);
		} else {
			System.out.println("Fuel Type mismatched on orderId");
		}
	}
}
