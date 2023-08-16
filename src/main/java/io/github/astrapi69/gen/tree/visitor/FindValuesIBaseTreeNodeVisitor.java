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
package io.github.astrapi69.gen.tree.visitor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicReference;

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import lombok.Getter;

/**
 * This visitor visits all {@link IBaseTreeNode} objects and checks if the value equals with the
 * value of this visitor
 *
 * @param <T>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id from the node
 */
public class FindValuesIBaseTreeNodeVisitor<V, K, T extends IBaseTreeNode<V, K, T>>
	implements
		Visitor<T>
{
	/**
	 * The decorator {@link AtomicReference} object for store all {@link BaseTreeNode} objects
	 */
	@Getter
	private final AtomicReference<Collection<T>> foundTreeNodes = new AtomicReference<>(
		new LinkedHashSet<>());

	/* The value */
	@Getter
	private final V value;

	/**
	 * Instantiates a new {@link FindValuesIBaseTreeNodeVisitor} object
	 *
	 * @param value
	 *            the value
	 */
	public FindValuesIBaseTreeNodeVisitor(final V value)
	{
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(T treeNode)
	{
		if (this.value == null)
		{
			if (treeNode != null && treeNode.getValue() == null)
			{
				foundTreeNodes.get().add(treeNode);
			}
		}
		else
		{
			if (this.value.equals(treeNode.getValue()))
			{
				foundTreeNodes.get().add(treeNode);
			}
		}
	}
}
