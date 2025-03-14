package UI;


import javax.swing.JFileChooser;

/**
 * <p>
 * Class to get the file from the file system selected in the GUI using a String file path.
 * </p>
 */
public class JFileChooserLoadField {
	/**
	 * <p>
	 * Get the path from the JFileChooser component
	 * </p>
	 * @return the file path added in the GUI.
	 */
	public String getPath(){
		
		String path=null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));

		//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	    int status = fileChooser.showOpenDialog(null);


	    if (status == JFileChooser.APPROVE_OPTION) {
	      
	      path=fileChooser.getSelectedFile().toString();
	      
	      
	      } 
	    return path;
	}
}