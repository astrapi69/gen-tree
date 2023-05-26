package io.github.astrapi69.gen.tree.visitor;

import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.BaseTreeNodeTestData;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * The unit test class for the class {@link MaxIndexFinderTreeNodeVisitor}
 */
public class MaxIndexFinderTreeNodeVisitorTest
{

	/**
	 * Test method for {@link MaxIndexFinderTreeNodeVisitor#visit(IBaseTreeNode)}
	 */
	@Test
	public void test()
	{

		MaxIndexFinderTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>> maxIndexFinderTreeNodeVisitor;
		BaseTreeNode<String, Long> baseTestTree;
		BaseTreeNode<String, Long> root;
		Long maxIndex;

		maxIndexFinderTreeNodeVisitor = new MaxIndexFinderTreeNodeVisitor<String, Long, BaseTreeNode<String, Long>>()
		{
			@Override
			public boolean isGreater(Long id)
			{
				return getMaxIndex() < id;
			}
		};


		root = BaseTreeNodeTestData.getSimpleTestTree();

		root.accept(maxIndexFinderTreeNodeVisitor);
		maxIndex = maxIndexFinderTreeNodeVisitor.getMaxIndex();
		assertEquals(maxIndex, 3);

		baseTestTree = BaseTreeNodeTestData.getBaseTestTree();

		baseTestTree.accept(maxIndexFinderTreeNodeVisitor);
		maxIndex = maxIndexFinderTreeNodeVisitor.getMaxIndex();
		assertEquals(maxIndex, 11);
	}
}