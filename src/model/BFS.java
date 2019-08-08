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
public class BFS<T extends GridLocation> implements PathFinder<T> {
	private T[][] grid;
	private Queue<LinearNode<T>> tree;

	/**
	 * Will create a new BFS object
	 * 
	 * @param grid The grid to use for path finding
	 */
	public BFS(T[][] grid) {
		this.grid = grid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.PathFinder#findPath(model.Cell, model.Cell)
	 */
	@Override
	public LinearNode<T> findPath(T startLocation, T targetLocation) {
		tree = new LinkedList<LinearNode<T>>();
		// Start at the target location so we can navigate the LinearNodes from start to
		// finish
		LinearNode<T> initialNode = new LinearNode<T>(targetLocation);
		tree.add(initialNode);
		LinearNode<T> path = searchNodes(startLocation);
		return path;
	}

	/**
	 * Will attempt to move to the given x and y position in the grid
	 * 
	 * @param node The current position
	 * @param x    The x coordinate to move to
	 * @param y    The y coordinate to move to
	 * @return If the move was successful
	 */
	private boolean tryMove(LinearNode<T> node, int x, int y) {
		boolean yIsValid = y >= 0 && y < grid.length;
		if (yIsValid) {
			boolean xIsValid = x >= 0 && x < grid[y].length;
			if (xIsValid) {
				// Obstacle check can occur here if needed
				LinearNode<T> element = new LinearNode<T>(grid[y][x]);
				element.setNext(node);
				tree.add(element);
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds a path Searches the grid for a path to the provided location
	 * 
	 * @param endLocation Where do we need to end
	 * @return the found path, null if none found
	 */
	private LinearNode<T> searchNodes(T endLocation) {
		while (!tree.isEmpty()) {
			LinearNode<T> node = tree.remove();
			if (node.getElement() == endLocation) {
				// We've reached the target. Send this path back.
				return node;
			}
			int x = node.getElement().getColumn();
			int y = node.getElement().getRow();

			// Try to move left
			tryMove(node, x - 1, y);

			// Try to move up
			tryMove(node, x, y - 1);

			// Try to move right
			tryMove(node, x + 1, y);

			// Try to move down
			tryMove(node, x, y + 1);
		}
		return null;
	}
}
