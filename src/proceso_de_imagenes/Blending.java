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
public class Blending extends Filtro{
    
    public Blending(Image imagen) {
        super(imagen);
    }
    
    public Image licua(Filtro segunda, double porcentaje){
        int red,green,blue;
        int w,h;
        w = Math.min(this.getX(), segunda.getX());
        h = Math.min(this.getY(), segunda.getY());
        WritableImage imagenD = new WritableImage(w, h);
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        PixelReader pixelI2 = segunda.getImage().getPixelReader();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                Color colorOriginal2 = pixelI2.getColor(i, j);
                red = (int)(Math.min(Math.max((255*((colorOriginal.getRed()*porcentaje)
                       + (colorOriginal2.getRed()* (1 - porcentaje)))), 0), 255));
                green = (int)(Math.min(Math.max((255*((colorOriginal.getGreen()*porcentaje)
                       + (colorOriginal2.getGreen()* (1 - porcentaje)))), 0), 255));
                blue = (int)(Math.min(Math.max((255*((colorOriginal.getBlue()*porcentaje)
                       + (colorOriginal2.getBlue()* (1 - porcentaje)))), 0), 255));               
                pixelD.setColor(i, j, Color.rgb(red, green, blue));
            }
        }
        return imagenD;
    }
}
