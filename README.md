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


best results for the genetic algorithm using a bigger population consisting of
of 100 individuals with 2000 generations for the for all three versions (5D, 10D, 30D)
for DeJong1, Schwefel, Rastrigin and Michalewicz


![ga](https://github.com/gabrieljiglau/numerical-optimization/assets/100293793/1535bc16-fc80-4d2c-8493-7c47d6ebe97e)

with their optima : DeJong1(0),Schwefel(-12569.487),Rastrigin(0)
and Michalewicz(-28.9) (30 dimensional versions).


by storing the evaluated fitness as a parameter inside the individual, the performance is superb


TO-DO: add simulated annealing results
here are the results : -11787.56(Schwefel), -27.76(Michalewicz), 24.54 Rastrigin (deJong is not named
because the "standard" algorithm was finding it's minimum of 0 relatively easy).





