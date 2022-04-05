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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import lombok.NonNull;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.tree.BaseTreeNode;
import io.github.astrapi69.tree.visitor.FindValuesBaseTreeNodeVisitor;

/**
 * The class {@link BaseTreeNodeHandlerExtensions} provides handler methods for the class
 * {@link BaseTreeNode}
 */
public class BaseTreeNodeHandlerExtensions
{

	/**
	 * Returns all siblings of the given {@link BaseTreeNode} object in the parent's children list
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return Returns all siblings of the given {@link BaseTreeNode} object
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
	 * Gets the root from the given {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return the root from the given {@link BaseTreeNode} object
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
	 * Returns the next sibling of the given {@link BaseTreeNode} object in the parent's children
	 * list. Returns null if the given {@link BaseTreeNode} object is the root or is the parent's
	 * last child
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of the given {@link BaseTreeNode} object or null if the given
	 *         {@link BaseTreeNode} object is the root or is the parent's last child
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
	 * Returns the previous sibling of the given {@link BaseTreeNode} object in the parent's
	 * children list. Returns null if the given {@link BaseTreeNode} object is the root or is the
	 * parent's first child.
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 *
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of the given {@link BaseTreeNode} object or null if the given
	 *         {@link BaseTreeNode} object is the root or is the parent's last child.
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
	 * Returns the distance from the root to the given {@link BaseTreeNode} object. Returns 0 if the
	 * given {@link BaseTreeNode} object is the root {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 *
	 * @param treeNode
	 *            the tree node
	 * @return the level from the given {@link BaseTreeNode} object
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

	/**
	 * Removes the given child from the given first {@link BaseTreeNode}
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child tree node
	 */
	public static <T, K> void removeChild(final @NonNull BaseTreeNode<T, K> parentTreeNode,
		final BaseTreeNode<T, K> child)
	{
		if (child != null)
		{
			if (isChildOf(parentTreeNode, child))
			{
				parentTreeNode.getChildren().remove(child);
				child.setParent(null);
				child.clearChildren();
			}
		}
	}

	/**
	 * Checks if the second given {@link BaseTreeNode} object is a child of the first
	 * {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @return true, if the second given {@link BaseTreeNode} object is a child of the first
	 *         {@link BaseTreeNode} object otherwise false
	 */
	public static <T, K> boolean isChildOf(final @NonNull BaseTreeNode<T, K> parentTreeNode,
		final @NonNull BaseTreeNode<T, K> child)
	{
		return parentTreeNode.getChildren().contains(child);
	}

	/**
	 * Checks if the first given {@link BaseTreeNode} object is the parent of the second
	 * {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @return true, if the first given {@link BaseTreeNode} object is the parent of the second
	 *         given {@link BaseTreeNode} object otherwise false
	 */
	public static <T, K> boolean isParentOf(final @NonNull BaseTreeNode<T, K> parentTreeNode,
		final @NonNull BaseTreeNode<T, K> child)
	{
		return child.getParent().equals(parentTreeNode);
	}

	/**
	 * Removes all the given children from the first given {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param children
	 *            the children to remove
	 */
	public static <T, K> void removeChildren(final @NonNull BaseTreeNode<T, K> parentTreeNode,
		final @NonNull Collection<BaseTreeNode<T, K>> children)
	{
		children.forEach(child -> BaseTreeNodeHandlerExtensions.removeChild(parentTreeNode, child));
	}

	/**
	 * Removes all children from the given {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 */
	public static <T, K> void removeChildren(final @NonNull BaseTreeNode<T, K> parentTreeNode)
	{
		BaseTreeNodeHandlerExtensions.removeChildren(parentTreeNode,
			new ArrayList<>(parentTreeNode.getChildren()));
	}

	/**
	 * Checks if the given {@link BaseTreeNode} is the root {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return true, if the given {@link BaseTreeNode} is the root {@link BaseTreeNode} object
	 */
	public static <T, K> boolean isRoot(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		return !BaseTreeNodeHandlerExtensions.hasParent(baseTreeNode);
	}

	/**
	 * Checks if the given {@link BaseTreeNode} object has a parent {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return true, if the given {@link BaseTreeNode} object has a parent {@link BaseTreeNode}
	 *         object otherwise false
	 */
	public static <T, K> boolean hasParent(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		return baseTreeNode.getParent() != null;
	}

	/**
	 * Checks if the given {@link BaseTreeNode} object is a node
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return true, if the given {@link BaseTreeNode} object is a node otherwise false
	 */
	public static <T, K> boolean isNode(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		return !baseTreeNode.isLeaf();
	}

	/**
	 * Removes all the descendants from the given {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 */
	public static <T, K> void clearAll(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		baseTreeNode.accept(treeNode -> treeNode.clearChildren());
	}

	/**
	 * Removes all the children from the given {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 */
	public static <T, K> void clearChildren(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		BaseTreeNodeHandlerExtensions.removeChildren(baseTreeNode,
			new ArrayList<>(baseTreeNode.getChildren()));
	}

	/**
	 * Adds the given child {@link BaseTreeNode} object to the first given parent
	 * {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 */
	public static <T, K> void addChild(final @NonNull BaseTreeNode<T, K> parentTreeNode,
		final BaseTreeNode<T, K> child)
	{
		if (child != null)
		{
			child.setParent(parentTreeNode);
			parentTreeNode.getChildren().add(child);
		}
	}

	/**
	 * Adds all the given children from the first given parent {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param children
	 *            the children to add
	 */
	public static <T, K> void addChildren(final @NonNull BaseTreeNode<T, K> parentTreeNode,
		final @NonNull Collection<BaseTreeNode<T, K>> children)
	{
		children.forEach(child -> BaseTreeNodeHandlerExtensions.addChild(parentTreeNode, child));
	}

