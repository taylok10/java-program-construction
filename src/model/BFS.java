/**
 * 
 */
package model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author kelly.taylor
 *
 */
public class BFS implements PathFinder {
	private Cell[][] grid;
	private Queue<LinearNode<Cell>> tree;
	
	public BFS(Cell[][] gridCells) {
		this.grid = gridCells;
	}
	
	/* (non-Javadoc)
	 * @see model.PathFinder#findPath(model.Cell, model.Cell)
	 */
	@Override
	public LinearNode<Cell> findPath(Cell startLocation, Cell targetLocation) {
        tree = new LinkedList<LinearNode<Cell>>();
        //Start at the target location so we can navigate the LinearNodes from start to finish
        LinearNode<Cell> initialNode = new LinearNode<Cell>(targetLocation);
        tree.add(initialNode);
        
        LinearNode<Cell> path = searchNodes(startLocation);
        return path;
	}
	
	private void tryMove(LinearNode<Cell> node, int x, int y) {
		boolean yIsValid = y >= 0 && y < grid.length;
		if(yIsValid) {
			boolean xIsValid = x >= 0 && x < grid[y].length;
			if(xIsValid) {
	        	// Obstacle check can occur here if needed
	        	LinearNode<Cell> element = new LinearNode<Cell>(grid[y][x]);
	        	element.setNext(node);
	        	tree.add(element);
			}
		}
	}

    private LinearNode<Cell> searchNodes(Cell endLocation)
    {
        while (!tree.isEmpty())
        {
        	LinearNode<Cell> node = tree.remove();
        	if(node.getElement() == endLocation) {
        		// We've reached the target. Send this path back.
        		return node;
        	}
            int x = node.getElement().getColumn();
            int y = node.getElement().getRow();

            //Try to move left
            tryMove(node, x - 1, y);

            //Try to move up
            tryMove(node, x, y - 1);

            //Try to move right
            tryMove(node, x + 1, y);

            //Try to move down
            tryMove(node, x, y + 1);
        }
        return null;
    }
}
