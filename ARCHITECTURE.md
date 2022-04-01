  ChefsNotes Architecture
```
┌──────────────────────────────────────────────────────────────┬┬────────────────────────────┬────────────────────────────┐
│                                                              ││                            │                            │
│  ┌─► Messages ◄─────────────── MainActivity     FullListView ││  Presentation Layer        │                            │
│  │                                                           ││  (presentation package)    │  DSOs                      │
│  │                                   │                       ││                            │  (objects package)         │
│  ├─► NoticeDialogFragment            │                       ││                            │                            │
│  │                                   │                       ││                            ├────────────────────────────┤
│  │    ┌───────────────────┬──────────┴────────┐              ││                            │                            │
│  │    │                   │                   │              ││                            │  Decimal                   │
│  │    ▼                   ▼                   ▼              ││                            │                            │
│                                                              ││                            │  Direction                 │
│ EditRecipe ◄─────── ViewRecipe ◄─────── RecipeBrowser        ││                            │                            │
│                                                              ││                            │  Fraction                  │
└──────────────────────────────────────────────────────────────┴┴────────────────────────────┤                            │
                                                                                             │  Ingredient                │
     │  │                   │                  │  │                                          │                            │
     │  │                   │                  │  │                                          │  Quantity                  │
     │  └───────────────────┼──────────────────┘  │                                          │                            │
     │                      │                     │                                          │  QuantityNum               │
     ▼                      ▼                     ▼                                          │                            │
                                                                                             │  Recipe                    │
┌──────────────────────────────────────────────────────────────┬┬────────────────────────────┤                            │
│                                                              ││                            │  RecipeExistenceException  │
│ IRecipeManager      IRecipeFetcher      ITagHandler          ││  Business Layer            │                            │
│                                                              ││  (business package)        │  SampleRecipe              │
│     │  ▲                │  ▲               │  ▲              ││                            │                            │
│     │  │                │  │               │  │              ││                            │  TagExistenceException     │
│     ▼  │                ▼  │               ▼  │              ││                            │                            │
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
```
