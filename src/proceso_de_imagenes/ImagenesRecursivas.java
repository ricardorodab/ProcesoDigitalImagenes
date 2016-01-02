/* -------------------------------------------------------------------                                      
 * ImagenesRecursivas.java                                                                                             
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
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
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
 * Clase que crea imagenes hechas de una misma imagen.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos obtener dos tipos de imagenes:
 * Hechas con colores reales o hechas desde la Web Pallete.
 * .</p>                              
 */
public class ImagenesRecursivas extends Filtro{
    
    /** Un contador para cuando creen las imagenes no repitan nombre. */
    private static int contador = 0;
    
    /**
     * Metodo constructor para optener imagenes recursivas.
     * @param imagen - Es la imagen original.
     */
    public ImagenesRecursivas(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo para crear un archivo con una imagen hecha de ella misma con 
     * tonalidades Web Pallete.
     * @param salida - Es el nombre de salida del archivo.
     * @param anchoX - Es el ancho que desea tener cada imagen en el mosaico.
     * @param largoY - Es el largo que desea tener cada imagen en el mosaico.
     * @throws IOException Si llegara a no poder crear el archivo o para escribir.
     */
    public void escribeWebPallete(String salida, int anchoX, int largoY) throws IOException{
        LinkedList<LinkedList<String>> imagenes = new LinkedList<>();
        VectorImage3D[] filtros = new VectorImage3D[216];
        int terminoX,terminoY,redInt,greenInt,blueInt;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        PixelReader pixelI = this.getImage().getPixelReader();
        red = green = blue = rojoRGB = verdeRGB = azulRGB = 0;
        terminoX = anchoX;
        terminoY = largoY;
        File archivoSalida = new File(salida);
        archivoSalida.createNewFile();
        FileWriter html = new FileWriter(archivoSalida);
        BufferedWriter escritor = new BufferedWriter(html);
        
        String texto = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\" \n"
                +"<tr>";
        escritor.write(texto);
        escritor.flush();
        
        int numFiltros = 0;
        for (int i = 0; i < 256; i += 51) {
            for (int j = 0; j < 256; j += 51) {
                for (int k = 0; k < 256; k += 51) {
                    VectorImage3D vector = new VectorImage3D(i, j, k, creaImagen(
                            (2*i)-255, (j*2)-255, (k*2)-255,this.getImage()));
                    filtros[numFiltros++] = vector;
                }
            }
        }
        
        for (int i = 0; i < this.getX(); i += anchoX) {
            terminoY = largoY;
            terminoX = i+anchoX;
            LinkedList<String> lTemp = new LinkedList<>();
            for (int j = 0; j < this.getY(); j += largoY) {
                terminoY = j+largoY;
                
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
                
                red = (rojoRGB/promedio);
                green = (verdeRGB/promedio);
                blue = (azulRGB/promedio);
                rojoRGB = verdeRGB = azulRGB = promedio = 0;
                red *= 255;
                green *= 255;
                blue *= 255;
                if(red < 25.5){
                    redInt = 0;
                }else if(red < 76.5){
                    redInt = 51;
                }else if(red < 127.5){
                    redInt = 102;
                }else if(red < 178.5){
                    redInt = 153;
                }else if(red < 229.5){
                    redInt = 204;
                }else{
                    redInt = 255;
                }
                if(green < 25.5){
                    greenInt = 0;
                }else if(green < 76.5){
                    greenInt = 51;
                }else if(green < 127.5){
                    greenInt = 102;
                }else if(green < 178.5){
                    greenInt = 153;
                }else if(green < 229.5){
                    greenInt = 204;
                }else{
                    greenInt = 255;
                }
                if(blue < 25.5){
                    blueInt = 0;
                }else if(blue < 76.5){
                    blueInt = 51;
                }else if(blue < 127.5){
                    blueInt = 102;
                }else if(blue < 178.5){
                    blueInt = 153;
                }else if(blue < 229.5){
                    blueInt = 204;
                }else{
                    blueInt = 255;
                }
                VectorImage3D filtroActual = new VectorImage3D(redInt, greenInt, blueInt, null);
                for (int k = 0; k < numFiltros; k++) {
                    if(filtroActual.equals(filtros[k])){
                        filtroActual.setImage(filtros[k].getImagen());
                        break;
                    }
                }
                texto = "<td><img src=\""+filtroActual.getImagen()+"\" width=\"20\", height=\"20\"></td> \n";
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
        escritor.close();
        contador = 0;
        Desktop.getDesktop().browse(archivoSalida.toURI());
    }
    
    /**
     * Metodo para crear un archivo con una imagen hecha de ella misma con 
     * tonalidades reales.
     * @param salida - Es el nombre de salida del archivo.
     * @param anchoX - Es el ancho que desea tener cada imagen en el mosaico.
     * @param largoY - Es el largo que desea tener cada imagen en el mosaico.
     * @param icono - Si es True, tomara imagenes hechas iconos y sera rapido.
     * @throws IOException Si llegara a no poder crear el archivo o para escribir.
     */
    public void colorReal(String salida,int anchoX, int largoY,boolean icono) throws IOException{
        LinkedList<LinkedList<String>> imagenes = new LinkedList<>();
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        PixelReader pixelI = this.getImage().getPixelReader();
        red = green = blue = rojoRGB = verdeRGB = azulRGB = 0;
        terminoX = anchoX;
        terminoY = largoY;
        Image imagenIconoUOriginal;
        if(icono){
            FiltroIcono reducido = new FiltroIcono(this.getImage());
            imagenIconoUOriginal = reducido.filtroIcono(anchoX, largoY);
        }else{
            imagenIconoUOriginal = this.getImage();
        }
        File archivoSalida = new File(salida);
        archivoSalida.createNewFile();
        FileWriter html = new FileWriter(archivoSalida);
        BufferedWriter escritor = new BufferedWriter(html);
        String texto = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\" \n"
                +"<tr>";
        String imagenTemp = "";
        escritor.write(texto);
        escritor.flush();
        
        for (int i = 0; i < this.getX(); i += anchoX) {
            terminoY = largoY;
            terminoX = i+anchoX;
            LinkedList<String> lTemp = new LinkedList<>();
            for (int j = 0; j < this.getY(); j += largoY) {
                terminoY = j+largoY;
                
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
                
                red = (rojoRGB/promedio);
                green = (verdeRGB/promedio);
                blue = (azulRGB/promedio);
                rojoRGB = verdeRGB = azulRGB = promedio = 0;
                red *= 510;
                green *= 510;
                blue *= 510;
                red -= 255;
                green -= 255;
                blue -= 255;
                imagenTemp = creaImagen((int)red, (int)green, (int)blue,imagenIconoUOriginal);
                texto = "<td><img src=\""+imagenTemp+"\" width=\"20\", height=\"20\"></td> \n";
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
        escritor.close();
        contador = 0;
        Desktop.getDesktop().browse(archivoSalida.toURI());
    }
    
    /**
     * Metodo privado para crear una imagen de tonalidades especificas.
     * @param rojo - Es el rojo deseado.
     * @param verde - Es el verde deseado.
     * @param azul - Es el azul deseado.
     * @param img - Es la imagen que se desea sacar la tonalidad.
     * @return Una cadena con el link de la imagen creada.
     */
    private String creaImagen(int rojo, int verde, int azul,Image img){
        FiltroRGB rgb = new FiltroRGB(img);
        BufferedImage buffe = SwingFXUtils.fromFXImage(rgb.RGB(rojo, verde, azul), null);
        File imagenFinal = new File((contador++) +".jpg");
        try {
            //Cambio
            ImageIO.write(buffe, "png", imagenFinal);
        } catch (IOException ex) {
            
        }
        return imagenFinal.getPath();
    }
    
}
