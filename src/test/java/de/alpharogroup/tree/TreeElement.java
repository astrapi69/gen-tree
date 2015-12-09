package de.alpharogroup.tree;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The class {@link TreeElement}.
 *
 * @param <P> the generic type of the parent
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeElement implements Serializable {
	/** The serial Version UID */
	private static final long serialVersionUID = 1L;
	private String name;
	private Boolean node;
	private TreeElement parent;
}