package br.edu.ifsul.tads_dener;

import br.edu.ifsul.tads_dener.api.projetos.Projeto;
import br.edu.ifsul.tads_dener.api.projetos.ProjetoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TadsDenerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjetoControllerTest extends BaseAPITest {

    // Métodos utilitários
    private ResponseEntity<ProjetoDTO> getProjeto(String url) {
        return get(url, ProjetoDTO.class);
    }

    private ResponseEntity<List<ProjetoDTO>> getProjetos(String url) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProjetoDTO>>() {
                });
    }

    @Test
    public void selectAll() {
        List<ProjetoDTO> projetos = getProjetos("/api/v1/projetos").getBody();
        assertNotNull(projetos);
        assertEquals(5, projetos.size());

        projetos = getProjetos("/api/v1/projetos?page=0&size=5").getBody();
        assertNotNull(projetos);
        assertEquals(5, projetos.size());
    }

    @Test
    public void selectByNome() {
        assertEquals(1, getProjetos("/api/v1/projetos/nome/Driver").getBody().size());
        assertEquals(1, getProjetos("/api/v1/projetos/nome/Callback").getBody().size());
        assertEquals(1, getProjetos("/api/v1/projetos/nome/Fable").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getProjetos("/api/v1/projetos/nome/xxx").getStatusCode());
    }

    @Test
    public void selectById() {
        assertNotNull(getProjeto("/api/v1/projetos/1"));
        assertNotNull(getProjeto("/api/v1/projetos/2"));
        assertNotNull(getProjeto("/api/v1/projetos/3"));

        assertEquals(HttpStatus.NOT_FOUND, getProjeto("/api/v1/projetos/1000").getStatusCode());
    }

    @Test
    public void testInsert() {
        Projeto projeto = new Projeto();
        projeto.setNome("New book");
        projeto.setDescricao("Great book");
        projeto.setDias_restantes(30L);
        projeto.setValor(new BigDecimal("132.90"));

        // Insert
        ResponseEntity response = post("/api/v1/projetos", projeto, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        ProjetoDTO p = getProjeto(location).getBody();

        assertNotNull(p);
        assertEquals("New book", p.getNome());
        assertEquals(Long.valueOf(30), p.getDias_restantes());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getProjeto(location).getStatusCode());
    }

    @Test
    public void testUpdate() {
        // Primeiro insere o objeto
        Projeto projeto = new Projeto();
        projeto.setNome("New book");
        projeto.setDescricao("Great book");
        projeto.setDias_restantes(30L);
        projeto.setValor(new BigDecimal(132.90));

        // Insert
        ResponseEntity response = post("/api/v1/projetos", projeto, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        ProjetoDTO p = getProjeto(location).getBody();

        assertNotNull(p);
        assertEquals("New book", p.getNome());
        assertEquals(Long.valueOf(30), p.getDias_restantes());

        // Altera seu valor
        Projeto po = Projeto.create(p);
        po.setDias_restantes(50L);

        // Update
        response = put("/api/v1/projetos/" + p.getId(), po, null);
        System.out.println(response);
        assertEquals(Long.valueOf(50), po.getDias_restantes());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getProjeto(location).getStatusCode());
    }

    @Test
    public void testDelete() {
        this.testInsert();
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getProjeto("/api/v1/projetos/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
