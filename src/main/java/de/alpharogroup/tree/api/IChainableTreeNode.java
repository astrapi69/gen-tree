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
 * The interface {@link IChainableTreeNode} represents a chainable tree node
 *
 * @param <T>
 *            the generic type of the value
 */
public interface IChainableTreeNode<T>
{

	/**
	 * Gets all parent {@link IChainableTreeNode} from the current tree node
	 *
	 * @return all parent {@link IChainableTreeNode} from the current tree node
	 */
	default List<IChainableTreeNode<T>> getAllParent()
	{
		List<IChainableTreeNode<T>> list = new LinkedList<>();
		IChainableTreeNode<T> parent = this;
		while (parent.hasParent())
		{
			parent = parent.getParent();
			list.add(parent);
		}
		return list;
	}

	/**
	 * Gets the child.
	 *
	 * @return the child
	 */
	IChainableTreeNode<T> getChild();

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	default int getChildCount()
	{
		return getChildren().size();
	}

	/**
	 * Gets the children from the current tree node
	 *
	 * @return the children from the current tree node
	 */
	default List<IChainableTreeNode<T>> getChildren()
	{
		List<IChainableTreeNode<T>> list = new LinkedList<>();
		IChainableTreeNode<T> child = this;
		while (child.hasChild())
		{
			child = child.getChild();
			list.add(child);
		}
		return list;
	}

	/**
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @return the level from this node.
	 */
	default int getLevel()
	{
		return getAllParent().size();
	}

	/**
	 * Gets the parent
	 *
	 * @return the parent
	 */
	IChainableTreeNode<T> getParent();

	/**
	 * Gets the root {@link IChainableTreeNode} object
	 *
	 * @return the root {@link IChainableTreeNode} object
	 */
	default IChainableTreeNode<T> getRoot()
	{
		IChainableTreeNode<T> root = this;
		IChainableTreeNode<T> parent = getParent();
		while (parent != null && !parent.isRoot())
		{
			parent = parent.getParent();
			root = parent;
		}
		return root;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Checks if this tree node has a child
	 *
	 * @return true, if successful
	 */
	default boolean hasChild()
	{
		return getChild() != null;
	}

	/**
	 * Checks for parent
	 *
	 * @return true, if successful
	 */
	default boolean hasParent()
	{
		return getParent() != null;
	}

	/**
	 * Checks if this treenode is root.
	 *
	 * @return true, if is root
	 */
	default boolean isRoot()
	{
		return !hasParent();
	}

	/**
	 * Sets the child.
	 *
	 * @param child
	 *            the new child
	 */
	void setChild(IChainableTreeNode<T> child);

	/**
	 * Sets the parent.
	 *
	 * @param parent
	 *            the new parent
	 */
	void setParent(final IChainableTreeNode<T> parent);

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(T value);

	/**
	 * Transforms this tree node object to an ordered {@link LinkedList} with all tree nodes
	 *
	 * @return the ordered {@link LinkedList} with all tree nodes
	 */
	default List<IChainableTreeNode<T>> toList()
	{
		List<IChainableTreeNode<T>> list = new LinkedList<>();
		IChainableTreeNode<T> root = getRoot();
		list.add(root);
		IChainableTreeNode<T> child = root;
		while (child.hasChild())
		{
			child = child.getChild();
			list.add(child);
		}
		return list;
	}

}
