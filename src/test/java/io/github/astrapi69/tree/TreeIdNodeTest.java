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
import java.util.UUID;

import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.AbstractTestCase;
import io.github.astrapi69.evaluate.object.evaluators.EqualsHashCodeAndToStringEvaluator;
import io.github.astrapi69.tree.element.TreeElement;

public class TreeIdNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	TreeElement firstChild;
	TreeIdNode<TreeElement, UUID> firstChildTreeNode;
	TreeElement firstGrandChild;
	TreeIdNode<TreeElement, UUID> firstGrandChildTreeNode;
	TreeElement firstGrandGrandChild;
	TreeIdNode<TreeElement, UUID> firstGrandGrandChildTreeNode;
	Set<UUID> list;
	TreeElement parent;
	TreeIdNode<TreeElement, UUID> parentTreeNode;
	TreeElement secondChild;
	TreeIdNode<TreeElement, UUID> secondChildTreeNode;

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

		parentTreeNode = TreeIdNode.<TreeElement, UUID> builder().id(UUID.randomUUID())
			.value(parent).build();

		firstChildTreeNode = TreeIdNode.<TreeElement, UUID> builder().value(firstChild)
			.id(UUID.randomUUID()).parentId(parentTreeNode.getParentId()).build();

		secondChildTreeNode = TreeIdNode.<TreeElement, UUID> builder().value(secondChild)
			.id(UUID.randomUUID()).parentId(parentTreeNode.getParentId()).build();

		firstGrandChildTreeNode = TreeIdNode.<TreeElement, UUID> builder().value(firstGrandChild)
			.id(UUID.randomUUID()).parentId(firstChildTreeNode.getParentId()).build();

		firstGrandGrandChildTreeNode = TreeIdNode.<TreeElement, UUID> builder()
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
	 * Test method for {@link TreeIdNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		TreeIdNode<TreeElement, UUID> parentTreeNode = new TreeIdNode<>();
		assertNotNull(parentTreeNode);
		parentTreeNode.setValue(parent);
		parentTreeNode = new TreeIdNode<>(parent);
		assertNotNull(parentTreeNode);
		TreeIdNode<TreeElement, UUID> treeNode = TreeIdNode.<TreeElement, UUID> builder().build();
		assertNotNull(treeNode);
		assertTrue(treeNode.isNode());
	}

	/**
	 * Test method for {@link TreeIdNode#equals(Object)} , {@link TreeIdNode#hashCode()} and
	 * {@link TreeIdNode#toString()}
	 */
	@Test
	public void testEqualsHashcodeAndToString()
	{
		TreeIdNode<TreeElement, UUID> first = new TreeIdNode<>(parent);
		TreeIdNode<TreeElement, UUID> second = new TreeIdNode<>();
		TreeIdNode<TreeElement, UUID> third = new TreeIdNode<>(parent);
		TreeIdNode<TreeElement, UUID> fourth = new TreeIdNode<>(parent);

		actual = EqualsHashCodeAndToStringEvaluator.evaluateEqualsHashcodeAndToString(first, second,
			third, fourth);
		expected = true;
		assertEquals(expected, actual);
	}

	// ========================================================

	/**
	 * Test method for {@link TreeIdNode#getChildCount()}.
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		list = parentTreeNode.getChildrenIds();

		actual = parentTreeNode.getChildCount();
		expected = list.size();
		assertEquals(expected, actual);

	}

	/**
	 * Test method for {@link TreeIdNode#hasChildren()}.
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(parentTreeNode.hasChildren());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(parentTreeNode.hasChildren());
	}

	/**
	 * Test method for {@link TreeIdNode#hasParent()}.
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(parentTreeNode.hasParent());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(firstChildTreeNode.hasParent());
	}

	/**
	 * Test method for {@link TreeIdNode#isRoot()}.
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(parentTreeNode.isRoot());
		parentTreeNode.addChild(firstChildTreeNode);
		assertFalse(firstChildTreeNode.isRoot());
	}

	/**
	 * Test method for {@link TreeIdNode#removeChild(TreeIdNode)}.
	 */
	@Test
	public void testRemoveChild()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		list = parentTreeNode.getChildrenIds();
		assertTrue(list.contains(firstChildTreeNode.getId()));

		parentTreeNode.removeChild(firstChildTreeNode);
		list = parentTreeNode.getChildrenIds();
		assertFalse(list.contains(firstChildTreeNode.getId()));
	}


	/**
	 * Test method for {@link TreeIdNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		final TreeIdNode<String, Long> parentTreeNode = new TreeIdNode<>("parent");
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("parent", (Factory<TreeIdNode<String, Long>>)() -> parentTreeNode)
			.build();
		final BeanTester beanTester = new BeanTester();
		beanTester.addCustomConfiguration(TreeIdNode.class, configuration);
		beanTester.testBean(TreeIdNode.class);
	}
}

