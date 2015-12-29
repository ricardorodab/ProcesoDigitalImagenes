/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso_de_imagenes;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author ricardo_rodab
 */
public class AmpliacionReduccion extends Filtro{

    public AmpliacionReduccion(Image imagen) {
        super(imagen);
    }
    
    public Image apliaReduce(int newX, int newY){
        Image icono = this.getImage();
        BufferedImage mosaico = SwingFXUtils.fromFXImage(icono, null);
        java.awt.Image imagen2 = mosaico.getScaledInstance(newX, newY, java.awt.Image.SCALE_SMOOTH);       
        BufferedImage bi = new BufferedImage(newX, newY, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(imagen2, 0, 0, null);
        return SwingFXUtils.toFXImage(bi, null);
        }
    }
    

