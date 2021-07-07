package madstodolist.service;
import madstodolist.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GrupoServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GrupoService grupoService;


    @Test
    @Transactional
    public void testNuevoGrupo(){
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        // WHEN
        Grupo grupo = grupoService.crear("prueba" , 1L);

        //Then
        Usuario usuario = usuarioService.findByEmail("ana.garcia@gmail.com");
        assertThat(usuario.getEquipos()).contains(grupo);
    }


}
