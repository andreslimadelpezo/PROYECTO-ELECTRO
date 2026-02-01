package App.DesktopApp.Forms;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import App.DesktopApp.CustomControl.PatButton;
import Infrastructure.AppException;
import Infrastructure.AppMSG;
import Infrastructure.Serial.SerialArduino;

public class AppStart extends JFrame {
    private JPanel pnlMenu = new JPanel();
    private JPanel pnlMain = new PantallaInicial();
    private SerialArduino serial;

    public AppStart(String titleApp) {
        initComponents(titleApp);
        initSensor();
    }

    private void initSensor() {
        try {
            serial = new SerialArduino();

            if (serial.conectar("COM4")) {
                serial.escuchar();

                serial.setListener(distancia -> {
                    serial.pausar();
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (AppMSG.showConfirmYesNo("¡Vehículo detectado a " + distancia + " cm!\n¿Desea registrar el ingreso?")) {
                            try {
                                BusinessLogic.ParqueaderoBL bl = new BusinessLogic.ParqueaderoBL();

                                if (bl.tieneEspacio()) {
                                    setPanel(new RegistroIngresoPanel(serial));
                                } else {
                                    AppMSG.showError("El parqueadero está LLENO.\n no puede registar mas vehiculos");
                                    serial.reanudar();
                                }

                            } catch (Exception ex) {
                                AppMSG.showError("Error al verificar capacidad: " + ex.getMessage());
                                serial.reanudar();
                            }

                        } else {
                             serial.reanudar();
                        }
                    });
                });
            } else {
                AppMSG.showError("No se pudo conectar al puerto COM4. Verifique el Arduino.");
            }
        } catch (AppException e) {
            AppMSG.showError("Error al inicializar la lógica del sensor: " + e.getMessage());
        }
    }

    public void setPanel(JPanel formularioPanel) {
        Container container = getContentPane();
        container.remove(pnlMain);
        pnlMain = formularioPanel;
        container.add(pnlMain, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void initComponents(String titleApp) {
        setTitle(titleApp);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Botones del Menú
        PatButton btnInicio = new PatButton("🏠 Inicio");
        PatButton btnIngreso = new PatButton("🚗 Registrar Ingreso");
        PatButton btnHistorial = new PatButton("📋 Historial");

        // Acciones
        btnInicio.addActionListener(e -> setPanel(new PantallaInicial()));
        // btnIngreso.addActionListener(e -> setPanel(new
        // RegistroIngresoPanel(serial)));
        btnHistorial.addActionListener(e -> setPanel(new HistorialPanel()));

        // Diseño del menú (Vertical)
        pnlMenu.setLayout(new GridLayout(10, 1, 5, 5));
        pnlMenu.add(btnInicio);
        pnlMenu.add(btnIngreso);
        pnlMenu.add(btnHistorial);

        // Contenedor principal
        setLayout(new BorderLayout());
        add(pnlMenu, BorderLayout.WEST);
        add(pnlMain, BorderLayout.CENTER);

        setVisible(true);
    }
}