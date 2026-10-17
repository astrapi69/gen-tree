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
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IntegerBinaryTreeTest
{

	@Test
	public void testGenericBinaryTreeWithInteger()
	{
		IntegerBinaryTree integerBinaryTree = new IntegerBinaryTree();

		integerBinaryTree.add(5).add(3).add(7).add(2).add(4).add(6).add(8);

		assertTrue(integerBinaryTree.contains(3));
		assertTrue(integerBinaryTree.contains(5));
		assertFalse(integerBinaryTree.contains(9));
	}

	/**
	 * The values to look up in the tree built from 5, 3, 7, 2, 4, 6 and 8, one row per value: the
	 * value and whether the tree contains it. The values smaller and greater than the first one are
	 * stored on different sides, so both sides are looked up
	 *
	 * @return the rows
	 */
	@DataProvider
	public static Object[][] containsRows()
	{
		return new Object[][] { { 5, true }, { 3, true }, { 2, true }, { 4, true }, { 7, true },
				{ 6, true }, { 8, true }, { 1, false }, { 9, false }, { 0, false } };
	}

	/**
	 * Test method for {@link GenericBinaryTree#contains(Object)} with every value that was added
	 * and with values that were not
	 *
	 * @param value
	 *            the value to look up
	 * @param expected
	 *            whether the tree contains the value
	 */
	@Test(dataProvider = "containsRows")
	public void testContains(final Integer value, final boolean expected)
	{
		IntegerBinaryTree integerBinaryTree = new IntegerBinaryTree();

		integerBinaryTree.add(5).add(3).add(7).add(2).add(4).add(6).add(8);

		assertEquals(expected, integerBinaryTree.contains(value));
	}

	/**
	 * Test method for {@link GenericBinaryTree#add(Object)} with a value that is already in the
	 * tree: it is neither smaller nor greater than itself, so it is not added a second time
	 */
	@Test
	public void testAddDuplicateValue()
	{
		IntegerBinaryTree integerBinaryTree = new IntegerBinaryTree();

		integerBinaryTree.add(5).add(5);

		assertEquals(Integer.valueOf(5), integerBinaryTree.first.getValue());
		assertNull(integerBinaryTree.first.getNext());
		assertNull(integerBinaryTree.first.getPrevious());

		integerBinaryTree.add(7).add(7);

		assertEquals(Integer.valueOf(7), integerBinaryTree.first.getPrevious().getValue());
		assertNull(integerBinaryTree.first.getPrevious().getNext());
		assertNull(integerBinaryTree.first.getPrevious().getPrevious());
	}
}
