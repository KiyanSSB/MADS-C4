package madstodolist.controller;


import madstodolist.authentication.ManagerUserSession;
import madstodolist.authentication.UsuarioNoLogeadoException;
import madstodolist.model.Grupo;
import madstodolist.model.Usuario;
import madstodolist.service.GrupoService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ListadoGruposController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    GrupoService grupoService;


    @GetMapping("/grupos")
    public String listarGrupos(Model model , HttpSession session){

        Long id = managerUserSession.usuarioLogeado(session);
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario" , usuario);

        if(managerUserSession.comprobarUsuarioLogeado(session, managerUserSession.usuarioLogeado(session))){

        }else{
            throw new UsuarioNoLogeadoException();
        }

        //Obtenemos la lista de equipos
        List<Grupo> grupos = grupoService.findAll();
        model.addAttribute("grupos", grupos);

        return "listaGrupos";
    }


    @GetMapping("/grupos/crear")
    public String crearGrupos(Model model, HttpSession session, @ModelAttribute GrupoData grupoData){

        Long id = managerUserSession.usuarioLogeado(session);
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario" , usuario);

        return "formNuevoGrupo";
    }

    @PostMapping("/grupos/crear")
    public String crearGrupoPost(Model model, HttpSession session,
                                 @ModelAttribute GrupoData grupoData){

        Long id = managerUserSession.usuarioLogeado(session);
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario" , usuario);

        Grupo grupo = grupoService.crear(grupoData.nombre,id);

        return "redirect:/grupos";
    }

}
