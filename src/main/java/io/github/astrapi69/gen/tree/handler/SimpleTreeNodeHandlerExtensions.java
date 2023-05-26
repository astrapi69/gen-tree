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

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.SimpleTreeNode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The class {@link SimpleTreeNodeHandlerExtensions} provides handler methods for the class
 * {@link SimpleTreeNode}
 */
public class SimpleTreeNodeHandlerExtensions
{

	/**
	 * Gets the children from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 *
	 * @return the children from the given {@link SimpleTreeNode} object
	 */
	public static <T, K> Collection<SimpleTreeNode<T, K>> getChildren(
		final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		Collection<SimpleTreeNode<T, K>> children = new LinkedHashSet<>();
		if (!treeNode.hasLeftMostChild())
		{
			return children;
		}
		SimpleTreeNode<T, K> leftMostChild = treeNode.getLeftMostChild();
		children.add(leftMostChild);
		if (leftMostChild.hasRightSibling())
		{
			SimpleTreeNode<T, K> currentRightSibling = leftMostChild.getRightSibling();
			children.add(currentRightSibling);
			do
			{
				currentRightSibling = currentRightSibling.getRightSibling();
				children.add(currentRightSibling);
			}
			while (currentRightSibling.hasRightSibling());
		}
		return children;
	}

	/**
	 * Returns all siblings of the given {@link SimpleTreeNode} object in the parent's children list
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return Returns all siblings of the given {@link SimpleTreeNode} object
	 */
	public static <T, K> Collection<SimpleTreeNode<T, K>> getAllSiblings(
		final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		Collection<SimpleTreeNode<T, K>> allSiblings = new LinkedHashSet<>();
		if (treeNode.hasParent())
		{
			allSiblings = getChildren(treeNode.getParent());
		}
		return allSiblings;
	}

