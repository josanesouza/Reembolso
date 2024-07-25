package com.meureembolso.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reembolsos")
public class Reembolso {
	@Id
	private String id;
	private String nome;
	private String matricula;
	private double valor;
	private String descricao;
	private StatusReembolso status;
	private String idUrl;

	public Reembolso() {
		this.status = StatusReembolso.SOLICITACAO_EM_ANALISE; // status inicial
	}
}
