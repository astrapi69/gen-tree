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
package io.github.astrapi69.gen.tree;

import io.github.astrapi69.AbstractTestCase;
import io.github.astrapi69.collection.set.SetFactory;
import io.github.astrapi69.gen.tree.api.ITreeNode;
import io.github.astrapi69.gen.tree.element.TreeElement;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.testng.AssertJUnit.*;

/**
 * The unit test class for the class {@link BaseTreeNode}
 */
public class BaseTreeNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	BaseTreeNodeTestTree testTree;

	/**
	 * Set up the tree structure for the unit tests
	 *
	 * <pre>
	 *   +- testTree.getRoot()("I'm testTree.getRoot()")
	 *      +- testTree.getFirstChild()("I'm the first child")
	 *      +- testTree.getSecondChild()("I'm the second child")
	 *      |  +- testTree.getFirstGrandChild()("I'm the first grand child")
	 *      |  |  +- testTree.getFirstGrandGrandChild()("I'm the first grand grand child")
	 *      |  |  +- testTree.getSecondGrandGrandChild()("I'm the second grand grand child)
	 *      |  |  |  +- testTree.getFirstGrandGrandGrandChild() ("I'm the first grand grand grand child")
	 *      |  +- testTree.getSecondGrandChild()("I'm the second grand child")
	 *      |  +- testTree.getThirdGrandChild()(null)
	 *      +- thirdChild("I'm the third child")
	 *      |  +- testTree.getFourthGrandChild()(null)
	 *      |  +- testTree.getFifthGrandChild()("I'm the fifth grand child")
	 * </pre>
	 */
	@BeforeMethod
	public void setup()
	{
		testTree = new BaseTreeNodeTestTree();
	}

	/**
	 * {@inheritDoc}
	 */
	@AfterMethod
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		testTree = null;
	}

	/**
	 * Test method for {@link BaseTreeNode#findAllByValue(Object)}
	 */
	@Test
	public void testFindAllByValue()
	{
		Collection<BaseTreeNode<String, Long>> actual;
		Collection<BaseTreeNode<String, Long>> expected;

		BaseTreeNode<String, Long> treeNode = BaseTreeNode.<String, Long> builder()
			.id(testTree.getIdGenerator().getNextId()).parent(testTree.getThirdChild()).value(
				testTree.getFifthGrandChildValue()).build();
		testTree.getThirdChild().addChild(treeNode);

		actual = testTree.getRoot().findAllByValue(testTree.getFifthGrandChildValue());
		expected = SetFactory.newLinkedHashSet(testTree.getFifthGrandChild(), treeNode);
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link BaseTreeNode#clearAll()}
	 */
	@Test
	public void testClearAll()
	{
		testTree.getRoot().clearAll();
		assertEquals(testTree.getRoot().getChildren().size(), 0);
	}

	/**
	 * Test method for {@link BaseTreeNode#clearChildren()}
	 */
	@Test
	public void testClearChildren()
	{
		testTree.getRoot().clearChildren();
		assertEquals(testTree.getRoot().getChildren().size(), 0);
	}

	/**
	 * Test method for {@link BaseTreeNode#findByValue(Object)}
	 */
	@Test
	public void testFindByValue()
	{
		BaseTreeNode<String, Long> actual;
		BaseTreeNode<String, Long> expected;

		BaseTreeNode<String, Long> treeNode = BaseTreeNode.<String, Long> builder()
			.id(testTree.getIdGenerator().getNextId()).parent(testTree.getThirdChild()).value(testTree.getFifthGrandChildValue()).build();
		testTree.getThirdChild().addChild(treeNode);

		actual = testTree.getRoot().findByValue(testTree.getFifthGrandChildValue());
		expected = testTree.getFifthGrandChild();
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link BaseTreeNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		final BaseTreeNode<String, Long> parentTreeNode = new BaseTreeNode<>("parent");
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("parent", (Factory<BaseTreeNode<String, Long>>)() -> parentTreeNode)
			.build();
		final BeanTester beanTester = new BeanTester();
		beanTester.addCustomConfiguration(BaseTreeNode.class, configuration);
		beanTester.testBean(BaseTreeNode.class);
	}

	/**
	 * Test method for {@link BaseTreeNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		BaseTreeNode<String, Long> parentTreeNode = new BaseTreeNode<>();
		assertNotNull(parentTreeNode);
		assertNotNull(parentTreeNode.getChildren());
		parentTreeNode.setValue("parent");
		parentTreeNode = new BaseTreeNode<>("parent");
		assertNotNull(parentTreeNode);
		BaseTreeNode<TreeElement, Long> treeNode = BaseTreeNode.<TreeElement, Long> builder()
			.build();
		assertNotNull(treeNode);
		assertTrue(treeNode.isNode());
	}

	/**
	 * Test method for {@link BaseTreeNode#traverse()}
	 */
	@Test
	public void testTraverse()
	{
		Collection<BaseTreeNode<String, Long>> subtree;

		subtree = testTree.getRoot().traverse();
		assertEquals(12, subtree.size());

		subtree = testTree.getFirstChild().traverse();
		assertEquals(1, subtree.size());

		subtree = testTree.getSecondChild().traverse();
		assertEquals(7, subtree.size());

		subtree = testTree.getThirdChild().traverse();
		assertEquals(3, subtree.size());

		subtree = testTree.getFirstGrandChild().traverse();
		assertEquals(4, subtree.size());

		subtree = testTree.getFirstGrandGrandChild().traverse();
		assertEquals(1, subtree.size());

		subtree = testTree.getSecondGrandGrandChild().traverse();
		assertEquals(2, subtree.size());

		subtree = testTree.getFirstGrandGrandGrandChild().traverse();
		assertEquals(1, subtree.size());

		subtree = testTree.getSecondGrandChild().traverse();
		assertEquals(1, subtree.size());

		subtree = testTree.getThirdGrandChild().traverse();
		assertEquals(1, subtree.size());

		subtree = testTree.getFourthGrandChild().traverse();
		assertEquals(1, subtree.size());

		subtree = testTree.getFifthGrandChild().traverse();
		assertEquals(1, subtree.size());
	}

	/**
	 * Test method for {@link BaseTreeNode#toList()}
	 */
	@Test
	public void testToList()
	{
		List<BaseTreeNode<String, Long>> subtree;

		subtree = testTree.getRoot().toList();
		assertEquals(12, subtree.size());

		subtree = testTree.getFirstChild().toList();
		assertEquals(1, subtree.size());

		subtree = testTree.getSecondChild().toList();
		assertEquals(7, subtree.size());

		subtree = testTree.getThirdChild().toList();
		assertEquals(3, subtree.size());

		subtree = testTree.getFirstGrandChild().toList();
		assertEquals(4, subtree.size());

		subtree = testTree.getFirstGrandGrandChild().toList();
		assertEquals(1, subtree.size());

		subtree = testTree.getSecondGrandGrandChild().toList();
		assertEquals(2, subtree.size());

		subtree = testTree.getFirstGrandGrandGrandChild().toList();
		assertEquals(1, subtree.size());

		subtree = testTree.getSecondGrandChild().toList();
		assertEquals(1, subtree.size());

		subtree = testTree.getThirdGrandChild().toList();
		assertEquals(1, subtree.size());

		subtree = testTree.getFourthGrandChild().toList();
		assertEquals(1, subtree.size());

		subtree = testTree.getFifthGrandChild().toList();
		assertEquals(1, subtree.size());
	}

	/**
	 * Test method for {@link BaseTreeNode#getAllSiblings()}
	 */
	@Test
	public void testGetAllSiblings()
	{
		Collection<BaseTreeNode<String, Long>> allSiblings;

		allSiblings = testTree.getRoot().getAllSiblings();
		assertEquals(0, allSiblings.size());

		allSiblings = testTree.getFirstChild().getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = testTree.getSecondChild().getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = testTree.getThirdChild().getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = testTree.getFirstGrandChild().getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = testTree.getFirstGrandGrandChild().getAllSiblings();
		assertEquals(1, allSiblings.size());

		allSiblings = testTree.getSecondGrandGrandChild().getAllSiblings();
		assertEquals(1, allSiblings.size());

		allSiblings = testTree.getFirstGrandGrandGrandChild().getAllSiblings();
		assertEquals(0, allSiblings.size());

		allSiblings = testTree.getSecondGrandChild().getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = testTree.getThirdGrandChild().getAllSiblings();
		assertEquals(2, allSiblings.size());

		allSiblings = testTree.getFourthGrandChild().getAllSiblings();
		assertEquals(1, allSiblings.size());

		allSiblings = testTree.getFifthGrandChild().getAllSiblings();
		assertEquals(1, allSiblings.size());
	}

	/**
	 * Test method for {@link BaseTreeNode#getChildCount()}
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;

		Collection<BaseTreeNode<String, Long>> children = testTree.getRoot().getChildren();

		actual = testTree.getRoot().getChildCount();
		expected = children.size();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getLevel()}
	 */
	@Test
	public void testGetLevel()
	{
		int actual;
		int expected;

		actual = testTree.getRoot().getLevel();
		expected = 0;
		assertEquals(expected, actual);

		actual = testTree.getFirstChild().getLevel();
		expected = 1;
		assertEquals(expected, actual);

		actual = testTree.getSecondChild().getLevel();
		assertEquals(expected, actual);

		actual = testTree.getFirstGrandChild().getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = testTree.getFirstGrandGrandChild().getLevel();
		expected = 3;
		assertEquals(expected, actual);

		actual = testTree.getSecondGrandGrandChild().getLevel();
		assertEquals(expected, actual);

		actual = testTree.getFirstGrandGrandGrandChild().getLevel();
		expected = 4;
		assertEquals(expected, actual);

		actual = testTree.getSecondGrandChild().getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = testTree.getThirdGrandChild().getLevel();
		assertEquals(expected, actual);

		actual = testTree.getThirdChild().getLevel();
		expected = 1;
		assertEquals(expected, actual);

		actual = testTree.getFourthGrandChild().getLevel();
		expected = 2;
		assertEquals(expected, actual);

		actual = testTree.getFifthGrandChild().getLevel();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getNextSibling()}
	 */
	@Test
	public void testGetNextSibling()
	{
		BaseTreeNode<String, Long> actual;
		BaseTreeNode<String, Long> expected;

		actual = testTree.getFirstChild().getNextSibling();
		expected = testTree.getSecondChild();
		assertEquals(expected, actual);

		actual = testTree.getRoot().getNextSibling();
		assertNull(actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getPreviousSibling()}
	 */
	@Test
	public void testGetPreviousSibling()
	{
		BaseTreeNode<String, Long> actual;
		BaseTreeNode<String, Long> expected;

		actual = testTree.getSecondChild().getPreviousSibling();
		expected = testTree.getFirstChild();
		assertEquals(expected, actual);

		actual = testTree.getRoot().getPreviousSibling();
		assertNull(actual);
	}

	/**
	 * Test method for {@link BaseTreeNode#getRoot()}
	 */
	@Test
	public void testGetRoot()
	{
		BaseTreeNode<String, Long> currentRoot;

		currentRoot = testTree.getRoot().getRoot();
		assertEquals(currentRoot, testTree.getRoot());

		currentRoot = testTree.getFirstGrandGrandGrandChild().getRoot();
		assertEquals(currentRoot, testTree.getRoot());
	}

	/**
	 * Test method for {@link BaseTreeNode#hasChildren()}
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(testTree.getFirstGrandGrandGrandChild().hasChildren());
		assertTrue(testTree.getRoot().hasChildren());
	}

	/**
	 * Test method for {@link BaseTreeNode#hasParent()}
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(testTree.getRoot().hasParent());
		assertTrue(testTree.getFirstGrandGrandGrandChild().hasParent());
	}

	/**
	 * Test method for {@link BaseTreeNode#isRoot()}
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(testTree.getRoot().isRoot());
		assertFalse(testTree.getFirstChild().isRoot());
	}

	/**
	 * Test method for {@link BaseTreeNode#removeChild(ITreeNode)}
	 */
	@Test
	public void testRemoveChild()
	{
		Collection<BaseTreeNode<String, Long>> children;

		children = testTree.getRoot().getChildren();
		assertTrue(children.contains(testTree.getFirstChild()));

		testTree.getRoot().removeChild(testTree.getFirstChild());

		children = testTree.getRoot().getChildren();
		assertFalse(children.contains(testTree.getFirstChild()));
	}

	/**
	 * Test method for {@link BaseTreeNode#removeChildren(Collection)}
	 */
	@Test
	public void testRemoveChildren()
	{
		Collection<BaseTreeNode<String, Long>> children;
		children = testTree.getRoot().getChildren();
		assertEquals(3, children.size());

		children = new ArrayList<>();
		children.add(testTree.getFirstChild());
		children.add(testTree.getSecondChild());

		testTree.getRoot().removeChildren(children);

		children = testTree.getRoot().getChildren();
		assertEquals(1, children.size());
		assertFalse(children.contains(testTree.getFirstChild()));
		assertFalse(children.contains(testTree.getSecondChild()));
		assertTrue(children.contains(testTree.getThirdChild()));
	}

	/**
	 * Test method for {@link BaseTreeNode#removeChildren()}
	 */
	@Test
	public void testRemoveAllChildren()
	{
		Collection<BaseTreeNode<String, Long>> children;
		children = testTree.getRoot().getChildren();
		assertEquals(3, children.size());

		testTree.getRoot().removeChildren();

		children = testTree.getRoot().getChildren();
		assertEquals(0, children.size());
	}

	/**
	 * Test method for {@link BaseTreeNode#containsAll(Collection)}
	 */
	@Test
	public void testContainsAll()
	{

		boolean actual;
		boolean expected;
		Collection<BaseTreeNode<String, Long>> children;

		children = testTree.getSecondChild().getChildren();

		actual = testTree.getRoot().containsAll(children);
		expected = true;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link BaseTreeNode#contains(ITreeNode)}
	 */
	@Test
	public void testContains()
	{
		boolean actual;
		boolean expected;

		actual = testTree.getRoot().contains(testTree.getFirstGrandGrandGrandChild());
		expected = true;
		assertEquals(actual, expected);

		actual = testTree.getFirstGrandGrandGrandChild().contains(testTree.getRoot());
		expected = false;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link BaseTreeNode#addChildren(Collection)}
	 */
	@Test
	public void testAddChildren()
	{
		Set<BaseTreeNode<String, Long>> children;
		BaseTreeNode<String, Long> fourthChild;
		BaseTreeNode<String, Long> fifthChild;

		fourthChild = BaseTreeNode.<String, Long> builder().id(testTree.getIdGenerator().getNextId()).parent(testTree.getRoot())
			.value("I'm the first child").build();

		fifthChild = BaseTreeNode.<String, Long> builder().id(testTree.getIdGenerator().getNextId()).parent(testTree.getRoot())
			.value("I'm the second child").build();
		children = SetFactory.newLinkedHashSet(fourthChild, fifthChild);
		testTree.getRoot().addChildren(children);
		Collection<BaseTreeNode<String, Long>> rootChildren = testTree.getRoot().getChildren();
		assertTrue(rootChildren.contains(fourthChild));
		assertTrue(rootChildren.contains(fifthChild));
	}

}

