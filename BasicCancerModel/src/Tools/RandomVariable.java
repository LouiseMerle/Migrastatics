package Tools;

import java.util.Random;

//Random number generator
//If you want to generate a random number write: RandomVariable.getDouble() 
//or RandomVariable.getInt()

/**
 * <p>
 * Class to generate random variables for different inputs
 * </p>
 */
public class RandomVariable {
	
	private static Random rand = new Random();
	
	/**
	 * <p>
	 * Function to generate a random Double value.
	 * </p>
	 * @return Random Double
	 */
	public static double getDouble(){
		return rand.nextDouble();
	}
	
	/**
	 * <p>
	 * Function to generate a random Double, but with a multiplier using an end index
	 * </p>
	 * @param endIndex Mulitplier for the randomly generated Double
	 * @return Random generated Double.
	 */
	public static double getDouble(double endIndex){
		return rand.nextDouble() *endIndex;
	}
	
	//0 inclusive, upperLimit exclusive
	/**
	 * <p>
	 * Function to generate a random Integer using an upper limit.
	 * </p>
	 * @param upperLimit Upper limit of the generated random Integer.
	 * @return Random generated integer.
	 */
	public static int getInt(int upperLimit){
		return rand.nextInt(upperLimit);
	}

}
