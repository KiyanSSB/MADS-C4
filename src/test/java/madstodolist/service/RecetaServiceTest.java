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

    @Test
    @Transactional
    public void testDarLike(){
        Receta receta = recetaService.nuevaRecetaUsuario(1L, "test" , "test");
        assertThat(receta.getLikes()).isEqualTo(0);
        recetaService.darLike(receta.getId());
        assertThat(receta.getLikes()).isEqualTo(1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////    TDD   ////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @Transactional
    public void TDDModificarTarea(){
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        Receta receta = recetaService.nuevaRecetaUsuario(1L, "prueba" , "prueba");
        Long idNuevaTarea = receta.getId();

        // WHEN

        //Modificamos la receta colocando diferentes nombre, ingredientes y atributos de favorito, likes y compartido
        Receta recetaModificada = recetaService.modificarTarea(idNuevaTarea, "modificado" , "modificado" , true ,  true);
        Receta recetaBD = recetaService.findById(idNuevaTarea);

        // THEN

        //Nombre
        assertThat(recetaModificada.getNombre()).isEqualTo("modificado");
        assertThat(recetaBD.getNombre()).isEqualTo("modificado");

        //Ingredientes
        assertThat(recetaModificada.getIngredientes()).isEqualTo("modificado");
        assertThat(recetaBD.getIngredientes()).isEqualTo("modificado");

        //Favoritos
        assertThat(recetaModificada.getCompartida()).isTrue();
        assertThat(recetaBD.getCompartida()).isTrue();

        //Compartidos
        assertThat(recetaModificada.getFavorita()).isTrue();
        assertThat(recetaBD.getFavorita()).isTrue();

    }


}
