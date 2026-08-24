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

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.gen.tree.TreeNode;
import io.github.astrapi69.gen.tree.api.ITreeNode;
import io.github.astrapi69.gen.tree.enumeration.traversal.TraversalType;
import io.github.astrapi69.id.generate.LongIdGenerator;

/**
 * The unit test class for the class {@link ITreeNodeHandlerExtensions}
 */
public class ITreeNodeHandlerExtensionsTest
{

	TreeNode<String> root;
	TreeNode<String> firstChild;
	TreeNode<String> secondChild;
	TreeNode<String> firstGrandChild;
	TreeNode<String> firstGrandGrandChild;
	TreeNode<String> secondGrandGrandChild;
	TreeNode<String> firstGrandGrandGrandChild;
	TreeNode<String> secondGrandChild;
	TreeNode<String> thirdGrandChild;
	TreeNode<String> thirdChild;
	TreeNode<String> fourthGrandChild;
	TreeNode<String> fifthGrandChild;
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
		root = TreeNode.<String> builder().value("I'm root").build();

		firstChild = TreeNode.<String> builder().parent(root).value("I'm the first child").build();

		secondChild = TreeNode.<String> builder().parent(root).value("I'm the second child")
			.build();

		firstGrandChild = TreeNode.<String> builder().parent(secondChild)
			.value("I'm the first grand child").build();

		firstGrandGrandChild = TreeNode.<String> builder().parent(firstGrandChild)
			.value("I'm the first grand grand child").build();

		secondGrandGrandChild = TreeNode.<String> builder().parent(firstGrandChild)
			.value("I'm the second grand grand child").build();

		firstGrandGrandGrandChild = TreeNode.<String> builder().parent(secondGrandGrandChild)
			.value("I'm the first grand grand grand child").build();

		secondGrandChild = TreeNode.<String> builder().parent(secondChild)
			.value("I'm the second grand child").build();

		thirdGrandChild = TreeNode.<String> builder().parent(secondChild).value(null).build();

		thirdChild = TreeNode.<String> builder().parent(root).value("I'm the third child").build();

