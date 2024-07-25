package com.meureembolso.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meureembolso.dto.ReembolsoDto;
import com.meureembolso.model.Reembolso;
import com.meureembolso.model.Url;
import com.meureembolso.service.ReembolsoService;
import com.meureembolso.service.UrlService;

@RestController
@RequestMapping("/meureembolso/api/v1/reembolso")
public class ReembolsoController {

	private final ReembolsoService reembolsoService;

	private final UrlService urlService;

	public ReembolsoController(ReembolsoService reembolsoService, UrlService urlService) {
		this.reembolsoService = reembolsoService;
		this.urlService = urlService;
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody Reembolso reembolso) {
		reembolso = this.reembolsoService.solicitarReembolso(reembolso);

		String urlOriginal = "http://meureembolso/api/v1/reembolso" + reembolso.getId();

		String id = null;
		Url url = new Url();
		do {
			id = RandomStringUtils.randomAlphanumeric(5, 10);
		} while (urlService.exitsUrlById(id));
		url.setId(id);
		url.setFullUrl(urlOriginal);
		url.setIdReembolso(reembolso.getId());
		urlService.save(url);
		reembolso.setIdUrl(id);
		reembolsoService.atualizarReembolso(reembolso);

		var redirectUrl = "link_acompanhamento: http://shorturl/" + id;
		return ResponseEntity.ok(redirectUrl);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReembolsoDto> consultarReembolso(@PathVariable String id) {
		Reembolso reembolso = reembolsoService.consultarReembolso(id);
		
		ReembolsoDto dto = new ReembolsoDto();
		dto.setId(id);
		dto.setValor(reembolso.getValor());
		dto.setDescricao(reembolso.getDescricao());
		dto.setStatus(reembolso.getStatus().toString());
		
		return ResponseEntity.ok(dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReembolsoDto> cancelarReembolso(@PathVariable String id, @RequestBody String motivo) {
		Reembolso reembolsoCancelado = reembolsoService.cancelarReembolso(id, motivo);
		ReembolsoDto dto = new ReembolsoDto();
		dto.setId(reembolsoCancelado.getId());
		dto.setDescricao(reembolsoCancelado.getDescricao());
		return ResponseEntity.ok(dto);
	}

}
