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
package io.github.astrapi69.gen.tree.api;

import io.github.astrapi69.data.identifiable.GenericIdentifiable;
import io.github.astrapi69.gen.tree.handler.IBaseTreeNodeHandlerExtensions;
import lombok.NonNull;

/**
 * The Interface {@link IBaseTreeNode} extends {@link ITreeNode} and provides an additional id field
 * that can be used as key
 *
 * @param <V>
 *            the generic type of the value
 * @param <K>
 *            the generic type of the id of the node
 * @param <T>
 *            the generic type of the concrete tree node
 */
public interface IBaseTreeNode<V, K, T extends IBaseTreeNode<V, K, T>>
	extends
		ITreeNode<V, T>,
		GenericIdentifiable<K>
{

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	default T findById(final @NonNull K id)
	{
		return IBaseTreeNodeHandlerExtensions.findById((T)this, id);
	}

	/**
	 * Sorts the children collection if the comparator is not null
	 */
	void sortChildren();
}
