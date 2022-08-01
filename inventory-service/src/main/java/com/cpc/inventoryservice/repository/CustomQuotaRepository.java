package com.cpc.inventoryservice.repository;


import com.cpc.inventoryservice.model.Quota;

public interface CustomQuotaRepository {
	Quota retrieveFinalQuota();
}