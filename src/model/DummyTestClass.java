package model;

public class DummyTestClass {
	public static void main(String[] args) {
		// Re-factor this to unit tests at some point, this was for sanity checking the conversion and re-factoring
        Cell[][] Grid = {
        	{ new Cell(0,0),new Cell(0,1),new Cell(0,2),new Cell(0,3),new Cell(0,4),new Cell(0,5) },
        	{ new Cell(1,0),new Cell(1,1),new Cell(1,2),new Cell(1,3),new Cell(1,4),new Cell(1,5) },
        	{ new Cell(2,0),new Cell(2,1),new Cell(2,2),new Cell(2,3),new Cell(2,4),new Cell(2,5) },
            { new Cell(3,0),new Cell(3,1),new Cell(3,2),new Cell(3,3),new Cell(3,4),new Cell(3,5) },
            { new Cell(4,0),new Cell(4,1),new Cell(4,2),new Cell(4,3),new Cell(4,4),new Cell(4,5) },
            { new Cell(5,0),new Cell(5,1),new Cell(5,2),new Cell(5,3),new Cell(5,4),new Cell(5,5) }
        };
		
		BFS<Cell> test = new BFS<Cell>(Grid);
		Cell startLocation = Grid[5][2];
		Cell endLocation = Grid[0][4];
		LinearNode<Cell> result = test.findPath(startLocation, endLocation);
        if (result != null)
        {
        	LinearNode<Cell> currentNode = result;
            int step = 1;
            System.out.println("Path found");
            while (currentNode.getNext() != null)
            {
            	System.out.println("step " + step + ": (row: " + currentNode.getElement().getRow() + ")(col: " + currentNode.getElement().getColumn() + ")");
                currentNode = currentNode.getNext();
                step++;
            }
        	System.out.println("step " + step + ": (row: " + currentNode.getElement().getRow() + ")(col: " + currentNode.getElement().getColumn() + ")");
        }
        else
        {
        	System.out.println("No possible path");
        }
	}
}
