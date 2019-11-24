package UI;

import Building.GenericBank;
import Building.Vehicles.Vehicle;
import DataStructure.JGraph;
import Simulation.Simulation;
import disMath.Node;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
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
    private JTree tree1;
    private JTabbedPane tabbedPane1;;
    private JPanel JGraphPanel;
    private JPanel JGraphCars;
    private JSlider slider1;
    private JRadioButton drawRoadsRadioButton;
    private JRadioButton drawMapRadioButton;
    private JLabel TimeLabel;
    private JSlider slider2;
    private JPanel JGraphRootBankMoney;
    private JPanel JGraphWait;
    private JList CarsList;
    private JTextPane MessegeArea;
    private JPanel CalcGraph;
    private Simulation simulation;
    private LogMessenger messenger;



    private Timer tm =new Timer(10,this);
    JGraph jGraph;
    JGraph jGraphCars;
    JGraph jGraphRootBank;
    JGraph jGraphWait;
    JGraph jCalc;

    public UI()
    {

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

        CarsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                for (int i = 0; i < CarsList.getModel().getSize(); i++) {
                   ((Vehicle)CarsList.getModel().getElementAt(i)).setSelected(false);
                }

                MessegeArea.setText("");

                for (Object vehicle:CarsList.getSelectedValuesList()) {
                    ((Vehicle) vehicle).setSelected(true);
                    MessegeArea.setText(MessegeArea.getText()+"\n"+vehicle.toString());
                }


            }
        });


    }


    public static void main(String[] args)
    {

        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.xrender","True");
        JFrame frame = new JFrame("UI");
        frame.setContentPane(new UI().MainJpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });


    }

    private void createUIComponents() throws CloneNotSupportedException {

        JMap a= new JMap();

        LeftPanel =a;

        simulation=new Simulation(a);

        a.populateGraph();

        textPaneMessage =new JTextPane();

        messenger = new LogMessenger(textPaneMessage);

        System.setOut (new PrintStream(messenger));

        tree1= new JTree();

        tree1.setModel(a);

        a.setDoubleBuffered(true);

        jGraph = new JGraph("[Time]", "[Out of money facilities]");

        JGraphPanel=jGraph;

        jGraphCars = new JGraph("[Time]", "[On Road Cars]");

        JGraphCars=jGraphCars;

        jGraphRootBank = new JGraph("[Time]", "[Money Amount]");

        JGraphRootBankMoney=jGraphRootBank;

        jGraphWait= new JGraph("[Time]", "[Wait time]");

        JGraphWait=jGraphWait;

        jCalc= new JGraph("[Time]", "[Calculations]");

        CalcGraph=jCalc;



         CarsList = new JList(simulation.getVehicles().toArray());


         jCalc.getData().add(new Point2D.Double(0,0));





    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {


                simulation.nextStep(simSpeed);

                jGraph.getData().add(new Point2D.Double(simulation.getTimePassed(),simulation.getMap().outOfMoneyBanks()));
                jGraphCars.getData().add(new Point2D.Double(simulation.getTimePassed(),simulation.getMap().onRoadCars()));
                jGraphRootBank.getData().add(new Point2D.Double(simulation.getTimePassed(),simulation.getMap().getRootBank().getMoneyAmount()));


                jCalc.getData().add(new Point2D.Double(simulation.getTimePassed(),messenger.getLogNum()));

                jCalc.setMovingAvg(true);

                messenger.setLogNum(0);

                double av=0;

                for (Node bank:simulation.getMap().getGraph().getNodes())
                    av+=((GenericBank)bank).getWithoutMoneyTime();

                av=((av)/(double)simulation.getMap().getGraph().getNodes().size());

                jGraphWait.getData().add(new Point2D.Double(simulation.getTimePassed(),av));

                TimeLabel.setText("TimePassed: [" +simulation.getDate()+"]");

        jGraphWait.repaint();
        jGraph.repaint();
        jGraphRootBank.repaint();
        jGraphCars.repaint();
        CalcGraph.repaint();
        CarsList.updateUI();
        tree1.updateUI();
            }

}
