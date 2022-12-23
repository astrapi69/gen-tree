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
package io.github.astrapi69.tree.convert;

import io.github.astrapi69.data.identifiable.IdGenerator;
import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.tree.element.GenericTreeElement;
import io.github.astrapi69.tree.element.TreeElement;
import lombok.NonNull;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Factory class for generate {@link DefaultMutableTreeNode} from {@link BaseTreeNode}
 */
public class BaseTreeNodeFactory
{

	/**
	 * Factory method that creates a new {@link BaseTreeNode} object from the given
	 * {@link TreeElement} object
	 *
	 * @param treeElement
	 *            the {@link TreeElement} object
	 * @param parentTreeNode
	 *            the parent object
	 * @return the new {@link BaseTreeNode} object
	 */
	public static <T, K> BaseTreeNode<T, K> initializeBaseTreeNodeWithTreeElement(
		final T treeElement, BaseTreeNode<T, K> parentTreeNode,
		final @NonNull IdGenerator<K> idGenerator)
	{
		K nextId = idGenerator.getNextId();
		BaseTreeNode<T, K> treeNode = BaseTreeNode.<T, K> builder().id(nextId).value(treeElement)
			.build();
		if (parentTreeNode != null)
		{
			parentTreeNode.addChild(treeNode);
		}
		return treeNode;
	}

	/**
	 * Factory method that creates a new {@link BaseTreeNode} object from the given
	 * {@link TreeElement} object
	 *
	 * @param treeElement
	 *            the {@link TreeElement} object
	 * @param parentTreeNode
	 *            the parent object
	 * @return the new {@link BaseTreeNode} object
	 */
	public static <T, K> BaseTreeNode<GenericTreeElement<T>, K> initializeTreeNodeWithTreeElement(
		final GenericTreeElement<T> treeElement,
		BaseTreeNode<GenericTreeElement<T>, K> parentTreeNode,
		final @NonNull IdGenerator<K> idGenerator)
	{
		BaseTreeNode<GenericTreeElement<T>, K> treeNode = initializeBaseTreeNodeWithTreeElement(
			treeElement, parentTreeNode, idGenerator);
		treeNode.setLeaf(treeElement.isLeaf());
		treeNode.setDisplayValue(treeElement.getName());
		return treeNode;
	}

}
