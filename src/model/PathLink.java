/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class PathLink<T> {
	private LinearNode<T> nextStep;
	private LinearNode<T> lastStep;

	/**
	 * Creates a new PathLink
	 * 
	 * @param step The first step of the path
	 */
	public PathLink(T step) {
		nextStep = new LinearNode<T>(step);
		lastStep = nextStep;
	}

	/**
	 * Creates a new PathLink generated from a LinearNode<T>
	 * 
	 * @param node LinearNode to generate the path from
	 */
	public PathLink(LinearNode<T> node) {
		this(node.getElement());
		LinearNode<T> currentNode = node;
		while (currentNode.getNext() != null) {
			currentNode = currentNode.getNext();
			addStep(currentNode.getElement());
		}
	}

	/**
	 * Adds a step to the path
	 * 
	 * @param step Step to add to the end of the path
	 */
	public void addStep(T step) {
		LinearNode<T> node = new LinearNode<T>(step);
		lastStep.setNext(node);
		lastStep = node;
	}

	/**
	 * Returns the next step and removes it from the path
	 * 
	 * @return The next step
	 */
	public T takeStep() {
		LinearNode<T> step = nextStep;
		if (step != null) {
			nextStep = nextStep.getNext();
			return step.getElement();
		}
		return null;
	}

	/**
	 * Gets number of steps in path
	 * 
	 * @return The number of steps in the path
	 */
	public int size() {
		int count = 0;
		LinearNode<T> current = nextStep;
		while (current != null) {
			count++;
			current = current.getNext();
		}
		return count;
	}
}