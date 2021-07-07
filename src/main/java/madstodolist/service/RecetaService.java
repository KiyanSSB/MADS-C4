package madstodolist.service;

import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RecetaService {
    Logger logger = LoggerFactory.getLogger(TareaService.class);

    private UsuarioRepository usuarioRepository;
    private RecetaRepository recetaRepository;

    //Constructor del servicio
    @Autowired
    public RecetaService(UsuarioRepository usuarioRepository, RecetaRepository recetaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.recetaRepository = recetaRepository;
    }

    //Listado de recetas de un usuario
    @Transactional (readOnly = true) //Definimos que es una operación de solo lectura
    public List<Receta> allRecetasUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null){
            throw new UsuarioNotFoundException();
        }

        List<Receta> recetas = new ArrayList<>(usuario.getRecetas());
        Collections.sort(recetas, (a,b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return  recetas;
    }

    //Añadir receta a un usuario
    @Transactional
    public  Receta nuevaRecetaUsuario(Long idUsuario,
                                      String nombre,
                                      String ingredientes
                                      ){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null){
            throw new UsuarioNotFoundException();
        }

        Receta receta = new Receta(usuario, nombre , ingredientes);
        recetaRepository.save(receta);
        return receta;
    }

    //Encontrar una receta por id
    @Transactional
    public Receta findById(Long idReceta){ return recetaRepository.findById(idReceta).orElse(null);}


    //Borrar una receta
    @Transactional
    public void borrarReceta(Long idReceta){
        Receta receta = recetaRepository.findById(idReceta).orElse(null);

        if(receta == null ){
            throw new RecetaServiceException("Receta para borrar no encontrada");

        }
        recetaRepository.delete(receta);
    }

    @Transactional
    public void setFavorito(Long idReceta,Boolean favorito){
        Receta receta = recetaRepository.findById(idReceta).orElse(null);
        if (receta == null) {
            throw new RecetaServiceException("Receta para añadir a favoritos no encontrada");
        }

        receta.setFavorita(favorito);
        recetaRepository.save(receta);
    }


    @Transactional
    public void setCompartida(Long idReceta,Boolean compartida){
        Receta receta = recetaRepository.findById(idReceta).orElse(null);
        if (receta == null) {
            throw new RecetaServiceException("Receta para añadir a compartidas no encontrada");
        }

        receta.setCompartida(compartida);
        recetaRepository.save(receta);
    }

    @Transactional
    public void darLike(Long idReceta){
        Receta receta = recetaRepository.findById(idReceta).orElse(null);
        if (receta == null) {
            throw new RecetaServiceException("Receta para añadir a compartidas no encontrada");
        }

        receta.sumarLike();
        recetaRepository.save(receta);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////    TDD   ////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Transactional
    public Receta modificarTarea(Long idReceta, String nombre , String ingredientes, Boolean compartido ,Boolean favorito){
        Receta receta = recetaRepository.findById(idReceta).orElse(null);
        if (receta== null){
            throw new RecetaServiceException("Receta para añadir a compartidas no encontrada");
        }

        receta.setNombre(nombre);
        receta.setIngredientes(ingredientes);
        receta.setCompartida(compartido);
        receta.setFavorita(favorito);

        recetaRepository.save(receta);

        return receta;
    }
}
