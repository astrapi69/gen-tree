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
package io.github.astrapi69.tree.handler;

import java.util.Collection;
import java.util.LinkedHashSet;

import lombok.NonNull;
import io.github.astrapi69.tree.BaseTreeNode;

/**
 * The class {@link BaseTreeNodeHandlerExtensions} provides handler methods for the class
 * {@link BaseTreeNode}
 */
public class BaseTreeNodeHandlerExtensions
{

	/**
	 * Returns all siblings of the given node in the parent's children list.
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return Returns all siblings of this node
	 */
	public static <T, K> Collection<BaseTreeNode<T, K>> getAllSiblings(
		final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		final BaseTreeNode<T, K> parent = baseTreeNode.getParent();
		if (parent == null)
		{
			return new LinkedHashSet<>();
		}
		final Collection<BaseTreeNode<T, K>> allSiblings = new LinkedHashSet<>(
			parent.getChildren());
		allSiblings.remove(baseTreeNode);
		return allSiblings;
	}

	/**
	 * Gets the root {@link BaseTreeNode} object
	 *
	 * @param baseTreeNode
	 *            the tree node
	 * @return the root {@link BaseTreeNode} object
	 */
	public static <T, K> BaseTreeNode<T, K> getRoot(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		BaseTreeNode<T, K> root = baseTreeNode;
		if (root.isRoot())
		{
			return root;
		}
		do
		{
			root = root.getParent();
		}
		while (root != null && !root.isRoot());
		return root;
	}

	/**
	 * Returns the next sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's last child.
	 *
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	public static <T, K> BaseTreeNode<T, K> getNextSibling(
		final @NonNull BaseTreeNode<T, K> currentTreeNode)
	{
		BaseTreeNode<T, K> next = null;
		if (currentTreeNode.getParent() == null)
		{
			return next;
		}
		boolean isNext = false;
		for (BaseTreeNode<T, K> baseTreeNode : currentTreeNode.getParent().getChildren())
		{
			if (isNext)
			{
				next = baseTreeNode;
				break;
			}
			if (baseTreeNode.equals(currentTreeNode))
			{
				isNext = true;
			}
		}
		return next;
	}

	/**
	 * Returns the previous sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's first child.
	 *
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	public static <T, K> BaseTreeNode<T, K> getPreviousSibling(
		final @NonNull BaseTreeNode<T, K> currentTreeNode)
	{
		BaseTreeNode<T, K> previous = null;
		if (currentTreeNode.getParent() == null)
		{
			return previous;
		}
		for (BaseTreeNode<T, K> baseTreeNode : currentTreeNode.getParent().getChildren())
		{
			if (baseTreeNode.equals(currentTreeNode))
			{
				break;
			}
			previous = baseTreeNode;
		}
		return previous;
	}

	/**
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @param treeNode
	 *            the tree node
	 * @return the level from this node.
	 */
	public static <T, K> int getLevel(final @NonNull BaseTreeNode<T, K> treeNode)
	{
		BaseTreeNode<T, K> currentTreeNode = treeNode;
		int count = 0;
		while ((currentTreeNode = currentTreeNode.getParent()) != null)
		{
			count++;
		}
		return count;
	}
}
