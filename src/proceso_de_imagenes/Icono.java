package proceso_de_imagenes;

import java.awt.Image;
import java.awt.image.BufferedImage;


 class Icono {

    /**
     * Regresa la imagen resultante de aplicar el filtro Icono.
     *
     * @param imagen Imagen original.
     * @param w Nueva altura.
     * @param h Nueva anchura.
     * @return La imagen redimensionada.
     */
     static final BufferedImage filtra(BufferedImage imagen, int w, int h) {
        int tam_pix_x = imagen.getWidth() / w;
        int tam_pix_y = imagen.getHeight() / h;
        BufferedImage mosaico = imagen;//Mosaico.filtra(imagen, tam_pix_x, tam_pix_y);
        Image icono = mosaico.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(icono, 0, 0, null);
        return bi;
    }
}
