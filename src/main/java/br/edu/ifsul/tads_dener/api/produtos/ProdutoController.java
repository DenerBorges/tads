package br.edu.ifsul.tads_dener.api.produtos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {
    @GetMapping
    public String selectAll() {
        return "selectAll()";
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
