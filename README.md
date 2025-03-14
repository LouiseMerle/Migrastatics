# Migrastatics

## Description
The following repository contains the source code for the Migrastatics simulation software.

## Installation
There are two ways to run the simulation software.

The first option is to download the .jar file and run *java -jar BasicCancerModel.jar*.

The second option is to run the application directly from the source code.

In order to run the application from the source code follow these steps:
1. Clone this repository
2. Open the project in Eclipse
3. Run the project as a Java Application

## Usage
To run the simulation start the Java Application and configure the desired ruleset.

Through the GUI, fill in the following Parameters:

After configuring the parameters, hit Start&Save for the simulation to run.

### Initial Settings
| Parameter | Description | Default Value |
|---------|-------------|---------------|
|Initial Population | the initial population size, these are the number of cells the simulation starts with | 2000 |
| Initial Distribution (sensitive, resistant) | The fraction of cells that are sensitive to treatment or resistant | 3/4 & 1/4 |
| Initial Tumor Size | The initial size of the tumor at the start of the simulation, defined as a radius | 10 |

### Cancer Model (without treatment) parameters
#### Payoff Matrix:
The Payoff Matrix is defined as a two row, two column matrix, where the first row and column represent the treatment sensitive cells (S) and the second row and column the treatment resistant cells (R). 
    The following interaction matrices were tested:
    
Neither competition nor cooperation
|   | **S**   | **R**   |
|---|-----|-----|
| **S** | 0.3 | 0.3 |
| **R** | 0.3 | 0.3 |

Competition best when surrounded by same cell type
|       | **S**   | **R**   |
|-------|-----|-----|
| **S** | 0.5 | 0.3 |
| **R** | 0.3 | 0.5 |

Competition sensitive cells proliferate faster
|       | **S**   | **R**   |
|-------|-----|-----|
| **S** | 0.7 | 0.2 |
| **R** | 0.2 | 0.3 |

Competition resistant cells proliferate faster
|       | **S**   | **R**   |
|-------|-----|-----|
| **S** | 0.3 | 0.2 |
| **R** | 0.2 | 0.7 |

Cooperation sensitive cells proliferate faster
|       | **S**   | **R**   |
|-------|-----|-----|
| **S** | 0.2 | 0.8 |
| **R** | 0.2 | 0.2 |

Cooperation resistant cells proliferate faster
|       | **S**   | **R**   |
|-------|-----|-----|
| **S** | 0.2 | 0.2 |
| **R** | 0.8 | 0.2 |


#### Interaction parameters:
| Parameter | Description | Default Value |
|---------|-------------|---------------|
| Interaction Radii | How far off from the radius of a cell interactions can take place | 1 |
| Offspring Radii | How far off a cell can place its offspring | 1 |
| Carrying Capacity | How many neighbours one cell can have | 6 |
| Death Probability | Probability of cell death for every iteration (percentage) | 0.20 |

### Migration model (Without treatment) parameters
| Parameter | Description | Default Value |
|---------|-------------|---------------|
| Number of possible migration sites | Number of sites the primary tumor can metastasise to | 8 |
| Fraction of invasice cells within tumor | Fraction of cells that can metastatise | 0.1 |
| Probability that invasice cells migrate | Probability for one cell to migrate to a metastasise | 0.1 |
| Survival chance at destination site | Probability for one cell to survive at new location | 0.1 |

### MTD Parameters
| Parameter | Description | Default Value |
|---------|-------------|---------------|
| Treatment initiated at step | At what step the cancer treatment should start in the simulation (-1 if no treatment) | -1 |
| Killing fraction of sensitive cells | Percentage of sensitive cells that are killed at every step by the cancer treatment | 0.9 |

### Migrastatic Parameters
| Parameter | Description | Default Value |
|---------|-------------|---------------|
| Treatment initiated at step | At what step the migrastatics treatment should start in the simulation (-1 if no treatment) | -1 |
| Probability to migrate after treatment | The probability to migrate once migrastatics treatment is on | 0.01 |

### Adaptive Treatment Parameter (Dose = MTD if treatment is on)
| Parameter | Description | Default Value |
|---------|-------------|---------------|
| Aaptive treatment initiated at step | At what step the adaptive treatment should be started in the simulation (-1 if no treatment) | -1 |
| Start treatment when tumor size increased again to that percentage | At what tumor size to start the treatment as a fraction of the initial tumor size | 1 |
| Stop treatment when tumor size decreased again to that percentage | At what tumor size to stop the treatment as a fraction of the initial tumor size | 0.5 |

### Simulation settings
| Parameter | Description | Default Value |
|---------|-------------|---------------|
| Number of steps | The number of steps each individual simulation should take | 100 |
| Plot after steps | The amount of steps after which the output should be generated | 1 |
| Number simulation runs | How many times a simulation can be run chronologically | 1 |
| File initial condition | The file that contains the configuration for the initial population. (Leave empty for random initial condition) | |
| File name | Name of file given for storing the output of the simulation | Test |
| Save directory | Location on file system to store output of the simulation | |
