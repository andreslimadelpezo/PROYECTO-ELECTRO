package Infrastructure.Serial;

import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;
import BusinessLogic.ParqueaderoBL;
import Infrastructure.AppException;

public class SerialArduino {
    private SerialPort puerto;
    private Scanner scanner;
    private Listener listener;
    ParqueaderoBL parqueaderoBL;
    private boolean detenido = false;

    public SerialArduino() throws AppException {
        try {
            this.parqueaderoBL = new ParqueaderoBL();
            System.out.println("SerialArduino: ParqueaderoBL listo.");
        } catch (Exception e) {
            throw new AppException("No se pudo iniciarl la bl desde serial arduino");
        }
    }

    public boolean conectar(String nombrePuerto) {
        puerto = SerialPort.getCommPort(nombrePuerto);
        puerto.setBaudRate(9600);
        puerto.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        if (puerto.openPort()) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            scanner = new Scanner(puerto.getInputStream());
            return true;
        }
        return false;
    }

    public void pausar() {
        this.detenido = true;
        System.out.println("--- Lectura pausada :---");
    }

    public void reanudar() {
    
    if (puerto != null && puerto.isOpen()) {
        byte[] basura = new byte[puerto.bytesAvailable()];
        puerto.readBytes(basura, basura.length); 
    }

    this.detenido = false;
    System.out.println("--- Puerto limpiado y lectura reanudada ---");
}

    public void escuchar() {
    new Thread(() -> {
        while (puerto != null && puerto.isOpen()) {
            try {

                if (detenido) {
                    Thread.sleep(200);
                    continue; 
                }

                if (puerto.bytesAvailable() > 0) {
                    if (scanner.hasNextLine()) {
                        String dato = scanner.nextLine().trim();
                        // Doble check por seguridad
                        if (!detenido && dato.startsWith("D:")) {
                            procesar(dato);
                        }
                    }
                }
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("Error en hilo: " + e.getMessage());
            }
        }
    }).start();
}

    public interface Listener {
        void onVehiculoDetectado(int distancia);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void procesar(String mensaje) {
        try {
            int dist = Integer.parseInt(mensaje.substring(2));
            System.out.println("Distancia: " + dist + " cm");

            if (parqueaderoBL != null && parqueaderoBL.esVehiculoValido(dist)) {
                if (listener != null) {
                    listener.onVehiculoDetectado(dist);
                }
            }
        } catch (Exception e) {
        }
    }

    public void enviarComando(char comando) {
        if (puerto != null && puerto.isOpen()) {
            puerto.writeBytes(new byte[] { (byte) comando }, 1);
            System.out.println("Comando enviado al Arduino: " + comando);
        }
    }
}