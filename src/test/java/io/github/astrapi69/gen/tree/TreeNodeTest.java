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
import io.github.astrapi69.id.generate.LongIdGenerator;
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

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * The unit test class for the class {@link TreeNode}
 */
public class TreeNodeTest extends AbstractTestCase<Boolean, Boolean>
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
	 * {@inheritDoc}
	 */
	@AfterMethod
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
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
	 * Test method for {@link TreeNode#isLeaf()}
	 */
	@Test
	public void testIsLeaf()
	{
		assertFalse(root.isLeaf());
		assertTrue(fifthGrandChild.isLeaf());
	}

	/**
	 * Test method for {@link TreeNode#isNode()}
	 */
	@Test
	public void testIsNode()
	{
		assertTrue(root.isNode());
		assertFalse(fifthGrandChild.isNode());
	}

	/**
	 * Test method for {@link TreeNode#findAllByValue(Object)}
	 */
	@Test
	public void testFindAllByValue()
	{
		Collection<TreeNode<String>> actual;
		Collection<TreeNode<String>> expected;

		TreeNode<String> treeNode = TreeNode.<String> builder().parent(thirdChild)
			.value(fifthGrandChildValue).build();
		thirdChild.addChild(treeNode);

		actual = root.findAllByValue(fifthGrandChildValue);
		expected = SetFactory.newLinkedHashSet(fifthGrandChild, treeNode);
		assertEquals(actual, expected);

		actual = root.findAllByValue(null);
		expected = SetFactory.newLinkedHashSet(thirdGrandChild, fourthGrandChild);
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link TreeNode#clearAll()}
	 */
	@Test
	public void testClearAll()
	{
		root.clearAll();
		assertEquals(root.getChildren().size(), 0);
	}

	/**
	 * Test method for {@link TreeNode#clearChildren()}
	 */
	@Test
	public void testClearChildren()
	{
		root.clearChildren();
		assertEquals(root.getChildren().size(), 0);
	}

	/**
	 * Test method for {@link TreeNode#findByValue(Object)}
	 */
	@Test
	public void testFindByValue()
	{
		TreeNode<String> actual;
		TreeNode<String> expected;

		TreeNode<String> treeNode = TreeNode.<String> builder().parent(thirdChild)
			.value(fifthGrandChildValue).build();
		thirdChild.addChild(treeNode);

		actual = root.findByValue(fifthGrandChildValue);
		expected = fifthGrandChild;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link BaseTreeNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		final TreeNode<String> parentTreeNode = new TreeNode<>("parent");
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("parent", new Factory<TreeNode<String>>()
			{

				@Override
				public TreeNode<String> create()
				{
					return parentTreeNode;
				}

			}).build();
		final BeanTester beanTester = new BeanTester();
		beanTester.testBean(TreeNode.class, configuration);
	}

	/**
	 * Test method for {@link BaseTreeNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		TreeNode<String> parentTreeNode = new TreeNode<>();
		assertNotNull(parentTreeNode);
		assertNotNull(parentTreeNode.getChildren());
		parentTreeNode.setValue("parent");
		parentTreeNode = new TreeNode<>("parent");
		assertNotNull(parentTreeNode);
		BaseTreeNode<TreeElement, Long> treeNode = BaseTreeNode.<TreeElement, Long> builder()
			.build();
		assertNotNull(treeNode);
		assertTrue(treeNode.isNode());
	}

	/**
	 * Test method for {@link TreeNode#traverse()}
	 */
	@Test
	public void testTraverse()
	{
		Collection<TreeNode<String>> subtree;

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
	 * Test method for {@link TreeNode#toList()}
	 */
	@Test
	public void testToList()
	{
		List<TreeNode<String>> subtree;

		subtree = root.toList();
		assertEquals(12, subtree.size());

		subtree = firstChild.toList();
		assertEquals(1, subtree.size());

		subtree = secondChild.toList();
		assertEquals(7, subtree.size());

		subtree = thirdChild.toList();
		assertEquals(3, subtree.size());

		subtree = firstGrandChild.toList();
		assertEquals(4, subtree.size());

		subtree = firstGrandGrandChild.toList();
		assertEquals(1, subtree.size());

		subtree = secondGrandGrandChild.toList();
		assertEquals(2, subtree.size());

		subtree = firstGrandGrandGrandChild.toList();
		assertEquals(1, subtree.size());

		subtree = secondGrandChild.toList();
		assertEquals(1, subtree.size());

		subtree = thirdGrandChild.toList();
		assertEquals(1, subtree.size());

		subtree = fourthGrandChild.toList();
		assertEquals(1, subtree.size());

		subtree = fifthGrandChild.toList();
		assertEquals(1, subtree.size());
	}

	/**
	 * Test method for {@link TreeNode#getAllSiblings()}
	 */
	@Test
	public void testGetAllSiblings()
	{
		Collection<TreeNode<String>> allSiblings;

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
	 * Test method for {@link TreeNode#getChildCount()}
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;

		Collection<TreeNode<String>> children = root.getChildren();

		actual = root.getChildCount();
		expected = children.size();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link TreeNode#getLevel()}
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
	 * Test method for {@link TreeNode#getNextSibling()}
	 */
	@Test
	public void testGetNextSibling()
	{
		TreeNode<String> actual;
		TreeNode<String> expected;

		actual = firstChild.getNextSibling();
		expected = secondChild;
		assertEquals(expected, actual);

		actual = root.getNextSibling();
		assertNull(actual);
	}

	/**
	 * Test method for {@link TreeNode#getPreviousSibling()}
	 */
	@Test
	public void testGetPreviousSibling()
	{
		TreeNode<String> actual;
		TreeNode<String> expected;

		actual = secondChild.getPreviousSibling();
		expected = firstChild;
		assertEquals(expected, actual);

		actual = root.getPreviousSibling();
		assertNull(actual);
	}

	/**
	 * Test method for {@link TreeNode#getRoot()}
	 */
	@Test
	public void testGetRoot()
	{
		TreeNode<String> currentRoot;

		currentRoot = root.getRoot();
		assertEquals(currentRoot, root);

		currentRoot = firstGrandGrandGrandChild.getRoot();
		assertEquals(currentRoot, root);
	}

	/**
	 * Test method for {@link TreeNode#hasChildren()}
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(firstGrandGrandGrandChild.hasChildren());
		assertTrue(root.hasChildren());
	}

	/**
	 * Test method for {@link TreeNode#hasParent()}
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(root.hasParent());
		assertTrue(firstGrandGrandGrandChild.hasParent());
	}

	/**
	 * Test method for {@link TreeNode#isRoot()}
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(root.isRoot());
		assertFalse(firstChild.isRoot());
	}

	/**
	 * Test method for {@link TreeNode#removeChild(ITreeNode)}
	 */
	@Test
	public void testRemoveChild()
	{
		Collection<TreeNode<String>> children;

		children = root.getChildren();
		assertTrue(children.contains(firstChild));

		root.removeChild(firstChild);

		children = root.getChildren();
		assertFalse(children.contains(firstChild));
	}

	/**
	 * Test method for {@link TreeNode#removeChildren(Collection)}
	 */
	@Test
	public void testRemoveChildren()
	{
		Collection<TreeNode<String>> children;
		children = root.getChildren();
		assertEquals(3, children.size());

		children = new ArrayList<>();
		children.add(firstChild);
		children.add(secondChild);

		root.removeChildren(children);

		children = root.getChildren();
		assertEquals(1, children.size());
		assertFalse(children.contains(firstChild));
		assertFalse(children.contains(secondChild));
		assertTrue(children.contains(thirdChild));
	}


	/**
	 * Test method for {@link TreeNode#removeChildren()}
	 */
	@Test
	public void testRemoveAllChildren()
	{
		Collection<TreeNode<String>> children;

		children = root.getChildren();
		assertEquals(3, children.size());

		root.removeChildren();

		children = root.getChildren();
		assertEquals(0, children.size());
	}

	/**
	 * Test method for {@link TreeNode#containsAll(Collection)}
	 */
	@Test
	public void testContainsAll()
	{
		boolean actual;
		boolean expected;
		Collection<TreeNode<String>> children;

		children = secondChild.getChildren();

		actual = root.containsAll(children);
		expected = true;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link TreeNode#contains(ITreeNode)}
	 */
	@Test
	public void testContains()
	{
		boolean actual;
		boolean expected;

		actual = root.contains(firstGrandGrandGrandChild);
		expected = true;
		assertEquals(actual, expected);

		actual = firstGrandGrandGrandChild.contains(root);
		expected = false;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link TreeNode#addChildren(Collection)}
	 */
	@Test
	public void testAddChildren()
	{
		Set<TreeNode<String>> children;
		TreeNode<String> fourthChild;
		TreeNode<String> fifthChild;

		fourthChild = TreeNode.<String> builder().parent(root).value("I'm the first child").build();

		fifthChild = TreeNode.<String> builder().parent(root).value("I'm the second child").build();
		children = SetFactory.newLinkedHashSet(fourthChild, fifthChild);
		root.addChildren(children);
		Collection<TreeNode<String>> rootChildren = root.getChildren();
		assertTrue(rootChildren.contains(fourthChild));
		assertTrue(rootChildren.contains(fifthChild));
	}

}
