## Change log
----------------------

Version 5.2-SNAPSHOT
-------------

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
[keep a changelog](http://keepachangelog.com/en/1.0.0/) Don’t let your friends dump git logs into changelogs
