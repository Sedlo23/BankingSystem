package UI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * UI
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 15.10.19
 */
public class UI {


    private JPanel MainJpanel;
    private JPanel LeftPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("UI");
        frame.setContentPane(new UI().MainJpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        JMap a= new JMap();

        LeftPanel =a;

        a.populateGraph();

        LeftPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (a.getMousePosition() != null)
                    a.collisionDetection(e.getPoint());

            }
        });
    }
}
