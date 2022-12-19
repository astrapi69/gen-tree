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

import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.tree.handler.ITreeNodeHandlerExtensions;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The Interface {@link ITreeNode} holds the children in a {@link Collection} object
 *
 * @param <V>
 *            the generic type of the value
 * @param <T>
 *            the generic type of the concrete tree node
 */
public interface ITreeNode<V, T extends ITreeNode<V, T>>
	extends
		Serializable,
		Acceptable<Visitor<T>>
{

	/**
	 * Adds the given child
	 *
	 * @param child
	 *            the child
	 */
	default void addChild(final T child)
	{
		ITreeNodeHandlerExtensions.addChild((T)this, child);
	}

	/**
	 * Adds all the given children
	 *
	 * @param children
	 *            the children to add
	 */
	default void addChildren(final @NonNull Collection<T> children)
	{
		ITreeNodeHandlerExtensions.addChildren((T)this, children);
	}

	/**
	 * Returns all siblings of this node in the parent's children list. Returns null if this node is
	 * the root.
	 *
	 * @return Returns all siblings of this node or null if this node is the root.
	 */
	default Collection<T> getAllSiblings()
	{
		return ITreeNodeHandlerExtensions.getAllSiblings((T)this);
	}

	/**
	 * Returns the previous sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's first child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	default T getPreviousSibling()
	{
		return ITreeNodeHandlerExtensions.getPreviousSibling((T)this);
	}

	/**
	 * Returns the next sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's last child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	default T getNextSibling()
	{
		return ITreeNodeHandlerExtensions.getNextSibling((T)this);
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
	Collection<T> getChildren();

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 */
	void setChildren(final Collection<T> children);

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
		return ITreeNodeHandlerExtensions.getLevel((T)this);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	T getParent();

	/**
	 * Sets the parent.
	 *
	 * @param parent
	 *            the new parent
	 */
	void setParent(final T parent);

	/**
	 * Gets the root object
	 *
	 * @return the root object
	 */
	default T getRoot()
	{
		return ITreeNodeHandlerExtensions.getRoot((T)this);
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	V getValue();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(final V value);

	/**
	 * Checks for children.
	 *
	 * @return true, if successful
	 */
	default boolean hasChildren()
	{
		return ITreeNodeHandlerExtensions.hasChildren((T)this);
	}

	/**
	 * Checks for parent
	 *
	 * @return true, if successful
	 */
	default boolean hasParent()
	{
		return ITreeNodeHandlerExtensions.hasParent((T)this);
	}

	/**
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	boolean isLeaf();

	/**
	 * Sets the flag that indicates if this tree node is a node or a leaf
	 *
	 * @param leaf
	 *            The flag to set that indicates if this tree node is a node or a leaf
	 */
	void setLeaf(boolean leaf);

	/**
	 * Checks if is node.
	 *
	 * @return true, if is node
	 */
	default boolean isNode()
	{
		return !isLeaf();
	}

	/**
	 * Checks if this {@link ITreeNode} is the root {@link ITreeNode} object
	 *
	 * @return true, if this {@link ITreeNode} is the root {@link ITreeNode} object
	 */
	default boolean isRoot()
	{
		return ITreeNodeHandlerExtensions.isRoot((T)this);
	}

	/**
	 * Removes the child.
	 *
	 * @param child
	 *            the child
	 */
	default void removeChild(final T child)
	{
		ITreeNodeHandlerExtensions.removeChild((T)this, child);
	}

	/**
	 * Removes all the children
	 */
	default void clearChildren()
	{
		ITreeNodeHandlerExtensions.clearChildren((T)this);
	}

	/**
	 * Removes all the descendants
	 */
	default void clearAll()
	{
		ITreeNodeHandlerExtensions.clearAll((T)this);
	}

	/**
	 * Removes all the children
	 */
	default void removeChildren()
	{
		ITreeNodeHandlerExtensions.removeChildren((T)this);
	}

	/**
	 * Removes all the given children
	 *
	 * @param children
	 *            the children to remove
	 */
	default void removeChildren(final @NonNull Collection<T> children)
	{
		ITreeNodeHandlerExtensions.removeChildren((T)this, children);
	}

	/**
	 * {@inheritDoc}
	 */
	default void accept(final @NonNull Visitor<T> visitor)
	{
		ITreeNodeHandlerExtensions.accept((T)this, visitor);
	}

	/**
	 * Find all {@link ITreeNode} objects that have the same value as the given value
	 *
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	default Collection<T> findAllByValue(final V value)
	{
		return ITreeNodeHandlerExtensions.findAllByValue((T)this, value);
	}

	/**
	 * Find all {@link ITreeNode} objects that have the same value as the given value
	 *
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	default T findByValue(final @NonNull V value)
	{
		return ITreeNodeHandlerExtensions.findByValue((T)this, value);
	}

	/**
	 * Checks if the given {@link ITreeNode} object is a descendant of this tree node
	 *
	 * @param treeNode
	 *            the tree node to check
	 * @return true if the given {@link ITreeNode} object is a descendant of this tree node
	 *         otherwise false
	 */
	default boolean contains(T treeNode)
	{
		return ITreeNodeHandlerExtensions.contains((T)this, treeNode);
	}

	/**
	 * Checks if the given {@link Collection} object of {@link ITreeNode} objects are descendants of
	 * this tree node
	 *
	 * @param treeNodes
	 *            the children to add
	 * @return true if the given {@link Collection} object of {@link ITreeNode} objects are
	 *         descendants of this tree node otherwise false
	 */
	default boolean containsAll(final @NonNull Collection<T> treeNodes)
	{
		return ITreeNodeHandlerExtensions.containsAll((T)this, treeNodes);
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link List} object
	 *
	 * @return a {@link List} object with this node and add all descendant
	 */
	default List<T> toList()
	{
		return ITreeNodeHandlerExtensions.toList((T)this);
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link Collection}
	 * object
	 *
	 * @return a {@link Collection} object with this node and add all descendant
	 */
	default Collection<T> traverse()
	{
		return ITreeNodeHandlerExtensions.traverse((T)this);
	}

}
