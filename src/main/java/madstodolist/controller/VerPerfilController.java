package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.model.Usuario;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class VerPerfilController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;


    //Para ver el perfil de un usuario
    @GetMapping("/perfil/{id}")
    public String verPerfil(@PathVariable(value = "id") Long idUsuario,
                            @ModelAttribute TareaData tareaData, Model model,
                            HttpSession session) {

        //Comprobamos si la persona que está intentando acceder al perfil es la
        //Misma que está logeada, si no lo impedimos
        managerUserSession.comprobarUsuarioLogeado(session, idUsuario);
        Usuario usuario = usuarioService.findById(idUsuario);

        //Si el usuario no se encuentra
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }

        //Vinculamos el usuario al perfil
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarioLogeado", managerUserSession.usuarioLogeado(session));
        return "verPerfil";
    }


    //Getter de la página de modificar el usuario
    @GetMapping("/perfil/{id}/editar")
    public String formEditaUsuario(@PathVariable(value = "id") Long idUsuario,
                                   Model model,
                                   @ModelAttribute Usuario usuarioData,
                                   HttpSession session) {

        //Comprobamos si la persona que está intentando acceder al perfil es la
        //Misma que está logeada, si no lo impedimos
        managerUserSession.comprobarUsuarioLogeado(session, idUsuario);

        //Comprobamos que existe el usuario
        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }

        //Si existe el usuario, guardamos en el usuarioData el contenido de los datos del usuario para pasarlos al formulario
        usuarioData.setEmail(usuario.getEmail());
        usuarioData.setNombre(usuario.getNombre());
        usuarioData.setFechaNacimiento(usuario.getFechaNacimiento());

        model.addAttribute("usuarioData", usuarioData);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarioLogeado", session.getAttribute("usuarioLogeado"));
        model.addAttribute("idUsuarioLogeado", session.getAttribute("idUsuarioLogeado"));
        return "formEditarUsuario";
    }


    @PostMapping("/perfil/{id}/editar")
    public String editarUsuario(@PathVariable(value = "id") Long idUsuario,
                                Model model,
                                @ModelAttribute Usuario usuarioData,
                                RedirectAttributes flash, HttpSession session)
            throws Exception {

        //Comprobamos que el usuario que quiere modificar es el mismo que se modifica
        managerUserSession.comprobarUsuarioLogeado(session, idUsuario);

        //Modificamos el usuario
        usuarioService.modificarUsuario(idUsuario, usuarioData.getEmail(), usuarioData.getNombre());

        //Si no se producen errores, mostramos el mensaje
        flash.addFlashAttribute("mensaje", "Usuario modificado correctamente");
        model.addAttribute("usuarioLogeado", session.getAttribute("usuarioLogeado"));
        model.addAttribute("idUsuarioLogeado", session.getAttribute("idUsuarioLogeado"));
        return "redirect:/perfil/" + idUsuario;
    }

}
