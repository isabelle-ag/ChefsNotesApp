Iteration 1 Worksheet
=====================

Adding a feature
-----------------

Tell the story of how one of your features was added to the project.
Provide links to the
feature, user stories, and merge requests (if used), associated tests, and merge commit
that was used complete the feature.

Use one or two paragraphs which can have point-form within them.

The story of [Recipe Creation](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/1):

Long story short, we had no idea what we were doing at the start of this, and more-or-less floundered trying to get our ducks in a row. There were a number of merges of backend stuff like the [DBMS interface](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/7), [DSOs](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/2), and even, well, realizing we had to [initialize](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/1) the android project. After all that was done, we were able to ~~horribly procrastinate~~ get working on the presentation aspects, though it would take a [number](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/9) [of](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/11) [merges](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/29) for it to be done, including needing to make some [business layer code](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/21) and [associated tests](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/21/diffs?commit_id=7b8de7044f48e15d8585fe03cdeb437f8a010c68).

Exceptional code
----------------

Provide a [link to a test of exceptional code](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/blob/7b8de7044f48e15d8585fe03cdeb437f8a010c68/app/src/test/java/comp3350/chefsnotes/business/IRecipeManagerTest.java#L66). In a few sentences,
provide an explanation of why the exception is handled or thrown
in the code you are testing.

Currently, our application works on an assumption that all recipe names in the db are unique, so this recipe-in-db-renaming code both A: makes sure that the recipe you're asking to rename is in the db. and B: makes sure that you aren't trying to rename it to something else. If either is the case, it throws the RecipeExistenceException so that the presentation layer can tell the user what they did wrong

Branching
----------

Provide a [link](branching.md) to where you describe your branching strategy.

Provide [screen shot](branch.png) of a feature being added using your branching strategy
successfully. (vscode git graph extension was used)

SOLID
-----

Find a SOLID violation in the project of group `(n%16)+1` (group 16 does group 1).
Open an issue in their project with the violation,
clearly explaining the SOLID violation - specifying the type, provide a link to that issue. Be sure
your links in the issues are to **specific commits** (not to `main`, or `develop` as those will be changed).

Provide a link to the issue you created [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-10/irecipe/-/issues/44).

Agile Planning
--------------

Write a paragraph about any plans that were changed. Did you
'push' any features to iteration 2? Did you change the description
of any Features or User Stories? Have links to any changed or pushed Features
or User Stories.

Oh yeah: plans changed a lot. We ended up having to push back half the features we were intending to implement for this iteration. We were hoping to get recipe creation, recipe viewing, a browser, and an editor all done.... but we bit off more than we could chew.
So we had to push back the [recipe browser](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/4) and [recipe editor](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/3) features (and [associated](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/18) [user](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/16) [stories](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/17)) to [iteration 2](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/milestones/2#tab-issues), as well as a [copy recipe](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/19) user story.
Some feature/user story descriptions were changed during the iteration, but they were mostly for minor clarifications as opposed to sweeping design changes.