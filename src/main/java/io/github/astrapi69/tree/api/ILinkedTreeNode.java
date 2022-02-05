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
package io.github.astrapi69.tree.api;

import java.util.LinkedList;
import java.util.List;

/**
 * The interface {@link ILinkedTreeNode} represents a chainable object with a generic value
 *
 * @param <T>
 *            the generic type of the value
 */
public interface ILinkedTreeNode<T> extends ITreeNode<T>
{

	/**
	 * Gets the first {@link ILinkedTreeNode} object
	 *
	 * @return the first {@link ILinkedTreeNode} object
	 */
	default ILinkedTreeNode<T> getFirst()
	{
		ILinkedTreeNode<T> first = this;
		ILinkedTreeNode<T> previous = getPrevious();
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
	ILinkedTreeNode<T> getNext();

	/**
	 * Sets the next object
	 *
	 * @param next
	 *            the new next object
	 */
	void setNext(ILinkedTreeNode<T> next);

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
	 * Gets all next {@link ILinkedTreeNode} from the current linked node
	 *
	 * @return all next {@link ILinkedTreeNode} from the current linked node
	 */
	default List<ILinkedTreeNode<T>> getNextLinkedNodes()
	{
		List<ILinkedTreeNode<T>> list = new LinkedList<>();
		ILinkedTreeNode<T> next = this;
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
	ILinkedTreeNode<T> getPrevious();

	/**
	 * Sets the previous object
	 *
	 * @param previous
	 *            the new previous object
	 */
	void setPrevious(final ILinkedTreeNode<T> previous);

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
	 * Gets all previous {@link ILinkedTreeNode} from the current linked node
	 *
	 * @return all previous {@link ILinkedTreeNode} from the current linked node
	 */
	default List<ILinkedTreeNode<T>> getPreviousLinkedNodes()
	{
		List<ILinkedTreeNode<T>> list = new LinkedList<>();
		ILinkedTreeNode<T> previous = this;
		while (previous.hasPrevious())
		{
			previous = previous.getPrevious();
			list.add(previous);
		}
		return list;
	}

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
	 * Transforms this linked node object to an ordered {@link LinkedList} with all nodes
	 *
	 * @return the ordered {@link LinkedList} with all nodes
	 */
	default List<ILinkedTreeNode<T>> toLinkedList()
	{
		List<ILinkedTreeNode<T>> list = new LinkedList<>();
		ILinkedTreeNode<T> first = getFirst();
		list.add(first);
		ILinkedTreeNode<T> next = first;
		while (next.hasNext())
		{
			next = next.getNext();
			list.add(next);
		}
		return list;
	}

}
