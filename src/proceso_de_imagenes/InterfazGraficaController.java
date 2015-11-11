/* -------------------------------------------------------------------
* ProyectoProcesoImagenes.java
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

import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jfx.messagebox.MessageBox;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Aug 20 2015.
 * <p>
 * Clase principal de la vista.</p>
 *
 * <p>
 * Dsde esta clase se mantiene la vista y el controlador.</p>
 */
public class InterfazGraficaController implements Initializable {
    
    protected static Stage stage;
    /** Variable para poder optener en tiempo constante la imagen original. */
    private static Image original = null;
    /** Variable para optener en tiempo constante la imagen actual. */
    private static Image actual = null;
    /** El numero de filtros actuales. */
    private static int noFiltro = 0;
    
    //Definimos los utencilios de nuestra intefaz grafica:
    @FXML
    private MenuItem abrir, nuevoItem, guardarComoI, salirI,cargaOriginal,acercaDe, cambiarBrillo;
    @FXML
    private Menu aplicarFiltros;
    @FXML
    private Label label;
    @FXML
    private Parent root;
    @FXML
    private ImageView imagen;
    @FXML
    private AnchorPane principal;
    @FXML
    private Button boton;
    
    /**
     * Metodo que aplica los filtros a la imagen actual.
     * @param event – es el evento del boton.
     */
    @FXML
    private void aplicaFiltro(ActionEvent event){
        
        switch(noFiltro){
            case 0:
                verOriginal(event);
                break;
            case 1:
                pintaRojo(event);
                break;
            case 2:
                pintaAzul(event);
                break;
            case 3:
                pintaVerde(event);
                break;
            case 4:
                pintaAzar(event);
                break;
            case 5:
                pintaGris(event);
                break;
            case 6:
                pintaGris2(event);
                break;
            case 7:
                pintaBlackLight(event);
            default:
                verOriginal(event);
                break;
        }
    }
    
    /**
     * Cambia el filtro actual del la imagen.
     * @param event – es el evento del menuItem.
     */
    @FXML
    private void cambiaFiltro(ActionEvent event){
        MenuItem fuente = (MenuItem)event.getSource();
        switch(fuente.getId()){
            case "filtroRojo":
                boton.setText("Aplicar filtro Rojo");
                noFiltro = 1;
                break;
            case "filtroAzul":
                boton.setText("Aplicar filtro Azul");
                noFiltro = 2;
                break;
            case "filtroVerde":
                boton.setText("Aplicar filtro Verde");
                noFiltro = 3;
                break;
            case "filtroAzar":
                boton.setText("Aplicar filtro Azar");
                noFiltro = 4;
                break;
            case "filtroGris":
                boton.setText("Aplicar filtro Gris (Promedio)");
                noFiltro = 5;
                break;
            case "filtroGris2":
                boton.setText("Aplicar filtro Gris (Valores)");
                noFiltro = 6;
                break;
            case "filtroBlackLight":
                boton.setText("Aplicar filtro BlackLight");
                noFiltro = 7;
                break;
                
                
        }
    }
    
    @FXML
    private void cambiaBrillo(ActionEvent e){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Ingrese el nuevo valor de brillo \n"
                + "Los valores pueden ir entre -255 a 255");
        
        final Spinner numero = new Spinner(-255, 255, 0);
        numero.setEditable(true);
        
        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");
        
        HBox botones = new HBox(aceptar, cancelar);
        botones.setSpacing(20);
        
        border.setTop(encabezado);
        border.setCenter(numero);
        border.setBottom(botones);
        
        
        Scene sscene = new Scene(border);
        second.setScene(sscene);
        second.setMinHeight(100);
        second.setMinWidth(200);
        
        second.show();
        
