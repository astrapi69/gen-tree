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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import io.github.astrapi69.gen.tree.api.ITreeNode;
import lombok.NonNull;

/**
 * The class {@link ITreeNodeHandlerExtensions} provides handler methods for the class
 * {@link ITreeNode}
 */
public class ITreeNodeHandlerExtensions
{

	/**
	 * Returns all siblings of the given {@link ITreeNode} object in the parent's children list
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return Returns all siblings of the given {@link ITreeNode} object
	 */
	public static <V, T extends ITreeNode<V, T>> Collection<T> getAllSiblings(
		final @NonNull T treeNode)
	{
		final T parent = treeNode.getParent();
		if (parent == null)
		{
			return new LinkedHashSet<>();
		}
		final Collection<T> allSiblings = new LinkedHashSet<>(parent.getChildren());
		allSiblings.remove(treeNode);
		return allSiblings;
	}

	/**
	 * Gets the root from the given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return the root from the given {@link ITreeNode} object
	 */
	public static <V, T extends ITreeNode<V, T>> T getRoot(final @NonNull T treeNode)
	{
		T root = treeNode;
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
	 * Returns the next sibling of the given {@link ITreeNode} object in the parent's children list.
	 * Returns null if the given {@link ITreeNode} object is the root or is the parent's last child
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of the given {@link ITreeNode} object or null if the given
	 *         {@link ITreeNode} object is the root or is the parent's last child
	 */
	public static <V, T extends ITreeNode<V, T>> T getNextSibling(final @NonNull T currentTreeNode)
	{
		T next = null;
		if (currentTreeNode.getParent() == null)
		{
			return next;
		}
		boolean isNext = false;
		for (T treeNode : currentTreeNode.getParent().getChildren())
		{
			if (isNext)
			{
				next = treeNode;
				break;
			}
			if (treeNode.equals(currentTreeNode))
			{
				isNext = true;
			}
		}
		return next;
	}

	/**
	 * Returns the previous sibling of the given {@link ITreeNode} object in the parent's children
	 * list. Returns null if the given {@link ITreeNode} object is the root or is the parent's first
	 * child.
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 *
	 * @param currentTreeNode
	 *            the tree node
	 * @return the next sibling of the given {@link ITreeNode} object or null if the given
	 *         {@link ITreeNode} object is the root or is the parent's last child.
	 */
	public static <V, T extends ITreeNode<V, T>> T getPreviousSibling(
		final @NonNull T currentTreeNode)
	{
		T previous = null;
		if (currentTreeNode.getParent() == null)
		{
			return previous;
		}
		for (T treeNode : currentTreeNode.getParent().getChildren())
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
	 * Returns the distance from the root to the given {@link ITreeNode} object. Returns 0 if the
	 * given {@link ITreeNode} object is the root {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 *
	 * @param treeNode
	 *            the tree node
	 * @return the level from the given {@link ITreeNode} object
	 */
	public static <V, T extends ITreeNode<V, T>> int getLevel(final @NonNull T treeNode)
	{
		T currentTreeNode = treeNode;
		int count = 0;
		while ((currentTreeNode = currentTreeNode.getParent()) != null)
		{
			count++;
		}
		return count;
	}

	/**
	 * Removes the given child from the given first {@link ITreeNode}
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child tree node
	 */
	public static <V, T extends ITreeNode<V, T>> void removeChild(final @NonNull T parentTreeNode,
		final T child)
	{
		removeChild(parentTreeNode, child, true);
	}

	/**
	 * Removes the given child from the given first {@link ITreeNode}
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child tree node
	 * @param clearChildren
	 *            the flag that indicates if the children from the child tree node will be cleared
	 */
	public static <V, T extends ITreeNode<V, T>> void removeChild(final @NonNull T parentTreeNode,
		final T child, final boolean clearChildren)
	{
		if (child != null)
		{
			removeFromParent(parentTreeNode, child, clearChildren);
		}
	}

	/**
	 * Removes the parent from the given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 */
	public static <V, T extends ITreeNode<V, T>> void removeFromParent(final @NonNull T treeNode)
	{
		if (treeNode.hasParent())
		{
			removeFromParent(treeNode.getParent(), treeNode);
		}
	}

	/**
	 * Removes the parent from the given child {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 */
	public static <V, T extends ITreeNode<V, T>> void removeFromParent(
		final @NonNull T parentTreeNode, final @NonNull T child)
	{
		removeFromParent(parentTreeNode, child, false);
	}

	/**
	 * Removes the parent from the given child {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @param clearChildren
	 *            the flag that indicates if the children from the child tree node will be cleared
	 */
	public static <V, T extends ITreeNode<V, T>> void removeFromParent(
		final @NonNull T parentTreeNode, final @NonNull T child, final boolean clearChildren)
	{
		if (isChildOf(parentTreeNode, child))
		{
			parentTreeNode.getChildren().remove(child);
			child.setParent(null);
			if (clearChildren)
			{
				child.clearChildren();
			}
		}
	}

	/**
	 * Moves the given {@link ITreeNode} object to the new given parent {@link ITreeNode} object
	 * that can be null
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNodeToMove
	 *            the tree node that will be moved to the new parent tree node
	 * @param newParentTreeNode
	 *            the new parent tree node
	 * @return true if the given {@link ITreeNode} object was moved otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean move(final @NonNull T treeNodeToMove,
		final T newParentTreeNode)
	{
		if (newParentTreeNode != null && newParentTreeNode.isLeaf())
		{
			return false;
		}
		// if the given new parent is a descendant of the tree node to move, then we return false
		// and do not move the tree node
		if (isDescendant(treeNodeToMove, newParentTreeNode))
		{
			return false;
		}
		if (treeNodeToMove.hasParent())
		{
			removeChild(treeNodeToMove.getParent(), treeNodeToMove, false);
		}
		if (newParentTreeNode != null && newParentTreeNode.isNode())
		{
			newParentTreeNode.addChild(treeNodeToMove);
		}
		return true;
	}

	/**
	 * Checks if the second given {@link ITreeNode} object is a child of the first given
	 * {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child tree node to check
	 * @return true, if the second given {@link ITreeNode} object is a child of the first
	 *         {@link ITreeNode} object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean isChild(final @NonNull T parentTreeNode,
		final T child)
	{
		return child != null && isChildOf(parentTreeNode, child);
	}

	/**
	 * Checks if the second given {@link ITreeNode} object is a child of the first {@link ITreeNode}
	 * object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @return true, if the second given {@link ITreeNode} object is a child of the first
	 *         {@link ITreeNode} object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean isChildOf(final @NonNull T parentTreeNode,
		final @NonNull T child)
	{
		return parentTreeNode.getChildren().contains(child);
	}

	/**
	 * Checks if the first given {@link ITreeNode} object is the parent of the second
	 * {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @return true, if the first given {@link ITreeNode} object is the parent of the second given
	 *         {@link ITreeNode} object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean isParentOf(final @NonNull T parentTreeNode,
		final @NonNull T child)
	{
		return child.getParent().equals(parentTreeNode);
	}

	/**
	 * Removes all the given children from the first given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param children
	 *            the children to remove
	 */
	public static <V, T extends ITreeNode<V, T>> void removeChildren(
		final @NonNull T parentTreeNode, final @NonNull Collection<T> children)
	{
		children.forEach(child -> ITreeNodeHandlerExtensions.removeChild(parentTreeNode, child));
	}

	/**
	 * Removes all children from the given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 */
	public static <V, T extends ITreeNode<V, T>> void removeChildren(
		final @NonNull T parentTreeNode)
	{
		ITreeNodeHandlerExtensions.removeChildren(parentTreeNode,
			new ArrayList<>(parentTreeNode.getChildren()));
	}

	/**
	 * Checks if the given {@link ITreeNode} is the root {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link ITreeNode} is the root {@link ITreeNode} object
	 */
	public static <V, T extends ITreeNode<V, T>> boolean isRoot(final @NonNull T treeNode)
	{
		return !ITreeNodeHandlerExtensions.hasParent(treeNode);
	}

	/**
	 * Checks if the given {@link ITreeNode} object has a next sibling {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link ITreeNode} object has a next sibling {@link ITreeNode}
	 *         object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean hasNextSibling(final @NonNull T treeNode)
	{
		return treeNode.getNextSibling() != null;
	}

	/**
	 * Checks if the given {@link ITreeNode} object has a parent {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link ITreeNode} object has a parent {@link ITreeNode} object
	 *         otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean hasParent(final @NonNull T treeNode)
	{
		return treeNode.getParent() != null;
	}

	/**
	 * Checks if the given {@link ITreeNode} object has a previous sibling {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link ITreeNode} object has a previous sibling {@link ITreeNode}
	 *         object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean hasPreviousSibling(
		final @NonNull T treeNode)
	{
		return treeNode.getPreviousSibling() != null;
	}

	/**
	 * Checks if the given {@link ITreeNode} object is a node
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link ITreeNode} object is a node otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean isNode(final @NonNull T treeNode)
	{
		return !treeNode.isLeaf();
	}

	/**
	 * Removes all the descendants from the given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 */
	public static <V, T extends ITreeNode<V, T>> void clearAll(final @NonNull T treeNode)
	{
		treeNode.accept(ITreeNode::clearChildren);
	}

	/**
	 * Removes all the children from the given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 */
	public static <V, T extends ITreeNode<V, T>> void clearChildren(final @NonNull T treeNode)
	{
		ITreeNodeHandlerExtensions.removeChildren(treeNode,
			new ArrayList<>(treeNode.getChildren()));
	}

	/**
	 * Adds the given child {@link ITreeNode} object to the first given parent {@link ITreeNode}
	 * object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 */
	public static <V, T extends ITreeNode<V, T>> void addChild(final @NonNull T parentTreeNode,
		final T child)
	{
		if (child != null && parentTreeNode.isNode())
		{
			child.setParent(parentTreeNode);
			parentTreeNode.getChildren().add(child);
		}
	}

	/**
	 * Adds the given child {@link ITreeNode} object to the first given parent {@link ITreeNode}
	 * object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child
	 * @param index
	 *            the index of the child to insert
	 */
	public static <V, T extends ITreeNode<V, T>> void addChild(final @NonNull T parentTreeNode,
		final T child, final int index)
	{
		if (child != null && parentTreeNode.isNode())
		{
			child.setParent(parentTreeNode);
			if (parentTreeNode.getChildren() instanceof List)
			{
				List<T> children = (List<T>)parentTreeNode.getChildren();
				children.add(index, child);
			}
			else
			{
				parentTreeNode.getChildren().add(child);
			}
		}
	}

	/**
	 * Gets an {@link Optional} object with the child tree node from the given index from the first
	 * given parent {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param index
	 *            the index of the child to get
	 * @return an {@link Optional} object with the child tree node
	 */
	public static <V, T extends ITreeNode<V, T>> Optional<T> getChildAt(
		final @NonNull T parentTreeNode, final int index)
	{
		if (parentTreeNode.isNode())
		{
			if (parentTreeNode.getChildren() instanceof List)
			{
				List<T> children = (List<T>)parentTreeNode.getChildren();
				T child = children.get(index);
				if (child != null)
				{
					return Optional.of(child);
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Gets the index of the given child from the given index from the first given parent
	 * {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param child
	 *            the child to resolve the index
	 * @return the index of the given child in this tree node
	 */
	public static <V, T extends ITreeNode<V, T>> int getChildIndex(final @NonNull T parentTreeNode,
		final T child)
	{
		if (child != null && parentTreeNode.isNode())
		{
			child.setParent(parentTreeNode);
			if (parentTreeNode.getChildren() instanceof List)
			{
				List<T> children = (List<T>)parentTreeNode.getChildren();
				return children.indexOf(child);
			}
		}
		return -1;
	}

	/**
	 * Adds all the given children from the first given parent {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param parentTreeNode
	 *            the parent tree node
	 * @param children
	 *            the children to add
	 */
	public static <V, T extends ITreeNode<V, T>> void addChildren(final @NonNull T parentTreeNode,
		final @NonNull Collection<T> children)
	{
		if (parentTreeNode.isNode())
		{
			children.forEach(child -> ITreeNodeHandlerExtensions.addChild(parentTreeNode, child));
		}
	}

	/**
	 * Gets the child count from the given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return the child count
	 */
	public static <V, T extends ITreeNode<V, T>> int getChildCount(final @NonNull T treeNode)
	{
		return treeNode.getChildren().size();
	}

	/**
	 * Checks if the given {@link ITreeNode} object has children
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @return true, if the given {@link ITreeNode} object has children otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean hasChildren(final @NonNull T treeNode)
	{
		return treeNode.getChildren() != null && !treeNode.getChildren().isEmpty();
	}

	/**
	 * Traverse the given {@link ITreeNode} object and add all descendants with the given
	 * {@link ITreeNode} object included in to the returned {@link Collection} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 *
	 * @return a {@link Collection} object with the given {@link ITreeNode} object and add all
	 *         descendants
	 */
	public static <V, T extends ITreeNode<V, T>> Collection<T> traverse(final @NonNull T treeNode)
	{
		final Collection<T> allTreeNodes = new LinkedHashSet<>();
		treeNode.accept(allTreeNodes::add);
		return allTreeNodes;
	}

	/**
	 * Find all {@link ITreeNode} objects from the first given {@link ITreeNode} object that serves
	 * as the search target, that have the same value as the given value
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	public static <V, T extends ITreeNode<V, T>> Collection<T> findAllByValue(
		final @NonNull T treeNode, final V value)
	{

		AtomicReference<Collection<T>> foundTreeNodes = new AtomicReference<>(
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
	 * Find the first occurrence of {@link ITreeNode} object from the first given {@link ITreeNode}
	 * object that serves as the search target, that have the same value as the given value
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param value
	 *            the value for the search process
	 * @return the first occurrence of {@link ITreeNode} object that have the same value as the
	 *         given value
	 */
	public static <V, T extends ITreeNode<V, T>> T findByValue(final @NonNull T treeNode,
		final V value)
	{
		final AtomicReference<T> found = new AtomicReference<>();
		ITreeNodeHandlerExtensions.findAllByValue(treeNode, value).stream().findFirst()
			.ifPresent(found::set);
		return found.get();
	}

	/**
	 * Checks if the second given {@link ITreeNode} object is a descendant of the first given
	 * {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param descendantCandidate
	 *            the tree node to check
	 * @return true if the given {@link ITreeNode} object is a descendant of the first given
	 *         {@link ITreeNode} object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean contains(final @NonNull T treeNode,
		final T descendantCandidate)
	{
		if (descendantCandidate == null)
		{
			return false;
		}
		return TreeNodeVisitorHandlerExtensions.traverse(treeNode).contains(descendantCandidate);
	}

	/**
	 * Checks if the given {@link Collection} object of {@link ITreeNode} objects are descendants of
	 * the first given {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param treeNodes
	 *            the collection of the tree nodes to check
	 * @return true if the given {@link Collection} object of {@link ITreeNode} objects are
	 *         descendants of the first given {@link ITreeNode} object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean containsAll(final @NonNull T treeNode,
		final @NonNull Collection<T> treeNodes)
	{
		return TreeNodeVisitorHandlerExtensions.traverse(treeNode).containsAll(treeNodes);
	}

	/**
	 * Traverse the given {@link ITreeNode} object and adds all descendant with it self-included in
	 * to a {@link List} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 *
	 * @return a {@link List} object with the given {@link ITreeNode} object and all descendants
	 */
	public static <V, T extends ITreeNode<V, T>> List<T> toList(final @NonNull T treeNode)
	{
		return new ArrayList<>(TreeNodeVisitorHandlerExtensions.traverse(treeNode));
	}

	/**
	 * Find the occurrence of {@link ITreeNode} object from the given possible descendant object
	 * that serves as the search target
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node
	 * @param possibleDescendant
	 *            the id for the search process
	 * @return the first occurrence of {@link ITreeNode} object that have the same value as the
	 *         given value
	 */
	public static <V, T extends ITreeNode<V, T>> T findFirstOccurenceOfDescendant(
		final @NonNull T treeNode, final T possibleDescendant)
	{
		final AtomicReference<Boolean> stopSearch = new AtomicReference<>(Boolean.FALSE);
		final AtomicReference<T> found = new AtomicReference<>();
		treeNode.accept(currentTreeNode -> {
			if (!stopSearch.get())
			{
				if (currentTreeNode.equals(possibleDescendant))
				{
					stopSearch.set(Boolean.TRUE);
					found.set(currentTreeNode);
				}
			}
		});
		return found.get();
	}

	/**
	 * Checks if the second given {@link ITreeNode} object is a descendant of the first given
	 * {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node to check
	 * @param possibleDescendant
	 *            the possible descendant tree node to check
	 * @return true, if the second given {@link ITreeNode} object is a descendant of the first
	 *         {@link ITreeNode} object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean isDescendant(final @NonNull T treeNode,
		final T possibleDescendant)
	{
		return findFirstOccurenceOfDescendant(treeNode, possibleDescendant) != null;
	}

	/**
	 * Checks if the second given {@link ITreeNode} object is an ancestor of the first given
	 * {@link ITreeNode} object
	 *
	 * @param <V>
	 *            the generic type of the value
	 * @param <T>
	 *            the generic type of the concrete tree node
	 * @param treeNode
	 *            the tree node to check
	 * @param possibleAncestor
	 *            the possible ancestor tree node to check
	 * @return true, if the second given {@link ITreeNode} object is a ancestor of the first
	 *         {@link ITreeNode} object otherwise false
	 */
	public static <V, T extends ITreeNode<V, T>> boolean isAncestor(final @NonNull T treeNode,
		final @NonNull T possibleAncestor)
	{
		T parent;
		parent = treeNode.getParent();
		while (parent != null)
		{
			if (parent == possibleAncestor)
			{
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

}
