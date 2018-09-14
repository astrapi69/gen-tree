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
package de.alpharogroup.tree.ifaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Interface ITreeNode.
 *
 * @param <T>
 *            the generic type
 */
public interface ITreeNode<T> extends Serializable
{

	/**
	 * Adds the child.
	 *
	 * @param child
	 *            the child
	 */
	default void addChild(final ITreeNode<T> child)
	{
		child.setParent(this);
		getChildren().add(child);
	}

	/**
	 * Adds the child.
	 *
	 * @param index
	 *            the index
	 * @param child
	 *            the child
	 * @throws IndexOutOfBoundsException
	 *             the index out of bounds exception
	 */
	default void addChildAt(final int index, final ITreeNode<T> child)
		throws IndexOutOfBoundsException
	{
		if (index < getChildren().size())
		{
			getChildren().add(index, child);
		}
		else
		{
			addChild(child);
		}
	}

	/**
	 * Returns all siblings of this node in the parent's children list. Returns null if this node is
	 * the root.
	 *
	 * @return Returns all siblings of this node or null if this node is the root.
	 */
	default List<ITreeNode<T>> getAllSiblings()
	{
		final ITreeNode<T> parent = getParent();
		if (parent == null)
		{
			return null;
		}
		final List<ITreeNode<T>> siblings = new ArrayList<>(parent.getChildren());
		siblings.remove(this);
		return siblings;
	}

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
	 * Gets the children.
	 *
	 * @return the children
	 */
	List<ITreeNode<T>> getChildren();

	/**
	 * Returns the depth of the tree beginning at this node Returns 0 if this node has no children.
	 *
	 * @return the depth of the tree beginning at this node.
	 */
	default int getDepth()
	{
		if (isLeaf() || getChildCount() == 0)
		{
			return 0;
		}
		int maxDepth = 1;
		int currentDepth = 1;
		for (ITreeNode<T> data : getChildren())
		{
			while (data.hasChildren())
			{
				currentDepth++;
				data = data.getChildren().get(0);
			}
			if (maxDepth < currentDepth)
			{
				maxDepth = currentDepth;
			}
		}
		return maxDepth;
	}

	/**
	 * Gets the optional display value.
	 *
	 * @return the display value
	 */
	String getDisplayValue();

	/**
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @return the level from this node.
	 */
	default int getLevel()
	{
		ITreeNode<T> current = this;
		int count = 0;
		while ((current = current.getParent()) != null)
		{
			count++;
		}
		return count;
	}

	/**
	 * Returns the next sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's last child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	default ITreeNode<T> getNextSibling()
	{
		if (getParent() == null)
		{
			return null;
		}
		final int index = getParent().getChildren().indexOf(this) + 1;
		if (index == getParent().getChildCount())
		{
			return null;
		}
		return getParent().getChildren().get(index);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	ITreeNode<T> getParent();

	/**
	 * Returns the previous sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's first child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	default ITreeNode<T> getPreviousSibling()
	{
		if (getParent() == null)
		{
			return null;
		}
		final int index = getParent().getChildren().indexOf(this) - 1;
		if (index < 0)
		{
			return null;
		}
		return getParent().getChildren().get(index);
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Checks for children.
	 *
	 * @return true, if successful
	 */
	default boolean hasChildren()
	{
		return getChildren() != null && !getChildren().isEmpty();
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
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	default boolean isLeaf()
	{
		return !isNode();
	}

	/**
	 * Checks if is node.
	 *
	 * @return true, if is node
	 */
	default boolean isNode()
	{
		return true;
	}

	/**
	 * Checks if this {@link ITreeNode} is the root {@link ITreeNode} object
	 *
	 * @return true, if this {@link ITreeNode} is the root {@link ITreeNode} object
	 */
	default boolean isRoot()
	{
		return !hasParent();
	};

	/**
	 * Gets the root {@link ITreeNode} object
	 *
	 * @return the root {@link ITreeNode} object
	 */
	default ITreeNode<T> getRoot()
	{
		ITreeNode<T> root = this;
		ITreeNode<T> parent = getParent();
		while (parent != null && !parent.isRoot())
		{
			parent = parent.getParent();
			root = parent;
		}
		return root;
	}

	/**
	 * Removes the child.
	 *
	 * @param child
	 *            the child
	 */
	default void removeChild(final ITreeNode<T> child)
	{
		getChildren().remove(child);
		child.setParent(null);
	}

	/**
	 * Removes the child.
	 *
	 * @param index
	 *            the index
	 * @throws IndexOutOfBoundsException
	 *             the index out of bounds exception
	 */
	default void removeChildAt(final int index) throws IndexOutOfBoundsException
	{
		final ITreeNode<T> child = getChildren().remove(index);
		if (child != null)
		{
			child.setParent(null);
		}
	}

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 */
	void setChildren(final List<ITreeNode<T>> children);

	/**
	 * Sets the optional display value.
	 *
	 * @param displayValue
	 *            the new optional display value
	 */
	void setDisplayValue(final String displayValue);

	/**
	 * Sets the parent.
	 *
	 * @param parent
	 *            the new parent
	 */
	void setParent(final ITreeNode<T> parent);

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(final T value);

	/**
	 * To list.
	 *
	 * @return the list
	 */
	default List<ITreeNode<T>> toList()
	{
		final List<ITreeNode<T>> list = new ArrayList<>();
		traverse(this, list);
		return list;
	}

	/**
	 * Traverse.
	 *
	 * @param node
	 *            the node
	 * @param list
	 *            the list
	 */
	default void traverse(final ITreeNode<T> node, final List<ITreeNode<T>> list)
	{
		list.add(node);
		for (final ITreeNode<T> data : node.getChildren())
		{
			traverse(data, list);
		}
	}

}