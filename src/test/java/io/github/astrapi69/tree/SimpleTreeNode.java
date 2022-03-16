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


import java.util.LinkedHashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@ToString(exclude = { "parent" })
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleTreeNode<T>
{

	@Getter
	@Setter
	/** The left most child */
	SimpleTreeNode<T> parent;
	/** The left most child */
	@Getter
	@Setter
	SimpleTreeNode<T> leftMostChild;
	/** The right sibling */
	@Getter
	@Setter
	SimpleTreeNode<T> rightSibling;
	/** The value */
	@Getter
	@Setter
	T value;
	/** The flag that indicates if this tree node is a leaf or a node */
	@Getter
	@Setter
	boolean leaf;

	public SimpleTreeNode(T value)
	{
		this.value = value;
	}

	public SimpleTreeNode<T> getRoot()
	{
		SimpleTreeNode<T> root = this;
		if (isRoot())
		{
			return root;
		}
		do
		{
			root = root.getParent();
		}
		while (!isRoot());
		return root;
	}

	/**
	 * Checks if this {@link SimpleTreeNode} is the root {@link SimpleTreeNode} object
	 *
	 * @return true, if this {@link SimpleTreeNode} is the root {@link SimpleTreeNode} object
	 */
	public boolean isRoot()
	{
		return !hasParent();
	}

	/**
	 * Checks if this {@link SimpleTreeNode} object is a node
	 *
	 * @return true, if this {@link SimpleTreeNode} object is a node otherwise false
	 */
	boolean isNode()
	{
		return !isLeaf();
	}

	public Set<SimpleTreeNode<T>> getAllSiblings()
	{
		Set<SimpleTreeNode<T>> allSiblings = new LinkedHashSet<>();
		if (hasParent())
		{
			SimpleTreeNode<T> parent = getParent();
			SimpleTreeNode<T> leftMostChild = parent.getLeftMostChild();
			allSiblings.add(leftMostChild);
			if (leftMostChild.hasRightSibling())
			{
				SimpleTreeNode<T> currentRightSibling = leftMostChild.getRightSibling();
				allSiblings.add(currentRightSibling);
				do
				{
					currentRightSibling = currentRightSibling.getRightSibling();
					allSiblings.add(currentRightSibling);
				}
				while (currentRightSibling.hasRightSibling());
			}
		}
		return allSiblings;
	}

	/**
	 * Checks if this node has a parent
	 *
	 * @return true, if successful
	 */
	public boolean hasParent()
	{
		return getParent() != null;
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
}
