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
import static org.testng.AssertJUnit.assertTrue;

import java.util.Collection;

import org.testng.annotations.Test;

import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.BaseTreeNodeTestData;

/**
 * The unit test class for the class {@link FindValuesIBaseTreeNodeVisitor}
 */
public class FindValuesIBaseTreeNodeVisitorTest
{

	/**
	 * Test method for
	 * {@link FindValuesIBaseTreeNodeVisitor#visit(io.github.astrapi69.gen.tree.api.IBaseTreeNode)}
	 * with a non null value to search for
	 */
	@Test
	public void testVisitWithNonNullValue()
	{
		BaseTreeNode<String, Long> root;
		FindValuesIBaseTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>> visitor;
		Collection<BaseTreeNode<String, Long>> foundTreeNodes;

		root = BaseTreeNodeTestData.getBaseTestTree();
		visitor = new FindValuesIBaseTreeNodeVisitor<>("I'm the fifth grand child");

		root.accept(visitor);

		assertEquals(visitor.getValue(), "I'm the fifth grand child");
		foundTreeNodes = visitor.getFoundTreeNodes().get();
		assertEquals(1, foundTreeNodes.size());
	}

	/**
	 * Test method for
	 * {@link FindValuesIBaseTreeNodeVisitor#visit(io.github.astrapi69.gen.tree.api.IBaseTreeNode)}
	 * with a value that does not match any node in the tree
	 */
	@Test
	public void testVisitWithNonNullValueNotFound()
	{
		BaseTreeNode<String, Long> root;
		FindValuesIBaseTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>> visitor;

		root = BaseTreeNodeTestData.getBaseTestTree();
		visitor = new FindValuesIBaseTreeNodeVisitor<>("not existing in the tree");

		root.accept(visitor);

		assertTrue(visitor.getFoundTreeNodes().get().isEmpty());
	}

	/**
	 * Test method for
	 * {@link FindValuesIBaseTreeNodeVisitor#visit(io.github.astrapi69.gen.tree.api.IBaseTreeNode)}
	 * with a null value to search for
	 */
	@Test
	public void testVisitWithNullValue()
	{
		BaseTreeNode<String, Long> root;
		FindValuesIBaseTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>> visitor;
		Collection<BaseTreeNode<String, Long>> foundTreeNodes;

		root = BaseTreeNodeTestData.getBaseTestTree();
		visitor = new FindValuesIBaseTreeNodeVisitor<>(null);

		root.accept(visitor);

		// thirdGrandChild and fourthGrandChild both have a null value
		foundTreeNodes = visitor.getFoundTreeNodes().get();
		assertEquals(2, foundTreeNodes.size());
	}

	/**
	 * Test method for
	 * {@link FindValuesIBaseTreeNodeVisitor#visit(io.github.astrapi69.gen.tree.api.IBaseTreeNode)}
	 * called directly with a null tree node while searching for a null value
	 */
	@Test
	public void testVisitWithNullValueAndNullTreeNode()
	{
		FindValuesIBaseTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>> visitor;

		visitor = new FindValuesIBaseTreeNodeVisitor<>(null);

		visitor.visit(null);

		assertTrue(visitor.getFoundTreeNodes().get().isEmpty());
	}

	/**
	 * Test method for
	 * {@link FindValuesIBaseTreeNodeVisitor#visit(io.github.astrapi69.gen.tree.api.IBaseTreeNode)}
	 * called directly with a tree node that has a non null value while searching for a null value
	 */
	@Test
	public void testVisitWithNullValueAndNonMatchingTreeNode()
	{
		FindValuesIBaseTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>> visitor;
		BaseTreeNode<String, Long> treeNode;

		visitor = new FindValuesIBaseTreeNodeVisitor<>(null);
		treeNode = BaseTreeNode.<String, Long> builder().value("not null").build();

		visitor.visit(treeNode);

		assertTrue(visitor.getFoundTreeNodes().get().isEmpty());
	}

}
