## Change log
----------------------

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
