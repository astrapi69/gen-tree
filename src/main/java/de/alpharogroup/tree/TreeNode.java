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
package de.alpharogroup.tree;

import java.util.ArrayList;
import java.util.List;

import de.alpharogroup.tree.ifaces.ITreeNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The generic class TreeNode.
 *
 * @param <T>
 *            the generic type
 */
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = { "children" })
public class TreeNode<T> implements ITreeNode<T>
{

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The children. */
	private List<ITreeNode<T>> children;

	/** The parent from this node. If this is null it is the root. */
	@Getter
	@Setter
	private ITreeNode<T> parent;

	/** The value. */
	@Getter
	@Setter
	private T value;

	/** The optional display value. */
	@Getter
	@Setter
	private String displayValue;

	/**
	 * Instantiates a new tree node.
	 *
	 * @param value
	 *            the value
	 */
	public TreeNode(final T value)
	{
		setValue(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(final ITreeNode<T> child)
	{
		child.setParent(this);
		getChildren().add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChildAt(final int index, final ITreeNode<T> child)
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
	 * {@inheritDoc}
	 */
	@Override
	public List<ITreeNode<T>> getAllSiblings()
	{
		final ITreeNode<T> parent = getParent();
		if (parent == null)
		{
			return null;
		}
		final List<ITreeNode<T>> siblings = new ArrayList<>(parent.getChildren());
		siblings.remove(this);
		return siblings;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getChildCount()
	{
		return getChildren().size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ITreeNode<T>> getChildren()
	{
		if (this.children == null)
		{
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDepth()
	{
		if (isLeaf() || getChildCount() == 0)
		{
			return 0;
		}
		int maxDepth = 1;
		int currentDepth = 1;
		for (ITreeNode<T> data : getChildren())
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
	 * {@inheritDoc}
	 */
	@Override
	public int getLevel()
	{
		ITreeNode<T> current = this;
		int count = 0;
		while ((current = current.getParent()) != null)
		{
			count++;
		}
		return count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITreeNode<T> getNextSibling()
	{
		final ITreeNode<T> parent = getParent();
		if (parent == null)
		{
			return null;
		}
		final int index = parent.getChildren().indexOf(this) + 1;
		if (index == parent.getChildCount())
		{
			return null;
		}
		return parent.getChildren().get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITreeNode<T> getPreviousSibling()
	{
		final ITreeNode<T> parent = getParent();
		if (parent == null)
		{
			return null;
		}
		final int index = parent.getChildren().indexOf(this) - 1;
		if (index < 0)
		{
			return null;
		}
		return parent.getChildren().get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren()
	{
		return getChildren() != null && !getChildren().isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasParent()
	{
		return parent != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLeaf()
	{
		return !isNode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNode()
	{
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRoot()
	{
		return !hasParent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeChild(final ITreeNode<T> child)
	{
		getChildren().remove(child);
		child.setParent(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeChildAt(final int index) throws IndexOutOfBoundsException
	{
		final ITreeNode<T> child = getChildren().remove(index);
		if (child != null)
		{
			child.setParent(null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setChildren(final List<ITreeNode<T>> children)
	{
		this.children = children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ITreeNode<T>> toList()
	{
		final List<ITreeNode<T>> list = new ArrayList<>();
		traverse(this, list);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void traverse(final ITreeNode<T> node, final List<ITreeNode<T>> list)
	{
		list.add(node);
		for (final ITreeNode<T> data : node.getChildren())
		{
			traverse(data, list);
		}
	}

}
