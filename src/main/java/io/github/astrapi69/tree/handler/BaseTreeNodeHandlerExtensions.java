package io.github.astrapi69.tree.handler;

import java.util.Collection;
import java.util.LinkedHashSet;

import io.github.astrapi69.tree.BaseTreeNode;

/**
 * The class {@link BaseTreeNodeHandlerExtensions} provides handler methods for the class {@link BaseTreeNode}
 */
public class BaseTreeNodeHandlerExtensions
{

	/**
	 * Returns all siblings of the given node in the parent's children list.
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the tree node
	 * @return Returns all siblings of this node
	 */
	public static <T, K> Collection<BaseTreeNode<T, K>> getAllSiblings(
		BaseTreeNode<T, K> baseTreeNode)
	{
		final BaseTreeNode<T, K> parent = baseTreeNode.getParent();
		if (parent == null)
		{
			return new LinkedHashSet<>();
		}
		final Collection<BaseTreeNode<T, K>> allSiblings = new LinkedHashSet<>(
			parent.getChildren());
		allSiblings.remove(baseTreeNode);
		return allSiblings;
	}
}
