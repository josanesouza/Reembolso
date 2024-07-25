package com.meureembolso.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.meureembolso.controller.UrlController;
import com.meureembolso.dto.ReembolsoDto;
import com.meureembolso.model.Reembolso;
import com.meureembolso.model.StatusReembolso;
import com.meureembolso.model.Url;
import com.meureembolso.service.ReembolsoService;
import com.meureembolso.service.UrlService;

public class UrlControllerTest {
	private MockMvc mockMvc;

	@Mock
	private UrlService urlService;

	@Mock
	private ReembolsoService reembolsoService;

	@InjectMocks
	private UrlController urlController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
	}

	@Test
	void testConsultarReembolso() throws Exception {
		// Arrange
		String id = "some-id";
		Url url = new Url();
		url.setId(id);
		url.setIdReembolso("reembolso-id");

		Reembolso reembolso = new Reembolso();
		reembolso.setId("reembolso-id");
		reembolso.setMatricula("123456");
		reembolso.setValor(150.0);
		reembolso.setDescricao("Reembolso de teste");
		reembolso.setStatus(StatusReembolso.APROVADO);

		when(urlService.findById(anyString())).thenReturn(url);
		when(reembolsoService.consultarReembolso(anyString())).thenReturn(reembolso);

		// Act
		ResponseEntity<ReembolsoDto> response = urlController.consultarReembolso(id);

		// Assert
		verify(urlService, times(1)).findById(anyString());
		verify(reembolsoService, times(1)).consultarReembolso(anyString());

		assertEquals(200, response.getStatusCodeValue());
		ReembolsoDto dto = response.getBody();
		assertEquals("123456", dto.getMatricula());
		assertEquals(150.0, dto.getValor());
		assertEquals("Reembolso de teste", dto.getDescricao());
		assertEquals(StatusReembolso.APROVADO.toString(), dto.getStatus());
	}	
}
