/* -------------------------------------------------------------------
 * Filtro.java
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

import javafx.scene.image.Image;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Aug 20 2015.
 * <p>
 * Clase modelo de un filtro.</p>
 *
 * <p>
 * Con esta pequeña clase modelo lo que necesitan todos mis filtros actuales.</p>
 */
public class Filtro {
    
    public static double PROGRESO = 0;
    /** Imagen del filtro. */
    private final Image imagen;
    /** La altitud de la imagen. */
    private final int x;
    /** La longitud de la imagen. */
    private final int y;
    /** Total de los pixeles por recorrer. */
    private double totalAvance;
    /** Total de los pixeles recocridos. */
    private double contadorAvance;
    
    /**
     * Metodo construccion de un filtro.
     * @param imagen – es la imagen para el filtro.
     */
    public Filtro(Image imagen){
        this.imagen = imagen;
        this.x = (int)imagen.getWidth();
        this.y = (int)imagen.getHeight();
        this.totalAvance = y*x;
        this.contadorAvance = 0;
    }
    
    public double avanzar(){
        return this.contadorAvance++;
    }
    
    public double getContador(){
        return this.contadorAvance;
    }
    public void setContador(double avance){
        this.contadorAvance = avance;
    }
    public double getTotal(){
        return this.totalAvance;
    }
    public void setTotal(double total){
        this.totalAvance = total;
    }
    
    /**
     * Metodo para que nos de el largo de la imagen.
     * @return El largo de la imagen.
     */
    public int getX(){
        return this.x;
    }
    
    /**
     * Metodo para que nos de el ancho de la imagen.
     * @return El ancho de la imagen.
     */
    public int getY(){
        return this.y;
    }
    
    /**
     * Metodo para que nos regrese la imagen.
     * @return La imagen del filtro.
     */
    public Image getImage(){
        return this.imagen;
    }
    
    
} //Fin de Filtro.java