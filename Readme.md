This was Syft coding challenge by Augustine
Libraries and architectural patterns used:
    •   Retrofit for api calls.
    •   RxJava for Background tasks, multithreading, and memory management.
    •   Viewmodel as architectural component for MVVM.
    •   LiveData for use with MVVM.
    •   JUnit for unit testing.
    •	Dependency Injection using Dagger

The task accomplished include:
    •	Display list of search results.
        o	Display search results in a RecyclerView.
    •	A searchbox to type a query.
        o	This would display results as the user types in text.
        o	Most popular repositories will appear first in the search results.
    •	Statistics about the current search.
        o	Number of Repositories found and the elapsed time.
    •	A facet list for filtering results.
        o	Filter by pragramming language.

Programming Language used: Kotlin

Extra tasks accomplished:
    •	Testing with JUnit
    •	Use of Architectural MVVM Pattern

Further improvements:
        •	infinite scrolling mechanism

Api
https://api.github.com/search/repositories?q=org:github+language:anyprograminglanguage&sort=stars&order=desc