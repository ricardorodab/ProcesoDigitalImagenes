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

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.apache.commons.io.FileUtils;

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
public class Fotomosaico {
    
    private static int lineas = 0;
    
    public static void sacaFotomosaico(Filtro filtro,int mosaico, File ruta, String salida,boolean recursiva) throws IOException{
        File infoLog = sacaArchivos(ruta,recursiva);
        //File infoLog = FileUtils.getFile("valoresImagenes.txt");
        int anchoX,largoY;
        anchoX = largoY = mosaico;
        LinkedList<LinkedList<String>> imagenes = new LinkedList<>();
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        PixelReader pixelI = filtro.getImage().getPixelReader();
        red = green = blue = rojoRGB = verdeRGB = azulRGB = 0;
        terminoX = anchoX;
        terminoY = largoY;
        //Image imagenIconoUOriginal = filtro.getImage();;
        File archivoSalida = new File(salida);
        archivoSalida.createNewFile();
        FileWriter html = new FileWriter(archivoSalida);
        BufferedWriter escritor = new BufferedWriter(html);
        String texto = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\" \n"
                +"<tr>";
        String imagenTemp = "";
        escritor.write(texto);
        escritor.flush();
        
        for (int i = 0; i < filtro.getX(); i += anchoX) {
            terminoY = largoY;
            terminoX = i+anchoX;
            LinkedList<String> lTemp = new LinkedList<>();
            for (int j = 0; j < filtro.getY(); j += largoY) {
                terminoY = j+largoY;
                
                for (int k = i; k < terminoX; k++) {
                    if(k >= filtro.getX())
                        break;
                    for (int l = j; l < terminoY; l++) {
                        if(l >= filtro.getY())
                            break;
                        Color colorOriginal = pixelI.getColor(k, l);
                        rojoRGB += colorOriginal.getRed();
                        verdeRGB += colorOriginal.getGreen();
                        azulRGB += colorOriginal.getBlue();
                        promedio++;
                    }
                }
                
                red = (rojoRGB/promedio);
                green = (verdeRGB/promedio);
                blue = (azulRGB/promedio);
                rojoRGB = verdeRGB = azulRGB = promedio = 0;
                imagenTemp = buscaImagen(infoLog,red,green, blue);
                texto = "<td><img src=\""+imagenTemp+"\" width=\"10\", height=\"10\"></td> \n";
                lTemp.add(texto);
            }
            imagenes.add(lTemp);
        }
        for (int i = 0; i < imagenes.getFirst().size(); i++) {
            for (LinkedList<String> lTemp : imagenes) {
                if(i >= lTemp.size())
                    break;
                escritor.write(lTemp.get(i));
                escritor.flush();
            }
            texto = "</tr><tr> \n";
            escritor.write(texto);
            escritor.flush();
        }
        texto = "</tr> \n"
                +"</table></center>";
        escritor.write(texto);
        escritor.flush();
        escritor.close();
        Desktop.getDesktop().browse(archivoSalida.toURI());
    }
    
    private static File sacaArchivos(File dir,boolean recursivo) throws IOException{
        lineas = 0;
        String[] ext = {"png","jpg","jpeg"};
        //TreeMap<Double,LinkedList<String>> rojiNegro = new TreeMap<>();
        Collection<File> files = FileUtils.listFiles(dir,ext, recursivo);
        File salida = new File("valoresImagenes.txt");
        salida.createNewFile();
        FileWriter txt = new FileWriter(salida);
        BufferedWriter escritor = new BufferedWriter(txt);
        String texto = "";
        for (File img : files) {
            double[] prom = promedio(img);
            /*double prom2 = Math.sqrt(
            Math.pow(prom[0], 2)+
            Math.pow(prom[1], 2)+
            Math.pow(prom[2], 2));
            if(rojiNegro.get(prom2) == null)
            rojiNegro.put(prom2, new LinkedList<>());
            rojiNegro.get(prom2).add(prom[0]+"#-"+prom[1]+"#-"+prom[2]+"#-"+img.getAbsolutePath());
            }*/
            // for (Map.Entry<Double,LinkedList<String>> lst : rojiNegro.entrySet()) {
            //     for (String str : lst.getValue()) {
            //         texto = lst.getKey()+"#-"+str+"\n";
            texto = prom[0]+"#-"+prom[1]+"#-"+prom[2]+"#-"+img.getAbsolutePath()+"\n";
            lineas++;
            escritor.write(texto);
            escritor.flush();
            //}
            }
            escritor.close();
            return salida;
        }
        
        
        private static String buscaImagen(File archivo, double red, double green, double blue) throws FileNotFoundException, IOException{
            String ruta = null;
            String linea = "";
            FileReader reader = new FileReader(archivo);
            BufferedReader buf = new BufferedReader(reader);
            double dif = Double.POSITIVE_INFINITY;
            String[] parString = null;
            String mejorOpcion = "";
            while(ruta == null){
                linea = buf.readLine();
                if(linea == null){
                    ruta = mejorOpcion;
                    continue;
                }
                parString = linea.split("#-");
                double promR = Double.parseDouble(parString[0]);
                double promG = Double.parseDouble(parString[1]);
                double promB = Double.parseDouble(parString[2]);
                double raiz = Math.sqrt(
                        Math.pow(red - promR, 2)+
                                Math.pow(green - promG, 2)+
                                Math.pow(blue - promB, 2)
                );
                if(raiz < dif){
                    dif = raiz;
                    mejorOpcion = parString[parString.length-1];
                }
            }
            return ruta;
        }
        
        private static double[] promedio(File img) throws FileNotFoundException{
            Image imagen = new Image(new FileInputStream(img));
            double[] promedio = new double[3];
            double promedioR,promedioG,promedioB;
            promedioR = promedioG = promedioB = 0;
            for (int i = 0; i < imagen.getWidth(); i++) {
                for (int j = 0; j < imagen.getHeight(); j++) {
                    promedioR += imagen.getPixelReader().getColor(i, j).getRed();
                    promedioG += imagen.getPixelReader().getColor(i, j).getGreen();
                    promedioB += imagen.getPixelReader().getColor(i, j).getBlue();
                }
            }
            promedio[0] = promedioR/(imagen.getWidth()*imagen.getHeight());
            promedio[1] = promedioG/(imagen.getWidth()*imagen.getHeight());
            promedio[2] = promedioB/(imagen.getWidth()*imagen.getHeight());
            return promedio;
        }
}
