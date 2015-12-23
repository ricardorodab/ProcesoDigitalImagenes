/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso_de_imagenes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javafx.scene.image.Image;

/**
 *
 * @author ricardo_rodab
 */
public class FiltrosHTML extends Filtro{

    public FiltrosHTML(Image imagen) {
        super(imagen);
    }
    
    public void escribe(URL resources, String salida) throws IOException{
        BufferedWriter escritor = new BufferedWriter(new FileWriter(salida));
        String texto = "<center><table>";
        escritor.write(texto);
        escritor.write("\n");
        escritor.flush();
        
    }
    
}
