package madstodolist.aplicacion;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.model.Receta;
import madstodolist.model.Tarea;
import madstodolist.model.Usuario;
import madstodolist.service.RecetaService;
import madstodolist.service.TareaService;
import madstodolist.service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecetaWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private RecetaService recetaService;

    // Al mocker el manegerUserSession, no lanza la excepción cuando
    // se intenta comprobar si un usuario está logeado
    @MockBean
    private ManagerUserSession managerUserSession;

    @Test
    public void editarRecetaDevuelveForm() throws Exception {
        Receta receta = new Receta( new Usuario("a@a.com") , "prueba" , "prueba");
        receta.setId(1L);
        receta.getUsuario().setId(1L);

        when(recetaService.findById(1L)).thenReturn(receta);

        this.mockMvc.perform(get("/recetas/1/modificar"))
                .andExpect(content().string(allOf(
                        // Contiene el texto de la tarea a editar
                        containsString("Nombre de la Receta:"))));
    }
}
