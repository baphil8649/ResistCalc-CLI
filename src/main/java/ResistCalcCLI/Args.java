package ResistCalcCLI;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {

    // Color (band) Values (from left to right)
    @Parameter(names = {"-c", "--color-bands"})
    private List<String> colors = new ArrayList<String>();

    // Debug Mode
    // Prints System messages as the program moves through logic
    @Parameter(names = "--debug", hidden = true)
    private boolean debug = false;
    
    // Help or Usage()
    @Parameter(names = "--help", help = true)
    private boolean help = false;
    
    // Temperature Coefficient
    @Parameter(names = {"-tc", "--temp-coefficient"}) 
    private int tempCoef;

    // Resistor Value (translate to color code)
    @Parameter(names = {"-r", "--resistance-value"}) 
    private String resistance;    

    // Tolerance
    @Parameter(names = {"-t", "--tolerance"}) 
    private double tolerance;
    
    // Ohm Units (K, M, G)
    @Parameter(names = {"-u", "--units"}) 
    private String units = "O"; // Ohms

    
    public String[] getColors() {
        String[] colorsList;
        
        if(colors.size() == 0) {
            colorsList = new String[1]; // If no colors are sent, send one NULL value

        } else if(colors.size() > 6) {
            colorsList = new String[6]; // Restrict to six color bands

        } else {
            colorsList = new String[colors.size()];
        }
        colorsList = colors.toArray(colorsList);
        
        return colorsList; // Return a list of colors
    }
    
    public boolean getDebug() {
        return debug; // Return debug status
    }
    
    public boolean getHelp() {
        return help; // Return help status
    }
    
    public int getTempCoef() {
        return tempCoef; // Return Temperature Coefficient of Resistance (PPM)
    }

    public String getResistance() {
        return resistance; // Return Resistor Value
    }

    public double getTolerance() {
        return tolerance; // Return Tolerance
    }

    public String getUnits() {
        switch(units) {
            case "O": // Ohms...
                break;
            case "K": // Kilo...
                break;
            case "M": // Mega...
                break;
            case "G": // Giga...
                break;
            default:
                if(debug == true){
                    System.out.println("WARNING: Unrecognized unit of measure - defaulting to Ohms");
                }
                units = "O"; // Ohms
        }
        
        return units; // Return Ohm Units
    }
}