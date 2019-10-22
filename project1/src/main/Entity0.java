package main;

public class Entity0 extends Entity
{
    // Perform any necessary initialization in the constructor
    public Entity0()
    {

        System.out.println("___________________________________________\r\n" +
                "            Node 0 Initialized           \n" +
                "___________________________________________");

        // distance from Entity0
        distanceTable[0][0] = 0;
        distanceTable[0][1] = 1;
        distanceTable[0][2] = 3;
        distanceTable[0][3] = 7;

        // distance from Entity1
        distanceTable[1][0] = 999;
        distanceTable[1][1] = 0;
        distanceTable[1][2] = 999;
        distanceTable[1][3] = 999;

        // distance from Entity2
        distanceTable[2][0] = 999;
        distanceTable[2][1] = 999;
        distanceTable[2][2] = 0;
        distanceTable[2][3] = 999;

        // distance from Entity3
        distanceTable[3][0] = 999;
        distanceTable[3][1] = 999;
        distanceTable[3][2] = 999;
        distanceTable[3][3] = 0;

        // print initial distance table
        printDT();


        // store the minimum costs from entity0
        int[] minCost = new int[NetworkSimulator.NUMENTITIES];

        //Parameters :
        //source_arr : array to be copied from
        //sourcePos : starting position in source array from where to copy
        //dest_arr : array to be copied in
        //destPos : starting position in destination array, where to copy in
        //len : total no. of components to be copied.
        System.arraycopy(distanceTable[0], 0, minCost, 0, NetworkSimulator.NUMENTITIES);


        //node 0 is able to link to nodes 1 , 2, and 3
        routePacket(0 , 1 , minCost);
        routePacket(0 , 2 , minCost);
        routePacket(0 , 3 , minCost);


    }


    public void routePacket(int source, int destination, int [] minCostArray){
        //at first source: 0 , dest: 1, mincost 0=0 1=1 2=3 3=7
        Packet p = new Packet(source, destination, minCostArray);

        //packet is sent with the information of source, destination, and minCostArray to toLayer2
        //this handles updates when a packet is received
        NetworkSimulator.toLayer2(p);
    }


    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
        boolean shouldUpdate = false;
        int source = p.getSource();
        int destination = p.getDest();

        //Preforming Bellman Ford Algorithm, Cost
        for (int i=0; i < NetworkSimulator.NUMENTITIES; i++){

            //System.out.println("Source: " + p.getSource() + " Destination: " + p.getDest()  + " MinCost: " + p.getMincost(i) +  " Before bellman: " + distanceTable[source][i]);

            if (distanceTable[source][i] > p.getMincost(i) + distanceTable[source][source]){

                //System.out.println("Source: " + p.getSource() + "Destination: " + p.getDest() + "getMinCost: " + p.getMincost(i));

                distanceTable[source][i] = p.getMincost(i) + distanceTable[source][source];
                shouldUpdate = true;
            }

            //System.out.println("Source: " + p.getSource() + " Destination: " + p.getDest()  + " MinCost: " + p.getMincost(i) +  " After bellman: " + distanceTable[source][i]);

        }

        if(shouldUpdate) {

            System.out.println("___________________________________________\r\n" +
                    "            The beginning of update() in node 0          \n" +
                    "___________________________________________");

            printDT();
//            int[] minCost = new int[NetworkSimulator.NUMENTITIES];
//
//            System.arraycopy(distanceTable[0], 0, minCost, 0, NetworkSimulator.NUMENTITIES);
//
//            routePacket(0 , 1 , minCost);
//            routePacket(0 , 2 , minCost);
//            routePacket(0 , 3 , minCost);

            System.out.println("___________________________________________\r\n" +
                    "            The end of update() in node 0          \n" +
                    "___________________________________________");
        }else {
            printDT();
            System.out.println("______________NO CHANGE___________");
        }

    }



    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }

    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   0   1   2   3");
        System.out.println("----+------------------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (distanceTable[i][j] < 10)
                {
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else
                {
                    System.out.print(" ");
                }

                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
