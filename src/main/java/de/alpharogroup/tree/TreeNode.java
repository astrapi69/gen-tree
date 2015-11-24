/**
 * The MIT License
 *
 * Copyright (C) 2007 Asterios Raptis
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
import java.util.Stack;

import de.alpharogroup.tree.ifaces.ITreeNode;

/**
 * The generic class TreeNode.
 * 
 * @param <T>
 *            the generic type
 */
public class TreeNode<T> implements ITreeNode<T>
{

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The children. */
	private List<ITreeNode<T>> children;

	/** The parent from this node. If this is null it is the root. */
	private ITreeNode<T> parent;

	/** The value. */
	private T value;

	/** The optional display value. */
	private String displayValue;

	/**
	 * Instantiates a new tree node.
	 */
	public TreeNode()
	{
	}

	/**
	 * Instantiates a new tree node.
	 * 
	 * @param value
	 *            the value
	 */
	public TreeNode(final T value)
	{
		this();
		setValue(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(final ITreeNode<T> child)
	{
		if (children != null)
		{
			children.add(child);
		}
		else
		{
			children = new ArrayList<>();
			children.add(child);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChildAt(final int index, final ITreeNode<T> child)
		throws IndexOutOfBoundsException
	{
		if (children != null && children.size() < index)
		{
			children.add(index, child);
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
	public boolean equals(final ITreeNode<T> treeNode)
	{
		return treeNode.getValue().equals(treeNode);
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
		if (children == null)
		{
			return 0;
		}
		return children.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ITreeNode<T>> getChildren()
	{
		if (this.children == null)
		{
			return new ArrayList<>();
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
		final Stack<Integer> stack = new Stack<>();
		stack.push(0);
		ITreeNode<T> node = getChildren().get(0);
		int depth = 0;
		int current = 1;
		while (!stack.empty())
		{
			if (node.getChildCount() != 0)
			{
				node = getChildren().get(0);
				stack.push(0);
				current++;
			}
			else
			{
				if (current > depth)
				{
					depth = current;
				}
				int size;
				int index;
				do
				{
					node = node.getParent();
					size = node.getChildCount();
					index = stack.pop() + 1;
					current--;
				}
				while (index >= size && node != this);

				if (index < size)
				{
					node = getChildren().get(index);
					stack.push(index);
					current++;
				}
			}
		}
		return depth;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayValue()
	{
		return displayValue;
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
	public ITreeNode<T> getParent()
	{
		return parent;
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
	public T getValue()
	{
		return value;
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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (children == null ? 0 : children.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
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
		if (children != null)
		{
			children.remove(child);
		}
		else
		{
			children = new ArrayList<>();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeChildAt(final int index) throws IndexOutOfBoundsException
	{
		children.remove(index);
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
	public void setDisplayValue(final String displayValue)
	{
		this.displayValue = displayValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setParent(final ITreeNode<T> parent)
	{
		this.parent = parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(final T value)
	{
		this.value = value;
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
