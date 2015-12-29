/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import static proceso_de_imagenes.InterfazGraficaController.stage;

/**
 *
 * @author ricardo_rodab
 */
public class Fotomosaico {
    
    private static int lineas = 0;
   
   public static void sacaFotomosaico(Filtro filtro,int mosaico, File ruta, String salida) throws IOException{
       File infoLog = sacaArchivos(ruta);
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
        Image imagenIconoUOriginal = filtro.getImage();;
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
   
   private static File sacaArchivos(File dir) throws IOException{
       lineas = 0;
       String[] ext = {"png","jpg","jpeg"};
       TreeMap<Double,LinkedList<String>> rojiNegro = new TreeMap<>();
       Collection<File> files = FileUtils.listFiles(dir,ext, true);       
       File salida = new File("valoresImagenes.txt");
       salida.createNewFile();
       FileWriter txt = new FileWriter(salida);
       BufferedWriter escritor = new BufferedWriter(txt);
       String texto = "";
       for (File img : files) {
           double[] prom = promedio(img);
           double prom2 = Math.sqrt(
                   Math.pow(prom[0], 2)+
                           Math.pow(prom[1], 2)+
                           Math.pow(prom[2], 2));
           if(rojiNegro.get(prom2) == null)
               rojiNegro.put(prom2, new LinkedList<>());
           rojiNegro.get(prom2).add(prom[0]+"#-"+prom[1]+"#-"+prom[2]+"#-"+img.getAbsolutePath());
       }
       for (Map.Entry<Double,LinkedList<String>> lst : rojiNegro.entrySet()) {
           for (String str : lst.getValue()) {
             texto = lst.getKey()+"#-"+str+"\n";
             lineas++;
             escritor.write(texto);
             escritor.flush();
           }                             
       }
       escritor.close();
       return salida;
   }
   
   private static String buscaImagen(File archivo, double red, double green, double blue) throws FileNotFoundException, IOException{
       String ruta = null;
       String linea = "";
       FileReader reader = new FileReader(archivo);
       BufferedReader buf = new BufferedReader(reader);
       double eucli = Math.sqrt(
       Math.pow(red, 2)+
               Math.pow(green, 2)+
               Math.pow(blue, 2));
       
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
        double prom = Double.parseDouble(parString[0]);
        if(Math.abs(eucli-prom) < dif){
           dif = Math.abs(eucli-prom);
           mejorOpcion = parString[parString.length-1];
        }
       }
       /*int i = 0;
       int veces = lineas/2;
       int iteraciones = 2;
       while(ruta == null){
           while(++i < veces){ buf.readLine();}
           i = 0;
           String[] parString = buf.readLine().split(" ");
           if(eucli - Double.parseDouble(parString[0]) < 0.01 && eucli - Double.parseDouble(parString[0]) > -0.01 ){
               ruta = parString[parString.length-1];
           }else if(eucli - Double.parseDouble(parString[0]) > 0.01){
               veces += veces/(iteraciones++);
           }else{
               veces -= veces/(iteraciones++);
           }
           buf.close();
           buf = new BufferedReader(new FileReader(archivo));
           if(veces < 1){
               parString = buf.readLine().split(" ");
               ruta = parString[parString.length-1];
               System.out.println("<1");
           }else if(veces >= lineas){
               String x = "";
               while((x = buf.readLine()) != null){ ruta = x; }
               parString = ruta.split(" ");
               ruta = parString[parString.length-1];
               System.out.println(">n"+lineas);
           }
       }*/
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
