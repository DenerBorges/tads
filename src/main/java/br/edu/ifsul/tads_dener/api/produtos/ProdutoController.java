package br.edu.ifsul.tads_dener.api.produtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;
    @GetMapping
    public List<Produto> selectAll() {
        return service.getProdutos();
    }
    @GetMapping("{id}")
    public String selectById(@PathVariable("id") Long id) {
        return "selectById " + id;
    }
    @GetMapping("/nome/{nome}")
    public String selectByNome(@PathVariable("nome") String nome) {
        return "selectByNome " + nome;
    }
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public String insert(@RequestBody Produto produto){
        return "insert " + produto;
    }
    @PutMapping("{id}")
    public String update(@PathVariable("id") Long id, @RequestBody Produto produto){
        return "update " + produto + id;
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id){
        return "delete " + id;
    }
}
