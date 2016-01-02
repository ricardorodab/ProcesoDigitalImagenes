/* -------------------------------------------------------------------
* VectorImage3D.java
* versión 1.0
* Copyright (C) 2015  José Ricardo Rodríguez Abreu.
* Facultad de Ciencias,
* Universidad Nacional Autónoma de México, Mexico.
*
* Este programa es software libre; se puede redistribuir
* y/o modificar en los términos establecidos por la
* Licencia Pública General de GNU tal como fue publicada
* por la Free Software Foundation en la versión 2 o
* superior.
*
* Este programa es distribuido con la esperanza de que
* resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
* sin la garantía implícita de COMERCIALIZACIÓN o
* ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
* Licencia Pública General de GNU para mayores detalles.
*
* Con este programa se debe haber recibido una copia de la
* Licencia Pública General de GNU, de no ser así, visite el
* siguiente URL:
* http://www.gnu.org/licenses/gpl.html
* o escriba a la Free Software Foundation Inc.,
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
* -------------------------------------------------------------------
*/
package proceso_de_imagenes;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Dic 31 2015.
 * <p>
 * Clase que nos ayuda a emular imagenes y puntos 3D.</p>
 *
 * <p>
 * Desde esta clase podemos obtener el comportamiento deseado de las imagenes
 * como si su color fuera un punto en el espacio.</p>
 */
public class VectorImage3D {
    
    /**
     * Es el valor del punto rojo.
     */
    private int rojo;
    /**
     * Es el valor del punto verde.
     */
    private int verde;
    /**
     * Es el valor del punto azul.
     */
    private int azul;
    /**
     * Es un URL a la imagen.
     */
    private String url;
    
    /**
     * Constructor de la clase Vector3D.
     * @param rojo - es el rojo.
     * @param verde - es el verde.
     * @param azul - es el azul.
     * @param imagenFuente - Es la imagen fuente en forma de String (URL).
     */
    public VectorImage3D(int rojo, int verde, int azul, String imagenFuente){
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
        this.url = imagenFuente;
    }
    
    /**
     * Metodo que nos regresa el URL de la imagen.
     * @return El URL en forma de String.
     */
    public String getImagen(){
        return this.url;
    }
    
    /**
     * Metodo para colocar una imagen.
     * @param imagenFuente - Es la imagen nueva (URL).
     */
    public void setImage(String imagenFuente){
        this.url = imagenFuente;
    }
    
    /**
     * Metodo que nos regresa el numero del color rojo.
     * @return En valor del rojo.
     */
    public int getRojo(){
        return this.rojo;
    }
    
    /**
     * Metodo que nos regresa el numero del color verde.
     * @return En valor del verde.
     */
    public int getVerde(){
        return this.verde;
    }
    
    /**
     * Metodo que nos regresa el numero del color azul.
     * @return En valor del azul
     */
    public int getAzul(){
        return this.azul;
    }
    
    /**
     * Metodo equals de la clase Vector3D
     * @param vector - es el vector a comparar.
     * @return - True si el elemento es igual.
     */
    public boolean equals(VectorImage3D vector){
        if(vector.getRojo() == this.rojo &&
                vector.getVerde() == this.verde &&
                vector.getAzul() == this.azul)
            return true;
        return false;
    }
}//Fin de VectorImage3D