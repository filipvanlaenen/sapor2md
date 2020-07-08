# Convertor of Sapor Results into Markdown Pages

## Getting Started

First of all, you need to obtain a copy of the source code and compile it into
an executable. Run the following commands to do this:

```
git clone git@github.com:filipvanlaenen/sapor2md.git
cd sapor2md
mvn clean compile assembly:single
```

If everything works well, you'll a JAR file in the `target` directory with all
dependencies included.

## Adjusted Medians

Let's try to calculate a set of adjusted medians for a seat projection. Move
to the `target` directory and save the following file there using the file name
`test-polychotomy-seats-probabilities.psv`:

```
Choice      | 0    | 1    | 2
Red Party   | 0.4  | 0.35 | 0.25
Blue Party  | 0    | 0.65 | 0.35
Green Party | 0.75 | 0.25
```

To do the calculation, run the following command:

```
java -jar sapor2md-1.0-SNAPSHOT-jar-with-dependencies.jar AdjustedMedians test-polychotomy-seats-probabilities.psv 3
```

The result should be as follows:

```
Choice | CI95LB | Median | Adjusted Median
Red Party | 0 | 1 | 2
Blue Party | 1 | 1 | 1
Green Party | 0 | 0 | 0
```

The output should be interpreted as follows:
* The Red Party has 0 seats as the lower bound for the 95% confidence interval,
  1 seat as the median, but 2 seats as the adjusted median;
* The Blue Party has 1 seat as both the lower bound for the 95% confidence
  interval, the median and the adjusted median;
* The Green Party has 0 seats for all three.

The Red and the Blue Party both have 1 seat as the median of their probability
mass functions. However, since we requested a parliament size of 3, the
program tries to adjust the medians in such a way the probability of the result
if maximized. In this case, there are three possible results. Adding a seat to
the Red Party has a total probability of 0.25 × 0.65 × 0.75 = 0.121875. Adding
a seat to the Blue Party as a total probability of 0.35 × 0.35 × 0.75 =
0.091875. Finally, adding a seat to the Green Party has a total probability of
0.35 × 0.65 × 0.25 = 0.56875. The first solution is clearly the best, hence the
median of the Red Party is adjusted from 1 seat to 2 seats.

The table below shows how the results change when the size of the parliament
is changed:

| Party       | 2 Seats | 3 Seats | 4 Seats | 5 Seats |
|:------------|:-------:|:-------:|:-------:|:-------:|
| Red Party   | 1 seat  | 2 seats | 2 seats | 2 seats |
| Blue Party  | 1 seat  | 1 seat  | 2 seats | 2 seats |
| Green Party | 0 seats | 0 seats | 0 seats | 1 seat  |
