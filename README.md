# Detekt custom rule 

Uses detekt, provides:

* a custom processor which provides a new metric
* a custom report format (CSV)

Also demonstrates usage of the custom logic from the gradle plugin, by (cough) adding itself as a dependency. 

To use that, first build and `./gradlew publishToMavenLocal`.  You should see the html report appear in `build/reports` 
as a side effect of this.

Having done that, uncomment the 2 build file sections that pick it up (see 2 TODO comments in build file) and do 
`./gradlew build` or something.

You will see a rather useless new metric in the HTML report, and a CSV report.

This is real-life bootstrapping, kids, not _just_ a hack.  Just about.

Feel free to split this contraption into two modules: One to build the custom analysis logic and one to use it. For now 
this repo mainly helps with pointing out the required nuts and bolts. 