package UI;

import Building.GenericBank;
import DataStructure.JGraph;
import Simulation.Simulation;
import disMath.Node;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
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

    double simSpeed=0.01;
    private JPanel MainJpanel;
    private JPanel LeftPanel;
    private JTextPane textPaneMessage;
    private JButton button1;
    private JTree tree1;
    private JTabbedPane tabbedPane1;
    private JPanel JGraphPanel;
    private JTabbedPane tabbedPane2;
    private JPanel JGraphCars;
    private JSlider slider1;
    private JRadioButton drawRoadsRadioButton;
    private JRadioButton drawMapRadioButton;
    private JLabel TimeLabel;
    private JSlider slider2;
    private Simulation simulation;
    private Timer tm =new Timer(10,this);
    JGraph jGraph;
    JGraph jGraphCars;

    public UI() {

        tm.start();

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                simSpeed = ((double) slider1.getValue()/100d);

            }
        });

        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                for (Node genericBank:simulation.getMap().getGraph().getNodes())
                    ((GenericBank)genericBank).setMoneySpending(slider2.getValue());

            }
        });

        drawMapRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                      if (itemEvent.getStateChange()==1)
                        simulation.getMap().setVisibility(true);
                      else
                          simulation.getMap().setVisibility(false);

            }
        });

        drawRoadsRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange()==1)
                    simulation.getMap().setDrawRoad(true);
                else
                    simulation.getMap().setDrawRoad(false);

            }
        });

        tree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
if (treeSelectionEvent.getOldLeadSelectionPath()!=null)
                if (treeSelectionEvent.getOldLeadSelectionPath().getLastPathComponent()instanceof GenericBank)
                 ((GenericBank)treeSelectionEvent.getOldLeadSelectionPath().getLastPathComponent()).setSelected(false);

                ((GenericBank)tree1.getLastSelectedPathComponent()).setSelected(true);
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

        simulation=new Simulation(a);

        a.populateGraph();



        textPaneMessage =new JTextPane();

        MessageBoard out = new MessageBoard(textPaneMessage);
        System.setOut (new PrintStream(out));


        tree1= new JTree();

        tree1.setModel(a);



        a.setDoubleBuffered(true);


        jGraph = new JGraph("[Time]", "[Out of money facilities]");

        JGraphPanel=jGraph;

        jGraphCars = new JGraph("[Time]", "[On Road Cars]");

        JGraphCars=jGraphCars;



    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        simulation.nextStep(simSpeed);

        jGraph.getData().add(new Point2D.Double(simulation.getTimePassed(),simulation.getMap().outOfMoneyBanks()));
            jGraphCars.getData().add(new Point2D.Double(simulation.getTimePassed(),simulation.getMap().onRoadCars()));


        TimeLabel.setText("TimePassed: [" +simulation.getDate()+"]");
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
