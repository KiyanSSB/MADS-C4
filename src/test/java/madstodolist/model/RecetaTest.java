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
public class RecetaTest {


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RecetaRepository recetaRepository;

    //
    // Tests modelo Tarea
    //


    @Test
    public void compartirReceta() throws Exception {
        // GIVEN
        Usuario usuario = new Usuario("juan.gutierrez@gmail.com");

        // WHEN

        Receta receta = new Receta(usuario,"prueba" , "prueba");

        receta.setCompartida(true);
        // THEN
    
        assertThat(receta.getCompartida()).isTrue();


    }

    @Test
    public void darLikeReceta() throws Exception {
        // GIVEN
        Usuario usuario = new Usuario("juan.gutierrez@gmail.com");
        // WHEN
        Receta receta = new Receta(usuario,"prueba" , "prueba");
        receta.sumarLike();
        // THEN
        assertThat(receta.getLikes()).isEqualTo(1);
    }

}
