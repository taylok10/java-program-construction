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
	
	public PathLink(T step) {
		nextStep = new LinearNode<T>(step);
		lastStep = nextStep;
	}
	
	public PathLink(LinearNode<T> node) {
		this(node.getElement());
		LinearNode<T> currentNode = node;
		while(currentNode.getNext() != null) {
			currentNode = currentNode.getNext();
			addStep(currentNode.getElement());
		}
	}
	
	public void addStep(T step) {
		LinearNode<T> node = new LinearNode<T>(step);
		lastStep.setNext(node);
		lastStep = node;
	}
	
	public T takeStep() {
		LinearNode<T> step = nextStep;
		if(step != null) {
			nextStep = nextStep.getNext();
			return step.getElement();
		}
		return null;
	}
	
	public int size() {
		int count = 0;
		
		LinearNode<T> current = nextStep;
		while(current != null)
		{
			count++;
			current = current.getNext();
		}
		
		return count;
	}
}