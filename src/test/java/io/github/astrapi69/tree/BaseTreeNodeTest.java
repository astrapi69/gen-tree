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
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.AbstractTestCase;

public class BaseTreeNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	BaseTreeNode<String> root;
	BaseTreeNode<String> firstChild;
	BaseTreeNode<String> secondChild;
	BaseTreeNode<String> firstGrandChild;
	BaseTreeNode<String> firstGrandGrandChild;
	BaseTreeNode<String> secondGrandGrandChild;
	BaseTreeNode<String> firstGrandGrandGrandChild;
	BaseTreeNode<String> secondGrandChild;
	BaseTreeNode<String> thirdGrandChild;
	BaseTreeNode<String> thirdChild;
	BaseTreeNode<String> fourthGrandChild;
	BaseTreeNode<String> fifthGrandChild;

	/**
	 * Set up the tree structure for the unit tests
	 * @formatter:off
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
	 * @formatter:on
	 */
	@BeforeMethod
	public void setup()
	{
		root = BaseTreeNode.<String> builder().value("I'm root").build();

		firstChild = BaseTreeNode.<String> builder().value("I'm the first child").build();

		secondChild = BaseTreeNode.<String> builder().parent(root).value("I'm the second child")
			.build();

		firstGrandChild = BaseTreeNode.<String> builder().parent(secondChild)
			.value("I'm the first grand child").build();

		firstGrandGrandChild = BaseTreeNode.<String> builder().parent(firstGrandChild)
			.value("I'm the first grand grand child").build();

		secondGrandGrandChild = BaseTreeNode.<String> builder().parent(firstGrandChild)
			.value("I'm the second grand grand child").build();

		firstGrandGrandGrandChild = BaseTreeNode.<String> builder().parent(secondGrandGrandChild)
			.value("I'm the first grand grand grand child").build();

		secondGrandChild = BaseTreeNode.<String> builder().parent(secondChild)
			.value("I'm the second grand child").build();

		thirdGrandChild = BaseTreeNode.<String> builder().parent(secondChild).value(null).build();

		thirdChild = BaseTreeNode.<String> builder().parent(root).value("I'm the third child")
			.build();

		fourthGrandChild = BaseTreeNode.<String> builder().parent(thirdChild).value(null).build();

		fifthGrandChild = BaseTreeNode.<String> builder().parent(thirdChild)
			.value("I'm the fifth grand child").build();

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
	 * Test method for {@link BaseTreeNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		BaseTreeNode<String> parentTreeNode = new BaseTreeNode<>();
		assertNotNull(parentTreeNode);
		parentTreeNode.setValue("parent");
		parentTreeNode = new BaseTreeNode<>("parent");
		assertNotNull(parentTreeNode);
		BaseTreeNode<TreeElement> treeNode = BaseTreeNode.<TreeElement> builder().build();
		assertNotNull(treeNode);
		assertTrue(treeNode.isNode());
	}

	/**
	 * Test method for {@link BaseTreeNode#traverse()}
	 */
	@Test
	public void testSubtree()
	{
		Set<BaseTreeNode<String>> subtree;

		subtree = root.traverse();
		assertEquals(12, subtree.size());

		subtree = firstChild.traverse();
		assertEquals(1, subtree.size());

		subtree = secondChild.traverse();
		assertEquals(7, subtree.size());

		subtree = thirdChild.traverse();
		assertEquals(3, subtree.size());

		subtree = firstGrandChild.traverse();
		assertEquals(4, subtree.size());

		subtree = firstGrandGrandChild.traverse();
		assertEquals(1, subtree.size());

		subtree = secondGrandGrandChild.traverse();
		assertEquals(2, subtree.size());

		subtree = firstGrandGrandGrandChild.traverse();
		assertEquals(1, subtree.size());

		subtree = secondGrandChild.traverse();
		assertEquals(1, subtree.size());

		subtree = thirdGrandChild.traverse();
		assertEquals(1, subtree.size());

		subtree = fourthGrandChild.traverse();
		assertEquals(1, subtree.size());

		subtree = fifthGrandChild.traverse();
		assertEquals(1, subtree.size());
	}

	/**
	 * Test method for {@link BaseTreeNode#getAllSiblings()}.
	 */
	@Test
	public void testGetAllSiblings()
	{
		Set<BaseTreeNode<String>> allSiblings;

		allSiblings = root.getAllSiblings();
		assertEquals(0, allSiblings.size());

		allSiblings = firstChild.getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = secondChild.getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = thirdChild.getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = firstGrandChild.getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = firstGrandGrandChild.getAllSiblings();
		assertEquals(1, allSiblings.size());

		allSiblings = secondGrandGrandChild.getAllSiblings();
		assertEquals(1, allSiblings.size());

		allSiblings = firstGrandGrandGrandChild.getAllSiblings();
		assertEquals(0, allSiblings.size());

		allSiblings = secondGrandChild.getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = thirdGrandChild.getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = fourthGrandChild.getAllSiblings();
		assertEquals(1, allSiblings.size());

		allSiblings = fifthGrandChild.getAllSiblings();
		assertEquals(1, allSiblings.size());
	}

	/**
	 * Test method for {@link BaseTreeNode#getChildCount()}.
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;

		Set<BaseTreeNode<String>> children = root.getChildren();

		actual = root.getChildCount();
		expected = children.size();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getLevel()}.
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
		expected = 1;
		assertEquals(expected, actual);

		actual = firstGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = firstGrandGrandChild.getLevel();
		expected = 3;
		assertEquals(expected, actual);

		actual = secondGrandGrandChild.getLevel();
		expected = 3;
		assertEquals(expected, actual);

		actual = firstGrandGrandGrandChild.getLevel();
		expected = 4;
		assertEquals(expected, actual);

		actual = secondGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = thirdGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = thirdChild.getLevel();
		expected = 1;
		assertEquals(expected, actual);

		actual = fourthGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = fifthGrandChild.getLevel();
		expected = 2;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getNextSibling()}.
	 */
	@Test
	public void testGetNextSibling()
	{
		BaseTreeNode<String> actual;
		BaseTreeNode<String> expected;

		actual = firstChild.getNextSibling();
		expected = secondChild;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getPreviousSibling()}.
	 */
	@Test
	public void testGetPreviousSibling()
	{
		BaseTreeNode<String> actual;
		BaseTreeNode<String> expected;

		actual = secondChild.getPreviousSibling();
		expected = firstChild;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getRoot()}.
	 */
	@Test
	public void testGetRoot()
	{
		BaseTreeNode<String> currentRoot;

		currentRoot = root.getRoot();
		assertEquals(currentRoot, root);

		currentRoot = firstGrandGrandGrandChild.getRoot();
		assertEquals(currentRoot, root);
	}

	/**
	 * Test method for {@link BaseTreeNode#hasChildren()}.
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(firstGrandGrandGrandChild.hasChildren());
		assertTrue(root.hasChildren());
	}

	/**
	 * Test method for {@link BaseTreeNode#hasParent()}.
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(root.hasParent());
		assertTrue(firstGrandGrandGrandChild.hasParent());
	}

	/**
	 * Test method for {@link BaseTreeNode#isRoot()}.
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(root.isRoot());
		assertFalse(firstChild.isRoot());
	}

	/**
	 * Test method for {@link BaseTreeNode#removeChild(BaseTreeNode)}.
	 */
	@Test
	public void testRemoveChild()
	{
		Set<BaseTreeNode<String>> children;

		children = root.getChildren();
		assertTrue(children.contains(firstChild));

		root.removeChild(firstChild);

		children = root.getChildren();
		assertFalse(children.contains(firstChild));
	}

}

