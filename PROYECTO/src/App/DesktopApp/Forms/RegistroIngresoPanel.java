package App.DesktopApp.Forms;

import java.awt.*;
import javax.swing.*;
import App.DesktopApp.CustomControl.*;
import DataAccess.DAOs.RegistroMovimientoDAO;
import DataAccess.DTOs.RegistroMovimientoDTO;
import Infrastructure.AppMSG;
import Infrastructure.Serial.SerialArduino;

public class RegistroIngresoPanel extends JPanel {
    private PatLabel lblTitulo = new PatLabel("📝 REGISTRO DE INGRESO");
    private PatTextBox txtPlaca = new PatTextBox();
    private PatTextBox txtNombre = new PatTextBox();
    private PatTextBox txtCedula = new PatTextBox();
    private PatButton btnGuardar = new PatButton("GUARDAR Y ABRIR");
    private PatButton btnCancelar = new PatButton("CANCELAR");
    private SerialArduino serial;

    public RegistroIngresoPanel(SerialArduino serial) {
        this.serial = serial;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- VALIDACIONES PREVENTIVAS ---

        // 1. Cédula: Solo números y máximo 10 dígitos
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtCedula.getText().length() >= 10) {
                    e.consume();
                }
            }
        });

        // 2. Placa: Forzar mayúsculas automáticamente
        txtPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                txtPlaca.setText(txtPlaca.getText().toUpperCase());
            }
        });

        // --- DISEÑO DE INTERFAZ ---

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new PatLabel("Placa:"), gbc);
        gbc.gridx = 1;
        add(txtPlaca, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new PatLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new PatLabel("Cédula:"), gbc);
        gbc.gridx = 1;
        add(txtCedula, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(btnGuardar, gbc);
        gbc.gridx = 1;
        add(btnCancelar, gbc);

        // Eventos
        btnGuardar.addActionListener(e -> btnGuardar_Click());
        btnCancelar.addActionListener(e -> btnCancelar_Click());
    }


    

    private void btnGuardar_Click() {

        
        String placa = txtPlaca.getText().trim();
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();

        if (placa.isEmpty() || nombre.isEmpty() || cedula.isEmpty()) {
            AppMSG.showError("Todos los campos son obligatorios.");
            return;
        }

        if (!placa.matches("^[A-Z]{3}-\\d{4}$")) {
            AppMSG.showError("Formato de placa inválido (Use: ABC-1234)");
            txtPlaca.requestFocus();
            return;
        }

        if (cedula.length() != 10) {
            AppMSG.showError("La cédula debe tener exactamente 10 dígitos.");
            txtCedula.requestFocus();
            return;
        }

        try {
            RegistroMovimientoDTO reg = new RegistroMovimientoDTO(placa, nombre, cedula);
            reg.setEstado("A"); 

            RegistroMovimientoDAO dao = new RegistroMovimientoDAO();
            if (dao.create(reg)) {
                if (serial != null)
                    serial.enviarComando('O'); 
                AppMSG.show("Vehículo registrado con éxito");
                txtPlaca.setText("");
                txtNombre.setText("");
                txtCedula.setText("");
                salir();
            }
        } catch (Exception ex) {
            AppMSG.showError("Error al guardar: " + ex.getMessage());
        }
    }

    private void btnCancelar_Click() {
        salir();
    }

    private void salir() {
        if (serial != null)
            serial.reanudar();
        this.setVisible(false);

    }
}