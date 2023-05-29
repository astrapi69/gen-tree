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
import io.github.astrapi69.evaluate.object.evaluator.EqualsHashCodeAndToStringEvaluator;
import io.github.astrapi69.gen.tree.element.TreeElement;
import io.github.astrapi69.id.generate.LongIdGenerator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * The unit test class for the class {@link TreeIdNode}
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreeIdNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	TreeIdNode<String, Long> root;
	TreeIdNode<String, Long> firstChild;
	TreeIdNode<String, Long> secondChild;
	TreeIdNode<String, Long> firstGrandChild;
	TreeIdNode<String, Long> firstGrandGrandChild;
	TreeIdNode<String, Long> secondGrandGrandChild;
	TreeIdNode<String, Long> firstGrandGrandGrandChild;
	TreeIdNode<String, Long> secondGrandChild;
	TreeIdNode<String, Long> thirdGrandChild;
	TreeIdNode<String, Long> thirdChild;
	TreeIdNode<String, Long> fourthGrandChild;
	TreeIdNode<String, Long> fifthGrandChild;
	String fifthGrandChildValue;
	LongIdGenerator idGenerator;
	TreeElement parent;

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
		parent = TreeElement.builder().name("parent").build();

		idGenerator = LongIdGenerator.of(0L);
		root = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId()).value("I'm root")
			.build();

		firstChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(root.getId()).value("I'm the first child").build();

		secondChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(root.getId()).value("I'm the second child").build();

		firstGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(secondChild.getId()).value("I'm the first grand child").build();

		firstGrandGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(firstGrandChild.getId()).value("I'm the first grand grand child").build();

		secondGrandGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(firstGrandChild.getId()).value("I'm the second grand grand child").build();

		firstGrandGrandGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(secondGrandGrandChild.getId()).value("I'm the first grand grand grand child")
			.build();

		secondGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(secondChild.getId()).value("I'm the second grand child").build();

		thirdGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(secondChild.getId()).value(null).build();

		thirdChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(root.getId()).value("I'm the third child").build();

		fourthGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId())
			.parentId(thirdChild.getId()).value(null).build();
		fifthGrandChildValue = "I'm the fifth grand child";
		fifthGrandChild = TreeIdNode.<String, Long> builder().id(idGenerator.getNextId()).leaf(true)
			.parentId(thirdChild.getId()).value(fifthGrandChildValue).build();

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

	/**
	 * Test method for {@link TreeIdNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		TreeElement parent = TreeElement.builder().name("parent").build();
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
	 * Test method for {@link TreeIdNode#getChildCount()}
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;

		Set<Long> childrenIds = root.getChildrenIds();

		actual = root.getChildCount();
		expected = childrenIds.size();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link TreeIdNode#hasChildren()}
	 */
	@Test
	public void testHasChildren()
	{
		assertFalse(firstGrandGrandGrandChild.hasChildren());
		assertTrue(root.hasChildren());
	}

	/**
	 * Test method for {@link TreeIdNode#hasParent()}
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(root.hasParent());
		assertTrue(firstGrandGrandGrandChild.hasParent());
	}

	/**
	 * Test method for {@link TreeIdNode#isRoot()}
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(root.isRoot());
		assertFalse(firstChild.isRoot());
	}

	/**
	 * Test method for {@link TreeIdNode#removeChild(TreeIdNode)}
	 */
	@Test
	public void testRemoveChild()
	{
		Set<TreeIdNode<String, Long>> children;
		Set<Long> childrenIds;

		childrenIds = root.getChildrenIds();
		assertTrue(childrenIds.contains(firstChild.getId()));

		root.removeChild(firstChild);

		childrenIds = root.getChildrenIds();
		assertFalse(childrenIds.contains(firstChild.getId()));
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
		beanTester.testBean(TreeIdNode.class, configuration);
	}

}
