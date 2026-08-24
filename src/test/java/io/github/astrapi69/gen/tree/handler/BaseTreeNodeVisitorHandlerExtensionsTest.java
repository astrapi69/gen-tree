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
package io.github.astrapi69.gen.tree.handler;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Collection;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.visitor.ReindexTreeNodeVisitor;
import io.github.astrapi69.gen.tree.visitor.TraverseTreeNodeVisitor;
import io.github.astrapi69.id.generate.LongIdGenerator;

/**
 * The unit test class for the class {@link BaseTreeNodeVisitorHandlerExtensions}
 */
public class BaseTreeNodeVisitorHandlerExtensionsTest
{

	BaseTreeNode<String, Long> root;
	BaseTreeNode<String, Long> firstChild;
	BaseTreeNode<String, Long> secondChild;
	BaseTreeNode<String, Long> firstGrandChild;
	BaseTreeNode<String, Long> firstGrandGrandChild;
	BaseTreeNode<String, Long> secondGrandGrandChild;
	BaseTreeNode<String, Long> firstGrandGrandGrandChild;
	BaseTreeNode<String, Long> secondGrandChild;
	BaseTreeNode<String, Long> thirdGrandChild;
	BaseTreeNode<String, Long> thirdChild;
	BaseTreeNode<String, Long> fourthGrandChild;
	BaseTreeNode<String, Long> fifthGrandChild;
	String fifthGrandChildValue;
	LongIdGenerator idGenerator;

	/**
	 * Set up the tree structure for the unit tests
	 *
	 * <pre>
	 *   +- root("I'm root")
	 *      +- firstChild("I'm the first child")
	 *      +- secondChild("I'm the second child")
	 *      |  +- firstGrandChild("I'm the first grand child")
	 *      |  |  +- firstGrandGrandChild("I'm the first grand grand child")
	 *      |  |  +- secondGrandGrandChild("I'm the second grand grand child)
	 *      |  |  |  +- firstGrandGrandGrandChild ("I'm the first grand grand grand child")
	 *      |  +- secondGrandChild("I'm the second grand child")
	 *      |  +- thirdGrandChild(null)
	 *      +- thirdChild("I'm the third child")
	 *      |  +- fourthGrandChild(null)
	 *      |  +- fifthGrandChild("I'm the fifth grand child")
	 * </pre>
	 */
	@BeforeMethod
	public void setup()
	{
		idGenerator = LongIdGenerator.of(0L);
		root = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).value("I'm root")
			.build();

		firstChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the first child").build();

		secondChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the second child").build();

		firstGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(secondChild).value("I'm the first grand child").build();

		firstGrandGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(firstGrandChild).value("I'm the first grand grand child").build();

		secondGrandGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(firstGrandChild).value("I'm the second grand grand child").build();

		firstGrandGrandGrandChild = BaseTreeNode.<String, Long> builder()
			.id(idGenerator.getNextId()).parent(secondGrandGrandChild)
			.value("I'm the first grand grand grand child").build();

		secondGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(secondChild).value("I'm the second grand child").build();

		thirdGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(secondChild).value(null).build();

		thirdChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the third child").build();

		fourthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(thirdChild).value(null).build();
		fifthGrandChildValue = "I'm the fifth grand child";
		fifthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(thirdChild).leaf(true).value(fifthGrandChildValue).build();

		// initialize all children
		root.addChild(firstChild);
		root.addChild(secondChild);
		root.addChild(thirdChild);

		secondChild.addChild(firstGrandChild);

		firstGrandChild.addChild(firstGrandGrandChild);
		firstGrandChild.addChild(secondGrandGrandChild);

		secondGrandGrandChild.addChild(firstGrandGrandGrandChild);

		secondChild.addChild(secondGrandChild);
		secondChild.addChild(thirdGrandChild);

