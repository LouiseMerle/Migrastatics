package UI;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import Tools.PopulationStatus;
import Tools.RuleSet;
import Tools.SaveObject;

/**
 * 	Class Save stores the output of all the different calculations to the, in the UI configured, output locations
 */
public class Save {
	
	private String path; 
	private FileWriter f;
	private FileWriter g;
	private FileWriter statistics;
	private FileWriter steps;
	private FileWriter settings;
	private ArrayList<SaveObject> history; //comes from SaveObject 
	
	
	/**
	 * Constructor for the Save class to initialize all values.
	 * @param path The path to which the files should be stored
	 * @param fileName The name given to the files stored.
	 * @param hybrid Not in use at the moment
	 * @param run The index of the current simulation run.
	 * @throws IOException When files cannot be stored this exception is thrown.
	 */
	public Save(String savePath, String fileName, boolean hybrid, int run) throws IOException{
		this.path = savePath; 
		f = new FileWriter(path + "/Location" +run+ ".m");
		g = new FileWriter(path + "/Distribution"+run + ".m");
		steps = new FileWriter(path + "/PlottedSteps"+run+ ".m"); 
		statistics = new FileWriter(path + "/Statistics"+run+ ".m"); 
		settings = new FileWriter(path + "/Settings"+run+ ".txt"); 
	}
	/**
	 * Function to save the statistics of a run.
	 * @param counterAboveCarryingCapacity ?
	 * @param counterOffspring ?
	 * @param ATon Wether or not the Adaptive treatment is on.
	 * @throws IOException Exception is thrown when file cannot be written.
	 */
	public void saveStatistics(int counterAboveCarryingCapacity, int counterOffspring, boolean ATon) throws IOException {
		statistics.write(counterAboveCarryingCapacity + "  " + counterOffspring + "  " + ATon +"\n" );
	}
	
	/**
	 * Function to save the settings of the simultion using the ruleset and the status of the population.
	 * @param rs The ruleset configured for the simulation.
	 * @param ps The population status for the simulation.
	 * @throws IOException Exception is thrown when file cannot be written.
	 */
	public void saveSettings(RuleSet rs, PopulationStatus ps) throws IOException {;
		String payoffString = "[";
		for (int i = 0; i < rs.getNumberTypes(); i++) {
			for (int j = 0; j < rs.getNumberTypes(); j++) {
				payoffString += rs.getPayoffMatrixEntry(i+1, j+1) + " ";
			}
			if (i != rs.getNumberTypes()-1) {
				payoffString += "; ";
			}
		}
		payoffString += "]";
		
		
		settings.write("Initial population size:		");
		settings.write(ps.getInitialPopulationSize() + "\n");
		settings.write("Initial type distribution:		");
		settings.write(ps.getDistributionString()+ "\n");
		settings.write("Initial placement:		");
		if (ps.getPlaceInds() == 0) {
			settings.write("randomly distributed"	 + "\n");
		} else {
			settings.write("\n");
		}
		settings.write("Initial tumor size:		");
		settings.write(ps.getinitTumorSize() + "\n");
		settings.write("Number types:		");
		settings.write(rs.getNumberTypes() + "\n");
		settings.write("Payoff Matrix:		");

		settings.write(payoffString + "\n");
		settings.write("Carrying Capacity:		");
		settings.write(rs.getCarryingCapacity() + "\n");
		settings.write("Death Probability:		");
		settings.write(rs.getDeathProbability() + "\n");
		settings.write("Neighborhood involved:		");
		settings.write("yes" + "\n");
		settings.write("Interaction Radii:		");
		settings.write(rs.getInteractionRadius() + "\n");
		settings.write("Offspring Radii:		");
		settings.write(rs.getOffspringRadius() + "\n");
		settings.write("Number migration sites		");
		settings.write(rs.getNumberMigrationSites() + "\n");
		settings.write("Survival new site		");
		settings.write(rs.getSurvivalDestination() + "\n");
		settings.write("Fraction invasive cells		");
		settings.write(ps.getFractionInvasiveCells() + "\n");
		settings.write("Probability that invasive cells migrate:		");
		settings.write(rs.getProbToMigrate() + "\n");
		settings.write("Treatment on after step:		");
		settings.write(rs.getCancerTreatmentStep() + "\n");
		settings.write("Fraction sensitive cells killed:		");
		settings.write(rs.getKillingSensitive() + "\n");
		settings.write("Migrastatics on after step:		");
		settings.write(rs.getMigrastaticStep() + "\n");
		settings.write("Probability to migrate when migrastatics on:		");
		settings.write(rs.getProbToMigrateDuringTreatment() + "\n");
		settings.close();
		
	}
	
	//Save steps into plottedSteps.txt
	/**
	 * Function to save steps into plottedSteps.txt
	 * @param step The step in the simulation.
	 * @throws IOException Exception that is thrown if the file cannot be stored.
	 */
	public void saveStepNo(int step) throws IOException{
		steps.write(step + "\n");
	}
	
	//Save distribution into Distribution.m
	/**
	 * Function to save the distribution into the file Distribution.m
	 * @param distri Matrix of distribution.
	 * @param total Total number of distributions.
	 * @throws IOException Exception that is thrown if the file cannot be stored.
	 */
	public void saveDistri(double [][] distri, int total) throws IOException{
		for (int i = 0; i < distri.length; i++) {
			for (int j = 0; j < 2; j++) {
				g.write(distri[i][j] + " ");
			}
			g.write(" ; ");
		}
		g.write(total + "\n");
	}

	
	//Save locations into Location.m
	/**
	 * Function to save the locations of the simulation into the Location.m file. 
	 * @param so The Save object class that contains all the locations.
	 * @throws IOException Exception that is thrown if the file cannot be stored.
	 */
	public void save(SaveObject so) throws IOException{
		f.write(so.toString() + "\n");	
	}
	/**
	 * Function to store the history of the simulation.
	 * @throws IOException Exception that is thrown if the file cannot be stored.
	 */
	public void writeHistory() throws IOException{
		for (int i = 0; i < history.size(); i++){
			f.write(history.get(i).toString() + "\n");
		}
		history.clear();
		history = new ArrayList<SaveObject>(100000);
		System.gc();
	}
	

	/**
	 * For all files generate a new line and close the file writer.
	 * @throws IOException Exception that is thrown if the file cannot be stored.
	 */
	public void finish() throws IOException{
		SaveObject so = new SaveObject((short)-10,(short)-10,(short)-10,-10,-10) ;
		f.write(so.toString() + "\n");
		f.close();
		g.close();
		steps.close();
		statistics.close();
	}
	
	
}
