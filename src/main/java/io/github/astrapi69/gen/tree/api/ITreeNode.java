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
package io.github.astrapi69.gen.tree.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.handler.ITreeNodeHandlerExtensions;
import io.github.astrapi69.gen.tree.handler.TreeNodeVisitorHandlerExtensions;
import io.github.astrapi69.tree.api.ITree;
import lombok.NonNull;

/**
 * The Interface {@link ITreeNode} holds the children in a {@link Collection} object
 *
 * @param <V>
 *            the generic type of the value
 * @param <T>
 *            the generic type of the concrete tree node
 */
public interface ITreeNode<V, T extends ITreeNode<V, T>> extends ITree<V, T>, Acceptable<Visitor<T>>
{

	/**
	 * Gets the reference from this {@link ITreeNode} object
	 * 
	 * @return this {@link ITreeNode} object
	 */
	@SuppressWarnings("unchecked")
	private T getThis()
	{
		T self = (T)this;
		return self;
	}

	/**
	 * {@inheritDoc}
	 */
	default void addChild(final T child)
	{
		ITreeNodeHandlerExtensions.addChild(getThis(), child);
	}

	/**
	 * {@inheritDoc}
	 */
	default void addChild(final T child, int index)
	{
		ITreeNodeHandlerExtensions.addChild(getThis(), child, index);
	}

	/**
	 * {@inheritDoc}
	 */
	default Optional<T> getChildAt(int index)
	{
		return ITreeNodeHandlerExtensions.getChildAt(getThis(), index);
	}

	/**
	 * {@inheritDoc}
	 */
	default int getChildIndex(T child)
	{
		return ITreeNodeHandlerExtensions.getChildIndex(getThis(), child);
	}

	/**
	 * {@inheritDoc}
	 */
	default void addChildren(final @NonNull Collection<T> children)
	{
		ITreeNodeHandlerExtensions.addChildren(getThis(), children);
	}

	/**
	 * {@inheritDoc}
	 */
	default Collection<T> getAllSiblings()
	{
		return ITreeNodeHandlerExtensions.getAllSiblings(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default T getPreviousSibling()
	{
		return ITreeNodeHandlerExtensions.getPreviousSibling(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default T getNextSibling()
	{
		return ITreeNodeHandlerExtensions.getNextSibling(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default int getChildCount()
	{
		return getChildren().size();
	}

	/**
	 * {@inheritDoc}
	 */
	default int getLevel()
	{
		return ITreeNodeHandlerExtensions.getLevel(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default T getRoot()
	{
		return ITreeNodeHandlerExtensions.getRoot(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasChildren()
	{
		return ITreeNodeHandlerExtensions.hasChildren(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasNextSibling()
	{
		return ITreeNodeHandlerExtensions.hasNextSibling(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasParent()
	{
		return ITreeNodeHandlerExtensions.hasParent(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasPreviousSibling()
	{
		return ITreeNodeHandlerExtensions.hasPreviousSibling(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean isNode()
	{
		return !isLeaf();
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean isRoot()
	{
		return ITreeNodeHandlerExtensions.isRoot(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default void removeChild(final T child)
	{
		ITreeNodeHandlerExtensions.removeChild(getThis(), child);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean isChild(final T child)
	{
		return ITreeNodeHandlerExtensions.isChild(getThis(), child);
	}

	/**
	 * {@inheritDoc}
	 */
	default void clearChildren()
	{
		ITreeNodeHandlerExtensions.clearChildren(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default void clearAll()
	{
		ITreeNodeHandlerExtensions.clearAll(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default void removeChildren()
	{
		ITreeNodeHandlerExtensions.removeChildren(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default void removeChildren(final @NonNull Collection<T> children)
	{
		ITreeNodeHandlerExtensions.removeChildren(getThis(), children);
	}

	/**
	 * {@inheritDoc}
	 */
	default void accept(final @NonNull Visitor<T> visitor)
	{
		TreeNodeVisitorHandlerExtensions.accept(getThis(), visitor);
	}

	/**
	 * {@inheritDoc}
	 */
	default Collection<T> findAllByValue(final V value)
	{
		return ITreeNodeHandlerExtensions.findAllByValue(getThis(), value);
	}

	/**
	 * {@inheritDoc}
	 */
	default T findByValue(final @NonNull V value)
	{
		return ITreeNodeHandlerExtensions.findByValue(getThis(), value);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean contains(T treeNode)
	{
		return ITreeNodeHandlerExtensions.contains(getThis(), treeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean containsAll(final @NonNull Collection<T> treeNodes)
	{
		return ITreeNodeHandlerExtensions.containsAll(getThis(), treeNodes);
	}

	/**
	 * {@inheritDoc}
	 */
	default List<T> toList()
	{
		return ITreeNodeHandlerExtensions.toList(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default Collection<T> traverse()
	{
		return TreeNodeVisitorHandlerExtensions.traverse(getThis());
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean isAncestor(T treeNode)
	{
		return ITreeNodeHandlerExtensions.isAncestor(getThis(), treeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean isDescendant(T treeNode)
	{
		return ITreeNodeHandlerExtensions.isDescendant(getThis(), treeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean move(T treeNode)
	{
		return ITreeNodeHandlerExtensions.move(getThis(), treeNode);
	}
}
