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
package io.github.astrapi69.gen.tree.convert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.meanbean.test.BeanTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.collection.set.SetFactory;
import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.TreeIdNode;
import io.github.astrapi69.gen.tree.element.GenericTreeElement;
import io.github.astrapi69.gen.tree.element.MysticCryptEntryModelBean;
import io.github.astrapi69.id.generate.LongIdGenerator;

/**
 * The unit test class for the class {@link BaseTreeNodeTransformer}
 */
public class BaseTreeNodeTransformerTest
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
	 * Test method for {@link BaseTreeNodeTransformer#toKeyMap(BaseTreeNode)}
	 */
	@Test
	public void testToKeyMap()
	{
		Map<Long, TreeIdNode<String, Long>> actual;

		actual = BaseTreeNodeTransformer.toKeyMap(root);
		assertNotNull(actual);
		assertEquals(actual.size(), 12);
	}

	/**
	 * Test method for {@link BaseTreeNodeTransformer#toKeyMap(BaseTreeNode)}
	 */
	@Test
	public void testGetRoot()
	{
		BaseTreeNode<String, Long> actual;
		BaseTreeNode<String, Long> expected;

		Map<Long, TreeIdNode<String, Long>> longTreeIdNodeMap = BaseTreeNodeTransformer
			.toKeyMap(root);
		actual = BaseTreeNodeTransformer.getRoot(longTreeIdNodeMap);
		expected = root;
		assertEquals(actual, expected);

		Map<Long, TreeIdNode<String, Long>> emptyMap = new LinkedHashMap<>();
		actual = BaseTreeNodeTransformer.getRoot(emptyMap);
		expected = null;
		assertEquals(actual, expected);
		// =========================================================================================
		BaseTreeNode<GenericTreeElement<List<MysticCryptEntryModelBean>>, Long> rootTreeNode;
		Map<Long, TreeIdNode<GenericTreeElement<List<MysticCryptEntryModelBean>>, Long>> rootTreeAsMap;

		LongIdGenerator idGenerator = LongIdGenerator.of(0L);
		GenericTreeElement<List<MysticCryptEntryModelBean>> parent = GenericTreeElement
			.<List<MysticCryptEntryModelBean>> builder().name("root")
			.iconPath("io/github/astrapi69/silk/icons/book.png").withText(true).build()
			.setDefaultContent(new ArrayList<>());

		GenericTreeElement<List<MysticCryptEntryModelBean>> firstChild = GenericTreeElement
			.<List<MysticCryptEntryModelBean>> builder().name("mykeys")
			.iconPath("io/github/astrapi69/silk/icons/folder.png").withText(true).build()
			.setDefaultContent(new ArrayList<>());
		rootTreeNode = BaseTreeNodeFactory.initializeTreeNodeWithTreeElement(parent, null,
			idGenerator);
		BaseTreeNodeFactory.initializeTreeNodeWithTreeElement(firstChild, rootTreeNode,
			idGenerator);

		rootTreeAsMap = BaseTreeNodeTransformer.toKeyMap(rootTreeNode);

		rootTreeNode = BaseTreeNodeTransformer.getRoot(rootTreeAsMap);
		assertNotNull(rootTreeNode);

		GenericTreeElement<List<MysticCryptEntryModelBean>> treeElement = GenericTreeElement
			.<List<MysticCryptEntryModelBean>> builder().name("foo").leaf(true).build();
		BaseTreeNode<GenericTreeElement<List<MysticCryptEntryModelBean>>, Long> newTreeNode = BaseTreeNode
			.<GenericTreeElement<List<MysticCryptEntryModelBean>>, Long> builder()
			.id(idGenerator.getNextId()).value(treeElement).parent(rootTreeNode)
			.displayValue(treeElement.getName()).leaf(treeElement.isLeaf()).build();
		rootTreeNode.addChild(newTreeNode);
	}

	/**
	 * Test method for {@link BaseTreeNodeTransformer#toTreeIdNode(BaseTreeNode)}
	 */
	@Test
	public void testToTreeIdNode()
	{
		TreeIdNode<String, Long> actual;
		TreeIdNode<String, Long> expected;

		actual = BaseTreeNodeTransformer.toTreeIdNode(root);
		expected = TreeIdNode.<String, Long> builder().id(root.getId()).leaf(root.isLeaf())
			.childrenIds(SetFactory.newLinkedHashSet(1L, 2L, 9L)).value(root.getValue()).build();
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link BaseTreeNodeTransformer#transform(Map)}
	 */
	@Test
	public void testTransform()
	{
		Map<Long, TreeIdNode<String, Long>> actual;

		actual = BaseTreeNodeTransformer.toKeyMap(root);
		assertNotNull(actual);
		assertEquals(actual.size(), 12);
		Map<Long, BaseTreeNode<String, Long>> convert = BaseTreeNodeTransformer.transform(actual);

		assertEquals(convert.size(), 12);

		Map<Long, BaseTreeNode<String, Long>> longBaseTreeNodeMap = BaseTreeNodeTransformer
			.toKeyBaseTreeNodeMap(root);
		assertEquals(convert, longBaseTreeNodeMap);
	}

	/**
	 * Test method for {@link BaseTreeNodeTransformer}
	 */
	@Test
	public void testWithBeanTester()
	{
		final BeanTester beanTester = new BeanTester();
		beanTester.testBean(BaseTreeNodeTransformer.class);
	}

}
