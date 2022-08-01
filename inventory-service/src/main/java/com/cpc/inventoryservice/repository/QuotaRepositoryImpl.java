package com.cpc.inventoryservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
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
}
