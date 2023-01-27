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

import static org.testng.AssertJUnit.assertEquals;

import java.util.Collection;

import org.testng.annotations.Test;

import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.BaseTreeNodeTestData;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import io.github.astrapi69.gen.tree.handler.ITreeNodeHandlerExtensions;

/**
 * The unit test class for the class {@link MergeTreeNodesVisitor}
 */
public class MergeTreeNodesVisitorTest
{

	/**
	 * Test method for {@link MergeTreeNodesVisitor#visit(IBaseTreeNode)}
	 */
	@Test
	public void test()
	{
		BaseTreeNode<String, Long> baseTestTree;
		BaseTreeNode<String, Long> root;
		MergeTreeNodesVisitor<String, Long, BaseTreeNode<String, Long>> mergeTreeNodesVisitor;
		Collection<BaseTreeNode<String, Long>> allTreeNodes;

		root = BaseTreeNodeTestData.getSimpleTestTree();

		baseTestTree = BaseTreeNodeTestData.getBaseTestTree();
		allTreeNodes = ITreeNodeHandlerExtensions.traverse(baseTestTree);
		assertEquals(allTreeNodes.size(), 12);

		mergeTreeNodesVisitor = new MergeTreeNodesVisitor<>(baseTestTree);

		root.accept(mergeTreeNodesVisitor);
		allTreeNodes = ITreeNodeHandlerExtensions.traverse(baseTestTree);
		assertEquals(allTreeNodes.size(), 15);
	}

}
