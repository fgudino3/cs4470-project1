package main;

public class Entity0 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity0() {
    	System.out.println("___________________________________________\r\n" + 
    			"            Node 0 Initialized           \n" + 
    			"___________________________________________");
    	
    	// distance to Entity0
    	distanceTable[0][0] = 0;
    	distanceTable[0][1] = 1;
    	distanceTable[0][2] = 3;
    	distanceTable[0][3] = 7;
    	
    	// distance to Entity1
    	distanceTable[1][0] = 999;
    	distanceTable[1][1] = 0;
    	distanceTable[1][2] = 999;
    	distanceTable[1][3] = 999;
    	
    	// distance to Entity2
    	distanceTable[2][0] = 999;
    	distanceTable[2][1] = 999;
    	distanceTable[2][2] = 0;
    	distanceTable[2][3] = 999;
    	
    	// distance to Entity3
    	distanceTable[3][0] = 999;
    	distanceTable[3][1] = 999;
    	distanceTable[3][2] = 999;
    	distanceTable[3][3] = 0;
    	
    	// print initial distance table
    	printDT();
    	
    	// store the minimum costs from entity0
    	int[] minCost = new int[NetworkSimulator.NUMENTITIES];
    	
    	System.arraycopy(distanceTable[0], 0, minCost, 0, NetworkSimulator.NUMENTITIES);
    	
    	// create packets for each neighbor
    	Packet entity1Packet = new Packet(0, 1, minCost);
    	Packet entity2Packet = new Packet(0, 2, minCost);
    	Packet entity3Packet = new Packet(0, 3, minCost);
    	
    	// send each neighbor its respective packet
    	NetworkSimulator.toLayer2(entity1Packet);
    	NetworkSimulator.toLayer2(entity2Packet);
    	NetworkSimulator.toLayer2(entity3Packet);
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p) {
    	System.out.println("___________________________________________\r\n" + 
    			"         Node 0 started updating         \n" + 
    			"___________________________________________");
    	// TODO: implement distance vector algorithm update distanceTable and
    	// send each entity the new minCost array
    	int[] minCost = new int[NetworkSimulator.NUMENTITIES];
    	
    	// create packets for each neighbor
    	Packet entity1Packet = new Packet(0, 1, minCost);
    	Packet entity2Packet = new Packet(0, 2, minCost);
    	Packet entity3Packet = new Packet(0, 3, minCost);
    	
    	// send each neighbor its respective packet
    	NetworkSimulator.toLayer2(entity1Packet);
    	NetworkSimulator.toLayer2(entity2Packet);
    	NetworkSimulator.toLayer2(entity3Packet);
    	
    	System.out.println("___________________________________________\r\n" + 
    			"         Node 0 finished updating        \n" + 
    			"___________________________________________");
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
