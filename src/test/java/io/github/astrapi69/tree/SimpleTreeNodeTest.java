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
package io.github.astrapi69.tree;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Set;

import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.id.generate.LongIdGenerator;
import io.github.astrapi69.tree.visitor.DisplayValueOfSimpleTreeNodeVisitor;
import io.github.astrapi69.tree.visitor.TraverseSimpleTreeNodeVisitor;

/**
 * The unit test class for the class {@link SimpleTreeNode}
 */
public class SimpleTreeNodeTest
{
	SimpleTreeNode<String, Long> root;
	SimpleTreeNode<String, Long> firstChild;
	SimpleTreeNode<String, Long> secondChild;
	SimpleTreeNode<String, Long> firstGrandChild;
	SimpleTreeNode<String, Long> firstGrandGrandChild;
	SimpleTreeNode<String, Long> secondGrandGrandChild;
	SimpleTreeNode<String, Long> firstGrandGrandGrandChild;
	SimpleTreeNode<String, Long> secondGrandChild;
	SimpleTreeNode<String, Long> thirdGrandChild;
	SimpleTreeNode<String, Long> thirdChild;
	SimpleTreeNode<String, Long> fourthGrandChild;
	SimpleTreeNode<String, Long> fifthGrandChild;

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
		LongIdGenerator idGenerator = LongIdGenerator.of(0L);
		root = SimpleTreeNode.<String, Long> builder().leftMostChild(firstChild).value("I'm root")
			.id(idGenerator.getNextId()).build();

		firstChild = SimpleTreeNode.<String, Long> builder().parent(root).rightSibling(secondChild)
			.id(idGenerator.getNextId()).value("I'm the first child").build();

		secondChild = SimpleTreeNode.<String, Long> builder().parent(root)
			.id(idGenerator.getNextId()).leftMostChild(firstGrandChild).rightSibling(thirdChild)
			.value("I'm the second child").build();

		firstGrandChild = SimpleTreeNode.<String, Long> builder().parent(secondChild)
			.id(idGenerator.getNextId()).leftMostChild(firstGrandGrandChild)
			.rightSibling(secondGrandChild).value("I'm the first grand child").build();

		firstGrandGrandChild = SimpleTreeNode.<String, Long> builder().parent(firstGrandChild)
			.id(idGenerator.getNextId()).rightSibling(secondGrandGrandChild)
			.value("I'm the first grand grand child").build();

		secondGrandGrandChild = SimpleTreeNode.<String, Long> builder().parent(firstGrandChild)
			.id(idGenerator.getNextId()).leftMostChild(firstGrandGrandGrandChild)
			.value("I'm the second grand grand child").build();

		firstGrandGrandGrandChild = SimpleTreeNode.<String, Long> builder()
			.id(idGenerator.getNextId()).parent(secondGrandGrandChild)
			.value("I'm the first grand grand grand child").build();

		secondGrandChild = SimpleTreeNode.<String, Long> builder().parent(secondChild)
			.id(idGenerator.getNextId()).rightSibling(thirdGrandChild)
			.value("I'm the second grand child").build();

		thirdGrandChild = SimpleTreeNode.<String, Long> builder().parent(secondChild).value(null)
			.id(idGenerator.getNextId()).build();

		thirdChild = SimpleTreeNode.<String, Long> builder().parent(root)
			.id(idGenerator.getNextId()).leftMostChild(fourthGrandChild)
			.value("I'm the third child").build();

		fourthGrandChild = SimpleTreeNode.<String, Long> builder().parent(thirdChild)
			.id(idGenerator.getNextId()).rightSibling(fifthGrandChild).value(null).build();

		fifthGrandChild = SimpleTreeNode.<String, Long> builder().parent(thirdChild)
			.id(idGenerator.getNextId()).value("I'm the fifth grand child").build();

		// initialize left most child and right sibling
		root.setLeftMostChild(firstChild);

		firstChild.setRightSibling(secondChild);

		secondChild.setLeftMostChild(firstGrandChild);
		secondChild.setRightSibling(thirdChild);

		firstGrandChild.setLeftMostChild(firstGrandGrandChild);
		firstGrandChild.setRightSibling(secondGrandChild);

		firstGrandGrandChild.setRightSibling(secondGrandGrandChild);

		secondGrandGrandChild.setLeftMostChild(firstGrandGrandGrandChild);

		secondGrandChild.setRightSibling(thirdGrandChild);

		thirdChild.setLeftMostChild(fourthGrandChild);

