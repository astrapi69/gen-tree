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
package io.github.astrapi69.tree.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.meanbean.test.BeanTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.collections.set.SetFactory;
import io.github.astrapi69.id.generate.LongIdGenerator;
import io.github.astrapi69.tree.BaseTreeNode;
import io.github.astrapi69.tree.TreeIdNode;

/**
 * The unit test class for the class {@link TreeNodeTransformer}
 */
public class TreeNodeTransformerTest
{

	BaseTreeNode<String, Long> root;
	BaseTreeNode<String, Long> firstChild;
	BaseTreeNode<String, Long> secondChild;
	BaseTreeNode<String, Long> firstGrandChild;
	BaseTreeNode<String, Long> firstGrandGrandChild;
	BaseTreeNode<String, Long> secondGrandGrandChild;
	BaseTreeNode<String, Long> firstGrandGrandGrandChild;
	BaseTreeNode<String, Long> secondGrandChild;
	BaseTreeNode<String, Long> thirdGrandChild;
	BaseTreeNode<String, Long> thirdChild;
	BaseTreeNode<String, Long> fourthGrandChild;
	BaseTreeNode<String, Long> fifthGrandChild;

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
		root = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).value("I'm root")
			.build();

		firstChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.value("I'm the first child").build();

		secondChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the second child").build();

		firstGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(secondChild).value("I'm the first grand child").build();

		firstGrandGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(firstGrandChild).value("I'm the first grand grand child").build();

		secondGrandGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(firstGrandChild).value("I'm the second grand grand child").build();

		firstGrandGrandGrandChild = BaseTreeNode.<String, Long> builder()
			.id(idGenerator.getNextId()).parent(secondGrandGrandChild)
			.value("I'm the first grand grand grand child").build();

		secondGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(secondChild).value("I'm the second grand child").build();

		thirdGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(secondChild).value(null).build();

		thirdChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the third child").build();

		fourthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(thirdChild).value(null).build();

		fifthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(thirdChild).value("I'm the fifth grand child").build();

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
	 * Test method for {@link TreeNodeTransformer#toKeyMap(BaseTreeNode)}
	 */
	@Test
	public void testToKeyMap()
	{
		Map<Long, TreeIdNode<String, Long>> actual;

		actual = TreeNodeTransformer.toKeyMap(root);
		assertNotNull(actual);
		assertEquals(actual.size(), 12);
	}

	/**
	 * Test method for {@link TreeNodeTransformer#toKeyMap(BaseTreeNode)}
	 */
	@Test
	public void testGetRoot()
	{
		BaseTreeNode<String, Long> actual;
		BaseTreeNode<String, Long> expected;

		actual = TreeNodeTransformer.getRoot(TreeNodeTransformer.toKeyMap(root));
		expected = root;
		assertEquals(actual, expected);

		Map<Long, TreeIdNode<String, Long>> emptyMap = new LinkedHashMap<>();
		actual = TreeNodeTransformer.getRoot(emptyMap);
		expected = null;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link TreeNodeTransformer#toTreeIdNode(BaseTreeNode)}
	 */
	@Test
	public void testToTreeIdNode()
	{
		TreeIdNode<String, Long> actual;
		TreeIdNode<String, Long> expected;

		actual = TreeNodeTransformer.toTreeIdNode(root);
		expected = TreeIdNode.<String, Long> builder().id(root.getId()).leaf(root.isLeaf())
			.childrenIds(SetFactory.newLinkedHashSet(1L, 2L, 9L)).value(root.getValue()).build();
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link TreeNodeTransformer#transform(Map)}
	 */
	@Test
	public void testTransform()
	{
		Map<Long, TreeIdNode<String, Long>> actual;

		actual = TreeNodeTransformer.toKeyMap(root);
		assertNotNull(actual);
		assertEquals(actual.size(), 12);
		Map<Long, BaseTreeNode<String, Long>> convert = TreeNodeTransformer.transform(actual);

		assertEquals(convert.size(), 12);

		Map<Long, BaseTreeNode<String, Long>> longBaseTreeNodeMap = TreeNodeTransformer
			.toKeyBaseTreeNodeMap(root);
		assertEquals(convert, longBaseTreeNodeMap);
	}

	/**
	 * Test method for {@link TreeNodeTransformer}
	 */
	@Test
	public void testWithBeanTester()
	{
		final BeanTester beanTester = new BeanTester();
		beanTester.testBean(TreeNodeTransformer.class);
	}

}
