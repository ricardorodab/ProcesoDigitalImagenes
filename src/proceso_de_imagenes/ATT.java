package proceso_de_imagenes;

import java.awt.image.*;


public class ATT {

    /**
     * Altura de cada linea ATT.
     */
    static final int N = 12;

    /**
     
     * @param src Imagen original
     * @return Imagen filtrada
     */
    static BufferedImage filtra(BufferedImage src) {
        BufferedImage ac = src;//ContrasteAlto.filtra(src); //Otro filtro
        int w = ac.getWidth();
        int h = ac.getHeight();
        Raster rac = ac.getData();
        BufferedImage nueva = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        WritableRaster wrn = nueva.getRaster();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h - N; j += N) {
                int puntos = 0;
                for (int y = j; y < j + N; y++) {
                    if (rac.getSample(i, y, 0) == 0) {
                        puntos++;
                    }
                }
                
                boolean[] acomodados = centra(N, puntos);
                for (int y = j; y < j + N; y++) {
                    if (acomodados[y - j]) {
                        wrn.setSample(i, y, 0, 0);
                        wrn.setSample(i, y, 1, 0);
                        wrn.setSample(i, y, 2, 0);
                    } else {
                        wrn.setSample(i, y, 0, 0xff);
                        wrn.setSample(i, y, 1, 0xff);
                        wrn.setSample(i, y, 2, 0xff);
                    }
                }
            }
        }
        lineas(nueva);
        return nueva;
    }

    /**
     * Dibuja lineas horizontales blancas.
     *
     * @param src Imagen original
     */
    private static void lineas(BufferedImage src) {
        WritableRaster wr = src.getRaster();
        for (int i = 0; i < wr.getWidth(); i++) {
            for (int j = 0; j < wr.getHeight() - N; j += N) {
                wr.setSample(i, j, 0, 0xff);
                wr.setSample(i, j, 1, 0xff);
                wr.setSample(i, j, 2, 0xff);
            }
        }
    }

    /**
     * @param tam Tamano del arreglo
     * @param puntos Numero de puntos que deben ser centrados
     * @return Arreglo con los puntos centrados
     */
    private static boolean[] centra(int tam, int puntos) {
        boolean[] acomodados = new boolean[tam];
        int n = puntos / 2;
        int m = puntos % 2 == 0 ? n - 1 : n;
        for (int i = (tam / 2) - n; i <= (tam / 2) + m; i++) {
            acomodados[i] = true;
        }
        return acomodados;
    }
}
