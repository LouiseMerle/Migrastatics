package Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import UI.Save;


/**
 * The class Controller plays the simulation. The class contains all the information like Rulesets, environment and population.
 * All steps are being updated from this class
 */
public class Controller {

	private RuleSet rs; //the rules of the game are stored here (payoff matrix, death probability etc.)
	private PopulationStatus ps; ////Every property of the whole population is stored (which individuals and their distribution)
	private Save s; //save object to story fields and important parameters

	private long startingTime; 
	private long endingTime;


	//public int interacting = 0;
	//private int ATpopulationSizeReference = 100000000;
	private boolean treatmentCancerOn = false;
	private boolean ATon = false;
	private double killingSensitive = 0;
	
	/**
	 * <p>
	 * The Run function is the starting point for the simulation. It uses all the available parameters to start the simulation. 
	 * It triggers a loop where all steps are executed and stores the results in the provided output location (parameter savePath).
	 * </p>
	 * @param filePath The file that contains the configuration for the initial population.
	 * @param initPopulationSize the initial population size provided through the GUI, these are the number of cells the simulation starts with.
	 * @param initDistribution The initial distribution of cells that are sensitive or resistant.
	 * @param initPlaceInds ? Place individuals?
	 * @param initTumorsize The initial size of the tumor at the start of the simulation, defined as a radius.
	 * @param numberTypes The number of cell types that are supported in the simulation. 
	 * @param payoffMatrix The interaction between the different cell types.
	 * @param interactionRadius How far off from the radius of a cell interactions can take place.
	 * @param offspringRadius How far off a cell can place its offspring.
	 * @param deathProbability Probability of cell death for every iteration
	 * @param carryingCapacity How many neighbours one cell can have.
	 * @param numberMigrationSites Number of sites the primary tumor can metastasise to.
	 * @param fractionInvasiveCells Fraction of cells that can metastatise.
	 * @param probToMigrate Probability for one cell to migrate to a metastasise.
	 * @param survivalDestination Probability for one cell to survive at new location
	 * @param savePath Location on file system to store output of the simulation. 
	 * @param fileName Name of file given for storing the output of the simulation
	 * @param simulationRuns How many times a simulation can be run chronologically
	 * @param gap The configuration after the amount of steps that the output should be generated.
	 * @param steps The number of steps each individual simulation should take.
	 * @param cancerTreatmentStep At what step the cancer treatment should start in the simulation.
	 * @param killingSensitive Percentage of sensitive cells that are killed at every step by the cancer treatment.
	 * @param migrastaticsStep At what step the migrastatics treatment should start in the simulation
	 * @param probToMigrateDuringTreatment The probability to migrate once migrastatics treatment is on.
	 * @param ATadaptiveStep At what step the adaptive treatment should be started.
	 * @param ATstartTreatment At what tumor size to start the treatment as a fraction of the initial tumor size
	 * @param ATstopTreatment At what tumor size to stop the treatment as a fraction of the initial tumor size.
	 * @throws IOException Exception thrown for any error.
	 */
	public void run(
			String filePath, 
			int initPopulationSize, 
			double [] initDistribution, 
			int initPlaceInds, 
			int initTumorsize,
			int numberTypes,  
			double[][]payoffMatrix,  
			double interactionRadius,  
			double offspringRadius,
			double deathProbability, 
			double carryingCapacity, 
			int numberMigrationSites, 
			double fractionInvasiveCells, 
			double probToMigrate, 
			double survivalDestination,
			String savePath, String fileName, 
			int simulationRuns, 
			int gap, 
			int steps, 
			int cancerTreatmentStep, 
			double killingSensitive, 
			int migrastaticsStep, 
			double probToMigrateDuringTreatment, 
			int ATadaptiveStep, 
			double ATstartTreatment, 
			double ATstopTreatment)
					throws IOException{
		
		for (int run = 0; run < simulationRuns; run++) {
			
			//initialization
			this.rs = new RuleSet(numberTypes,payoffMatrix, deathProbability,  
					interactionRadius, offspringRadius, 
					carryingCapacity, numberMigrationSites, probToMigrate, survivalDestination, fractionInvasiveCells, initTumorsize, cancerTreatmentStep, 
					killingSensitive, migrastaticsStep, probToMigrateDuringTreatment, ATadaptiveStep, ATstartTreatment, ATstopTreatment);
			this.ps = new PopulationStatus(initPopulationSize, filePath, initDistribution, rs, initPlaceInds, initTumorsize, fractionInvasiveCells);
			this.s = new Save(savePath, fileName, false, run);
			this.s.saveSettings(rs, ps);
			System.out.println("Timer on");
			this.startingTime = System.currentTimeMillis();
	
			System.out.println("Computation started");
	
			//**************
			// Simulation
			//**************
			
			for (int i = 0; i < steps; i++){
	
				System.out.println();
	
				System.out.println("Step no. " + i + " Needed Time: " + ((System.currentTimeMillis() - startingTime)/1000) + " sec");
	
				//
				// Save current individual list at each gap'th step
				//
				if (i % gap == 0){
					ArrayList<Individual>[] cellsAllSites = ps.getIndividuals();
					for (int site = 0; site < cellsAllSites.length; site++) {
						ArrayList<Individual> indsCurrentSite = cellsAllSites[site];
						for (int j = 0; j < indsCurrentSite.size(); j++){
							s.save(new SaveObject(indsCurrentSite.get(j),i));
						}
						this.s.saveStepNo(i);
					}
					if (i != steps - 1){
						s.save(new SaveObject((short)0,(short)0,(short)0,0,0));
					}
				}
				s.saveDistri(this.ps.getDistribution(), this.ps.getCurrentPopulationSize());
				//
				// performStep: here the interactions take place
				//
				performStep(i, steps);
			}
			//
			// finish files (closing them)
			//
			try {
				s.finish();
			} catch (IOException e) {
				throw e;
			}			
			this.endingTime = System.currentTimeMillis();
			System.out.println("Needed time: " + ((endingTime - startingTime)/1000) + " sec");
		}
	}



