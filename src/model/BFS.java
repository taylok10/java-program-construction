/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author kelly.taylor
 *
 */
public class BFS<T extends GridLocation> implements PathFinder<T> {
	private T[][] grid;
	private Queue<LinearNode<T>> tree;
	private ArrayList<T> visitedLocations;
	private boolean ignoreObstacles;

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
	public PathLink<T> findPath(T startLocation, T targetLocation, boolean ignoreObstacles) {
		this.ignoreObstacles = ignoreObstacles;
		tree = new LinkedList<LinearNode<T>>();
		visitedLocations = new ArrayList<T>();
		LinearNode<T> initialNode = new LinearNode<T>(startLocation);
		tree.add(initialNode);

		PathLink<T> path = searchNodes(targetLocation);
		if (path != null) {
			// Reverse so the path gets returned in the correct order
			path.reverse();
			// Remove starting position from path
			path.takeStep();
		}
		return path;
	}

	/**
	 * Confirms if the location has previously been visited
	 * 
	 * @param location What is the location to check
	 * @return if we have previously visited this location
	 */
	private boolean hasLocationBeenVisited(T location) {
		return visitedLocations.contains(location);
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
				T location = grid[y][x]; // Location is valid
				if (!hasLocationBeenVisited(location) && (ignoreObstacles || !location.isBlocked())) {
					// Location has not been reached quicker and is available - move here
					LinearNode<T> element = new LinearNode<T>(location);
					element.setNext(node);
					tree.add(element);
					visitedLocations.add(location);
					return true;
				}
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
	private PathLink<T> searchNodes(T endLocation) {
		while (!tree.isEmpty()) {
			LinearNode<T> node = tree.remove();
			if (node.getElement().equals(endLocation)) {
				// We've reached the target. This is the quickest path.
				return new PathLink<T>(node);
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
