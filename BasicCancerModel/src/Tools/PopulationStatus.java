package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * <p>
 * Class PopulationStatus stores all the properties and information about the total population. What individuals and their distribution are present at each step of the simulation.
 * </p>
 */
public class PopulationStatus {

	private ArrayList<Individual>[] cellsAllSites;
	private double [][] distributionAtAllSites;				//and their distribution
	private int initialPopulation;
	private int initTumorsize;
	private double fractionInvasiveCells;
	private int placeInds;
	private RuleSet rs;
	private int currentPopulationSize;
	
	/**
	 * <p>
	 * Getter function to get the initial tumor size of the population.
	 * </p>
	 * @return Initial tumor size.
	 */
	public int getinitTumorSize(){
		return this.initTumorsize;
	}
	
	/**
	 * <p>
	 * Getter function to get the fraction of cells that are invasive.
	 * </p>
	 * @return The fraction of cells that are invasive.
	 */
	public double getFractionInvasiveCells() {
		return this.fractionInvasiveCells;
	}
	
	
	// Constructor generating initial population
	/**
	 * <p>
	 * Constructor for the PopulationStatus class
	 * </p>
	 * @param population The total size of the initial population the simulation starts with.
	 * @param filePath Location of the file containing the initial population configuration - optional value
	 * @param distribution The total size of the initial distribution the simulation starts with.
	 * @param rs The ruleset that the simulation is started with.
	 * @param placeInds The location of the individual cells.
	 * @param initTumorsize The initial size of the tumor that the simulation starts with.
	 * @param fractionInvasiveCells The initial fraction of the cells that are invasive that the simulation starts with.
	 * @throws IOException Exception is thrown when file cannot be read from the file system.
	 */
	@SuppressWarnings("unchecked")
	public PopulationStatus(int population, String filePath, double[] distribution, RuleSet rs,
			int placeInds, int initTumorsize, double fractionInvasiveCells) throws IOException {
		int numberSites = rs.getNumberMigrationSites() + 1; //including solid tumor
		this.initTumorsize = initTumorsize;
		this.rs = rs;
		int numberTypes = rs.getNumberTypes();
		this.initialPopulation = population;
		this.fractionInvasiveCells = fractionInvasiveCells;
		this.distributionAtAllSites = new double[numberSites][numberTypes];
		this.placeInds = placeInds;
		for (int i = 0; i < numberTypes; i++) {
			this.distributionAtAllSites[0][i] = distributionAtAllSites[0][i];
		}
		this.cellsAllSites = (ArrayList<Individual>[]) new ArrayList[numberSites];
		
		for (int i = 0; i < numberSites; i++) {
			cellsAllSites[i] = new ArrayList<Individual>();
		}
		
		//If there is an initial population file configured in the UI use this to generate the population otherwise run the normal pre-coded population configuration.
		if(filePath.isEmpty() == false) {
			loadInitialPopulationFromFile(filePath);

		} else {
		
			this.currentPopulationSize = 0;
			int total1;
			int total2;
			double[] deviance;
			// Form Initial Population
			if (numberTypes == 2) {
				total1 = (int) (population * distribution[0]);
				total2 = (int) (population * distribution[1]) + total1;
				
				deviance = new double[2];
				deviance[0] = population * distribution[0] - total1;
				deviance[1] = population * distribution[1] - total2;
	
				double max = 0; // maximum deviance of all types
				short maxType = 0; // type of maximum deviance
				short forbidden = 3; // when rest is 2, don't put both in same type
				// (exclude from finding maximum)
				double x;
				double y;
				for (int i = 0; i < population; i++) {
	
					Individual ind = new Individual();
	
					// Set Position of individuals
					if (placeInds == 0) {
						// resistant and sensitive cells well-mixed in tumor
						double r2 = initTumorsize * Math.sqrt(RandomVariable.getDouble());
						double theta2 = 2 * Math.PI * RandomVariable.getDouble();
	
						x = r2 * Math.cos(theta2);
						y = r2 * Math.sin(theta2);
						ind.setPosition(x, y);
					} else if (placeInds == 1) {
						// resistant cells outside of tumor
						// TODO: Implement
					}
					
	
					if (i < total1) {
						ind.setType((short) 1);
					} else if (i < total2) {
						ind.setType((short) 2);
						// there may be some individuals left we have to decide which type
						// are they also. e.g., size=19, distri=[0.2,0.5,0.3], they will have
						// type 1 2 3: 3 9 5 respectively, there are 2 left then.We give it to
						// type 1 and 3 as [19*0.2-3,19*0.5-8,19*0.3-5] = [0.8, 0.5, 0.7] they have
						// bigger absolute
						// values
					} else {
						max = -1.0;
						maxType = 0;
	
						for (int j = 0; j < deviance.length; j++) {
							if (forbidden != j) {
								if (deviance[j] > max) {
									max = deviance[j];
									maxType = (short) (j + 1);
								} else if (deviance[j] == max) {
									maxType = (short) ((rs.getPayoffMatrixEntry((short) j, (short) (maxType - 1)) > rs
											.getPayoffMatrixEntry((short) (maxType - 1), (short) j)) ? maxType : j + 1);
								}
							}
						}
						ind.setType(maxType);
					}
					
					//Define invasiveness
					if (RandomVariable.getDouble() < fractionInvasiveCells) {
						ind.setInvasive(true);
					} else {
						ind.setInvasive(false);
					}
					
					cellsAllSites[0].add(ind);
					currentPopulationSize++;
				}
			}
		}
		updateDistribution();

	}
	/**
	 * <p>
	 * Getter function to retrieve all the place of all individuals
	 * </p>	
	 * @return the place of the individuals.
	 */
	public int getPlaceInds() {
		return this.placeInds;
	}
	/**
	 * <p>
	 * Getter function to retrieve the initial size of the population
	 * </p>	
	 * @return the initial size of the population.
	 */
	public int getInitialPopulationSize() {
		return this.initialPopulation;
	}
	

