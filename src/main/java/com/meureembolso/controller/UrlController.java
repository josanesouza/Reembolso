package com.meureembolso.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meureembolso.dto.ReembolsoDto;
import com.meureembolso.model.Reembolso;
import com.meureembolso.model.Url;
import com.meureembolso.service.ReembolsoService;
import com.meureembolso.service.UrlService;

@RestController
@RequestMapping("/shorturl")
public class UrlController {
	private final UrlService urlService;
	private final ReembolsoService reembolsoService;

	public UrlController(UrlService urlService, ReembolsoService reembolsoService) {
		this.urlService = urlService;
		this.reembolsoService = reembolsoService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReembolsoDto> consultarReembolso(@PathVariable String id) {

		Url url = urlService.findById(id);
		Reembolso reembolso = reembolsoService.consultarReembolso(url.getIdReembolso());
		ReembolsoDto dto = new ReembolsoDto();
		dto.setMatricula(reembolso.getMatricula());
		dto.setValor(reembolso.getValor());
		dto.setDescricao(reembolso.getDescricao());
		dto.setStatus(reembolso.getStatus().toString());

		return ResponseEntity.ok(dto);
	}

}
