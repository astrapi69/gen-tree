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
package io.github.astrapi69.gen.tree;

import java.util.Collection;
import java.util.Set;

import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.handler.SimpleTreeNodeHandlerExtensions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The generic class {@link SimpleTreeNode} holds only the parent, the left most child and the right
 * sibling
 *
 * @param <V>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = { "parent" })
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleTreeNode<V, K> implements Acceptable<Visitor<SimpleTreeNode<V, K>>>
{

	/** The id from this node. */
	K id;

	/** The left most child */
	SimpleTreeNode<V, K> parent;

	/** The left most child */
	SimpleTreeNode<V, K> leftMostChild;

	/** The right sibling */
	SimpleTreeNode<V, K> rightSibling;

	/** The value */
	V value;

	/** The flag that indicates if this tree node is a leaf or a node */
	boolean leaf;

	/**
	 * Instantiates a new {@link SimpleTreeNode} object
	 *
	 * @param value
	 *            the value
	 */
	public SimpleTreeNode(V value)
	{
		this.value = value;
	}

	/**
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @return the level from this node.
	 */
	public int getLevel()
	{
		return SimpleTreeNodeHandlerExtensions.getLevel(this);
	}

	/**
	 * Gets the root {@link SimpleTreeNode} object
	 *
	 * @return the root {@link SimpleTreeNode} object
	 */
	public SimpleTreeNode<V, K> getRoot()
	{
		return SimpleTreeNodeHandlerExtensions.getRoot(this);
	}

	/**
	 * Checks if this {@link SimpleTreeNode} is the root {@link SimpleTreeNode} object
	 *
	 * @return true, if this {@link SimpleTreeNode} is the root {@link SimpleTreeNode} object
	 */
	public boolean isRoot()
	{
		return SimpleTreeNodeHandlerExtensions.isRoot(this);
	}

	/**
	 * Checks if this {@link SimpleTreeNode} object is a node
	 *
	 * @return true, if this {@link SimpleTreeNode} object is a node otherwise false
	 */
	public boolean isNode()
	{
		return !isLeaf();
	}

	/**
	 * Gets all the siblings from this node
	 *
	 * @return all the siblings from this node
	 */
	public Collection<SimpleTreeNode<V, K>> getAllSiblings()
	{
		return SimpleTreeNodeHandlerExtensions.getAllSiblings(this);
	}

	/**
	 * Gets all the left siblings from this node
	 *
	 * @return all the left siblings from this node
	 */
	public Collection<SimpleTreeNode<V, K>> getAllLeftSiblings()
	{
		return SimpleTreeNodeHandlerExtensions.getAllLeftSiblings(this);
	}

	/**
	 * Gets all the right siblings from this node
	 *
	 * @return all the right siblings from this node
	 */
	public Collection<SimpleTreeNode<V, K>> getAllRightSiblings()
	{
		return SimpleTreeNodeHandlerExtensions.getAllRightSiblings(this);
	}

	/**
	 * Checks if this node has a parent
	 *
	 * @return true, if successful
	 */
	public boolean hasParent()
	{
		return SimpleTreeNodeHandlerExtensions.hasParent(this);
	}

	/**
	 * Checks if this node has a right sibling
	 *
	 * @return true, if successful
	 */
	public boolean hasRightSibling()
	{
		return getRightSibling() != null;
	}

	/**
	 * Checks if this node has a left most child
	 *
	 * @return true, if successful
	 */
	public boolean hasLeftMostChild()
	{
		return getLeftMostChild() != null;
	}

	/**
	 * Traverse this node and add all descendants with this included in to a {@link Set}
	 *
	 * @return a {@link Set} with this node and add all descendants
	 */
	public Collection<SimpleTreeNode<V, K>> traverse()
	{
		return SimpleTreeNodeHandlerExtensions.traverse(this);
	}

	/**
	 * Gets the children
	 *
	 * @return the children
	 */
	public Collection<SimpleTreeNode<V, K>> getChildren()
	{
		return SimpleTreeNodeHandlerExtensions.getChildren(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor<SimpleTreeNode<V, K>> visitor)
	{
		SimpleTreeNodeHandlerExtensions.accept(this, visitor);
	}

}
