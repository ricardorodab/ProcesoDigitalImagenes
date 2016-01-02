/* -------------------------------------------------------------------
* Compresion.java
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import static proceso_de_imagenes.InterfazGraficaController.stage;

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
public class Compresion {
    
    /**
     * Metodo estatico que nos comprime una imagen.
     * @param filtro - es un filtro para obtener una imagen.
     * @throws IOException - en caso de tener problemas para crear el archivo.
     */
    public static void comprimeLossy(Filtro filtro) throws IOException{
        FileChooser fileChooser = new FileChooser();
        //Creamos un archivo con extension especial .procImg
        FileChooser.ExtensionFilter extFilter0 = new FileChooser.ExtensionFilter("procIMG files (*.procImg)", "*.procImg");
        fileChooser.getExtensionFilters().addAll(extFilter0);
        File file = fileChooser.showSaveDialog(stage);
        file.createNewFile();
        FileWriter salida = new FileWriter(file);
        BufferedWriter escritor = new BufferedWriter(salida);
        //Tamaño de la imagen. nxm
        String texto = filtro.getX()+" "+filtro.getY()+"\n";
        escritor.write(texto);
        escritor.flush();
        Color color;
        int contador;
        double siguiente, promedio;
        for (int i = 0; i < filtro.getX(); i++) {
            for (int j = 0; j < filtro.getY()-1; j++) {
                color = filtro.getImage().getPixelReader().getColor(i, j);
                promedio = (int)(promedio(color)*255);
                contador = j+1;
                while(contador < filtro.getY()){
                    //Vemos el promedio del tono siguiente.
                    siguiente = (int)(promedio(filtro.getImage().getPixelReader().getColor(i, contador))*255);
                    //Si el tono siguiente es igual al actual aumento el contador y repito.
                    if(promedio != siguiente)
                        break;
                    contador++;
                }
                Integer primedioFin = (int)(promedio);
                //Imprimo el promedio de tomo de grtis con la cantidad de veces que se repite en hexadecimal.
                texto = Integer.toHexString(primedioFin)+" "+(contador-j)+" ";
                escritor.write(texto);
                escritor.flush();
                j = contador-1;
            }
            texto = "\n";
            escritor.write(texto);
            escritor.flush();
        }
        escritor.close();
    }
    
    /**
     * Metodo que descomprime un procImg
     * @param archivo - es el archivo procImg
     * @return Una imagen en escala de grises de la original.
     */
    public static Image descomprimeLossy(File archivo) throws FileNotFoundException, IOException{
        WritableImage imagen = null;
        if (archivo != null) {
            BufferedReader buff = new BufferedReader(new FileReader(archivo));
            String linea = buff.readLine();
            String[] parseado = linea.trim().split(" ");
            imagen = new WritableImage(Integer.parseInt(parseado[0]),Integer.parseInt(parseado[1]));
            int numLinea = 0;
            while((linea = buff.readLine()) != null){
                parseado = linea.trim().split(" ");
                int j = 0;
                int temp = 0;
                for (int i = 0; i < parseado.length; i += 2) {
                    while(temp < Integer.parseInt(parseado[i+1])){
                        int color = Integer.parseInt(parseado[i], 16);
                        imagen.getPixelWriter().setColor(numLinea, j, Color.rgb(color, color, color));
                        j++;
                        temp++;
                    }
                    temp = 0;
                }
                numLinea++;
            }
        }
        return imagen;
    }
    
    /**
     * Metodo que nos da el promedio de un color en escala de gris.
     * @param color es el color que queremos sacar la escala de gris.
     * @return el promedio en tono de gris 0 < tono < 255.
     */
    private static double promedio(Color color){
        return ((color.getBlue()+color.getGreen()+color.getRed())/3);
    }
    
} //Fin de Compresion.java