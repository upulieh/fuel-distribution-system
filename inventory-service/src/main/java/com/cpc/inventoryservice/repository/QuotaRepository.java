package com.cpc.inventoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cpc.inventoryservice.model.Quota;

public interface QuotaRepository extends MongoRepository<Quota, String>, CustomQuotaRepository{
}
