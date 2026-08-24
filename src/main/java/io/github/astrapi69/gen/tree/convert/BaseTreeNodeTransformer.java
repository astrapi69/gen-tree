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
package io.github.astrapi69.gen.tree.convert;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import io.github.astrapi69.gen.tree.BaseTreeNode;
import io.github.astrapi69.gen.tree.TreeIdNode;
import lombok.NonNull;

/**
 * The class {@link BaseTreeNodeTransformer} provides algorithms for converting between the
 * {@link BaseTreeNode} objects and {@link TreeIdNode} objects. This is useful if you want to save
 * {@link BaseTreeNode} objects in a store, you have first to transform the {@link BaseTreeNode}
 * objects (you want to store) to {@link TreeIdNode} objects and save them. Note: The
 * {@link BaseTreeNode} object can not be stored
 */
public final class BaseTreeNodeTransformer
{
	private BaseTreeNodeTransformer()
	{
	}

	/**
	 * Transforms the given {@link BaseTreeNode} object to a {@link Map} object with the key and the
	 * corresponding {@link TreeIdNode} objects
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param root
	 *            the {@link BaseTreeNode} object to transform
	 * @return a {@link Map} object with the corresponding {@link TreeIdNode} objects
	 */
	public static <T, K> Map<K, TreeIdNode<T, K>> toKeyMap(final @NonNull BaseTreeNode<T, K> root)
	{
		return root.traverse().stream().collect(Collectors.toMap(BaseTreeNode::getId, // keyMapper
			BaseTreeNodeTransformer::toTreeIdNode, // valueMapper
			BaseTreeNodeTransformer::throwOnDuplicateId, // mergeFunction
			LinkedHashMap::new // mapFactory
		));
	}

	/**
	 * Merge function for a
	 * {@link Collectors#toMap(java.util.function.Function, java.util.function.Function, java.util.function.BinaryOperator)}
	 * collector that rejects two {@link TreeIdNode} objects mapped to the same id instead of
	 * silently keeping the first one and dropping the second, which would hide a corrupt id
	 * assignment
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param first
	 *            the {@link TreeIdNode} object that was collected first
	 * @param second
	 *            the {@link TreeIdNode} object that was collected under the same, already used id
	 * @return never returns normally
	 * @throws IllegalStateException
	 *             always, reporting the duplicate id
	 */
	private static <T, K> TreeIdNode<T, K> throwOnDuplicateId(final TreeIdNode<T, K> first,
		final TreeIdNode<T, K> second)
	{
		throw new IllegalStateException(
			"BaseTreeNodeTransformer.toKeyMap: duplicate id " + first.getId());
	}

	/**
	 * Transforms the given {@link BaseTreeNode} object to a {@link TreeIdNode} object
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNode
	 *            the {@link BaseTreeNode} object to convert
	 * @return the new created {@link TreeIdNode} object
	 */
	public static <T, K> TreeIdNode<T, K> toTreeIdNode(
		final @NonNull BaseTreeNode<T, K> baseTreeNode)
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
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param root
	 *            the {@link BaseTreeNode} object to transform
	 * @return a {@link Map} object with the corresponding {@link BaseTreeNode} objects
	 */
	public static <T, K> Map<K, BaseTreeNode<T, K>> toKeyBaseTreeNodeMap(
		final @NonNull BaseTreeNode<T, K> root)
	{
		return root.traverse().stream().collect(Collectors.toMap(BaseTreeNode::getId, // keyMapper
			element -> element, // valueMapper
			(first, second) -> {
				throw new IllegalStateException(
					"BaseTreeNodeTransformer.toKeyBaseTreeNodeMap: duplicate id " + first.getId());
			}, // mergeFunction
			LinkedHashMap::new // mapFactory
		));
	}

