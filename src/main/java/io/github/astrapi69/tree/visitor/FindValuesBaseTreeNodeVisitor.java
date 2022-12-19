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
package io.github.astrapi69.tree.visitor;

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.tree.BaseTreeNode;
import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This visitor visits all {@link BaseTreeNode} objects and checks if the value equals with the
 * value of this visitor
 *
 * @param <T>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 */
public class FindValuesBaseTreeNodeVisitor<T, K> implements Visitor<BaseTreeNode<T, K>>
{
	/**
	 * The decorator {@link AtomicReference} object for store all {@link BaseTreeNode} objects
	 */
	@Getter
	private final AtomicReference<Collection<BaseTreeNode<T, K>>> foundTreeNodes = new AtomicReference<>(
		new LinkedHashSet<>());

	/* The value */
	@Getter
	private final T value;

	/**
	 * Instantiates a new {@link FindValuesBaseTreeNodeVisitor} object
	 *
	 * @param value
	 *            the value
	 */
	public FindValuesBaseTreeNodeVisitor(final T value)
	{
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(BaseTreeNode<T, K> treeNode)
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
