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

import lombok.NonNull;
import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.handler.ITreeNodeHandlerExtensions;
import io.github.astrapi69.gen.tree.handler.TreeNodeVisitorHandlerExtensions;
import io.github.astrapi69.tree.api.ITree;

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
	 * {@inheritDoc}
	 */
	default void addChild(final T child)
	{
		ITreeNodeHandlerExtensions.addChild((T)this, child);
	}

	/**
	 * {@inheritDoc}
	 */
	default void addChildren(final @NonNull Collection<T> children)
	{
		ITreeNodeHandlerExtensions.addChildren((T)this, children);
	}

	/**
	 * {@inheritDoc}
	 */
	default Collection<T> getAllSiblings()
	{
		return ITreeNodeHandlerExtensions.getAllSiblings((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default T getPreviousSibling()
	{
		return ITreeNodeHandlerExtensions.getPreviousSibling((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default T getNextSibling()
	{
		return ITreeNodeHandlerExtensions.getNextSibling((T)this);
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
		return ITreeNodeHandlerExtensions.getLevel((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default T getRoot()
	{
		return ITreeNodeHandlerExtensions.getRoot((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasChildren()
	{
		return ITreeNodeHandlerExtensions.hasChildren((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasNextSibling()
	{
		return ITreeNodeHandlerExtensions.hasNextSibling((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasParent()
	{
		return ITreeNodeHandlerExtensions.hasParent((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean hasPreviousSibling()
	{
		return ITreeNodeHandlerExtensions.hasPreviousSibling((T)this);
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
		return ITreeNodeHandlerExtensions.isRoot((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default void removeChild(final T child)
	{
		ITreeNodeHandlerExtensions.removeChild((T)this, child);
	}

	/**
	 * {@inheritDoc}
	 */
	default void clearChildren()
	{
		ITreeNodeHandlerExtensions.clearChildren((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default void clearAll()
	{
		ITreeNodeHandlerExtensions.clearAll((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default void removeChildren()
	{
		ITreeNodeHandlerExtensions.removeChildren((T)this);
	}

	/**
	 * {@inheritDoc}
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
		TreeNodeVisitorHandlerExtensions.accept((T)this, visitor);
	}

	/**
	 * {@inheritDoc}
	 */
	default Collection<T> findAllByValue(final V value)
	{
		return ITreeNodeHandlerExtensions.findAllByValue((T)this, value);
	}

	/**
	 * {@inheritDoc}
	 */
	default T findByValue(final @NonNull V value)
	{
		return ITreeNodeHandlerExtensions.findByValue((T)this, value);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean contains(T treeNode)
	{
		return ITreeNodeHandlerExtensions.contains((T)this, treeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	default boolean containsAll(final @NonNull Collection<T> treeNodes)
	{
		return ITreeNodeHandlerExtensions.containsAll((T)this, treeNodes);
	}

	/**
	 * {@inheritDoc}
	 */
	default List<T> toList()
	{
		return ITreeNodeHandlerExtensions.toList((T)this);
	}

	/**
	 * {@inheritDoc}
	 */
	default Collection<T> traverse()
	{
		return TreeNodeVisitorHandlerExtensions.traverse((T)this);
	}

}
