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
import java.util.Collection;
import java.util.List;

import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.AbstractTestCase;
import io.github.astrapi69.collections.set.SetFactory;
import io.github.astrapi69.evaluate.object.evaluators.EqualsHashCodeAndToStringEvaluator;
import io.github.astrapi69.tree.api.ITreeNode;
import io.github.astrapi69.tree.element.TreeElement;

/**
 * The unit test class for the class {@link TreeNode}
 */
public class TreeNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	TreeElement firstChild;
	ITreeNode<TreeElement> firstChildTreeNode;
	TreeElement firstGrandChild;
	ITreeNode<TreeElement> firstGrandChildTreeNode;
	TreeElement firstGrandGrandChild;
	ITreeNode<TreeElement> firstGrandGrandChildTreeNode;
	Collection<ITreeNode<TreeElement>> list;
	TreeElement parent;
	ITreeNode<TreeElement> parentTreeNode;
	TreeElement secondChild;
	ITreeNode<TreeElement> secondChildTreeNode;

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

		parentTreeNode = TreeNode.<TreeElement> builder().value(parent).build();

		firstChildTreeNode = TreeNode.<TreeElement> builder().value(firstChild)
			.parent(parentTreeNode).build();

		secondChildTreeNode = TreeNode.<TreeElement> builder().value(secondChild)
			.parent(parentTreeNode).build();

		firstGrandChildTreeNode = TreeNode.<TreeElement> builder().value(firstGrandChild)
			.parent(firstChildTreeNode).build();

		firstGrandGrandChildTreeNode = TreeNode.<TreeElement> builder().value(firstGrandGrandChild)
			.parent(firstChildTreeNode).build();
	}

	/**
	 * Test method for {@link TreeNode#addChildren(Collection)}
	 */
	@Test
	public void testAddChildren()
	{
		Collection<ITreeNode<String>> children;
		TreeNode<String> root;
		TreeNode<String> firstChild;
		TreeNode<String> secondChild;

		root = TreeNode.<String> builder().value("root").build();
		firstChild = TreeNode.<String> builder().value("firstChild").build();
		secondChild = TreeNode.<String> builder().value("secondChild").build();

		children = SetFactory.newLinkedHashSet(firstChild, secondChild);

		root.addChildren(children);

		Collection<ITreeNode<String>> rootChildren = root.getChildren();
		assertTrue(rootChildren.contains(firstChild));
		assertTrue(rootChildren.contains(secondChild));
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
	 * Test method for {@link TreeNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		ITreeNode<TreeElement> parentTreeNode = new TreeNode<>();
		assertNotNull(parentTreeNode);
		parentTreeNode.setValue(parent);
		parentTreeNode = new TreeNode<>(parent);
		assertNotNull(parentTreeNode);
		TreeNode<TreeElement> treeNode = TreeNode.<TreeElement> builder().build();
		assertNotNull(treeNode);
		assertTrue(treeNode.isNode());
	}

	/**
	 * Test method for {@link TreeNode#equals(Object)} , {@link TreeNode#hashCode()} and
	 * {@link TreeNode#toString()}
	 */
	@Test
	public void testEqualsHashcodeAndToString()
	{
		TreeNode<TreeElement> first = new TreeNode<>(parent);
		TreeNode<TreeElement> second = new TreeNode<>();
		TreeNode<TreeElement> third = new TreeNode<>(parent);
		TreeNode<TreeElement> fourth = new TreeNode<>(parent);

		actual = EqualsHashCodeAndToStringEvaluator.evaluateEqualsHashcodeAndToString(first, second,
			third, fourth);
		expected = true;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link TreeNode#getAllSiblings()}.
	 */
	@Test
	public void testGetAllSiblings()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		List<ITreeNode<TreeElement>> allSiblings = firstChildTreeNode.getAllSiblings();

		assertEquals(allSiblings.size(), 2);
		assertEquals(allSiblings.get(0), secondChildTreeNode);
		assertEquals(allSiblings.get(1), firstGrandChildTreeNode);

		list = parentTreeNode.getAllSiblings();
		assertNull(list);
	}

	// ========================================================

	/**
	 * Test method for {@link TreeNode#getChildCount()}.
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
	 * Test method for {@link TreeNode#getDepth()}.
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
	 * Test method for {@link TreeNode#getLevel()}.
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

	@Test
	public void testGetRoot()
	{
		ITreeNode<TreeElement> root;

		root = parentTreeNode.getRoot();
		assertEquals(root, parentTreeNode);

		root = firstGrandChildTreeNode.getRoot();
		assertEquals(root, parentTreeNode);
	}

	/**
	 * Test method for {@link TreeNode#hasChildren()}.
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(parentTreeNode.hasChildren());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(parentTreeNode.hasChildren());
	}

	/**
	 * Test method for {@link TreeNode#hasParent()}.
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(parentTreeNode.hasParent());
		parentTreeNode.addChild(firstChildTreeNode);
		assertTrue(firstChildTreeNode.hasParent());
	}

	/**
	 * Test method for {@link TreeNode#isRoot()}.
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(parentTreeNode.isRoot());
		parentTreeNode.addChild(firstChildTreeNode);
		assertFalse(firstChildTreeNode.isRoot());
	}

	/**
	 * Test method for {@link TreeNode#removeChild(ITreeNode)}.
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
	 * Test method for {@link TreeNode#toList()}.
	 */
	@Test
	public void testToList()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		parentTreeNode.addChild(firstGrandChildTreeNode);

		List<ITreeNode<TreeElement>> treeNodes = parentTreeNode.toList();

		assertEquals(treeNodes.size(), 4);
		assertTrue(treeNodes.contains(parentTreeNode));
		assertTrue(treeNodes.contains(firstChildTreeNode));
		assertTrue(treeNodes.contains(firstGrandChildTreeNode));
		assertTrue(treeNodes.contains(secondChildTreeNode));
	}

	/**
	 * Test method for {@link TreeNode#traverse()}
	 */
	@Test
	public void testTraverse()
	{
		parentTreeNode.addChild(firstChildTreeNode);
		parentTreeNode.addChild(secondChildTreeNode);
		firstChildTreeNode.addChild(firstGrandChildTreeNode);

		Collection<ITreeNode<TreeElement>> traverse = parentTreeNode.traverse();
		assertEquals(traverse.size(), 4);
		ArrayList<ITreeNode<TreeElement>> list = (ArrayList<ITreeNode<TreeElement>>)traverse;
		assertTrue(list.contains(parentTreeNode));
		assertTrue(list.contains(firstChildTreeNode));
		assertTrue(list.contains(firstGrandChildTreeNode));
		assertTrue(list.contains(secondChildTreeNode));
	}

	/**
	 * Test method for {@link TreeNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		final TreeNode<TreeElement> parentTreeNode = new TreeNode<>(parent);
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("parent", new Factory<TreeNode<TreeElement>>()
			{

				@Override
				public TreeNode<TreeElement> create()
				{
					return parentTreeNode;
				}

			}).build();
		final BeanTester beanTester = new BeanTester();
		beanTester.addCustomConfiguration(TreeNode.class, configuration);
		beanTester.testBean(TreeNode.class);
	}
}
