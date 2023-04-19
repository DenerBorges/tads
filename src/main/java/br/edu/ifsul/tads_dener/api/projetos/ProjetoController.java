package br.edu.ifsul.tads_dener.api.projetos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/projetos")
@Api(value = "Projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService service;
    @GetMapping
    @ApiOperation(value = "Retorna todos os projetos cadastrados.")
    public ResponseEntity<List<ProjetoDTO>> selectAll() {
        List<ProjetoDTO> projetos = service.getProjetos();
        return ResponseEntity.ok(projetos);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Retorna um projeto pelo identificador.")
    public ResponseEntity<ProjetoDTO> selectById(@PathVariable("id") Long id) {
        ProjetoDTO projeto = service.getProjetoById(id);
        return ResponseEntity.ok(projeto);
    }

    @GetMapping("/nome/{nome}")
    @ApiOperation(value = "Retorna uma lista de projetos pelo nome.")
    public ResponseEntity<List<ProjetoDTO>> selectByNome(@PathVariable("nome") String nome) {
        List<ProjetoDTO> projetos = service.getProjetosByNome(nome);
        return projetos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(projetos);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    @ApiOperation(value = "Insere um novo projeto.")
    public ResponseEntity<String> insert(@RequestBody Projeto projeto) {
        ProjetoDTO p = service.insert(projeto);
        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Altera um projeto existente.")
    public ResponseEntity<ProjetoDTO> update(@PathVariable("id") Long id, @RequestBody Projeto projeto) {
        projeto.setId(id);
        ProjetoDTO p = service.update(projeto, id);
        return p != null ?
                ResponseEntity.ok(p) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um projeto.")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
