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
package io.github.astrapi69.gen.tree;

import io.github.astrapi69.id.generate.LongIdGenerator;

public class BaseTreeNodeTestData
{

	/**
	 * Builds a new base test tree on every call. The tree used to be a single static instance, so a
	 * test that merged into it (for instance {@code MergeTreeNodesVisitorTest}) changed it for
	 * every later test in the same JVM. Under PIT a minion runs the tests of several mutants in one
	 * JVM, so whether such a test passed depended on which mutants ran before it in the same
	 * minion, and the mutation score swung between runs of the same commit
	 *
	 * @return the root of a new base test tree, see {@link BaseTreeNodeTestTree}
	 */
	public static BaseTreeNode<String, Long> getBaseTestTree()
	{
		return new BaseTreeNodeTestTree().getRoot();
	}

	public static BaseTreeNode<String, Long> getSimpleTestTree()
	{
		BaseTreeNode<String, Long> root;
		BaseTreeNode<String, Long> secondChild;
		BaseTreeNode<String, Long> fourthChild;
		BaseTreeNode<String, Long> sixthGrandChild;
		LongIdGenerator idGenerator;

		idGenerator = LongIdGenerator.of(0L);
		root = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).value("I'm root")
			.build();

		secondChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the second child").build();

		fourthChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
			.value("I'm the fourth child").build();

		sixthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
			.parent(secondChild).value("I'm the sixth grand child").build();

		root.addChild(secondChild);
		root.addChild(fourthChild);
		secondChild.addChild(sixthGrandChild);
		return root;
	}


}
