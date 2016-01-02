/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso_de_imagenes;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author ricardo_rodab
 */
public class Proceso extends InterfazGraficaController{
    
    public static void muestraProceso(double total){
        BorderPane cajaP = new BorderPane();
        HBox caja = new HBox(20);
        final ProgressBar pb = new ProgressBar(0);
        final ProgressIndicator pi = new ProgressIndicator(0);
        caja.getChildren().addAll(pb,pi);
        cajaP.setCenter(caja);
        final Stage third = new Stage();
        Scene scene = new Scene(cajaP);
        third.setScene(scene);
        third.show();
    }
    
}
