# optimization
instances of FunctionG are evaluated by a genetic algorithm


instances of the EvaluationFunction interface are evaluated by
either hillClimbing or a version of hybridisedSimulatedAnnealing

all the test functions are multimodal and as such,some runs might be(or not) more
successful than the others

best results for hillClimbing(HC) and simulatedAnnealing(SA)
for the 30 dimensional versions of DeJong1,Schwefel,Rastrigin
and Michalewicz

![image](https://github.com/gabrieljiglau/funtion-optimization/assets/100293793/55f7de11-c684-4be7-9f7b-eb7653199424)

![image](https://github.com/gabrieljiglau/funtion-optimization/assets/100293793/ddc89d5e-add6-4e5e-8cab-0c9cd8e1bfdc)


best results for the genetic algorithm using a relatively small population
of 80 individuals with 200 generations
for the 30 dimensional versions of DeJong1,Schwefel,Rastrigin
and Michalewicz

![image](https://github.com/gabrieljiglau/funtion-optimization/assets/100293793/abe8e1d8-df52-4532-a856-337f1b153df7)

with their optima : DeJong1(0),Schwefel(-12569.487),Rastrigin(0)
and Michalewicz(-28.9) (30dimensional versions).

upon further testing, bigger population sizes and generations(in tests 200, 1050 respectively)
Schewfel's function was minimized to -12460 and Michalewicz -28, but in comparatively more time :
10 minutes vs 2/3 (data gathered for smaller populations with lesser generations).





