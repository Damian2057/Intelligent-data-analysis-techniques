# Comparison of PSO and DE algorithms

## Parameters
- "stopCondition": ['accuracy', 'iteration']
- "number": [0.0001, 1000]
- "xRange": [[-100,100], [-2.15, 2.15]]
- "dimension": [20, 50]
- startTimes: [5, 10, 100]

## DifferentialEvolution
- "reproductionType": ['best', 'random']
- "populationSize": [20, 50, 600]
- "amplificationFactor": [0.5, 0.7]
- "crossOver": [["crossoverType": "random", "CR": 0.5], ["crossoverType": "exponential", "numberOfCopies": 2]]