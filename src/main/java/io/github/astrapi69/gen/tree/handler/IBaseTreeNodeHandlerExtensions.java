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
package io.github.astrapi69.gen.tree.handler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import io.github.astrapi69.gen.tree.visitor.MergeTreeNodesVisitor;
import lombok.NonNull;

/**
 * The class {@link IBaseTreeNodeHandlerExtensions} provides handler methods for the class
 * {@link IBaseTreeNode}
 */
public class IBaseTreeNodeHandlerExtensions
{

	/**
	 * Find the occurrence of {@link IBaseTreeNode} object from the given key object that serves as
	 * the search target
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param id
	 *            the id for the search process
	 * @return the first occurrence of {@link IBaseTreeNode} object that have the same value as the
	 *         given value
	 */
	public static <V, K, T extends IBaseTreeNode<V, K, T>> T findById(final @NonNull T treeNode,
		final K id)
	{
		final AtomicReference<Boolean> stopSearch = new AtomicReference<>(Boolean.FALSE);
		final AtomicReference<T> found = new AtomicReference<>();
		treeNode.accept(currentTreeNode -> {
			if (!stopSearch.get())
			{
				if (currentTreeNode.getId().equals(id))
				{
					stopSearch.set(Boolean.TRUE);
					found.set(currentTreeNode);
				}
			}
		});
		return found.get();
	}

	/**
	 * Merge the given tree nodes with the given root tree node
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param root
	 *            the root tree node
	 * @param treeNodes
	 *            the tree nodes to merge with
	 * @return the root with the merged tree nodes
	 */
	public static <V, K, T extends IBaseTreeNode<V, K, T>> T mergeTreeNodes(final @NonNull T root,
		final @NonNull List<T> treeNodes)
	{
		MergeTreeNodesVisitor<V, K, T> visitor = new MergeTreeNodesVisitor<>(root);
		for (T treeNode : treeNodes)
		{
			treeNode.accept(visitor);
		}
		return root;
	}

	/**
	 * Merge the given tree nodes with the given root tree node
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param root
	 *            the root tree node
	 * @param treeNodes
	 *            the tree nodes to merge with
	 * @return the root with the merged tree nodes
	 */
	public static <V, K, T extends IBaseTreeNode<V, K, T>> T mergeTreeNodes(final @NonNull T root,
		T... treeNodes)
	{
		return mergeTreeNodes(root, Arrays.asList(treeNodes));
	}

}
