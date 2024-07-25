package com.meureembolso.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "urls")
public class Url {

	@Id
	private String id;
	
	private String fullUrl;
	
	private String idReembolso;
	
}
