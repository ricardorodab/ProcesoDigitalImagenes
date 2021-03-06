/* -------------------------------------------------------------------
* FiltroMosaico.java
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
 * @since Nov 11 2015.
 * <p>
 * Clase que nos da el filtro mosaico a una imagen.</p>
 *
 * <p>
 * Desde esta clase podemos obtener el filtro mosaico.</p>
 */
public class FiltroMosaico extends Filtro{
    
    /**
     * Constructor del filtro mosaico.
     * @param img - es la imagen que obtendrá el filtro.
     */
    public FiltroMosaico(Image img){
        super(img);
    }
    
    /**
     * Metodo que nos genera una imagen en mosaicos.
     * @param anchoX - Es el ancho de cada mosaico.
     * @param largoY - Es el largo de cada mosaico. 
     * @return - Una imagen pixeleada con mosaicos promedios.
     */
    public Image sacaMosaico(int anchoX, int largoY){
        return sacaMosaico(anchoX,largoY,true);
    }
    
    /**
     * Metodo que nos genera una imagen en mosaicos.
     * @param anchoX - Es el ancho de cada mosaico.
     * @param largoY - Es el largo de cada mosaico.
     * @param progreso - Es true si se quiere llevar un progreso. 
     * @return - Una imagen pixeleada con mosaicos promedios.
     */
    public Image sacaMosaico(int anchoX, int largoY, boolean progreso){
        if(progreso)
            Filtro.PROGRESO = 0;
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
       rojoRGB = verdeRGB = azulRGB = 0;
        //Aumentamos i,j cada ancho y largo de los mosaicos.
        for (int i = 0; i < this.getX(); i += anchoX) {
            terminoX = i+anchoX;
            for (int j = 0; j < this.getY(); j += largoY) {
                terminoY = j+largoY;
                //Dentro de cada i,j recorremos los anchoX*largoY pixeles del recuadro.
                //Para sacar el promedio del cuadrante.
                for (int k = i; k < terminoX; k++) {
                    if(k >= this.getX())
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= this.getY())
                            break;
                        Color colorOriginal = pixelI.getColor(k, l);
                        rojoRGB += colorOriginal.getRed();
                        verdeRGB += colorOriginal.getGreen();
                        azulRGB += colorOriginal.getBlue();
                        promedio++;
                    }
                }
                //Optenemos el promedio de cada rojo,verde,azul de dichos cuadrantes.
                red = (rojoRGB/promedio);
                green = (verdeRGB/promedio);
                blue = (azulRGB/promedio);
                //Reiniciamos los valores que sumaremos de cada region.
                rojoRGB = verdeRGB = azulRGB = promedio = 0;
                //Para ese mismo cuadrante pintamos esa region del promedio.
                for (int k = i; k < terminoX; k++) {
                    if(k >= this.getX())
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= this.getY())
                            break;
                        pixelD.setColor(k, l, Color.color(red, green, blue));
                        if(progreso)
                            Filtro.PROGRESO = this.avanzar()/this.getTotal();
                    }
                }
            }
        }
        return imagenD;
    }
} //Fin de FiltroMosaico.java