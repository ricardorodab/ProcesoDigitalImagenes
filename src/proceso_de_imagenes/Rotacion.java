/* -------------------------------------------------------------------
* Rotacion.java
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
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Dic 16 2015.
 * <p>
 * Clase que da el comportamiento de la tabla carreras.</p>
 *
 * <p>
 * Desde esta clase podemos obtener el comportamiento deseado de la tabla.</p>
 */
public class Rotacion extends Filtro{
    
    /** Entero para representar 0º */
    public static final int CERO = 0;
    /** Entero para representar 90º */
    public static final int NOVENTA = 90;
    /** Entero para representar 180º */
    public static final int CIENTO_OCHENTA = 180;
    /** Entero para representar 270º */
    public static final int DOSCIENTOS_SETENTA = 270;
    
    /**
     * Metodo constructor de imagenes rotadas.
     * @param imagen - Es la imagen a rotar.
     */
    public Rotacion(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo para girar una imagen por el metodo de matriz de rotacion.
     * @param grados - Es la cantidad de grados a girar.
     * @return Una imagen rotada.
     */
    public Image rotarMatriz(int grados){
        Filtro.PROGRESO = 0;
        int red,green,blue;
        WritableImage imagenD = null;
        if(grados == NOVENTA || grados == DOSCIENTOS_SETENTA){
            imagenD = new WritableImage(this.getY(), this.getX());
        }else if(grados == CIENTO_OCHENTA){
            imagenD = new WritableImage(this.getX(), this.getY());
        }else if (grados == CERO){
            return this.getImage();
        }else{
            MessageBox.show(new Stage(), "Los grados sólo pueden ser 0º,90º,180º y 270º",
                    "Error crítico", MessageBox.ICON_ERROR | MessageBox.OK);
        }
        double cos,sin;
        if(grados == NOVENTA || grados == DOSCIENTOS_SETENTA){
            //Para evitar problemas con Sen de 90º y Cos 0º
            sin = Math.sin(Math.toRadians(grados+.01));
            cos = Math.cos(Math.toRadians(grados));
        }else{
            cos = Math.cos(Math.toRadians(grados+01));
            sin = Math.sin(Math.toRadians(grados));
        }
        int mitadX = (this.getX()/2)-1;
        int mitadY = (this.getY()/2)-1;
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                red = (int) (colorOriginal.getRed()*255);
                green = (int) (colorOriginal.getGreen()*255);
                blue = (int) (colorOriginal.getBlue()*255);
                int newI = i - mitadX;
                int newJ = j - mitadY;
                int newX = (int)((newI*cos)+(newJ*sin));
                int newY = (int)((-1*newI*sin)+(newJ*cos));
                if(grados == NOVENTA){
                    newX += mitadY;
                    newY += mitadX;
                }else if(grados == CIENTO_OCHENTA){
                    newX += mitadX;
                    newY += mitadY;
                }else{
                    newX += mitadY;
                    newY += mitadX;
                }
                if (newX < 0 || newY < 0) {
                    continue;
                }
                pixelD.setColor(newX,newY, Color.rgb(red, green, blue));
                Filtro.PROGRESO = (this.avanzar()/this.getTotal());
            }
        }
        return imagenD;
    }
    
    /**
     * Metodo para girar una imagen por el metodo de formula.
     * @param grados - Es la cantidad de grados a girar.
     * @return Una imagen rotada.
     */
    public Image rotar(int grados){
        Filtro.PROGRESO = 0;
        int red,green,blue;
        WritableImage imagenD = null;
        if(grados == NOVENTA || grados == DOSCIENTOS_SETENTA){
            imagenD = new WritableImage(this.getY(), this.getX());
        }else if(grados == CIENTO_OCHENTA){
            imagenD = new WritableImage(this.getX(), this.getY());
        }else if (grados == CERO){
            return this.getImage();
        }else{
             MessageBox.show(new Stage(), "Los grados sólo pueden ser 0º,90º,180º y 270º",
                    "Error crítico", MessageBox.ICON_ERROR | MessageBox.OK);
        }
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                red = (int) (colorOriginal.getRed()*255);
                green = (int) (colorOriginal.getGreen()*255);
                blue = (int) (colorOriginal.getBlue()*255);
                if(grados == NOVENTA){
                    pixelD.setColor(j, this.getX()-i-1, Color.rgb(red, green, blue));
                }else if(grados == CIENTO_OCHENTA){
                    pixelD.setColor(this.getX()-i-1, this.getY()-j-1, Color.rgb(red, green, blue));
                }else{
                    pixelD.setColor(this.getY()-j-1, i, Color.rgb(red, green, blue));
                }
                Filtro.PROGRESO = (this.avanzar()/this.getTotal());
            }
        }
        return imagenD;
    }
}//Fin de Rotacion.java