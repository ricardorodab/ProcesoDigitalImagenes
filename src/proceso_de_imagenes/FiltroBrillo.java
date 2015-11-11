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
public class FiltroBrillo extends Filtro{
 
    private int brillo;
    
    public FiltroBrillo(Image img, int brilloNuevo){
        super(img);
        this.brillo = brilloNuevo;
    }
    
    public Image filtroBrillo(){
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
                red = rojoRGB+this.brillo;
                green = verdeRGB+this.brillo;
                blue = azulRGB+this.brillo;
                if(red > 255)
                    red = 255;
                else if(red < 0)
                    red = 0;
                if(green > 255)
                    green = 255;
                else if(green < 0)
                    green = 0;
                if(blue > 255)
                    blue = 255;
                else if(blue < 0)
                    blue = 0;
                pixelD.setColor(i, j, Color.rgb(red, green, blue));
            }
        }
        return imagenD;
    }
    
}
