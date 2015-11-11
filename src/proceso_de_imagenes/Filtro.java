/* -------------------------------------------------------------------
 * ProyectoProcesoImagenes.java
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
    
    /** Imagen del filtro. */
    protected Image imagen;
    /** La altitud de la imagen. */
    protected int x;
    /** La longitud de la imagen. */
    protected int y;
    
    /**
     * Metodo construccion de un filtro.
     * @param imagen – es la imagen para el filtro.
     */
    public Filtro(Image imagen){
        this.imagen = imagen;
        this.x = (int)imagen.getWidth();
        this.y = (int)imagen.getHeight();
    }
    
    
} //Fin de Filtro.java