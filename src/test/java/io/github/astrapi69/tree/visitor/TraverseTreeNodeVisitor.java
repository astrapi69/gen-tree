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
package io.github.astrapi69.tree.visitor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import io.github.astrapi69.tree.api.ITreeNode;
import lombok.Getter;
import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.tree.SimpleTreeNode;

/**
 * This visitor visits all {@link ITreeNode} objects and adds them to a {@link Collection} object
 * with all descendant
 *
 * @param <T>
 *            the generic type of the value
 */
public class TraverseTreeNodeVisitor<T, K extends ITreeNode<T, K>> implements Visitor<K>
{
	/**
	 * a {@link Collection} object for store all {@link SimpleTreeNode} objects
	 */
	@Getter
	private final Collection<K> allTreeNodes = new LinkedHashSet<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(K simpleTreeNode)
	{
		allTreeNodes.add(simpleTreeNode);
	}
}