        cancelar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                second.close();
                principal.setDisable(false);
            }
        });
        
        aceptar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               final int brillo = (int)numero.getValue();
               Thread hilo = new Thread(new Task() {

                   @Override
                   protected Object call() throws Exception {
                       FiltroBrillo fbrillo = new FiltroBrillo(imagen.getImage(), brillo);
                       actual = fbrillo.filtroBrillo();
                       imagen.setImage(actual);
                       stage.getScene().setRoot(principal);
                       return null;
                   }
               });
               hilo.start();
               modificadores(true);
               second.close();
               principal.setDisable(false);
            }
        });
                
        
    }
    
    
    private void pintaBlackLight(ActionEvent event){
        Thread hilo = new Thread(new Task() {

            @Override
            protected Object call() throws Exception {
                BlackLight blacklight = new BlackLight(imagen.getImage());
                actual = blacklight.filtroBlackLight();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaAzar(ActionEvent event){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroRGB azar = new FiltroRGB(imagen.getImage());
                actual = azar.azar();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    
    //Metodo privado que pinta gris con r*.3+g*.59+b*.11
    private void pintaGris2(ActionEvent event){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroGris gris = new FiltroGris(imagen.getImage());
                actual = gris.grisValores();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    //Metodo privado que pinta la imagen con el promedio de la suma de r+g+b/3
    private void pintaGris(ActionEvent event){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroGris gris = new FiltroGris(imagen.getImage());
                actual = gris.grisPromedio();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    //Metodo privado para pintar la imagen de verde.
    private void pintaVerde(ActionEvent event){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroRGB verde = new FiltroRGB(imagen.getImage());
                actual = verde.verde();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    //Metodo privado para pintar la imagen de azul.
    private void pintaAzul(ActionEvent event){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroRGB azul = new FiltroRGB(imagen.getImage());
                actual = azul.azul();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    //Metodo privado para pintar la imagen de rojo.
    private void pintaRojo(ActionEvent event){
        //anterior = imagen.getImage();
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroRGB rojo = new FiltroRGB(imagen.getImage());
                actual = rojo.rojo();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    /**
     * Metodo para volver al principio y poner la imagen original.
     * @param event – es el evento que desata el metodo.
     */
    @FXML
    private void verOriginal(ActionEvent event){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                imagen.setImage(original);
                actual = original;
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    /**
     * Metodo para salir del programa.
     * @param event – es la llamada a la funcion.
     */
    @FXML
    private void salir(ActionEvent event){
        if(original != imagen.getImage()){
            int confirmacion = MessageBox.show(new Stage(), "¿Está seguro que desea salir sin guardar?", "Salir",MessageBox.ICON_WARNING | MessageBox.YES | MessageBox.NO);
            if(confirmacion == MessageBox.YES)
                System.exit(0);
        }else{
            System.exit(0);
        }
    }
    
    /**
     * Metodo para guardar la imagen.
     * @param event – es la llamada a la funcion.
     */
    @FXML
    private void guardarComo(ActionEvent event){
        if(original == null)
            return;
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter0 = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().addAll(extFilter2,extFilter1,extFilter0);
        File file = fileChooser.showSaveDialog(stage);
        
        try {
            
            ImageIO.write(SwingFXUtils.fromFXImage(actual, null),
                    "png", file);
        } catch (IOException ex) {
            
            
        }
        
    }
    
    /**
     * Metodo para cerrar todo y empezar de cero.
     * @param event – es la llamada a la funcion.
     */
    @FXML
    private void abrirNuevoItem(ActionEvent event){
        if(original == null){
            return;
        }
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                imagen.setImage(null);
                actual = null;
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(false);
        original = null;
    }
    
    /**
     * Metodo para abrir una imagen.
     * @param event – es la llamada a la funcion.
     */
    @FXML
    private void abrirImagen(ActionEvent event){
        FileChooser ventana = new FileChooser();
        File archivo = ventana.showOpenDialog(stage);
        ventana.setTitle("Abrir");
        if (archivo != null) {
            try {
                final Image imagenEntrada = new Image(new FileInputStream(archivo));
                original = imagenEntrada;
                Thread hilo = new Thread(new Task<Object>() {
                    
                    @Override
                    protected Object call() throws Exception {
                        imagen.setImage(imagenEntrada);
                        actual = imagenEntrada;
                        stage.getScene().setRoot(principal);
                        return null;
                    }
                });
                hilo.start();
                modificadores(true);
            }catch(IOException e){
            }
        }
    }
    //Metodo privado para activar y desactivar funciones.
    private void modificadores(boolean valor){
        if(valor){
            aplicarFiltros.setDisable(!valor);
            cargaOriginal.setDisable(!valor);
            guardarComoI.setDisable(!valor);
            cambiarBrillo.setDisable(!valor);
        }else{
            cambiarBrillo.setDisable(!valor);
            guardarComoI.setDisable(!valor);
            aplicarFiltros.setDisable(!valor);
            cargaOriginal.setDisable(!valor);
        }
    }
    
    /**
     * Método que despliega la licencia del programa y una pequeña ayuda.
     * @param event – es el evento.
     */
    @FXML
    private void acercaDe(ActionEvent event){
        String msj = " Programa creador de filtros:\n" +
                " Versión 1.0\n" +
                " Copyright (C) 2015  José Ricardo Rodríguez Abreu.\n" +
                " Facultad de Ciencias,\n" +
                " Universidad Nacional Autónoma de México, Mexico.\n" +
                " \n" +
                " Este programa es software libre; se puede redistribuir\n" +
                " y/o modificar en los términos establecidos por la\n" +
                " Licencia Pública General de GNU tal como fue publicada\n" +
                " por la Free Software Foundation en la versión 2 o\n" +
                " superior.\n" +
                " \n" +
                " Este programa es distribuido con la esperanza de que\n" +
                " resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho\n" +
                " sin la garantía implícita de COMERCIALIZACIÓN o\n" +
                " ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la\n" +
                " Licencia Pública General de GNU para mayores detalles.\n" +
                " \n" +
                " Con este programa se debe haber recibido una copia de la\n" +
                " Licencia Pública General de GNU, de no ser así, visite el\n" +
                " siguiente URL:\n" +
                " http://www.gnu.org/licenses/gpl.html\n" +
                " o escriba a la Free Software Foundation Inc.,\n" +
                " 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.\n\n"
                + "Proyecto - Proceso digital de imágenes. \n"
                + "Alumno: José Ricardo Rodríguez Abreu. \n"
                + "IMPORTANTE: Cualquier tema realcionado enviar correo a ricardo_rodab@ciencias.unam.mx";
        
        MessageBox.show(new Stage(), msj, "Un poco sobre el programa...",
                MessageBox.OK);
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
} //Fin de InterfazGraficaController.java
