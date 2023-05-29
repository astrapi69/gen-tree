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
package io.github.astrapi69.gen.tree.binary;

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
import io.github.astrapi69.gen.tree.element.TreeElement;

/**
 * The unit test class for the class {@link LinkedNode}
 */
public class LinkedNodeTest extends AbstractTestCase<Boolean, Boolean>
{

	TreeElement fifthElement;
	LinkedNode<TreeElement> fifthTreeNode;
	TreeElement firstElement;
	LinkedNode<TreeElement> firstTreeNode;
	TreeElement fourthElement;
	LinkedNode<TreeElement> fourthTreeNode;
	TreeElement secondElement;
	LinkedNode<TreeElement> secondTreeNode;
	TreeElement thirdElement;
	LinkedNode<TreeElement> thirdTreeNode;

	/**
	 * {@inheritDoc}
	 */
	@BeforeMethod
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		firstElement = TreeElement.builder().name("first ").build();
		secondElement = TreeElement.builder().name("second ").build();
		thirdElement = TreeElement.builder().name("third ").build();
		fourthElement = TreeElement.builder().name("fourth ").build();
		fifthElement = TreeElement.builder().name("fifth.").build();
		firstTreeNode = LinkedNode.<TreeElement> builder().value(firstElement).build();
		secondTreeNode = LinkedNode.<TreeElement> builder().value(secondElement)
			.previous(firstTreeNode).build();
		thirdTreeNode = LinkedNode.<TreeElement> builder().value(thirdElement)
			.previous(secondTreeNode).build();
		fourthTreeNode = LinkedNode.<TreeElement> builder().value(fourthElement)
			.previous(thirdTreeNode).build();
		fifthTreeNode = LinkedNode.<TreeElement> builder().value(fifthElement)
			.previous(fourthTreeNode).build();

		firstTreeNode.setNext(secondTreeNode);
		secondTreeNode.setNext(thirdTreeNode);
		thirdTreeNode.setNext(fourthTreeNode);
		fourthTreeNode.setNext(fifthTreeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	@AfterMethod
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	@Test
	public void testConcatenate()
	{
		String actual;
		String expected;
		TreeElement current;
		LinkedNode<TreeElement> currentNext;
		LinkedNode<TreeElement> currentTreeNode = firstTreeNode;
		StringBuilder sb = new StringBuilder();
		do
		{
			current = currentTreeNode.getValue();
			sb.append(current.getName());
			currentNext = currentTreeNode.getNext();
			currentTreeNode = currentNext;
		}
		while (currentNext != null);
		actual = sb.toString();
		expected = "first second third fourth fifth.";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link LinkedNode} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		LinkedNode<TreeElement> parentTreeNode = new LinkedNode<>();
		assertNotNull(parentTreeNode);
		parentTreeNode.setValue(firstElement);
	}

	/**
	 * Test method for {@link LinkedNode#toList()}
	 */
	@Test
	public final void testToList()
	{
		int actual;
		int expected;
		List<LinkedNode<TreeElement>> list = firstTreeNode.toList();
		assertNotNull(list);
		actual = list.size();
		expected = 5;
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link LinkedNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("previous", (Factory<LinkedNode<TreeElement>>)() -> firstTreeNode)
			.overrideFactory("next", (Factory<LinkedNode<TreeElement>>)() -> secondTreeNode)
			.build();
		final BeanTester beanTester = new BeanTester();
		beanTester.testBean(LinkedNode.class, configuration);
	}
}
