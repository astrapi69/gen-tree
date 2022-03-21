package io.github.astrapi69.tree.visitor;

import io.github.astrapi69.design.pattern.visitor.Visitor;
import io.github.astrapi69.tree.SimpleTreeNode;

public class DisplayValueOfSimpleTreeNodeVisitor<T> implements Visitor<SimpleTreeNode<T>>
{

	@Override
	public void visit(SimpleTreeNode<T> simpleTreeNode)
	{
		System.err.println(simpleTreeNode.getValue());
	}
}
