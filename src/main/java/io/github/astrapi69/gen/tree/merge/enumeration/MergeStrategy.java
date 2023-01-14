package io.github.astrapi69.gen.tree.merge.enumeration;

/**
 * The enum {@link MergeStrategy} will be used for merge tree nodes
 */
public enum MergeStrategy
{
	/**
	 * This strategy will overwrite the existing node with the new one
	 */
	OVERWRITE,

	/**
	 * This strategy will keep the existing node and ignore the new one
	 */
	KEEP
}
