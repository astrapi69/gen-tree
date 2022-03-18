package io.github.astrapi69.tree;

import io.github.astrapi69.design.pattern.visitor.Visitor;

public class DisplayValueOfSimpleTreeNodeVisitor<T> implements Visitor<SimpleTreeNode<T>>
{

	@Override
	public void visit(SimpleTreeNode<T> simpleTreeNode)
	{
		System.err.println(simpleTreeNode.getValue());
	}
}
