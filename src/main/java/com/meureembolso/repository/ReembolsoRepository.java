package com.meureembolso.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meureembolso.model.Reembolso;

@Repository
public interface ReembolsoRepository extends MongoRepository<Reembolso, String>{

}
