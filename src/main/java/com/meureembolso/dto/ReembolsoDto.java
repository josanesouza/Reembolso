package com.meureembolso.dto;

import lombok.Data;

@Data
public class ReembolsoDto {
	private String id;
    private String matricula;
    private double valor;
    private String descricao;
    private String status;
}
