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
public class FiltroNegativo extends Filtro{
    
    public FiltroNegativo(Image img){
        super(img);
    }
    
    public Image negativo(){
         int red,green,blue,rojoRGB,verdeRGB,azulRGB;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.imagen.getPixelReader();
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                rojoRGB = (int) (colorOriginal.getRed()*255);
                verdeRGB = (int) (colorOriginal.getGreen()*255);
                azulRGB = (int) (colorOriginal.getBlue()*255);                
                red = 255 - rojoRGB;
                green = 255 - verdeRGB;
                blue = 255 - azulRGB;              
                pixelD.setColor(i, j, Color.rgb(red, green, blue));
            }
        }
        return imagenD;
    }    
}
