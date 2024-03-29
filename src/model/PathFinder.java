/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public interface PathFinder<T extends GridLocation> {
	/**
	 * Finds a path
	 * 
	 * @param startLocation   Where should the path begin
	 * @param targetLocation  Where do we need to end
	 * @param ignoreObstacles Find a path that doesn't care about obstacles
	 * @return the found path, null if none found
	 */
	PathLink<T> findPath(T startLocation, T targetLocation, boolean ignoreObstacles);
}