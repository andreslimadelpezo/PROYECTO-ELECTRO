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
    


    public PantallaInicial() {
        setLayout(new GridLayout(4, 1, 10, 10));

        try {
            
            pBL = new ParqueaderoBL();
            actualizarDatos(); 
            
        Timer timer = new Timer(1000, e -> {
            actualizarDatos();
        });
        timer.start(); 

        } catch (Exception e) {
            lblNombre.setText("Error al conectar con la base de datos");
            System.err.println("Error en PantallaInicial: " + e.getMessage());
        }
        
        add(lblNombre);
        add(lblCapacidad);
        add(lblOcupados);
        add(lblLibres);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;


    }
    

    public void actualizarDatos() {
    try {
        if (pBL != null) {
            ParqueaderoDTO estado = pBL.getResumenEstado();
            
            lblNombre.setText("🏪 " + estado.getNombre());
            lblCapacidad.setText("📊 Capacidad Total: " + estado.getCapacidadTotal());
            lblOcupados.setText("🚗 Ocupados: " + estado.getOcupados());
            lblLibres.setText("✅ Libres: " + estado.getLibres()); 

            if (estado.getLibres() <= 0) { 
                lblLibres.setText("❌ SIN ESPACIO");
                lblLibres.setForeground(Color.RED);
            } else {
                lblLibres.setForeground(new Color(34, 139, 34)); 
            }
        }
    } catch (AppException e) {
        lblNombre.setText("⚠️ Error de conexión a la BD");
    }
}
}