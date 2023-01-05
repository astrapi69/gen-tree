package io.github.astrapi69.gen.tree.visitor;

import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.BaseTreeNodeTestTree;
import io.github.astrapi69.id.generate.LongIdGenerator;

public class MergeTreeNodesVisitorTest
{
	public void test()
	{

		BaseTreeNode<String, Long> root;
		BaseTreeNode<String, Long> fourthChild;
		BaseTreeNodeTestTree testTree;
		MergeTreeNodesVisitor<String, Long> mergeTreeNodesVisitor;
		LongIdGenerator idGenerator;

		idGenerator = LongIdGenerator.of(0L);
		root = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).value("I'm root")
			.build();

		fourthChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the fourth child").build();

		root.addChild(fourthChild);

		testTree = new BaseTreeNodeTestTree();
		mergeTreeNodesVisitor = new MergeTreeNodesVisitor<>(testTree.getRoot());

		root.accept(mergeTreeNodesVisitor);
	}
}
