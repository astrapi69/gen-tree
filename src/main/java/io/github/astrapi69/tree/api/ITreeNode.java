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
import java.util.Collection;
import java.util.List;

import lombok.NonNull;
import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.tree.handler.ITreeNodeHandlerExtensions;

/**
 * The Interface {@link ITreeNode} holds the children in a {@link Collection} object
 *
 * @param <T>
 *            the generic type of the value
 */
public interface ITreeNode<T> extends Serializable, Acceptable<Visitor<ITreeNode<T>>>
{

	/**
	 * Adds the given child
	 *
	 * @param child
	 *            the child
	 */
	default void addChild(final ITreeNode<T> child)
	{
		ITreeNodeHandlerExtensions.addChild(this, child);
	}

	/**
	 * Adds all the given children
	 *
	 * @param children
	 *            the children to add
	 */
	default void addChildren(final @NonNull Collection<ITreeNode<T>> children)
	{
		ITreeNodeHandlerExtensions.addChildren(this, children);
	}

	/**
	 * Returns all siblings of this node in the parent's children list. Returns null if this node is
	 * the root.
	 *
	 * @return Returns all siblings of this node or null if this node is the root.
	 */
	default Collection<ITreeNode<T>> getAllSiblings()
	{
		return ITreeNodeHandlerExtensions.getAllSiblings(this);
	}

	/**
	 * Returns the previous sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's first child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	default ITreeNode<T> getPreviousSibling()
	{
		return ITreeNodeHandlerExtensions.getPreviousSibling(this);
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
		return ITreeNodeHandlerExtensions.getNextSibling(this);
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
		return ITreeNodeHandlerExtensions.getLevel(this);
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
		return ITreeNodeHandlerExtensions.getRoot(this);
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
		return ITreeNodeHandlerExtensions.hasChildren(this);
	}

	/**
	 * Checks for parent
	 *
	 * @return true, if successful
	 */
	default boolean hasParent()
	{
		return ITreeNodeHandlerExtensions.hasParent(this);
	}

	/**
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	default boolean isLeaf()
	{
		return false;
	}

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
		return ITreeNodeHandlerExtensions.isRoot(this);
	}

	/**
	 * Removes the child.
	 *
	 * @param child
	 *            the child
	 */
	default void removeChild(final ITreeNode<T> child)
	{
		ITreeNodeHandlerExtensions.removeChild(this, child);
	}

	/**
	 * Removes all the children
	 */
	default void clearChildren()
	{
		ITreeNodeHandlerExtensions.clearChildren(this);
	}

	/**
	 * Removes all the descendants
	 */
	default void clearAll()
	{
		ITreeNodeHandlerExtensions.clearAll(this);
	}

	/**
	 * Removes all the children
	 */
	default void removeChildren()
	{
		ITreeNodeHandlerExtensions.removeChildren(this);
	}

	/**
	 * Removes all the given children
	 *
	 * @param children
	 *            the children to remove
	 */
	default void removeChildren(final @NonNull Collection<ITreeNode<T>> children)
	{
		ITreeNodeHandlerExtensions.removeChildren(this, children);
	}

	/**
	 * {@inheritDoc}
	 */
	default void accept(final @NonNull Visitor<ITreeNode<T>> visitor)
	{
		ITreeNodeHandlerExtensions.accept(this, visitor);
	}

	/**
	 * Find all {@link ITreeNode} objects that have the same value as the given value
	 *
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	default Collection<ITreeNode<T>> findAllByValue(final @NonNull T value)
	{
		return ITreeNodeHandlerExtensions.findAllByValue(this, value);
	}

	/**
	 * Find all {@link ITreeNode} objects that have the same value as the given value
	 *
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	default ITreeNode<T> findByValue(final @NonNull T value)
	{
		return ITreeNodeHandlerExtensions.findByValue(this, value);
	}

	/**
	 * Checks if the given {@link ITreeNode} object is a descendant of this tree node
	 *
	 * @param treeNode
	 *            the tree node to check
	 * @return true if the given {@link ITreeNode} object is a descendant of this tree node
	 *         otherwise false
	 */
	default boolean contains(ITreeNode<T> treeNode)
	{
		return ITreeNodeHandlerExtensions.contains(this, treeNode);
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
	default boolean containsAll(final @NonNull Collection<ITreeNode<T>> treeNodes)
	{
		return ITreeNodeHandlerExtensions.containsAll(this, treeNodes);
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link List} object
	 *
	 * @return a {@link List} object with this node and add all descendant
	 */
	default List<ITreeNode<T>> toList()
	{
		return ITreeNodeHandlerExtensions.toList(this);
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link Collection}
	 * object
	 *
	 * @return a {@link Collection} object with this node and add all descendant
	 */
	default Collection<ITreeNode<T>> traverse()
	{
		return ITreeNodeHandlerExtensions.traverse(this);
	}

}
