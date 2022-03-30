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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;

/**
 * The Interface ITreeNode.
 *
 * @param <T>
 *            the generic type of the value
 */
public interface ITreeNode<T> extends Serializable, Acceptable<Visitor<ITreeNode<T>>>
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
	Collection<ITreeNode<T>> getChildren();

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 */
	void setChildren(final Collection<ITreeNode<T>> children);

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
				data = data.getChildren().stream().findFirst().get();
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
	 * Sets the optional display value.
	 *
	 * @param displayValue
	 *            the new optional display value
	 */
	void setDisplayValue(final String displayValue);

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
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	ITreeNode<T> getParent();

	/**
	 * Sets the parent.
	 *
	 * @param parent
	 *            the new parent
	 */
	void setParent(final ITreeNode<T> parent);

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
	 * Gets the value.
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(final T value);

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
	 * Checks if is node. Overwrite this method if this node is a leaf
	 *
	 * @return true, if is node
	 */
	default boolean isNode()
	{
		return true;
	}

	/**
	 * Sets the flag that indicates if this tree node is a node or a leaf
	 *
	 * @param node
	 *            The flag to set that indicates if this tree node is a node or a leaf
	 */
	void setNode(boolean node);

	/**
	 * Checks if this {@link ITreeNode} is the root {@link ITreeNode} object
	 *
	 * @return true, if this {@link ITreeNode} is the root {@link ITreeNode} object
	 */
	default boolean isRoot()
	{
		return !hasParent();
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
	 * {@inheritDoc}
	 */
	default void accept(Visitor<ITreeNode<T>> visitor)
	{
		visitor.visit(this);
		getChildren().forEach(child -> child.accept(visitor));
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link List} object
	 *
	 * @return a {@link List} object with this node and add all descendant
	 */
	default List<ITreeNode<T>> toList()
	{
		return new ArrayList<>(traverse());
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link Collection}
	 * object
	 *
	 * @return a {@link Collection} object with this node and add all descendant
	 */
	Collection<ITreeNode<T>> traverse();

}
