package App.DesktopApp.Forms;

import javax.swing.*;
import java.awt.*;
import App.DesktopApp.CustomControl.*;
import BusinessLogic.ParqueaderoBL;
import DataAccess.DTOs.ParqueaderoDTO;
import Infrastructure.AppException;

public class PantallaInicial extends JPanel {
    private ParqueaderoBL pBL;
    private PatLabel lblNombre    = new PatLabel();
    private PatLabel lblCapacidad = new PatLabel();
    private PatLabel lblOcupados  = new PatLabel();
    private PatLabel lblLibres    = new PatLabel();
    private PanelParqueoGrafico pnlGrafico;

    public PantallaInicial() {
        setLayout(new BorderLayout(10, 10));

        // Panel de información superior
        JPanel pnlInfo = new JPanel(new GridLayout(1, 4, 10, 10));
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.add(lblNombre);
        pnlInfo.add(lblCapacidad);
        pnlInfo.add(lblOcupados);
        pnlInfo.add(lblLibres);

        pnlGrafico = new PanelParqueoGrafico();

        try {
            pBL = new ParqueaderoBL();
            actualizarDatos(); 
            
            // Refresco cada segundo
            Timer timer = new Timer(1000, e -> actualizarDatos());
            timer.start(); 
        } catch (Exception e) {
            lblNombre.setText("Error de conexión");
        }
        
        add(pnlInfo, BorderLayout.NORTH);
        add(pnlGrafico, BorderLayout.CENTER);
    }

    public void actualizarDatos() {
        try {
            if (pBL != null) {
                ParqueaderoDTO estado = pBL.getResumenEstado();
                lblNombre.setText(estado.getNombre());
                lblCapacidad.setText("Total: " + estado.getCapacidadTotal());
                lblOcupados.setText("Ocupados: " + estado.getOcupados());
                lblLibres.setText("Libres: " + estado.getLibres()); 

                // Enviamos la cantidad de ocupados al panel de dibujo
                pnlGrafico.setCantidadCarros(estado.getOcupados());

                if (estado.getLibres() <= 0) { 
                    lblLibres.setForeground(Color.RED);
                } else {
                    lblLibres.setForeground(new Color(34, 139, 34)); 
                }
            }
        } catch (AppException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
        }
    }

    // --- CLASE INTERNA CORREGIDA PARA 7 ARRIBA Y 7 ABAJO ---
    private class PanelParqueoGrafico extends JPanel {
        private Image imgFondo;
        private Image imgCarro;
        private int cantidadCarros = 0;

        public PanelParqueoGrafico() {
            try {
                imgFondo = new ImageIcon(getClass().getResource("/Infrastructure/Assets/img/ParqueaderoVacio.jpg")).getImage();
                imgCarro = new ImageIcon(getClass().getResource("/Infrastructure/Assets/img/Carro.jpg")).getImage();
            } catch (Exception e) {
                System.err.println("Error cargando imágenes: " + e.getMessage());
            }
        }

        public void setCantidadCarros(int cantidad) {
            this.cantidadCarros = cantidad;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            if (imgFondo != null) {
                g2d.drawImage(imgFondo, 0, 0, getWidth(), getHeight(), this);
            }

            if (imgCarro != null) {
                // Limitamos a 14 espacios (7x2)
                int totalADibujar = Math.min(cantidadCarros, 14);

                for (int i = 0; i < totalADibujar; i++) {
                    // CAMBIO CLAVE: divisor 7 para que el 8vo pase a la siguiente fila
                    int fila = i / 7; 
                    int col  = i % 7;
                    
                    int x, y;

                    // Lógica de X: Salto en la columna 6 (el 7mo carro)
                    if (col < 6) {
                        x = (int) (getWidth() * (0.027 + (col * 0.108)));
                    } else {
                        x = (int) (getWidth() * 0.885); // Puesto separado
                    }
                    
                    // Lógica de Y: Ajustado para que la fila de abajo esté centrada
                    if (fila == 0) {
                        y = (int) (getHeight() * 0.08); // Arriba
                    } else {
                        y = (int) (getHeight() * 0.68); // Abajo (bajado al centro)
                    }
                    
                    // Tamaño del carro proporcional
                    int anchoCarro = (int) (getWidth() * 0.085);
                    int altoCarro = (int) (getHeight() * 0.28);
                    
                    g2d.drawImage(imgCarro, x, y, anchoCarro, altoCarro, this); 
                }
            }
        }
    }
}