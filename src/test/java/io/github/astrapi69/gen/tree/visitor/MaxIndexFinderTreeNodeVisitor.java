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

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.SimpleTreeNode;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import io.github.astrapi69.gen.tree.api.ITreeNode;
import io.github.astrapi69.gen.tree.merge.enumeration.MergeStrategy;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * This visitor visits all {@link ITreeNode} objects and adds them to a {@link Collection} object
 * with all descendant
 *
 * @param <T>
 *            the generic type of the value
 */
@Getter
public abstract class MaxIndexFinderTreeNodeVisitor<V, K, T extends IBaseTreeNode<V, K, T>>
	implements
		Visitor<T>
{
	K maxIndex;

	/**
	 * Instantiates a new {@link MergeTreeNodesVisitor} object
	 *
	 * @param root
	 *            the {@link IBaseTreeNode} object
	 */
	public MaxIndexFinderTreeNodeVisitor(final @NonNull T root)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(T treeNode)
	{
		K id = treeNode.getId();
		if (maxIndex == null)
		{
			maxIndex = id;
		}
		if (isGreater(id))
		{
			maxIndex = id;
		}
	}

	abstract boolean isGreater(K id);
}
