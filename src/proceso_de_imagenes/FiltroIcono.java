package proceso_de_imagenes;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;


class FiltroIcono extends Filtro{
    
    public FiltroIcono(Image imagen) {
        super(imagen);
    }
    
    /**
     * Regresa la imagen resultante de aplicar el filtro FiltroIcono.
     *
     * @param imagen Imagen original.
     * @param ancho Nueva altura.
     * @param alto Nueva anchura.
     * @return La imagen redimensionada.
     */
    public Image filtroIcono(int ancho, int alto) {
        int newX = this.x / ancho;
        int newY = this.y / alto;
        FiltroMosaico mos = new FiltroMosaico(this.imagen);
        Image icono = mos.sacaMosaico(newX, newY);
        BufferedImage mosaico = SwingFXUtils.fromFXImage(icono, null);
        java.awt.Image imagen2 = mosaico.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);       
        BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(imagen2, 0, 0, null);
        return SwingFXUtils.toFXImage(bi, null);
    }
    
    
}
