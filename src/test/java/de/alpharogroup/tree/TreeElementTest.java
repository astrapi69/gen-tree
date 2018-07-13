package de.alpharogroup.tree;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

import org.meanbean.test.BeanTester;
import org.testng.annotations.Test;

import de.alpharogroup.evaluate.object.SilentEqualsHashCodeAndToStringEvaluator;

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
		model = new TreeElement("name", false, null);
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
		actual = SilentEqualsHashCodeAndToStringEvaluator
			.evaluateEqualsHashcodeAndToStringQuietly(TreeElement.class);
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
