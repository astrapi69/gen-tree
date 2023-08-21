## Change log
----------------------

Version 8.4
-------------

ADDED:

- new visitor class FindValuesIBaseTreeNodeVisitor
- new visitor class MaxIndexFinderTreeNodeVisitor
- new method 'isAncestor' in interface ITreeNode created that checks if a given tree node is an ancestor of this tree node
- new method 'isDescendant' in interface ITreeNode created that checks if a given tree node is a descendant of this tree node
- new method 'move' in interface ITreeNode created that moves a given tree node to the given new parent tree node
- new method 'isAncestor' in the extension class ITreeNodeHandlerExtensions that checks if a given tree node is an ancestor of the given tree node
- new method 'isDescendant' in the extension class ITreeNodeHandlerExtensions that checks if a given tree node is a descendant of the given tree node
- new method 'move' in the extension class ITreeNodeHandlerExtensions that moves a given tree node to the given new parent tree node
- new method 'addChild' to insert a child tree node with a specific given index
- new method 'getChildAt' to get an optional of the child tree node with the given index
- new method 'getChildIndex' to the index from the given child tree node

CHANGED:

- update gradle version to 8.3
- update of gradle-plugin dependency spotless-plugin-gradle to new minor version 6.20.0
- update of dependency tree-api to new minor version 1.5

Version 8.3
-------------

ADDED:

- new method 'isChild' in interface ITreeNode created that checks if a given tree node is the child of this tree node
- new method 'isChild' in extension class ITreeNodeHandlerExtensions created that checks if a given tree node is the child of this tree node

CHANGED:

- update of gradle-plugin dependency 'com.github.ben-manes.versions.gradle.plugin' to new version 0.47.0
- update of dependency tree-api to new minor version 1.3

Version 8.2
-------------

ADDED:

- new field childComparator in class BaseTreeNode
- new visitor class MaxIndexFinderTreeNodeVisitor for find the greatest index in the tree
- new visitor class ReindexTreeNodeVisitor for reindex a tree node and the children of it

CHANGED:

- update gradle version to 8.1.1
- update of gradle-plugin dependency spotless-plugin-gradle to new minor version 6.19.0
- update of test dependency silly-collection to new major version 21
- update of test dependency testng to new minor version 7.8.0
- update of test dependency 'com.github.meanbeanlib:meanbean' to new version 3.0.0-M9

Version 8.1
-------------

ADDED:

- new enum class MergeStrategy created for indicate which strategy to take on merge process
- new merge visitor class BaseMergeTreeNodesVisitor with the MergeStrategy and a tree node to merge
- new method in class IBaseTreeNodeHandlerExtensions created for merge optional tree nodes with a given root node
- new method in class IBaseTreeNodeHandlerExtensions created for merge a list of tree nodes with a given root node

CHANGED:

- update of gradle-plugin dependency spotless-plugin-gradle to new minor version 6.13.0
- class MergeTreeNodesVisitor extends now BaseMergeTreeNodesVisitor and use the merge strategy KEEP

Version 8
-------------

ADDED:

- new extension class BaseTreeNodeVisitorHandlerExtensions for visit BaseTreeNode objects

CHANGED:

- moved test-dependency data-api to project dependency and update to new version 4.1
- interface IBaseTreeNode extends now from interface GenericIdentifiable

Version 7.5
-------------

ADDED:

- new method in interface IBaseTreeNode created for find a tree node by id
- new handler class IBaseTreeNodeHandlerExtensions created that provides an implementation of the method findById
- new visitor class MergeTreeNodesVisitor created for merge tree nodes

CHANGED:

- update gradle version to 8.0-rc-1
- update of gradle-plugin dependency spotless-plugin-gradle to new minor version 6.12.1
- update of test dependency silly-collection to new minor version 20.3
- update of test dependency testng to new minor version 7.7.1
- javadoc optimized

Version 7.4
-------------

CHANGED:

- update of dependency tree-api to new minor version 1.2

Version 7.3
-------------

ADDED:

- new dependency tree-api in minor version 1.1

CHANGED:

- move all classes to new base package io.github.astrapi69.gen.tree.*
- move of interface ITree to it own module tree-api

Version 7.2
-------------

ADDED:

- new interface ITree that defines only the abstract methods for the tree and the tree nodes
- new handler extension class TreeNodeVisitorHandlerExtensions created that handles only visitor issues for tree nodes

CHANGED:

- ownership from organization lightblueseas to github user ported

Version 7.1
-------------

ADDED:

- new checks if the parent is a node if adding a child

CHANGED:

