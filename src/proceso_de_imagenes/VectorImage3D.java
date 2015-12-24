/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

/**
 *
 * @author ricardo_rodab
 */
public class VectorImage3D {
    
    private int rojo;
    private int verde;
    private int azul;
    private String url;
    
    public VectorImage3D(int rojo, int verde, int azul, String imagenFuente){
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
        this.url = imagenFuente;
    }
    
    public String getImagen(){
        return this.url;
    }
    
    public void setImage(String imagenFuente){
        this.url = imagenFuente;
    }
    
    public int getRojo(){
        return this.rojo;
    }
    
    public int getVerde(){
        return this.verde;
    }
    
    public int getAzul(){
        return this.azul;
    }
    
    public boolean equals(VectorImage3D vector){
        if(vector.getRojo() == this.rojo &&
                vector.getVerde() == this.verde &&
                vector.getAzul() == this.azul)
            return true;
        return false;
    }
}
