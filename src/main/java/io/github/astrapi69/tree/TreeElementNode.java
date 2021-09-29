package io.github.astrapi69.tree;

import lombok.NonNull;

/**
 * The concreted class {@link TreeElementNode}
 */
public class TreeElementNode extends TreeNode<TreeElement>
{

	/**
	 * Instantiates a new {@link TreeElementNode}
	 *
	 * @param value the value
	 */
	public TreeElementNode(final @NonNull TreeElement value)
	{
		this(value, null);
	}

	/**
	 * Instantiates a new {@link TreeElementNode}
	 *
	 * @param value          the value
	 * @param parentTreeNode the parent tree node
	 */
	public TreeElementNode(final @NonNull TreeElement value,
		final TreeNode<TreeElement> parentTreeNode)
	{
		super(value);
		setParent(parentTreeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setValue(final TreeElement value)
	{
		super.setValue(value);
		if (value != null)
		{
			setNode(value.isNode());
			setDisplayValue(value.getName());
		}
	}
}
