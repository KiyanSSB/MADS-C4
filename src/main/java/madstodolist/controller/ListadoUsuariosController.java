package madstodolist.controller;



import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.model.Usuario;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ListadoUsuariosController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;


    @GetMapping("/usuarios")
    public String listarUsuarios(Model model , HttpSession session){


        Long id = managerUserSession.usuarioLogeado(session);
        Usuario usuario = usuarioService.findById(id);

        Iterable<Usuario> usuarios = usuarioService.getAllUsers();
        model.addAttribute("usuarios" , usuarios);
        model.addAttribute("usuario" , usuario);

        return "listaUsuarios";
    }


    @GetMapping("/buscar/{id}")
    public String verPerfilBuscado(Model model, HttpSession session, @PathVariable(value="id") Long idUsuario){

        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }

        model.addAttribute("usuario" , usuario);


        return "perfilBuscado";
    }



}
