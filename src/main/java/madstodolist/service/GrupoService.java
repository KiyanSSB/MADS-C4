package madstodolist.service;

import madstodolist.model.Grupo;
import madstodolist.model.GrupoRepository;

import madstodolist.model.Usuario;
import madstodolist.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    private GrupoRepository grupoRepository;

    private UsuarioRepository usuarioRepository;


    @Autowired
    public GrupoService(GrupoRepository grupoRepository, UsuarioRepository usuarioRepository) {
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Grupo crear(String nombreGrupo, Long idUsuario) {

        if ( usuarioRepository.findById(idUsuario).orElse(null) == null) {
            throw new TareaServiceException("Usuario " + idUsuario + " no existe al crear el equipo " + nombreGrupo);
        }

        Grupo grupo = new Grupo(nombreGrupo);
        grupoRepository.save(grupo);

        grupo.addUsuario(usuarioRepository.findById(idUsuario).orElse(null));

        return grupo;
    }

    @Transactional(readOnly = true)
    public List<Grupo> findAll() {
        List<Grupo> grupos = grupoRepository.findAll();

        return grupos;
    }

    @Transactional(readOnly = true)
    public Grupo findById(Long id){
        Grupo grupo = grupoRepository.findById(id).orElse(null);
        return  grupo;
    }


    @Transactional
    public void añadirUsuarioGrupo(Long idGrupo,Usuario usuario){
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        grupo.addUsuario(usuario);
        grupoRepository.save(grupo);
    }
}

