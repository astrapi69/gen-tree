/**
 * The MIT License
 *
 * Copyright (C) 2007 Asterios Raptis
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
package de.alpharogroup.tree.ifaces;

import java.io.Serializable;
import java.util.List;

/**
 * The Interface ITreeNode.
 *
 * @param <T>
 *            the generic type
 */
public interface ITreeNode<T> extends Serializable
{

	/**
	 * Adds the child.
	 *
	 * @param child
	 *            the child
	 */
	void addChild(final ITreeNode<T> child);

	/**
	 * Gets the optional display value.
	 *
	 * @return the display value
	 */
	String getDisplayValue();

	/**
	 * Sets the optional display value.
	 *
	 * @param displayValue
	 *            the new optional display value
	 */
	void setDisplayValue(final String displayValue);

	/**
	 * Adds the child.
	 *
	 * @param index
	 *            the index
	 * @param child
	 *            the child
	 * @throws IndexOutOfBoundsException
	 *             the index out of bounds exception
	 */
	void addChildAt(final int index, final ITreeNode<T> child) throws IndexOutOfBoundsException;

	/**
	 * Equals.
	 *
	 * @param treeNode
	 *            the tree node
	 * @return true, if successful
	 */
	boolean equals(final ITreeNode<T> treeNode);

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	int getChildCount();

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	List<ITreeNode<T>> getChildren();

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	ITreeNode<T> getParent();

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Checks for children.
	 *
	 * @return true, if successful
	 */
	boolean hasChildren();

	/**
	 * Checks for parent.
	 *
	 * @return true, if successful
	 */
	boolean hasParent();

	/**
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	boolean isLeaf();

	/**
	 * Checks if is node.
	 *
	 * @return true, if is node
	 */
	boolean isNode();

	/**
	 * Checks if this treenode is root.
	 *
	 * @return true, if is root
	 */
	boolean isRoot();

	/**
	 * Removes the child.
	 *
	 * @param child
	 *            the child
	 */
	void removeChild(final ITreeNode<T> child);

	/**
	 * Removes the child.
	 *
	 * @param index
	 *            the index
	 * @throws IndexOutOfBoundsException
	 *             the index out of bounds exception
	 */
	void removeChildAt(final int index) throws IndexOutOfBoundsException;

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 */
	void setChildren(final List<ITreeNode<T>> children);

	/**
	 * Sets the parent.
	 *
	 * @param parent
	 *            the new parent
	 */
	void setParent(final ITreeNode<T> parent);

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(final T value);

	/**
	 * To list.
	 *
	 * @return the list
	 */
	List<ITreeNode<T>> toList();

	/**
	 * Traverse.
	 *
	 * @param node
	 *            the node
	 * @param list
	 *            the list
	 */
	void traverse(final ITreeNode<T> node, final List<ITreeNode<T>> list);

	/**
	 * Returns the next sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's last child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	ITreeNode<T> getNextSibling();

	/**
	 * Returns the previous sibling of this node in the parent's children list. Returns null if this
	 * node is the root or is the parent's first child.
	 *
	 * @return the next sibling of this node or null if this node is the root or is the parent's
	 *         last child.
	 */
	ITreeNode<T> getPreviousSibling();

	/**
	 * Returns all siblings of this node in the parent's children list. Returns null if this node is
	 * the root.
	 *
	 * @return Returns all siblings of this node or null if this node is the root.
	 */
	List<ITreeNode<T>> getAllSiblings();

	/**
	 * Returns the distance from the root to this node. Returns 0 if this node is the root.
	 *
	 * @return the level from this node.
	 */
	int getLevel();

	/**
	 * Returns the depth of the tree beginning at this node Returns 0 if this node has no children.
	 *
	 * @return the depth of the tree beginning at this node.
	 */
	int getDepth();

}