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

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Aug 20 2015.
 * <p>
 * Clase de filtros RGB.</p>
 *
 * <p>
 * Desde esta clase creamos los filtros RGB.</p>
 */
public class FiltroRGB extends Filtro{
    
    /**
     * Metodo constructor del filtro RGB.
     * @param imagen – es la imagen para los filtros.
     */
    public FiltroRGB(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo que nos da el filtro verde.
     * @return la imagen con el filtro verde.
     */
    public Image verde(){
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                Color color2 = new Color(0, color.getGreen(), 0, color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
    
    /**
     * Metodo que nos da el filtro azul.
     * @return la imagen con el filtro azul.
     */
    public Image azul(){
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                Color color2 = new Color(0, 0, color.getBlue(), color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
    
    /**
     * Metodo que nos da el filtro rojo
     * @return la imagen con el filtro rojo.
     */
    public Image rojo(){
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                Color color2 = new Color(color.getRed(), 0, 0, color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
    
    /**
     * Metodo que nos da el filtro azar
     * @return la imagen con el filtro azar.
     */
    public Image azar(){
        Random aleatorio = new Random();
        int r,g,b;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelWriter pixelD = imagenD.getPixelWriter();
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {                
                r = aleatorio.nextInt(255);
                g = aleatorio.nextInt(255);
                b = aleatorio.nextInt(255);
                pixelD.setColor(i, j, Color.rgb(r, g, b));
            }
        }
        return imagenD;
    }
    
} //Fin de FiltroRGB.java