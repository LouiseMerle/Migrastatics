package UI;


import javax.swing.JFileChooser;

/**
 * <p>
 * Class to get the file from the file system selected in the GUI using a File selector widget.
 * </p>
 */
public class JFileChooserSelectionOption {
	
	/**
	 * Function to get the path of a file from the file chooser widget.
	 * @return The file path of the selected file.
	 */
	public String getPath(){
		
		String path=null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));

		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

	    int status = fileChooser.showOpenDialog(null);


	    if (status == JFileChooser.APPROVE_OPTION) {
	      
	      path=fileChooser.getSelectedFile().toString();
	      
	      
	      } 
	    return path;
	}
}