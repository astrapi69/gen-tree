module gen.tree {
    requires lombok;
    requires design.patterns.visitor;
    exports io.github.astrapi69.tree.api;
    exports io.github.astrapi69.tree;
    exports io.github.astrapi69.tree.binary;
    exports io.github.astrapi69.tree.convert;
    exports io.github.astrapi69.tree.handler;
    exports io.github.astrapi69.tree.visitor;
}
