package madstodolist.model;

import madstodolist.model.Tarea;
import madstodolist.model.TareaRepository;
import madstodolist.model.Usuario;
import madstodolist.model.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrupoTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    GrupoRepository grupoRepository;



    @Test
    public void crearGrupo(){
        Grupo grupo = new Grupo("test");
        assertThat(grupo.nombre).isEqualTo("test");
    }

    @Test
    public void grabarGrupo(){
        Grupo grupo = new Grupo("test");

        grupoRepository.save(grupo);

        assertThat(grupo.getId()).isNotNull();
    }
}
