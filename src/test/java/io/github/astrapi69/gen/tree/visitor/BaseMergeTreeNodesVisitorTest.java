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
import io.github.astrapi69.gen.tree.BaseTreeNodeTestTree;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import io.github.astrapi69.gen.tree.enumeration.merge.MergeStrategy;

/**
 * The unit test class for the class {@link BaseMergeTreeNodesVisitor}
 * <p>
 * Note: every test method that merges into a base tree builds its own fresh
 * {@link BaseTreeNodeTestTree} object instead of reusing
 * {@link BaseTreeNodeTestData#getBaseTestTree()}, since that method always returns the very same
 * shared, mutable tree object; merging into it here would otherwise leak mutations into other test
 * classes that rely on that shared tree being in its pristine state
 */
public class BaseMergeTreeNodesVisitorTest
{

	/**
	 * Test method for {@link BaseMergeTreeNodesVisitor#visit(IBaseTreeNode)} with the
	 * {@link MergeStrategy#OVERWRITE} strategy
	 */
	@Test
	public void testVisitWithOverwriteStrategy()
	{
		BaseTreeNode<String, Long> baseTestTree;
		BaseTreeNode<String, Long> root;
		BaseMergeTreeNodesVisitor<String, Long, BaseTreeNode<String, Long>> mergeTreeNodesVisitor;
		Collection<BaseTreeNode<String, Long>> allTreeNodes;

		root = BaseTreeNodeTestData.getSimpleTestTree();

		baseTestTree = new BaseTreeNodeTestTree().getRoot();
		allTreeNodes = baseTestTree.traverse();
		assertEquals(allTreeNodes.size(), 12);

		mergeTreeNodesVisitor = new BaseMergeTreeNodesVisitor<>(baseTestTree,
			MergeStrategy.OVERWRITE);

		root.accept(mergeTreeNodesVisitor);
		allTreeNodes = baseTestTree.traverse();
		assertEquals(allTreeNodes.size(), 15);
	}

	/**
	 * Test method for {@link BaseMergeTreeNodesVisitor#visit(IBaseTreeNode)} that verifies that
	 * merging twice with the {@link MergeStrategy#OVERWRITE} strategy does not lead to duplicate
	 * children, since the target children collection is a {@link java.util.Set} object
	 */
	@Test
	public void testVisitWithOverwriteStrategyAppliedTwice()
	{
		BaseTreeNode<String, Long> baseTestTree;
		BaseTreeNode<String, Long> root;
		BaseMergeTreeNodesVisitor<String, Long, BaseTreeNode<String, Long>> mergeTreeNodesVisitor;
		Collection<BaseTreeNode<String, Long>> allTreeNodes;

		root = BaseTreeNodeTestData.getSimpleTestTree();
		baseTestTree = new BaseTreeNodeTestTree().getRoot();

		mergeTreeNodesVisitor = new BaseMergeTreeNodesVisitor<>(baseTestTree,
			MergeStrategy.OVERWRITE);

		root.accept(mergeTreeNodesVisitor);
		allTreeNodes = baseTestTree.traverse();
		assertEquals(allTreeNodes.size(), 15);

		// applying the same merge a second time must not duplicate any children
		root.accept(mergeTreeNodesVisitor);
		allTreeNodes = baseTestTree.traverse();
		assertEquals(allTreeNodes.size(), 15);
	}

	/**
	 * Test method for {@link BaseMergeTreeNodesVisitor#visit(IBaseTreeNode)} for the case that the
	 * visited tree node cannot be found by id in the tree node to merge with
	 */
	@Test
	public void testVisitWhenTreeNodeNotFoundById()
	{
		BaseTreeNode<String, Long> baseTestTree;
		BaseTreeNode<String, Long> unrelatedTreeNode;
		BaseMergeTreeNodesVisitor<String, Long, BaseTreeNode<String, Long>> mergeTreeNodesVisitor;
		int sizeBefore;
		int sizeAfter;

		baseTestTree = new BaseTreeNodeTestTree().getRoot();
		sizeBefore = baseTestTree.traverse().size();

		unrelatedTreeNode = BaseTreeNode.<String, Long> builder().id(999L)
			.value("not part of the tree to merge with").build();

		mergeTreeNodesVisitor = new BaseMergeTreeNodesVisitor<>(baseTestTree, MergeStrategy.KEEP);

		mergeTreeNodesVisitor.visit(unrelatedTreeNode);

		sizeAfter = baseTestTree.traverse().size();
		assertEquals(sizeBefore, sizeAfter);
	}

	/**
	 * Test method for
	 * {@link BaseMergeTreeNodesVisitor#BaseMergeTreeNodesVisitor(IBaseTreeNode, MergeStrategy)}
	 * with a null mergeWith argument
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testConstructorWithNullMergeWith()
	{
		BaseTreeNode<String, Long> mergeWith = null;

		new BaseMergeTreeNodesVisitor<>(mergeWith, MergeStrategy.KEEP);
	}

	/**
	 * Test method for
	 * {@link BaseMergeTreeNodesVisitor#BaseMergeTreeNodesVisitor(IBaseTreeNode, MergeStrategy)}
	 * with a null mergeStrategy argument
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testConstructorWithNullMergeStrategy()
	{
		BaseTreeNode<String, Long> mergeWith = BaseTreeNode.<String, Long> builder().build();

		new BaseMergeTreeNodesVisitor<>(mergeWith, null);
	}

}
