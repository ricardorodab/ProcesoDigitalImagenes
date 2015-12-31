/* -------------------------------------------------------------------
* Blending.java
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
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Dic 31 2015.
 * <p>
 * Clase que da el comportamiento del filtro Blending.</p>
 *
 * <p>
 * Desde esta clase podemos obtener una imagen compuesta de dos diferentes.</p>
 */
public class Blending extends Filtro{
    
    /**
     * Método constructor de la clase  Blending.
     * @param imagen - es la imagen a aplicar el filtro.
     */
    public Blending(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo que nos fusiona dos imagenes digitales.
     * @param segunda - es la segunda imagen a fusionar.
     * @param porcentaje - es el porsentaje de la segunda imagen.
     * @return una imagen compuesta de dos: La del filtro y la segunda.
     */
    public Image licua(Filtro segunda, double porcentaje){
        int red,green,blue;
        int w,h;
        w = Math.min(this.getX(), segunda.getX());
        h = Math.min(this.getY(), segunda.getY());
        WritableImage imagenD = new WritableImage(w, h);
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        PixelReader pixelI2 = segunda.getImage().getPixelReader();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                Color colorOriginal2 = pixelI2.getColor(i, j);
                //Color = (color * potcentaje) + (color * (1 - porcentaje))
                red = (int)(Math.min(Math.max((255*((colorOriginal.getRed()*porcentaje)
                        + (colorOriginal2.getRed()* (1 - porcentaje)))), 0), 255));
                green = (int)(Math.min(Math.max((255*((colorOriginal.getGreen()*porcentaje)
                        + (colorOriginal2.getGreen()* (1 - porcentaje)))), 0), 255));
                blue = (int)(Math.min(Math.max((255*((colorOriginal.getBlue()*porcentaje)
                        + (colorOriginal2.getBlue()* (1 - porcentaje)))), 0), 255));
                pixelD.setColor(i, j, Color.rgb(red, green, blue));
            }
        }
        return imagenD;
    }
} //Fin de Blending.java