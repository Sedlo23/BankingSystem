package DataStructure;

import disMath.Edge;

import java.util.LinkedList;

/**
 * DataStructure
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 06.11.19
 */
public class Path extends LinkedList<Edge>
{

    public int getWeight()
    {
        int tmp =0;

        for(Edge edge:this)
            tmp+=edge.getWeight();

        return tmp;

    }


}