	/**
	 * Transforms the given {@link Map} object that contains {@link TreeIdNode} objects as values
	 * and the id as key
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeIdNodeMap
	 *            the {@link Map} object with the {@link TreeIdNode} objects to transform
	 * @return a {@link Map} object with the corresponding {@link BaseTreeNode} objects
	 */
	public static <T, K> Map<K, BaseTreeNode<T, K>> transform(
		final @NonNull Map<K, TreeIdNode<T, K>> treeIdNodeMap)
	{
		final Map<K, BaseTreeNode<T, K>> baseTreeNodeMap = treeIdNodeMap.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, // keyMapper
				entry -> BaseTreeNode.<T, K> builder().id(entry.getValue().getId())
					.value(entry.getValue().getValue())
					.displayValue(entry.getValue().getDisplayValue())
					.leaf(entry.getValue().isLeaf()).build(), // valueMapper
				BaseTreeNodeTransformer::throwOnDuplicateBaseTreeNodeId, // mergeFunction
				LinkedHashMap::new // mapFactory
			));
		for (Map.Entry<K, TreeIdNode<T, K>> entry : treeIdNodeMap.entrySet())
		{
			K key = entry.getKey();
			TreeIdNode<T, K> treeIdNode = entry.getValue();
			BaseTreeNode<T, K> baseTreeNode = baseTreeNodeMap.get(key);

			BaseTreeNode<T, K> parent = null;
			if (treeIdNode.getParentId() != null)
			{
				parent = baseTreeNodeMap.get(treeIdNode.getParentId());
				if (parent == null)
				{
					throw new IllegalStateException("BaseTreeNodeTransformer.transform: node " + key
						+ " references unknown parent id " + treeIdNode.getParentId());
				}
			}
			baseTreeNode.setParent(parent);

			Set<BaseTreeNode<T, K>> children = new LinkedHashSet<>();
			for (K childId : treeIdNode.getChildrenIds())
			{
				BaseTreeNode<T, K> child = baseTreeNodeMap.get(childId);
				if (child == null)
				{
					throw new IllegalStateException("BaseTreeNodeTransformer.transform: node " + key
						+ " references unknown child id " + childId);
				}
				children.add(child);
			}
			baseTreeNode.setChildren(children);
		}
		assertNoCycles(baseTreeNodeMap);
		return baseTreeNodeMap;
	}

	/**
	 * Merge function for the id-to-node map built inside {@link #transform(Map)} that rejects two
	 * nodes mapped to the same id instead of silently keeping the first one, which would hide a
	 * corrupt id assignment
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param first
	 *            the {@link BaseTreeNode} object that was collected first
	 * @param second
	 *            the {@link BaseTreeNode} object that was collected under the same, already used id
	 * @return never returns normally
	 * @throws IllegalStateException
	 *             always, reporting the duplicate id
	 */
	private static <T, K> BaseTreeNode<T, K> throwOnDuplicateBaseTreeNodeId(
		final BaseTreeNode<T, K> first, final BaseTreeNode<T, K> second)
	{
		throw new IllegalStateException(
			"BaseTreeNodeTransformer.transform: duplicate id " + first.getId());
	}

	/**
	 * Verifies that every node's parent chain terminates at a root instead of looping back on
	 * itself. A cycle among {@link TreeIdNode} objects has no root at all, and wiring it into
	 * {@link BaseTreeNode} parent/child pointers would otherwise loop forever the first time
	 * something walks up from one of its members, for instance {@link BaseTreeNode#getRoot()}
	 * <p>
	 * Every node's chain is walked at most once in total: once a node's chain is known to reach a
	 * root, every node visited on the way there is remembered as resolved and skipped by later
	 * calls
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param baseTreeNodeMap
	 *            the id-to-node map to check
	 * @throws IllegalStateException
	 *             if a cycle is detected
	 */
	private static <T, K> void assertNoCycles(final Map<K, BaseTreeNode<T, K>> baseTreeNodeMap)
	{
		final Set<K> resolved = new LinkedHashSet<>();
		for (K key : baseTreeNodeMap.keySet())
		{
			if (resolved.contains(key))
			{
				continue;
			}
			final Set<K> onPath = new LinkedHashSet<>();
			BaseTreeNode<T, K> current = baseTreeNodeMap.get(key);
			while (current != null && !resolved.contains(current.getId()))
			{
				if (!onPath.add(current.getId()))
				{
					throw new IllegalStateException(
						"BaseTreeNodeTransformer.transform: cycle detected involving id "
							+ current.getId());
				}
				current = current.getParent();
			}
			resolved.addAll(onPath);
		}
	}

	/**
	 * Retrieves the root {@link BaseTreeNode} object from the given @link Map} object that contains
	 * {@link TreeIdNode} objects as values and the id as key
	 *
	 * @param <T>
	 *            the generic type of the value
	 * @param <K>
	 *            the generic type of the id of the node
	 * @param treeIdNodeMap
	 *            the {@link Map} object with the {@link TreeIdNode} objects to transform
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
			BaseTreeNode<T, K> any = entry.getValue();
			BaseTreeNode<T, K> treeNode = any.getRoot();
			root.set(treeNode);
		});
		return root.get();
	}
}
