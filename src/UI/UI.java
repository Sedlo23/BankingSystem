package UI;

import disMath.Graph;
import disMath.Node;

import javax.swing.*;
import java.awt.event.*;
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
    private JButton button1;
    private JTabbedPane tabbedPane1;
    private Graph graph;

    public UI() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                    graph.computePaths(graph.getNodes().get(0));

            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UI");
        frame.setContentPane(new UI().MainJpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    private void createUIComponents() throws CloneNotSupportedException {
        JMap a= new JMap();

        LeftPanel =a;

        a.populateGraph(50,50);

        graph=a.getGraph();

        LeftPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (a.getMousePosition() != null)
                    a.collisionDetection(e.getPoint());
            }
        });

        LeftPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (a.getMousePosition() != null)
                a.collisionDetectionNodeSelection(e.getPoint());

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
