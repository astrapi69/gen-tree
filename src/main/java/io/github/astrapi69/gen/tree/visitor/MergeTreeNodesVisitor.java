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

import lombok.NonNull;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.BaseTreeNode;

/**
 * This visitor visits all {@link BaseTreeNode} objects and merges all nodes to the given
 * {@link BaseTreeNode} object. This means only the given {@link BaseTreeNode} object will be
 * changed and the {@link BaseTreeNode} object that implements this visitor will be not changed
 *
 * @param <T>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 */
public class MergeTreeNodesVisitor<T, K> implements Visitor<BaseTreeNode<T, K>>
{
	/**
	 * The {@link BaseTreeNode} object that will be merged with the {@link BaseTreeNode} object that
	 * implements this visitor
	 */
	BaseTreeNode<T, K> mergeWith;

	/**
	 * Instantiates a new {@link MergeTreeNodesVisitor} object
	 *
	 * @param mergeWith
	 *            the {@link BaseTreeNode} object
	 */
	public MergeTreeNodesVisitor(final @NonNull BaseTreeNode<T, K> mergeWith)
	{
		this.mergeWith = mergeWith;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(BaseTreeNode<T, K> treeNode)
	{
		BaseTreeNode<T, K> byId = this.mergeWith.findById(treeNode.getId());
		if (byId != null)
		{
			BaseTreeNode<T, K> parent = byId.getParent();
			if (parent != null && !parent.getChildren().contains(treeNode))
			{
				parent.addChild(treeNode);
			}
		}
	}

}
