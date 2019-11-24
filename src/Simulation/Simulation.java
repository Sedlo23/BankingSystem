package Simulation;

import Building.GenericBank;
import Building.Vehicles.Vehicle;
import UI.JMap;
import disMath.Node;

import java.util.ArrayList;


/**
 * Simulation
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 22.10.19
 */
public class Simulation implements Runnable {

    private double timePassed=0;

    private JMap map;

    private double time=0.5;

    public Simulation(JMap map)
    {
        this.map = map;
    }

    public void nextStep(double time)
    {

        if (time<0.10)
        {
            timePassed+=time;
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
        else {

            double tmpTime =time;
            time=0.1;
            for (double i =0;i<tmpTime;i+=0.1)
            {
        timePassed+=time;

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

        if (map.getRootBank()!=null&&(int) getTimePassed() % 24 == 0) {

            map.getRootBank().setMoneyAmount(7500000);
        }


    }

    public String getDate() {
        {
            int tmpTime=(int)(getTimePassed()*60);
            return ""+tmpTime/24/60+"D :"+tmpTime/60%24+"H :"+tmpTime%60+"M";
        }
    }

    public double getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(double timePassed) {
        this.timePassed = timePassed;
    }

    public JMap getMap() {
        return map;
    }

    public void setMap(JMap map) {
        this.map = map;
    }

    public ArrayList<Vehicle> getVehicles()
    {
       ArrayList<Vehicle>tmp = new ArrayList<Vehicle>() ;

       for (Node node:map.getGraph().getNodes())
           tmp.addAll(((GenericBank)node).getVehicleList());

        return tmp;
    }

    @Override
    public void run() {
                nextStep(time);
    }
}
