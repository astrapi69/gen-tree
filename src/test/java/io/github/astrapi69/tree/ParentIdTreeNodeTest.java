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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.AbstractTestCase;
import io.github.astrapi69.evaluate.object.evaluators.EqualsHashCodeAndToStringEvaluator;

public class ParentIdTreeNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	TreeElement firstChild;
	ParentIdTreeNode<TreeElement, UUID> firstChildTreeNode;
	TreeElement firstGrandChild;
	ParentIdTreeNode<TreeElement, UUID> firstGrandChildTreeNode;
	TreeElement firstGrandGrandChild;
	ParentIdTreeNode<TreeElement, UUID> firstGrandGrandChildTreeNode;
	List<ParentIdTreeNode<TreeElement, UUID>> list;
	TreeElement parent;
	ParentIdTreeNode<TreeElement, UUID> parentTreeNode;
	TreeElement secondChild;
	ParentIdTreeNode<TreeElement, UUID> secondChildTreeNode;

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
		secondChild = TreeElement.builder().name("secondChild").parent(parent).node(true).build();

		parentTreeNode = ParentIdTreeNode.<TreeElement, UUID> builder().id(UUID.randomUUID())
			.value(parent).node(true).build();

		firstChildTreeNode = ParentIdTreeNode.<TreeElement, UUID> builder().value(firstChild)
			.id(UUID.randomUUID()).parentId(parentTreeNode.getParentId()).build();

		secondChildTreeNode = ParentIdTreeNode.<TreeElement, UUID> builder().value(secondChild)
			.id(UUID.randomUUID()).parentId(parentTreeNode.getParentId()).build();

		firstGrandChildTreeNode = ParentIdTreeNode.<TreeElement, UUID> builder()
			.value(firstGrandChild).id(UUID.randomUUID()).parentId(firstChildTreeNode.getParentId())
			.build();

		firstGrandGrandChildTreeNode = ParentIdTreeNode.<TreeElement, UUID> builder()
			.id(UUID.randomUUID()).value(firstGrandGrandChild)
			.parentId(firstChildTreeNode.getParentId()).build();
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
	 * Test method for {@link ParentIdTreeNode#addChildAt(int, ParentIdTreeNode)}.
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
	 * Test method for {@link ParentIdTreeNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		ParentIdTreeNode<TreeElement, UUID> parentTreeNode = new ParentIdTreeNode<>();
		assertNotNull(parentTreeNode);
		parentTreeNode.setValue(parent);
		parentTreeNode = new ParentIdTreeNode<>(parent);
		assertNotNull(parentTreeNode);
		ParentIdTreeNode<TreeElement, UUID> treeNode = ParentIdTreeNode
			.<TreeElement, UUID> builder().build();
		assertNotNull(treeNode);
		assertTrue(treeNode.isNode());
	}

	/**
	 * Test method for {@link ParentIdTreeNode#equals(Object)} , {@link ParentIdTreeNode#hashCode()}
	 * and {@link ParentIdTreeNode#toString()}
	 */
	@Test
	public void testEqualsHashcodeAndToString()
	{
		ParentIdTreeNode<TreeElement, UUID> first = new ParentIdTreeNode<>(parent);
		ParentIdTreeNode<TreeElement, UUID> second = new ParentIdTreeNode<>();
		ParentIdTreeNode<TreeElement, UUID> third = new ParentIdTreeNode<>(parent);
		ParentIdTreeNode<TreeElement, UUID> fourth = new ParentIdTreeNode<>(parent);

		actual = EqualsHashCodeAndToStringEvaluator.evaluateEqualsHashcodeAndToString(first, second,
			third, fourth);
		expected = true;
		assertEquals(expected, actual);
	}

	// ========================================================

	/**
	 * Test method for {@link ParentIdTreeNode#getChildCount()}.
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
	 * Test method for {@link ParentIdTreeNode#hasChildren()}.
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(parentTreeNode.hasChildren());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(parentTreeNode.hasChildren());
	}

	/**
	 * Test method for {@link ParentIdTreeNode#hasParent()}.
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(parentTreeNode.hasParent());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(firstChildTreeNode.hasParent());
	}

	/**
	 * Test method for {@link ParentIdTreeNode#isRoot()}.
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(parentTreeNode.isRoot());
		parentTreeNode.addChild(firstChildTreeNode);
		assertFalse(firstChildTreeNode.isRoot());
	}

	/**
	 * Test method for {@link ParentIdTreeNode#removeChild(ParentIdTreeNode)}.
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
	 * Test method for {@link ParentIdTreeNode#removeChildAt(int)}.
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
	 * Test method for {@link ParentIdTreeNode#toList()}.
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
	 * Test method for {@link ParentIdTreeNode#traverse(ParentIdTreeNode, List)}
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

