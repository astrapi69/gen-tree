/**
 * The MIT License
 * <p>
 * Copyright (C) 2015 Asterios Raptis
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.gen.tree.handler;

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import lombok.NonNull;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * The class {@link BaseTreeNodeVisitorHandlerExtensions} provides handler methods for the visit all
 * field in the class {@link IBaseTreeNode}
 */
public class BaseTreeNodeVisitorHandlerExtensions
{

	/**
	 * Traverse the given {@link IBaseTreeNode} object and add all descendants with the given
	 * {@link IBaseTreeNode} object included in to the returned {@link Collection} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 *
	 * @return a {@link Collection} object with the given {@link IBaseTreeNode} object and add all
	 *         descendants
	 */
	public static <V, K, T extends IBaseTreeNode<V, K, T>> Collection<T> traverse(
		final @NonNull T treeNode)
	{
		final Collection<T> allTreeNodes = new LinkedHashSet<>();
		treeNode.accept(allTreeNodes::add);
		return allTreeNodes;
	}

	/**
	 * Accepts the given visitor that provides a custom algorithm for processing all elements
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param visitor
	 *            the visitor
	 */
	public static <V, K, T extends IBaseTreeNode<V, K, T>> void accept(final @NonNull T treeNode,
		final @NonNull Visitor<T> visitor)
	{
		BaseTreeNodeVisitorHandlerExtensions.accept(treeNode, visitor, false);
	}

	/**
	 * Accepts the given visitor that provides a custom algorithm for processing all elements
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param visitor
	 *            the visitor
	 * @param visitBefore
	 *            the flag if this flag is true the visit of the given {@link IBaseTreeNode} object
	 *            is before visit the children otherwise the visit is after visit the children
	 */
	public static <V, K, T extends IBaseTreeNode<V, K, T>> void accept(final @NonNull T treeNode,
		final @NonNull Visitor<T> visitor, final boolean visitBefore)
	{
		boolean visitAfter = !visitBefore;
		if (visitBefore)
		{
			visitor.visit(treeNode);
		}
		treeNode.getChildren().forEach(
			child -> BaseTreeNodeVisitorHandlerExtensions.accept(child, visitor, visitBefore));
		if (visitAfter)
		{
			visitor.visit(treeNode);
		}
	}

}
