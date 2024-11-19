package com.example.parcialweb.dto;

import lombok.Data;

@Data
public class ContratoDTO {
    private Long id;
    private String identificador;
    private Double valor;
    private String nombreContratante;
    private String documentoContratante;
    private String nombreContratantista;
    private String documentoContratantista;
    private String fechaInicial;
    private String fechaFinal;
}