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
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertSame;

import java.util.Collection;

import org.testng.annotations.DataProvider;
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
 * {@link BaseTreeNodeTestTree} object, so that no merge can leak into another test
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
	 * The merge strategies, one row per strategy
	 *
	 * @return the rows
	 */
	@DataProvider
	public static Object[][] mergeStrategies()
	{
		return new Object[][] { { MergeStrategy.OVERWRITE }, { MergeStrategy.KEEP } };
	}

	/**
	 * Test method for {@link BaseMergeTreeNodesVisitor#visit(IBaseTreeNode)} with every merge
	 * strategy for a visited tree node whose id belongs to a node under a different parent in the
	 * tree node to merge with: it is not attached there
	 *
	 * @param mergeStrategy
	 *            the merge strategy
	 */
	@Test(dataProvider = "mergeStrategies")
	public void testVisitDoesNotAttachUnderADifferentParent(final MergeStrategy mergeStrategy)
	{
		BaseTreeNodeTestTree baseTestTree;
		BaseTreeNode<String, Long> root;
		BaseTreeNode<String, Long> sixthGrandChild;

		root = BaseTreeNodeTestData.getSimpleTestTree();
		baseTestTree = new BaseTreeNodeTestTree();
		// id 3 is the sixth grand child under the second child (id 1) here, and the first grand
		// child under the second child (id 2) in the base test tree
		sixthGrandChild = root.findById(3L);

		root.accept(new BaseMergeTreeNodesVisitor<>(baseTestTree.getRoot(), mergeStrategy));

		assertFalse(baseTestTree.getSecondChild().getChildren().contains(sixthGrandChild));
		assertSame(root.findById(1L), sixthGrandChild.getParent());
	}

	/**
	 * Test method for {@link BaseMergeTreeNodesVisitor#visit(IBaseTreeNode)} with the
	 * {@link MergeStrategy#KEEP} strategy for visited tree nodes that already have an equal node at
	 * the same place in the tree node to merge with: KEEP keeps the existing node and ignores the
	 * new one, so the new one stays in its own tree, under its own parent
	 */
	@Test
	public void testVisitWithKeepStrategyIgnoresNodesThatAreAlreadyThere()
	{
		BaseTreeNode<String, Long> mergeWith;
		BaseTreeNode<String, Long> root;
		BaseTreeNode<String, Long> secondChild;
		BaseTreeNode<String, Long> sixthGrandChild;

		mergeWith = BaseTreeNodeTestData.getSimpleTestTree();
		root = BaseTreeNodeTestData.getSimpleTestTree();
		secondChild = root.findById(1L);
		sixthGrandChild = root.findById(3L);

		root.accept(new BaseMergeTreeNodesVisitor<>(mergeWith, MergeStrategy.KEEP));

		assertSame(root, secondChild.getParent());
		assertSame(secondChild, sixthGrandChild.getParent());
		assertEquals(4, mergeWith.traverse().size());
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
