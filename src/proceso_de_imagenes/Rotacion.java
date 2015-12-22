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
public class Rotacion extends Filtro{
    
    public final int CERO = 0;
    public final int NOVENTA = 90;
    public final int CIENTO_OCHENTA = 180;
    public final int DOSCIENTOS_SETENTA = 270;
    
    
    public Rotacion(Image imagen) {
        super(imagen);
    }
    
    public Image rotarMatriz(int grados){
        int red,green,blue;
        WritableImage imagenD = null;
        if(grados == NOVENTA || grados == DOSCIENTOS_SETENTA){
            imagenD = new WritableImage(this.y, this.x);
        }else if(grados == CIENTO_OCHENTA){
            imagenD = new WritableImage(this.x, this.y);
        }else if (grados == CERO){
            return this.imagen;
        }else{
            //ERROR
        }
        double cos,sin;
        if(grados == NOVENTA || grados == DOSCIENTOS_SETENTA){
            sin = Math.sin(Math.toRadians(grados+.01));
            cos = Math.cos(Math.toRadians(grados));
        }else{
            cos = Math.cos(Math.toRadians(grados+01));
            sin = Math.sin(Math.toRadians(grados));
        }
        //cos = Math.cos(Math.toRadians(grados));
        //sin = Math.sin(Math.toRadians(grados));
        int mitadX = (this.x/2)-1;
        int mitadY = (this.y/2)-1;
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.imagen.getPixelReader();
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                red = (int) (colorOriginal.getRed()*255);
                green = (int) (colorOriginal.getGreen()*255);
                blue = (int) (colorOriginal.getBlue()*255);
                int newI = i - mitadX;
                int newJ = j - mitadY;
                int newX = (int)((newI*cos)+(newJ*sin));
                int newY = (int)((-1*newI*sin)+(newJ*cos));
                if(grados == NOVENTA){
                    newX += mitadY;
                    newY += mitadX;
                }else if(grados == CIENTO_OCHENTA){
                    newX += mitadX;
                    newY += mitadY;
                }else{
                    newX += mitadY;
                    newY += mitadX;
                }
                if (newX < 0 || newY < 0) {
                    continue;
                }
                pixelD.setColor(newX,newY, Color.rgb(red, green, blue));
            }
        }
        return imagenD;
    }
    
    public Image rotar(int grados){
        int red,green,blue;
        WritableImage imagenD = null;
        if(grados == NOVENTA || grados == DOSCIENTOS_SETENTA){
            imagenD = new WritableImage(this.y, this.x);
        }else if(grados == CIENTO_OCHENTA){
            imagenD = new WritableImage(this.x, this.y);
        }else if (grados == CERO){
            return this.imagen;
        }else{
            //ERROR
        }
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.imagen.getPixelReader();
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                Color colorOriginal = pixelI.getColor(i, j);
                red = (int) (colorOriginal.getRed()*255);
                green = (int) (colorOriginal.getGreen()*255);
                blue = (int) (colorOriginal.getBlue()*255);
                if(grados == NOVENTA){
                    pixelD.setColor(j, this.x-i-1, Color.rgb(red, green, blue));
                }else if(grados == CIENTO_OCHENTA){
                    pixelD.setColor(this.x-i-1, this.y-j-1, Color.rgb(red, green, blue));
                }else{
                    pixelD.setColor(this.y-j-1, i, Color.rgb(red, green, blue));
                }
            }
        }
        return imagenD;
    }
}
