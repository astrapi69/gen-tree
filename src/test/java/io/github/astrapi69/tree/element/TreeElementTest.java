/**
 * The MIT License
 * <p>
 * Copyright (C) 2015 Asterios Raptis
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.tree.element;

import io.github.astrapi69.evaluate.object.evaluator.EqualsHashCodeAndToStringEvaluator;
import org.meanbean.test.BeanTester;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * The unit test class for the class {@link TreeElement}
 */
public class TreeElementTest
{

	/**
	 * Test method for {@link TreeElement} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		TreeElement model = new TreeElement();
		assertNotNull(model);
		model = new TreeElement("name");
		assertNotNull(model);
		model = TreeElement.builder().build();
		assertNotNull(model);
	}

	/**
	 * Test method for {@link TreeElement#equals(Object)} , {@link TreeElement#hashCode()} and
	 * {@link TreeElement#toString()}
	 */
	@Test
	public void testEqualsHashcodeAndToStringWithClassSilently()
	{
		boolean expected;
		boolean actual;
		actual = EqualsHashCodeAndToStringEvaluator
			.evaluateEqualsHashcodeAndToString(TreeElement.builder().build());
		expected = true;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link TreeElement}
	 */
	@Test
	public void testWithBeanTester()
	{
		final BeanTester beanTester = new BeanTester();
		beanTester.testBean(TreeElement.class);
	}

}
