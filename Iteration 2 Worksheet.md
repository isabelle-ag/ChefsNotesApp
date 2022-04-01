# Iteration 2 Worksheet
## Paying off technical debt

> Show **two** instances of your group paying off technical debt. For these two instances:

> - Explain how your are paying off the technical debt.
> - Show commits, links to lines in your commit where you paid off technical debt.
> - Classify the debt, and justify why you chose that classification with 1-3 sentences.

1) Reckless Inadvertant
    - The UI was designed to only fit the Nexus 7 screensize, and was breaking on any other device. We refactored the UI files to allow for other screen sizes to display the app correctly.
    - Links: [1](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/commit/a5a259b5da1c981db28ba4807b1435e5d61efb01) [2](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/commit/131c91ef8fdcfd18a887ee06ffbcef1d35ae634b) [3](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/commit/376c66cfca30479c35b041c599b776c538c80d9f) [4](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/commit/3a6b889d888a7855927fd9afb4d7998ef1cee1b9)

2) Prudent Deliberate
    - Last iteration, our recipe viewer had a hard coded recipe in it. We modified it to accept any recipe object and display it properly.
    - [Link](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/commit/7a30fc718a1c3cfbc66530fc0fc68b48f8cc969d)
    



## SOLID

> Find a SOLID violation in the project of group with group number **n-1 in the same section of the course as you (group 1 does group 16)**. Open an issue in their project with the violation, clearly explaining the SOLID violation - specifying the type, provide a link to that issue. Be sure your links in the issues are to **specific commits** (not to `main`, or `develop` as those will be changed).

Provide a link to the issue you created [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-8/recipe-app/-/issues/23).

## Retrospective

> Describe how the retrospective has changed the way you are doing your project. Is there evidence of the change in estimating/committing/peer review/timelines/testing? Provide those links and evidence here - or explain why there is not evidence.

We ensured all members had working software to work on the project with this time around. Previously, there was a disconnect between users and how their Android Studio and Git were working. All members are synced up now.

We had changed our workflow slightly when it comes to tests, previously we had wrote all our code and *then* wrote tests. This time tests were written as soon as code was written. 
- [Link](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/merge_requests/59)

The group as a whole changed the way we branch and update main. Previously if there was no open branch and nothing broke, a commit to main was acceptable. This time no changes were made directly on main, only in branches which later got merged into the main branch.
- [Link to Visual](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/network/main)

 ## Design patterns

> Show links to your project where you use a well-known design pattern. Which pattern is it? Provide links to the design pattern that you used.
> **Note**: Though Dependency Injection is a programming pattern, we would like to see a programming pattern other than Dependency Injections.

1) Singleton
    - Both our real and fake databases operate as singletons, all classes can access the database through its interface, and it is the same single database every time. Services.java provides the singleton functionality.
    - [Link](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/blob/4d3f66621a4f83b51e482a4bb31b898eb663b23b/app/src/main/java/comp3350/chefsnotes/application/Services.java)

2) Factory Object
    - Our two implementations of the database, the fake and the real one, operate through the same interface. When DBMS tools is instantiated, the calling class can decide which it would like to use. 
    - [Link to Interface](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/blob/4d3f66621a4f83b51e482a4bb31b898eb663b23b/app/src/main/java/comp3350/chefsnotes/persistence/DBMSTools.java), [Fake Database](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/blob/main/app/src/main/java/comp3350/chefsnotes/persistence/FakeDBMS.java), [Real Database](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/blob/main/app/src/main/java/comp3350/chefsnotes/persistence/RecipePersistence.java)

3) Null Object
   - Did a quick [refactor]() to use a null object for `amt` in `Quantity` instead of nulling it and later nullchecking it in `Ingredient`. 
## Iteration 1 Feedback fixes
> Provide a link to an issue opened by the grader.
> Explain what the issue was, and why it was flagged. Explain what you did to refactor or fix your code to address the issue. Provide links to the commits where you fixed the issue.

We didn't actually get any Gitlab issues from the graders, but we did get some feedback on d2l. 
!["issue"](https://media.discordapp.net/attachments/936312495960907820/959531132637028403/unknown.png?width=900&height=185)
To resolve this, we did a pass where we took out the NonNull stuff and cleaned up these constructors in [this commit](https://code.cs.umanitoba.ca/winter-2022-a02/group-9/chefsnotes/-/commit/c2add5d439477d62dde3b0de1c0543b24e9e5bf5?merge_request_iid=53)
