/* -------------------------------------------------------------------                                      
 * Convolucion.java                                                                                             
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
 * Clase que da el comportamiento de los filtros que se sacan por convolucion.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos usar la matriz de convolucion junto con algunos filtros..</p>                              
 */
public class Convolucion extends Filtro{
    
    /**
     * Metodo constructor de la clase Convolucion.
     * @param imagen - es la imagen que guarda el filtro.
     */
    public Convolucion(Image imagen){
        super(imagen);
    }
    
    /**
     * Metodo que nos da un filtro emboss.
     * @return - una imagen dada un filtro emboss.
     */
    public Image emboss(){
        double [][] matriz = new double[][]{
            {-1, -1, -1, -1,  0},
            {-1, -1, -1,  0,  1},
            {-1, -1,  0,  1,  1},
            {-1,  0,  1,  1,  1},
            {0,  1,  1,  1,  1}
        };
        FiltroGris temp = new FiltroGris(aplicaConvolucion(
                aplicaConvolucion(this.getImage(), matriz, 1, 128), matriz,1,128));
        return temp.grisPromedio();
    }
    
    /**
     * Metodo que nos da un filtro sharpen.
     * @return - Una imagen dada un filtro sharpen
     */
    public Image sharpen(){
        double [][] matriz = new double[][]{
            {-1, -1, -1},
            {-1,  9, -1},
            {-1, -1, -1}
        };
        return aplicaConvolucion(this.getImage(),matriz,1,0);
    }
    
    /**
     * Metodo que nos da un filtro bordes.
     * @return - Una imagen dada un filtro bordes.
     */
    public Image bordes(){
        double [][] matriz = new double[][]{
            {-1,  0,  0,  0,  0},
            {0, -2,  0,  0,  0},
            {0,  0,  6,  0,  0},
            {0,  0,  0, -2,  0},
            {0,  0,  0,  0, -1}
        };
        return aplicaConvolucion(aplicaConvolucion(this.getImage(), matriz, 1, 0),matriz,1,0);
    }
    
    /**
     * Metodo que nos da un filtro blur.
     * @return - Una imagen dada un filtro blur aplicado.
     */
    public Image blur(){
        double [][] matriz = new double[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
        };
        return aplicaConvolucion(this.getImage(),matriz,(1.0/13.0),0);
    }
    
    /**
     * Metodo que nos da un filtro promedio.
     * @return - Una imagen dada un filtro promedio.
     */
    public Image promedio(){
          double [][] matriz = new double[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };
        return aplicaConvolucion(this.getImage(),matriz,(1.0/9.0),0);
    }
    
    /**
     * Metodo que nos da un filtro motion blur.
     * @return - Una imagen dada un filtro motion blur.
     */
    public Image motionBlur(){
        double [][] matriz = new double[][]{
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
        return aplicaConvolucion(this.getImage(),matriz,(1.0/9.0),0);
    }
    
    /**
     * Metodo privado para aplicar la convolucion.
     * @param imagenOriginal - es la imagen a aplicar.
     * @param matriz - es la matriz que debemos consultar.
     * @param factor - es el factor de división.
     * @param bias - es el factor de producto de cada pixel.
     * @return - Una imagen con un filtro convolicion aplicado.
     */
    private Image aplicaConvolucion(Image imagenOriginal, double[][] matriz,double factor,double bias) {
        BufferedImage imagen = SwingFXUtils.fromFXImage(imagenOriginal, null);
        BufferedImage nuevoLienzo = enmarca(imagen, matriz.length / 2, matriz.length / 2);
        WritableRaster imagenD = nuevoLienzo.getRaster();
        int n = nuevoLienzo.getWidth();
        int m = nuevoLienzo.getHeight();
        WritableRaster nue = nuevoLienzo.getRaster();
        if (n < matriz.length || m < matriz.length) {
            return SwingFXUtils.toFXImage(imagen, null);
        }
        for (int i = 0; i < n - matriz.length; i++) {
            for (int j = 0; j < m - matriz.length; j++) {
                double r = 0;
                double g = 0;
                double b = 0;
                for (int x = i; x < i + matriz.length; x++) {
                    for (int y = j; y < j + matriz.length; y++) {
                        r += imagenD.getSample(x, y, 0) * matriz[x - i][y - j];
                        g += imagenD.getSample(x, y, 1) * matriz[x - i][y - j];
                        b += imagenD.getSample(x, y, 2) * matriz[x - i][y - j];
                    }
                }
                r = Math.min(Math.max((factor * r + bias), 0), 255); 
                g = Math.min(Math.max((factor * g + bias), 0), 255); 
                b = Math.min(Math.max((factor * b + bias), 0), 255); 

                nue.setSample(i + matriz.length / 2, j + matriz.length / 2, 0, r);
                nue.setSample(i + matriz.length / 2, j + matriz.length / 2, 1, g);
                nue.setSample(i + matriz.length / 2, j + matriz.length / 2, 2, b);
            }
        }
        BufferedImage nueva = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        nueva.setData(nue);
        int w = nueva.getWidth();
        int h = nueva.getHeight();
        return SwingFXUtils.toFXImage(nueva.getSubimage(matriz.length/2, matriz.length / 2, w - 2 * (matriz.length/2), h - 2 * (matriz.length / 2)),null);
    }
    
    private BufferedImage enmarca(BufferedImage imagen, int alto, int ancho) {
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        BufferedImage nueva = new BufferedImage(w + 2 * alto, h + 2 * ancho, BufferedImage.TYPE_INT_RGB);
        WritableRaster img = imagen.getRaster();
        WritableRaster wr = nueva.getRaster();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int r = img.getSample(i, j, 0);
                int g = img.getSample(i, j, 1);
                int b = img.getSample(i, j, 2);
                wr.setSample(i + alto, j + ancho, 0, r);
                wr.setSample(i + alto, j + ancho, 1, g);
                wr.setSample(i + alto, j + ancho, 2, b);
            }
        }
        nueva.setData(wr);
        return nueva;
    }
} //Fin de Convolucion.java