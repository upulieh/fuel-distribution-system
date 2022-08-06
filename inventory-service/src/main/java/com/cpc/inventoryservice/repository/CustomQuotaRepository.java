package com.cpc.inventoryservice.repository;


import com.cpc.inventoryservice.model.Quota;

//for custom queries
public interface CustomQuotaRepository {
	Quota retrieveFinalQuota();
	Quota retrieveSpecificRecord(String id);
}