		fourthGrandChild.setRightSibling(fifthGrandChild);
	}

	/**
	 * {@inheritDoc}
	 */
	@AfterMethod
	protected void tearDown()
	{
		root = null;
		firstChild = null;
		secondChild = null;
		firstGrandChild = null;
		firstGrandGrandChild = null;
		secondGrandGrandChild = null;
		firstGrandGrandGrandChild = null;
		secondGrandChild = null;
		thirdGrandChild = null;
		thirdChild = null;
		fourthGrandChild = null;
		fifthGrandChild = null;
	}

	/**
	 * Test method for {@link SimpleTreeNode#getAllSiblings()}
	 */
	@Test
	public void testGetAllSilbings()
	{
		Set<SimpleTreeNode<String, Long>> allSiblings = root.getAllSiblings();
		assertEquals(allSiblings.size(), 0);
		allSiblings = secondChild.getAllSiblings();
		assertEquals(allSiblings.size(), 3);
	}

	/**
	 * Test method for {@link SimpleTreeNode#getAllRightSiblings()}
	 */
	@Test
	public void testGetAllRightSiblings()
	{
		Set<SimpleTreeNode<String, Long>> allRightSiblings = root.getAllSiblings();
		assertEquals(allRightSiblings.size(), 0);
		allRightSiblings = secondChild.getAllRightSiblings();
		assertEquals(allRightSiblings.size(), 1);
	}

	/**
	 * Test method for {@link SimpleTreeNode#getAllLeftSiblings()}
	 */
	@Test
	public void testGetAllLeftSiblings()
	{
		Set<SimpleTreeNode<String, Long>> allRightSiblings = root.getAllSiblings();
		assertEquals(allRightSiblings.size(), 0);
		allRightSiblings = secondChild.getAllLeftSiblings();
		assertEquals(allRightSiblings.size(), 1);
		allRightSiblings = firstChild.getAllLeftSiblings();
		assertEquals(allRightSiblings.size(), 0);
	}

	/**
	 * Test method for {@link SimpleTreeNode#getRightSibling()}
	 */
	@Test
	public void testRightSilbing()
	{
		boolean hasRightSibling;
		SimpleTreeNode<String, Long> rightSibling;

		rightSibling = root.getRightSibling();
		assertNull(rightSibling);
		assertFalse(root.hasRightSibling());

		hasRightSibling = firstChild.hasRightSibling();
		assertTrue(hasRightSibling);
		rightSibling = firstChild.getRightSibling();
		assertEquals(secondChild, rightSibling);

		hasRightSibling = secondChild.hasRightSibling();
		assertTrue(hasRightSibling);
		rightSibling = secondChild.getRightSibling();
		assertEquals(thirdChild, rightSibling);

		hasRightSibling = thirdChild.hasRightSibling();
		assertFalse(hasRightSibling);
		rightSibling = thirdChild.getRightSibling();
		assertNull(rightSibling);
	}

	/**
	 * Test method for {@link SimpleTreeNode#getRoot()}
	 */
	@Test
	public void testGetRoot()
	{
		SimpleTreeNode<String, Long> currentRoot;

		currentRoot = root.getRoot();
		assertEquals(currentRoot, root);

		currentRoot = firstGrandGrandGrandChild.getRoot();
		assertEquals(currentRoot, root);
	}

	/**
	 * Test method for {@link SimpleTreeNode#getLevel()}
	 */
	@Test
	public void testGetLevel()
	{
		int actual;
		int expected;

		actual = root.getLevel();
		expected = 0;
		assertEquals(expected, actual);

		actual = firstChild.getLevel();
		expected = 1;
		assertEquals(expected, actual);

		actual = secondChild.getLevel();
		assertEquals(expected, actual);

		actual = firstGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = firstGrandGrandChild.getLevel();
		expected = 3;
		assertEquals(expected, actual);

		actual = secondGrandGrandChild.getLevel();
		assertEquals(expected, actual);

		actual = firstGrandGrandGrandChild.getLevel();
		expected = 4;
		assertEquals(expected, actual);

		actual = secondGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = thirdGrandChild.getLevel();
		assertEquals(expected, actual);

		actual = thirdChild.getLevel();
		expected = 1;
		assertEquals(expected, actual);

		actual = fourthGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = fifthGrandChild.getLevel();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link SimpleTreeNode#accept(Visitor)}
	 */
	@Test
	public void testVisitor()
	{
		root.accept(new DisplayValueOfSimpleTreeNodeVisitor<>());
		TraverseSimpleTreeNodeVisitor<String, Long> traverseVisitor;
		Set<SimpleTreeNode<String, Long>> allTreeNodes;
		traverseVisitor = new TraverseSimpleTreeNodeVisitor<>();
		root.accept(traverseVisitor);
		allTreeNodes = traverseVisitor.getAllTreeNodes();
		assertEquals(allTreeNodes.size(), 12);

		Set<SimpleTreeNode<String, Long>> subTree = thirdChild.traverse();
		assertEquals(3, subTree.size());
	}

	/**
	 * Test method for {@link SimpleTreeNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		final SimpleTreeNode<String, Long> parentTreeNode = new SimpleTreeNode<>("parent");
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("parent", (Factory<SimpleTreeNode<String, Long>>)() -> parentTreeNode)
			.build();
		final BeanTester beanTester = new BeanTester();
		beanTester.addCustomConfiguration(SimpleTreeNode.class, configuration);
		beanTester.testBean(SimpleTreeNode.class);
	}

}
