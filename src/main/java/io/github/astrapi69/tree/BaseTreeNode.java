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
 * The generic class {@link BaseTreeNode} provides the same functionality as {@link TreeNode}
 * without implementing an interface
 *
 * @param <T>
 *            the generic type of the value
 */
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "children" })
@ToString(exclude = { "children" })
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseTreeNode<T>
{


	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The children. */
	@Setter
	@Builder.Default
	List<BaseTreeNode<T>> children = new ArrayList<>();

	/** The optional display value. */
	@Getter
	@Setter
	String displayValue;

	/** The parent from this node. If this is null it is the root. */
	@Getter
	@Setter
	BaseTreeNode<T> parent;

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
	public BaseTreeNode(final T value)
	{
		setValue(value);
	}

	public List<BaseTreeNode<T>> getChildren()
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
	public void addChild(final BaseTreeNode<T> child)
	{
		child.setParent(this);
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
	public void addChildAt(final int index, final BaseTreeNode<T> child)
		throws IndexOutOfBoundsException
	{
		if (index < getChildren().size())
		{
			getChildren().add(index, child);
		}
		else
		{
			addChild(child);
		}
	}

	/**
	 * Returns all siblings of this node in the parent's children list. Returns null if this node is
	 * the root.
	 *
	 * @return Returns all siblings of this node or null if this node is the root.
	 */
	public List<BaseTreeNode<T>> getAllSiblings()
	{
		final BaseTreeNode<T> parent = getParent();
		if (parent == null)
		{
			return null;
		}
		final List<BaseTreeNode<T>> siblings = new ArrayList<>(parent.getChildren());
		siblings.remove(this);
		return siblings;
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
	public BaseTreeNode<T> getChild(BaseTreeNode<T> parent, int index)
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
	public int getIndexOfChild(BaseTreeNode<T> parent, BaseTreeNode<T> child)
	{
		return parent.getChildren().indexOf(child);
	}

	/**
	 * Returns the depth of the tree beginning at this node Returns 0 if this node has no children.
	 *
	 * @return the depth of the tree beginning at this node.
	 */
	public int getDepth()
	{
		if (isLeaf() || getChildCount() == 0)
		{
			return 0;
		}
		int maxDepth = 1;
		int currentDepth = 1;
		for (BaseTreeNode<T> data : getChildren())
		{
			while (data.hasChildren())
			{
				currentDepth++;
				data = data.getChildren().get(0);
			}
			if (maxDepth < currentDepth)
			{
				maxDepth = currentDepth;
			}
		}
		return maxDepth;
	}

	/**
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @return the level from this node.
	 */
	public int getLevel()
	{
		BaseTreeNode<T> current = this;
		int count = 0;
		while ((current = current.getParent()) != null)
		{
			count++;
		}
		return count;
	}

	/**
	 * Returns the next sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's last child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	public BaseTreeNode<T> getNextSibling()
	{
		if (getParent() == null)
		{
			return null;
		}
		final int index = getParent().getChildren().indexOf(this) + 1;
		if (index == getParent().getChildCount())
		{
			return null;
		}
		return getParent().getChildren().get(index);
	}

	/**
	 * Returns the previous sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's first child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	public BaseTreeNode<T> getPreviousSibling()
	{
		if (getParent() == null)
		{
			return null;
		}
		final int index = getParent().getChildren().indexOf(this) - 1;
		if (index < 0)
		{
			return null;
		}
		return getParent().getChildren().get(index);
	}

	/**
	 * Gets the root {@link BaseTreeNode} object
	 *
	 * @return the root {@link BaseTreeNode} object
	 */
	public BaseTreeNode<T> getRoot()
	{
		BaseTreeNode<T> root = this;
		BaseTreeNode<T> parent = getParent();
		while (parent != null && !parent.isRoot())
		{
			parent = parent.getParent();
			root = parent;
		}
		return root;
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
		return getParent() != null;
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
	 * Checks if this {@link BaseTreeNode} is the root {@link BaseTreeNode} object
	 *
	 * @return true, if this {@link BaseTreeNode} is the root {@link BaseTreeNode} object
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
	public void removeChild(final BaseTreeNode<T> child)
	{
		getChildren().remove(child);
		child.setParent(null);
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
		final BaseTreeNode<T> child = getChildren().remove(index);
		if (child != null)
		{
			child.setParent(null);
		}
	}

	/**
	 * To list.
	 *
	 * @return the list
	 */
	public List<BaseTreeNode<T>> toList()
	{
		final List<BaseTreeNode<T>> list = new ArrayList<>();
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
	public void traverse(final BaseTreeNode<T> node, final List<BaseTreeNode<T>> list)
	{
		list.add(node);
		for (final BaseTreeNode<T> data : node.getChildren())
		{
			traverse(data, list);
		}
	}

}