		thirdChild.addChild(fourthGrandChild);
		thirdChild.addChild(fifthGrandChild);
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#traverse(io.github.astrapi69.gen.tree.api.IBaseTreeNode)}
	 */
	@Test
	public void testTraverse()
	{
		Collection<BaseTreeNode<String, Long>> subtree;

		subtree = BaseTreeNodeVisitorHandlerExtensions.traverse(root);
		assertEquals(12, subtree.size());
		assertTrue(subtree.contains(root));
		assertTrue(subtree.contains(fifthGrandChild));

		subtree = BaseTreeNodeVisitorHandlerExtensions.traverse(fifthGrandChild);
		assertEquals(1, subtree.size());
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#traverse(io.github.astrapi69.gen.tree.api.IBaseTreeNode)}
	 * with a null tree node
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testTraverseWithNullTreeNode()
	{
		BaseTreeNodeVisitorHandlerExtensions.traverse(null);
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#accept(io.github.astrapi69.gen.tree.api.IBaseTreeNode, io.github.astrapi69.design.pattern.visitor.Visitor)}
	 */
	@Test
	public void testAcceptTwoArg()
	{
		TraverseTreeNodeVisitor<String, BaseTreeNode<String, Long>> visitor;

		visitor = new TraverseTreeNodeVisitor<>();
		BaseTreeNodeVisitorHandlerExtensions.accept(root, visitor);
		assertEquals(12, visitor.getAllTreeNodes().size());
		assertTrue(visitor.getAllTreeNodes().contains(root));
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#accept(io.github.astrapi69.gen.tree.api.IBaseTreeNode, io.github.astrapi69.design.pattern.visitor.Visitor)}
	 * that verifies that the two argument overload delegates with a visit-after (post order)
	 * traversal, exactly like calling the three argument overload with {@code visitBefore=false}
	 */
	@Test
	public void testAcceptTwoArgIsPostOrder()
	{
		ReindexTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>> reindexTreeNodeVisitor;

		reindexTreeNodeVisitor = new ReindexTreeNodeVisitor<>(LongIdGenerator.of(200L));
		BaseTreeNodeVisitorHandlerExtensions.accept(root, reindexTreeNodeVisitor);
		// post order visits the root last, so with 12 nodes and ids starting at 200 the root gets
		// the very last generated id: 200 + 11 = 211
		assertEquals(Long.valueOf(211L), root.getId());
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#accept(io.github.astrapi69.gen.tree.api.IBaseTreeNode, io.github.astrapi69.design.pattern.visitor.Visitor)}
	 * with a null tree node
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testAcceptTwoArgWithNullTreeNode()
	{
		TraverseTreeNodeVisitor<String, BaseTreeNode<String, Long>> visitor;

		visitor = new TraverseTreeNodeVisitor<>();
		BaseTreeNodeVisitorHandlerExtensions.accept(null, visitor);
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#accept(io.github.astrapi69.gen.tree.api.IBaseTreeNode, io.github.astrapi69.design.pattern.visitor.Visitor)}
	 * with a null visitor
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testAcceptTwoArgWithNullVisitor()
	{
		BaseTreeNodeVisitorHandlerExtensions.accept(root, null);
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#accept(io.github.astrapi69.gen.tree.api.IBaseTreeNode, io.github.astrapi69.design.pattern.visitor.Visitor, boolean)}
	 * with a null tree node
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testAcceptThreeArgWithNullTreeNode()
	{
		TraverseTreeNodeVisitor<String, BaseTreeNode<String, Long>> visitor;

		visitor = new TraverseTreeNodeVisitor<>();
		BaseTreeNodeVisitorHandlerExtensions.accept(null, visitor, true);
	}

	/**
	 * Test method for
	 * {@link BaseTreeNodeVisitorHandlerExtensions#accept(io.github.astrapi69.gen.tree.api.IBaseTreeNode, io.github.astrapi69.design.pattern.visitor.Visitor, boolean)}
	 * with a null visitor
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testAcceptThreeArgWithNullVisitor()
	{
		BaseTreeNodeVisitorHandlerExtensions.accept(root, null, true);
	}

}
