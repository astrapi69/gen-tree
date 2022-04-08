package io.github.astrapi69.tree.element;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * The class {@link GenericTreeElement} represents as the name already presume a tree element
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = { "parent" })
@ToString(exclude = { "parent" })
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenericTreeElement<T> implements Serializable
{
	public static final String DEFAULT_CONTENT_KEY = "default_content";
	/** The serial Version UID */
	private static final long serialVersionUID = 1L;
	/** The map with optional properties */
	@Builder.Default
	final Map<String, Object> properties = new LinkedHashMap<>();
	/** The name of this tree element. */
	String name;
	/** The flag that indicates if this tree element is a node. */
	boolean node;
	/** The flag that indicates if a text label should shown if an icon exists */
	boolean withText;
	/** The icon path for a custom tree icon, if not set default icon will be set */
	String iconPath;
	/** The parent of this tree element. */
	GenericTreeElement<T> parent;

	/**
	 * Gets the default content object from the map
	 *
	 * @return the default content object from the map
	 */
	public T getDefaultContent()
	{
		return (T)properties.get(DEFAULT_CONTENT_KEY);
	}

	/**
	 * Sets the default content object from the map to the given object
	 *
	 * @param defaultContent
	 *            the default content object to set
	 * @return this object
	 */
	public GenericTreeElement setDefaultContent(T defaultContent)
	{
		properties.put(DEFAULT_CONTENT_KEY, defaultContent);
		return this;
	}
}
