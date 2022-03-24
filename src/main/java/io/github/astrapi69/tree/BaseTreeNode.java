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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import io.github.astrapi69.design.pattern.visitor.Acceptable;
import io.github.astrapi69.design.pattern.visitor.Visitor;

/**
 * The generic class {@link BaseTreeNode} hold the children in a {@link Set}
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
	@Getter
	@Builder.Default
	Set<BaseTreeNode<T, K>> children = new LinkedHashSet<>();

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
	 * Adds the child.
	 *
	 * @param child
	 *            the child
	 */
	public void addChild(final BaseTreeNode<T, K> child)
	{
		child.setParent(this);
		getChildren().add(child);
	}

	/**
	 * Returns all siblings of this node in the parent's children list. Returns null if this node is
	 * the root.
	 *
	 * @return Returns all siblings of this node or null if this node is the root.
	 */
	public Set<BaseTreeNode<T, K>> getAllSiblings()
	{
		final BaseTreeNode<T, K> parent = getParent();
		if (parent == null)
		{
			return new LinkedHashSet<>();
		}
		final Set<BaseTreeNode<T, K>> allSiblings = new LinkedHashSet<>(parent.getChildren());
		allSiblings.remove(this);
		return allSiblings;
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
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @return the level from this node.
	 */
	public int getLevel()
	{
		BaseTreeNode<T, K> current = this;
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
	public BaseTreeNode<T, K> getNextSibling()
	{
		BaseTreeNode<T, K> next = null;
		if (getParent() == null)
		{
			return next;
		}
		boolean isNext = false;
		for (BaseTreeNode<T, K> baseTreeNode : getParent().getChildren())
		{
			if (isNext)
			{
				next = baseTreeNode;
				break;
			}
			if (baseTreeNode.equals(this))
			{
				isNext = true;
			}
		}
		return next;
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
		BaseTreeNode<T, K> previous = null;
		if (getParent() == null)
		{
			return previous;
		}
		for (BaseTreeNode<T, K> baseTreeNode : getParent().getChildren())
		{
			if (baseTreeNode.equals(this))
			{
				break;
			}
			previous = baseTreeNode;
		}
		return previous;
	}

	/**
	 * Gets the root {@link BaseTreeNode} object
	 *
	 * @return the root {@link BaseTreeNode} object
	 */
	public BaseTreeNode<T, K> getRoot()
	{
		BaseTreeNode<T, K> root = this;
		BaseTreeNode<T, K> parent = getParent();
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
	 * Checks if this {@link BaseTreeNode} object is a node
	 *
	 * @return true, if this {@link BaseTreeNode} object is a node otherwise false
	 */
	public boolean isNode()
	{
		return !isLeaf();
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
	public void removeChild(final BaseTreeNode<T, K> child)
	{
		getChildren().remove(child);
		child.setParent(null);
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link List} object
	 *
	 * @return a {@link List} object with this node and add all descendant
	 */
	public List<BaseTreeNode<T, K>> toList()
	{
		return new ArrayList<>(traverse());
	}

	/**
	 * Traverse this node and adds all descendant with this included in to a {@link Set} object
	 * 
	 * @return a {@link Set} object with this node and add all descendant
	 */
	public Set<BaseTreeNode<T, K>> traverse()
	{
		final Set<BaseTreeNode<T, K>> allTreeNodes = new LinkedHashSet<>();
		this.accept(allTreeNodes::add);
		return allTreeNodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor<BaseTreeNode<T, K>> visitor)
	{
		visitor.visit(this);
		getChildren().forEach(child -> child.accept(visitor));
	}

}
