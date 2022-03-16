package io.github.astrapi69.tree;

public class DisplayValueOfSimpleTreeNodeVisitor<T> implements Visitor<SimpleTreeNode<T>> {

    @Override
    public void visit(SimpleTreeNode<T> simpleTreeNode) {
        System.out.println(simpleTreeNode.getValue());
    }
}
