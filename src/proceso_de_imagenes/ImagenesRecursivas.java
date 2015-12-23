/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

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
    
    public void escribe(String salida,int anchoX, int largoY) throws IOException{
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.imagen.getPixelReader();
        red = green = blue = rojoRGB = verdeRGB = azulRGB = 0;
        terminoX = anchoX;
        terminoY = largoY;
        //salida en lugar de String
        BufferedWriter escritor = new BufferedWriter(new FileWriter("ejemplo.html"));
        //String texto = "<center><table> \n <tr> \n";
        String texto = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\" \n"
                +"<tr>";
        String imagenTemp = "";
        escritor.write(texto);
        escritor.flush();
        
        
        for (int i = 0; i < this.x; i += anchoX) {
            terminoY = largoY;
            terminoX = i+anchoX;
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
                imagenTemp = creaImagen((int)red, (int)green, (int)blue);
                /*texto ="<td> \n"
                +"<img src=\""+imagenTemp+"\"> \n"
                +"</td> \n";*/
                texto = "<td><img src=\""+imagenTemp+"\" width=\"20\", height=\"20\"></td> \n";
                escritor.write(texto);
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
    }
    
    
    private String creaImagen(int rojo, int verde, int azul){
        FiltroRGB rgb = new FiltroRGB(this.imagen);
        BufferedImage buffe = SwingFXUtils.fromFXImage(rgb.RGB(rojo, verde, azul), null);
        File imagenFinal = new File((contador++) +".jpg");
        try {
            ImageIO.write(buffe, "png", imagenFinal);
        } catch (IOException ex) {
            
        }
        return imagenFinal.getPath();
    }
    
}
