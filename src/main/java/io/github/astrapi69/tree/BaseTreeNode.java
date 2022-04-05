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
package io.github.astrapi69.tree;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.tree.handler.BaseTreeNodeHandlerExtensions;

/**
 * The generic class {@link BaseTreeNode} have a generic id and value object
 *
 * @param <T>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 */
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "children", "parent" })
@ToString(exclude = { "children", "parent" })
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseTreeNode<T, K> implements Acceptable<Visitor<BaseTreeNode<T, K>>>
{

	/** The id from this node. */
	@Getter
	@Setter
	K id;

	/** The value. */
	@Getter
	@Setter
	T value;

	/** The children. */
	@Setter
	@Builder.Default
	Collection<BaseTreeNode<T, K>> children = new LinkedHashSet<>();

	/** The optional display value. */
	@Getter
	@Setter
	String displayValue;

	/** The parent from this node. If this is null it is the root. */
	@Getter
	@Setter
	BaseTreeNode<T, K> parent;

	/** The flag that indicates if this tree node is a leaf or a node */
	@Getter
	@Setter
	boolean leaf;

	/**
	 * Instantiates a new {@link BaseTreeNode} object
	 *
	 * @param value
	 *            the value
	 */
	public BaseTreeNode(final T value)
	{
		setValue(value);
	}

	/**
	 * Gets the children of this node
	 *
	 * @return the children
	 */
	public Collection<BaseTreeNode<T, K>> getChildren()
	{
		return this.children;
	}

	/**
	 * Adds the child.
	 *
	 * @param child
	 *            the child
	 */
	public void addChild(final BaseTreeNode<T, K> child)
	{
		BaseTreeNodeHandlerExtensions.addChild(this, child);
	}

	/**
	 * Adds all the given children
	 *
	 * @param children
	 *            the children to add
	 */
	public void addChildren(final @NonNull Collection<BaseTreeNode<T, K>> children)
	{
		BaseTreeNodeHandlerExtensions.addChildren(this, children);
	}

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	public int getChildCount()
	{
		return BaseTreeNodeHandlerExtensions.getChildCount(this);
	}

	/**
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @return the level from this node.
	 */
	public int getLevel()
	{
		return BaseTreeNodeHandlerExtensions.getLevel(this);
	}

	/**
	 * Returns the next sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's last child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	public BaseTreeNode<T, K> getNextSibling()
	{
		return BaseTreeNodeHandlerExtensions.getNextSibling(this);
	}

	/**
	 * Returns the previous sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's first child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	public BaseTreeNode<T, K> getPreviousSibling()
	{
		return BaseTreeNodeHandlerExtensions.getPreviousSibling(this);
	}

	/**
	 * Gets the root {@link BaseTreeNode} object
	 *
	 * @return the root {@link BaseTreeNode} object
	 */
	public BaseTreeNode<T, K> getRoot()
	{
		return BaseTreeNodeHandlerExtensions.getRoot(this);
	}

	/**
	 * Checks for children.
	 *
	 * @return true, if successful
	 */
	public boolean hasChildren()
	{
		return BaseTreeNodeHandlerExtensions.hasChildren(this);
	}

	/**
	 * Checks for parent
	 *
	 * @return true, if successful
	 */
	public boolean hasParent()
	{
		return BaseTreeNodeHandlerExtensions.hasParent(this);
	}

	/**
	 * Checks if this {@link BaseTreeNode} object is a node
	 *
	 * @return true, if this {@link BaseTreeNode} object is a node otherwise false
	 */
	public boolean isNode()
	{
		return BaseTreeNodeHandlerExtensions.isNode(this);
	}

	/**
	 * Checks if this {@link BaseTreeNode} is the root {@link BaseTreeNode} object
	 *
	 * @return true, if this {@link BaseTreeNode} is the root {@link BaseTreeNode} object
	 */
	public boolean isRoot()
	{
		return BaseTreeNodeHandlerExtensions.isRoot(this);
	}

	/**
	 * Removes the child.
	 *
	 * @param child
	 *            the child
	 */
	public void removeChild(final BaseTreeNode<T, K> child)
	{
		BaseTreeNodeHandlerExtensions.removeChild(this, child);
	}

	/**
	 * Removes all the children
	 */
	public void clearChildren()
	{
		BaseTreeNodeHandlerExtensions.clearChildren(this);
	}

	/**
	 * Removes all the descendants
	 */
	public void clearAll()
	{
		BaseTreeNodeHandlerExtensions.clearAll(this);
	}

	/**
	 * Removes all the given children
	 *
	 * @param children
	 *            the children to remove
	 */
	public void removeChildren(final @NonNull Collection<BaseTreeNode<T, K>> children)
	{
		BaseTreeNodeHandlerExtensions.removeChildren(this, children);
	}

	/**
	 * Removes all the children
	 */
	public void removeChildren()
	{
		BaseTreeNodeHandlerExtensions.removeChildren(this);
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link List} object
	 *
	 * @return a {@link List} object with this node and add all descendant
	 */
	public List<BaseTreeNode<T, K>> toList()
	{
		return BaseTreeNodeHandlerExtensions.toList(this);
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link Collection}
	 * object
	 * 
	 * @return a {@link Collection} object with this node and add all descendant
	 */
	public Collection<BaseTreeNode<T, K>> traverse()
	{
		return BaseTreeNodeHandlerExtensions.traverse(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(final @NonNull Visitor<BaseTreeNode<T, K>> visitor)
	{
		BaseTreeNodeHandlerExtensions.accept(this, visitor);
	}

	/**
	 * Find all {@link BaseTreeNode} objects that have the same value as the given value
	 *
	 * @param value
	 *            the value for the search process
	 * @return a {@link Collection} object with all found occurrences that have the same value as
	 *         the given value
	 */
	public Collection<BaseTreeNode<T, K>> findAllByValue(final T value)
	{
		return BaseTreeNodeHandlerExtensions.findAllByValue(this, value);
	}

	/**
	 * Find the first occurrence of {@link BaseTreeNode} object that have the same value as the
	 * given value
	 *
	 * @param value
	 *            the value for the search process
	 * @return the first occurrence of {@link BaseTreeNode} object that have the same value as the
	 *         given value
	 */
	public BaseTreeNode<T, K> findByValue(final T value)
	{
		return BaseTreeNodeHandlerExtensions.findByValue(this, value);
	}

	/**
	 * Checks if the given {@link BaseTreeNode} object is a descendant of this tree node
	 * 
	 * @param baseTreeNode
	 *            the tree node to check
	 * @return true if the given {@link BaseTreeNode} object is a descendant of this tree node
	 *         otherwise false
	 */
	public boolean contains(final BaseTreeNode<T, K> baseTreeNode)
	{
		return BaseTreeNodeHandlerExtensions.contains(this, baseTreeNode);
	}

	/**
	 * Checks if the given {@link Collection} object of {@link BaseTreeNode} objects are descendants
	 * of this tree node
	 * 
	 * @param baseTreeNodes
	 *            the collection of the tree nodes to check
	 * @return true if the given {@link Collection} object of {@link BaseTreeNode} objects are
	 *         descendants of this tree node otherwise false
	 */
	public boolean containsAll(final @NonNull Collection<BaseTreeNode<T, K>> baseTreeNodes)
	{
		return BaseTreeNodeHandlerExtensions.containsAll(this, baseTreeNodes);
	}

}
