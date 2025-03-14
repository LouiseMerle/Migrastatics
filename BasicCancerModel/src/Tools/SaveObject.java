package Tools;


/**
 * <p>
 * Class to save the state of a cell
 * </p>
 */
public class SaveObject {
	
	private short type; // 0 = sensitive, 1 = resistant
	private short invasive; // 0 = non-invasive, 1 = invasive
	private short site; //0 = solid tumor
	private double locationX; //location of cell
	private double locationY; //location of cell
	
	/**
	 * <p>
	 * Constructor to save a cell as object. 
	 * </p>
	 * @param type The type of the cell (0 = sensitive, 1 = resistant)
	 * @param invasive Wether or not the cell is invasive (0 = non-invasive, 1 = invasive)
	 * @param site The site in which the cell is located (0 = solid tumor)
	 * @param locationX Location of the cell on the X axis
	 * @param locationY Location of the cell on the y axis
	 */
	public SaveObject(short type, short invasive, short site, double locationX, double locationY){
		this.type = type; 
		this.invasive = invasive;
		this.site = site;
		this.locationX = locationX;
		this.locationY = locationY;
	}
	/**
	 * <p>
	 * Getter function to get the X axis location of a cell.
	 * </p>
	 * @return X axis location of the cell
	 */
	public double getLocationX() {
		return locationX;
	}
	/**
	 * <p>
	 * Setter function to set the X axis location of a cell.
	 * </p>
	 * @param locationX the new X axis value of the cell location.
	 */
	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}
	
	/**
	 * <p>
	 * Getter function to get the Y axis location of a cell.
	 * </p>
	 * @return Y axis location of the cell.
	 */
	public double getLocationY() {
		return locationY;
	}

	/**
	 * <p>
	 * Setter function to set the new Y axis location of a cell.
	 * </p>
	 * @param locationY the new Y axis value of the cell location.
	 */
	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	/**
	 * <p>
	 * Function to store the values for an individual cell for a given step in the simulation.
	 * </p>
	 * @param individual The individual cell
	 * @param currentStep The current step in the simulation.
	 */
	public SaveObject(Individual individual, int currentStep){
		this.type = individual.getType();
		boolean invasiveness = individual.getInvasive();
		if (invasiveness) {
			this.invasive = 1;
		} else {
			this.invasive = 0;
		}
		this.locationX = individual.getX();
		this.locationY = individual.getY();
	}

	public String toString(){
		return "" + this.type + "  " + this.invasive + "  " + this.site + "  " + this.locationX + "  " + this.locationY;
	}
	

}
