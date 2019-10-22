package main;

public class Entity2 extends Entity
{    
	private int[] neighbors = new int[]{0, 1, 3};
	private int entity = 2;

    // Perform any necessary initialization in the constructor
    public Entity2()
    {
    	System.out.println(
    			"___________________________________________\r\n" + 
    			"            Node 2 Initialized           \n" + 
    			"___________________________________________"
    	);
    	
    	// distance to Entity0
    	distanceTable[0][0] = 0;
    	distanceTable[0][1] = 999;
    	distanceTable[0][2] = 999;
    	distanceTable[0][3] = 999;
    	
    	// distance to Entity1
    	distanceTable[1][0] = 999;
    	distanceTable[1][1] = 0;
    	distanceTable[1][2] = 999;
    	distanceTable[1][3] = 999;
    	
    	// distance to Entity2
    	distanceTable[2][0] = 3;
    	distanceTable[2][1] = 1;
    	distanceTable[2][2] = 0;
    	distanceTable[2][3] = 2;
    	
    	// distance to Entity3
    	distanceTable[3][0] = 999;
    	distanceTable[3][1] = 999;
    	distanceTable[3][2] = 999;
    	distanceTable[3][3] = 0;
    	
    	// print initial distance table
    	printDT();
    	
    	// store the minimum costs from entity0
    	int[] minCost = new int[NetworkSimulator.NUMENTITIES];
    	
    	System.arraycopy(distanceTable[entity], 0, minCost, 0, NetworkSimulator.NUMENTITIES);
    	
    	// create packets for each neighbor
    	for (int i=0; i < neighbors.length; i++){
    		Packet entityPacket = new Packet(entity, neighbors[i], minCost);
    		NetworkSimulator.toLayer2(entityPacket);
    	}
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
    	boolean tableUpdated = false;
    	int source = p.getSource();
        int dest = p.getDest();
        
        // update this entities distance tables from neighbors packet
    	for (int i=0; i < NetworkSimulator.NUMENTITIES; i++){
    		distanceTable[source][i] = p.getMincost(i);
    	}
        
    	// update entities own distance table
    	for (int i=0; i < NetworkSimulator.NUMENTITIES; i++){
    		if(distanceTable[entity][i] > (distanceTable[entity][source] + p.getMincost(i))) {
    			distanceTable[entity][i] = distanceTable[entity][source] + p.getMincost(i);
    			tableUpdated = true;
    		}
    	}
        
    	// send out updated table from this entity
    	if(tableUpdated) {
    		int[] minCost = new int[NetworkSimulator.NUMENTITIES];
    		System.arraycopy(distanceTable[entity], 0, minCost, 0, NetworkSimulator.NUMENTITIES);
    		for (int i=0; i < neighbors.length; i++){
        		Packet entityPacket = new Packet(entity, neighbors[i], minCost);
        		NetworkSimulator.toLayer2(entityPacket);
        	}
    	}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
      {
          System.out.println();
          System.out.println("           via");
          System.out.println(" D2 |  0   1   2   3");
          System.out.println("----+-----------------");
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
