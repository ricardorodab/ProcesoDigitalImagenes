/* -------------------------------------------------------------------
* FiltroOleo.java
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Dic 31 2015.
 * <p>
 * Clase que da Una imagen con un efecto de pintura en oleo.</p>
 *
 * <p>
 * Desde esta clase podemos obtener una imagen que parece fue pintada en oleo.</p>
 */
public class FiltroOleo extends Filtro{
    
    /**
     * Cantidad de vecindades que se visitan.
     * YO RECOMIENDO un número entre 5 y 10.
     */
    private static final int radio = 7;
    /**
     * Metodo constructor de filtros tipo oleos.
     * @param imagen - es la imagen que optendremos el filtro.
     */
    public FiltroOleo(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo que nos da una imagen con un filtro mediana.
     * @return - una imagen sin ruido que es producto de optener medianas.
     */
    public Image mediana(){
        int anchoX,largoY;
        anchoX = largoY = 3;
        //Listas para almacenar los promedios de cierta vecindad.
        LinkedList<Double> medianaR = new LinkedList<>();
        LinkedList<Double> medianaG = new LinkedList<>();
        LinkedList<Double> medianaB = new LinkedList<>();
        int terminoX,terminoY;
        double red,green,blue;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        red = green = blue = 0;
        terminoX = anchoX;
        terminoY = largoY;
        for (int i = 0; i < this.getX(); i++) {
            terminoY = largoY;
            terminoX = i+anchoX;
            for (int j = 0; j < this.getY(); j++) {
                terminoY = j+largoY;
                
                for (int k = i; k < terminoX; k++) {
                    if(k >= this.getX())
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= this.getY())
                            break;
                        Color colorOriginal = pixelI.getColor(k, l);
                        //Agrego los promedios de las vecindades a la lista.
                        medianaR.add(colorOriginal.getRed());
                        medianaG.add(colorOriginal.getGreen());
                        medianaB.add(colorOriginal.getBlue());
                    }
                    
                }
                //Tomo la mitad de una lista (Las 3 tienen el mismo largo).
                int mitad = medianaR.size()/2;
                //Ordeno las listas.
                medianaR.sort(null);
                medianaG.sort(null);
                medianaB.sort(null);
                //Tomo el valor de medio de las listas.
                red = medianaR.get(mitad);
                green = medianaG.get(mitad);
                blue = medianaB.get(mitad);
                //Limpio las listas para la nueva vecindad.
                medianaR.clear();
                medianaG.clear();
                medianaB.clear();                
                pixelD.setColor(i, j, Color.color(red, green, blue));
            }            
        }
        return imagenD;
    }
    
    /**
     * Metodo que nos da una imagen con un filtro oleo.
     * @return - una imagen que parece fue pintada con oleo.
     */
    public Image oleo(){
        Filtro.PROGRESO = 0;
        double totalAvance = this.getX()*this.getY();
        double contadorAvance = 0;
        int anchoX,largoY;
        //Cantidad de vecindades que se visitan.
        //YO RECOMIENDO un número entre 5 y 10.
        anchoX = largoY = radio;
        //Tengo 3 mapas (3 colores) para ver repeticiones: 
        //K = PromedioColorRGB
        //V = Cantidad de veces vistos en vecindad.
        Map<Double,Integer> frecuenciaR = new HashMap<Double,Integer>();
        Map<Double,Integer> frecuenciaG = new HashMap<Double,Integer>();
        Map<Double,Integer> frecuenciaB = new HashMap<Double,Integer>();
        int terminoX,terminoY;
        double red,green,blue;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        red = green = blue = 0;
        terminoX = anchoX;
        terminoY = largoY;
        for (int i = 0; i < this.getX(); i++) {
            terminoY = largoY;
            terminoX = i+anchoX;
            for (int j = 0; j < this.getY(); j++) {
                terminoY = j+largoY;
                //Tomo el double maximo poder sustituir siempre.
                double maxR = Double.NEGATIVE_INFINITY;
                double maxG = Double.NEGATIVE_INFINITY;
                double maxB = Double.NEGATIVE_INFINITY;                      
                for (int k = i; k < terminoX; k++) {
                    if(k >= this.getX())
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= this.getY())
                            break;
                        Color colorOriginal = pixelI.getColor(k, l);
                        //Si es la primera vez que leo un pixel lo sustituyo en los valores por default.
                        if(maxR == Double.NEGATIVE_INFINITY){
                            maxR = colorOriginal.getRed();
                            maxG = colorOriginal.getGreen();
                            maxB = colorOriginal.getBlue();
                        }
                        //Veo su frecuencia si existe.
                        //Si está en el diccionario entonces lo busco y aumento su frecuencia.
                        //Reviso si su nueva refuencia es mayor y asigna al maxRGB el que mayor frecuencia tenga.
                        //Para todo R.G.B.  
                        //ROJO:
                        if(frecuenciaR.containsKey(colorOriginal.getRed())){
                            frecuenciaR.put(colorOriginal.getRed(), frecuenciaR.get(colorOriginal.getRed())+1);
                            maxR = frecuenciaR.get(maxR) > frecuenciaR.get(colorOriginal.getRed()) ? maxR : colorOriginal.getRed();
                        }else{
                            frecuenciaR.put(colorOriginal.getRed(), 1);
                        }
                        //VERDE:
                        if(frecuenciaG.containsKey(colorOriginal.getGreen())){
                            frecuenciaG.put(colorOriginal.getGreen(), frecuenciaG.get(colorOriginal.getGreen())+1);
                            maxG = frecuenciaG.get(maxG) > frecuenciaG.get(colorOriginal.getGreen()) ? maxG : colorOriginal.getGreen();
                        }else{
                            frecuenciaG.put(colorOriginal.getGreen(), 1);
                        }
                        //AZU:
                        if(frecuenciaB.containsKey(colorOriginal.getBlue())){
                            frecuenciaB.put(colorOriginal.getBlue(), frecuenciaB.get(colorOriginal.getBlue())+1);
                            maxB = frecuenciaB.get(maxB) > frecuenciaB.get(colorOriginal.getBlue()) ? maxB : colorOriginal.getBlue();
                        }else{
                            frecuenciaB.put(colorOriginal.getBlue(), 1);
                        }
                    }
                }
                red = maxR;
                green = maxG;
                blue = maxB;
                frecuenciaR.clear();
                frecuenciaG.clear();
                frecuenciaB.clear();         
                pixelD.setColor(i, j, Color.color(red, green, blue));
                //Progreso
                Filtro.PROGRESO = (++contadorAvance/totalAvance);
            }           
        }
        //third.close();
        return imagenD;
    }
}//Fin de FiltroOleo.java