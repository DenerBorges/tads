package br.edu.ifsul.tads_dener.api.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService service;
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> selectAll() {
        List<ProjetoDTO> projetos = service.getProjetos();
        return ResponseEntity.ok(projetos);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjetoDTO> selectById(@PathVariable("id") Long id) {
        ProjetoDTO projeto = service.getProjetoById(id);
        return ResponseEntity.ok(projeto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProjetoDTO>> selectByNome(@PathVariable("nome") String nome) {
        List<ProjetoDTO> projetos = service.getProjetosByNome(nome);
        return projetos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(projetos);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<String> insert(@RequestBody Projeto projeto) {
        ProjetoDTO p = service.insert(projeto);
        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ProjetoDTO> update(@PathVariable("id") Long id, @RequestBody Projeto projeto) {
        projeto.setId(id);
        ProjetoDTO p = service.update(projeto, id);
        return p != null ?
                ResponseEntity.ok(p) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
