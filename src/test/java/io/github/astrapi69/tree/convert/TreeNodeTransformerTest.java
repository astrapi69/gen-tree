package io.github.astrapi69.tree.convert;

import io.github.astrapi69.collections.set.SetFactory;
import io.github.astrapi69.id.generate.LongIdGenerator;
import io.github.astrapi69.tree.BaseTreeNode;
import io.github.astrapi69.tree.TreeIdNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * The unit test class for the class {@link TreeNodeTransformer}
 */
public class TreeNodeTransformerTest {


    BaseTreeNode<String, Long> root;
    BaseTreeNode<String, Long> firstChild;
    BaseTreeNode<String, Long> secondChild;
    BaseTreeNode<String, Long> firstGrandChild;
    BaseTreeNode<String, Long> firstGrandGrandChild;
    BaseTreeNode<String, Long> secondGrandGrandChild;
    BaseTreeNode<String, Long> firstGrandGrandGrandChild;
    BaseTreeNode<String, Long> secondGrandChild;
    BaseTreeNode<String, Long> thirdGrandChild;
    BaseTreeNode<String, Long> thirdChild;
    BaseTreeNode<String, Long> fourthGrandChild;
    BaseTreeNode<String, Long> fifthGrandChild;

    /**
     * Set up the tree structure for the unit tests
     *
     * <pre>
     *   +- root("I'm root")
     *      +- firstChild("I'm the first child")
     *      +- secondChild("I'm the second child")
     *      |  +- firstGrandChild("I'm the first grand child")
     *      |  |  +- firstGrandGrandChild("I'm the first grand grand child")
     *      |  |  +- secondGrandGrandChild("I'm the second grand grand child)
     *      |  |  |  +- firstGrandGrandGrandChild ("I'm the first grand grand grand child")
     *      |  +- secondGrandChild("I'm the second grand child")
     *      |  +- thirdGrandChild(null)
     *      +- thirdChild("I'm the third child")
     *      |  +- fourthGrandChild(null)
     *      |  +- fifthGrandChild("I'm the fifth grand child")
     * </pre>
     */
    @BeforeMethod
    public void setup()
    {
        LongIdGenerator idGenerator = LongIdGenerator.of(0L);
        root = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).value("I'm root")
                .build();

        firstChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .value("I'm the first child").build();

        secondChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
                .value("I'm the second child").build();

        firstGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .parent(secondChild).value("I'm the first grand child").build();

        firstGrandGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .parent(firstGrandChild).value("I'm the first grand grand child").build();

        secondGrandGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .parent(firstGrandChild).value("I'm the second grand grand child").build();

        firstGrandGrandGrandChild = BaseTreeNode.<String, Long> builder()
                .id(idGenerator.getNextId()).parent(secondGrandGrandChild)
                .value("I'm the first grand grand grand child").build();

        secondGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .parent(secondChild).value("I'm the second grand child").build();

        thirdGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .parent(secondChild).value(null).build();

        thirdChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId()).parent(root)
                .value("I'm the third child").build();

        fourthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .parent(thirdChild).value(null).build();

        fifthGrandChild = BaseTreeNode.<String, Long> builder().id(idGenerator.getNextId())
                .parent(thirdChild).value("I'm the fifth grand child").build();

        // initialize all children
        root.addChild(firstChild);
        root.addChild(secondChild);
        root.addChild(thirdChild);

        secondChild.addChild(firstGrandChild);

        firstGrandChild.addChild(firstGrandGrandChild);
        firstGrandChild.addChild(secondGrandGrandChild);

        secondGrandGrandChild.addChild(firstGrandGrandGrandChild);

        secondChild.addChild(secondGrandChild);
        secondChild.addChild(thirdGrandChild);

        thirdChild.addChild(fourthGrandChild);
        thirdChild.addChild(fifthGrandChild);
    }

    /**
     * Test method for {@link TreeNodeTransformer#toKeyMap(BaseTreeNode)}
     */
    @Test
    public void testToKeyMap() {
        Map<Long, TreeIdNode<String, Long>> actual;

        actual = TreeNodeTransformer.toKeyMap(root);
        assertNotNull(actual);
        assertTrue(actual.size() == 12);
    }

    /**
     * Test method for {@link TreeNodeTransformer#transform(BaseTreeNode)}
     */
    @Test
    public void testConvert() {
        TreeIdNode<String, Long> actual;
        TreeIdNode<String, Long> expected;

        actual = TreeNodeTransformer.transform(root);
        expected = TreeIdNode.<String, Long>builder()
                .id(root.getId())
                .leaf(root.isLeaf())
                .childrenIds(SetFactory.newLinkedHashSet(1L,2L,9L))
                .value(root.getValue())
                .build();
        assertEquals(actual, expected);
    }

    /**
     * Test method for {@link TreeNodeTransformer#transform(Map)}
     */
//    @Test
//    public void testTestConvert() {
//        Map<Long, TreeIdNode<String, Long>> actual;
//
//        actual = TreeNodeTransformer.toKeyMap(root);
//        assertNotNull(actual);
//        assertTrue(actual.size() == 12);
//        Map<Long, BaseTreeNode<String, Long>> convert = TreeNodeTransformer.transform(actual);
//
//        assertTrue(convert.size() == 12);
//
//        Map<Long, BaseTreeNode<String, Long>> longBaseTreeNodeMap = TreeNodeTransformer.toKeyBaseTreeNodeMap(root);
//        assertEquals(convert, longBaseTreeNodeMap);
//    }
}
