package br.edu.ifsul.tads_dener.api.projetos;

import lombok.Data;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
public class ProjetoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Long dias_restantes;

    public static ProjetoDTO create(Projeto p) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(p, ProjetoDTO.class);
    }
}
