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

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The generic class {@link ParentIdTreeNode} provides the same functionality as {@link TreeNode}
 * without implementing an interface
 *
 * @param <T>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 */
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "children" })
@ToString(exclude = { "children" })
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParentIdTreeNode<T, K>
{

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The id from this node. */
	@Getter
	@Setter
	K id;

	/** The parent id from this node. If parentId is null this tree node is the root. */
	@Getter
	@Setter
	K parentId;

	/** The children. */
	@Setter
	@Builder.Default
	List<ParentIdTreeNode<T, K>> children = new ArrayList<>();

	/** The optional display value. */
	@Getter
	@Setter
	String displayValue;

	/** The value. */
	@Getter
	@Setter
	T value;

	/** The flag that indicates if this tree node is a node or a leaf */
	@Getter
	@Setter
	@Builder.Default
	boolean node = true;

	/**
	 * Instantiates a new tree node.
	 *
	 * @param value
	 *            the value
	 */
	public ParentIdTreeNode(final T value)
	{
		setValue(value);
	}

	public List<ParentIdTreeNode<T, K>> getChildren()
	{
		if (this.children == null)
		{
			this.children = new ArrayList<>();
		}
		return this.children;
	}


	/**
	 * Adds the child.
	 *
	 * @param child
	 *            the child
	 */
	public void addChild(final ParentIdTreeNode<T, K> child)
	{
		child.setParentId(this.id);
		getChildren().add(child);
	}

	/**
	 * Adds the child.
	 *
	 * @param index
	 *            the index
	 * @param child
	 *            the child
	 * @throws IndexOutOfBoundsException
	 *             the index out of bounds exception
	 */
	public void addChildAt(final int index, final ParentIdTreeNode<T, K> child)
		throws IndexOutOfBoundsException
	{
		if (index < getChildren().size())
		{
			child.setParentId(this.id);
			getChildren().add(index, child);
		}
		else
		{
			addChild(child);
		}
	}

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	public int getChildCount()
	{
		return getChildren().size();
	}

	/**
	 * Gets the child from the given index.
	 *
	 * @param parent
	 *            the parent
	 * @param index
	 *            the index
	 * @return the child from the given index
	 */
	public ParentIdTreeNode<T, K> getChild(ParentIdTreeNode<T, K> parent, int index)
	{
		return parent.getChildren().get(index);
	}

	/**
	 * Gets the index of the given child from the given parent.
	 *
	 * @param parent
	 *            the parent
	 * @param child
	 *            the child
	 * @return the index of the given child from the given parent
	 */
	public int getIndexOfChild(ParentIdTreeNode<T, K> parent, ParentIdTreeNode<T, K> child)
	{
		return parent.getChildren().indexOf(child);
	}

	/**
	 * Checks for children.
	 *
	 * @return true, if successful
	 */
	public boolean hasChildren()
	{
		return getChildren() != null && !getChildren().isEmpty();
	}

	/**
	 * Checks for parent
	 *
	 * @return true, if successful
	 */
	public boolean hasParent()
	{
		return getParentId() != null;
	}

	/**
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	public boolean isLeaf()
	{
		return !isNode();
	}

	/**
	 * Checks if this {@link ParentIdTreeNode} is the root {@link ParentIdTreeNode} object
	 *
	 * @return true, if this {@link ParentIdTreeNode} is the root {@link ParentIdTreeNode} object
	 */
	public boolean isRoot()
	{
		return !hasParent();
	}

	/**
	 * Removes the child.
	 *
	 * @param child
	 *            the child
	 */
	public void removeChild(final ParentIdTreeNode<T, K> child)
	{
		getChildren().remove(child);
		child.setParentId(null);
	}

	/**
	 * Removes the child.
	 *
	 * @param index
	 *            the index
	 * @throws IndexOutOfBoundsException
	 *             the index out of bounds exception
	 */
	public void removeChildAt(final int index) throws IndexOutOfBoundsException
	{
		final ParentIdTreeNode<T, K> child = getChildren().remove(index);
		if (child != null)
		{
			child.setParentId(null);
		}
	}

	/**
	 * To list.
	 *
	 * @return the list
	 */
	public List<ParentIdTreeNode<T, K>> toList()
	{
		final List<ParentIdTreeNode<T, K>> list = new ArrayList<>();
		traverse(this, list);
		return list;
	}

	/**
	 * Traverse.
	 *
	 * @param node
	 *            the node
	 * @param list
	 *            the list
	 */
	public void traverse(final ParentIdTreeNode<T, K> node, final List<ParentIdTreeNode<T, K>> list)
	{
		list.add(node);
		for (final ParentIdTreeNode<T, K> data : node.getChildren())
		{
			traverse(data, list);
		}
	}

}
