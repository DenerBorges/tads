package br.edu.ifsul.tads_dener.api.projetos;

import br.edu.ifsul.tads_dener.api.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository rep;

    public List<ProjetoDTO> getProjetos() {
        return rep.findAll().stream().map(ProjetoDTO::create).collect(Collectors.toList());
    }

    public ProjetoDTO getProjetoById(Long id) {
        Optional<Projeto> projeto = rep.findById(id);
        return projeto.map(ProjetoDTO::create).orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado"));
    }

    public List<ProjetoDTO> getProjetosByNome(String nome) {
        return rep.findByNome(nome).stream().map(ProjetoDTO::create).collect(Collectors.toList());
    }

    public ProjetoDTO insert(Projeto projeto) {
        Assert.isNull(projeto.getId(), "Não foi possível inserir o registro");
        return ProjetoDTO.create(rep.save(projeto));
    }

    public ProjetoDTO update(Projeto projeto, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Projeto> optional = rep.findById(id);
        if (optional.isPresent()) {
            Projeto db = optional.get();
            db.setNome(projeto.getNome());
            db.setDescricao(projeto.getDescricao());
            db.setValor(projeto.getValor());
            db.setDias_restantes(projeto.getDias_restantes());
            System.out.println("Projeto id " + db.getId());
            rep.save(db);
            return ProjetoDTO.create(db);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
