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

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.TreeSet;

/**
 * The generic class {@link TreeIdNode} keeps no references to the parent or the children, only the
 * id's are kept.
 *
 * @param <T>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 */
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreeIdNode<T, K>
{

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The id from this node */
	@Getter
	@Setter
	K id;

	/** The parent id from this node. If parentId is null this tree node is the root */
	@Getter
	@Setter
	K parentId;

	/** All ids from the children */
	@Getter
	@Setter
	@Builder.Default
	Set<K> childrenIds = new TreeSet<>();

	/** The optional display value */
	@Getter
	@Setter
	String displayValue;

	/** The value */
	@Getter
	@Setter
	T value;

	/** The flag that indicates if this tree node is a leaf or a node */
	@Getter
	@Setter
	boolean leaf;

	/**
	 * Instantiates a new {@link TreeIdNode} object
	 *
	 * @param value
	 *            the value
	 */
	public TreeIdNode(final T value)
	{
		setValue(value);
	}

	/**
	 * Adds the child.
	 *
	 * @param child
	 *            the child
	 */
	public void addChild(final TreeIdNode<T, K> child)
	{
		if (child != null && this.isNode())
		{
			child.setParentId(this.id);
			this.getChildrenIds().add(child.getId());
		}
	}

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	public int getChildCount()
	{
		return this.getChildrenIds().size();
	}

	/**
	 * Checks for children.
	 *
	 * @return true, if successful
	 */
	public boolean hasChildren()
	{
		return this.getChildrenIds() != null && !this.getChildrenIds().isEmpty();
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
	 * Checks if this {@link TreeIdNode} object is a node
	 *
	 * @return true, if this {@link TreeIdNode} object is a node otherwise false
	 */
	public boolean isNode()
	{
		return !isLeaf();
	}

	/**
	 * Checks if this {@link TreeIdNode} is the root {@link TreeIdNode} object
	 *
	 * @return true, if this {@link TreeIdNode} is the root {@link TreeIdNode} object
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
	public void removeChild(final TreeIdNode<T, K> child)
	{
		this.getChildrenIds().remove(child.getId());
		child.setParentId(null);
	}

}
