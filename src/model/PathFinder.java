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
     * @param startLocation Where should the path begin
     * @param targetLocation Where do we need to end
     * @return the found path, null if none found
     */
	LinearNode<T> findPath(T startLocation, T targetLocation);
}