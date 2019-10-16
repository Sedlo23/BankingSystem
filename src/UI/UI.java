package UI;

import disMath.Node;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.PrintStream;

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
    private JPanel MessagePanel;
    private JTextPane textPaneMessage;
    private JList nodeJList;

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

        textPaneMessage =new JTextPane();

        MessageBoard out = new MessageBoard(textPaneMessage);
        System.setOut (new PrintStream(out));

        nodeJList = new JList();

        DefaultListModel<Node> defaultListModel = new DefaultListModel<>();

        a.getGraph().getNodes().forEach(x -> defaultListModel.addElement(x));

        nodeJList.setModel(defaultListModel);

    }
}
