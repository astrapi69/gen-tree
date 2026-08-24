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
package io.github.astrapi69.gen.tree.handler;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertSame;

import java.util.Collection;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.id.generate.LongIdGenerator;

/**
 * The unit test class for the class {@link IBaseTreeNodeHandlerExtensions}
 */
public class IBaseTreeNodeHandlerExtensionsTest
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
	String fifthGrandChildValue;
	LongIdGenerator idGenerator;

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
		idGenerator = LongIdGenerator.of(0L);
		root = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).value("I'm root")
			.build();

		firstChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
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
		fifthGrandChildValue = "I'm the fifth grand child";
		fifthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(thirdChild).leaf(true).value(fifthGrandChildValue).build();

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
	 * Builds a small, independent {@link BaseTreeNode} tree that intentionally shares ids and
	 * values with the early nodes of the fixture tree built in {@link #setup()} so that it can be
	 * merged into it with
	 * {@link IBaseTreeNodeHandlerExtensions#mergeTreeNodes(io.github.astrapi69.gen.tree.api.IBaseTreeNode, List)}.
	 * Mirrors the fixture also used by {@code MergeTreeNodesVisitorTest}
	 *
	 * <pre>
	 * +-root("I'm root") + -secondChild("I'm the second child")
	 * 	| +-sixthGrandChild("I'm the sixth grand child") + -fourthChild("I'm the fourth child")
	 * </pre>
	 *
	 * @return the root of the merge source tree
	 */
	private BaseTreeNode<String, Long> newMergeSourceTree()
	{
		LongIdGenerator mergeIdGenerator;
		BaseTreeNode<String, Long> mergeRoot;
		BaseTreeNode<String, Long> mergeSecondChild;
		BaseTreeNode<String, Long> mergeFourthChild;
		BaseTreeNode<String, Long> mergeSixthGrandChild;

		mergeIdGenerator = LongIdGenerator.of(0L);
		mergeRoot = BaseTreeNode.<String, Long> builder().id(mergeIdGenerator.getNextId())
			.value("I'm root").build();

		mergeSecondChild = BaseTreeNode.<String, Long> builder().id(mergeIdGenerator.getNextId())
			.parent(mergeRoot).value("I'm the second child").build();

		mergeFourthChild = BaseTreeNode.<String, Long> builder().id(mergeIdGenerator.getNextId())
			.parent(mergeRoot).value("I'm the fourth child").build();

		mergeSixthGrandChild = BaseTreeNode.<String, Long> builder()
			.id(mergeIdGenerator.getNextId()).parent(mergeSecondChild)
			.value("I'm the sixth grand child").build();

		mergeRoot.addChild(mergeSecondChild);
		mergeRoot.addChild(mergeFourthChild);
		mergeSecondChild.addChild(mergeSixthGrandChild);
		return mergeRoot;
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#findById(io.github.astrapi69.gen.tree.api.IBaseTreeNode, Object)}
	 */
	@Test
	public void testFindById()
	{
		BaseTreeNode<String, Long> actual;

		actual = IBaseTreeNodeHandlerExtensions.findById(root, secondGrandChild.getId());
		assertEquals(secondGrandChild, actual);

		actual = IBaseTreeNodeHandlerExtensions.findById(root, fifthGrandChild.getId());
		assertEquals(fifthGrandChild, actual);

		actual = IBaseTreeNodeHandlerExtensions.findById(root, -1L);
		assertNull(actual);
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#findById(io.github.astrapi69.gen.tree.api.IBaseTreeNode, Object)}
	 * that verifies that the first occurrence, in traversal order, is returned when several nodes
	 * share the same id
	 */
	@Test
	public void testFindByIdReturnsFirstMatchOnDuplicateIds()
	{
		BaseTreeNode<String, Long> duplicateIdRoot;
		BaseTreeNode<String, Long> firstMatch;
		BaseTreeNode<String, Long> secondMatch;
		BaseTreeNode<String, Long> actual;

		duplicateIdRoot = BaseTreeNode.<String, Long> builder().id(100L)
			.value("I'm the duplicate id root").build();
		firstMatch = BaseTreeNode.<String, Long> builder().id(42L).parent(duplicateIdRoot)
			.value("I'm the first match").build();
		secondMatch = BaseTreeNode.<String, Long> builder().id(42L).parent(duplicateIdRoot)
			.value("I'm the second match").build();
		duplicateIdRoot.addChild(firstMatch);
		duplicateIdRoot.addChild(secondMatch);

		actual = IBaseTreeNodeHandlerExtensions.findById(duplicateIdRoot, 42L);
		assertEquals(firstMatch, actual);
		assertNotSame(secondMatch, actual);
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#findById(io.github.astrapi69.gen.tree.api.IBaseTreeNode, Object)}
	 * with a null tree node
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testFindByIdWithNullTreeNode()
	{
		IBaseTreeNodeHandlerExtensions.findById(null, 1L);
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#mergeTreeNodes(io.github.astrapi69.gen.tree.api.IBaseTreeNode, List)}
	 */
	@Test
	public void testMergeTreeNodesWithList()
	{
		BaseTreeNode<String, Long> mergeSource;
		BaseTreeNode<String, Long> result;
		Collection<BaseTreeNode<String, Long>> before;
		Collection<BaseTreeNode<String, Long>> after;

		before = root.traverse();
		assertEquals(12, before.size());

		mergeSource = newMergeSourceTree();
		result = IBaseTreeNodeHandlerExtensions.mergeTreeNodes(root, List.of(mergeSource));

		assertSame(root, result);
		after = root.traverse();
		assertEquals(15, after.size());
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#mergeTreeNodes(io.github.astrapi69.gen.tree.api.IBaseTreeNode, List)}
	 * with a null root
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testMergeTreeNodesWithNullRoot()
	{
		IBaseTreeNodeHandlerExtensions.mergeTreeNodes((BaseTreeNode<String, Long>)null,
			List.of(newMergeSourceTree()));
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#mergeTreeNodes(io.github.astrapi69.gen.tree.api.IBaseTreeNode, List)}
	 * with a null list of tree nodes
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testMergeTreeNodesWithNullList()
	{
		IBaseTreeNodeHandlerExtensions.mergeTreeNodes(root, (List<BaseTreeNode<String, Long>>)null);
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#mergeTreeNodes(io.github.astrapi69.gen.tree.api.IBaseTreeNode, Object[])}
	 */
	@Test
	public void testMergeTreeNodesVarargs()
	{
		BaseTreeNode<String, Long> mergeSource;
		BaseTreeNode<String, Long> result;

		mergeSource = newMergeSourceTree();
		result = IBaseTreeNodeHandlerExtensions.mergeTreeNodes(root, mergeSource);

		assertSame(root, result);
		assertEquals(15, root.traverse().size());
	}

	/**
	 * Test method for
	 * {@link IBaseTreeNodeHandlerExtensions#mergeTreeNodes(io.github.astrapi69.gen.tree.api.IBaseTreeNode, Object[])}
	 * with a null root
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testMergeTreeNodesVarargsWithNullRoot()
	{
		IBaseTreeNodeHandlerExtensions.mergeTreeNodes((BaseTreeNode<String, Long>)null,
			newMergeSourceTree());
	}

}
