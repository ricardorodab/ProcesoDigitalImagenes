/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author ricardo_rodab
 */
public class Esteganografia extends Filtro{
    
    public Esteganografia(Image imagen) {
        super(imagen);
    }
    
    public Image esconde(String mensaje){
        int marca = 0;
        int grisImagenR,grisImagenG,grisImagenB,grisImagenR2,grisImagenG2,grisImagenB2,grisImagenR3,grisImagenG3,grisImagenB3,
                newR,newG,newB,newR2,newG2,newB2,newR3,newG3,restriccion;
        restriccion = 1;
        WritableImage imagenF = new WritableImage(this.getX(), this.getY());
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY()-2; j++) { 
                grisImagenR = (int)(this.getImage().getPixelReader().getColor(i, j).getRed()*255);
                grisImagenG = (int)(this.getImage().getPixelReader().getColor(i, j).getGreen()*255);
                grisImagenB = (int)(this.getImage().getPixelReader().getColor(i, j).getBlue()*255);
                if(marca < mensaje.length()){
                    grisImagenR2 = (int)(this.getImage().getPixelReader().getColor(i, j+1).getRed()*255);
                    grisImagenG2 = (int)(this.getImage().getPixelReader().getColor(i, j+1).getGreen()*255);
                    grisImagenB2 = (int)(this.getImage().getPixelReader().getColor(i, j+1).getBlue()*255);
                    grisImagenR3 = (int)(this.getImage().getPixelReader().getColor(i, j+2).getRed()*255);
                    grisImagenG3 = (int)(this.getImage().getPixelReader().getColor(i, j+2).getGreen()*255);
                    grisImagenB3 = (int)(this.getImage().getPixelReader().getColor(i, j+2).getBlue()*255);
                    int letra = (int)mensaje.charAt(marca++);
                    newR = (grisImagenR & ~1) | (1 & (letra >> 7));
                    newG = (grisImagenG & ~1) | (1 & (letra >> 6));
                    newB = (grisImagenB & ~1) | (1 & (letra >> 5));
                    newR2 = (grisImagenR2 & ~1) | (1 & (letra >> 4));
                    newG2 = (grisImagenG2 & ~1) | (1 & (letra >> 3));
                    newB2 = (grisImagenB2 & ~1) | (1 & (letra >> 2));
                    newR3 = (grisImagenR3 & ~1) | (1 & (letra >> 1));
                    newG3 = (grisImagenG3 & ~1) | (1 & (letra >> 0));
                    imagenF.getPixelWriter().setColor(i, j, Color.rgb(newR, newG, newB));
                    imagenF.getPixelWriter().setColor(i, j+1, Color.rgb(newR2, newG2, newB2));
                    imagenF.getPixelWriter().setColor(i, j+2, Color.rgb(newR3, newG3, grisImagenB3));
                    j += 2;
                }else{
                    grisImagenR &= ~1;
                    grisImagenG &= ~1;
                    grisImagenB &= ~1;
                    imagenF.getPixelWriter().setColor(i, j, Color.rgb(grisImagenR, grisImagenG, grisImagenB));
                    restriccion = 0;
                }
            }
        }
        return imagenF;
    }
    
    public String descifra(){
        int contador = 0;
        String texto = "";
        int letra = 0;
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY()-2; j += 3) {
                int letra1 = (int)(this.getImage().getPixelReader().getColor(i, j).getRed()*255);
                int letra2 = (int)(this.getImage().getPixelReader().getColor(i, j).getGreen()*255);
                int letra3 = (int)(this.getImage().getPixelReader().getColor(i, j).getBlue()*255);
                int letra4 = (int)(this.getImage().getPixelReader().getColor(i, j+1).getRed()*255);
                int letra5 = (int)(this.getImage().getPixelReader().getColor(i, j+1).getGreen()*255);
                int letra6 = (int)(this.getImage().getPixelReader().getColor(i, j+1).getBlue()*255);
                int letra7 = (int)(this.getImage().getPixelReader().getColor(i, j+2).getRed()*255);
                int letra8 = (int)(this.getImage().getPixelReader().getColor(i, j+2).getGreen()*255);
                letra = ((letra1 << 7) & 128) + ((letra2 << 6) & 64) + ((letra3 << 5) & 32) +
                        ((letra4 << 4) & 16) + ((letra5 << 3) & 8) + ((letra6 << 2) & 4)+((letra7 << 1) & 2) + (letra8 & 1);
                texto = texto.concat(""+(char)letra); 
                contador++;
                if(j % 3 == 0)
                    texto = texto.trim();
                //if(texto.length() == 20)
                 //   return texto;
            }
            texto = texto.trim();
        }
        System.out.println("".equals(""+(char)0));
        return texto.replace( ""+(char)0,"").trim();
    }
    
}
