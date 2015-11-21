# VirtuaSimulator

VirtuaSimulator provides facilities for virtual embedded networks simulation and
assessment. It focuses primarily on network dependability attributes.

Setup:
* Java;
* Maven;

### Code Modules

1. Generator;
2. Graphicator;
3. Simulator;

#### Generator
Generate random input for simulation experiments. It may, for instance, generate
random substrate networks and virtual network requests for an environment of
network virtualization.

#### Graphicator
Create formatted files for properly data visualization. It takes simulation's
output and generates files that may serve as input for tools like LibreOffice.

#### Simulator
Simulate embedding of virtual network requests onto a substrate physical network.
It also disposes implementation for allocation approaches -- in other words, for
Virtual Network Mapping Problem solvers.

#### Tasks
Each module owns some tasks that serve as sample simulator utilization.

### Executing tasks
Run tasks with Maven exec plugin:
```bash
$ mvn clean compile exec:java -Dexec.mainClass=generator.tasks.CreateVirtuaVNMPs
```
In summary:

1. `clean` removes all previously compiled files -- if any;
2. `compile` compiles the source code;
3. `exec:java` invokes Java main from class passed through `-Dexec.mainClass`;

### Running the tests

```bash
$ mvn test
```
Running tests for a single test class:
```bash
$ mvn test -Dtest=GraphTest.java # run tests in GraphTest.java
```

PS.: test cases depend on a lot of stuff. All dependencies must be supplied or
tests won't pass.

### Contributing

* Fork;
* Create new branch;
* Fix some messy thing or implement a new awesome feature;
* Issue a pull request;
