/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.tree.convert;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import io.github.astrapi69.tree.BaseTreeNode;
import io.github.astrapi69.tree.TreeIdNode;
import lombok.NonNull;

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
		return root.traverse().stream().collect(Collectors.toMap(BaseTreeNode::getId, // keyMapper
			TreeNodeTransformer::toTreeIdNode, // valueMapper
			(first, second) -> first, // mergeFunction
			LinkedHashMap::new // mapFactory
		));
	}

	/**
	 * Transforms the given {@link BaseTreeNode} object to a {@link TreeIdNode} object
	 * 
	 * @param baseTreeNode
	 *            the {@link BaseTreeNode} object to convert
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @return the new created {@link TreeIdNode} object
	 */
	public static <T, K> TreeIdNode<T, K> toTreeIdNode(
		@NonNull final BaseTreeNode<T, K> baseTreeNode)
	{
		return TreeIdNode.<T, K> builder().id(baseTreeNode.getId())
			.parentId(baseTreeNode.hasParent() ? baseTreeNode.getParent().getId() : null)
			.value(baseTreeNode.getValue()).displayValue(baseTreeNode.getDisplayValue())
			.leaf(baseTreeNode.isLeaf()).childrenIds(baseTreeNode.getChildren().stream()
				.map(BaseTreeNode::getId).collect(Collectors.toSet()))
			.build();
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
		return root.traverse().stream().collect(Collectors.toMap(BaseTreeNode::getId, // keyMapper
			element -> element, // valueMapper
			(first, second) -> first, // mergeFunction
			LinkedHashMap::new // mapFactory
		));
	}

	/**
	 * Transforms the given {@link Map} object that contains {@link TreeIdNode} objects as values
	 * and the id as key
	 *
	 * @param treeIdNodeMap
	 *            the {@link Map} object with the {@link TreeIdNode} objects to transform
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @return a {@link Map} object with the corresponding {@link BaseTreeNode} objects
	 */
	public static <T, K> Map<K, BaseTreeNode<T, K>> transform(
		Map<K, TreeIdNode<T, K>> treeIdNodeMap)
	{
		final Map<K, BaseTreeNode<T, K>> baseTreeNodeMap = treeIdNodeMap.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, // keyMapper
				entry -> BaseTreeNode.<T, K> builder().id(entry.getValue().getId())
					.value(entry.getValue().getValue())
					.displayValue(entry.getValue().getDisplayValue())
					.leaf(entry.getValue().isLeaf()).build(), // valueMapper
				(first, second) -> first, // mergeFunction
				LinkedHashMap::new // mapFactory
			));
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
				.map(baseTreeNodeMap::get).collect(Collectors.toSet());
		}
		return baseTreeNodeMap;
	}

	/**
	 * Retrieves the root {@link BaseTreeNode} object from the given @link Map} object that contains
	 * {@link TreeIdNode} objects as values and the id as key
	 *
	 * @param treeIdNodeMap
	 *            the {@link Map} object with the {@link TreeIdNode} objects to transform
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @return the root {@link BaseTreeNode} object or null if not found
	 */
	public static <T, K> BaseTreeNode<T, K> getRoot(
		final @NonNull Map<K, TreeIdNode<T, K>> treeIdNodeMap)
	{
		AtomicReference<BaseTreeNode<T, K>> root = new AtomicReference<>();
		if (treeIdNodeMap.isEmpty())
		{
			return root.get();
		}
		transform(treeIdNodeMap).entrySet().stream().findAny().ifPresent(entry -> {
			root.set(entry.getValue().getRoot());
		});
		return root.get();
	}
}
