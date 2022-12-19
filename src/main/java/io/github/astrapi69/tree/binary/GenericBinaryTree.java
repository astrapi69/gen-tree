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

import java.util.Objects;

/**
 * The abstract class {@link GenericBinaryTree} represents a generic binary tree. A binary tree is a
 * recursive data structure where a binary tree node can have only 2 children. For an example see
 * the unit tests
 *
 * @param <T>
 *            the generic type of the values
 */
public abstract class GenericBinaryTree<T>
{

	/**
	 * the first {@link LinkedNode} object is also the root object
	 */
	protected LinkedNode<T> first;

	/**
	 * Checks if the given value is smaller than the value of the given {@link LinkedNode} object
	 *
	 * @param linkedNode
	 *            the {@link LinkedNode} object
	 * @param value
	 *            the value
	 * @return true if the given value is smaller than the value of the given {@link LinkedNode}
	 *         object otherwise false
	 */
	public abstract boolean isSmaller(LinkedNode<T> linkedNode, T value);

	/**
	 * Checks if the given value is greater than the value of the given {@link LinkedNode} object
	 *
	 * @param linkedNode
	 *            the {@link LinkedNode} object
	 * @param value
	 *            the value
	 * @return true if the given value is greater than the value of the given {@link LinkedNode}
	 *         object otherwise false
	 */
	public abstract boolean isGreater(LinkedNode<T> linkedNode, T value);

	/**
	 * Adds the given value to the binary tree at the right position
	 *
	 * @param linkedNode
	 *            the {@link LinkedNode} object
	 * @param value
	 *            the value
	 * @return the added {@link LinkedNode} object
	 */
	protected LinkedNode<T> addRecursively(LinkedNode<T> linkedNode, T value)
	{
		if (linkedNode == null)
		{
			return LinkedNode.<T> builder().value(value).build();
		}

		if (isSmaller(linkedNode, value))
		{
			LinkedNode<T> next = (LinkedNode<T>)linkedNode.getNext();
			LinkedNode<T> nextLinkedNode = addRecursively(next, value);
			linkedNode.setNext(nextLinkedNode);
		}
		else if (isGreater(linkedNode, value))
		{
			LinkedNode<T> previous = (LinkedNode<T>)linkedNode.getPrevious();
			LinkedNode<T> previousLinkedNode = addRecursively(previous, value);
			linkedNode.setPrevious(previousLinkedNode);
		}
		return linkedNode;
	}

	/**
	 * Adds the given value to the binary tree at the right position
	 *
	 * @param value
	 *            the value
	 * @return this binary tree object so you can chain
	 */
	public GenericBinaryTree<T> add(T value)
	{
		first = addRecursively(first, value);
		return this;
	}

	/**
	 * Checks recursively if the given value exists in this binary tree object
	 *
	 * @param linkedNode
	 *            the {@link LinkedNode} object
	 * @param value
	 *            the value
	 * @return true if the given value exists in this binary tree object otherwise false
	 */
	protected boolean containsRecursively(LinkedNode<T> linkedNode, T value)
	{
		if (linkedNode == null)
		{
			return false;
		}
		if (Objects.equals(value, linkedNode.getValue()))
		{
			return true;
		}
		return isSmaller(linkedNode, value)
			? containsRecursively((LinkedNode<T>)linkedNode.getNext(), value)
			: containsRecursively((LinkedNode<T>)linkedNode.getPrevious(), value);
	}

	/**
	 * Checks if the given value exists in this binary tree object
	 *
	 * @param value
	 *            the value
	 * @return true if the given value exists in this binary tree object otherwise false
	 */
	public boolean contains(T value)
	{
		return containsRecursively(first, value);
	}

}
