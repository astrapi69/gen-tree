## Change log
----------------------

Version 5.5
-------------

ADDED:

- new generic id field in class SimpleTreeNode
- new class TreeNodeTransformer that transforms between BaseTreeNode to TreeIdNode and back

CHANGED:

- the id field from the BaseTreeNode class has now a generic type
- moved dependency data-api to a test-dependency
- moved dependency id-generate to a test-dependency
- method isNode in the BaseTreeNode class is now public
- rename of class ParentIdTreeNode to TreeIdNode
- tagged interface ILinkedNode as deprecated
- moved LinkedNode to package 'io.github.astrapi69.tree.binary'

Version 5.4
-------------

ADDED:

- new BaseTreeNode class provides the same functionality as TreeNode without implementing an interface
- new class ParentIdTreeNode without direct parent reference, but therefore instead an id and parentId
- new class SimpleTreeNode that represents the leftmost child to right sibling style

CHANGED:

- update of test dependency test-objects to new major version 6
- moved tree element classes to its own package 'io.github.astrapi69.tree.element'
- moved class GenericBinaryTree to its own package 'io.github.astrapi69.tree.binary'
- removed unused class ChainableTreeNode and the corresponding interface IChainableTreeNode

Version 5.3
-------------

ADDED:

- new abstract class GenericBinaryTree that represents a generic binary tree
- new gradle-plugin dependency of 'com.diffplug.spotless:spotless-plugin-gradle' in version 6.3.0 for formatting source code
- new gradle-plugin dependency of 'org.ajoberstar.grgit:grgit-gradle' in version 4.4.1 for create git release tags
- new workflow github-action for 'Java CI with Gradle' with codecov-action

CHANGED:

- update gradle to new version 7.4
- update of lombok version to 1.18.22
- improve gradle build performance by adding new gradle parameters for caching, parallel, configure on demand and file watch
- update of com.github.ben-manes.versions.gradle.plugin to new version 0.42.0
- update of test dependency test-objects version to 5.7
- update of test dependency testng version to 7.5
- update of test dependency jobj-contract-verifier version to 3.5

Version 5.2
-------------

ADDED:

- new class TreeElementNode that creates a TreeNode from a TreeElement

CHANGED:

- update gradle to new version 7.2
- update of test dependency test-objects version to 5.5
- replaced dependency jobject-evaluate with new dependency jobj-contract-verifier in version 3.3

Version 5.1
-------------

ADDED:

- new method that get from a given parent the child with a given index
- new method that get the index from a given child from the given parent

CHANGED:

- added exclude in EqualsAndHashCode for the children field

Version 5
-------------

ADDED:

- gradle as new build system

CHANGED:

- changed project nature from maven to gradle nature
- changed to new package io.github.astrapi69
- removed maven related files
- update of test-objects dependency version to 5.3
- moved interfaces from package ifaces to new appropriate api package

Version 4.12
-------------

ADDED:

- created new tree node IChainableTreeNode
- created new linked node ILinkedNode
- created methods in IChainableTreeNode

* getAllParent
* getChildCount
* getChildren
* getLevel
* toList

- created methods in ILinkedNode

* getNextCount
* getNextLinkedNodes
* getPreviousCount
* getPreviousLinkedNodes
* toList

CHANGED:

- update of parent version to 4.5
- update of test-objects dependency version to 5
- update of jobject-evaluate dependency version to 2.5

Version 4.11
-------------

ADDED:

- new CHANGELOG.md file created
- new eclipse launch scripts created

CHANGED:

- update of parent version to 3.12
- removed unneeded .0 at the end of version
- unit tests extended for improve code coverage
- moved TreeElement from test package to main package

Notable links:
[keep a changelog](http://keepachangelog.com/en/1.0.0/) Don’t let your friends dump git logs into
changelogs
