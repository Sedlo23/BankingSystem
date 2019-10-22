package Simulation;

import Building.GenericBank;
import Building.Vehicles.Vehicle;
import UI.JMap;
import disMath.Node;

/**
 * Simulation
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 22.10.19
 */
public class Simulation {

    private double timePassed=0;

    private JMap map;

    public Simulation(JMap map) {
        this.map = map;
    }

    public void nextStep(double time)
    {



        for (Node genericBank:map.getGraph().getNodes())
        {
            ((GenericBank)genericBank).bankingActivity(time);
            for (Vehicle vehicle:((GenericBank)genericBank).getVehicleList())
            {
                vehicle.move(time);
            }
        }

        map.repaint();
    }
}
