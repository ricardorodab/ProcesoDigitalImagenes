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

/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Dic 31 2015.                                                                                      
 * <p>                                                                                                      
 * Clase que da el comportamiento de la tabla carreras.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Desde esta clase podemos obtener el comportamiento deseado de la tabla.</p>                              
 */
public class VectorImage3D {
    
    private int rojo;
    private int verde;
    private int azul;
    private String url;
    
    public VectorImage3D(int rojo, int verde, int azul, String imagenFuente){
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
        this.url = imagenFuente;
    }
    
    public String getImagen(){
        return this.url;
    }
    
    public void setImage(String imagenFuente){
        this.url = imagenFuente;
    }
    
    public int getRojo(){
        return this.rojo;
    }
    
    public int getVerde(){
        return this.verde;
    }
    
    public int getAzul(){
        return this.azul;
    }
    
    public boolean equals(VectorImage3D vector){
        if(vector.getRojo() == this.rojo &&
                vector.getVerde() == this.verde &&
                vector.getAzul() == this.azul)
            return true;
        return false;
    }
}
