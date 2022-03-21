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
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.tree.visitor.DisplayValueOfSimpleTreeNodeVisitor;
import io.github.astrapi69.tree.visitor.TraverseSimpleTreeNodeVisitor;

public class SimpleTreeNodeTest
{
	SimpleTreeNode<String> root;
	SimpleTreeNode<String> firstChild;
	SimpleTreeNode<String> secondChild;
	SimpleTreeNode<String> firstGrandChild;
	SimpleTreeNode<String> firstGrandGrandChild;
	SimpleTreeNode<String> secondGrandGrandChild;
	SimpleTreeNode<String> firstGrandGrandGrandChild;
	SimpleTreeNode<String> secondGrandChild;
	SimpleTreeNode<String> thirdGrandChild;
	SimpleTreeNode<String> thirdChild;
	SimpleTreeNode<String> fourthGrandChild;
	SimpleTreeNode<String> fifthGrandChild;

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
		root = SimpleTreeNode.<String> builder().leftMostChild(firstChild).value("I'm root")
			.build();

		firstChild = SimpleTreeNode.<String> builder().parent(root).rightSibling(secondChild)
			.value("I'm the first child").build();

		secondChild = SimpleTreeNode.<String> builder().parent(root).leftMostChild(firstGrandChild)
			.rightSibling(thirdChild).value("I'm the second child").build();

		firstGrandChild = SimpleTreeNode.<String> builder().parent(secondChild)
			.leftMostChild(firstGrandGrandChild).rightSibling(secondGrandChild)
			.value("I'm the first grand child").build();

		firstGrandGrandChild = SimpleTreeNode.<String> builder().parent(firstGrandChild)
			.rightSibling(secondGrandGrandChild).value("I'm the first grand grand child").build();

		secondGrandGrandChild = SimpleTreeNode.<String> builder().parent(firstGrandChild)
			.leftMostChild(firstGrandGrandGrandChild).value("I'm the second grand grand child")
			.build();

		firstGrandGrandGrandChild = SimpleTreeNode.<String> builder().parent(secondGrandGrandChild)
			.value("I'm the first grand grand grand child").build();

		secondGrandChild = SimpleTreeNode.<String> builder().parent(secondChild)
			.rightSibling(thirdGrandChild).value("I'm the second grand child").build();

		thirdGrandChild = SimpleTreeNode.<String> builder().parent(secondChild).value(null).build();

		thirdChild = SimpleTreeNode.<String> builder().parent(root).leftMostChild(fourthGrandChild)
			.value("I'm the third child").build();

		fourthGrandChild = SimpleTreeNode.<String> builder().parent(thirdChild)
			.rightSibling(fifthGrandChild).value(null).build();

		fifthGrandChild = SimpleTreeNode.<String> builder().parent(thirdChild)
			.value("I'm the fifth grand child").build();

		// initialize left most child and right sibling

		root.setLeftMostChild(firstChild);

		firstChild.setRightSibling(secondChild);

		secondChild.setLeftMostChild(firstGrandChild);
		secondChild.setRightSibling(thirdChild);

		firstGrandChild.setLeftMostChild(firstGrandGrandChild);
		firstGrandChild.setRightSibling(secondGrandChild);

		firstGrandGrandChild.setRightSibling(secondGrandGrandChild);

		secondGrandGrandChild.setLeftMostChild(firstGrandGrandGrandChild);

		secondGrandChild.setRightSibling(thirdGrandChild);

		thirdChild.setLeftMostChild(fourthGrandChild);

		fourthGrandChild.setRightSibling(fifthGrandChild);
	}

	@Test
	public void testGetAllSilbings()
	{
		Set<SimpleTreeNode<String>> allSiblings = root.getAllSiblings();
		assertEquals(allSiblings.size(), 0);
		allSiblings = secondChild.getAllSiblings();
		assertEquals(allSiblings.size(), 3);
	}

	@Test
	public void testGetAllRightSiblings()
	{
		Set<SimpleTreeNode<String>> allRightSiblings = root.getAllSiblings();
		assertEquals(allRightSiblings.size(), 0);
		allRightSiblings = secondChild.getAllRightSiblings();
		assertEquals(allRightSiblings.size(), 1);
	}

	@Test
	public void testGetAllLeftSiblings()
	{
		Set<SimpleTreeNode<String>> allRightSiblings = root.getAllSiblings();
		assertEquals(allRightSiblings.size(), 0);
		allRightSiblings = secondChild.getAllLeftSiblings();
		assertEquals(allRightSiblings.size(), 1);
		allRightSiblings = firstChild.getAllLeftSiblings();
		assertEquals(allRightSiblings.size(), 0);
	}

	@Test
	public void testRightSilbing()
	{
		boolean hasRightSibling;
		SimpleTreeNode<String> rightSibling;

		rightSibling = root.getRightSibling();
		assertNull(rightSibling);
		assertFalse(root.hasRightSibling());

		hasRightSibling = firstChild.hasRightSibling();
		assertTrue(hasRightSibling);
		rightSibling = firstChild.getRightSibling();
		assertEquals(secondChild, rightSibling);

		hasRightSibling = secondChild.hasRightSibling();
		assertTrue(hasRightSibling);
		rightSibling = secondChild.getRightSibling();
		assertEquals(thirdChild, rightSibling);

		hasRightSibling = thirdChild.hasRightSibling();
		assertFalse(hasRightSibling);
		rightSibling = thirdChild.getRightSibling();
		assertNull(rightSibling);
	}

	@Test
	public void testVisitor()
	{
		root.accept(new DisplayValueOfSimpleTreeNodeVisitor<>());
		TraverseSimpleTreeNodeVisitor<String> traverseVisitor;
		Set<SimpleTreeNode<String>> allTreeNodes;
		traverseVisitor = new TraverseSimpleTreeNodeVisitor<>();
		root.accept(traverseVisitor);
		allTreeNodes = traverseVisitor.getAllTreeNodes();
		assertEquals(allTreeNodes.size(), 12);

		Set<SimpleTreeNode<String>> subTree = thirdChild.traverse();
		assertEquals(3, subTree.size());
	}

}
