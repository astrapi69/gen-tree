package io.github.astrapi69.gen.tree.visitor;

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.gen.tree.BaseTreeNode;
import lombok.NonNull;

public class MergeTreeNodesVisitor<T, K> implements Visitor<BaseTreeNode<T, K>>
{
	BaseTreeNode<T, K> mergeWith;
	public MergeTreeNodesVisitor(final @NonNull BaseTreeNode<T, K> mergeWith) {
		this.mergeWith = mergeWith;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(BaseTreeNode<T, K> treeNode)
	{
		BaseTreeNode<T, K> byId = this.mergeWith.findById(treeNode.getId());
		if(byId != null) {
			byId.getParent().getChildren();
		}
	}
}
