package com.meureembolso.serviceTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.meureembolso.model.Reembolso;
import com.meureembolso.service.ReembolsoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReembolsoServiceTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReembolsoService reembolsoService;

	@Test
	public void testSolicitarReembolso() throws Exception {
		Reembolso reembolso = new Reembolso();
		reembolso.setId("1");
		reembolso.setDescricao("Reembolso teste");

		Mockito.when(reembolsoService.solicitarReembolso(Mockito.any(Reembolso.class))).thenReturn(reembolso);

		mockMvc.perform(post("/meureembolso/api/v1/reembolso").contentType(MediaType.APPLICATION_JSON)
				.content("{\"descricao\": \"Reembolso teste\"}")).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.descricao").value("Reembolso teste"));
	}
}
