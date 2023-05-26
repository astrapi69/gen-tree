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

import io.github.astrapi69.collection.set.SetFactory;
import io.github.astrapi69.gen.tree.SimpleTreeNode;
import io.github.astrapi69.id.generate.LongIdGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.AssertJUnit.assertEquals;

/**
 * The unit test class for the class {@link SimpleTreeNodeHandlerExtensions}
 */
public class SimpleTreeNodeHandlerExtensionsTest
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

		fifthGrandChild = SimpleTreeNode.<String, Long> builder().parent(thirdChild).leaf(true)
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
	 * Test method for {@link SimpleTreeNodeHandlerExtensions#getChildren(SimpleTreeNode)}
	 */
	@Test
	public void testGetChildren()
	{
		Collection<SimpleTreeNode<String, Long>> actual;
		Collection<SimpleTreeNode<String, Long>> expected;
		// new scenario ...
		actual = SimpleTreeNodeHandlerExtensions.getChildren(root);
		expected = SetFactory.newLinkedHashSet(firstChild, secondChild, thirdChild);
		assertEquals(expected, actual);
		// new scenario ...
		actual = SimpleTreeNodeHandlerExtensions.getChildren(fifthGrandChild);
		expected = SetFactory.newLinkedHashSet();
		assertEquals(expected, actual);

	}

	/**
	 * Test method for {@link SimpleTreeNodeHandlerExtensions#getNextSibling(SimpleTreeNode)}
	 */
	@Test
	public void testGetNextSibling()
	{
		SimpleTreeNode<String, Long> actual;
		SimpleTreeNode<String, Long> expected;
		// new scenario ...
		actual = SimpleTreeNodeHandlerExtensions.getNextSibling(root);
		expected = null;
		assertEquals(expected, actual);
		// new scenario ...
		actual = SimpleTreeNodeHandlerExtensions.getNextSibling(firstChild);
		expected = secondChild;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link SimpleTreeNodeHandlerExtensions#getPreviousSibling(SimpleTreeNode)}
	 */
	@Test
	public void testGetPreviousSibling()
	{
		SimpleTreeNode<String, Long> actual;
		SimpleTreeNode<String, Long> expected;
		// new scenario ...
		actual = SimpleTreeNodeHandlerExtensions.getNextSibling(root);
		expected = null;
		assertEquals(expected, actual);
		// new scenario ...
		actual = SimpleTreeNodeHandlerExtensions.getNextSibling(firstChild);
		expected = secondChild;
		assertEquals(expected, actual);
	}
}
