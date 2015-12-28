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
public class AltoContrasteFiltroInverso extends Filtro{

    public AltoContrasteFiltroInverso(Image imagen) {
        super(imagen);
    }
    

   public Image inverso() {
        int rojoRGB,verdeRGB,azulRGB;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                rojoRGB = (int) (colorOriginal.getRed()*255);
                verdeRGB = (int) (colorOriginal.getGreen()*255);
                azulRGB = (int) (colorOriginal.getBlue()*255);    
                if( ((rojoRGB + verdeRGB + azulRGB)/3) < 127 ){
                    pixelD.setColor(i, j, Color.rgb(255, 255, 255));
                }else{
                    pixelD.setColor(i, j, Color.rgb(0, 0, 0));
                } 
            }
        }
        return imagenD;
    }

   public Image altoContraste() {
        int rojoRGB,verdeRGB,azulRGB;
        WritableImage imagenD = new WritableImage(this.getX(), this.getY());
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.getImage().getPixelReader();
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                rojoRGB = (int) (colorOriginal.getRed()*255);
                verdeRGB = (int) (colorOriginal.getGreen()*255);
                azulRGB = (int) (colorOriginal.getBlue()*255);    
                if( ((rojoRGB + verdeRGB + azulRGB)/3) > 127 ){
                    pixelD.setColor(i, j, Color.rgb(255, 255, 255));
                }else{
                    pixelD.setColor(i, j, Color.rgb(0, 0, 0));
                } 
            }
        }
        return imagenD;
    }
    
}