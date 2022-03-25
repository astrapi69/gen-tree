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
package io.github.astrapi69.tree.binary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.List;

/**
 * The class {@link LinkedNode} is a representation of a binary tree node and can have only one next
 * element and one previous element and a current value.
 *
 * @param <T>
 *            the generic type of the value
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = { "next" })
@ToString(exclude = { "next" })
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkedNode<T>
{

	/** The next. */
	LinkedNode<T> next;

	/** The previous. */
	LinkedNode<T> previous;

	/** The value. */
	T value;

	/**
	 * Instantiates a new {@link LinkedNode}
	 *
	 * @param value
	 *            the value
	 */
	public LinkedNode(T value)
	{
		this.value = value;
	}


	/**
	 * Gets the first {@link LinkedNode} object
	 *
	 * @return the first {@link LinkedNode} object
	 */
	public LinkedNode<T> getFirst()
	{
		LinkedNode<T> first = this;
		LinkedNode<T> previous = getPrevious();
		while (previous != null && !previous.isFirst())
		{
			previous = previous.getPrevious();
			first = previous;
		}
		return first;
	}

	/**
	 * Checks for previous object
	 *
	 * @return true, if successful
	 */
	public boolean hasPrevious()
	{
		return getPrevious() != null;
	}

	/**
	 * Checks if this is the first object
	 *
	 * @return true, if is first
	 */
	public boolean isFirst()
	{
		return !hasPrevious();
	}

	/**
	 * Gets the next count.
	 *
	 * @return the next count
	 */
	public int getNextCount()
	{
		return getNextLinkedNodes().size();
	}

	/**
	 * Gets all next {@link LinkedNode} from the current linked node
	 *
	 * @return all next {@link LinkedNode} from the current linked node
	 */
	public List<LinkedNode<T>> getNextLinkedNodes()
	{
		List<LinkedNode<T>> list = new LinkedList<>();
		LinkedNode<T> next = this;
		while (next.hasNext())
		{
			next = next.getNext();
			list.add(next);
		}
		return list;
	}

	/**
	 * Checks if this tree node has a next object
	 *
	 * @return true, if successful
	 */
	public boolean hasNext()
	{
		return getNext() != null;
	}

	/**
	 * Transforms this linked node object to an ordered {@link LinkedList} with all nodes
	 *
	 * @return the ordered {@link LinkedList} with all nodes
	 */
	public List<LinkedNode<T>> toList()
	{
		List<LinkedNode<T>> list = new LinkedList<>();
		LinkedNode<T> first = getFirst();
		list.add(first);
		LinkedNode<T> next = first;
		while (next.hasNext())
		{
			next = next.getNext();
			list.add(next);
		}
		return list;
	}
}
