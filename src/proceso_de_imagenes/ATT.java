package proceso_de_imagenes;

import java.awt.image.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class ATT extends Filtro{
    
    
    
    /**
     * Altura de cada linea ATT.
     */
    static final int N = 12;
    
    public ATT(Image imagen) {
        super(imagen);
    }
    
    /**
     *
     * @return Imagen filtrada
     */
    public Image filtra() {
        AltoContrasteFiltroInverso altoContraste = new AltoContrasteFiltroInverso((Image)this.getImage());
        BufferedImage ac = SwingFXUtils.fromFXImage(altoContraste.altoContraste(),null);
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
        return SwingFXUtils.toFXImage(nueva, null);
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
