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
package io.github.astrapi69.tree.element;

import static org.testng.AssertJUnit.assertNotNull;

import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.testng.annotations.Test;

import io.github.astrapi69.tree.TreeNode;

/**
 * The unit test class for the class {@link TreeElementNode}
 */
public class TreeElementNodeTest
{

	/**
	 * Test method for {@link TreeElementNode} constructors
	 */
	@Test
	public final void testConstructors()
	{
		TreeElement model;
		TreeElementNode treeElementNode;

		model = new TreeElement("name");
		assertNotNull(model);
		model = TreeElement.builder().build();
		assertNotNull(model);
		treeElementNode = new TreeElementNode(model);
		assertNotNull(treeElementNode);

		treeElementNode = TreeElementNode.builder().build();
		assertNotNull(treeElementNode);
	}

	/**
	 * Test method for {@link TreeElementNode}
	 */
	@Test
	public void testWithBeanTester()
	{
		final TreeElementNode parentTreeNode = new TreeElementNode();
		Configuration configuration = new ConfigurationBuilder()
			.overrideFactory("parent", new Factory<TreeElementNode>()
			{

				@Override
				public TreeElementNode create()
				{
					return parentTreeNode;
				}

			}).build();
		final BeanTester beanTester = new BeanTester();
		beanTester.addCustomConfiguration(TreeNode.class, configuration);
		beanTester.testBean(TreeNode.class);
	}
}
