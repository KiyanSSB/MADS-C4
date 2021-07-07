package madstodolist.service;

import madstodolist.model.Grupo;
import madstodolist.model.GrupoRepository;

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
        List<Grupo> equipos = grupoRepository.findAll();

        return equipos;
    }


}

