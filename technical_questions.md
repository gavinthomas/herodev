#Technical questions

1. How long did you spend on the coding test

- I stuck to the 4 hour limit initially however I spent an extra 30 mins tidying up some code and commenting

2. What would you add to your solution if you had more time?

- Handle orientation changes, I could probably achieve this relatively easy by using a fragment with setRetainInstance set to true.

- I would have liked to have pagination, the API supports it however it is not robust enough to allow pagination with sorting.

- I wanted to add a SearchView to let the user search for anything rather than restricting the results to cheese but due pretty much every parameter returned by the API being optional it doesn't really allow for sorting past alphabetical order.

- There are unit tests covering the presenter logic in ProductPresenterTest however given more time I would perhaps create UI tests for the implementation of the custom FabSortMenuView

- I'm not sure whether or not you guys use RxJava or kotlin coroutines but I made the decision to go with coroutines. I've had extensive experience with RxJava but I used this opportunity to have a go at implementing kotlin coroutines as it's the new "cool" thing to do and it's fairly easy compared to RxJava's initial learning curve. Kotlin has functional programming at it's core so bundling that with the asynchronous ability of coroutines completely diminishes the point of using RxJava. One issue I have with coroutines is I'm not 100% on the best approach for error handling, the current implementation handles exceptions with a try catch which is suitable for this project but on a larger project a better solution would be required.

- Add proper JavaDoc