- update gradle to new version 7.6
- update of gradle-plugin dependency 'com.github.ben-manes.versions.gradle.plugin' to new version 0.44.0
- update of gradle-plugin dependency 'spotless-plugin-gradle' to new minor version 6.12.0
- update of test dependency silly-collection to new minor version 20.1
- update of test dependency test-object to new minor version 7.2
- update of test dependency data-api to new major version 4
- update of test dependency id-generate to new minor version 1.1
- update of test dependency testng to new minor version 7.7.0

Version 7
-------------

ADDED:

- new module-info.java file with definition of required modules and packages to export
- extracted sections from build.gradle to its own gradle files for clearness

CHANGED:

- update to jdk version 11
- update gradle to new version 7.5.1
- update of lombok version to 1.18.24
- update of gradle-plugin dependency 'spotless-plugin-gradle' to new minor version 6.10.0
- update of gradle-plugin dependency 'grgit-gradle' to new minor version 5.0.0
- moved element classes to test module because there was only for presentation purposes
- replaced obsolete package.html with package-info.java files
- update of dependency visitor to new major version 6
- update of test dependency silly-collection to new major version 20
- update of test dependency test-object to new minor version 7.1
- update of test dependency testng to new patch version 7.6.1
- update of test dependency jobj-contract-verifier to new major version 4

Version 6.1
-------------

ADDED:

- new interface IBaseTreeNode that extends ITreeNode and provides an id field
- new extension class ITreeNodeHandlerExtensions for handling ITreeNode objects
- new extension class SimpleTreeNodeHandlerExtensions for handling SimpleTreeNode objects
- all relevant default methods from interface ITreeNode are now decorated with the new extension class
  ITreeNodeHandlerExtensions

CHANGED:

- rename of TreeNodeTransformer to BaseTreeNodeTransformer
- update of test dependency silly-collections to new minor version 18.1
- generified the concrete subclass in interface ITreeNode
- BaseTreeNode implements now the new interface IBaseTreeNode
- unit tests extended

Version 6
-------------

ADDED:

- new method that gets the root from a map of TreeIdNode objects
- ITreeNode extends now interface Acceptable for accepting Visitor objects
- new default method to interface ITreeNode that add a collection of children
- new visitor class FindValuesBaseTreeNodeVisitor that can find BaseTreeNode with the same value
- new method clearChildren in class BaseTreeNode that removes all children from the tree node
- new method clearAll in class BaseTreeNode that removes all descendants from the tree node
- new method removeChildren in class BaseTreeNode that removes the given collection from the tree node
- new method findAllByValue in class BaseTreeNode that finds all BaseTreeNode objects that have the same value as the
  given value
- new method findByValue in class BaseTreeNode that finds the first occurrence of BaseTreeNode object that have the same
  value as the given value
- new method contains in class BaseTreeNode that checks if the given BaseTreeNode object is a descendant of the tree
  node
- new method containsAll in class BaseTreeNode that checks if the given collection of BaseTreeNode objects are
  descendants of the tree node
- new method clearChildren in class ITreeNode that removes all children from the tree node
- new method clearAll in class ITreeNode that removes all descendants from the tree node
- new method removeChildren in class ITreeNode that removes the given collection from the tree node
- new method findAllByValue in class ITreeNode that finds all ITreeNode objects that have the same value as the given
  value
- new method findByValue in class ITreeNode that finds the first occurrence of ITreeNode object that have the same value
  as the given value
- new method contains in class ITreeNode that checks if the given ITreeNode object is a descendant of the tree node
- new method containsAll in class ITreeNode that checks if the given collection of ITreeNode objects are descendants of
  the tree node

CHANGED:

- update gradle to new version 7.4.2
- moved configuration file for formatting to src/test/resources directory
- removed deprecated interface ILinkedNode
- update of gradle-plugin dependency 'spotless-plugin-gradle' to new minor version 6.4.1
- ITreeNode children from List to more abstract Collection interface
- removed List related methods from interface ITreeNode
- replaced node flag with leaf flag in interface ITreeNode

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

- new BaseTreeNode class provides the same functionality as TreeNode without implementing an
  interface
- new class ParentIdTreeNode without direct parent reference, but therefore instead an id and
  parentId
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
- new gradle-plugin dependency of 'com.diffplug.spotless:spotless-plugin-gradle' in version 6.3.0
  for formatting source code
- new gradle-plugin dependency of 'org.ajoberstar.grgit:grgit-gradle' in version 4.4.1 for create
  git release tags
- new workflow github-action for 'Java CI with Gradle' with codecov-action

CHANGED:

- update gradle to new version 7.4
- update of lombok version to 1.18.22
- improve gradle build performance by adding new gradle parameters for caching, parallel, configure
  on demand and file watch
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
