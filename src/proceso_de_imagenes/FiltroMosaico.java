/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author ricardo_rodab
 */
public class FiltroMosaico extends Filtro{
    
    public FiltroMosaico(Image img){
        super(img);
    }
    
    public Image sacaMosaico(int anchoX, int largoY){
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        red = green = blue = rojoRGB = verdeRGB = azulRGB = 0;
        terminoX = anchoX;
        terminoY = largoY;
        for (int i = 0; i < this.getX(); i += anchoX) {
            terminoY = largoY;
            terminoX = i+anchoX;
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
                
                
                
                for (int k = i; k < terminoX; k++) {
                      if(k >= this.getX())
                        break;
                    for (int l = j; l < terminoY; l++) {
                       if(l >= this.getY())
                            break;
                        pixelD.setColor(k, l, Color.color(red, green, blue));
                    }
                }
                
            }
            
        }
        return imagenD;
    }
    
    
}
