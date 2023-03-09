package br.edu.ifsul.tads_dener.api.produtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Long estoque;
    private Boolean situacao;
}