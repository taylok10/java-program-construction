/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class LinearNode<T> {
	private T element;
	private LinearNode<T> next;

	/**
	 * Creates a new LinearNode
	 * 
	 * @param element The element of the LinearNode
	 */
	public LinearNode(T element) {
		next = null;
		this.element = element;
	}

	/**
	 * Gets the node 'following' this node
	 * 
	 * @return The node 'following' this node
	 */
	public LinearNode<T> getNext() {
		return next;
	}

	/**
	 * Sets the node 'following' this node
	 * 
	 * @param node The node 'following' this node
	 */
	public void setNext(LinearNode<T> node) {
		next = node;
	}

	/**
	 * Gets the element of the LinearNode
	 * 
	 * @return The element of the LinearNode
	 */
	public T getElement() {
		return element;
	}

	/**
	 * Sets the element of the LinearNode
	 * 
	 * @param element The element to set against the LinearNode
	 */
	public void setElement(T element) {
		this.element = element;
	}
}
