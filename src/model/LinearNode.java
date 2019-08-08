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
	
	public LinearNode(T element)
	{
		next = null;
		this.element = element;
	}
	
	public LinearNode<T> getNext()
	{
		return next;
	}
	
	public void setNext(LinearNode<T> node)
	{
		next = node;
	}
	
	public T getElement()
	{
		return element;
	}
	
	public void setElement(T element)
	{
		this.element = element;
	}
}
