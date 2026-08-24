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
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.enumeration.traversal.TraversalType;
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

	/**
	 * Returns the greatest distance from this {@link ITreeNode} object down to any of its
	 * descendants. Returns 0 if this {@link ITreeNode} object is a leaf. The counterpart of
	 * {@link #getLevel()}, which measures the distance up to the root
	 *
	 * @return the height of the subtree of this {@link ITreeNode} object
	 */
	default int height()
	{
		return ITreeNodeHandlerExtensions.height(getThis());
	}

	/**
	 * Finds the deepest {@link ITreeNode} object that is an ancestor of, or equal to, both this
	 * {@link ITreeNode} object and the given {@link ITreeNode} object
	 *
	 * @param treeNode
	 *            the other tree node
	 * @return the lowest common ancestor of this {@link ITreeNode} object and the given
	 *         {@link ITreeNode} object or null if they do not share a common ancestor, for instance
	 *         because they belong to different trees
	 */
	default T lowestCommonAncestor(final T treeNode)
	{
		return ITreeNodeHandlerExtensions.lowestCommonAncestor(getThis(), treeNode);
	}

	/**
	 * Keeps only the descendants of this {@link ITreeNode} object (itself included) that satisfy
	 * the given predicate, preserving the hierarchy of the survivors: when a node is dropped, its
	 * surviving descendants are promoted to take its place under the nearest surviving ancestor.
	 * Mutates this {@link ITreeNode} object and its descendants in place by detaching the nodes
	 * that do not satisfy the predicate and reattaching their surviving descendants
	 *
	 * @param predicate
	 *            the predicate that a surviving node has to satisfy
	 * @return a {@link List} object with this {@link ITreeNode} object if it satisfies the
	 *         predicate, or with its promoted surviving descendants otherwise. An empty
	 *         {@link List} object means that no descendant survived the filtering
	 */
	default List<T> filterTree(final Predicate<T> predicate)
	{
		return ITreeNodeHandlerExtensions.filterTree(getThis(), predicate);
	}

	/**
	 * Creates a deep, independent copy of the subtree of this {@link ITreeNode} object. Since a
	 * generic type parameter cannot be instantiated directly, the caller supplies a shallow copy
	 * function that copies a single node's own fields (for instance id and value, but not its
	 * parent or children); {@code cloneSubtree} wires up the copied children itself
	 *
	 * @param nodeCopier
	 *            the function that creates a shallow copy of a single {@link ITreeNode} object,
	 *            without its parent or children
	 * @return a new {@link ITreeNode} object that is a deep, detached copy of this
	 *         {@link ITreeNode} object
	 */
	default T cloneSubtree(final UnaryOperator<T> nodeCopier)
	{
		return ITreeNodeHandlerExtensions.cloneSubtree(getThis(), nodeCopier);
	}

	/**
	 * Folds every value in the subtree of this {@link ITreeNode} object into a single accumulator,
	 * in the given traversal order. The tree-shaped counterpart of {@code Stream.reduce}
	 *
	 * @param <A>
	 *            the generic type of the accumulator
	 * @param seed
	 *            the initial accumulator value
	 * @param accumulator
	 *            the function that combines the current accumulator with a visited node
	 * @param traversalType
	 *            the traversal order, either {@link TraversalType#PREORDER} or
	 *            {@link TraversalType#POSTORDER}. {@link TraversalType#INORDER} is not supported
	 *            since it is only meaningful for binary trees
	 * @return the final accumulator value after every node in the subtree has been visited
	 */
	default <A> A reduceTree(final A seed, final BiFunction<A, T, A> accumulator,
		final TraversalType traversalType)
	{
		return ITreeNodeHandlerExtensions.reduceTree(getThis(), seed, accumulator, traversalType);
	}
}