	/**
	 * Gets the root from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return the root from the given {@link SimpleTreeNode} object
	 */
	public static <T, K> SimpleTreeNode<T, K> getRoot(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		SimpleTreeNode<T, K> root = treeNode;
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
	 * Returns the next sibling of the given {@link SimpleTreeNode} object in the parent's children
	 * list. Returns null if the given {@link SimpleTreeNode} object is the root or is the parent's
	 * last child
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of the given {@link SimpleTreeNode} object or null if the given
	 *         {@link SimpleTreeNode} object is the root or is the parent's last child
	 */
	public static <T, K> SimpleTreeNode<T, K> getNextSibling(
		final @NonNull SimpleTreeNode<T, K> currentTreeNode)
	{
		return currentTreeNode.getRightSibling();
	}

	/**
	 * Returns the previous sibling of the given {@link SimpleTreeNode} object in the parent's
	 * children list. Returns null if the given {@link SimpleTreeNode} object is the root or is the
	 * parent's first child.
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 *
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of the given {@link SimpleTreeNode} object or null if the given
	 *         {@link SimpleTreeNode} object is the root or is the parent's last child.
	 */
	public static <T, K> SimpleTreeNode<T, K> getPreviousSibling(
		final @NonNull SimpleTreeNode<T, K> currentTreeNode)
	{
		SimpleTreeNode<T, K> previous = null;
		if (currentTreeNode.getParent() == null)
		{
			return previous;
		}
		for (SimpleTreeNode<T, K> treeNode : currentTreeNode.getParent().getChildren())
		{
			if (treeNode.equals(currentTreeNode))
			{
				break;
			}
			previous = treeNode;
		}
		return previous;
	}

	/**
	 * Returns the distance from the root to the given {@link SimpleTreeNode} object. Returns 0 if
	 * the given {@link SimpleTreeNode} object is the root {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 *
	 * @param treeNode
	 *            the tree node
	 * @return the level from the given {@link SimpleTreeNode} object
	 */
	public static <T, K> int getLevel(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		SimpleTreeNode<T, K> currentTreeNode = treeNode;
		int count = 0;
		while ((currentTreeNode = currentTreeNode.getParent()) != null)
		{
			count++;
		}
		return count;
	}

	/**
	 * Removes the given child from the given first {@link SimpleTreeNode}
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
	public static <T, K> void removeChild(final @NonNull SimpleTreeNode<T, K> parentTreeNode,
		final SimpleTreeNode<T, K> child)
	{
		if (child != null)
		{
			if (isChildOf(parentTreeNode, child))
			{
				parentTreeNode.getChildren().remove(child);
				child.setParent(null);
				child.setLeftMostChild(null);
			}
		}
	}

	/**
	 * Checks if the second given {@link SimpleTreeNode} object is a child of the first
	 * {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @return true, if the second given {@link SimpleTreeNode} object is a child of the first
	 *         {@link SimpleTreeNode} object otherwise false
	 */
	public static <T, K> boolean isChildOf(final @NonNull SimpleTreeNode<T, K> parentTreeNode,
		final @NonNull SimpleTreeNode<T, K> child)
	{
		return parentTreeNode.getChildren().contains(child);
	}

	/**
	 * Checks if the first given {@link SimpleTreeNode} object is the parent of the second
	 * {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @return true, if the first given {@link SimpleTreeNode} object is the parent of the second
	 *         given {@link SimpleTreeNode} object otherwise false
	 */
	public static <T, K> boolean isParentOf(final @NonNull SimpleTreeNode<T, K> parentTreeNode,
		final @NonNull SimpleTreeNode<T, K> child)
	{
		return child.getParent().equals(parentTreeNode);
	}

	/**
	 * Removes all the given children from the first given {@link SimpleTreeNode} object
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
	public static <T, K> void removeChildren(final @NonNull SimpleTreeNode<T, K> parentTreeNode,
		final @NonNull Collection<SimpleTreeNode<T, K>> children)
	{
		children
			.forEach(child -> SimpleTreeNodeHandlerExtensions.removeChild(parentTreeNode, child));
	}

	/**
	 * Removes all children from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param parentTreeNode
	 *            the parent tree node
	 */
	public static <T, K> void removeChildren(final @NonNull SimpleTreeNode<T, K> parentTreeNode)
	{
		SimpleTreeNodeHandlerExtensions.removeChildren(parentTreeNode,
			new ArrayList<>(parentTreeNode.getChildren()));
	}

	/**
	 * Checks if the given {@link SimpleTreeNode} is the root {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link SimpleTreeNode} is the root {@link SimpleTreeNode} object
	 */
	public static <T, K> boolean isRoot(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		return !treeNode.hasParent();
	}

	/**
	 * Checks if the given {@link SimpleTreeNode} object has a parent {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link SimpleTreeNode} object has a parent {@link SimpleTreeNode}
	 *         object otherwise false
	 */
	public static <T, K> boolean hasParent(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		return treeNode.getParent() != null;
	}

	/**
	 * Checks if the given {@link SimpleTreeNode} object is a node
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link SimpleTreeNode} object is a node otherwise false
	 */
	public static <T, K> boolean isNode(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		return !treeNode.isLeaf();
	}

	/**
	 * Removes all the descendants from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 */
	public static <T, K> void clearAll(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		treeNode.accept(currentTreeNode -> currentTreeNode.setLeftMostChild(null));
	}

	/**
	 * Removes all the children from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 */
	public static <T, K> void clearChildren(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		SimpleTreeNodeHandlerExtensions.removeChildren(treeNode,
			new ArrayList<>(treeNode.getChildren()));
	}

	/**
	 * Adds the given child {@link SimpleTreeNode} object to the first given parent
	 * {@link SimpleTreeNode} object
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
	public static <T, K> void addChild(final @NonNull SimpleTreeNode<T, K> parentTreeNode,
		final SimpleTreeNode<T, K> child)
	{
		if (child != null && parentTreeNode.isNode())
		{
			child.setParent(parentTreeNode);
			parentTreeNode.getChildren().add(child);
		}
	}

	/**
	 * Adds all the given children from the first given parent {@link SimpleTreeNode} object
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
	public static <T, K> void addChildren(final @NonNull SimpleTreeNode<T, K> parentTreeNode,
		final @NonNull Collection<SimpleTreeNode<T, K>> children)
	{
		if (parentTreeNode.isNode())
		{
			children
				.forEach(child -> SimpleTreeNodeHandlerExtensions.addChild(parentTreeNode, child));
		}
	}

	/**
	 * Gets the child count from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return the child count
	 */
	public static <T, K> int getChildCount(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		return treeNode.getChildren().size();
	}

	/**
	 * Checks if the given {@link SimpleTreeNode} object has children
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link SimpleTreeNode} object has children otherwise false
	 */
	public static <T, K> boolean hasChildren(final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		return treeNode.getChildren() != null && !treeNode.getChildren().isEmpty();
	}

	/**
	 * Traverse the given {@link SimpleTreeNode} object and add all descendants with the given
	 * {@link SimpleTreeNode} object included in to the returned {@link Collection} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 *
	 * @return a {@link Collection} object with the given {@link SimpleTreeNode} object and add all
	 *         descendants
	 */
	public static <T, K> Collection<SimpleTreeNode<T, K>> traverse(
		final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		final Collection<SimpleTreeNode<T, K>> allTreeNodes = new LinkedHashSet<>();
		treeNode.accept(allTreeNodes::add);
		return allTreeNodes;
	}

	/**
	 * Accepts the given visitor that provides a custom algorithm for processing all elements
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @param visitor
	 *            the visitor
	 */
	public static <T, K> void accept(final @NonNull SimpleTreeNode<T, K> treeNode,
		final @NonNull Visitor<SimpleTreeNode<T, K>> visitor)
	{
		SimpleTreeNodeHandlerExtensions.accept(treeNode, visitor, true);
	}

	/**
	 * Accepts the given visitor that provides a custom algorithm for processing all elements
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @param visitor
	 *            the visitor
	 * @param visitBefore
	 *            the flag if this flag is true the visit of the given {@link SimpleTreeNode} object
	 *            is before visit the children otherwise the visit is after visit the children
	 */
	public static <T, K> void accept(final @NonNull SimpleTreeNode<T, K> treeNode,
		final @NonNull Visitor<SimpleTreeNode<T, K>> visitor, final boolean visitBefore)
	{
		boolean visitAfter = !visitBefore;
		if (visitBefore)
		{
			visitor.visit(treeNode);
		}
		if (treeNode.hasLeftMostChild())
		{
			treeNode.getLeftMostChild().accept(visitor);
		}
		if (treeNode.hasRightSibling())
		{
			treeNode.getRightSibling().accept(visitor);
		}
		if (visitAfter)
		{
			visitor.visit(treeNode);
		}
	}

	/**
	 * Find all {@link SimpleTreeNode} objects from the first given {@link SimpleTreeNode} object
	 * that serves as the search target, that have the same value as the given value
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	public static <T, K> Collection<SimpleTreeNode<T, K>> findAllByValue(
		final @NonNull SimpleTreeNode<T, K> treeNode, final T value)
	{

		AtomicReference<Collection<SimpleTreeNode<T, K>>> foundTreeNodes = new AtomicReference<>(
			new LinkedHashSet<>());
		treeNode.accept(currentTreeNode -> {
			if (value == null)
			{
				if (currentTreeNode != null && currentTreeNode.getValue() == null)
				{
					foundTreeNodes.get().add(currentTreeNode);
				}
			}
			else
			{
				if (value.equals(currentTreeNode.getValue()))
				{
					foundTreeNodes.get().add(currentTreeNode);
				}
			}
		});
		return foundTreeNodes.get();
	}

	/**
	 * Find the first occurrence of {@link SimpleTreeNode} object from the first given
	 * {@link SimpleTreeNode} object that serves as the search target, that have the same value as
	 * the given value
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @param value
	 *            the value for the search process
	 * @return the first occurrence of {@link SimpleTreeNode} object that have the same value as the
	 *         given value
	 */
	public static <T, K> SimpleTreeNode<T, K> findByValue(
		final @NonNull SimpleTreeNode<T, K> treeNode, final T value)
	{
		final AtomicReference<SimpleTreeNode<T, K>> found = new AtomicReference<>();
		SimpleTreeNodeHandlerExtensions.findAllByValue(treeNode, value).stream().findFirst()
			.ifPresent(currentTreeNode -> {
				found.set(currentTreeNode);
			});
		return found.get();
	}

	/**
	 * Checks if the second given {@link SimpleTreeNode} object is a descendant of the first given
	 * {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @param descendantCandidate
	 *            the tree node to check
	 * @return true if the given {@link SimpleTreeNode} object is a descendant of the first given
	 *         {@link SimpleTreeNode} object otherwise false
	 */
	public static <T, K> boolean contains(final @NonNull SimpleTreeNode<T, K> treeNode,
		final SimpleTreeNode<T, K> descendantCandidate)
	{
		if (descendantCandidate == null)
		{
			return false;
		}
		return SimpleTreeNodeHandlerExtensions.traverse(treeNode).contains(descendantCandidate);
	}

	/**
	 * Checks if the given {@link Collection} object of {@link SimpleTreeNode} objects are
	 * descendants of the first given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @param treeNodes
	 *            the collection of the tree nodes to check
	 * @return true if the given {@link Collection} object of {@link SimpleTreeNode} objects are
	 *         descendants of the first given {@link SimpleTreeNode} object otherwise false
	 */
	public static <T, K> boolean containsAll(final @NonNull SimpleTreeNode<T, K> treeNode,
		final @NonNull Collection<SimpleTreeNode<T, K>> treeNodes)
	{
		return SimpleTreeNodeHandlerExtensions.traverse(treeNode).containsAll(treeNodes);
	}

	/**
	 * Traverse the given {@link SimpleTreeNode} object and adds all descendant with it
	 * self-included in to a {@link List} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 *
	 * @return a {@link List} object with the given {@link SimpleTreeNode} object and all
	 *         descendants
	 */
	public static <T, K> List<SimpleTreeNode<T, K>> toList(
		final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		return new ArrayList<>(SimpleTreeNodeHandlerExtensions.traverse(treeNode));
	}

	/**
	 * Gets all the right siblings from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return all the right siblings from the given {@link SimpleTreeNode} object
	 */
	public static <T, K> Collection<SimpleTreeNode<T, K>> getAllRightSiblings(
		final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		Collection<SimpleTreeNode<T, K>> allRightSiblings = new LinkedHashSet<>();
		if (treeNode.hasRightSibling())
		{
			SimpleTreeNode<T, K> currentRightSibling;
			do
			{
				currentRightSibling = treeNode.getRightSibling();
				allRightSiblings.add(currentRightSibling);
			}
			while (currentRightSibling.hasRightSibling());
		}
		return allRightSiblings;
	}


	/**
	 * Gets all the left siblings from the given {@link SimpleTreeNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeNode
	 *            the tree node
	 * @return all the left siblings from the given {@link SimpleTreeNode} object
	 */
	public static <T, K> Collection<SimpleTreeNode<T, K>> getAllLeftSiblings(
		final @NonNull SimpleTreeNode<T, K> treeNode)
	{
		Collection<SimpleTreeNode<T, K>> allSiblings = new LinkedHashSet<>();
		if (treeNode.hasParent())
		{
			SimpleTreeNode<T, K> parent = treeNode.getParent();
			SimpleTreeNode<T, K> leftMostChild = parent.getLeftMostChild();
			if (leftMostChild.equals(treeNode))
			{
				return allSiblings;
			}
			allSiblings.add(leftMostChild);
			if (leftMostChild.hasRightSibling())
			{
				SimpleTreeNode<T, K> currentRightSibling = leftMostChild.getRightSibling();
				if (currentRightSibling.equals(treeNode))
				{
					return allSiblings;
				}
				allSiblings.add(currentRightSibling);
				do
				{
					currentRightSibling = currentRightSibling.getRightSibling();
					if (currentRightSibling.equals(treeNode))
					{
						return allSiblings;
					}
					allSiblings.add(currentRightSibling);
				}
				while (currentRightSibling.hasRightSibling());
			}
		}
		return allSiblings;
	}

}
