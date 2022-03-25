package io.github.astrapi69.tree.convert;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NonNull;
import io.github.astrapi69.tree.BaseTreeNode;
import io.github.astrapi69.tree.TreeIdNode;

/**
 * The class {@link TreeNodeTransformer} provides algorithms for convert and transform between the
 * {@link BaseTreeNode} objects and {@link TreeIdNode} objects
 */
public final class TreeNodeTransformer
{
	private TreeNodeTransformer()
	{
	}

	/**
	 * Transforms the given {@link BaseTreeNode} object to a {@link Map} object with the key and the
	 * corresponding {@link TreeIdNode} objects
	 * 
	 * @param root
	 *            the {@link BaseTreeNode} object to transform
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @return a {@link Map} object with the corresponding {@link TreeIdNode} objects
	 */
	public static <T, K> Map<K, TreeIdNode<T, K>> toKeyMap(@NonNull final BaseTreeNode<T, K> root)
	{
		return root.traverse().stream().collect(Collectors.toMap(element -> element.getId(), // keyMapper
			element -> transform(element), // valueMapper
			(first, second) -> first, // mergeFunction
			LinkedHashMap::new // mapFactory
		));
	}

	/**
	 * Transforms the given {@link BaseTreeNode} object to a {@link Map} object with the key and the
	 * corresponding {@link BaseTreeNode} objects
	 *
	 * @param root
	 *            the {@link BaseTreeNode} object to transform
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @return a {@link Map} object with the corresponding {@link BaseTreeNode} objects
	 */
	public static <T, K> Map<K, BaseTreeNode<T, K>> toKeyBaseTreeNodeMap(
		@NonNull final BaseTreeNode<T, K> root)
	{
		return root.traverse().stream().collect(Collectors.toMap(element -> element.getId(), // keyMapper
			element -> element, // valueMapper
			(first, second) -> first, // mergeFunction
			() -> new LinkedHashMap<>() // mapFactory
		));
	}

	/**
	 * Transforms the given {@link BaseTreeNode} object to a {@link TreeIdNode} object
	 * @param baseTreeNode
	 *            the {@link BaseTreeNode} object to convert
	 * @param <T>
	 * @param <K>
	 * @return
	 */
	public static <T, K> TreeIdNode<T, K> transform(@NonNull final BaseTreeNode<T, K> baseTreeNode)
	{
		return TreeIdNode.<T, K> builder().id(baseTreeNode.getId())
			.parentId(baseTreeNode.hasParent() ? baseTreeNode.getParent().getId() : null)
			.value(baseTreeNode.getValue()).displayValue(baseTreeNode.getDisplayValue())
			.leaf(baseTreeNode.isLeaf()).childrenIds(baseTreeNode.getChildren().stream()
				.map(child -> child.getId()).collect(Collectors.toSet()))
			.build();
	}

	public static <T, K> Map<K, BaseTreeNode<T, K>> toKeyBaseTreeNodeMap(Map<K, TreeIdNode<T, K>> treeIdNodeMap)
	{
		Map<K, BaseTreeNode<T, K>> baseTreeNodeMap = new LinkedHashMap<>(treeIdNodeMap.size());
		for (Map.Entry<K, TreeIdNode<T, K>> entry : treeIdNodeMap.entrySet())
		{
			K key = entry.getKey();
			TreeIdNode<T, K> treeIdNode = entry.getValue();
			baseTreeNodeMap.put(key,
				BaseTreeNode.<T, K> builder().id(treeIdNode.getId()).value(treeIdNode.getValue())
					.displayValue(treeIdNode.getDisplayValue()).leaf(treeIdNode.isLeaf()).build());
		}
		for (Map.Entry<K, TreeIdNode<T, K>> entry : treeIdNodeMap.entrySet())
		{
			K key = entry.getKey();
			TreeIdNode<T, K> treeIdNode = entry.getValue();
			BaseTreeNode<T, K> baseTreeNode = baseTreeNodeMap.get(key);
			BaseTreeNode<T, K> parent = treeIdNode.getParentId() != null
				? baseTreeNodeMap.get(treeIdNode.getParentId())
				: null;
			baseTreeNode.setParent(parent);
			Set<BaseTreeNode<T, K>> children = treeIdNode.getChildrenIds().stream()
				.map(id -> baseTreeNodeMap.get(id)).collect(Collectors.toSet());
		}
		return baseTreeNodeMap;
	}
}
