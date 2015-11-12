package proceso_de_imagenes;

import java.awt.image.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

 class Convolucion {

    /**
     * Regresa la imagen resultante al aplicarle el Filtro Convolucion.
     *
     * @param imagen Imagen original.
     * @param mat Matriz para aplicar la convolucion.
     * @return Imagen filtrada.
     */
     public static final Image filtra(Image imagenOriginal, double[][] mat) {
         BufferedImage imagen = SwingFXUtils.fromFXImage(imagenOriginal, null);
         int k = mat.length;
        BufferedImage enmarcada = enmarca(imagen, k / 2, k / 2);
        WritableRaster img = enmarcada.getRaster();
        int n = enmarcada.getWidth();
        int m = enmarcada.getHeight();
        WritableRaster nue = enmarcada.getRaster();
        if (n < k || m < k) {
            return SwingFXUtils.toFXImage(imagen, null);
        }
        double factor = suma(mat);
        for (int i = 0; i < n - k; i++) {
            for (int j = 0; j < m - k; j++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (int x = i; x < i + k; x++) {
                    for (int y = j; y < j + k; y++) {
                        r += img.getSample(x, y, 0) * mat[x - i][y - j];
                        g += img.getSample(x, y, 1) * mat[x - i][y - j];
                        b += img.getSample(x, y, 2) * mat[x - i][y - j];
                    }
                }
                if (factor != 0) {
                    r /= Math.abs(factor);
                    b /= Math.abs(factor);
                    g /= Math.abs(factor);
                }
                //r = Procesador.acota(r);
                //g = Procesador.acota(g);
                //b = Procesador.acota(b);
                nue.setSample(i + k / 2, j + k / 2, 0, r);
                nue.setSample(i + k / 2, j + k / 2, 1, g);
                nue.setSample(i + k / 2, j + k / 2, 2, b);
            }
        }
        BufferedImage nueva = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        nueva.setData(nue);
        return desenmarca(nueva, k / 2, k / 2);
    }

    /**
     * @param imagen Imagen original.
     * @param ancho Anchura del marco.
     * @return Imagen con el marco.
     */
    public static BufferedImage enmarca(BufferedImage imagen, int alto, int ancho) {
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        BufferedImage nueva = new BufferedImage(w + 2 * alto, h + 2 * ancho, BufferedImage.TYPE_INT_RGB);
        WritableRaster img = imagen.getRaster();
        WritableRaster wr = nueva.getRaster();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int r = img.getSample(i, j, 0);
                int g = img.getSample(i, j, 1);
                int b = img.getSample(i, j, 2);
                wr.setSample(i + alto, j + ancho, 0, r);
                wr.setSample(i + alto, j + ancho, 1, g);
                wr.setSample(i + alto, j + ancho, 2, b);
            }
        }
        nueva.setData(wr);
        return nueva;
    }

    /**
     * @param imagen Imagen original.
     * @param ancho Anchura dle marco.
     * @return Iamgen sin el marco.
     */
    public static Image desenmarca(BufferedImage imagen, int ancho, int alto) {
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        return SwingFXUtils.toFXImage(imagen.getSubimage(ancho, alto, w - 2 * ancho, h - 2 * alto),null);
    }

    /**
     * @param mat Matriz.
     * @return Suma de las entradas.
     */
    public static double suma(double[][] mat) {
        double suma = 0;
        for (double[] mat1 : mat) {
            for (int j = 0; j < mat[0].length; j++) {
                suma += mat1[j];
            }
        }
        return suma;
    }
}
