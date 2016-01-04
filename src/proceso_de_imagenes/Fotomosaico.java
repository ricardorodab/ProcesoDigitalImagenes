/* -------------------------------------------------------------------
* FotoMosaico.java
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
 * @since Dic 29 2015.
 * <p>
 * Clase para otener una imagen hecho de fotomosaicos.</p>
 *
 * <p>
 * Desde esta clase creamos un metodo para sacar un archivo donde podamos ver una imagen hecha
 * con otras. Parecido al filtro mosaico es en escencia una combinacion de filtro mosaico y
 *  las imagenes recursivas como color real o web pallete. </p>
 */
public class Fotomosaico {
    
    /**
     * Metodo estatico que genera un archivo html para observar la imagen original
     * de manera que esté formada de imagenes.
     * @param filtro - Es el lugar donde se encuentra la imagen a transformar.
     * @param mosaico - Es el tamano que ocupara cada imagen por mosaico.
     * @param ruta - Es la ruta del directorio donde se desea sacar las imagenes.
     * @param salida - Es el nombre del archivo de salida.
     * @param recursiva - Selecciona si desea buscar dentro de los directorios recursivos.
     * @throws IOException - En caso de tener problemas de escritura.
     */
    public static void sacaFotomosaico(Filtro filtro,int mosaico,
            File ruta, String salida,boolean recursiva) throws IOException{ 
        Fotomosaico.sacaFotomosaico(null,filtro, mosaico, ruta, salida, recursiva);
    }
    
    /**
     * Metodo estatico que genera un archivo html para observar la imagen original
     * de manera que esté formada de imagenes.
     * @param infoLogEntrada - Es el archivo con los datos de la biblioteca
     * @param filtro - Es el lugar donde se encuentra la imagen a transformar.
     * @param mosaico - Es el tamano que ocupara cada imagen por mosaico.
     * @param salida - Es el nombre del archivo de salida.
     * @param recursiva - Selecciona si desea buscar dentro de los directorios recursivos.
     * @throws IOException - En caso de tener problemas de escritura.
     */
    public static void sacaFotomosaico(File infoLogEntrada,Filtro filtro,int mosaico,
            String salida,boolean recursiva) throws IOException{ 
        Fotomosaico.sacaFotomosaico(infoLogEntrada,filtro, mosaico, null, salida, recursiva);
    }
    
    /**
     * Metodo estatico que genera un archivo html para observar la imagen original
     * de manera que esté formada de imagenes.
     * @param infoLogEntrada - Es el archivo con los datos de la biblioteca
     * @param filtro - Es el lugar donde se encuentra la imagen a transformar.
     * @param mosaico - Es el tamano que ocupara cada imagen por mosaico.
     * @param ruta - Es la ruta del directorio donde se desea sacar las imagenes.
     * @param salida - Es el nombre del archivo de salida.
     * @param recursiva - Selecciona si desea buscar dentro de los directorios recursivos.
     * @throws IOException - En caso de tener problemas de escritura.
     */
    private static void sacaFotomosaico(File infoLogEntrada,Filtro filtro,
            int mosaico, File ruta, String salida,boolean recursiva) throws IOException{
        Filtro.PROGRESO = 0;
        File infoLog;
        //Crea un archivo con la informacion de las fotos en el archivo.
        if(infoLogEntrada == null){
         infoLog = sacaArchivos(ruta,recursiva);
        }else{
            infoLog = infoLogEntrada;
        }
        //Toma un archivo ya creado con la informacion de las fotos.
        //File infoLog = FileUtils.getFile("valoresImagenes.txt");
        Filtro.PROGRESO = 0;
        int anchoX,largoY;
        anchoX = largoY = mosaico;
        LinkedList<LinkedList<String>> imagenes = new LinkedList<>();
        int terminoX,terminoY;
        double rojoRGB ,verdeRGB,azulRGB,red,green,blue;
        int promedio = 0;
        PixelReader pixelI = filtro.getImage().getPixelReader();
        rojoRGB = verdeRGB = azulRGB = 0;
        
        File archivoSalida = new File(salida);
        archivoSalida.createNewFile();
        FileWriter html = new FileWriter(archivoSalida);
        try (BufferedWriter escritor = new BufferedWriter(html)) {
            String texto = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\" \n"
                    +"<tr>";
            String imagenTemp;
            escritor.write(texto);
            escritor.flush();
            
            for (int i = 0; i < filtro.getX(); i += anchoX) {
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
                            Filtro.PROGRESO = (filtro.avanzar()/filtro.getTotal());
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
            Filtro.PROGRESO = -1;
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
        }
        Desktop.getDesktop().browse(archivoSalida.toURI());
    }
    
    /**
     * Metodo privado para optener el archivo que necesitamos para hacer el fotomosaico.
     * @param dir - Es el directorio donde encontraremos las fotos.
     * @param recursivo - True si deseamos buscar en los directorios anidados.
     * @return Un archivo con las especificaciones de cada imagen dentro del directorio.
     * @throws IOException Lo lanza cuando hay problemas al leer el archivo o crearlo.
     */
    private static File sacaArchivos(File dir,boolean recursivo) throws IOException{
        String[] ext = {"png","jpg","jpeg","PNG","JPG","JPEG"};
        Collection<File> files = FileUtils.listFiles(dir,ext, recursivo);
        File salida = new File("valoresImagenes.dataLogIm");
        salida.createNewFile();
        FileWriter txt = new FileWriter(salida);
        try (BufferedWriter escritor = new BufferedWriter(txt)) {
            String texto;
            double total = files.size();
            double cuenta = 0;
            for (File img : files) {
                Filtro.PROGRESO = (cuenta++/total);
                double[] prom = promedio(img);
                texto = prom[0]+"#-"+prom[1]+"#-"+prom[2]+"#-"+img.getAbsolutePath()+"\n";
                escritor.write(texto);
                escritor.flush();
            }
        }
        return salida;
    }
    
    /**
     * Metodo para buscar la imagen que este mas cerca a distancia euclidiana
     * en referencia a los colores que se les pasa de parametros.
     * @param archivo - El archivo en donde se encuentran los datos.
     * @param red - El color rojo que se busca.
     * @param green - El color verde que se busca.
     * @param blue - El color azul que se busca.
     * @return Una cadena con la ruta de la imagen que mas se acerca a los colores.
     * @throws FileNotFoundException Si el archivo que se le pasa no existe.
     * @throws IOException Si al leer una linea del archivo el archivo esta corrupto o no existe.
     */
    private static String buscaImagen(File archivo, double red, double green,
            double blue) throws FileNotFoundException, IOException{
        String ruta = null;
        String linea;
        FileReader reader = new FileReader(archivo);
        BufferedReader buf = new BufferedReader(reader);
        double dif = Double.POSITIVE_INFINITY;
        String[] parString;
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
    
    /**
     * Metodo privado para sacar el promedio de una imagen.
     * @param img - Es la imagen que se quiere sacar el promedio.
     * @return Un arreglo con valores promedios en 0,1 y 2 de RGB respectivamente.
     * @throws FileNotFoundException Si el archivo que se pasa no existe.
     */
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
}//Fin de Fotomosaico.java