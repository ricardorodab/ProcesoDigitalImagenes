/* -------------------------------------------------------------------                                      
 * FiltroATT.java                                                                                             
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

import java.awt.image.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Dic 31 2015.                                                                                      
 * <p>                                                                                                      
 * Clase que define al filtro ATT.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos obtener el filtro de tipo AT and T.</p>                              
 */
public class FiltroATT extends Filtro{
    
    
    
    /** Tomamos un numero aleatorio de numeros de lineas */
    private static final int N = 15;
    
    /**
     * Método constructor de la clase ATT 
     * @param imagen - es la imagen a aplicar el filtro.
     */
    public FiltroATT(Image imagen) {
        super(imagen);
    }
    
  /**
   * Nos da el filtro con un efecto similar al icono de FiltroATT
   * @return La imagen con el efecto FiltroATT, lineas en blanco y negro.
   */
    public Image filtra() {
        Filtro.PROGRESO = 0;
        AltoContrasteFiltroInverso altoContraste = new AltoContrasteFiltroInverso((Image)this.getImage());
        BufferedImage ac = SwingFXUtils.fromFXImage(altoContraste.altoContraste(),null);
        int w = ac.getWidth();
        int h = ac.getHeight();
        Raster rac = ac.getData();
        BufferedImage nueva = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        WritableRaster wrn = nueva.getRaster();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h - N; j += N) {
                int puntos = 0;
                for (int y = j; y < j + N; y++) {
                    if (rac.getSample(i, y, 0) == 0) {
                        puntos++;
                    }
                }               
                boolean[] acomodados = centra(N, puntos);
                for (int y = j; y < j + N; y++) {
                    if (acomodados[y - j]) {
                        wrn.setSample(i, y, 0, 0);
                        wrn.setSample(i, y, 1, 0);
                        wrn.setSample(i, y, 2, 0);
                    } else {
                        wrn.setSample(i, y, 0, 0xff);
                        wrn.setSample(i, y, 1, 0xff);
                        wrn.setSample(i, y, 2, 0xff);
                    }
                }
                Filtro.PROGRESO = (this.avanzar()/this.getTotal());
                for (int k = 0; k < N; k++) {
                    this.avanzar();
                }
            }
        }
        lineas(nueva);
        return SwingFXUtils.toFXImage(nueva, null);
    }
    
    /**
     * Dibuja lineas horizontales blancas.
     *
     * @param src Imagen original - es la imagen a la que original se le marcara.
     */
    private static void lineas(BufferedImage src) {
        WritableRaster wr = src.getRaster();
        for (int i = 0; i < wr.getWidth(); i++) {
            for (int j = 0; j < wr.getHeight() - N; j += N) {
                wr.setSample(i, j, 0, 0xff);
                wr.setSample(i, j, 1, 0xff);
                wr.setSample(i, j, 2, 0xff);
            }
        }
    }
    
    /**
     * @param tam Tamano del arreglo
     * @param puntos Numero de puntos que deben ser centrados
     * @return Arreglo con los puntos centrados
     */
    private static boolean[] centra(int tam, int puntos) {
        boolean[] acomodados = new boolean[tam];
        int n = puntos / 2;
        int m = puntos % 2 == 0 ? n - 1 : n;
        for (int i = (tam / 2) - n; i <= (tam / 2) + m; i++) {
            acomodados[i] = true;
        }
        return acomodados;
    }
} //Fin de FiltroATT.java
