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
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The generic class {@link BaseTreeNode} have a generic id and value object
 *
 * @param <V>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id from the node
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "children", "parent" })
@ToString(exclude = { "children", "parent" })
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseTreeNode<V, K> implements IBaseTreeNode<V, K, BaseTreeNode<V, K>>
{

	/** The id from this node */
	K id;

	/** The value */
	V value;

	/** The comparator object for sort the children */
	Comparator<BaseTreeNode<V, K>> childComparator;

	/** The children */
	Collection<BaseTreeNode<V, K>> children;

	/** The optional display value */
	String displayValue;

	/** The parent from this node. If this is null, it is the root */
	BaseTreeNode<V, K> parent;

	/** The flag that indicates if this tree node is a leaf or a node */
	boolean leaf;

	/**
	 * Instantiates a new {@link BaseTreeNode} object
	 *
	 * @param value
	 *            the value
	 */
	public BaseTreeNode(final V value)
	{
		setValue(value);
	}

	/**
	 * Gets the children of this node
	 *
	 * @return the children
	 */
	public Collection<BaseTreeNode<V, K>> getChildren()
	{
		if (this.children == null)
		{
			if (this.childComparator == null)
			{
				this.children = new LinkedHashSet<>();
			}
			else
			{
				this.children = new TreeSet<>(this.childComparator);
			}
		}
		return this.children;
	}

	/**
	 * Sorts the children collection if the comparator is not null
	 */
	public void sortChildren()
	{
		if (this.childComparator != null)
		{
			this.children = this.children.stream().sorted(this.childComparator)
				.collect(Collectors.toCollection(TreeSet::new));
		}
	}
}
