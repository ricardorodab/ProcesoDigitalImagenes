/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package proceso_de_imagenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageWriter;
import static proceso_de_imagenes.InterfazGraficaController.stage;

/**
 *
 * @author ricardo_rodab
 */
public class Compresion {
    
    public static void comprimeLossy(Filtro filtro) throws IOException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter0 = new FileChooser.ExtensionFilter("procIMG files (*.procImg)", "*.procImg");
        fileChooser.getExtensionFilters().addAll(extFilter0);
        File file = fileChooser.showSaveDialog(stage);
        file.createNewFile();
        FileWriter salida = new FileWriter(file);
        BufferedWriter escritor = new BufferedWriter(salida);
        String texto = filtro.getX()+" "+filtro.getY()+"\n";
        escritor.write(texto);
        escritor.flush();
        Color color;
        int contador = 0;
        double siguiente, promedio;
        siguiente = 0;
        for (int i = 0; i < filtro.getX(); i++) {
            for (int j = 0; j < filtro.getY()-1; j++) {
                color = filtro.getImage().getPixelReader().getColor(i, j);
                promedio = (int)(promedio(color)*255);
               contador = j+1;
                while(contador < filtro.getY()){
                    siguiente = (int)(promedio(filtro.getImage().getPixelReader().getColor(i, contador))*255);
                    if(promedio != siguiente)
                        break;
                    contador++;
                }
                Integer primedioFin = (int)(promedio);
                texto = Integer.toHexString(primedioFin)+" "+(contador-j)+" ";
                escritor.write(texto);
                escritor.flush();
                j = contador-1;
            }
            texto = "\n";
            escritor.write(texto);
            escritor.flush();
        }
        escritor.close();
    }
    
    public static Image descomprimeLossy(File archivo){
        WritableImage imagen = null;
        if (archivo != null) {
            try {
                BufferedReader buff = new BufferedReader(new FileReader(archivo));
                String linea = buff.readLine();
                String[] parseado = linea.trim().split(" ");
                imagen = new WritableImage(Integer.parseInt(parseado[0]),Integer.parseInt(parseado[1]));
                int numLinea = 0;
                while((linea = buff.readLine()) != null){
                    parseado = linea.trim().split(" ");
                    int j = 0;
                    int temp = 0;
                    for (int i = 0; i < parseado.length; i += 2) {
                        while(temp < Integer.parseInt(parseado[i+1])){
                            int color = Integer.parseInt(parseado[i], 16);
                            imagen.getPixelWriter().setColor(numLinea, j, Color.rgb(color, color, color));
                            j++;
                            temp++;
                        }
                        temp = 0;
                    }
                    numLinea++;
                }
            }catch(IOException e){
                System.out.println(e);
            }
        }
        return imagen;
    }
    
    private static double promedio(Color color){
        return ((color.getBlue()+color.getGreen()+color.getRed())/3);
    }
    
}
