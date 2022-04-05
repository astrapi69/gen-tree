package io.github.astrapi69.tree.handler;

import static org.testng.AssertJUnit.assertEquals;

import java.util.Collection;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.astrapi69.id.generate.LongIdGenerator;
import io.github.astrapi69.tree.BaseTreeNode;
import io.github.astrapi69.tree.visitor.FindValuesBaseTreeNodeVisitor;

public class BaseTreeNodeHandlerExtensionsTest
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
			.parent(thirdChild).value(fifthGrandChildValue).build();

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
	 * Test method for {@link BaseTreeNodeHandlerExtensions#contains(BaseTreeNode, BaseTreeNode)}
	 */
	@Test
	public void testContains()
	{
		boolean actual;
		boolean expected;

		actual = BaseTreeNodeHandlerExtensions.contains(root, firstGrandGrandGrandChild);
		expected = true;
		assertEquals(actual, expected);

		actual = BaseTreeNodeHandlerExtensions.contains(firstGrandGrandGrandChild, root);
		expected = false;
		assertEquals(actual, expected);

		actual = BaseTreeNodeHandlerExtensions.contains(root, null);
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link BaseTreeNodeHandlerExtensions#isParentOf(BaseTreeNode, BaseTreeNode)}
	 */
	@Test
	public void testisParentOf()
	{
		boolean actual;
		boolean expected;
		actual = BaseTreeNodeHandlerExtensions.isParentOf(root, firstChild);
		expected = true;
		assertEquals(expected, actual);

		actual = BaseTreeNodeHandlerExtensions.isParentOf(root, fifthGrandChild);
		expected = false;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link BaseTreeNodeHandlerExtensions#traverse(BaseTreeNode)}
	 */
	@Test
	public void testAccept()
	{
		FindValuesBaseTreeNodeVisitor<String, Long> visitor = new FindValuesBaseTreeNodeVisitor<>(
			fifthGrandChildValue);
		BaseTreeNodeHandlerExtensions.accept(root, visitor, true);
		Collection<BaseTreeNode<String, Long>> baseTreeNodes = visitor.getFoundTreeNodes().get();
		assertEquals(1, baseTreeNodes.size());
	}

	/**
	 * Test method for {@link BaseTreeNodeHandlerExtensions#traverse(BaseTreeNode)}
	 */
	@Test
	public void testTraverse()
	{
		Collection<BaseTreeNode<String, Long>> subtree;

		subtree = BaseTreeNodeHandlerExtensions.traverse(root);
		assertEquals(12, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(firstChild);
		assertEquals(1, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(secondChild);
		assertEquals(7, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(thirdChild);
		assertEquals(3, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(firstGrandChild);
		assertEquals(4, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(firstGrandGrandChild);
		assertEquals(1, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(secondGrandGrandChild);
		assertEquals(2, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(firstGrandGrandGrandChild);
		assertEquals(1, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(secondGrandChild);
		assertEquals(1, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(thirdGrandChild);
		assertEquals(1, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(fourthGrandChild);
		assertEquals(1, subtree.size());

		subtree = BaseTreeNodeHandlerExtensions.traverse(fifthGrandChild);
		assertEquals(1, subtree.size());
	}
}
