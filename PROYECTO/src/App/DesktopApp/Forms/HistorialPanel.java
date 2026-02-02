package App.DesktopApp.Forms;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import App.DesktopApp.CustomControl.*;
import DataAccess.DAOs.RegistroMovimientoDAO;
import DataAccess.DTOs.RegistroMovimientoDTO;
import Infrastructure.AppMSG;
import Infrastructure.AppStyle;

public class HistorialPanel extends JPanel {
    private PatLabel     lblTitulo   = new PatLabel("VEHÍCULOS ACTIVOS");
    private PatLabel     lblBuscar   = new PatLabel("Buscar Placa:");
    private PatTextBox   txtBuscar   = new PatTextBox();
    private PatButton    btnRefrescar= new PatButton(" Refrescar");
    private PatButton    btnSalida   = new PatButton(" Registrar Salida");
    
    private JTable            tabla;
    private DefaultTableModel modelo;
    private List<RegistroMovimientoDTO> listaCompleta;

    public HistorialPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- CABECERA (Título y Búsqueda) ---
        JPanel pnlNorth = new JPanel(new GridLayout(2, 1, 10, 10));
        pnlNorth.setOpaque(false);

        JPanel pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setOpaque(false);
        lblTitulo.setCustomizeComponent(lblTitulo.getText(), AppStyle.FONT_BOLD, AppStyle.COLOR_FONT, AppStyle.ALIGNMENT_LEFT);
        pnlTitle.add(lblTitulo, BorderLayout.WEST);
        pnlTitle.add(btnRefrescar, BorderLayout.EAST);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.setOpaque(false);
        txtBuscar.setPreferredSize(new Dimension(150, 25));
        pnlSearch.add(lblBuscar);
        pnlSearch.add(txtBuscar);

        pnlNorth.add(pnlTitle);
        pnlNorth.add(pnlSearch);
        add(pnlNorth, BorderLayout.NORTH);

        // --- TABLA ---
        String[] columnas = {"ID", "Placa", "Nombre Responsable", "Cédula", "Ingreso"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.setFont(AppStyle.FONT);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(AppStyle.FONT_BOLD);
        tabla.getTableHeader().setForeground(AppStyle.COLOR_FONT);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(AppStyle.createBorderRect());
        add(scrollPane, BorderLayout.CENTER);

        // --- PIE (Botón Salida) ---
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSouth.setOpaque(false);
        pnlSouth.add(btnSalida);
        add(pnlSouth, BorderLayout.SOUTH);

        // --- EVENTOS ---
        btnRefrescar.addActionListener(e -> cargarDatos());
        
        // Filtro en tiempo real
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarDatos(txtBuscar.getText().toUpperCase());
            }
        });

        // Lógica de Salida
        btnSalida.addActionListener(e -> registrarSalida());

        cargarDatos();
    }

    private void cargarDatos() {
        try {
            listaCompleta = new RegistroMovimientoDAO().readAll();
            mostrarEnTabla(listaCompleta);
        } catch (Exception e) {
            AppMSG.showError("Error al cargar datos: " + e.getMessage());
        }
    }

    private void mostrarEnTabla(List<RegistroMovimientoDTO> lista) {
        modelo.setRowCount(0);
        for (RegistroMovimientoDTO v : lista) {
            modelo.addRow(new Object[]{ v.getIdRegistro(), v.getPlaca(), v.getNombreResponsable(), v.getCedulaResponsable(), v.getFechaIngreso() });
        }
    }

    private void filtrarDatos(String texto) {
        if (listaCompleta == null) return;
        List<RegistroMovimientoDTO> filtrada = listaCompleta.stream()
            .filter(v -> v.getPlaca().contains(texto))
            .collect(Collectors.toList());
        mostrarEnTabla(filtrada);
    }

    private void registrarSalida() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            AppMSG.show("Por favor, seleccione un vehículo de la tabla.");
            return;
        }

        try {
            Integer id = (Integer) modelo.getValueAt(fila, 0);
            String placa = (String) modelo.getValueAt(fila, 1);

            if (AppMSG.showConfirmYesNo("¿Confirmar salida del vehículo con placa: " + placa + "?")) {
                RegistroMovimientoDAO dao = new RegistroMovimientoDAO();
                if (dao.delete(id)) {
                    AppMSG.show("Salida registrada con éxito.");
                    cargarDatos(); 
                }
            }
        } catch (Exception e) {
            AppMSG.showError("Error al registrar salida: " + e.getMessage());
        }
    }
}