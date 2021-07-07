package madstodolist.controller;


import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.RecetaNotFoundException;
import madstodolist.controller.exception.TareaNotFoundException;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.model.Tarea;
import madstodolist.model.Receta;
import madstodolist.model.Usuario;
import madstodolist.service.RecetaService;
import madstodolist.service.TareaService;
import madstodolist.service.UsuarioService;
import madstodolist.service.UsuarioServiceException;
import org.apache.tomcat.util.http.parser.HttpParser;
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


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                          FAVORITOS                     ////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //Listado de todas las recetas FAVORITAS
    //Listado de todas las Recetas
    @GetMapping("/usuarios/{id}/recetas/favoritas")
    public String listadoRecetasFavoritas(@PathVariable(value = "id") Long idUsuario,
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
        return "listaRecetasFavoritas";
    }

    //Añade una receta a FAVORITAs
    @PostMapping("/recetas/{id}/favoritas")
    @ResponseBody
    public String recetaFavorita(@PathVariable(value = "id") Long idReceta){
        Receta receta = recetaService.findById(idReceta);
        if(receta == null){
            throw new RecetaNotFoundException();
        }
        recetaService.setFavorito(idReceta,true);
        return "";
    }

    //Elimina una receta a FAVORITAs
    @PostMapping("/recetas/{id}/quitarfavoritas")
    @ResponseBody
    public String QuitarrecetaFavorita(@PathVariable(value = "id") Long idReceta){
        Receta receta = recetaService.findById(idReceta);
        if(receta == null){
            throw new RecetaNotFoundException();
        }
        recetaService.setFavorito(idReceta,false);
        return "";
    }


    //Borrar una receta
    @DeleteMapping("/recetas/{id}")
    @ResponseBody
    public  String borrarReceta(@PathVariable(value = "id") Long idReceta,
                                RedirectAttributes flash, HttpSession session){
        Receta receta = recetaService.findById(idReceta);
        if(receta == null){
            throw new RecetaNotFoundException();
        }

        managerUserSession.comprobarUsuarioLogeado(session, receta.getUsuario().getId());

        recetaService.borrarReceta(idReceta);
        return "";
    }


    //Listar recetas compartidas de un usuario buscado
    @GetMapping("/buscar/{id}/compartidas")
    public String listarRecetasCompartidas(@PathVariable (value = "id") Long idUsuario,
                                           Model model, HttpSession session){

        Usuario usuario =  usuarioService.findById(idUsuario);
        model.addAttribute("usuario", usuario);

        return "listaRecetasCompartidas";
    }


    ///Añadir una receta a compartidas
    @PostMapping("/recetas/{id}/compartidas")
    @ResponseBody
    public String recetaCompartida(@PathVariable(value = "id") Long idReceta){
        Receta receta = recetaService.findById(idReceta);
        if(receta == null){
            throw new RecetaNotFoundException();
        }
        recetaService.setCompartida(idReceta,true);
        return "";
    }


    //
    @PostMapping("/copiar/{id}")
    @ResponseBody
    public void recetaCopiada(@PathVariable(value = "id") Long idReceta, HttpSession session){
        Receta receta = recetaService.findById(idReceta);
        if (receta == null){
            throw new RecetaNotFoundException();
        }

        Long id = managerUserSession.usuarioLogeado(session);
        managerUserSession.comprobarUsuarioLogeado(session,id);

        recetaService.nuevaRecetaUsuario(id,receta.getNombre(),receta.getIngredientes());
    }
}
