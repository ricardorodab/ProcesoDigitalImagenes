/* -------------------------------------------------------------------
* AmplicacionReduccion.java
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

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Dic 28 2015.
 * <p>
 * Clase que da el comportamiento de apliacion y reduccion de imagenes.</p>
 *
 * <p>
 * Desde esta clase podemos reducir o ampliar una imagen digital.</p>
 */
public class AmpliacionReduccion extends Filtro{
    
    /**
     * Método constructor de la clase  AmpliacionReduccion.
     * @param imagen - es la imagen a aplicar el filtro.
     */
    public AmpliacionReduccion(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo que aplica o reduce una imagen.
     * @param newX - es la nueva coordenada X.
     * @param newY - es la neuva coordenada Y.
     * @return la nueva imagen con tamaño X,Y
     */
    public Image apliaReduce(int newX, int newY){
        Filtro.PROGRESO = .25;
        Image icono = this.getImage();
        BufferedImage mosaico = SwingFXUtils.fromFXImage(icono, null);
        //Llamo al metodo que saca mosaicos o iconos.
        java.awt.Image imagen2 = mosaico.getScaledInstance(newX, newY, java.awt.Image.SCALE_SMOOTH);
        BufferedImage bi = new BufferedImage(newX, newY, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(imagen2, 0, 0, null);
        Filtro.PROGRESO = .75;
        return SwingFXUtils.toFXImage(bi, null);
    }
} //Fin de AmpliacionReduccion.java    