	//computation (steps)
	/**
	 * <p>
	 * The performStep function will execute an individual step of the simulation
	 * </p>
	 * @param currentStep The index of the current step being executed.
	 * @param steps The total number of steps to execute in this simulation.
	 * @throws IOException Exceptiont thrown for any error.
	 */
	@SuppressWarnings("unchecked")
	public void performStep(int currentStep, int steps) throws IOException{
		//Counter for statistics
		int counterAboveCarryingCapacity = 0;
		int counterMigrations = 0;
		int counterOffspring = 0;
		
		int numberSites = rs.getNumberMigrationSites()+1; //including solid tumor
		
		//**********************
		//Death process & Migration
		//**********************

		//if MTD treatment is on
		if (currentStep >= this.rs.getCancerTreatmentStep() && this.rs.getCancerTreatmentStep() != -1) {
			treatmentCancerOn = true;
			killingSensitive = this.rs.getKillingSensitive();
		} 
		//if adaptive treatment is on
		if (this.rs.getATadaptiveStep() != -1) {
			//check if it is the first step of the first cycle to be able to store the number of cells as a reference
			if (currentStep == this.rs.getATadaptiveStep()) {
				//ATpopulationSizeReference = this.ps.getCurrentPopulationSize();
				treatmentCancerOn = true;
				ATon = true;
				killingSensitive = this.rs.getKillingSensitive();
				System.out.println("Adaptive Treatment started at step " + currentStep);
			} else {
				if (treatmentCancerOn) {
					// check whether tumor size <= 50%
					if (this.ps.getCurrentPopulationSize() <= this.ps.getInitialPopulationSize() *this.rs.getATstopTreatment()) {
						treatmentCancerOn = false;
						ATon = false;
						System.out.println("Adaptive Treatment stopped at step " + currentStep);
					}
				} else {
					// check whether tumor size >= 100% of reference 
					if (this.ps.getCurrentPopulationSize() >= this.ps.getInitialPopulationSize() * this.rs.getATstartTreatment()) {
						treatmentCancerOn = true;
						ATon = true;
						killingSensitive = this.rs.getKillingSensitive();
						//this.ATpopulationSizeReference = this.ps.getCurrentPopulationSize();
						this.rs.setATadaptiveStep(currentStep);
						System.out.println("Again Adaptive Treatment started at step " + currentStep);
					}
				}
			}
		}
		
		double deathProb = this.rs.getDeathProbability();
		double probToMigrate = this.rs.getProbToMigrate();
		//if migrastatic treatment is on
		if (currentStep >= this.rs.getMigrastaticStep() && this.rs.getMigrastaticStep() != -1) {
			probToMigrate = this.rs.getProbToMigrateDuringTreatment();
		}
		double survivalAtDestination = this.rs.getSurvivalDestination();
		//we have an array of arraylists (array because we have 8 sites and tumor and arraylist because we have varying number of inds at these locations)
		ArrayList<Individual>[] survivingIndsAllSites = (ArrayList<Individual>[]) new ArrayList[numberSites];
		ArrayList<Individual>[] newFieldAllSites = (ArrayList<Individual>[]) new ArrayList[numberSites];
		
		for (int site = 0; site < numberSites;site++) {
			newFieldAllSites[site] = new ArrayList<Individual>();
			survivingIndsAllSites[site] = new ArrayList<Individual>();
		}
		
		for (int site = 0; site < numberSites; site++) {
			ArrayList<Individual> cellsCurrentSite = ps.getIndividuals(site);
			System.out.println("site " + site + " size " + cellsCurrentSite.size());
			for (int i = 0; i < cellsCurrentSite.size(); i++) {
				//natural death probability
				if (RandomVariable.getDouble() > deathProb) {
					Individual ind = cellsCurrentSite.get(i);
					//individual survives if no cancer treatment or resistant type or sensitive, but coins says it survives
					if (!treatmentCancerOn || ind.getType() == 2 || RandomVariable.getDouble() > killingSensitive) { 
						if (ind.getInvasive()) {
							if (RandomVariable.getDouble() < probToMigrate) {
								if (RandomVariable.getDouble() < survivalAtDestination) {
									int newSite = RandomVariable.getInt(numberSites-1) + 1;
									newFieldAllSites[newSite].add(ind);

									// new position of migrating cell
									double[] positionNewSite = this.rs.getSiteLocation(newSite);
									double r2 = this.rs.getInteractionRadius() * Math.sqrt(RandomVariable.getDouble());
									double theta2 = 2 * Math.PI * RandomVariable.getDouble();
									ind.setPosition(positionNewSite[0] + r2 * Math.cos(theta2),
											positionNewSite[1] + r2 * Math.sin(theta2));

									counterMigrations += 1;
								}
							} else {
								survivingIndsAllSites[site].add(ind);
							}
						} else {
							survivingIndsAllSites[site].add(ind);
						}
					}
				}
			}
		}
		
		
		//**************
		// Interaction
		//**************
		
		// Individuals need to interact in random order:
		for (int i = 0; i < numberSites; i++) {
			Collections.shuffle(survivingIndsAllSites[i]);
		}
		
		// surviving inds will not be touched anymore. From this list, we get the adults that interact
		// newField contains the newborns only -> will be used for density check together with surviving inds list
		
		double interactionRadius = rs.getInteractionRadius();
		
		for (int site = 0; site < numberSites; site++) {
			ArrayList<Individual> survivingIndsCurrentSite = survivingIndsAllSites[site];
			for (Individual ind : survivingIndsCurrentSite) {
				ArrayList<Individual> possibleNeighbors = new ArrayList<Individual>();
				int density = 0;
				double xInd = ind.getX();
				double yInd = ind.getY();

				for (Individual possibleNeighbor : survivingIndsCurrentSite) {
					double xNei = possibleNeighbor.getX();
					double yNei = possibleNeighbor.getY();
					double distance = Math.sqrt((xInd - xNei) * (xInd - xNei) + (yInd - yNei) * (yInd - yNei));
					if (distance < interactionRadius) {
						possibleNeighbors.add(possibleNeighbor);
						density++;
					}
				}
				for (Individual newborn : newFieldAllSites[site]) {
					double xNei = newborn.getX();
					double yNei = newborn.getY();
					double distance = Math.sqrt((xInd - xNei) * (xInd - xNei) + (yInd - yNei) * (yInd - yNei));
					if (distance < interactionRadius) {
						density++;
					}
				}

				// check if density is smaller carrying capacity
				double offspringRadius = this.rs.getOffspringRadius();
				if (density / (Math.PI * offspringRadius * offspringRadius) < this.rs.getCarryingCapacity()) {
					Individual matingPartner;
					if (possibleNeighbors.size() > 1) {
						// make sure that individual does not interact with itself
						while (true) {
							matingPartner = possibleNeighbors.get(RandomVariable.getInt(possibleNeighbors.size()));
							if (matingPartner.getType() != ind.getType()
									|| Math.abs(matingPartner.getX() - ind.getX()) > 0.000000001
									|| Math.abs(matingPartner.getY() - ind.getY()) > 0.000000001) {
								break;
							}
						}
						if (rs.getPayoffMatrixEntry(ind.getType(), matingPartner.getType()) > RandomVariable.getDouble()) {
							// place offspring in offspring radius
							Individual offspring = new Individual();
							offspring.setType(ind.getType());
							if (RandomVariable.getDouble() < this.rs.getFractionInvasiveCells()) {
								offspring.setInvasive(true);
							} else {
								offspring.setInvasive(false);
							}
							

							double r2 = offspringRadius * Math.sqrt(RandomVariable.getDouble());
							double theta2 = 2 * Math.PI * RandomVariable.getDouble();
							offspring.setPosition(ind.getX() + r2 * Math.cos(theta2),
									ind.getY() + r2 * Math.sin(theta2));

							
							//DEBUGGING:
							// System.out.println("Payoff: " + rs.getPayoffMatrixEntry(ind.getType(),
							// matingPartner.getType()) + " Individual of type " + ind.getType() + " at " +
							// ind.getX() + "," + ind.getY() + " interacts with individual of type "+
							// matingPartner.getType() + " at " + matingPartner.getX() + "," +
							// matingPartner.getY() + " and produces offspring of type " +
							// offspring.getType() + " at " + offspring.getX() + "," + offspring.getY());
							
							newFieldAllSites[site].add(offspring);
							counterOffspring++;
						}
					}
				} else {
					counterAboveCarryingCapacity++;
				}
			}
		}
		int populationSize = 0;
		for (int i = 0; i < numberSites; i++) {
			populationSize += newFieldAllSites[i].size();
			populationSize += survivingIndsAllSites[i].size();
		}
		System.out.println("Population size at the end of the step: " + populationSize);
		System.out.println("Individuals that could not interact: " + counterAboveCarryingCapacity);
		System.out.println("Successful migrations: " + counterMigrations);
		ps.updatePopulation(newFieldAllSites, survivingIndsAllSites);
		s.saveStatistics(counterAboveCarryingCapacity, counterOffspring, ATon);
	}

}