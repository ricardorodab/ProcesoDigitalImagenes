/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 *
 * @author ricardo_rodab
 */
public class ImagenesRecursivas extends Filtro{
    
    private static int contador = 0;
    
    public ImagenesRecursivas(Image imagen) {
        super(imagen);
    }
    
    public void escribeWebPallete(String salida, int anchoX, int largoY) throws IOException{
        
    }
    
    public void escribe(String salida,int anchoX, int largoY,boolean icono) throws IOException{
        LinkedList<LinkedList<String>> imagenes = new LinkedList<>();
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        PixelReader pixelI = this.imagen.getPixelReader();
        red = green = blue = rojoRGB = verdeRGB = azulRGB = 0;
        terminoX = anchoX;
        terminoY = largoY;
        Image imagenIconoUOriginal;
        if(icono){
            FiltroIcono reducido = new FiltroIcono(this.imagen);
            imagenIconoUOriginal = reducido.filtroIcono(anchoX, largoY);
        }else{
            imagenIconoUOriginal = this.imagen;
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
        
        for (int i = 0; i < this.x; i += anchoX) {
            terminoY = largoY;
            terminoX = i+anchoX;
            LinkedList<String> lTemp = new LinkedList<>();
            for (int j = 0; j < this.y; j += largoY) {
                terminoY = j+largoY;
                
                for (int k = i; k < terminoX; k++) {
                    if(k >= this.x)
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= this.y)
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
        Desktop.getDesktop().browse(archivoSalida.toURI());
    }
    
    
    private String creaImagen(int rojo, int verde, int azul,Image img){
        FiltroRGB rgb = new FiltroRGB(img);
        BufferedImage buffe = SwingFXUtils.fromFXImage(rgb.RGB(rojo, verde, azul), null);
        File imagenFinal = new File((contador++) +".jpg");
        try {
            //Cambio
            ImageIO.write(buffe, "jpg", imagenFinal);
        } catch (IOException ex) {
            
        }
        return imagenFinal.getPath();
    }
    
}
