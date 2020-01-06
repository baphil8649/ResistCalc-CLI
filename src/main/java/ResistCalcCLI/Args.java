package ResistCalcCLI;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {
    // Number of Bands (resistor type)
    @Parameter(names = {"-b", "--bands"}, 
            description = "Number of Bands (resistor type). Accepts 3 (default), 4, 5 and 6")
    private int bands = 3;

    // Color (band) Values (from left to right)
    @Parameter(names = {"-c", "--colors"}, description = "Color (band) Values (from left to right)")
    private List<String> colors = new ArrayList<String>();

    // Debug Mode
    // Prints System messages as the program moves through logic
    @Parameter(names = "--debug", description = "Debug Mode", hidden = true)
    private boolean debug = false;
    
    // Help or Usage()
    @Parameter(names = "--help", help = true)
    private boolean help;
    
    //Temperature Coefficient of Resistance (PPM)
    @Parameter(names = {"-p", "--ppm"}) 
    private int ppm;
    
    // Tolerance
    @Parameter(names = {"-t", "--tolerance"}) 
    private double tolerance;
    
    // Ohm Units (K, M, G)
    @Parameter(names = {"-u", "--units"}) 
    private String units = "O"; // 'O' = ohms
    
    // Resistor Value (translate to color code)
    @Parameter(names = {"-r", "--resistance-value"}) 
    private String resistance;


    public int getBands() {
        return bands; // Return the number of bands used
    }
    
    public List getColors() {
        return colors; // Return a list of colors
    }
    
    public boolean getDebug() {
        return debug; // Return debug status
    }
    
    public boolean getHelp() {
        return help; // Return help status
    }
    
    public int getPPM() {
        return ppm; // Return Temperature Coefficient of Resistance (PPM)
    }

    public double getTolerance() {
        return tolerance; // Return Tolerance
    }

    public String getUnits() {
        return units; // Return Ohm Units
    }

    public String getResistance() {
        return resistance; // Return Resistor Value
    }
}