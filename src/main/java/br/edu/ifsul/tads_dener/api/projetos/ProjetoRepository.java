package br.edu.ifsul.tads_dener.api.projetos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto,Long> {

    List<Projeto> findByNome(String nome);
}
