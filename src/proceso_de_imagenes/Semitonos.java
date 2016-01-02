/* -------------------------------------------------------------------                                      
 * Semitonos.java                                                                                             
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


import java.awt.Desktop;
import java.io.BufferedWriter;
import javafx.scene.image.WritableImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Dic 31 2015.                                                                                      
 * <p>                                                                                                      
 * Clase que da una imagen dada con semitonos.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos obtener una imagen con semitonos.</p>                              
 */
public class Semitonos {
    
    /**
     * Variable para ver cuantos grados de semitonos hay.
     */
    private static final int NUM_TONO = 10;
    
    public static void semitono(Image img,int cuadranteFoto, String salida) throws IOException{
        creaSemitonos(20);
        File archivoSalida = new File(salida);
        archivoSalida.createNewFile();
        FileWriter html = new FileWriter(archivoSalida);
        BufferedWriter escritor = new BufferedWriter(html);
        String texto = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\" \n"
                +"<tr>";
        escritor.write(texto);
        escritor.flush();
        String imagenTemp = "";       
        LinkedList<LinkedList<String>> imagenes = new LinkedList<>();
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        PixelReader pixelI = img.getPixelReader();
        red = green = blue = rojoRGB = verdeRGB = azulRGB = 0;
        terminoX = cuadranteFoto;
        terminoY = cuadranteFoto;
        for (int i = 0; i < img.getWidth(); i += cuadranteFoto) {
            terminoY = cuadranteFoto;
            terminoX = i+cuadranteFoto;
            LinkedList<String> lTemp = new LinkedList<>();
            for (int j = 0; j < img.getHeight(); j += cuadranteFoto) {
                terminoY = j+cuadranteFoto;       
                for (int k = i; k < terminoX; k++) {
                    if(k >= img.getWidth())
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= img.getHeight())
                            break;
                        Color colorOriginal = pixelI.getColor(k, l);
                        rojoRGB += colorOriginal.getRed();
                        verdeRGB += colorOriginal.getGreen();
                        azulRGB += colorOriginal.getBlue();
                        promedio++;
                    }
                } 
                red = (rojoRGB/promedio);
                green = (verdeRGB/promedio);
                blue = (azulRGB/promedio);
                rojoRGB = verdeRGB = azulRGB = promedio = 0;
                
                double promedio2 = red+green+blue;
                promedio2 /= 3;
                promedio2 *= 255;
                if(255-promedio2 < 25){
                    imagenTemp = "1.png";
                }else if(255-promedio2 < 50){
                    imagenTemp = "2.png";
                }else if(255-promedio2 < 75){
                    imagenTemp = "3.png";
                }else if(255-promedio2 < 100){
                    imagenTemp = "4.png";
                }else if(255-promedio2 < 125){
                    imagenTemp = "5.png";
                }else if(255-promedio2 < 150){
                    imagenTemp = "6.png";
                }else if(255-promedio2 < 175){
                    imagenTemp = "7.png";
                }else if(255-promedio2 < 200){
                    imagenTemp = "8.png";
                }else if(255-promedio2 < 225){
                    imagenTemp = "9.png";
                }else{
                    imagenTemp = "10.png";
                }
                texto = "<td><img src=\""+imagenTemp+"\" width=\"4\", height=\"4\"></td> \n";
                lTemp.add(texto);
            }
            imagenes.add(lTemp);
        }
        for (int i = 0; i < imagenes.getFirst().size(); i++) {
            for (LinkedList<String> lTemp : imagenes) {
                if(i >= lTemp.size())
                    break;
                escritor.write(lTemp.get(i));
                escritor.flush();
            }
            texto = "</tr><tr> \n";
            escritor.write(texto);
            escritor.flush();
        }
        texto = "</tr> \n"
                +"</table></center>";
        escritor.write(texto);
        escritor.flush();
        Desktop.getDesktop().browse(archivoSalida.toURI());
  }
    
    /**
     * Metodo para crear los semitonos. Imagenes con círculos.
     * @param TamanoPuntos - Es el radio de los puntos.
     * @throws IOException En caso de tener problemas al crear un semitono.
     */
    private static void creaSemitonos(int TamanoPuntos) throws IOException{
        int y,x;
        y = x = TamanoPuntos;
        WritableImage imagen = null;
        for(int i = 0; i < NUM_TONO; i++){
            imagen = new WritableImage(x,y);
            double radio = (((double)i)/(double)NUM_TONO)*(x/2);
            for(int j = 0; j < x; j++){
                for(int k = 0; k < y; k++){
                    double d1,d2;
                    d1 = (double)j-(((double)x)/2);
                    d2 = (double)k-(((double)y)/2);
                    double radioActual = Math.sqrt(Math.pow(d1,2)+Math.pow(d2,2));
                    if(radioActual <= radio){
                        imagen.getPixelWriter().setColor(j,k, Color.rgb(0,0,0));
                    }
                }
            }
            ImageIO.write(SwingFXUtils.fromFXImage(imagen, null),"png", new File((1+i)+".png"));
        }
    }
}//Fin de semitonos.java