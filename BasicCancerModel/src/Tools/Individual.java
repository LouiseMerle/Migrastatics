package Tools;


/**
 * <p>
 * An individual has only information about itself. No information about distribution or field size etc. 
 * </p>
 */
public class Individual {

	private short type;  //type of individual
	private double x;	//x-coordinate
	private double y;	//y-coordinate
	private boolean invasive;
	
	//Getter 
	/**
	 * <p>
	 * Getter function for getting the type of the individual cell.
	 * </p>
	 * @return The type of the individual cell.
	 */
	public short getType() {
		return type;
	}
	/**
	 * <p>
	 * Setter function to set the type of the individual cell.
	 * </p>
	 * @param type The new type of the individual cell.
	 */
	public void setType(short type) {
		this.type = type;
	}
	/**
	 * <p>
	 * Getter function to get the X axis location of the individual cell
	 * </p>
	 * @return The X axis location of the individual cell.
	 */
	public double getX() {
		return x;
	}
	/**
	 * <p>
	 * Setter function to set the X axis location of the individual cell
	 * </p>
	 * @param x The new X axis location of the individual cell.
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * <p>
	 * Getter function to get the Y axis location of the individual cell
	 * </p>
	 * @return The Y axis location of the individual cell.
	 */
	public double getY() {
		return y;
	}
	/**
	 * <p>
	 * Setter function to set the Y axis location of the individual cell
	 * </p>
	 * @param y The new Y axis location of the individual cell.
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * <p>
	 * Setter function to set the X and Y axis location of the individual cell
	 * </p>
	 * @param x The new X axis location of the individual cell.
	 * @param y The new Y axis location of the individual cell
	 */
	public void setPosition(double x, double y){
		this.x = x; 
		this.y = y;
	}
	/**
	 * <p>
	 * Getter function to get if the cell is invasive.
	 * </p>
	 * @return The value if the cell is invasive.
	 */
	public boolean getInvasive() {
		return this.invasive;
	}
	/**
	 * <p>
	 * Setter function to set if the cell is invasive.
	 * </p>
	 * @param invasive The value if the cell is invasive.
	 */
	public void setInvasive(boolean invasive) {
		this.invasive = invasive;
	}
	
}
