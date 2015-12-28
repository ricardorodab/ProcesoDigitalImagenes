package proceso_de_imagenes;

import java.awt.image.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Convolucion extends Filtro{
    
    
    public Convolucion(Image imagen){
        super(imagen);
    }
    
    public Image emboss(){
        double [][] matriz = new double[][]{
            {-1, -1, -1, -1,  0},
            {-1, -1, -1,  0,  1},
            {-1, -1,  0,  1,  1},
            {-1,  0,  1,  1,  1},
            {0,  1,  1,  1,  1}
        };
        FiltroGris temp = new FiltroGris(aplicaConvolucion(
                aplicaConvolucion(this.getImage(), matriz, 1, 128), matriz,1,128));
        return temp.grisPromedio();
    }
    
    public Image sharpen(){
        double [][] matriz = new double[][]{
            {-1, -1, -1},
            {-1,  9, -1},
            {-1, -1, -1}
        };
        return aplicaConvolucion(this.getImage(),matriz,1,0);
    }
    
    
    public Image bordes(){
        double [][] matriz = new double[][]{
            {-1,  0,  0,  0,  0},
            {0, -2,  0,  0,  0},
            {0,  0,  6,  0,  0},
            {0,  0,  0, -2,  0},
            {0,  0,  0,  0, -1}
        };
        return aplicaConvolucion(aplicaConvolucion(this.getImage(), matriz, 1, 0),matriz,1,0);
    }
    
    public Image blur(){
        double [][] matriz = new double[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
        };
        return aplicaConvolucion(this.getImage(),matriz,(1.0/13.0),0);
    }
    
    public Image promedio(){
          double [][] matriz = new double[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };
        return aplicaConvolucion(this.getImage(),matriz,(1.0/9.0),0);
    }
    
    public Image motionBlur(){
        double [][] matriz = new double[][]{
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
        return aplicaConvolucion(this.getImage(),matriz,(1.0/9.0),0);
    }
    
    private Image aplicaConvolucion(Image imagenOriginal, double[][] matriz,double factor,double bias) {
        BufferedImage imagen = SwingFXUtils.fromFXImage(imagenOriginal, null);
        BufferedImage nuevoLienzo = enmarca(imagen, matriz.length / 2, matriz.length / 2);
        WritableRaster imagenD = nuevoLienzo.getRaster();
        int n = nuevoLienzo.getWidth();
        int m = nuevoLienzo.getHeight();
        WritableRaster nue = nuevoLienzo.getRaster();
        if (n < matriz.length || m < matriz.length) {
            return SwingFXUtils.toFXImage(imagen, null);
        }
        for (int i = 0; i < n - matriz.length; i++) {
            for (int j = 0; j < m - matriz.length; j++) {
                double r = 0;
                double g = 0;
                double b = 0;
                for (int x = i; x < i + matriz.length; x++) {
                    for (int y = j; y < j + matriz.length; y++) {
                        r += imagenD.getSample(x, y, 0) * matriz[x - i][y - j];
                        g += imagenD.getSample(x, y, 1) * matriz[x - i][y - j];
                        b += imagenD.getSample(x, y, 2) * matriz[x - i][y - j];
                    }
                }
                r = Math.min(Math.max((factor * r + bias), 0), 255); 
                g = Math.min(Math.max((factor * g + bias), 0), 255); 
                b = Math.min(Math.max((factor * b + bias), 0), 255); 

                nue.setSample(i + matriz.length / 2, j + matriz.length / 2, 0, r);
                nue.setSample(i + matriz.length / 2, j + matriz.length / 2, 1, g);
                nue.setSample(i + matriz.length / 2, j + matriz.length / 2, 2, b);
            }
        }
        BufferedImage nueva = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        nueva.setData(nue);
        int w = nueva.getWidth();
        int h = nueva.getHeight();
        return SwingFXUtils.toFXImage(nueva.getSubimage(matriz.length/2, matriz.length / 2, w - 2 * (matriz.length/2), h - 2 * (matriz.length / 2)),null);
    }
    
    private BufferedImage enmarca(BufferedImage imagen, int alto, int ancho) {
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
}
