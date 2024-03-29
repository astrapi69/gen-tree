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

import java.util.Collection;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.gen.tree.TreeNode;
import io.github.astrapi69.gen.tree.api.ITreeNode;
import io.github.astrapi69.id.generate.LongIdGenerator;

/**
 * The unit test class for the class {@link ITreeNodeHandlerExtensions}
 */
public class ITreeNodeHandlerExtensionsTest
{

	TreeNode<String> root;
	TreeNode<String> firstChild;
	TreeNode<String> secondChild;
	TreeNode<String> firstGrandChild;
	TreeNode<String> firstGrandGrandChild;
	TreeNode<String> secondGrandGrandChild;
	TreeNode<String> firstGrandGrandGrandChild;
	TreeNode<String> secondGrandChild;
	TreeNode<String> thirdGrandChild;
	TreeNode<String> thirdChild;
	TreeNode<String> fourthGrandChild;
	TreeNode<String> fifthGrandChild;
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
		root = TreeNode.<String> builder().value("I'm root").build();

		firstChild = TreeNode.<String> builder().parent(root).value("I'm the first child").build();

		secondChild = TreeNode.<String> builder().parent(root).value("I'm the second child")
			.build();

		firstGrandChild = TreeNode.<String> builder().parent(secondChild)
			.value("I'm the first grand child").build();

		firstGrandGrandChild = TreeNode.<String> builder().parent(firstGrandChild)
			.value("I'm the first grand grand child").build();

		secondGrandGrandChild = TreeNode.<String> builder().parent(firstGrandChild)
			.value("I'm the second grand grand child").build();

		firstGrandGrandGrandChild = TreeNode.<String> builder().parent(secondGrandGrandChild)
			.value("I'm the first grand grand grand child").build();

		secondGrandChild = TreeNode.<String> builder().parent(secondChild)
			.value("I'm the second grand child").build();

		thirdGrandChild = TreeNode.<String> builder().parent(secondChild).value(null).build();

		thirdChild = TreeNode.<String> builder().parent(root).value("I'm the third child").build();

		fourthGrandChild = TreeNode.<String> builder().parent(thirdChild).value(null).build();
		fifthGrandChildValue = "I'm the fifth grand child";
		fifthGrandChild = TreeNode.<String> builder().parent(thirdChild).leaf(true)
			.value(fifthGrandChildValue).build();

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
	 * This method will be invoked after every unit test method for clear any test instances
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
		fifthGrandChildValue = null;
		idGenerator = null;
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isParentOf(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testIsParentOf()
	{
		boolean actual;
		boolean expected;
		actual = ITreeNodeHandlerExtensions.isParentOf(root, firstChild);
		expected = true;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.isParentOf(root, fifthGrandChild);
		expected = false;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isNode(ITreeNode)}
	 */
	@Test
	public void testIsNode()
	{
		boolean actual;
		boolean expected;
		actual = ITreeNodeHandlerExtensions.isNode(root);
		expected = true;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.isNode(fifthGrandChild);
		expected = false;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getChildCount(ITreeNode)}
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;

		Collection<TreeNode<String>> children = root.getChildren();

		actual = ITreeNodeHandlerExtensions.getChildCount(root);
		expected = children.size();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#contains(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testContains()
	{
		boolean actual;
		boolean expected;

		actual = ITreeNodeHandlerExtensions.contains(root, firstGrandGrandGrandChild);
		expected = true;
		assertEquals(actual, expected);

		actual = ITreeNodeHandlerExtensions.contains(firstGrandGrandGrandChild, root);
		expected = false;
		assertEquals(actual, expected);

		actual = ITreeNodeHandlerExtensions.contains(root, null);
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#move(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testMove()
	{
		boolean actual;
		boolean expected;

		actual = ITreeNodeHandlerExtensions.move(firstChild, thirdChild);
		expected = true;
		assertEquals(actual, expected);

		actual = ITreeNodeHandlerExtensions.move(root, firstGrandGrandGrandChild);
		expected = false;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isAncestor(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testIsAncestor()
	{
		boolean actual;
		boolean expected;

		actual = ITreeNodeHandlerExtensions.isAncestor(firstChild, thirdChild);
		expected = false;
		assertEquals(actual, expected);

		actual = ITreeNodeHandlerExtensions.isAncestor(firstGrandGrandGrandChild, root);
		expected = true;
		assertEquals(actual, expected);
	}
}
