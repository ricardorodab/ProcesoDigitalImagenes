/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso_de_imagenes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import static proceso_de_imagenes.InterfazGraficaController.stage;

/**
 *
 * @author ricardo_rodab
 */
public class Fotomosaico {
    
    private String ruta;
    private String salida;
   
   
   public static void sacaFotomosaico(File ruta, String salida) throws IOException{
       sacaArchivos(ruta);
   }
   
   private static File sacaArchivos(File dir) throws IOException{
       String[] ext = {"png","jpg","jpeg"};
       TreeMap<Double,LinkedList<String>> rojiNegro = new TreeMap<>();
       Collection<File> files = FileUtils.listFiles(dir,ext, true);       
       File salida = new File("valoresImagenes.txt");
       salida.createNewFile();
       FileWriter txt = new FileWriter(salida);
       BufferedWriter escritor = new BufferedWriter(txt);
       String texto = "";
       for (File img : files) {
           double prom = promedio(img);
           if(rojiNegro.get(prom).isEmpty())
               rojiNegro.put(prom, new LinkedList<>());
           rojiNegro.get(prom).add(img.getPath());
       }
       for (Map.Entry<Double,LinkedList<String>> lst : rojiNegro.entrySet()) {
           for (String str : lst.getValue()) {
             texto = lst.getKey()+"-"+str+"\n"; 
             escritor.write(texto);
             escritor.flush();
           }                             
       }
       escritor.close();
       return salida;
   }
   
   private static double promedio(File img) throws FileNotFoundException{
    Image imagen = new Image(new FileInputStream(img));
    double promedio = 0;
       for (int i = 0; i < imagen.getWidth(); i++) {
           for (int j = 0; j < imagen.getHeight(); j++) {
               promedio += imagen.getPixelReader().getColor(i, j).getRed();
               promedio += imagen.getPixelReader().getColor(i, j).getGreen();
               promedio += imagen.getPixelReader().getColor(i, j).getBlue();               
           }
       }
       promedio /= (imagen.getWidth()*imagen.getHeight());
       return promedio;
   }
}
