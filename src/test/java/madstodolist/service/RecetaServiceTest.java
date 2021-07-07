package madstodolist.service;


import madstodolist.model.Receta;
import madstodolist.model.Tarea;
import madstodolist.model.Usuario;
import madstodolist.service.TareaService;
import madstodolist.service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecetaServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RecetaService recetaService;


    @Test
    @Transactional
    public void  testNuevaRecetaUsuario(){
        //Given

        //When
        Receta receta = recetaService.nuevaRecetaUsuario(1L, "test" , "test");

        //Then
        Usuario usuario = usuarioService.findByEmail("ana.garcia@gmail.com");
        assertThat(usuario.getRecetas().contains(receta));
    }

    @Test
    @Transactional
    public void  testNuevaRecetaCompartidaUsuario(){
        //Given

        //When
        Receta receta = recetaService.nuevaRecetaUsuario(1L, "test" , "test");
        receta.setCompartida(true);
        //Then
        Usuario usuario = usuarioService.findByEmail("ana.garcia@gmail.com");
        List <Receta> recetas = recetaService.allRecetasUsuario(1L);

        Receta compartida = recetaService.findById(1L);
        assertThat(compartida.getCompartida()).isTrue();

    }


}
