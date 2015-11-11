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
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import jfx.messagebox.MessageBox;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Aug 20 2015.
 * <p>
 * Clase de filtros en Gris.</p>
 *
 * <p>
 * Desde aqui se modelan los filtros en gris.</p>
 */
public class FiltroGris extends Filtro{
    
    public static final int ROJO = 0;
    public static final int VERDE = 1;
    public static final int AZUL = 2;
    
    
    /**
     * Es el metodo constructor de los filtros.
     * @param imagen – es la imagen a pasar en filtro.
     */
    public FiltroGris(Image imagen){
        super(imagen);
    }
    
    /**
     * Es el metodo que nos da los filtros en grises por promedio.
     * @return Una imagen que es en escala de grises.
     */
    public Image grisPromedio(){
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                double gris = ((color.getBlue()+color.getGreen()+color.getRed())/3);
                Color color2 = new Color(gris, gris, gris, color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
    
    /**
     * Metodo que nos da los filtros en grises por valores fijos.
     * @return Una imagen que es en escala de grises.
     */
    public Image grisValores(){
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                double gris = (color.getRed()*.3)+ (color.getGreen()*.59)+ (color.getBlue()*.11);
                Color color2 = new Color(gris,gris,gris, color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
    /**
     * Metodo que nos da los filtros en grises por valores fijos.
     * @return Una imagen que es en escala de grises.
     */
    public Image grisDesaturacion(){
        double r,g,b;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                double gris = (Math.max(Math.max(r, g),b)+Math.min(Math.min(r, g),b))/2;
                Color color2 = new Color(gris,gris,gris, color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
    
    public Image grisDescomposicion(boolean max){
        double r,g,b;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                double gris;
                if(max){
                    gris = Math.max(Math.max(r, g),b);
                }else{
                    gris = Math.min(Math.min(r, g),b);
                }
                Color color2 = new Color(gris,gris,gris, color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
    
    public Image grisColor(int numColor){
        if (numColor != 0 &&
                numColor != 1 &&
                numColor != 2) {
            MessageBox.show(null, "Error de color", "Color no encontrado",
                    MessageBox.ICON_ERROR | MessageBox.ABORT);
        }
        double r,g,b;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelReader pixelI = this.imagen.getPixelReader();
        PixelWriter pixelD = imagenD.getPixelWriter();
        Color color;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                color = pixelI.getColor(i, j);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                double gris;
                if(numColor == 0){
                    gris = r;
                }else if(numColor == 2){
                    gris = g;
                }else{
                    gris = b;
                }
                Color color2 = new Color(gris,gris,gris, color.getOpacity());
                pixelD.setColor(i, j, color2);
            }
        }
        return imagenD;
    }
} //Fin de FiltroGris.java