/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.print.attribute.HashAttributeSet;

/**
 *
 * @author ricardo_rodab
 */
public class FiltroOleo extends Filtro{
    
    public FiltroOleo(Image imagen) {
        super(imagen);
    }
    
    public Image mediana(){
        int anchoX,largoY;
        anchoX = largoY = 3;
        LinkedList<Double> medianaR = new LinkedList<Double>();
        LinkedList<Double> medianaG = new LinkedList<Double>();
        LinkedList<Double> medianaB = new LinkedList<Double>();
        int terminoX,terminoY;
        double red,green,blue;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.imagen.getPixelReader();
        red = green = blue = 0;
        terminoX = anchoX;
        terminoY = largoY;
        for (int i = 0; i < this.x; i++) {
            terminoY = largoY;
            terminoX = i+anchoX;
            for (int j = 0; j < this.y; j++) {
                terminoY = j+largoY;               
                
                for (int k = i; k < terminoX; k++) {
                    if(k >= this.x)
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= this.y)
                            break;
                        Color colorOriginal = pixelI.getColor(k, l);
                        medianaR.add(colorOriginal.getRed());
                        medianaG.add(colorOriginal.getGreen());
                        medianaB.add(colorOriginal.getBlue());
                    }
                    
                }
                int mitad = medianaR.size()/2;
                medianaR.sort(null);
                medianaG.sort(null);
                medianaB.sort(null);                
                
                red = medianaR.get(mitad);
                green = medianaG.get(mitad);
                blue = medianaB.get(mitad);
                medianaR.clear();
                medianaG.clear();
                medianaB.clear();
                
                pixelD.setColor(i, j, Color.color(red, green, blue));
            }
            
        }
        return imagenD;
    }
    
    public Image oleo(){
        int anchoX,largoY;
        anchoX = largoY = 7;
        Map<Double,Integer> frecuenciaR = new HashMap<Double,Integer>();
        Map<Double,Integer> frecuenciaG = new HashMap<Double,Integer>();
        Map<Double,Integer> frecuenciaB = new HashMap<Double,Integer>();
        int terminoX,terminoY;
        double red,green,blue;
        WritableImage imagenD = new WritableImage(this.x, this.y);
        PixelWriter pixelD = imagenD.getPixelWriter();
        PixelReader pixelI = this.imagen.getPixelReader();
        red = green = blue = 0;
        terminoX = anchoX;
        terminoY = largoY;
        for (int i = 0; i < this.x; i++) {
            terminoY = largoY;
            terminoX = i+anchoX;
            for (int j = 0; j < this.y; j++) {
                terminoY = j+largoY;
                
                double maxR = Double.NEGATIVE_INFINITY;
                double maxG = Double.NEGATIVE_INFINITY;
                double maxB = Double.NEGATIVE_INFINITY;
                
                
                for (int k = i; k < terminoX; k++) {
                    if(k >= this.x)
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= this.y)
                            break;
                        Color colorOriginal = pixelI.getColor(k, l);
                        
                        if(maxR == Double.NEGATIVE_INFINITY){
                            maxR = colorOriginal.getRed();
                            maxG = colorOriginal.getGreen();
                            maxB = colorOriginal.getBlue();
                        }
                        
                        if(frecuenciaR.containsKey(colorOriginal.getRed())){
                            frecuenciaR.put(colorOriginal.getRed(), frecuenciaR.get(colorOriginal.getRed())+1);
                            maxR = frecuenciaR.get(maxR) > frecuenciaR.get(colorOriginal.getRed()) ? maxR : colorOriginal.getRed();
                        }else{
                            frecuenciaR.put(colorOriginal.getRed(), 1);
                        }
                        
                        
                        if(frecuenciaG.containsKey(colorOriginal.getGreen())){
                            frecuenciaG.put(colorOriginal.getGreen(), frecuenciaG.get(colorOriginal.getGreen())+1);
                            maxG = frecuenciaG.get(maxG) > frecuenciaG.get(colorOriginal.getGreen()) ? maxG : colorOriginal.getGreen();
                        }else{
                            frecuenciaG.put(colorOriginal.getGreen(), 1);
                        }
                        
                        
                        
                        if(frecuenciaB.containsKey(colorOriginal.getBlue())){
                            frecuenciaB.put(colorOriginal.getBlue(), frecuenciaB.get(colorOriginal.getBlue())+1);
                            maxB = frecuenciaB.get(maxB) > frecuenciaB.get(colorOriginal.getBlue()) ? maxB : colorOriginal.getBlue();
                        }else{
                            frecuenciaB.put(colorOriginal.getBlue(), 1);
                        }
                    }
                }
                red = maxR;
                green = maxG;
                blue = maxB;
                frecuenciaR.clear();
                frecuenciaG.clear();
                frecuenciaB.clear();
                
                pixelD.setColor(i, j, Color.color(red, green, blue));
            }
            
        }
        return imagenD;
    }
}
