package com.meureembolso.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.meureembolso.controller.ReembolsoController;
import com.meureembolso.dto.ReembolsoDto;
import com.meureembolso.model.Reembolso;
import com.meureembolso.model.StatusReembolso;
import com.meureembolso.service.ReembolsoService;
import com.meureembolso.service.UrlService;

public class ReembolsoControllerTest {
	private MockMvc mockMvc;

	@Mock
	private ReembolsoService reembolsoService;

	@Mock
	private UrlService urlService;

	@InjectMocks
	private ReembolsoController reembolsoController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reembolsoController).build();
	}

	@Test
	void testSave() {
		Reembolso reembolso = new Reembolso();
		reembolso.setId(String.valueOf(1L));
		when(reembolsoService.solicitarReembolso(any(Reembolso.class))).thenReturn(reembolso);
		when(urlService.exitsUrlById(any())).thenReturn(false);

		ResponseEntity<String> response = reembolsoController.save(reembolso);

		verify(reembolsoService, times(1)).solicitarReembolso(any(Reembolso.class));
		verify(urlService, times(1)).save(any());
		verify(reembolsoService, times(1)).atualizarReembolso(any());

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("link_acompanhamento: http://shorturl/" + reembolso.getIdUrl(), response.getBody());
	}

	@Test
	void testConsultarReembolso() {
		Reembolso reembolso = new Reembolso();
		reembolso.setId(String.valueOf(1L));
		reembolso.setValor(100.0);
		reembolso.setDescricao("Test Description");
		reembolso.setStatus(StatusReembolso.SOLICITACAO_EM_ANALISE);
		when(reembolsoService.consultarReembolso(any())).thenReturn(reembolso);

		ResponseEntity<ReembolsoDto> response = reembolsoController.consultarReembolso("1");

		verify(reembolsoService, times(1)).consultarReembolso(any());

		assertEquals(200, response.getStatusCodeValue());
		ReembolsoDto dto = response.getBody();
		assertEquals("1", dto.getId());
		assertEquals(100.0, dto.getValor());
		assertEquals("Test Description", dto.getDescricao());
		assertEquals(StatusReembolso.SOLICITACAO_EM_ANALISE.toString(), dto.getStatus());
	}

	@Test
	void testCancelarReembolso() {
		Reembolso reembolso = new Reembolso();
		reembolso.setId(String.valueOf(1L));
		reembolso.setDescricao("Test Description");
		when(reembolsoService.cancelarReembolso(any(), any())).thenReturn(reembolso);

		ResponseEntity<ReembolsoDto> response = reembolsoController.cancelarReembolso("1", "Motivo");

		verify(reembolsoService, times(1)).cancelarReembolso(any(), any());

		assertEquals(200, response.getStatusCodeValue());
		ReembolsoDto dto = response.getBody();
		assertEquals("1", dto.getId());
		assertEquals("Test Description", dto.getDescricao());
	}

}
