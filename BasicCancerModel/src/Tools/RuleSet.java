package Tools;

/**
 * <p>
 * Class to define the complete ruleset of the simulation. This is used in subsequent classes and functions to execute the steps in the simulation.
 * </p>
 */
public class RuleSet {

	private double[][] payoffMatrix;
	private double interactionRadius; 
	private double offspringRadius;
	private double carryingCapacity;
	private double deathProbability;
	private int numberTypes;
	private double survivalDestination;
	private int numberMigrationSites;
	private double[][] siteLocations;
	private double probToMigrate;
	private double fractionInvasiveCells;
	private int cancerTreatmentStep;
	private double killingSensitive;
	private int migrastaticStep;
	private double probToMigrateDuringTreatment;
	private int ATadaptiveStep;
	private double ATstartTreatment;
	private double ATstopTreatment;


	/**
	 * <p>
	 * Constructor for initializing the Rule set for the simulation.
	 * </p>
	 * @param numberTypes The number of supported cell types for the simulation.
	 * @param payoffMatrix The interaction between the different cell types.
	 * @param deathProbability Probability of cell death for every iteration
	 * @param interactionRadius How far off from the radius of a cell interactions can take place.
	 * @param offspringRadius How far off a cell can place its offspring.
	 * @param carryingCapacity How many neighbours one cell can have.
	 * @param numberMigrationSites Number of sites the primary tumor can metastasise to.
	 * @param probToMigrate Probability for one cell to migrate to a metastasise.
	 * @param survivalDestination Probability for one cell to survive at new location
	 * @param fractionInvasiveCells Fraction of cells that can metastatise.
	 * @param tumorSize The initial size of the tumor at the start of the simulation, defined as a radius.
	 * @param cancerTreatmentStep At what step the cancer treatment should start in the simulation.
	 * @param killingSensitive Percentage of sensitive cells that are killed at every step by the cancer treatment.
	 * @param migrastaticStep At what step the migrastatics treatment should start in the simulation
	 * @param probToMigrateDuringTreatment The probability to migrate once migrastatics treatment is on.
	 * @param ATadaptiveStep At what step the adaptive treatment should be started.
	 * @param ATstartTreatment At what tumor size to start the treatment as a fraction of the initial tumor size
	 * @param ATstopTreatment At what tumor size to stop the treatment as a fraction of the initial tumor size.
	 */
	public RuleSet(int numberTypes, double[][]payoffMatrix, double deathProbability,
			double interactionRadius, double offspringRadius,
			double carryingCapacity, int numberMigrationSites, double probToMigrate, double survivalDestination, 
			double fractionInvasiveCells, int tumorSize, int cancerTreatmentStep, double killingSensitive, int migrastaticStep, double probToMigrateDuringTreatment,
			int ATadaptiveStep, double ATstartTreatment, double ATstopTreatment){

		this.interactionRadius = interactionRadius;
		this.offspringRadius = offspringRadius;
		this.deathProbability = deathProbability;
		this.numberTypes = numberTypes;
		this.survivalDestination = survivalDestination;
		this.numberMigrationSites = numberMigrationSites;
		this.payoffMatrix = new double[numberTypes][numberTypes];
		this.probToMigrate = probToMigrate;
		this.fractionInvasiveCells = fractionInvasiveCells;
		this.cancerTreatmentStep = cancerTreatmentStep;
		this.killingSensitive = killingSensitive;
		this.migrastaticStep = migrastaticStep;
		this.probToMigrateDuringTreatment = probToMigrateDuringTreatment;
		this.ATadaptiveStep = ATadaptiveStep; //gives us dynamically the start of the current cycle. The first cycle starts when the user defined the start. Next cycles are determined by the tumor size.
		this.ATstartTreatment = ATstartTreatment;
		this.ATstopTreatment = ATstopTreatment;
		
		this.siteLocations = new double[numberMigrationSites+1][2]; //for each migration size, we store x and y coordinate of the center of site
		//define where the migration sites are
		
		this.siteLocations[0][0] = 0;
		this.siteLocations[0][1] = 0;
		int distanceToTumor = 50;
		if (this.numberMigrationSites >= 1) {
			this.siteLocations[1][0] = 0;
			this.siteLocations[1][1] = tumorSize + distanceToTumor;
		}
		if (this.numberMigrationSites >= 2) {
			this.siteLocations[2][0] = tumorSize + distanceToTumor;
			this.siteLocations[2][1] = tumorSize + distanceToTumor;
		}
		if (this.numberMigrationSites >= 3) {
			this.siteLocations[3][0] = tumorSize + distanceToTumor;
			this.siteLocations[3][1] = 0;
		}
		if (this.numberMigrationSites >= 4) {
			this.siteLocations[4][0] = tumorSize + distanceToTumor;
			this.siteLocations[4][1] = -(tumorSize + distanceToTumor);
		}
		if (this.numberMigrationSites >= 5) {
			this.siteLocations[5][0] = 0;
			this.siteLocations[5][1] = -(tumorSize + distanceToTumor);
		}
		if (this.numberMigrationSites >= 6) {
			this.siteLocations[6][0] = -(tumorSize + distanceToTumor);
			this.siteLocations[6][1] = -(tumorSize + distanceToTumor);
		}
		if (this.numberMigrationSites >= 7) {
			this.siteLocations[7][0] = -(tumorSize + distanceToTumor);
			this.siteLocations[7][1] = 0;
		}
		if (this.numberMigrationSites == 8) {
			this.siteLocations[8][0] = -(tumorSize + distanceToTumor);
			this.siteLocations[8][1] = tumorSize + distanceToTumor;
		}
		if (this.numberMigrationSites > 8) {
			System.out.println("More than 8 sites are not yet implemented");
		}
		
		for (int i = 0; i < numberTypes; i++){
			for (int j = 0; j < numberTypes; j++){
				this.payoffMatrix[i][j] = payoffMatrix[i][j];
			}
		}
		this.carryingCapacity = carryingCapacity; 
	}
	
