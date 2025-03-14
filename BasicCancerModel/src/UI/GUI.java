package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Tools.Controller;

/**
 * <p>
 * Class to generate the UI using JFrame framework
 * </p>
 */
public class GUI extends JFrame implements ActionListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9036858918060403958L;

	/**
	 * New instance of the Controller class to start a simulation.
	 */
	private Controller controller = new Controller();
	
	/**
	 * Input field to input the number of simulation runs.
	 */
	private JTextField numberSimulationRuns = new JTextField("1");
	/**
	 * Input field to store the number of steps taken per simulation.
	 */
	private JTextField numberSteps = new JTextField("100");
	/**
	 * The number of steps taken after which the results are pltoted.
	 */
	private JTextField gap = new JTextField("1"); //plot after so many steps
	/**
	 * Input field for the file name to store the plots
	 */
	private JTextField filenName = new JTextField("Test");
	
	
	/**
	 * Input field for the initial population size
	 */
	private JTextField population = new JTextField("2000");	
	/**
	 * Input field for the initial tumor size
	 */
	private JTextField initTumorSize = new JTextField("10");
	/**
	 * Input field for the initial distribution of sensitive cells
	 */
	private JTextField distri1 = new JTextField("3/4");
	/**
	 * Input field for the initial distribution of resistant cells
	 */
	private JTextField distri2 = new JTextField("1/4");

	/**
	 * Input field to set the interaction radius.
	 */
	private JTextField narea1 = new JTextField("1");
	/**
	 * Input field to set the Density radius.
	 */
	private JTextField karea1 = new JTextField("1");

	/**
	 * Input field for first row, first column in the Payoff matrix
	 */
	private JTextField payoff00 = new JTextField("0.7");
	/**
	 * Input field for first row, second column in the Payoff matrix
	 */
	private JTextField payoff01 = new JTextField("0.3");
	/**
	 * Input field for second row, first column in the Payoff matrix
	 */
	private JTextField payoff10 = new JTextField("0.2");
	/**
	 * Input field for second row, second column in the Payoff matrix
	 */
	private JTextField payoff11 = new JTextField("0.2");
	
	/**
	 * Input field for the death probability
	 */
	private JTextField deathProb = new JTextField("0.20");
	/**
	 * Input field for The carrying capacity
	 */
	private JTextField carryingCapacity = new JTextField("6");
	
	/**
	 * Input field for the fraction of cells that are invasive
	 */
	private JTextField fractionInvasiveCells = new JTextField("0.1");
	/**
	 * Input field for probability for one cell to survice at new destination
	 */
	private JTextField survivalDestination = new JTextField("0.1");
	/**
	 * Input field for number of migration sites
	 */
	private JTextField numberMigrationSites = new JTextField("8");
	/**
	 * Input field for probability for a cell to migrate to a metastasise.
	 */
	private JTextField probabilityToMigrate = new JTextField("0.1");
	
	/**
	 * Input field for step at which the cancer treatment should start
	 */
	private JTextField cancerTreatmentStep = new JTextField("-1");
	/**
	 * Input field for percentage of sensitive cells that are killed at every step by the cancer treatment.
	 */
	private JTextField killingSensitive = new JTextField("0.9");
	
	/**
	 * Input field for at what step the migrastatics should start
	 */
	private JTextField migrastaticsStep = new JTextField("-1");
	/**
	 * Input field for probability to migrate once migrastatics treatment is on.
	 */
	private JTextField probabilityToMigrateWhenTreating = new JTextField("0.01");
	
	/**
	 * Input field for the step at which the adaptive treatment shoudl be started
	 */
	private JTextField adaptiveStep = new JTextField("0");
	/**
	 * Input field to which percentage needs the tumor to re-grow before starting applying MDT again
	 */
	private JTextField startTreatment = new JTextField("1"); //
	/**
	 * Input field to which percentage of tumor size when treatment is stopped
	 */
	private JTextField stopTreatment = new JTextField("0.5");
	
	/**
	 * Building up the UI
	 */
	private JPanel mainpanel, subpanel1, subpanel2, subpanel3, subpanel4, 
	subpanel8, subpanel11,subpanel5,brpanel,brpanelFp,
	subpanel12;
	/**
	 * Adding the buttons to the UI to start exit and browse
	 */
	private JButton start, exit, browse, browseFp;
	/**
	 * Input field for the folder where all output files should be stored in.
	 */
	private JTextField saveto=new JTextField("");
	//private JTextField loadField = new JTextField();
	/**
	 * Input field for the file that allows to start with an initial population from a config file.
	 */
	private JTextField filePath = new JTextField("");
	/**
	 * Function to setup the GUI
	 */
	public GUI() {

		this.setTitle("Local Dynamic Model (SH)");
		
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(53, 2));
		
		JLabel label = new JLabel("    Initial settings");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		mainpanel.add(label);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));

		mainpanel.add(new JLabel("    Initial Population:"));
		mainpanel.add(population);

		mainpanel.add(new JLabel("    Initial Distribution (sensitive, resistant):"));
		subpanel8 = new JPanel(new GridLayout(1, 3));
		subpanel8.add(distri1);
		subpanel8.add(distri2);
		mainpanel.add(subpanel8);
		
		mainpanel.add(new JLabel("    Initial tumor size:"));
		mainpanel.add(initTumorSize);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		label = new JLabel("    Cancer model (without treatment) parameters");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		mainpanel.add(label);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		

		mainpanel.add(new JLabel("    Payoff Matrix:"));
		subpanel1 = new JPanel();
		subpanel1.setLayout(new GridLayout(1, 2));
		subpanel1.add(payoff00);
		subpanel1.add(payoff01);
		mainpanel.add(subpanel1);

		mainpanel.add(new JLabel());
		subpanel2 = new JPanel();
		subpanel2.setLayout(new GridLayout(1, 2));
		subpanel2.add(payoff10);
		subpanel2.add(payoff11);
		mainpanel.add(subpanel2);

		mainpanel.add(new JLabel());
		subpanel3 = new JPanel();
		subpanel3.setLayout(new GridLayout(1, 2));
		mainpanel.add(subpanel3);
		
		
		mainpanel.add(new JLabel("    Interaction Radii:"));
		subpanel11 = new JPanel(new GridLayout(1, 1));
		subpanel11.add(narea1);
		mainpanel.add(subpanel11);
		mainpanel.add(new JLabel("    Offspring Radii:"));
		subpanel12 = new JPanel(new GridLayout(1, 1));
		subpanel12.add(karea1);
		mainpanel.add(subpanel12);
		narea1.setEnabled(true);
		karea1.setEnabled(true);
		
		mainpanel.add(new JLabel("    Carrying Capacity (cells per unit area):"));
		mainpanel.add(carryingCapacity);

		mainpanel.add(new JLabel("    Death Probability:")); 
		mainpanel.add(deathProb); 
		
		
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		label = new JLabel("    Migration model (without treatment) parameters");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		mainpanel.add(label);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		mainpanel.add(new JLabel("    Number of possible migration sites:"));
		mainpanel.add(this.numberMigrationSites);
		
		mainpanel.add(new JLabel("    Fraction of invasive cells within tumor:"));
		mainpanel.add(fractionInvasiveCells);
		
		mainpanel.add(new JLabel("    Probability that invasive cells migrate:"));
		mainpanel.add(this.probabilityToMigrate);
		
		mainpanel.add(new JLabel("    Survival chance at destination site:"));
		mainpanel.add(survivalDestination);
		
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		
		label = new JLabel("    MTD parameters");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		mainpanel.add(label);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		mainpanel.add(new JLabel("    Treatment initiated at step (-1 if no treatment):"));
		mainpanel.add(cancerTreatmentStep);
		
		mainpanel.add(new JLabel("    Killing fraction of sensitive cells:"));
		mainpanel.add(killingSensitive);
		
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		
		
		label = new JLabel("    Migrastatics parameters");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		mainpanel.add(label);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		mainpanel.add(new JLabel("    Treatment initiated at step (-1 if no treatment):"));
		mainpanel.add(this.migrastaticsStep);
		mainpanel.add(new JLabel("    Probability to migrate after treatment:"));
		mainpanel.add(this.probabilityToMigrateWhenTreating);
		
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		label = new JLabel("    Adaptive Treatment parameters (dose = MTD if treatment is on)");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		mainpanel.add(label);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		mainpanel.add(new JLabel("    Adaptive treatment initiated at step (-1 if no treatment):"));
		mainpanel.add(adaptiveStep);
		
		mainpanel.add(new JLabel("    Start treatment when tumor size increased again to that percentage"));
		mainpanel.add(startTreatment);
		
		mainpanel.add(new JLabel("    Stop treatment when tumor size decreased again to that percentage"));
		mainpanel.add(stopTreatment);
		
		
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		label = new JLabel("    Simulation settings");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		mainpanel.add(label);
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		
		mainpanel.add(new JLabel("    Number of steps:"));
		mainpanel.add(numberSteps);
		mainpanel.add(new JLabel("    Plot after steps: "));
		mainpanel.add(gap);
		mainpanel.add(new JLabel("    Number simulation runs: "));
		mainpanel.add(this.numberSimulationRuns);

		
		mainpanel.add(new JLabel("    File initial condition (leave empty for random initial condition):"));
		mainpanel.add(filePath);

		
		mainpanel.add(new JLabel("  "));
		brpanelFp = new JPanel();
		brpanelFp.setLayout(new GridLayout(1, 4));
		browseFp = new JButton("browse");
		brpanelFp.add(new JLabel("  "));
		brpanelFp.add(new JLabel("  "));
		brpanelFp.add(new JLabel("  "));
		brpanelFp.add(browseFp);
		mainpanel.add(brpanelFp);////////
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		

		mainpanel.add(new JLabel("    File name:"));
		mainpanel.add(filenName);
		
		mainpanel.add(new JLabel("    Save directory:"));
		mainpanel.add(saveto);
		
		mainpanel.add(new JLabel("  "));
		brpanel = new JPanel();
		brpanel.setLayout(new GridLayout(1, 4));
		browse = new JButton("browse");
		brpanel.add(new JLabel("  "));
		brpanel.add(new JLabel("  "));
		brpanel.add(new JLabel("  "));
		brpanel.add(browse);
		mainpanel.add(brpanel);////////
		mainpanel.add(new JLabel("  "));
		mainpanel.add(new JLabel("  "));
		

		
		subpanel4 = new JPanel();
		subpanel4.setLayout(new GridLayout(1, 1));
		subpanel5 = new JPanel();
		subpanel5.setLayout(new GridLayout(1, 2));
		start = new JButton("Start&Save");
		exit = new JButton("Exit");
		subpanel4.add(start);
		subpanel5.add(exit);
		mainpanel.add(subpanel4);
		mainpanel.add(subpanel5);

		start.addActionListener(this);
		exit.addActionListener(this);
		browse.addActionListener(this);
		browseFp.addActionListener(this);

		this.add(mainpanel);
		this.setPreferredSize(new Dimension(900,650));
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	/**
	 * <p>This function is the listener for all action buttons in the UI. It will detect the source type and based on this trigger a specific function</p>
	 * @param e The action that has been triggered
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == start) {			
			try {
				
				double[]distribution = {
						DeciToDouble(this.distri1.getText()),
						DeciToDouble(this.distri2.getText())
				};
				
				double [][] payoffMatrix = {
						{Double.parseDouble(payoff00.getText()),Double.parseDouble(payoff01.getText())},
						{Double.parseDouble(payoff10.getText()),Double.parseDouble(payoff11.getText())}

				};
				
				int placeInds = 0;//0 = randomly distributed

				controller.run(
						this.filePath.getText(),
						Integer.parseInt(this.population.getText()),  //population size
						distribution, //initial type distribution
						placeInds, 
						Integer.parseInt(initTumorSize.getText()), 
						2, //number types
						payoffMatrix,	//array 2x2 payoff matrix
						Double.parseDouble(this.narea1.getText()),
						Double.parseDouble(this.karea1.getText()),
						Double.parseDouble(deathProb.getText()),
						Double.parseDouble(this.carryingCapacity.getText()), 
						Integer.parseInt(this.numberMigrationSites.getText()),
						Double.parseDouble(this.fractionInvasiveCells.getText()),
						Double.parseDouble(this.probabilityToMigrate.getText()),
						Double.parseDouble(this.survivalDestination.getText()),
						//this.loadField.getText(),
						this.saveto.getText(), // save path
						this.filenName.getText(), // filename
						Integer.parseInt(this.numberSimulationRuns.getText()), 
						Integer.parseInt(this.gap.getText()),
						Integer.parseInt(this.numberSteps.getText()), 
						Integer.parseInt(this.cancerTreatmentStep.getText()),
						Double.parseDouble(this.killingSensitive.getText()),
						Integer.parseInt(this.migrastaticsStep.getText()),
						Double.parseDouble(this.probabilityToMigrateWhenTreating.getText()), 
						Integer.parseInt(this.adaptiveStep.getText()),
						Double.parseDouble(this.startTreatment.getText()), 
						Double.parseDouble(this.stopTreatment.getText())
						

				);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, String.format("Error while starting simulation: %s \n %s", e1.getMessage(), e1.getStackTrace()));
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, String.format("Error while starting simulation: %s \n %s", e1.getMessage(), e1.getStackTrace()));
				e1.printStackTrace();
			} finally {
				JOptionPane.showMessageDialog(this, "Simulation run succesfully");
			}

		}
		if (e.getSource() == exit) {

			System.exit(0);
		}

		if(e.getSource() == browse){

			saveto.setText(new JFileChooserSelectionOption().getPath());
		}
		if(e.getSource() == browseFp) {
			
			filePath.setText(new JFileChooserSelectionOption().getPath());
		}
		
		
	}
	
	
		/**
		 * <p>to form distribution from ()/() to double</p>
		 * @param s1 string value to change into double.
		 * @return change the transformed distribution as a double.
		 */
		public double DeciToDouble(String s1) {
			double change;
			if(s1.length()>1){

				String[] str1 = s1.split("/", 2);
				change = (Double.parseDouble(str1[0]))
						/ (Double.parseDouble(str1[1]));
			}
			else {
				if(s1.startsWith("0")) change=0;
				else change=1;
			}
			return change;
		}

}
