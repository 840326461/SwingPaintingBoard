import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolMenuListener implements ActionListener {
    MainFrame mainFrame;

    public ToolMenuListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DrawType temp = DrawType.valueOf(e.getActionCommand());
        mainFrame.setDrawType(temp);
    }
}
