/* -------------------------------------------------------------------
* .java
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;


/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Dic 31 2015.
 * <p>
 * Clase para crear estereograma dado una imagen..</p>
 *
 * <p>
 * Desde esta clase podemos crear imagenes estereogramas.</p>
 */
public class FiltroEstereograma {
    
    /** Imagen final */
    private BufferedImage img;
    /** Imagen del patron. */
    private BufferedImage pattern;
    /** Profundidad incluyendo patron. */
    private BufferedImage depth;
    /** Patron con ancho. */
    private int standart = 140;
    /** */
    private int tile = 100;
    
    Random r = new Random();
    
    /**
     * Metodo constructor de la clase.
     * @param salida - es el nombre del archivo de salida.
     * @param img2 - es la imagen a convertir.
     */
    public FiltroEstereograma(String salida, BufferedImage img2){
        // Iniciamos la imagen
        img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        // Ambas necesitan tener la misma resolución
        depth = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
        pattern = new BufferedImage(standart,standart,BufferedImage.TYPE_INT_RGB);
        gen_pattern();
        gen_background();
        gen_foreground(img2);
        gen_stereograph();
        
        try {
            File output = new File(salida+".jpg");
            ImageIO.write(img,"jpg",output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Generador aleatorio de grises.
     */
    public void gen_pattern(){
        for(int i = 0;i<standart;i++){
            for(int j = 0;j<standart;j++){
                int f = r.nextInt(255);
                Color c3 = new Color(f,f,f);
                pattern.setRGB(i, j, c3.getRGB());
            }
        }
    }
    
    /**
     * Apica el patrón al fonto pero primero al titulo.
     */
    public void gen_background(){
        for(int i = 0;i<standart;i++){
            for(int j = 0;j<img.getHeight();j++){
                img.setRGB(i, j, pattern.getRGB(i%standart, j%standart));
            }
        }
    }
    
    /**
     * Metodo que genera el fondo del archivo.
     * @param img2
     */
    public void gen_foreground(BufferedImage img2){
        for(int i = 0;i<depth.getWidth();i++){
            for(int j = 0;j<depth.getHeight();j++){
                Color c1 = new Color(10,100,100);
                //Lo llena con el fondo de color.
                depth.setRGB(i, j, c1.getRGB());
            }
        }
        
        Graphics2D g2d = depth.createGraphics();
        g2d.setColor(new Color(20,60,60));
        // Agrega el texto.
        g2d.setFont(new Font("Serif",Font.BOLD,150));
        //g2d.drawString("Pepe", 80, 250);
        g2d.dispose();
    }
    
    /**
     * Genera el efecto de stereograma.
     */
    public void gen_stereograph(){
        for(int i = 0;i<img.getWidth();i++){
            for(int j = 0;j<img.getHeight();j++){
                int de = new Color(depth.getRGB(i, j)).getRed(); // getting the depth from the depth image(stored im red component)
                if(i+tile < img.getWidth())img.setRGB(i+tile, j, img.getRGB(i, j)); // repetition of the background
                if(i+tile-de < img.getWidth())img.setRGB(i+tile-de, j, img.getRGB(i, j)); // real stereogram effect
            }
        }
        
    }
    /*  private static Color[] colores(){
    Color[] colores = {Color.BLACK,Color.PURPLE,Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW,Color.VIOLET,Color.BLUEVIOLET,Color.ORANGE,
    Color.MAGENTA,Color.DARKRED,Color.BROWN,Color.BURLYWOOD,Color.CADETBLUE,Color.CHARTREUSE,Color.CHOCOLATE,
    Color.CORNFLOWERBLUE,Color.CRIMSON,Color.CYAN,Color.DARKBLUE,Color.DARKCYAN,Color.DARKGOLDENROD,Color.AQUA,
    Color.DARKGRAY,Color.DARKGREEN,Color.DARKKHAKI,Color.DARKMAGENTA,Color.DARKOLIVEGREEN,Color.DARKORANGE,
    Color.DARKORCHID,Color.DARKSALMON,Color.DARKSEAGREEN,Color.ANTIQUEWHITE,Color.DARKSLATEBLUE,Color.CORAL,
    Color.DARKSLATEGREY,Color.DARKTURQUOISE,Color.DARKVIOLET,Color.DEEPPINK,Color.DEEPSKYBLUE,Color.FIREBRICK,
    Color.FORESTGREEN,Color.FUCHSIA,Color.GOLD,Color.GRAY,Color.GREENYELLOW,Color.HOTPINK,Color.INDIANRED,Color.INDIGO,
    Color.AQUAMARINE,Color.LAWNGREEN,Color.LIGHTBLUE,Color.LIGHTSEAGREEN,Color.LIME,Color.MAROON,Color.MEDIUMBLUE,
    Color.NAVY,Color.OLIVE,Color.OLIVEDRAB,Color.ORANGERED,Color.ORCHID,Color.PERU,Color.PINK,Color.PLUM,Color.POWDERBLUE,
    Color.ROSYBROWN,Color.ROYALBLUE,Color.SADDLEBROWN,Color.SALMON,Color.SANDYBROWN,Color.SEAGREEN,Color.SIENNA,
    Color.SKYBLUE,Color.SLATEBLUE,Color.SLATEGREY,Color.SPRINGGREEN,Color.STEELBLUE,Color.TEAL,Color.TAN,Color.TOMATO,
    Color.TURQUOISE,Color.YELLOWGREEN};
    return colores;
    }*/
    
}
