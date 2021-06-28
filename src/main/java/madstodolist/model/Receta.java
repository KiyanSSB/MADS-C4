package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "recetas")
public class Receta implements Serializable {
    private  static  final  long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String ingredientes;

    private Integer likes;

    private Boolean favorita;

    private Boolean compartida;



    //Relación muchas recetas, un usuario
    @ManyToOne
    //Nombre de la columna en la BD que guarda físicament el
    //ID del usuario con el que está asociado una tarea
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructor vacío necesario para JPA/Hibernate.
    // Lo hacemos privado para que no se pueda usar desde el código de la aplicación. Para crear un
    // usuario en la aplicación habrá que llamar al constructor público. Hibernate sí que lo puede usar, a pesar
    // de ser privado.
    private Receta() { }

    // Al crear una receta la asociamos automáticamente a un
    // usuario. Actualizamos por tanto la lista de recetas del
    // usuario.

    public Receta(Usuario usuario, String nombre, String ingredientes){
        this.usuario = usuario; //Usuario que tiene dicha receta
        this.nombre = nombre; //Nombre de la receta
        this.ingredientes = ingredientes; //Ingredientes que conforman la receta
        this.likes = 0;
        this.compartida = false;
        this.favorita = false;
        usuario.getRecetas().add(this);
    }



    //ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //Nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    //Ingredientes
    public String getIngredientes() {
        return ingredientes;
    }

    public void  setIngredientes(String ingredientes){
        this.ingredientes = ingredientes;
    }

    //Likes
    public Integer getLikes() {
        return likes;
    }
    public void sumarLike (){
        this.likes ++;
    }

    //Favorita
    public Boolean getFavorita() {
        return favorita;
    }

    public void setFavorita(Boolean favorita) {
        this.favorita = favorita;
    }

    //Compartida
    public Boolean getCompartida() {
        return compartida;
    }

    public void setCompartida(Boolean compartida) {
        this.compartida = compartida;
    }
}
