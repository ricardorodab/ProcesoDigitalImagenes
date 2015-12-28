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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private MenuItem abrir, nuevoItem, guardarComoI, salirI,cargaOriginal,
            acercaDe, cambiarBrillo, rotarItem, rotarMatrizItem, colorRealItem,
            webPalleteItem, lossy;
    @FXML
    private Menu aplicarFiltros,comprimirMenu;
    @FXML
    private Label label;
    @FXML
    private Parent root;
    @FXML
    private ImageView originalPermanente;
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
                break;
            case 8:
                pintaNegativo(event);
                break;
            case 9:
                pintaInverso(event);
                break;
            case 10:
                pintaAltoContraste(event);
                break;
            case 11:
                pintaATT(event);
                break;
            case 12:
                pintaGris3(event);
                break;
            case 13:
                pintaGris4(event,true);
                break;
            case 14:
                pintaGris4(event,false);
                break;
            case 15:
                pintaGris5(event,FiltroGris.ROJO);
                break;
            case 16:
                pintaGris5(event,FiltroGris.VERDE);
                break;
            case 17:
                pintaGris5(event,FiltroGris.AZUL);
                break;
            case 18:
                pintaMarcaDeAgua(event);
                break;
            case 19:
                pintaMosaico(event);
                break;
            case 20:
                pintaIcono(event);
                break;
            case 21:
                pintaBlur(event);
                break;
            case 22:
                pintaMotionBlur(event);
                break;
            case 23:
                pintaBordes(event);
                break;
            case 24:
                pintaSharpen(event);
                break;
            case 25:
                pintaEmboss(event);
                break;
            case 26:
                fusiona(event);
                break;
            case 27:
                pintaOleo(event);
                break;
            case 28:
                pintaGris6(event);
                break;
            case 29:
                pintaGris7(event);
                break;
            case 30:
                pintaMediana(event);
                break;
            case 31:
                pintaPromedio(event);
                break;
            case 32:
                pintaRGB(event);
                break;
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
            case "filtroNegativo":
                boton.setText("Aplicar filtro Negativo");
                noFiltro = 8;
                break;
            case "filtroInverso":
                boton.setText("Aplicar Filtro Inverso");
                noFiltro = 9;
                break;
            case "filtroAltoContraste":
                boton.setText("Aplicar A. contraste");
                noFiltro = 10;
                break;
            case "filtroATT":
                boton.setText("Aplicar filtro AT&T");
                noFiltro = 11;
                break;
            case "filtroGris3":
                boton.setText("Aplicar filtro Gris (Desaturación)");
                noFiltro = 12;
                break;
            case "filtroGris41":
                boton.setText("Aplicar filtro Gris (Max Descomposición)");
                noFiltro = 13;
                break;
            case "filtroGris42":
                boton.setText("Aplicar filtro Gris (Min Descomposición)");
                noFiltro = 14;
                break;
            case "filtroGris51":
                boton.setText("Aplicar filtro Gris (Con Rojo)");
                noFiltro = 15;
                break;
            case "filtroGris52":
                boton.setText("Aplicar filtro Gris (Con Verde)");
                noFiltro = 16;
                break;
            case "filtroGris53":
                boton.setText("Aplicar filtro Gris (Con Azul)");
                noFiltro = 17;
                break;
            case "filtroMarcaDeAgua":
                boton.setText("Aplicar filtro Marca de Agua");
                noFiltro = 18;
                break;
            case "filtroMosaico":
                boton.setText("Aplicar filtro Mosaico");
                noFiltro = 19;
                break;
            case "filtroIcono":
                boton.setText("Aplicar filtro Icono");
                noFiltro = 20;
                break;
            case "filtroBlur":
                boton.setText("Aplicar filtro Blur");
                noFiltro = 21;
                break;
            case "filtroMotionBlur":
                boton.setText("Aplicar filtro Motion Blur");
                noFiltro = 22;
                break;
            case "filtroBordes":
                boton.setText("Aplicar filtro Bordes");
                noFiltro = 23;
                break;
            case "filtroSharpen":
                boton.setText("Aplicar filtro Sharpen");
                noFiltro = 24;
                break;
            case "filtroEmboss":
                boton.setText("Aplicar filtro Emboss");
                noFiltro = 25;
                break;
            case "filtroBlending":
                boton.setText("Aplicar filtro Blending");
                noFiltro = 26;
                break;
            case "filtroOleo":
                boton.setText("Aplicar filtro Oleo");
                noFiltro = 27;
                break;
            case "filtroGris6":
                boton.setText("Aplicar filtro Gris (Por # Grises)");
                noFiltro = 28;
                break;
            case "filtroGris7":
                boton.setText("Aplicar filtro Gris con dithering");
                noFiltro = 29;
                break;
            case "filtroMediana":
                boton.setText("Aplicar filtro Mediana");
                noFiltro = 30;
                break;
            case "filtroPromedio":
                boton.setText("Aplicar filtro Promedio");
                noFiltro = 31;
                break;
            case "filtroRGB":
                boton.setText("Aplicar filtro RGB");
                noFiltro = 32;
                break;
                
                
        }
    }
    
    @FXML
    private void descomprimeLossy(ActionEvent event){
        FileChooser ventana = new FileChooser();
        FileChooser.ExtensionFilter extFilter0 = new FileChooser.ExtensionFilter("procIMG files (*.procImg)", "*.procImg");
        ventana.setTitle("Abrir");
        ventana.setSelectedExtensionFilter(extFilter0);
        File archivo = ventana.showOpenDialog(stage);
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                original = Compresion.descomprimeLossy(archivo);            
                imagen.setImage(original);
                originalPermanente.setImage(original);
                actual = original;
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    @FXML
    private void webPallete(ActionEvent event) throws IOException{
        ImagenesRecursivas recursiva = new ImagenesRecursivas(imagen.getImage());
        try {
            recursiva.escribeWebPallete("salida.html", 20, 20);
        } catch (IOException ex) {
            //ERROR
        }
    }
    
    @FXML
    private void comprimirLossy(ActionEvent event) throws IOException{
        Compresion.comprimeLossy(new Filtro(imagen.getImage()));
    }
    
    @FXML
    private void colorReal(ActionEvent event) throws IOException{
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Para este filtro implemento dos métodos: "
                + "\n a) Normal. Usando imágenes de resolución igual a la original lo que la hace más lento."
                + "\n b) Rápido. Usando íconos, reduciendo el tamaño de la imagen realizando el proceso en segundos."
                + "\n\n Por favor seleccione el método de su preferencia.");
        
        Button normal = new Button("Método normal");
        Button rapido = new Button("Método rápido");
        Button cancelar = new Button("Cancelar");
        
        HBox botones = new HBox(normal,rapido, cancelar);
        botones.setSpacing(20);
        
        border.setTop(encabezado);
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
        
        normal.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                second.close();
                int resp = MessageBox.show(stage, "Considere que este método puede tardar muchos minutos. \n"
                        + "¿Desea continuar?", "¿Está seguro?", MessageBox.YES | MessageBox.NO);
                if(resp == MessageBox.YES){
                    Stage second2 = new Stage();
                    
                    BorderPane border = new BorderPane();
                    Text encabezado = new Text("Ingrese el tamaño de los mosaicos \n"
                            + "Los valores pueden ir entre 1 a 9999");
                    
                    final Spinner numero = new Spinner(1, 9999, 10);
                    numero.setEditable(true);
                    
                    Button aceptar = new Button("Aceptar");
                    Button cancelar = new Button("Cancelar");
                    
                    HBox botones = new HBox(aceptar, cancelar);
                    botones.setSpacing(20);
                    
                    border.setTop(encabezado);
                    border.setCenter(numero);
                    border.setBottom(botones);
                    
                    
                    Scene sscene = new Scene(border);
                    second2.setScene(sscene);
                    second2.setMinHeight(100);
                    second2.setMinWidth(200);
                    
                    second2.show();
                    
                    cancelar.setOnAction(new EventHandler<ActionEvent>() {
                        
                        @Override
                        public void handle(ActionEvent event) {
                            second2.close();
                            principal.setDisable(false);
                        }
                    });
                    
                    aceptar.setOnAction(new EventHandler<ActionEvent>() {
                        
                        @Override
                        public void handle(ActionEvent event) {
                            second2.close();
                            String input = "";
                            TextInputDialog dialog = new TextInputDialog("ejemplo.html");
                            dialog.setTitle("Archivo de salida");
                            dialog.setHeaderText("Ingrese el nombre de archivo de salida:");
                            dialog.setContentText("Ingrese aqui el nombre de salida desea que tenga su archivo .html");
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent()){
                                input = result.get();
                            }else{
                                input = "imagen_recursiva.html";
                            }
                            if(!input.endsWith(".html")){
                                input = input.concat(".html");
                            }
                            final int cuadricula = (int)numero.getValue();
                            ImagenesRecursivas recursiva = new ImagenesRecursivas(imagen.getImage());
                            try {
                                recursiva.escribe(input, cuadricula, cuadricula,false);
                            } catch (IOException ex) {
                                //ERROR
                            }
                            second2.close();
                            principal.setDisable(false);
                        }
                    });
                }else{
                    principal.setDisable(false);
                }
            }
        });
        
        rapido.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                second.close();
                int resp = MessageBox.show(stage, "Considere que este método puede hacer que pierda calidad. \n"
                        + "¿Desea continuar?", "¿Está seguro?", MessageBox.YES | MessageBox.NO);
                if(resp == MessageBox.YES){
                    Stage second2 = new Stage();
                    
                    BorderPane border = new BorderPane();
                    Text encabezado = new Text("Ingrese el tamaño de los mosaicos \n"
                            + "Los valores pueden ir entre 1 a 9999");
                    
                    final Spinner numero = new Spinner(1, 9999, 10);
                    numero.setEditable(true);
                    
                    Button aceptar = new Button("Aceptar");
                    Button cancelar = new Button("Cancelar");
                    
                    HBox botones = new HBox(aceptar, cancelar);
                    botones.setSpacing(20);
                    
                    border.setTop(encabezado);
                    border.setCenter(numero);
                    border.setBottom(botones);
                    
                    
                    Scene sscene = new Scene(border);
                    second2.setScene(sscene);
                    second2.setMinHeight(100);
                    second2.setMinWidth(200);
                    
                    second2.show();
                    
                    cancelar.setOnAction(new EventHandler<ActionEvent>() {
                        
                        @Override
                        public void handle(ActionEvent event) {
                            second2.close();
                            principal.setDisable(false);
                        }
                    });
                    
                    aceptar.setOnAction(new EventHandler<ActionEvent>() {
                        
                        @Override
                        public void handle(ActionEvent event) {
                            second2.close();
                            String input = "";
                            TextInputDialog dialog = new TextInputDialog("ejemplo.html");
                            dialog.setTitle("Archivo de salida");
                            dialog.setHeaderText("Ingrese el nombre de archivo de salida:");
                            dialog.setContentText("Ingrese aqui el nombre de salida desea que tenga su archivo .html");
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent()){
                                input = result.get();
                            }else{
                                input = "imagen_recursiva.html";
                            }
                            if(!input.endsWith(".html")){
                                input = input.concat(".html");
                            }
                            final int cuadricula = (int)numero.getValue();
                            ImagenesRecursivas recursiva = new ImagenesRecursivas(imagen.getImage());
                            try {
                                recursiva.escribe(input, cuadricula, cuadricula,true);
                            } catch (IOException ex) {
                                //ERROR
                            }
                            
                            principal.setDisable(false);
                        }
                    });
                }else{
                    principal.setDisable(false);
                }
            }
        });
    }
    
    
    private void pintaRGB(ActionEvent event){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Ingrese los nuevos valores RGB \n"
                + "Los valores pueden ir entre 1 a 9999");
        
        final Spinner numeroR = new Spinner(-255, 255, 0);
        final Spinner numeroG = new Spinner(-255, 255, 0);
        final Spinner numeroB = new Spinner(-255, 255, 0);
        numeroR.setEditable(true);
        numeroG.setEditable(true);
        numeroB.setEditable(true);
        
        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");
        
        HBox botones = new HBox(aceptar, cancelar);
        botones.setSpacing(20);
        
        VBox red = new VBox(new Label("Rojo:"),numeroR);
        VBox green = new VBox(new Label("Verde:"),numeroG);
        VBox blue = new VBox(new Label("Azul:"),numeroB);
        
        HBox rgb = new HBox(red,green,blue);
        
        
        border.setTop(encabezado);
        border.setCenter(rgb);
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
                final int rojo = (int)numeroR.getValue();
                final int verde = (int)numeroG.getValue();
                final int azul = (int)numeroB.getValue();
                Thread hilo = new Thread(new Task() {
                    
                    @Override
                    protected Object call() throws Exception {
                        FiltroRGB rgbFiltro = new FiltroRGB(imagen.getImage());
                        actual = rgbFiltro.RGB(rojo, verde, azul);
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
    
    @FXML
    private void rotarImagen(ActionEvent e){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Seleccione cuantos grados desea girar la imagen \n");
        
        final ChoiceBox grados = new ChoiceBox(FXCollections.observableArrayList("0º","90º","180º","270º"));
        grados.getSelectionModel().selectFirst();
        
        
        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");
        
        HBox botones = new HBox(aceptar, cancelar);
        botones.setSpacing(20);
        
        border.setTop(encabezado);
        border.setCenter(grados);
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
                final int numGrados;
                if(grados.getValue().equals("90º")){
                    numGrados = 90;
                }else if(grados.getValue().equals("180º")){
                    numGrados = 180;
                }else if(grados.getValue().equals("270º")){
                    numGrados = 270;
                }else{
                    numGrados = 0;
                }
                
                Thread hilo = new Thread(new Task() {
                    
                    @Override
                    protected Object call() throws Exception {
                        Rotacion rotada = new Rotacion(imagen.getImage());
                        MenuItem fuente = (MenuItem)e.getSource();
                        if(fuente.getId().equals("rotarItem")){
                            actual = rotada.rotar(numGrados);
                        }else{
                            actual = rotada.rotarMatriz(numGrados);
                            
                        }
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
    
    private void fusiona(ActionEvent event){
        FileChooser ventana = new FileChooser();
        ventana.setTitle("Fusionar con");
        File archivo = ventana.showOpenDialog(stage);
        
        if (archivo != null) {
            try {
                final Image imagenEntrada = new Image(new FileInputStream(archivo));
                principal.setDisable(true);
                Stage second = new Stage();
                
                BorderPane border = new BorderPane();
                Text encabezado = new Text("Ingrese el porcentaje de visibilidad de la nueva imagen. \n"
                        + "Los valores pueden ir entre 0 a 100");
                
                final Spinner numero = new Spinner(0, 100, 50);
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
                        final double alpha = (double)((int)numero.getValue())/100;
                        Thread hilo = new Thread(new Task<Object>() {
                            
                            @Override
                            protected Object call() throws Exception {
                                Filtro externa = new Filtro(imagenEntrada);
                                Blending interna = new Blending(imagen.getImage());
                                actual = interna.licua(externa, 1-alpha);
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
            }catch(IOException e){
            }
        }
    }
    
    private void pintaPromedio(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Convolucion promedio = new Convolucion(imagen.getImage());
                actual = promedio.promedio();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaMediana(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                FiltroOleo oleo = new FiltroOleo(imagen.getImage());
                actual = oleo.mediana();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaOleo(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                FiltroOleo oleo = new FiltroOleo(imagen.getImage());
                actual = oleo.oleo();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaEmboss(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Convolucion convolucion = new Convolucion(imagen.getImage());
                actual = convolucion.emboss();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaSharpen(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Convolucion convolucion = new Convolucion(imagen.getImage());
                actual = convolucion.sharpen();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaBordes(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Convolucion convolucion = new Convolucion(imagen.getImage());
                actual = convolucion.bordes();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaMotionBlur(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Convolucion convolucion = new Convolucion(imagen.getImage());
                actual = convolucion.motionBlur();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaBlur(ActionEvent event){
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Convolucion convolucion = new Convolucion(imagen.getImage());
                actual = convolucion.blur();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaIcono(ActionEvent event){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Ingrese el tamaño final del icono \n"
                + "Los valores pueden ir entre 1 a 9999");
        
        final Spinner numero = new Spinner(1, 9999, 50);
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
                final int cuadricula = (int)numero.getValue();
                Thread hilo = new Thread(new Task() {
                    
                    @Override
                    protected Object call() throws Exception {
                        FiltroIcono icono = new FiltroIcono(imagen.getImage());
                        actual = icono.filtroIcono(cuadricula, cuadricula);
                        imagen.setImage(actual);
                        stage.getScene().setRoot(principal);
                        return null;
                    }
                });
                hilo.start();
                modificadores(true);
                second.close();
                principal.setDisable(false);
                int resp = MessageBox.show(stage, "El icono se verá reflejado en su tamaño final después de guardar. \n"
                        + "¿Desea guardar su icono?", "Guarde su icono", MessageBox.YES | MessageBox.NO);
                if(resp == MessageBox.YES){
                    guardarComo(event);
                }
            }
        });
    }
    
    private void pintaMosaico(ActionEvent event){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Ingrese el tamaño de los mosaicos \n"
                + "Los valores pueden ir entre 1 a 9999");
        
        final Spinner numero = new Spinner(1, 9999, 10);
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
                final int cuadricula = (int)numero.getValue();
                Thread hilo = new Thread(new Task() {
                    
                    @Override
                    protected Object call() throws Exception {
                        FiltroMosaico mosaico = new FiltroMosaico(imagen.getImage());
                        actual = mosaico.sacaMosaico(cuadricula, cuadricula);
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
    
    private void pintaMarcaDeAgua(ActionEvent event){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Seleccione la posición de su marca de agua \n");
        
        final ChoiceBox grados = new ChoiceBox(FXCollections.observableArrayList("Superior Izquierda","Superior Derecha",
                "Inferior Izquierda","Inferior Derecha"));
        grados.getSelectionModel().selectFirst();
        
        
        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");
        
        HBox botones = new HBox(aceptar, cancelar);
        botones.setSpacing(20);
        
        border.setTop(encabezado);
        border.setCenter(grados);
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
                second.close();
                final int posicionMarca;
                if(grados.getValue().equals("Superior Izquierda")){
                    posicionMarca = MarcaDeAgua.SUP_IZQ;
                }else if(grados.getValue().equals("Superior Derecha")){
                    posicionMarca = MarcaDeAgua.SUP_DER;
                }else if(grados.getValue().equals("Inferior Izquierda")){
                    posicionMarca = MarcaDeAgua.INF_IZQ;
                }else{
                    posicionMarca = MarcaDeAgua.INF_DER;
                }
                Thread hilo = new Thread(new Task<Object>() {
                    
                    @Override
                    protected Object call() throws Exception {
                        Image marca = MarcaDeAgua.filtra(imagen.getImage(),
                                posicionMarca, MarcaDeAgua.BLACK);
                        actual = marca;
                        imagen.setImage(actual);
                        stage.getScene().setRoot(principal);
                        return null;
                    }
                });
                principal.setDisable(false);
                hilo.start();
                modificadores(true);
            }
        });
    }
    
    private void pintaGris7(ActionEvent event){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Ingrese la cantidad de grises que desea \n"
                + "Los valores pueden ir entre 2 a 256");
        
        final Spinner numero = new Spinner(2, 256, 100);
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
                final int cuadricula = (int)numero.getValue();
                Thread hilo = new Thread(new Task<Object>() {
                    
                    @Override
                    protected Object call() throws Exception {
                        FiltroGris gris = new FiltroGris(imagen.getImage());
                        actual = gris.grisCuantosDithering(cuadricula);
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
    
    private void pintaGris6(ActionEvent event){
        principal.setDisable(true);
        Stage second = new Stage();
        
        BorderPane border = new BorderPane();
        Text encabezado = new Text("Ingrese la cantidad de grises que desea \n"
                + "Los valores pueden ir entre 2 a 256");
        
        final Spinner numero = new Spinner(2, 256, 100);
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
                final int cuadricula = (int)numero.getValue();
                Thread hilo = new Thread(new Task<Object>() {
                    
                    @Override
                    protected Object call() throws Exception {
                        FiltroGris gris = new FiltroGris(imagen.getImage());
                        actual = gris.grisCuantos(cuadricula);
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
    
    private void pintaGris3(ActionEvent event){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroGris gris = new FiltroGris(imagen.getImage());
                actual = gris.grisDesaturacion();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaGris4(ActionEvent event,boolean max){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroGris gris = new FiltroGris(imagen.getImage());
                actual = gris.grisDescomposicion(max);
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaGris5(ActionEvent event, int colorNum){
        Thread hilo = new Thread(new Task<Object>() {
            
            @Override
            protected Object call() throws Exception {
                FiltroGris gris = new FiltroGris(imagen.getImage());
                actual = gris.grisColor(colorNum);
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaATT(ActionEvent event) {
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                ATT ATAndT = new ATT(imagen.getImage());
                actual = ATAndT.filtra();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaAltoContraste(ActionEvent event) {
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                AltoContrasteFiltroInverso altoContraste = new AltoContrasteFiltroInverso(imagen.getImage());
                actual = altoContraste.altoContraste();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    
    private void pintaInverso(ActionEvent event) {
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                AltoContrasteFiltroInverso inverso = new AltoContrasteFiltroInverso(imagen.getImage());
                actual = inverso.inverso();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
    }
    private void pintaNegativo(ActionEvent event) {
        Thread hilo = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                FiltroNegativo negativo = new FiltroNegativo(imagen.getImage());
                actual = negativo.negativo();
                imagen.setImage(actual);
                stage.getScene().setRoot(principal);
                return null;
            }
        });
        hilo.start();
        modificadores(true);
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
                originalPermanente.setImage(null);
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
        ventana.setTitle("Abrir");
        File archivo = ventana.showOpenDialog(stage);
        
        if (archivo != null) {
            try {
                final Image imagenEntrada = new Image(new FileInputStream(archivo));
                original = imagenEntrada;
                Thread hilo = new Thread(new Task<Object>() {
                    
                    @Override
                    protected Object call() throws Exception {
                        imagen.setImage(imagenEntrada);
                        originalPermanente.setImage(imagenEntrada);
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
            rotarItem.setDisable(!valor);
            rotarMatrizItem.setDisable(!valor);
            colorRealItem.setDisable(!valor);
            webPalleteItem.setDisable(!valor);
            lossy.setDisable(!valor);
            comprimirMenu.setDisable(!valor);
        }else{
            rotarItem.setDisable(!valor);
            cambiarBrillo.setDisable(!valor);
            guardarComoI.setDisable(!valor);
            aplicarFiltros.setDisable(!valor);
            cargaOriginal.setDisable(!valor);
            rotarMatrizItem.setDisable(!valor);
            colorRealItem.setDisable(!valor);
            webPalleteItem.setDisable(!valor);
            lossy.setDisable(!valor);
            comprimirMenu.setDisable(!valor);
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
