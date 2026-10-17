# Mutation testing

gen-tree runs [PIT](https://pitest.org/) on every push and pull request. The build fails when the
mutation score drops below the floor in `build.gradle`.

| Setting | Value | Where |
|---|---|---|
| Tool | PIT 1.25.9, Gradle plugin `info.solidsoft.pitest` 1.19.0, `pitest-testng-plugin` 1.0.0 | `gradle/libs.versions.toml` |
| Mutator group | `STRONGER` | `gradle/mutation-testing.gradle` |
| Scope | `io.github.astrapi69.gen.tree.*`, no excluded classes or methods | `gradle/mutation-testing.gradle` |
| Floor | `mutationThreshold = 95` (PIT compares the rounded percentage) | `build.gradle` |

`STRONGER` is `DEFAULTS` plus three mutators: `REMOVE_CONDITIONALS_EQUAL_IF` and
`REMOVE_CONDITIONALS_ORDER_IF`, which force a condition to true (`DEFAULTS` only forces conditions
to false), and `EXPERIMENTAL_SWITCH`. Forcing a guard to true finds guards whose protection no test
observes.

## How to run it

```
./gradlew pitest
```

The reports land in `build/reports/pitest/`: `index.html` to browse, `mutations.xml` with one entry
per mutant. CI runs the same task after `./gradlew build` (`.github/workflows/gradle.yml`) and then
converts `mutations.xml` into `mutation-context.json`, kept as the artifact `mutation-context`.

## Measured numbers

Measured on 2026-10-17 with PIT 1.25.9 on Java 21, three runs of the same commit and one run each
with 1 and 8 threads; every run gave the same status for every mutant.

| Mutators | State | Mutants | Killed | Timed out | Survived | No coverage | Score | Strict score |
|---|---|---:|---:|---:|---:|---:|---:|---:|
| STRONGER | before the triage (develop at `84ef29b`) | 579 | 545 | 1 | 29 | 4 | 94.3 % | 94.1 % |
| STRONGER | after the triage | 578 | 561 | 1 | 16 | 0 | **97.2 %** | 97.1 % |
| DEFAULTS | before the triage | 423 | 408 | 1 | 11 | 3 | 96.7 % | 96.5 % |
| DEFAULTS | after the triage | 422 | 415 | 1 | 6 | 0 | 98.6 % | 98.3 % |

The score is PIT's own: killed and timed out over all mutants. The strict score is the one
`mutation-context.json` reports next to it, with a timed-out mutant not counted as detected. The
one timed-out mutant removes the cycle check in `BaseTreeNodeTransformer.assertNoCycles`, which
turns a cycle into an endless loop; it times out on every run.

All 16 survivors under STRONGER are listed below, none of them is excluded from the run.

## What makes a kill count

A mutant is only killed if a test fails because of the mutant, every time. Three things in the
test suite made PIT's verdict wrong, or dependent on what else had run in the same JVM; they are
gone, and they should not come back:

- **No shared mutable fixtures.** `BaseTreeNodeTestData.getBaseTestTree()` returned one static
  tree that `MergeTreeNodesVisitorTest` merged into. A PIT minion runs the tests of many mutants in
  one JVM, so from its second run in a minion on that test failed whatever the mutant, and two
  mutants counted as killed by a test that checked nothing about them. Build a fresh fixture per
  test.
- **Nothing that rewrites static state of the classes under test.** `CloneQuietlyExtensions.clone`
  from jobj-cloner copies static fields too and replaced the coverage probes PIT keeps in
  `BaseTreeNode` ([astrapi69/jobj-cloner#1](https://github.com/astrapi69/jobj-cloner/issues/1));
  every line of `BaseTreeNode` run after that test counted as not covered.
- **A failed setup counts as a failure.** PIT's TestNG plugin counts a test that TestNG skipped
  because its `@BeforeMethod` failed as skipped, not failed, so a mutant that breaks the shared
  setup of a test class survived every test in it. `SkippedBySetupFailureListener` (test sources,
  registered in `META-INF/services/org.testng.ITestNGListener`) reports such a test as failed, as the
  Gradle test task already does.

meanbean's `BeanTester` fills properties with random values from an unseeded generator. No mutant
depends on a `testWithBeanTester` test for its kill: with all of them disabled, PIT reports the
same status for every mutant.

## Survivors that stay

PIT has no way to mark a mutant as equivalent, and excluding classes or methods would hide more
than the survivor. The survivors stay in the run and count against the score; this table is the
argument for each of them. Line numbers refer to the sources at the commit that added this page.

**B** is an equivalent mutant: no caller can observe a difference. **C** is a specification gap:
the mutant changes behaviour that nobody has decided, so no test can say which side is right.

| # | Class | Method | Line | Mutator | Kind | Why it survives |
|---:|---|---|---:|---|:---:|---|
| 1 | `BaseTreeNodeTransformer` | `assertNoCycles` | 252 | removed conditional, equality to false | B | Skipping a key that is already resolved is a shortcut: the walk below stops at once at a resolved node, so the result is the same. Holds for maps keyed by the node ids, as `transform` requires. |
| 2 | `BaseTreeNodeTransformer` | `assertNoCycles` | 258 | removed conditional, equality to true | B | Without the stop at resolved nodes the walk goes on to the root through nodes that were already checked; on a valid tree that adds no repeated id, and a cycle is found either way. Only the cost changes. |
| 3 | `BaseTreeNodeTransformer` | `getRoot` | 288 | removed conditional, equality to false | B | The early return for an empty map is a shortcut: `transform` of an empty map is empty and the method returns null either way. |
| 4 | `BaseTreeNodeTransformer` | `getRoot` | 290 | null return value | B | The early return already returns null. |
| 5 | `ITreeNodeHandlerExtensions` | `getRoot` | 94 | removed conditional, equality to true | B | The `root != null` check never sees null: the loop stops at the first node without a parent (`isRoot()` is `getParent() == null`), so it never steps from there to null. |
| 6 | `ITreeNodeHandlerExtensions` | `move` | 328 | removed conditional, equality to true | B | A non-null new parent that reaches this line is a node, since a leaf returned `false` at the top of the method; `isNode()` is always true here. |
| 7 | `ITreeNodeHandlerExtensions` | `getChildIndex` | 656 | removed conditional, equality to true (`child != null`) | C | Differs only when the parent's children list holds a null element, which `addChild` never adds but a caller can put into the list it gets from `getChildren()`. Whether a null child then has an index is not decided. |
| 8 | `ITreeNodeHandlerExtensions` | `getChildIndex` | 656 | removed conditional, equality to true (`isNode()`) | C | Differs only for a node flagged as leaf that still holds children. `getChildAt` and `getChildIndex` treat its children as absent, `getChildCount` and `hasChildren` count them; which is right is not decided. |
| 9 | `ITreeNodeHandlerExtensions` | `addChildren` | 682 | removed conditional, equality to true | B | `addChild` checks `isNode()` itself, so adding to a leaf stays a no-op without the outer check. |
| 10 | `ITreeNodeHandlerExtensions` | `findAllByValue` (lambda) | 765 | removed conditional, equality to true | B | The visitor never gets null: `accept` throws on a null child instead of handing it to the visitor. |
| 11 | `ITreeNodeHandlerExtensions` | `contains` | 823 | removed conditional, equality to false | B | The traversal set never holds null, so looking null up answers `false` like the early return. |
| 12 | `ITreeNodeHandlerExtensions` | `height` | 977 | changed conditional boundary | B | `<` to `<=` assigns an equal height to the maximum, which leaves it as it was. |
| 13 | `SimpleTreeNodeHandlerExtensions` | `getRoot` | 118 | removed conditional, equality to true | B | As row 5. |
| 14 | `SimpleTreeNodeHandlerExtensions` | `addChildren` | 443 | removed conditional, equality to true | B | As row 9. |
| 15 | `SimpleTreeNodeHandlerExtensions` | `findAllByValue` (lambda) | 581 | removed conditional, equality to true | B | `accept` walks the `leftMostChild` / `rightSibling` links and stops at null, so the visitor never gets null. |
| 16 | `SimpleTreeNodeHandlerExtensions` | `contains` | 642 | removed conditional, equality to false | B | As row 11. |

14 are equivalent (B) and 2 are specification gaps (C).

Of the 33 mutants the first STRONGER run left undetected, 6 were already caught by existing tests
that PIT could not see (see above), 12 were missing assertions and have tests now, 14 are the
equivalent mutants in this table, and one led to a bug fix: `getChildIndex` set the parent of the
node it looked up (row 8 is the mutant on its `isNode()` guard; row 7 appeared with the fix, since
the null check no longer protects a `setParent` call). Two more mutants, on the `KEEP` branch of
`BaseMergeTreeNodesVisitor.visit`, counted as killed only through the shared fixture; they have
real tests now too, so 14 mutants in all are killed by new tests (A).

## Not a survivor, but not decided either

`MergeStrategy.OVERWRITE` says it overwrites the existing node with the new one. The children of a
`BaseTreeNode` are a set, so `BaseMergeTreeNodesVisitor` adding an equal node keeps the existing one
and only re-points the new node's parent; a node with the same id but another value is added next
to the existing one, under both strategies. The tests pin what `KEEP` documents (an existing node
is kept, the new one ignored) and leave `OVERWRITE` as it is until somebody decides what it should
do.
