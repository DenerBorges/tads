package br.edu.ifsul.tads_dener;

import br.edu.ifsul.tads_dener.api.infra.exception.ObjectNotFoundException;
import br.edu.ifsul.tads_dener.api.projetos.Projeto;
import br.edu.ifsul.tads_dener.api.projetos.ProjetoDTO;
import br.edu.ifsul.tads_dener.api.projetos.ProjetoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjetoServiceTest {

    @Autowired
    private ProjetoService service;

    @Test
    public void testGetProjetos() {
        List<ProjetoDTO> projetos = service.getProjetos();
        assertEquals(5, projetos.size());
    }

    @Test
    public void testGetProjetoById() {
        ProjetoDTO p = service.getProjetoById(1L);
        assertNotNull(p);
        assertEquals("The Book", p.getNome());
    }

    @Test
    public void getProjetoByNome() {
        assertEquals(1, service.getProjetosByNome("The Book").size());
        assertEquals(1, service.getProjetosByNome("Callback").size());
        assertEquals(1, service.getProjetosByNome("Fable").size());
    }

    @Test
    public void testInsert() {

        // criando projeto de teste
        Projeto projeto = new Projeto();
        projeto.setNome("Teste");
        projeto.setDescricao("Descricao do projeto");
        projeto.setValor(new BigDecimal("10.00"));
        projeto.setDias_restantes(10L);

        // inserindo o projeto na base de dados
        ProjetoDTO p = service.insert(projeto);

        // se inseriu
        assertNotNull(p);

        // confirmando se o projeto foi realmente inserido na base de dados
        Long id = p.getId();
        assertNotNull(id);
        p = service.getProjetoById(id);
        assertNotNull(p);

        // comparando os valores inseridos com os valores pesquisados para confirmar
        assertEquals("Teste", p.getNome());
        assertEquals("Descricao do projeto", p.getDescricao());
        assertEquals(new BigDecimal("10.00"), p.getValor());
        assertEquals(Long.valueOf(10), p.getDias_restantes());

        // deletando o objeto
        service.delete(id);
        // verificando se deletou
        try {
            service.getProjetoById(id);
            fail("O projeto não foi excluído!");
        } catch (ObjectNotFoundException e) {
            // Ok
        }
    }

    @Test
    public void TestUpdate() {
        ProjetoDTO pDTO = service.getProjetoById(1L);
        String nome = pDTO.getNome();
        pDTO.setNome("Just Book");
        Projeto p = Projeto.create(pDTO);

        pDTO = service.update(p, p.getId());
        assertNotNull(pDTO);
        assertEquals("Just Book", pDTO.getNome());

        p.setNome(nome);
        pDTO = service.update(p, p.getId());
        assertNotNull(pDTO);
    }

    @Test
    public void testDelete() {

        // criando projeto de teste
        Projeto projeto = new Projeto();
        projeto.setNome("Teste");
        projeto.setDescricao("Descricao do projeto");
        projeto.setValor(new BigDecimal("1.00"));
        projeto.setDias_restantes(1L);

        // inserindo o projeto na base de dados
        ProjetoDTO p = service.insert(projeto);

        // se inseriu
        assertNotNull(p);

        // confirmando se o projeto foi realmente inserido na base de dados
        Long id = p.getId();
        assertNotNull(id);
        p = service.getProjetoById(id);
        assertNotNull(p);

        // deletando o objeto
        service.delete(id);
        // verificando se deletou
        try {
            service.getProjetoById(id);
            fail("O projeto não foi excluído!");
        } catch (ObjectNotFoundException e) {
            // Ok
        }
    }
}
