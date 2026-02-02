package App.DesktopApp.Forms;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import DataAccess.DTOs.RegistroMovimientoDTO; 

public class ParqueaderoGrafico extends JPanel {
    private Image imgFondo;
    private Image imgCarro;
    private List<RegistroMovimientoDTO> listaCarros;

    public ParqueaderoGrafico() {
        try {
            // Carga de recursos desde el Classpath
            imgFondo = new ImageIcon(getClass().getResource("/Infrastructure/Assets/img/ParqueaderoVacio.jpg")).getImage();
            imgCarro  = new ImageIcon(getClass().getResource("/Infrastructure/Assets/img/Carro.jpg")).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar imágenes: " + e.getMessage());
        }
        setPreferredSize(new Dimension(1000, 400)); 
    }

    public void actualizarCarros(List<RegistroMovimientoDTO> carros) {
        this.listaCarros = carros;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Mejorar la calidad del dibujo
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Dibujar el fondo
        if (imgFondo != null) {
            g2d.drawImage(imgFondo, 0, 0, getWidth(), getHeight(), this);
        }

        // 2. Dibujar los 14 carros (7 arriba y 7 abajo)
        if (listaCarros != null && imgCarro != null) {
            // Limitamos a 14 espacios totales
            int totalADibujar = Math.min(listaCarros.size(), 14);

            for (int i = 0; i < totalADibujar; i++) {
                int x, y;
                int columna = i % 7;   // Esto garantiza que después de 7 carros, la columna vuelva a empezar en 0
                boolean esFilaAbajo = i >= 7; // Los primeros 7 (0-6) van arriba, del 7 al 13 abajo

                // --- POSICIÓN X (Horizontal) ---
                // Ajustado para que el último (columna 6) salte el espacio vacío
                if (columna < 6) {
                    x = (int) (getWidth() * (0.027 + (columna * 0.108)));
                } else {
                    // El 7mo carro de cada fila salta al cuadro de la derecha
                    x = (int) (getWidth() * 0.885);
                }

                // --- POSICIÓN Y (Vertical) ---
                if (!esFilaAbajo) {
                    // FILA ARRIBA: 8% de la altura
                    y = (int) (getHeight() * 0.08); 
                } else {
                    // FILA ABAJO: 72% de la altura (ajustado para que bajen más)
                    y = (int) (getHeight() * 0.72); 
                }

                // --- TAMAÑO DEL CARRO ---
                int anchoCarro = (int) (getWidth() * 0.085);
                int altoCarro = (int) (getHeight() * 0.26);

                g2d.drawImage(imgCarro, x, y, anchoCarro, altoCarro, this);
            }
        }
    }
}