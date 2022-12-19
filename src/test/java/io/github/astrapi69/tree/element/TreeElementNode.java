/**
 * The MIT License
 * <p>
 * Copyright (C) 2015 Asterios Raptis
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.tree.element;

import io.github.astrapi69.tree.TreeNode;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * The concreted class {@link TreeElementNode}
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class TreeElementNode extends TreeNode<TreeElement>
{

	/**
	 * Instantiates a new {@link TreeElementNode}
	 *
	 * @param value
	 *            the value
	 */
	public TreeElementNode(final @NonNull TreeElement value)
	{
		this(value, null);
	}

	/**
	 * Instantiates a new {@link TreeElementNode}
	 *
	 * @param value
	 *            the value
	 * @param parentTreeNode
	 *            the parent tree node
	 */
	public TreeElementNode(final @NonNull TreeElement value,
		final TreeNode<TreeElement> parentTreeNode)
	{
		super(value);
		setParent(parentTreeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(final TreeElement value)
	{
		super.setValue(value);
		if (value != null)
		{
			setDisplayValue(value.getName());
		}
	}

}
