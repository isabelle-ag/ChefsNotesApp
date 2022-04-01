  ChefsNotes Architecture

┌──────────────────────────────────────────────────────────────┬┬────────────────────────────┬────────────────────────────┐
│                                                              ││                            │                            │
│ MainActivity ─────► Messages            FullListView         ││  Presentation Layer        │  DSOs                      │
│                                                              ││  (presentation package)    │  (objects package)         │
│      │                                                       ││                            │                            │
│      ├────────────────────────────────────────┐              ││                            ├────────────────────────────┤
│      │                                        │              ││                            │                            │
│      ▼                                        ▼              ││                            │  Decimal                   │
│                                                              ││                            │                            │
│ EditRecipe ◄─────── ViewRecipe ◄─────── RecipeBrowser        ││                            │  Direction                 │
│                                                              ││                            │                            │
└──────────────────────────────────────────────────────────────┴┴────────────────────────────┤  Fraction                  │
                                                                                             │                            │
     │  │                   │                  │  │                                          │  Ingredient                │
     │  │                   │                  │  │                                          │                            │
     │  └───────────────────┼──────────────────┘  │                                          │  Quantity                  │
     │                      │                     │                                          │                            │
     ▼                      ▼                     ▼                                          │  QuantityNum               │
                                                                                             │                            │
┌──────────────────────────────────────────────────────────────┬┬────────────────────────────┤  Recipe                    │
│                                                              ││                            │                            │
│ IRecipeManager      IRecipeFetcher      ITagHandler          ││  Business Layer            │  RecipeExistenceException  │
│                                                              ││  (business package)        │                            │
│     │  ▲                │  ▲               │  ▲              ││                            │  SampleRecipe              │
│     │  │                │  │               │  │              ││                            │                            │
│     ▼  │                ▼  │               ▼  │              ││                            │  TagExistenceException     │
│                                                              ││                            │                            │
│ RecipeManager       RecipeFetcher       TagHandler           ││                            │                            │
│                                                              ││                            │                            │
└──────────────────────────────────────────────────────────────┴┴────────────────────────────┤                            │
                                                                                             │                            │
      │                   │                   │                                              │                            │
      │                   │                   │                                              │                            │
      ├───────────────────┘                   │                                              │                            │
      │                                       │                                              │                            │
      │                                       │                                              │                            │
      ▼                                       ▼                                              │                            │
                                                                                             │                            │
┌──────────────────────────────────────────────────────────────┬┬────────────────────────────┤                            │
│                                                              ││                            │                            │
│ DBMSTools                              TagDBMSTools          ││  Persistence Layer         │                            │
│                                                              ││  (persistence package)     │                            │
│     ▲                                       ▲                ││                            │                            │
│     │                                       │                ││                            │                            │
│    ┌┴──────────────┐                 ┌──────┴────┐           ││                            │                            │
│    ▼               ▼                 ▼           ▼           ││                            │                            │
│                                                              ││                            │                            │
│ FakeDBMS   RecipePersistence     FakeTagDB  TagDBMSTools     ││                            │                            │
│                                                              ││                            │                            │
└──────────────────────────────────────────────────────────────┴┴────────────────────────────┴────────────────────────────┘