		fourthGrandChild = TreeNode.<String> builder().parent(thirdChild).value(null).build();
		fifthGrandChildValue = "I'm the fifth grand child";
		fifthGrandChild = TreeNode.<String> builder().parent(thirdChild).leaf(true)
			.value(fifthGrandChildValue).build();

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
	 * This method will be invoked after every unit test method for clear any test instances
	 */
	@AfterMethod
	protected void tearDown()
	{
		root = null;
		firstChild = null;
		secondChild = null;
		firstGrandChild = null;
		firstGrandGrandChild = null;
		secondGrandGrandChild = null;
		firstGrandGrandGrandChild = null;
		secondGrandChild = null;
		thirdGrandChild = null;
		thirdChild = null;
		fourthGrandChild = null;
		fifthGrandChild = null;
		fifthGrandChildValue = null;
		idGenerator = null;
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isParentOf(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testIsParentOf()
	{
		boolean actual;
		boolean expected;
		actual = ITreeNodeHandlerExtensions.isParentOf(root, firstChild);
		expected = true;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.isParentOf(root, fifthGrandChild);
		expected = false;
		assertEquals(expected, actual);

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isParentOf((TreeNode<String>)null, firstChild));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isParentOf(root, (TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isNode(ITreeNode)}
	 */
	@Test
	public void testIsNode()
	{
		boolean actual;
		boolean expected;
		actual = ITreeNodeHandlerExtensions.isNode(root);
		expected = true;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.isNode(fifthGrandChild);
		expected = false;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getChildCount(ITreeNode)}
	 */
	@Test
	public void testGetChildCount()
	{
		int actual;
		int expected;

		Collection<TreeNode<String>> children = root.getChildren();

		actual = ITreeNodeHandlerExtensions.getChildCount(root);
		expected = children.size();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#contains(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testContains()
	{
		boolean actual;
		boolean expected;

		actual = ITreeNodeHandlerExtensions.contains(root, firstGrandGrandGrandChild);
		expected = true;
		assertEquals(actual, expected);

		actual = ITreeNodeHandlerExtensions.contains(firstGrandGrandGrandChild, root);
		expected = false;
		assertEquals(actual, expected);

		actual = ITreeNodeHandlerExtensions.contains(root, null);
		assertEquals(actual, expected);

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.contains((TreeNode<String>)null, firstChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#move(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testMove()
	{
		boolean actual;
		boolean expected;

		actual = ITreeNodeHandlerExtensions.move(firstChild, thirdChild);
		expected = true;
		assertEquals(actual, expected);
		// verify the node was actually reparented, not just that true was returned
		assertFalse(root.getChildren().contains(firstChild));
		assertTrue(thirdChild.getChildren().contains(firstChild));
		assertEquals(thirdChild, firstChild.getParent());

		actual = ITreeNodeHandlerExtensions.move(root, firstGrandGrandGrandChild);
		expected = false;
		assertEquals(actual, expected);

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.move((TreeNode<String>)null, thirdChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#move(ITreeNode, ITreeNode)} that verifies
	 * that moving a tree node under a leaf target is rejected and leaves the tree unchanged
	 */
	@Test
	public void testMoveToLeafTargetReturnsFalse()
	{
		boolean actual;

		actual = ITreeNodeHandlerExtensions.move(firstChild, fifthGrandChild);

		assertFalse(actual);
		assertEquals(root, firstChild.getParent());
		assertTrue(root.getChildren().contains(firstChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#move(ITreeNode, ITreeNode)} that verifies
	 * that moving a tree node to a null parent simply detaches it from its current parent
	 */
	@Test
	public void testMoveToNullDetachesNode()
	{
		boolean actual;

		actual = ITreeNodeHandlerExtensions.move(firstChild, null);

		assertTrue(actual);
		assertNull(firstChild.getParent());
		assertFalse(root.getChildren().contains(firstChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isAncestor(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testIsAncestor()
	{
		boolean actual;
		boolean expected;

		actual = ITreeNodeHandlerExtensions.isAncestor(firstChild, thirdChild);
		expected = false;
		assertEquals(actual, expected);

		actual = ITreeNodeHandlerExtensions.isAncestor(firstGrandGrandGrandChild, root);
		expected = true;
		assertEquals(actual, expected);

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isAncestor((TreeNode<String>)null, root));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isAncestor(firstChild, (TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#height(ITreeNode)}
	 */
	@Test
	public void testHeight()
	{
		int actual;
		int expected;

		actual = ITreeNodeHandlerExtensions.height(fifthGrandChild);
		expected = 0;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.height(secondGrandGrandChild);
		expected = 1;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.height(firstGrandChild);
		expected = 2;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.height(secondChild);
		expected = 3;
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.height(root);
		expected = 4;
		assertEquals(expected, actual);

		// note: PIT reports a surviving "changed conditional boundary" mutant on the
		// 'maxChildHeight < childHeight' comparison in height(); flipping it to '<=' only
		// causes an equal value to be reassigned to itself, so it never changes the final
		// result and is an equivalent mutant that cannot be killed by any external assertion
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.height((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#lowestCommonAncestor(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testLowestCommonAncestor()
	{
		TreeNode<String> actual;

		actual = ITreeNodeHandlerExtensions.lowestCommonAncestor(firstGrandGrandChild,
			secondGrandGrandChild);
		assertEquals(firstGrandChild, actual);

		actual = ITreeNodeHandlerExtensions.lowestCommonAncestor(firstGrandGrandGrandChild,
			secondGrandChild);
		assertEquals(secondChild, actual);

		actual = ITreeNodeHandlerExtensions.lowestCommonAncestor(firstChild, fifthGrandChild);
		assertEquals(root, actual);

		actual = ITreeNodeHandlerExtensions.lowestCommonAncestor(root, firstGrandGrandGrandChild);
		assertEquals(root, actual);

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.lowestCommonAncestor((TreeNode<String>)null, root));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.lowestCommonAncestor(root, (TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#lowestCommonAncestor(ITreeNode, ITreeNode)}
	 * that verifies that null is returned when the two given tree nodes belong to different trees
	 */
	@Test
	public void testLowestCommonAncestorDifferentTrees()
	{
		TreeNode<String> otherRoot;
		TreeNode<String> otherChild;
		TreeNode<String> actual;

		otherRoot = TreeNode.<String> builder().value("other root").build();
		otherChild = TreeNode.<String> builder().parent(otherRoot).value("other child").build();
		otherRoot.addChild(otherChild);

		actual = ITreeNodeHandlerExtensions.lowestCommonAncestor(firstChild, otherChild);

		assertNull(actual);
	}

	/**
	 * Test method for
	 * {@link ITreeNodeHandlerExtensions#filterTree(ITreeNode, java.util.function.Predicate)}
	 */
	@Test
	public void testFilterTree()
	{
		List<TreeNode<String>> actual;

		// drop firstGrandChild but keep its descendants, promoted under secondChild
		actual = ITreeNodeHandlerExtensions.filterTree(root,
			node -> !"I'm the first grand child".equals(node.getValue()));

		assertEquals(1, actual.size());
		assertEquals(root, actual.get(0));

		Collection<TreeNode<String>> secondChildChildren = secondChild.getChildren();
		assertFalse(secondChildChildren.contains(firstGrandChild));
		assertTrue(secondChildChildren.contains(firstGrandGrandChild));
		assertTrue(secondChildChildren.contains(secondGrandGrandChild));
		// the untouched part of the promoted subtree keeps its own structure
		assertTrue(secondGrandGrandChild.getChildren().contains(firstGrandGrandGrandChild));
		assertEquals(secondChild, firstGrandGrandChild.getParent());
		assertEquals(secondChild, secondGrandGrandChild.getParent());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.filterTree((TreeNode<String>)null, node -> true));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.filterTree(root, null));
	}

	/**
	 * Test method for
	 * {@link ITreeNodeHandlerExtensions#cloneSubtree(ITreeNode, java.util.function.UnaryOperator)}
	 */
	@Test
	public void testCloneSubtree()
	{
		TreeNode<String> clone = ITreeNodeHandlerExtensions.cloneSubtree(secondGrandGrandChild,
			node -> TreeNode.<String> builder().value(node.getValue())
				.displayValue(node.getDisplayValue()).leaf(node.isLeaf()).build());

		assertNotSame(secondGrandGrandChild, clone);
		assertEquals(secondGrandGrandChild.getValue(), clone.getValue());
		assertNull(clone.getParent());
		assertEquals(1, clone.getChildren().size());

		TreeNode<String> clonedGrandChild = clone.getChildren().iterator().next();
		assertNotSame(firstGrandGrandGrandChild, clonedGrandChild);
		assertEquals(firstGrandGrandGrandChild.getValue(), clonedGrandChild.getValue());
		assertEquals(clone, clonedGrandChild.getParent());

		// the original subtree is untouched
		assertEquals(1, secondGrandGrandChild.getChildren().size());
		assertTrue(secondGrandGrandChild.getChildren().contains(firstGrandGrandGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.cloneSubtree((TreeNode<String>)null, node -> node));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.cloneSubtree(secondGrandGrandChild, null));
	}

	/**
	 * Test method for
	 * {@link ITreeNodeHandlerExtensions#cloneSubtree(ITreeNode, java.util.function.UnaryOperator)}
	 * that verifies that the clone is fully detached even when the given copier function reuses the
	 * source's parent and children references, for instance via a Lombok {@code toBuilder()} copy
	 */
	@Test
	public void testCloneSubtreeDetachesFromToBuilderCopier()
	{
		TreeNode<String> clone;

		clone = ITreeNodeHandlerExtensions.cloneSubtree(secondGrandGrandChild,
			node -> node.toBuilder().build());

		assertNull(clone.getParent());
		assertNotSame(secondGrandGrandChild.getChildren(), clone.getChildren());
		assertEquals(1, clone.getChildren().size());
		// the original subtree must not have been polluted by the clone
		assertEquals(1, secondGrandGrandChild.getChildren().size());
	}

	/**
	 * Test method for
	 * {@link ITreeNodeHandlerExtensions#reduceTree(ITreeNode, Object, java.util.function.BiFunction, TraversalType)}
	 */
	@Test
	public void testReduceTree()
	{
		int actual;
		int expected;

		actual = ITreeNodeHandlerExtensions.reduceTree(secondChild, 0, (count, node) -> count + 1,
			TraversalType.PREORDER);
		expected = secondChild.traverse().size();
		assertEquals(expected, actual);

		actual = ITreeNodeHandlerExtensions.reduceTree(secondChild, 0, (count, node) -> count + 1,
			TraversalType.POSTORDER);
		assertEquals(expected, actual);

		List<String> preOrderValues = ITreeNodeHandlerExtensions.reduceTree(secondChild,
			new ArrayList<String>(), (list, node) -> {
				list.add(node.getValue());
				return list;
			}, TraversalType.PREORDER);
		assertEquals("I'm the second child", preOrderValues.get(0));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.reduceTree((TreeNode<String>)null, 0,
				(count, node) -> count + 1, TraversalType.PREORDER));
		assertThrows(NullPointerException.class, () -> ITreeNodeHandlerExtensions
			.reduceTree(secondChild, 0, null, TraversalType.PREORDER));
		assertThrows(NullPointerException.class, () -> ITreeNodeHandlerExtensions
			.reduceTree(secondChild, 0, (count, node) -> count + 1, null));
	}

	/**
	 * Test method for
	 * {@link ITreeNodeHandlerExtensions#reduceTree(ITreeNode, Object, java.util.function.BiFunction, TraversalType)}
	 * that verifies that {@link TraversalType#INORDER} is rejected since it is only meaningful for
	 * binary trees
	 */
	@Test
	public void testReduceTreeInorderThrowsUnsupportedOperationException()
	{
		assertThrows(UnsupportedOperationException.class, () -> ITreeNodeHandlerExtensions
			.reduceTree(root, 0, (count, node) -> count + 1, TraversalType.INORDER));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getAllSiblings(ITreeNode)}
	 */
	@Test
	public void testGetAllSiblings()
	{
		Collection<TreeNode<String>> actual;

		actual = ITreeNodeHandlerExtensions.getAllSiblings(secondChild);
		assertEquals(2, actual.size());
		assertTrue(actual.contains(firstChild));
		assertTrue(actual.contains(thirdChild));
		assertFalse(actual.contains(secondChild));

		// root has no parent, so it has no siblings; the returned collection must still be a
		// fresh, mutable collection and not a shared immutable empty singleton
		actual = ITreeNodeHandlerExtensions.getAllSiblings(root);
		assertTrue(actual.isEmpty());
		actual.add(firstChild);
		assertEquals(1, actual.size());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getAllSiblings((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getRoot(ITreeNode)}
	 */
	@Test
	public void testGetRoot()
	{
		assertEquals(root, ITreeNodeHandlerExtensions.getRoot(root));
		assertEquals(root, ITreeNodeHandlerExtensions.getRoot(firstGrandGrandGrandChild));
		assertEquals(root, ITreeNodeHandlerExtensions.getRoot(fifthGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getRoot((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getNextSibling(ITreeNode)}
	 */
	@Test
	public void testGetNextSibling()
	{
		assertEquals(secondChild, ITreeNodeHandlerExtensions.getNextSibling(firstChild));
		assertEquals(thirdChild, ITreeNodeHandlerExtensions.getNextSibling(secondChild));
		assertNull(ITreeNodeHandlerExtensions.getNextSibling(thirdChild));
		assertNull(ITreeNodeHandlerExtensions.getNextSibling(root));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getNextSibling((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getPreviousSibling(ITreeNode)}
	 */
	@Test
	public void testGetPreviousSibling()
	{
		assertEquals(firstChild, ITreeNodeHandlerExtensions.getPreviousSibling(secondChild));
		assertEquals(secondChild, ITreeNodeHandlerExtensions.getPreviousSibling(thirdChild));
		assertNull(ITreeNodeHandlerExtensions.getPreviousSibling(firstChild));
		assertNull(ITreeNodeHandlerExtensions.getPreviousSibling(root));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getPreviousSibling((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getLevel(ITreeNode)}
	 */
	@Test
	public void testGetLevel()
	{
		assertEquals(0, ITreeNodeHandlerExtensions.getLevel(root));
		assertEquals(1, ITreeNodeHandlerExtensions.getLevel(firstChild));
		assertEquals(2, ITreeNodeHandlerExtensions.getLevel(firstGrandChild));
		assertEquals(4, ITreeNodeHandlerExtensions.getLevel(firstGrandGrandGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getLevel((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#removeChild(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testRemoveChildTwoArguments()
	{
		int sizeBefore;

		ITreeNodeHandlerExtensions.removeChild(root, firstChild);

		assertFalse(root.getChildren().contains(firstChild));
		assertNull(firstChild.getParent());

		// null child -> no-op, no exception
		sizeBefore = root.getChildren().size();
		ITreeNodeHandlerExtensions.removeChild(root, null);
		assertEquals(sizeBefore, root.getChildren().size());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeChild((TreeNode<String>)null, firstChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#removeChild(ITreeNode, ITreeNode, boolean)}
	 */
	@Test
	public void testRemoveChildThreeArguments()
	{
		ITreeNodeHandlerExtensions.removeChild(root, secondChild, true);
		assertNull(secondChild.getParent());
		assertTrue(secondChild.getChildren().isEmpty());

		ITreeNodeHandlerExtensions.removeChild(root, thirdChild, false);
		assertNull(thirdChild.getParent());
		assertFalse(thirdChild.getChildren().isEmpty());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeChild((TreeNode<String>)null, firstChild, true));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#removeFromParent(ITreeNode)}
	 */
	@Test
	public void testRemoveFromParentSingleArgument()
	{
		ITreeNodeHandlerExtensions.removeFromParent(firstChild);

		assertFalse(root.getChildren().contains(firstChild));
		assertNull(firstChild.getParent());

		// a tree node without a parent is left untouched, no exception
		ITreeNodeHandlerExtensions.removeFromParent(root);
		assertNull(root.getParent());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeFromParent((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#removeFromParent(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testRemoveFromParentTwoArguments()
	{
		int secondChildChildCountBefore;

		secondChildChildCountBefore = secondChild.getChildren().size();

		ITreeNodeHandlerExtensions.removeFromParent(root, secondChild);

		assertFalse(root.getChildren().contains(secondChild));
		assertNull(secondChild.getParent());
		// this overload always uses clearChildren=false: the removed node keeps its own children
		assertEquals(secondChildChildCountBefore, secondChild.getChildren().size());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeFromParent((TreeNode<String>)null, firstChild));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeFromParent(root, (TreeNode<String>)null));
	}

	/**
	 * Test method for
	 * {@link ITreeNodeHandlerExtensions#removeFromParent(ITreeNode, ITreeNode, boolean)}
	 */
	@Test
	public void testRemoveFromParentWithClearChildrenFlag()
	{
		TreeNode<String> outsider;

		// clearChildren = true also clears the removed node's own children
		ITreeNodeHandlerExtensions.removeFromParent(root, secondChild, true);
		assertNull(secondChild.getParent());
		assertTrue(secondChild.getChildren().isEmpty());

		// clearChildren = false keeps the removed node's own children intact
		ITreeNodeHandlerExtensions.removeFromParent(root, thirdChild, false);
		assertNull(thirdChild.getParent());
		assertFalse(thirdChild.getChildren().isEmpty());

		// not actually a child of the given parent -> no-op
		outsider = TreeNode.<String> builder().value("outsider").build();
		ITreeNodeHandlerExtensions.removeFromParent(root, outsider, true);
		assertNull(outsider.getParent());

		assertThrows(NullPointerException.class, () -> ITreeNodeHandlerExtensions
			.removeFromParent((TreeNode<String>)null, firstChild, true));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeFromParent(root, (TreeNode<String>)null, true));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isChild(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testIsChild()
	{
		assertTrue(ITreeNodeHandlerExtensions.isChild(root, firstChild));
		assertFalse(ITreeNodeHandlerExtensions.isChild(root, null));
		assertFalse(ITreeNodeHandlerExtensions.isChild(root, thirdGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isChild((TreeNode<String>)null, firstChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isChildOf(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testIsChildOf()
	{
		assertTrue(ITreeNodeHandlerExtensions.isChildOf(root, firstChild));
		assertFalse(ITreeNodeHandlerExtensions.isChildOf(root, thirdGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isChildOf((TreeNode<String>)null, firstChild));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isChildOf(root, (TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#removeChildren(ITreeNode, Collection)}
	 */
	@Test
	public void testRemoveChildrenCollection()
	{
		Collection<TreeNode<String>> toRemove;

		toRemove = new ArrayList<>();
		toRemove.add(firstChild);
		toRemove.add(secondChild);

		ITreeNodeHandlerExtensions.removeChildren(root, toRemove);

		assertEquals(1, root.getChildren().size());
		assertTrue(root.getChildren().contains(thirdChild));
		assertNull(firstChild.getParent());
		assertNull(secondChild.getParent());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeChildren((TreeNode<String>)null, toRemove));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeChildren(root, null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#removeChildren(ITreeNode)}
	 */
	@Test
	public void testRemoveChildrenAll()
	{
		ITreeNodeHandlerExtensions.removeChildren(root);

		assertTrue(root.getChildren().isEmpty());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.removeChildren((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isRoot(ITreeNode)}
	 */
	@Test
	public void testIsRoot()
	{
		assertTrue(ITreeNodeHandlerExtensions.isRoot(root));
		assertFalse(ITreeNodeHandlerExtensions.isRoot(firstChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isRoot((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#hasNextSibling(ITreeNode)}
	 */
	@Test
	public void testHasNextSibling()
	{
		assertTrue(ITreeNodeHandlerExtensions.hasNextSibling(firstChild));
		assertFalse(ITreeNodeHandlerExtensions.hasNextSibling(thirdChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.hasNextSibling((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#hasParent(ITreeNode)}
	 */
	@Test
	public void testHasParent()
	{
		assertFalse(ITreeNodeHandlerExtensions.hasParent(root));
		assertTrue(ITreeNodeHandlerExtensions.hasParent(firstChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.hasParent((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#hasPreviousSibling(ITreeNode)}
	 */
	@Test
	public void testHasPreviousSibling()
	{
		assertTrue(ITreeNodeHandlerExtensions.hasPreviousSibling(secondChild));
		assertFalse(ITreeNodeHandlerExtensions.hasPreviousSibling(firstChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.hasPreviousSibling((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isNode(ITreeNode)}
	 */
	@Test
	public void testIsNodeThrowsOnNull()
	{
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isNode((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#clearAll(ITreeNode)}
	 */
	@Test
	public void testClearAll()
	{
		ITreeNodeHandlerExtensions.clearAll(secondChild);

		assertTrue(secondChild.getChildren().isEmpty());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.clearAll((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#clearChildren(ITreeNode)}
	 */
	@Test
	public void testClearChildren()
	{
		ITreeNodeHandlerExtensions.clearChildren(thirdChild);

		assertTrue(thirdChild.getChildren().isEmpty());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.clearChildren((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#addChild(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testAddChildTwoArguments()
	{
		TreeNode<String> newChild;
		TreeNode<String> anotherChild;
		int sizeBefore;

		newChild = TreeNode.<String> builder().value("newChild").build();

		ITreeNodeHandlerExtensions.addChild(root, newChild);

		assertTrue(root.getChildren().contains(newChild));
		assertEquals(root, newChild.getParent());

		// null child -> no-op
		sizeBefore = root.getChildren().size();
		ITreeNodeHandlerExtensions.addChild(root, null);
		assertEquals(sizeBefore, root.getChildren().size());

		// leaf parent -> no-op
		anotherChild = TreeNode.<String> builder().value("another").build();
		ITreeNodeHandlerExtensions.addChild(fifthGrandChild, anotherChild);
		assertNull(anotherChild.getParent());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.addChild((TreeNode<String>)null, newChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#addChild(ITreeNode, ITreeNode, int)}
	 */
	@Test
	public void testAddChildAtIndex()
	{
		TreeNode<String> inserted;
		TreeNode<String> anotherChild;
		TreeNode<String> nonListParent;
		TreeNode<String> childForSet;
		List<TreeNode<String>> rootChildren;
		int sizeBefore;

		inserted = TreeNode.<String> builder().value("inserted").build();

		ITreeNodeHandlerExtensions.addChild(root, inserted, 1);

		rootChildren = new ArrayList<>(root.getChildren());
		assertEquals(4, rootChildren.size());
		assertEquals(inserted, rootChildren.get(1));
		assertEquals(root, inserted.getParent());

		// null child -> no-op
		sizeBefore = root.getChildren().size();
		ITreeNodeHandlerExtensions.addChild(root, null, 0);
		assertEquals(sizeBefore, root.getChildren().size());

		// leaf parent -> no-op
		anotherChild = TreeNode.<String> builder().value("another").build();
		ITreeNodeHandlerExtensions.addChild(fifthGrandChild, anotherChild, 0);
		assertNull(anotherChild.getParent());

		// non-List children collection -> falls back to a plain add
		nonListParent = TreeNode.<String> builder().value("nonList").build();
		nonListParent.setChildren(new LinkedHashSet<>());
		childForSet = TreeNode.<String> builder().value("childForSet").build();
		ITreeNodeHandlerExtensions.addChild(nonListParent, childForSet, 0);
		assertTrue(nonListParent.getChildren().contains(childForSet));
		assertEquals(nonListParent, childForSet.getParent());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.addChild((TreeNode<String>)null, inserted, 0));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getChildAt(ITreeNode, int)}
	 */
	@Test
	public void testGetChildAt()
	{
		TreeNode<String> nonListParent;
		TreeNode<String> listedChild;
		TreeNode<String> parentWithNullChild;

		assertEquals(Optional.of(firstChild), ITreeNodeHandlerExtensions.getChildAt(root, 0));
		assertEquals(Optional.of(secondChild), ITreeNodeHandlerExtensions.getChildAt(root, 1));

		// leaf parent -> not a node -> empty
		assertEquals(Optional.empty(), ITreeNodeHandlerExtensions.getChildAt(fifthGrandChild, 0));

		// non-List children collection -> empty
		nonListParent = TreeNode.<String> builder().value("nonList").build();
		nonListParent.setChildren(new LinkedHashSet<>());
		listedChild = TreeNode.<String> builder().value("child").build();
		nonListParent.getChildren().add(listedChild);
		assertEquals(Optional.empty(), ITreeNodeHandlerExtensions.getChildAt(nonListParent, 0));

		// a null element at the given index -> empty
		parentWithNullChild = TreeNode.<String> builder().value("parent").build();
		parentWithNullChild.getChildren().add(null);
		assertEquals(Optional.empty(),
			ITreeNodeHandlerExtensions.getChildAt(parentWithNullChild, 0));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getChildAt((TreeNode<String>)null, 0));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getChildIndex(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testGetChildIndex()
	{
		TreeNode<String> detached;
		int actual;
		int expected;

		actual = ITreeNodeHandlerExtensions.getChildIndex(root, secondChild);
		expected = 1;
		assertEquals(expected, actual);

		// getChildIndex reparents the given child to the given parent as a side effect, even
		// when the child was not attached there before the call
		detached = TreeNode.<String> builder().value("detached").build();
		root.getChildren().add(detached);
		actual = ITreeNodeHandlerExtensions.getChildIndex(root, detached);
		expected = 3;
		assertEquals(expected, actual);
		assertEquals(root, detached.getParent());

		// null child -> -1, no NullPointerException
		actual = ITreeNodeHandlerExtensions.getChildIndex(root, null);
		expected = -1;
		assertEquals(expected, actual);

		// leaf parent -> not a node -> -1
		actual = ITreeNodeHandlerExtensions.getChildIndex(fifthGrandChild, firstChild);
		expected = -1;
		assertEquals(expected, actual);

		// non-List children collection -> falls through to -1
		TreeNode<String> nonListParent = TreeNode.<String> builder().value("nonList").build();
		nonListParent.setChildren(new LinkedHashSet<>());
		TreeNode<String> childForSet = TreeNode.<String> builder().value("childForSet").build();
		nonListParent.getChildren().add(childForSet);
		actual = ITreeNodeHandlerExtensions.getChildIndex(nonListParent, childForSet);
		expected = -1;
		assertEquals(expected, actual);

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getChildIndex((TreeNode<String>)null, firstChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#addChildren(ITreeNode, Collection)}
	 */
	@Test
	public void testAddChildren()
	{
		TreeNode<String> extraOne;
		TreeNode<String> extraTwo;
		Collection<TreeNode<String>> extras;
		int sizeBefore;

		extraOne = TreeNode.<String> builder().value("extraOne").build();
		extraTwo = TreeNode.<String> builder().value("extraTwo").build();
		extras = new ArrayList<>();
		extras.add(extraOne);
		extras.add(extraTwo);

		ITreeNodeHandlerExtensions.addChildren(root, extras);

		assertTrue(root.getChildren().contains(extraOne));
		assertTrue(root.getChildren().contains(extraTwo));
		assertEquals(root, extraOne.getParent());
		assertEquals(root, extraTwo.getParent());

		// leaf parent -> no-op
		sizeBefore = fifthGrandChild.getChildren().size();
		ITreeNodeHandlerExtensions.addChildren(fifthGrandChild, extras);
		assertEquals(sizeBefore, fifthGrandChild.getChildren().size());

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.addChildren((TreeNode<String>)null, extras));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.addChildren(root, null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#getChildCount(ITreeNode)}
	 */
	@Test
	public void testGetChildCountThrowsOnNull()
	{
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.getChildCount((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#hasChildren(ITreeNode)}
	 */
	@Test
	public void testHasChildren()
	{
		assertTrue(ITreeNodeHandlerExtensions.hasChildren(root));
		assertFalse(ITreeNodeHandlerExtensions.hasChildren(fifthGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.hasChildren((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#traverse(ITreeNode)}
	 */
	@Test
	public void testTraverse()
	{
		Collection<TreeNode<String>> actual;

		actual = ITreeNodeHandlerExtensions.traverse(secondChild);

		assertEquals(7, actual.size());
		assertTrue(actual.contains(secondChild));
		assertTrue(actual.contains(firstGrandGrandGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.traverse((TreeNode<String>)null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#findAllByValue(ITreeNode, Object)}
	 */
	@Test
	public void testFindAllByValue()
	{
		Collection<TreeNode<String>> actual;

		actual = ITreeNodeHandlerExtensions.findAllByValue(root, fifthGrandChildValue);
		assertEquals(1, actual.size());
		assertTrue(actual.contains(fifthGrandChild));

		// null value matches every node whose value is null
		actual = ITreeNodeHandlerExtensions.findAllByValue(root, null);
		assertEquals(2, actual.size());
		assertTrue(actual.contains(thirdGrandChild));
		assertTrue(actual.contains(fourthGrandChild));

		assertThrows(NullPointerException.class, () -> ITreeNodeHandlerExtensions
			.findAllByValue((TreeNode<String>)null, fifthGrandChildValue));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#findByValue(ITreeNode, Object)}
	 */
	@Test
	public void testFindByValue()
	{
		TreeNode<String> actual;

		actual = ITreeNodeHandlerExtensions.findByValue(root, fifthGrandChildValue);
		assertEquals(fifthGrandChild, actual);

		assertNull(ITreeNodeHandlerExtensions.findByValue(root, "does not exist"));

		assertThrows(NullPointerException.class, () -> ITreeNodeHandlerExtensions
			.findByValue((TreeNode<String>)null, fifthGrandChildValue));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#containsAll(ITreeNode, Collection)}
	 */
	@Test
	public void testContainsAll()
	{
		List<TreeNode<String>> descendants;
		List<TreeNode<String>> notAllDescendants;
		boolean actual;

		descendants = new ArrayList<>();
		descendants.add(firstGrandChild);
		descendants.add(secondGrandChild);

		actual = ITreeNodeHandlerExtensions.containsAll(root, descendants);
		assertTrue(actual);

		notAllDescendants = new ArrayList<>();
		notAllDescendants.add(firstGrandChild);
		notAllDescendants.add(TreeNode.<String> builder().value("outsider").build());

		actual = ITreeNodeHandlerExtensions.containsAll(root, notAllDescendants);
		assertFalse(actual);

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.containsAll((TreeNode<String>)null, descendants));
		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.containsAll(root, null));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#toList(ITreeNode)}
	 */
	@Test
	public void testToList()
	{
		List<TreeNode<String>> actual;

		actual = ITreeNodeHandlerExtensions.toList(root);

		assertEquals(12, actual.size());
		assertTrue(actual.contains(root));
		assertTrue(actual.contains(fifthGrandChild));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.toList((TreeNode<String>)null));
	}

	/**
	 * Test method for
	 * {@link ITreeNodeHandlerExtensions#findFirstOccurenceOfDescendant(ITreeNode, ITreeNode)} that
	 * verifies that the search stops at, and returns, the FIRST structural match rather than merely
	 * any node considered equal, since {@link TreeNode#equals(Object)} excludes the children field
	 * and two distinct sibling instances can therefore be equal to each other
	 */
	@Test
	public void testFindFirstOccurenceOfDescendant()
	{
		TreeNode<String> duplicateA;
		TreeNode<String> duplicateB;
		TreeNode<String> actual;

		duplicateA = TreeNode.<String> builder().parent(thirdChild).value("duplicate").build();
		duplicateB = TreeNode.<String> builder().parent(thirdChild).value("duplicate").build();
		thirdChild.addChild(duplicateA);
		thirdChild.addChild(duplicateB);
		assertEquals(duplicateA, duplicateB);

		actual = ITreeNodeHandlerExtensions.findFirstOccurenceOfDescendant(root, duplicateA);

		assertSame(duplicateA, actual);

		assertThrows(NullPointerException.class, () -> ITreeNodeHandlerExtensions
			.findFirstOccurenceOfDescendant((TreeNode<String>)null, duplicateA));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#isDescendant(ITreeNode, ITreeNode)}
	 */
	@Test
	public void testIsDescendant()
	{
		assertTrue(ITreeNodeHandlerExtensions.isDescendant(root, fifthGrandChild));
		assertFalse(ITreeNodeHandlerExtensions.isDescendant(firstChild, root));

		assertThrows(NullPointerException.class,
			() -> ITreeNodeHandlerExtensions.isDescendant((TreeNode<String>)null, fifthGrandChild));
	}

	/**
	 * Test method for {@link ITreeNodeHandlerExtensions#move(ITreeNode, ITreeNode)} that verifies
	 * the branch where the tree node to move has no parent of its own (so the removeChild call is
	 * skipped) while it is still moved under an unrelated, non-descendant target
	 */
	@Test
	public void testMoveWithoutParentSkipsRemoveChild()
	{
		TreeNode<String> otherRoot;
		TreeNode<String> otherChild;
		boolean actual;

		otherRoot = TreeNode.<String> builder().value("other root").build();
		otherChild = TreeNode.<String> builder().parent(otherRoot).value("other child").build();
		otherRoot.addChild(otherChild);

		actual = ITreeNodeHandlerExtensions.move(root, otherChild);

		assertTrue(actual);
		assertEquals(otherChild, root.getParent());
		assertTrue(otherChild.getChildren().contains(root));
	}

	/**
	 * Test method for the implicit default constructor of {@link ITreeNodeHandlerExtensions},
	 * covering it as a pure utility class with only static members
	 */
	@Test
	public void testConstructor()
	{
		assertNotNull(new ITreeNodeHandlerExtensions());
	}
}
