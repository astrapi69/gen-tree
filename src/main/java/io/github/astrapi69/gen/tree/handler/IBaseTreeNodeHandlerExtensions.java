package io.github.astrapi69.gen.tree.handler;

import java.util.concurrent.atomic.AtomicReference;

import lombok.NonNull;
import io.github.astrapi69.gen.tree.api.IBaseTreeNode;

public class IBaseTreeNodeHandlerExtensions
{

	public static <V, K, T extends IBaseTreeNode<V, K, T>> T findById(final @NonNull T treeNode,
		final K id)
	{
		final AtomicReference<Boolean> stopSearch = new AtomicReference<>(Boolean.FALSE);
		final AtomicReference<T> found = new AtomicReference<>();
		treeNode.accept(currentTreeNode -> {
			if(!stopSearch.get()) {
				if(currentTreeNode.getId().equals(id))
				{
					stopSearch.set(Boolean.TRUE);
					found.set(currentTreeNode);
				}
			}
		});
		return found.get();
	}
}
