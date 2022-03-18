package io.github.astrapi69.tree;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import io.github.astrapi69.design.pattern.visitor.Visitor;

public class TraverseSimpleTreeNodeVisitor<T> implements Visitor<SimpleTreeNode<T>>
{

	@Getter
	final Set<SimpleTreeNode<T>> allTreeNodes = new LinkedHashSet<>();

	@Override
	public void visit(SimpleTreeNode<T> simpleTreeNode)
	{
		allTreeNodes.add(simpleTreeNode);
	}
}
