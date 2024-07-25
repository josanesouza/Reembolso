package com.meureembolso.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.meureembolso.exception.ReembolsoNotFoundException;
import com.meureembolso.model.Reembolso;
import com.meureembolso.model.StatusReembolso;
import com.meureembolso.repository.ReembolsoRepository;

@Service
public class ReembolsoService {
	private final ReembolsoRepository reembolsoRepository;

	public ReembolsoService(ReembolsoRepository reembolsoRepository) {
		this.reembolsoRepository = reembolsoRepository;
	}

	public Reembolso solicitarReembolso(Reembolso reembolso) {
		// O período de solicitação de reembolso é do dia 1 ao dia 20 de cada mês.
		LocalDate hoje = LocalDate.now();
		if (hoje.getDayOfMonth() < 1 || hoje.getDayOfMonth() > 20) {
			reembolso.setStatus(StatusReembolso.NEGADO);
			throw new IllegalArgumentException("Solicitação fora do período permitido – 01 à 20 de cada mês.");
			
		}
		// O valor máximo de reembolso é de 500,00 por mês.
		// TODO melhorar para verificar valores solicitados
		Double limite = 500.00;
		if (reembolso.getValor() > limite) {
			reembolso.setStatus(StatusReembolso.NEGADO);
			throw new IllegalArgumentException("Limite mensal excedido. Seu limite para solicitação de reembolso é de"
					+ (limite - reembolso.getValor() ));
		}
		
		return reembolsoRepository.save(reembolso);
	}

	public Reembolso consultarReembolso(String id) {
		return reembolsoRepository.findById(id)
				.orElseThrow(() -> new ReembolsoNotFoundException("Reembolso não encontrado"));
	}

	public Reembolso cancelarReembolso(String id, String motivo) {
		Reembolso reembolso = consultarReembolso(id);
		reembolso.setStatus(StatusReembolso.CANCELAMENTO_EM_ANALISE);
		return reembolsoRepository.save(reembolso);
	}

	public Reembolso atualizarReembolso(Reembolso reembolso) {
		return reembolsoRepository.save(reembolso);
	}
}
