/* -------------------------------------------------------------------
* AltoContrasteFiltroInverso.java
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
 * Clase que da el comportamiento del filtro Alto contraste e inverso.</p>
 *
 * <p>
 * Desde esta clase podemos obtener el comportamiento de 2 filtros:
 * Alto contraste e inverso.</p>
 */
public class AltoContrasteFiltroInverso extends Filtro{
    
    /**
     * Método constructor de la clase  AltoContrasteFiltroInverso.
     * @param imagen - es la imagen a aplicar el filtro.
     */
    public AltoContrasteFiltroInverso(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo que nos da el filtro inverso.
     * @return un filtro en blanco y negro donde los colores n > 127 son negro.
     */
    public Image inverso() {
        int rojoRGB,verdeRGB,azulRGB;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                rojoRGB = (int) (colorOriginal.getRed()*255);
                verdeRGB = (int) (colorOriginal.getGreen()*255);
                azulRGB = (int) (colorOriginal.getBlue()*255);
                //Si el promedio es menor que 127 pinta de blanco.
                if( ((rojoRGB + verdeRGB + azulRGB)/3) < 127 ){
                    pixelD.setColor(i, j, Color.rgb(255, 255, 255));
                }else{
                    pixelD.setColor(i, j, Color.rgb(0, 0, 0));
                }
            }
        }
        return imagenD;
    }
    
    /**
     * Metodo que nos da el filtro de un alto contraste.
     * @return un filtro en blanco y negro donde los colores n > 127 son blancos.
     */
    public Image altoContraste() {
        int rojoRGB,verdeRGB,azulRGB;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                rojoRGB = (int) (colorOriginal.getRed()*255);
                verdeRGB = (int) (colorOriginal.getGreen()*255);
                azulRGB = (int) (colorOriginal.getBlue()*255);
                //Si el promedio es mayor que 127 pinta de blanco.
                if( ((rojoRGB + verdeRGB + azulRGB)/3) > 127 ){
                    pixelD.setColor(i, j, Color.rgb(255, 255, 255));
                }else{
                    pixelD.setColor(i, j, Color.rgb(0, 0, 0));
                }
            }
        }
        return imagenD;
    }
} //Fin de AltoContrasteFiltroInverso.java