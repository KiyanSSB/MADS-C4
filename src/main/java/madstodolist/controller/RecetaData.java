package madstodolist.controller;

public class RecetaData {
    //Nombre
    public String nombre;
    public String getNombre() { return nombre;}
    public void setNombre(String nombre) { this.nombre = nombre;}

    //Ingredientes
    public String ingredientes;
    public String getIngredientes() { return ingredientes;};
    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes;};

    //Favorita
    public Boolean favorita;
    public Boolean getFavorita() { return favorita;};
    public void setFavorita(Boolean favorita) { this.favorita = favorita;};

}
