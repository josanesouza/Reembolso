package com.meureembolso.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.meureembolso.model.Url;

public interface UrlRepository extends MongoRepository<Url, String> {

}
