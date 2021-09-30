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
import static org.testng.AssertJUnit.assertNotNull;

import java.util.List;

import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.AbstractTestCase;
import io.github.astrapi69.tree.api.IChainableTreeNode;

/**
 * The unit test class for the class {@link ChainableTreeNode}
 */
public class ChainableTreeNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	TreeElement fifthElement;
	ChainableTreeNode<TreeElement> fifthTreeNode;
	TreeElement firstElement;
	ChainableTreeNode<TreeElement> firstTreeNode;
	TreeElement fourthElement;
	ChainableTreeNode<TreeElement> fourthTreeNode;
	TreeElement secondElement;
	ChainableTreeNode<TreeElement> secondTreeNode;
	TreeElement thirdElement;
	ChainableTreeNode<TreeElement> thirdTreeNode;

	@BeforeMethod
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		firstElement = TreeElement.builder().name("first ").parent(null).build();
		secondElement = TreeElement.builder().name("second ").parent(firstElement).build();
		thirdElement = TreeElement.builder().name("third ").parent(secondElement).build();
		fourthElement = TreeElement.builder().name("fourth ").parent(thirdElement).build();
		fifthElement = TreeElement.builder().name("fifth.").parent(fourthElement).build();
		firstTreeNode = ChainableTreeNode.<TreeElement> builder().value(firstElement).build();
		secondTreeNode = ChainableTreeNode.<TreeElement> builder().value(secondElement)
			.parent(firstTreeNode).build();
		thirdTreeNode = ChainableTreeNode.<TreeElement> builder().value(thirdElement)
			.parent(secondTreeNode).build();
		fourthTreeNode = ChainableTreeNode.<TreeElement> builder().value(fourthElement)
			.parent(thirdTreeNode).build();
		fifthTreeNode = ChainableTreeNode.<TreeElement> builder().value(fifthElement)
			.parent(fourthTreeNode).build();

		firstTreeNode.setChild(secondTreeNode);
		secondTreeNode.setChild(thirdTreeNode);
		thirdTreeNode.setChild(fourthTreeNode);
		fourthTreeNode.setChild(fifthTreeNode);
	}

	@AfterMethod
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	@Test(enabled = true)
	public void testConcatenate()
	{
		String actual;
		String expected;
		TreeElement current;
		IChainableTreeNode<TreeElement> currentChild;
		IChainableTreeNode<TreeElement> currentTreeNode = firstTreeNode;
		StringBuilder sb = new StringBuilder();
		do
		{
			current = currentTreeNode.getValue();
			sb.append(current.getName());
			currentChild = currentTreeNode.getChild();
			currentTreeNode = currentChild;
		}
		while (currentChild != null);
		actual = sb.toString();
		expected = "first second third fourth fifth.";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link ChainableTreeNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		IChainableTreeNode<TreeElement> parentTreeNode = new ChainableTreeNode<>();
		assertNotNull(parentTreeNode);
		parentTreeNode.setValue(firstElement);
		parentTreeNode = new ChainableTreeNode<>(firstElement);
		assertNotNull(parentTreeNode);
	}

	/**
	 * Test method for {@link ChainableTreeNode#getChildren()}
	 */
	@Test
	public final void testGetChildren()
	{
		int actual;
		int expected;
		List<IChainableTreeNode<TreeElement>> list;
		// scenario
		// 1
		list = fifthTreeNode.getChildren();
		assertNotNull(list);
		actual = list.size();
		expected = 0;
		assertEquals(actual, expected);
		// 2
		list = firstTreeNode.getChildren();
		assertNotNull(list);
		actual = list.size();
		expected = 4;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link ChainableTreeNode#toList()}
	 */
	@Test
	public final void testToList()
	{
		int actual;
		int expected;
		List<IChainableTreeNode<TreeElement>> list;
		// scenario
		// 1
		list = firstTreeNode.toList();
		assertNotNull(list);
		actual = list.size();
		expected = 5;
		assertEquals(actual, expected);
		// 2
		list = fifthTreeNode.toList();
		assertNotNull(list);
		actual = list.size();
		expected = 5;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link ChainableTreeNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("parent", new Factory<ChainableTreeNode<TreeElement>>()
			{

				@Override
				public ChainableTreeNode<TreeElement> create()
				{
					return firstTreeNode;
				}

			}).overrideFactory("child", new Factory<ChainableTreeNode<TreeElement>>()
			{

				@Override
				public ChainableTreeNode<TreeElement> create()
				{
					return secondTreeNode;
				}

			}).build();
		final BeanTester beanTester = new BeanTester();
		beanTester.addCustomConfiguration(ChainableTreeNode.class, configuration);
		beanTester.testBean(ChainableTreeNode.class);
	}
}
