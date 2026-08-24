/**
 * The MIT License
 *
 * Copyright (C) 2026 Asterios Raptis
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
package io.github.astrapi69.gen.tree.example;

import java.util.Collection;

import io.github.astrapi69.gen.tree.BaseTreeNode;

public class ExpressionTreeExample
{

	public static void main(String[] args)
	{
		// Create the operand nodes
		BaseTreeNode<String, Long> a = new BaseTreeNode<>("a");
		BaseTreeNode<String, Long> b = new BaseTreeNode<>("b");
		BaseTreeNode<String, Long> c = new BaseTreeNode<>("c");

		// Create the operator nodes
		BaseTreeNode<String, Long> multiply = new BaseTreeNode<>("*");
		BaseTreeNode<String, Long> add = new BaseTreeNode<>("+");

		// Construct the tree by setting the children
		multiply.addChild(b); // 'b' is the left child of '*'
		multiply.addChild(c); // 'c' is the right child of '*'

		add.addChild(a); // 'a' is the left child of '+'
		add.addChild(multiply); // The multiplication result is the right child of '+'

		// Now 'add' is the root of the entire expression tree
		evaluateExpressionTree(add);
		traverseAndPrintInOrder(add);
		traverseAndPrintPostOrder(add);
	}

	// Example of tree traversal in infix order (left, root, right)
	public static void traverseAndPrintInOrder(BaseTreeNode<String, Long> node)
	{
		if (node == null)
			return;

		Collection<BaseTreeNode<String, Long>> children = node.getChildren();
		if (children.size() > 0)
		{
			traverseAndPrintInOrder((BaseTreeNode<String, Long>)children.toArray()[0]); // left
																						// child
		}

		// Print the current node's value (operator or operand)
		System.out.print(node.getValue() + " ");

		if (children.size() > 1)
		{
			traverseAndPrintInOrder((BaseTreeNode<String, Long>)children.toArray()[1]); // right
																						// child
		}
	}

	// Example of tree traversal in post-order (left, right, root)
	public static void traverseAndPrintPostOrder(BaseTreeNode<String, Long> node)
	{
		if (node == null)
			return;

		Collection<BaseTreeNode<String, Long>> children = node.getChildren();
		if (children.size() > 0)
		{
			traverseAndPrintPostOrder((BaseTreeNode<String, Long>)children.toArray()[0]); // left
																							// child
		}

		if (children.size() > 1)
		{
			traverseAndPrintPostOrder((BaseTreeNode<String, Long>)children.toArray()[1]); // right
																							// child
		}

		// Print the current node's value (operator or operand)
		System.out.print(node.getValue() + " ");
	}

	// Simple method to "evaluate" the tree (in this case, just prints the expression)
	public static void evaluateExpressionTree(BaseTreeNode<String, Long> node)
	{
		System.out.print("Expression Tree in Infix: ");
		traverseAndPrintInOrder(node); // a + b * c
		System.out.println();
		System.out.print("Expression Tree in Postfix: ");
		traverseAndPrintPostOrder(node); // a b c * +
		System.out.println();
	}
}
