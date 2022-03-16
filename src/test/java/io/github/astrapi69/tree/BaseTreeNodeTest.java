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
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.AbstractTestCase;
import io.github.astrapi69.evaluate.object.evaluators.EqualsHashCodeAndToStringEvaluator;

public class BaseTreeNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	TreeElement firstChild;
	BaseTreeNode<TreeElement> firstChildTreeNode;
	TreeElement firstGrandChild;
	BaseTreeNode<TreeElement> firstGrandChildTreeNode;
	TreeElement firstGrandGrandChild;
	BaseTreeNode<TreeElement> firstGrandGrandChildTreeNode;
	List<BaseTreeNode<TreeElement>> list;
	TreeElement parent;
	BaseTreeNode<TreeElement> parentTreeNode;
	TreeElement secondChild;
	BaseTreeNode<TreeElement> secondChildTreeNode;

	@BeforeMethod
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		parent = TreeElement.builder().name("parent").parent(null).node(true).build();
		firstChild = TreeElement.builder().name("firstChild").parent(parent).node(true).build();
		firstGrandChild = TreeElement.builder().name("firstGrandChild").parent(firstChild)
			.node(true).build();
		firstGrandGrandChild = TreeElement.builder().name("firstGrandGrandChild")
			.parent(firstGrandChild).node(true).build();
		secondChild = TreeElement.builder().name("secondChild").parent(parent).build();

		parentTreeNode = BaseTreeNode.<TreeElement> builder().value(parent).build();

		firstChildTreeNode = BaseTreeNode.<TreeElement> builder().value(firstChild)
			.parent(parentTreeNode).build();

		secondChildTreeNode = BaseTreeNode.<TreeElement> builder().value(secondChild)
			.parent(parentTreeNode).build();

		firstGrandChildTreeNode = BaseTreeNode.<TreeElement> builder().value(firstGrandChild)
			.parent(firstChildTreeNode).build();

		firstGrandGrandChildTreeNode = BaseTreeNode.<TreeElement> builder()
			.value(firstGrandGrandChild).parent(firstChildTreeNode).build();
	}

	@AfterMethod
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		list = null;
		parent = null;
		firstChild = null;
		firstGrandChild = null;
		secondChild = null;
		parentTreeNode = null;
		firstChildTreeNode = null;
		secondChildTreeNode = null;
		firstGrandChildTreeNode = null;
	}

	/**
	 * Test method for {@link BaseTreeNode#addChildAt(int, BaseTreeNode)}.
	 */
	@Test
	public void testAddChildAt()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChildAt(0, secondChildTreeNode);
		parentTreeNode.addChildAt(4, firstGrandChildTreeNode);

		list = parentTreeNode.getChildren();

		assertEquals(list.size(), 3);
		assertEquals(list.get(0), secondChildTreeNode);
		assertEquals(list.get(1), firstChildTreeNode);
		assertEquals(list.get(2), firstGrandChildTreeNode);
	}

	/**
	 * Test method for {@link BaseTreeNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		BaseTreeNode<TreeElement> parentTreeNode = new BaseTreeNode<>();
		assertNotNull(parentTreeNode);
		parentTreeNode.setValue(parent);
		parentTreeNode = new BaseTreeNode<>(parent);
		assertNotNull(parentTreeNode);
		BaseTreeNode<TreeElement> treeNode = BaseTreeNode.<TreeElement> builder().build();
		assertNotNull(treeNode);
		assertTrue(treeNode.isNode());
	}

	/**
	 * Test method for {@link BaseTreeNode#getAllSiblings()}.
	 */
	@Test
	public void testGetAllSiblings()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		list = firstChildTreeNode.getAllSiblings();

		assertEquals(list.size(), 2);
		assertEquals(list.get(0), secondChildTreeNode);
		assertEquals(list.get(1), firstGrandChildTreeNode);

		list = parentTreeNode.getAllSiblings();
		assertNull(list);
	}

	// ========================================================

	/**
	 * Test method for {@link BaseTreeNode#getChildCount()}.
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		list = parentTreeNode.getChildren();

		actual = parentTreeNode.getChildCount();
		expected = list.size();
		assertEquals(expected, actual);

	}

	/**
	 * Test method for {@link BaseTreeNode#getDepth()}.
	 */
	@Test
	public void testGetDepth()
	{
		int actual;
		int expected;

		actual = parentTreeNode.getDepth();
		expected = 0;
		assertEquals(expected, actual);

		parentTreeNode.addChild(firstChildTreeNode);

		actual = parentTreeNode.getDepth();
		expected = 1;
		assertEquals(expected, actual);
		parentTreeNode.addChild(secondChildTreeNode);
		firstChildTreeNode.addChild(firstGrandChildTreeNode);

		actual = parentTreeNode.getDepth();
		expected = 2;
		assertEquals(expected, actual);

		firstGrandChildTreeNode.addChild(firstGrandGrandChildTreeNode);

		actual = parentTreeNode.getDepth();
		expected = 3;
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

		actual = parentTreeNode.getLevel();
		expected = 0;
		assertEquals(expected, actual);

		parentTreeNode.addChild(firstChildTreeNode);

		actual = firstChildTreeNode.getLevel();
		expected = 1;
		assertEquals(expected, actual);
		parentTreeNode.addChild(secondChildTreeNode);
		firstChildTreeNode.addChild(firstGrandChildTreeNode);

		actual = firstGrandChildTreeNode.getLevel();
		expected = 2;
		assertEquals(expected, actual);

		firstGrandChildTreeNode.addChild(firstGrandGrandChildTreeNode);

		actual = firstGrandGrandChildTreeNode.getLevel();
		expected = 3;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getNextSibling()}.
	 */
	@Test
	public void testGetNextSibling()
	{
		BaseTreeNode<TreeElement> actual;
		BaseTreeNode<TreeElement> expected;

		actual = firstChildTreeNode.getNextSibling();
		expected = null;
		assertEquals(expected, actual);

		parentTreeNode.addChild(firstChildTreeNode);

		actual = firstChildTreeNode.getNextSibling();
		assertEquals(expected, actual);

		parentTreeNode.addChild(secondChildTreeNode);

		actual = firstChildTreeNode.getNextSibling();
		expected = secondChildTreeNode;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getPreviousSibling()}.
	 */
	@Test
	public void testGetPreviousSibling()
	{
		BaseTreeNode<TreeElement> actual;
		BaseTreeNode<TreeElement> expected;

		actual = firstChildTreeNode.getPreviousSibling();
		expected = null;
		assertEquals(expected, actual);

		parentTreeNode.addChild(firstChildTreeNode);

		actual = firstChildTreeNode.getPreviousSibling();
		assertEquals(expected, actual);

		parentTreeNode.addChild(secondChildTreeNode);

		actual = secondChildTreeNode.getPreviousSibling();
		expected = firstChildTreeNode;
		assertEquals(expected, actual);
	}

	@Test
	public void testGetRoot()
	{
		BaseTreeNode<TreeElement> root;

		root = parentTreeNode.getRoot();
		assertEquals(root, parentTreeNode);

		root = firstGrandChildTreeNode.getRoot();
		assertEquals(root, parentTreeNode);
	}

	/**
	 * Test method for {@link BaseTreeNode#hasChildren()}.
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(parentTreeNode.hasChildren());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(parentTreeNode.hasChildren());
	}

	/**
	 * Test method for {@link BaseTreeNode#hasParent()}.
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(parentTreeNode.hasParent());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(firstChildTreeNode.hasParent());
	}

	/**
	 * Test method for {@link BaseTreeNode#isRoot()}.
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(parentTreeNode.isRoot());
		parentTreeNode.addChild(firstChildTreeNode);
		assertFalse(firstChildTreeNode.isRoot());
	}

	/**
	 * Test method for {@link BaseTreeNode#removeChild(BaseTreeNode)}.
	 */
	@Test
	public void testRemoveChild()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		list = parentTreeNode.getChildren();
		assertTrue(list.contains(firstChildTreeNode));

		parentTreeNode.removeChild(firstChildTreeNode);
		list = parentTreeNode.getChildren();
		assertFalse(list.contains(firstChildTreeNode));
	}

	/**
	 * Test method for {@link BaseTreeNode#removeChildAt(int)}.
	 */
	@Test
	public void testRemoveChildAt()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);

		list = parentTreeNode.getChildren();
		assertTrue(list.contains(firstChildTreeNode));

		parentTreeNode.removeChildAt(0);

		list = parentTreeNode.getChildren();
		assertFalse(list.contains(firstChildTreeNode));
	}

	/**
	 * Test method for {@link BaseTreeNode#toList()}.
	 */
	@Test
	public void testToList()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		list = parentTreeNode.toList();

		assertEquals(list.size(), 4);
		assertEquals(list.get(0), parentTreeNode);
		assertEquals(list.get(1), firstChildTreeNode);
		assertEquals(list.get(2), secondChildTreeNode);
		assertEquals(list.get(3), firstGrandChildTreeNode);
	}

	/**
	 * Test method for {@link BaseTreeNode#traverse(BaseTreeNode, List)}
	 */
	@Test
	public void testTraverse()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		firstChildTreeNode.addChild(firstGrandChildTreeNode);

		list = new ArrayList<>();
		parentTreeNode.traverse(parentTreeNode, list);
		assertEquals(list.size(), 4);
		assertEquals(list.get(0), parentTreeNode);
		assertEquals(list.get(1), firstChildTreeNode);
		assertEquals(list.get(2), firstGrandChildTreeNode);
		assertEquals(list.get(3), secondChildTreeNode);
	}

}

