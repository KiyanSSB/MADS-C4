package madstodolist.service;

import madstodolist.model.Usuario;
import madstodolist.model.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UsuarioService {

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    public enum LoginStatus {LOGIN_OK, USER_NOT_FOUND, ERROR_PASSWORD}

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public LoginStatus login(String eMail, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(eMail);
        if (!usuario.isPresent()) {
            return LoginStatus.USER_NOT_FOUND;
        } else if (!usuario.get().getPassword().equals(password)) {
            return LoginStatus.ERROR_PASSWORD;
        } else {
            return LoginStatus.LOGIN_OK;
        }
    }

    // Se añade un usuario en la aplicación.
    // El email y password del usuario deben ser distinto de null
    // El email no debe estar registrado en la base de datos
    @Transactional
    public Usuario registrar(Usuario usuario) {
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioBD.isPresent())
            throw new UsuarioServiceException("El usuario " + usuario.getEmail() + " ya está registrado");
        else if (usuario.getEmail() == null)
            throw new UsuarioServiceException("El usuario no tiene email");
        else if (usuario.getPassword() == null)
            throw new UsuarioServiceException("El usuario no tiene password");
        else return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElse(null);
    }



    @Transactional
    public Usuario modificarUsuario(Long id, String email , String nombre){

        //Cogemos el usuario por el id
        Usuario modificarUsuario = usuarioRepository.findById(id).orElse(null);

        //Comprobamos que existe
        if (modificarUsuario == null) throw new UsuarioServiceException("El usuario que se intenta modificar no existe");

        //Si existe el usuario y no se produce ningún error, modificamos los valores
        modificarUsuario.setEmail(email);
        modificarUsuario.setNombre(nombre);


        //Guardamos el usuario en la base de datos
        usuarioRepository.save(modificarUsuario);
        return modificarUsuario;
    }

    @Transactional
    public void bloquearUsuario(Long idUsuario, Boolean bloquear){
        //Cogemos el usuario por el id
        Usuario modificarUsuario = usuarioRepository.findById(idUsuario).orElse(null);

        //Comprobamos que existe
        if (modificarUsuario == null) throw new UsuarioServiceException("El usuario que se intenta modificar no existe");

        //Si existe el usuario y no se produce ningún error, modificamos los valores
        modificarUsuario.setBloqueado(bloquear);

        //Guardamos el usuario en la base de datos
        usuarioRepository.save(modificarUsuario);
    }

    public Iterable<Usuario> getAllUsers(){
        return  usuarioRepository.findAll();
    }
}
