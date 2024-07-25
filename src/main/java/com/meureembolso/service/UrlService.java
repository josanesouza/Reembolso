package com.meureembolso.service;

import org.springframework.stereotype.Service;

import com.meureembolso.exception.UrlNotFoundException;
import com.meureembolso.model.Url;
import com.meureembolso.repository.ReembolsoRepository;
import com.meureembolso.repository.UrlRepository;

@Service
public class UrlService {
	
	private final ReembolsoRepository reembolsoRepository;

	private final UrlRepository urlRepository;
	
	public UrlService(UrlRepository urlRepository, ReembolsoRepository reembolsoRepository) {
		this.urlRepository = urlRepository;
		this.reembolsoRepository = reembolsoRepository;
	}

	public boolean exitsUrlById(String id) {
		return urlRepository.existsById(id);
	}
	
	public Url save(Url url) {
		return urlRepository.save(url);
	}

	public Url findById(String id) {
		return urlRepository.findById(id)
				.orElseThrow(() -> new UrlNotFoundException("Url não encontrada"));
	}
}
