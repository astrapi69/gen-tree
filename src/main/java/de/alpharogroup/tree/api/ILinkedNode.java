/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.tree.api;

import java.util.LinkedList;
import java.util.List;

/**
 * The interface {@link ILinkedNode} represents a chainable object with a generic value
 *
 * @param <T>
 *            the generic type of the value
 */
public interface ILinkedNode<T>
{

	/**
	 * Gets the first {@link ILinkedNode} object
	 *
	 * @return the first {@link ILinkedNode} object
	 */
	default ILinkedNode<T> getFirst()
	{
		ILinkedNode<T> first = this;
		ILinkedNode<T> previous = getPrevious();
		while (previous != null && !previous.isFirst())
		{
			previous = previous.getPrevious();
			first = previous;
		}
		return first;
	}

	/**
	 * Gets the next object
	 *
	 * @return the next object
	 */
	ILinkedNode<T> getNext();

	/**
	 * Gets the next count.
	 *
	 * @return the next count
	 */
	default int getNextCount()
	{
		return getNextLinkedNodes().size();
	}

	/**
	 * Gets all next {@link ILinkedNode} from the current linked node
	 *
	 * @return all next {@link ILinkedNode} from the current linked node
	 */
	default List<ILinkedNode<T>> getNextLinkedNodes()
	{
		List<ILinkedNode<T>> list = new LinkedList<>();
		ILinkedNode<T> next = this;
		while (next.hasNext())
		{
			next = next.getNext();
			list.add(next);
		}
		return list;
	}

	/**
	 * Gets the previous object
	 *
	 * @return the previous object
	 */
	ILinkedNode<T> getPrevious();

	/**
	 * Gets the next count.
	 *
	 * @return the next count
	 */
	default int getPreviousCount()
	{
		return getPreviousLinkedNodes().size();
	}

	/**
	 * Gets all previous {@link ILinkedNode} from the current linked node
	 *
	 * @return all previous {@link ILinkedNode} from the current linked node
	 */
	default List<ILinkedNode<T>> getPreviousLinkedNodes()
	{
		List<ILinkedNode<T>> list = new LinkedList<>();
		ILinkedNode<T> previous = this;
		while (previous.hasPrevious())
		{
			previous = previous.getPrevious();
			list.add(previous);
		}
		return list;
	}

	/**
	 * Gets the value
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Checks if this tree node has a next object
	 *
	 * @return true, if successful
	 */
	default boolean hasNext()
	{
		return getNext() != null;
	}

	/**
	 * Checks for previous object
	 *
	 * @return true, if successful
	 */
	default boolean hasPrevious()
	{
		return getPrevious() != null;
	}

	/**
	 * Checks if this is the first object
	 *
	 * @return true, if is first
	 */
	default boolean isFirst()
	{
		return !hasPrevious();
	}

	/**
	 * Sets the next object
	 *
	 * @param next
	 *            the new next object
	 */
	void setNext(ILinkedNode<T> next);

	/**
	 * Sets the previous object
	 *
	 * @param previous
	 *            the new previous object
	 */
	void setPrevious(final ILinkedNode<T> previous);

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(T value);

	/**
	 * Transforms this linked node object to an ordered {@link LinkedList} with all nodes
	 *
	 * @return the ordered {@link LinkedList} with all nodes
	 */
	default List<ILinkedNode<T>> toList()
	{
		List<ILinkedNode<T>> list = new LinkedList<>();
		ILinkedNode<T> first = getFirst();
		list.add(first);
		ILinkedNode<T> next = first;
		while (next.hasNext())
		{
			next = next.getNext();
			list.add(next);
		}
		return list;
	}

}
