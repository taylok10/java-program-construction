/**
 * 
 */
package model;

import java.util.AbstractQueue;

/**
 * @author kelly.taylor
 *
 */
public interface IPathFinder {
    /**
     * Finds a path
     * @param startLocation Where should the path begin
     * @param targetLocation Where do we need to end
     * @return the found path, null if none found
     */
	AbstractQueue<Cell> findPath(Cell startLocation, Cell targetLocation);
}