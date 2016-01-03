/* -------------------------------------------------------------------
* BlackLight.java
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
 * Clase que da el comportamiento de la tabla carreras.</p>
 *
 * <p>
 * Desde esta clase podemos obtener el comportamiento deseado de la tabla.</p>
 */
public class BlackLight extends Filtro{
    
    /** Alpha */
    private static final int a = 2;
    
    /**
     * Método constructor de la clase BlackLight.
     * @param img - es la imagen a aplicar el filtro.
     */
    public BlackLight(Image img){
        super(img);
    }
    
    /**
     * Metodo que saca una imagen con efecto blacklight
     * @return - la imagen con una imagen con efecto de luz negra.
     */
    public Image filtroBlackLight(){
        Filtro.PROGRESO = 0;
        int red,green,blue,blacklight, rojoRGB,verdeRGB,azulRGB;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                rojoRGB = (int) (colorOriginal.getRed()*255);
                verdeRGB = (int) (colorOriginal.getGreen()*255);
                azulRGB = (int) (colorOriginal.getBlue()*255);
                //Num blacklight = promedio de los colores.
                blacklight = ((rojoRGB+verdeRGB+azulRGB)/3);
                //Para cada color es:
                //Color = |color-blacklight|*alpha
                red = (Math.abs(rojoRGB-blacklight)*a);
                green = (Math.abs(verdeRGB-blacklight)*a);
                blue = (Math.abs(azulRGB-blacklight)+a);
                red = Math.min(Math.max(red, 0), 255);
                green = Math.min(Math.max(green, 0), 255);
                blue = Math.min(Math.max(blue, 0), 255);                
                pixelD.setColor(i, j, Color.rgb(red, green, blue));
                Filtro.PROGRESO = (this.avanzar()/this.getTotal());
            }
        }
        return imagenD;
    }
} //Fin de BlackLight.java
