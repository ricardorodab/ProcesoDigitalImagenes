/* -------------------------------------------------------------------                                      
 * FiltroIcono.java                                                                                             
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
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Dic 31 2015.                                                                                      
 * <p>                                                                                                      
 * Clase que da icono de una imagen.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos obtener iconos dada una imagen.</p>                              
 */
class FiltroIcono extends Filtro{
    
    /**
     * Es el contructor de la clase FiltroIcono
     * @param imagen - es la imagen que se querra sacar el icono.
     */
    public FiltroIcono(Image imagen) {
        super(imagen);
    }
    
    /**
     * Regresa la imagen resultante de aplicar el filtro FiltroIcono.
     *
     * @param imagen Imagen original.
     * @param ancho Nueva altura.
     * @param alto Nueva anchura.
     * @return La imagen redimensionada.
     */
    public Image filtroIcono(int ancho, int alto) {
        int newX = this.getX() / ancho;
        int newY = this.getY() / alto;
        FiltroMosaico mos = new FiltroMosaico(this.getImage());
        Image icono = mos.sacaMosaico(newX, newY,false);
        BufferedImage mosaico = SwingFXUtils.fromFXImage(icono, null);
        java.awt.Image imagen2 = mosaico.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);       
        BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(imagen2, 0, 0, null);
        return SwingFXUtils.toFXImage(bi, null);
    }    
}//Fin de FiltroIcono.java