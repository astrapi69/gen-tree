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

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import io.github.astrapi69.gen.tree.merge.enumeration.MergeStrategy;

/**
 * This visitor visits all {@link IBaseTreeNode} objects and merges all nodes to the given
 * {@link IBaseTreeNode} object. This means only the given {@link IBaseTreeNode} object will be
 * changed and the {@link IBaseTreeNode} object that implements this visitor will be not changed
 *
 * @param <T>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BaseMergeTreeNodesVisitor<V, K, T extends IBaseTreeNode<V, K, T>> implements Visitor<T>
{

	/**
	 * The {@link IBaseTreeNode} object that will be merged with the {@link IBaseTreeNode} object
	 * that implements this visitor
	 */
	T mergeWith;

	MergeStrategy mergeStrategy;

	/**
	 * Instantiates a new {@link BaseMergeTreeNodesVisitor} object
	 *
	 * @param mergeWith
	 *            the {@link IBaseTreeNode} object
	 * @param mergeStrategy
	 *            the {@link MergeStrategy} object
	 */
	public BaseMergeTreeNodesVisitor(final @NonNull T mergeWith,
		final @NonNull MergeStrategy mergeStrategy)
	{
		this.mergeWith = mergeWith;
		this.mergeStrategy = mergeStrategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(T treeNode)
	{
		final T byId = this.mergeWith.findById(treeNode.getId());
		if (byId != null)
		{
			final T parent = byId.getParent();
			final T treeNodeParent = treeNode.getParent();
			switch (mergeStrategy)
			{
				case OVERWRITE :
					if (parent != null && parent.equals(treeNodeParent))
					{
						parent.addChild(treeNode);
					}
					break;
				default :
					if (parent != null && parent.equals(treeNodeParent)
						&& !parent.getChildren().contains(treeNode))
					{
						parent.addChild(treeNode);
					}
			}
		}
	}

}