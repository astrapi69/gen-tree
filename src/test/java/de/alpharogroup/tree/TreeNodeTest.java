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
package de.alpharogroup.tree;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import de.alpharogroup.tree.ifaces.ITreeNode;

public class TreeNodeTest
{

	@Test(enabled = true)
	public void traverse()
	{
		final TreeElement parent = TreeElement.builder().name("parent").parent(null).node(false)
			.build();
		final TreeElement firstChild = TreeElement.builder().name("firstChild").parent(parent)
			.node(false).build();
		final TreeElement firstGrandChild = TreeElement.builder().name("firstGrandChild")
			.parent(firstChild).node(true).build();
		final TreeElement secondChild = TreeElement.builder().name("secondChild").parent(parent)
			.node(true).build();
		final ITreeNode<TreeElement> parentTreeNode = new TreeNode<TreeElement>(parent);
		final ITreeNode<TreeElement> firstChildTreeNode = new TreeNode<TreeElement>(firstChild);
		parentTreeNode.addChild(firstChildTreeNode);
		final ITreeNode<TreeElement> secondChildTreeNode = new TreeNode<TreeElement>(secondChild);
		parentTreeNode.addChild(secondChildTreeNode);
		final ITreeNode<TreeElement> firstGrandChildTreeNode = new TreeNode<TreeElement>(
			firstGrandChild);
		firstChildTreeNode.addChild(firstGrandChildTreeNode);
		final List<ITreeNode<TreeElement>> list = new ArrayList<>();
		parentTreeNode.traverse(parentTreeNode, list);
		System.out.println(list);
	}
}
