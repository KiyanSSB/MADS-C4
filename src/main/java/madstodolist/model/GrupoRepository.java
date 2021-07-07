package madstodolist.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GrupoRepository extends CrudRepository<Grupo,Long> {
    List<Grupo> findAll();
}
