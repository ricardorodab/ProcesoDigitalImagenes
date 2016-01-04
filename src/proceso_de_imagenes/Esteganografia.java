/* -------------------------------------------------------------------                                      
 * Esteganofrafia.java                                                                                             
 * versión 1.0                                                                                              
 * Copyright (C) 2015  José Ricardo Rodríguez Abreu.                                                        
 * Facultad de Ciencias,                                                                                    
 * Universidad Nacional Autónoma de México, Mexico.                                                         
 *                                                                                                          
 * Este programa es software libre; se puede redistribuir                                                   
 * y/o modificar en los términos establecidos por la                                                        
 * Licencia Pública General de GNU tal como fue publicada                                                   
 * por la Free Software Foundation en la versión 2 o                                                        
 * superior.                                                                                                
 *                                                                                                          
 * Este programa es distribuido con la esperanza de que                                                     
 * resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho                                                  
 * sin la garantía implícita de COMERCIALIZACIÓN o                                                          
 * ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la                                                        
 * Licencia Pública General de GNU para mayores detalles.                                                   
 *                                                                                                          
 * Con este programa se debe haber recibido una copia de la                                                 
 * Licencia Pública General de GNU, de no ser así, visite el                                                
 * siguiente URL:                                                                                           
 * http://www.gnu.org/licenses/gpl.html                                                                     
 * o escriba a la Free Software Foundation Inc.,                                                            
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.                                                
 * -------------------------------------------------------------------                                      
 */
package proceso_de_imagenes;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Dic 31 2015.                                                                                      
 * <p>                                                                                                      
 * Clase que da el comportamiento de encriptar texto en una imagen.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos encriptar y desencriptar texto en una imagen..</p>                              
 */
public class Esteganografia extends Filtro{
    
    /**
     * Constructor la clase Esteganografia.
     * @param imagen - es la imagen donde se esconderán o descifrarán cosas.
     */
    public Esteganografia(Image imagen) {
        super(imagen);
    }
    
    /**
     * Metodo para esconder un mensaje.
     * @param mensaje - El mensaje que se esconde dentro de la imagen y lo hace 
     * invisible ante el ojo humano promedio.
     * @return Una imagen practicamente identica.
     */
    public Image esconde(String mensaje){
        Filtro.PROGRESO = 0;
        int marca = 0;
        int grisImagenR,grisImagenG,grisImagenB,grisImagenR2,grisImagenG2,grisImagenB2,grisImagenR3,grisImagenG3,grisImagenB3,
                newR,newG,newB,newR2,newG2,newB2,newR3,newG3;
        WritableImage imagenF = new WritableImage(this.getX(), this.getY());
        /* POR EJEMPLO:
        (1 1 0 1 1 0 1 1) (0 1 0 0 1 0 0 0) (0 1 0 0 0 0 1 0)
        (0 0 0 1 1 1 1 1) (0 1 0 1 1 0 1 0) (1 1 0 1 1 1 1 1)
        (0 0 0 0 1 1 1 1) (0 1 0 0 0 1 1 1) (0 0 0 0 0 1 1 1)
        */
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
                    // ~ == NOT
                    // ~1 == 1111 1110
                    newR = (grisImagenR & ~1) | (1 & (letra >> 7));
                    newG = (grisImagenG & ~1) | (1 & (letra >> 6));
                    newB = (grisImagenB & ~1) | (1 & (letra >> 5));
                    newR2 = (grisImagenR2 & ~1) | (1 & (letra >> 4));
                    newG2 = (grisImagenG2 & ~1) | (1 & (letra >> 3));
                    newB2 = (grisImagenB2 & ~1) | (1 & (letra >> 2));
                    newR3 = (grisImagenR3 & ~1) | (1 & (letra >> 1));
                    newG3 = (grisImagenG3 & ~1) | (1 & (letra));
                    imagenF.getPixelWriter().setColor(i, j, Color.rgb(newR, newG, newB));
                    imagenF.getPixelWriter().setColor(i, j+1, Color.rgb(newR2, newG2, newB2));
                    imagenF.getPixelWriter().setColor(i, j+2, Color.rgb(newR3, newG3, grisImagenB3));
                    j += 2;
                    Filtro.PROGRESO = (this.avanzar()/this.getTotal());
                    this.avanzar();
                }else{
                    grisImagenR &= ~1;
                    grisImagenG &= ~1;
                    grisImagenB &= ~1;
                    imagenF.getPixelWriter().setColor(i, j, Color.rgb(grisImagenR, grisImagenG, grisImagenB));
                    Filtro.PROGRESO = (this.avanzar()/this.getTotal());
                }
            }
        }
        return imagenF;
    }
    
    /**
     * Metodo para encontrar un mensaje si lo hay en una imagen.
     * @return La cadena (el mensaje si hay) que estaba dentro de la imagen.
     */
    public String descifra(){
        Filtro.PROGRESO = 0;
        String texto = "";
        int letra;
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
            }
            Filtro.PROGRESO = (this.avanzar()/this.getTotal());
            this.avanzar();
            this.avanzar();
            texto = texto.trim();
        }
        return texto.replace(""+(char)0,"").trim();
    }
    
} //Fin de Esteganografia.java