	private void loadInitialPopulationFromFile(String filePath) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("  ");
				if (parts.length >= 5) {
					short type = Short.parseShort(parts[0].trim());
					boolean invasive = parts[1].trim().equals("1");
					int site = Integer.parseInt(parts[2].trim());
					double x = Double.parseDouble(parts[3].trim());
					double y = Double.parseDouble(parts[4].trim());

					Individual ind = new Individual();
					ind.setType(type);
					ind.setInvasive(invasive);
					ind.setPosition(x, y);


					if (site >= 0 && site < cellsAllSites.length) {
						cellsAllSites[site].add(ind);
						currentPopulationSize++;
					}
				}
			}
		}
	}
	
	
 /**
  * <p>
  * Updates the distribution of cell types across all sites.
  * For each site, the number of cells of each type is counted and stored in an array.
  * The distribution of cell types is then calculated and stored in a 2D array.
  * </p>
  */
	public void updateDistribution(){
		for (int site = 0; site < rs.getNumberMigrationSites()+1; site++) {
			int[] types = new int[2];
			for (int i = 0; i < this.cellsAllSites[site].size(); i++){
				int type = this.cellsAllSites[site].get(i).getType();
				if (type == 1){
					types[0]++;
				} else if (type ==2){
					types[1]++;
				} else {
					types[2]++;
				}
			}
			for (int i = 0; i < distributionAtAllSites[site].length; i++) {
				this.distributionAtAllSites[site][i] = (double)(types[i]);
			}
		}
		
		
	}
	/**
	 * Getter function of all the individual cells of the population.
	 * @return all cells at all sites.
	 */
	public ArrayList<Individual>[] getIndividuals(){
		return this.cellsAllSites;
	}
	
	/**
	 * Getter function to get all individuals for a specific site.
	 * @param site The site for which the individuals should be retrieved.
	 * @return List of individual cells.
	 */
	public ArrayList<Individual> getIndividuals(int site){
		ArrayList<Individual> inds = new ArrayList<Individual>(this.cellsAllSites[site].size());
		for (int i = 0; i < this.cellsAllSites[site].size(); i++){
			inds.add(this.cellsAllSites[site].get(i));
		}
		return inds;
	}

	/**
	 * Getter function to get the distribution at a specific site
	 * @param site The site for which the distribution should be retrieved
	 * @return List of distribution.
	 */
	public double[] getDistribution(int site){
		return this.distributionAtAllSites[site];
	}
	/**
	 * Getter function for retrieving all the distributions at all sites.
	 * @return List of distributions.
	 */
	public double[][] getDistribution(){
		return this.distributionAtAllSites;
	}

	/**
	 * Getter function to get the distribution as a single String output.
	 * @return Concatenated String with all distributions.
	 */
	public String getDistributionString(){
		String s = "";
		for (int i = 0; i < this.rs.getNumberMigrationSites()+1; i++) {
			for (int j = 0; j < distributionAtAllSites[i].length; j++) {
				s += this.distributionAtAllSites[i][j] + " ";
			}
			s+= ";";
		}
		
		return s;
	}
	
 /**
  * Returns the current population size.
  *
  * @return The current population size.
  */
	public int getCurrentPopulationSize() {
		return this.currentPopulationSize;
	}

	
 /**
  * Updates the population of cells for all sites, given the new and surviving cells.
  *
  * @param newCellsdAllSites An array of ArrayLists containing the new cells for each site.
  * @param survivingCellsAllSites An array of ArrayLists containing the surviving cells for each site.
  */
	@SuppressWarnings("unchecked")
	public void updatePopulation(ArrayList<Individual>[] newCellsdAllSites, ArrayList<Individual>[] survivingCellsAllSites) {
		int numberSites = rs.getNumberMigrationSites()+1;
		this.currentPopulationSize =0;
		this.cellsAllSites = (ArrayList<Individual>[]) new ArrayList[numberSites];
		
		for (int site = 0; site < numberSites; site++) {
			cellsAllSites[site] = new ArrayList<Individual>();
			for (int i = 0; i < newCellsdAllSites[site].size(); i++) {
				this.cellsAllSites[site].add(newCellsdAllSites[site].get(i));
				currentPopulationSize++;
			}
			for (int j = 0; j < survivingCellsAllSites[site].size(); j++) {
				this.cellsAllSites[site].add(survivingCellsAllSites[site].get(j));
				currentPopulationSize++;
			}
		}
		this.updateDistribution();
	}

}
