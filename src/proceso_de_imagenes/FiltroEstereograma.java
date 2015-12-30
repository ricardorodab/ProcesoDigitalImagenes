/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;


/**
 *
 * @author ricardo_rodab
 */
public class FiltroEstereograma {
    

   BufferedImage img; // final Image
   BufferedImage pattern; // pattern Image
   BufferedImage depth; // depth field (inlcudes text)
   
   int standart = 140; // pattern tile width
   int tile = 100;
   
   Random r = new Random();

   public FiltroEstereograma(String salida, BufferedImage img2){
      img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB); // init images
      depth = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB); // both need to have the same resolution
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
   
   public void gen_pattern(){ // a standart grayscale noise generator
      for(int i = 0;i<standart;i++){
         for(int j = 0;j<standart;j++){
            int f = r.nextInt(255);
            Color c3 = new Color(f,f,f);
            pattern.setRGB(i, j, c3.getRGB());
         }
      }
   }
   
   public void gen_background(){ // apply pattern to background but only first tile
      for(int i = 0;i<standart;i++){
         for(int j = 0;j<img.getHeight();j++){
            img.setRGB(i, j, pattern.getRGB(i%standart, j%standart));      
         }
      }
   }
   
   public void gen_foreground(BufferedImage img2){ // generate the depth field
      for(int i = 0;i<depth.getWidth();i++){
         for(int j = 0;j<depth.getHeight();j++){
            Color c1 = new Color(10,100,100);
            depth.setRGB(i, j, c1.getRGB()); // fill with background color
         }
      }
      
      Graphics2D g2d = depth.createGraphics();
      g2d.setColor(new Color(20,60,60));
      g2d.setFont(new Font("Serif",Font.BOLD,150)); // add the text
      //g2d.drawString("Pepe", 80, 250);
      g2d.dispose();
   }
   
   public void gen_stereograph(){ // the effect
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
