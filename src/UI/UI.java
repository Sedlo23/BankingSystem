package UI;

import Simulation.Simulation;
import disMath.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;

/**
 * UI
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 15.10.19
 */
public class UI implements ActionListener  {


    private JPanel MainJpanel;
    private JPanel LeftPanel;
    private JPanel MessagePanel;
    private JTextPane textPaneMessage;
    private JList nodeJList;
    private JButton button1;
    private Simulation simulation;
    private Timer tm =new Timer(10,this);

    public UI() {


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                simulation.nextStep(1);
                nodeJList.updateUI();
            }
        });
        tm.start();
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

        simulation=new Simulation(a);

        a.populateGraph(5,5,20);





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

        a.setDoubleBuffered(true);

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        simulation.nextStep(1);
    }

    private BufferedImage toCompatibleImage(BufferedImage image)
    {
        // obtain the current system graphical settings
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfxConfig.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage newImage = gfxConfig.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = newImage.createGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return newImage;
    }
}
