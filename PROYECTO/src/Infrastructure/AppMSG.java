//  © 2K26 ❱──💀──❰ pat_mic ? code is life : life is code
package Infrastructure;

import javax.swing.JOptionPane;

public abstract class AppMSG {
    private AppMSG() {}

    public static final void show(String msg){
        JOptionPane.showMessageDialog(null, msg, "Parqueadero Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static final void showError(String msg){
        JOptionPane.showMessageDialog(null, msg, "Error de Sistema", JOptionPane.ERROR_MESSAGE);
    }

    public static final boolean showConfirmYesNo(String msg){
        return (JOptionPane.showConfirmDialog(null, msg, "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }
}