	/**
	 * Gets the child count from the given {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return the child count
	 */
	public static <T, K> int getChildCount(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		return baseTreeNode.getChildren().size();
	}

	/**
	 * Checks if the given {@link BaseTreeNode} object has children
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return true, if the given {@link BaseTreeNode} object has children otherwise false
	 */
	public static <T, K> boolean hasChildren(final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		return baseTreeNode.getChildren() != null && !baseTreeNode.getChildren().isEmpty();
	}

	/**
	 * Traverse the given {@link BaseTreeNode} object and add all descendants with the given
	 * {@link BaseTreeNode} object included in to the returned {@link Collection} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 *
	 * @return a {@link Collection} object with the given {@link BaseTreeNode} object and add all
	 *         descendants
	 */
	public static <T, K> Collection<BaseTreeNode<T, K>> traverse(
		final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		final Collection<BaseTreeNode<T, K>> allTreeNodes = new LinkedHashSet<>();
		baseTreeNode.accept(allTreeNodes::add);
		return allTreeNodes;
	}

	/**
	 * Accepts the given visitor that provides a custom algorithm for processing all elements
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @param visitor
	 *            the visitor
	 */
	public static <T, K> void accept(final @NonNull BaseTreeNode<T, K> baseTreeNode,
		final @NonNull Visitor<BaseTreeNode<T, K>> visitor)
	{
		BaseTreeNodeHandlerExtensions.accept(baseTreeNode, visitor, false);
	}

	/**
	 * Accepts the given visitor that provides a custom algorithm for processing all elements
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @param visitor
	 *            the visitor
	 * @param visitBefore
	 *            the flag if this flag is true the visit of the given {@link BaseTreeNode} object
	 *            is before visit the children otherwise the visit is after visit the children
	 */
	public static <T, K> void accept(final @NonNull BaseTreeNode<T, K> baseTreeNode,
		final @NonNull Visitor<BaseTreeNode<T, K>> visitor, final boolean visitBefore)
	{
		boolean visitAfter = !visitBefore;
		if (visitBefore)
		{
			visitor.visit(baseTreeNode);
		}
		baseTreeNode.getChildren()
			.forEach(child -> BaseTreeNodeHandlerExtensions.accept(child, visitor, visitBefore));
		if (visitAfter)
		{
			visitor.visit(baseTreeNode);
		}
	}

	/**
	 * Find all {@link BaseTreeNode} objects from the first given {@link BaseTreeNode} object that
	 * serves as the search target, that have the same value as the given value
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	public static <T, K> Collection<BaseTreeNode<T, K>> findAllByValue(
		final @NonNull BaseTreeNode<T, K> baseTreeNode, final T value)
	{
		FindValuesBaseTreeNodeVisitor<T, K> visitor = new FindValuesBaseTreeNodeVisitor<>(value);
		baseTreeNode.accept(visitor);
		return visitor.getFoundTreeNodes().get();
	}

	/**
	 * Find the first occurrence of {@link BaseTreeNode} object from the first given
	 * {@link BaseTreeNode} object that serves as the search target, that have the same value as the
	 * given value
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @param value
	 *            the value for the search process
	 * @return the first occurrence of {@link BaseTreeNode} object that have the same value as the
	 *         given value
	 */
	public static <T, K> BaseTreeNode<T, K> findByValue(
		final @NonNull BaseTreeNode<T, K> baseTreeNode, final T value)
	{
		final AtomicReference<BaseTreeNode<T, K>> found = new AtomicReference<>();
		BaseTreeNodeHandlerExtensions.findAllByValue(baseTreeNode, value).stream().findFirst()
			.ifPresent(treeNode -> {
				found.set(treeNode);
			});
		return found.get();
	}

	/**
	 * Checks if the second given {@link BaseTreeNode} object is a descendant of the first given
	 * {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @param descendantCandidate
	 *            the tree node to check
	 * @return true if the given {@link BaseTreeNode} object is a descendant of the first given
	 *         {@link BaseTreeNode} object otherwise false
	 */
	public static <T, K> boolean contains(final @NonNull BaseTreeNode<T, K> baseTreeNode,
		final BaseTreeNode<T, K> descendantCandidate)
	{
		if (descendantCandidate == null)
		{
			return false;
		}
		return BaseTreeNodeHandlerExtensions.traverse(baseTreeNode).contains(descendantCandidate);
	}

	/**
	 * Checks if the given {@link Collection} object of {@link BaseTreeNode} objects are descendants
	 * of the first given {@link BaseTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @param baseTreeNodes
	 *            the collection of the tree nodes to check
	 * @return true if the given {@link Collection} object of {@link BaseTreeNode} objects are
	 *         descendants of the first given {@link BaseTreeNode} object otherwise false
	 */
	public static <T, K> boolean containsAll(final @NonNull BaseTreeNode<T, K> baseTreeNode,
		final @NonNull Collection<BaseTreeNode<T, K>> baseTreeNodes)
	{
		return BaseTreeNodeHandlerExtensions.traverse(baseTreeNode).containsAll(baseTreeNodes);
	}

	/**
	 * Traverse the given {@link BaseTreeNode} object and adds all descendant with it self-included
	 * in to a {@link List} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 *
	 * @return a {@link List} object with the given {@link BaseTreeNode} object and all descendants
	 */
	public static <T, K> List<BaseTreeNode<T, K>> toList(
		final @NonNull BaseTreeNode<T, K> baseTreeNode)
	{
		return new ArrayList<>(BaseTreeNodeHandlerExtensions.traverse(baseTreeNode));
	}

}
