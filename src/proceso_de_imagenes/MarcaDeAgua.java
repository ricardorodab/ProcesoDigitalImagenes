package proceso_de_imagenes;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;


class MarcaDeAgua {

    /**
     * Dependiendo si la marca es a colores o en blanco y negro.
     */
    static final int BLACK = 0, COLOR = 1;
    /**
     * Regiones donde se puede colocar la marca de agua.
     */
    static final int SUP_IZQ = 1, SUP_DER = 2, INF_IZQ = 3, INF_DER = 4;

    /**
     * Dibuja una cadena sobre la imagen que le pasan en la region que le
     * indican y con el color que le indican.
     *
     * @param src
     * @param region
     * @param tipo
     * @return
     */
    static BufferedImage filtra(BufferedImage src, int region, int tipo) {
        String s = JOptionPane.showInputDialog("Escribe algo para el Marco de Agua");
        BufferedImage nueva = src;
        int w = nueva.getWidth();
        int h = nueva.getHeight();
        int size = (w / s.length()) / 2;
        Graphics g = nueva.getGraphics();
        Font f = new Font(Font.MONOSPACED, Font.PLAIN, size);
        g.setFont(f);
        if (tipo == COLOR) {
            g.setColor(Color.GREEN);
        }else{
            if (tipo == BLACK) {
            g.setColor(Color.BLACK);
        }
        } 
        
        switch (region) {
            case SUP_IZQ:
                g.drawString(s, 0, size);
                break;
            case SUP_DER:
                g.drawString(s, w - s.length() * size, size);
                break;
            case INF_IZQ:
                g.drawString(s, 0, h - size);
                break;
            case INF_DER:
                g.drawString(s, w - s.length() * size, h - size);
                break;
        }
        g.dispose();
        return nueva;
    }
}
