package madstodolist.controller;


import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.TareaNotFoundException;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.model.Tarea;
import madstodolist.model.Receta;
import madstodolist.model.Usuario;
import madstodolist.service.RecetaService;
import madstodolist.service.TareaService;
import madstodolist.service.UsuarioService;
import madstodolist.service.UsuarioServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RecetaController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RecetaService recetaService;

    @Autowired
    ManagerUserSession managerUserSession;


    //Listado de todas las Recetas
    @GetMapping("/usuarios/{id}/recetas")
    public String listadoRecetas(@PathVariable(value = "id") Long idUsuario,
                                 Model model,
                                 HttpSession session){

        //Comprobamos que el usuario que accede a esta dirección es el registrado
        managerUserSession.comprobarUsuarioLogeado(session,idUsuario);
        Usuario usuario = usuarioService.findById(idUsuario);
        if(usuario == null){
            throw  new UsuarioNotFoundException();
        }

        List<Receta> recetas = recetaService.allRecetasUsuario(idUsuario);
        model.addAttribute("usuario" , usuario);
        model.addAttribute("recetas" , recetas);
        return "listaRecetas";
    }


    //Getter de la página para crear una nueva receta
    @GetMapping("/usuarios/{id}/recetas/nueva")
    public String formNuevaReceta(@PathVariable(value = "id") Long idUsuario,
                                  @ModelAttribute RecetaData recetaData,
                                  Model model,
                                  HttpSession session){
        //Comprobamos que el usuario que accede a esta dirección es el registrado
        managerUserSession.comprobarUsuarioLogeado(session,idUsuario);
        Usuario usuario = usuarioService.findById(idUsuario);
        if(usuario == null){
            throw  new UsuarioNotFoundException();
        }

        model.addAttribute("usuario", usuario);
        return "formNuevaReceta";
    }

    //Post de la página para crear la nueva receta
    @PostMapping("/usuarios/{id}/recetas/nueva")
    public String nuevaReceta(@PathVariable(value = "id") Long idUsuario,
                              @ModelAttribute RecetaData recetaData,
                              Model model, RedirectAttributes flash,
                              HttpSession session){

        managerUserSession.comprobarUsuarioLogeado(session,idUsuario);

        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null){
            throw new UsuarioNotFoundException();
        }

        recetaService.nuevaRecetaUsuario(idUsuario,recetaData.getNombre(),recetaData.getIngredientes());
        flash.addFlashAttribute("mensaje" ,  "Receta creada correctamente");
        return "redirect:/usuarios/" + idUsuario + "/recetas";
    }

    //Borrar una receta
    @DeleteMapping("/recetas/{id}")
    @ResponseBody
    public  String borrarReceta(@PathVariable(value = "id") Long idReceta,
                                RedirectAttributes flash, HttpSession session){
        Receta receta = recetaService.findById(idReceta);
        if(receta == null){
            throw  new RecetaNotFoundException();
        }
    }
}
