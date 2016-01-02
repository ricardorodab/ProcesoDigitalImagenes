/* -------------------------------------------------------------------                                      
 * MarcaDeAgua.java                                                                                             
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

import java.awt.*;
import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javax.swing.JOptionPane;

/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Dic 31 2015.                                                                                      
 * <p>                                                                                                      
 * Clase de filtros de Marca de Agua.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos obtener una imagen con un filtro del estilo de marca de agua.</p>                              
 */
class MarcaDeAgua {

    /**
     * Dependiendo si la marca es a colores o en blanco y negro.
     */
    public static final int BLACK = 0, COLOR = 1;
    /**
     * Regiones donde se puede colocar la marca de agua.
     */
    public static final int SUP_IZQ = 1, SUP_DER = 2, INF_IZQ = 3, INF_DER = 4;

    /**
     * Dibuja una cadena sobre la imagen que le pasan en la region que le
     * indican y con el color que le indican.
     *
     * @param src - Es la imagen 
     * @param region - La ubicacion de la marca.
     * @return Una imagen con la marca de agua.
     */
   public static Image filtra(Image src, int region){ //, int tipo) {
        String s = JOptionPane.showInputDialog("Escribe algo para el Marco de Agua");
        BufferedImage nueva = SwingFXUtils.fromFXImage(src, null);
        int w = nueva.getWidth();
        int h = nueva.getHeight();
        int size = (w / s.length()) / 2;
        Graphics g = nueva.getGraphics();
        Font f = new Font(Font.MONOSPACED, Font.PLAIN, size);
        g.setFont(f);
        /*if (tipo == COLOR) {
            g.setColor(Color.GREEN);
        }else{
            if (tipo == BLACK) {
            g.setColor(Color.BLACK);
        }
        }*/        
        switch (region) {
            case SUP_IZQ:
                g.drawString(s, 0, size);
                break;
            case SUP_DER:
                g.drawString(s, w - s.length() * size, size);
                break;
            case INF_IZQ:
                g.drawString(s, 0, h - size);
                break;
            case INF_DER:
                g.drawString(s, w - s.length() * size, h - size);
                break;
        }
        g.dispose();
        Filtro temp = new Filtro(SwingFXUtils.toFXImage(nueva, null));
        Blending original = new Blending(src);
        return original.licua(temp, .5);        
    }
} //Fin de MarcaDeAgua.java