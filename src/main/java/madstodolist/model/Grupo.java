package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "grupos")
public class Grupo {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    public String nombre;

    @ManyToMany
    @JoinTable(name = "grupo_usuario",
            joinColumns = { @JoinColumn(name = "grupo_key") },
            inverseJoinColumns = { @JoinColumn(name = "usuario_key") })
    private Set<Usuario> usuarios = new HashSet<Usuario>();

    // Constructor vacío necesario para JPA/Hibernate.
    // Lo hacemos privado para que no se pueda usar desde el código de la aplicación. Para crear un
    // usuario en la aplicación habrá que llamar al constructor público. Hibernate sí que lo puede usar, a pesar
    // de ser privado.
    private Grupo() {}

    public Grupo(String nombre) {
        this.nombre = nombre;
    }


    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Set<Usuario> getUsuarios(){ return usuarios; }

    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }

    public void addUsuario(Usuario usuario) {
        usuario.getEquipos().add(this); // Linea necesaria para una consistencia en memoria
        this.usuarios.add(usuario);
    }

    public void removeUsuario(Usuario usuario) {
        usuario.getEquipos().remove(this);
        this.usuarios.remove(usuario);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(nombre);
    }
}