	/**
	 * <p>
	 * Getter function to get the value for at what step the migrastatic treatment should start.
	 * </p>
	 * @return the step at which the migrastatic treatment starts
	 */
	public int getMigrastaticStep () {
		return this.migrastaticStep;
	}
	/**
	 * <p>
	 * Getter function to get the value for the probability to migrate during treatment.
	 * </p>
	 * @return The probability.
	 */
	public double getProbToMigrateDuringTreatment() {
		return this.probToMigrateDuringTreatment;
	}
	/**
	 * <p>
	 * Getter function to get the value for at what step the cancer treatment should start.
	 * </p>
	 * @return At what step the cancer treatment starts.
	 */
	public int getCancerTreatmentStep () {
		return this.cancerTreatmentStep;
	}
	/**
	 * <p>
	 * Getter function to get the percentage of sensitive cells that are killed at every step.
	 * </p>
	 * @return The percentage of sensitive cells that are killed at every step.
	 */
	public double getKillingSensitive() {
		return this.killingSensitive;
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
	/**
	 * <p>
	 * Getter function to get the list of the location for a given site.
	 * </p>
	 * @param site The site for which the list of locations should be returned.
	 * @return The list of locations for a given site.
	 */
	public double [] getSiteLocation(int site) {
		return this.siteLocations[site];
	}
	/**
	 * <p>
	 * Getter function to get the probability of migration.
	 * </p>
	 * @return The probability of migration.
	 */
	public double getProbToMigrate() {
		return this.probToMigrate;
	}
	/**
	 * <p>
	 * Getter function to get the number of migration sites.
	 * </p>
	 * @return The number of migration sites.
	 */
	public int getNumberMigrationSites() {
		return this.numberMigrationSites;
	}
	/**
	 * <p>
	 * Getter function to get the location of the migration site.
	 * </p>
	 * @return the location of the migration site.
	 */
	public double getSurvivalDestination() {
		return this.survivalDestination;
	}
	
	//TODO: find out what number types is. 
	/**
	 * <p>
	 * Getter function to get all NumberTypes
	 * </p>
	 * @return the number of types.
	 */
	public int getNumberTypes() {
		return this.numberTypes;
	}
	/**
	 * <p>
	 * Getter function to get the Death probability
	 * </p>
	 * @return The death probability
	 */
	public double getDeathProbability() {
		return this.deathProbability;
	}
	
	/**
	 * <p>
	 * Getter function to get the carrying capacity
	 * </p>
	 * @return The carrying capacity.
	 */
	public double getCarryingCapacity(){
		return this.carryingCapacity;
	}
	/**
	 * <p>
	 * Setter function to set the carrying capacity
	 * </p>
	 * @param carryingCapacity The carrying capacity that should be set.
	 */
	public void setCarryingCapacity(double carryingCapacity){
		this.carryingCapacity = carryingCapacity;
	}
	/**
	 * <p>
	 * Getter function to get the interaction radius
	 * </p>
	 * @return The interaction radius.
	 */
	public double getInteractionRadius(){
		return this.interactionRadius;
	}
	/**
	 * <p>
	 * Getter function to get the offspring radius.
	 * </p>
	 * @return The offspring radius.
	 */
	public double getOffspringRadius(){
		return this.offspringRadius;
	}
	/**
	 * <p>
	 * Getter function to get what step is used to start the adaptive treatment.
	 * </p>
	 * @return Step at which the adaptive treatment is started.
	 */
	public int getATadaptiveStep() {
		return this.ATadaptiveStep;
	}
	/**
	 * <p>
	 * Setter function to set the step at which the adaptive treatment should start.
	 * </p>
	 * @param step The step at which the adaptive treatment should start.
	 */
	public void setATadaptiveStep(int step) {
		this.ATadaptiveStep = step;
	}
	/**
	 * <p>
	 * Getter function to get the start size of the tumor at which the adaptive treatment should start.
	 * </p>
	 * @return Start size of the tumor to start the adaptive treatment.
	 */
	public double getATstartTreatment() {
		return this.ATstartTreatment;
	}
	/**
	 * <p>
	 * Getter function to get the the size of the tumor at which the adaptive treatment should stop.
	 * </p>
	 * @return Size of the tumor to stop the adaptive treatment.
	 */
	public double getATstopTreatment() {
		return this.ATstopTreatment;
	}
	/**
	 * <p>
	 * Getter function to get the payoff matrix.
	 * </p>
	 * @param typeRow At what row to get the value out of the matrix.
	 * @param typeColumn At what column to get the value out of the matrix.
	 * @return The value of the payoff matrix for a given row and column.
	 */
	
	public double getPayoffMatrixEntry(int typeRow, int typeColumn) {
		return this.payoffMatrix[typeRow-1][typeColumn-1];
	}
}
