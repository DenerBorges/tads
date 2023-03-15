package br.edu.ifsul.tads_dener.api.produtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository rep;

    public List<Produto> getProdutos() {
        return rep.findAll().stream().collect(Collectors.toList());
    }


}
