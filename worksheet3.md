What technical debt has been cleaned up
========================================

[commit](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/commit/4334f4966370d85b49c6de87c79f6a1976a70dcf)

We cleaned up inadvertent prudent technical debt in recipe browser. We didn't know how to use non-String arrays in the array adapter for the list view of recipes. After some preliminary research we were unable to determine whether it was possible to do easily, and decided to focus our time on other features and add duplicate code to return the information as strings. In this iteration, we were able to dedicate more time to research and discovered how to use other objects in the array adapter. With this change we were able to remove the duplicate code and streamline classes.

What technical debt did you leave?
==================================

EditRecipe bloated out of control, and should have been refactored out into creation and editing. However, we barely have time to get any actual features done without spending time reworking stuff. I'd classify it as reckless-intentional debt because we knew that it probably should be different classes but we just needed to make it work

Discuss a Feature or User Story that was cut/re-prioritized
============================================

Feature cut: Meals
We cut the option to create Meals, which were intended to be a collection of Recipes in a sort of "folder", indicating serving sizes and multiples of Recipes to cook. We decided to cut this feature near the start of iteration 3. We realized that a lot of work would need to go into creating this (2-3 more user views, new segments to persistence, logic for compiling and displaying total ingredient list, and much more. In the end we decided to prioritize the expansion and polishing of existing features, and adding features which were closely tied to these existing features. [link](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/issues/6)


Acceptance test/end-to-end
==========================

In [this test](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/blob/main/app/src/androidTest/java/comp3350/chefsnotes/RecipeCreationTest.java), the user's ability to create new recipes is tested. Specifically, the user story which specifies that a user should have the ability to create a new recipe with instructions that can be given a time estimate that will be displayed in the recipe viewer. In order to do so, the automated test begins on the start menu and selects the "create recipe" button. It enters a title for the recipe, and fills in the title field, time estimate field, and instruction field for a direction. It then clicks the save button and ensures that the direction and time estimate are displayed correctly. In order to ensure that the test is not flakey, very specific details about the contents and position of each view is used when referring to them, so the test can be certain of which one it is supposed to use.

Acceptance test, untestable
===============

In order to format the recipe view in a pleasing way, the strings a user inputs are altered. This made testing what the user sees challenging, as it had to be tested against a reformatted version of the input. Additionally, many of the views were created programmatically, and as a result did not have unique ids. This meant having espresso find these views was challenging. Doing so involved describing the view using other views in its hierarchy, which is often somewhat abstracted in Android Studio. Finally, our "Photos" feature required using an API which takes the user outside of the app, to a file explorer. Unfortunately, Espresso can only issue commands while the app is open, and thus had no control when the file explorer opens. There is also no way to compare the photo that appears in the view to an expected photo, as the data type is not comparable. Thus no acceptance tests could be developed for the "Photos" feature.

Velocity/teamwork
=================
Project Velocity
Our estimates definitely got better through the course. Initially we had no idea and very little experience with many of the tools we were expected to use. This made us greatly underestimate the time required for various steps, especially for the first iteration.

Iteration 1: Est 30hrs, Spent: 100hrs40m
Iteration 2: Est 48hrs, Spent: 56hrs
Iteration 3: Est ??hrs, Spent: ???hrs
