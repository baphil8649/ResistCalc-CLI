package ResistCalcCLI;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {
    // Color (band) Values (from left to right)
    @Parameter(names = {"-c", "--color-bands"})
    private List<String> colors = new ArrayList<String>();

    // Resistor Value (translate to color code)
    @Parameter(names = {"-r", "--resistance-value"}) 
    private String resistance = "";

    // Tolerance
    @Parameter(names = {"-t", "--tolerance"}) 
    private double tolerance;

    // Temperature Coefficient
    @Parameter(names = {"-tc", "--temp-coefficient"}) 
    private int tempCoef;    

    // Ohm Units (K, M, G)
    @Parameter(names = {"-u", "--units"}) 
    private String units = "O"; // Ohms
    
    // Debug Mode
    // Prints System messages as the program moves through logic
    @Parameter(names = "--debug", hidden = true)
    private boolean debug = false;
    
    // Help or Usage()
    @Parameter(names = "--help", help = true)
    private boolean help = false;
        
    public String[] getColors() {
        String[] colorsList;
        
        if(colors.size() > 6) {
            colorsList = new String[6]; // Restrict to six color bands

        } else {
            colorsList = new String[colors.size()];
        }
        
        colorsList = colors.toArray(colorsList);
        
        // Uppercase and trim characters on each given band
        for(int idx = 0; idx < colorsList.length; idx++) {
            colorsList[idx] = colorsList[idx].toUpperCase().trim();
        }
        
        return colorsList; // Return a list of colors
    }

    public String getResistance() {
        //if(resistance.matches(".*[a-zA-Z]+.*") == true) {
        //    resistance = "HELP";
        //}
        
        if(resistance.matches("^[0-9]+$") == false) { // Numerical values only
            resistance = "NOTNUMERIC";
        }
        
        if(resistance.length() > 12) { // Restrict to twelve digits
            resistance = resistance.substring(0, 12);
        }
        
        return resistance;
    }

    public double getTolerance() {
        return tolerance; // Return Tolerance
    }

    public int getTempCoef() {
        return tempCoef; // Return Temperature Coefficient of Resistance (PPM)
    }

    public String getUnits() {
        switch(units.toUpperCase()) {
            case "O": // Ohms...
                break;
            case "K": // Kilo...
                break;
            case "M": // Mega...
                break;
            case "G": // Giga...
                break;
            default:
                units = "O"; // Ohms
        }
        
        return units.toUpperCase(); // Return Ohm Units
    }

    public boolean getDebug() {
        return debug; // Return debug status
    }
    
    public boolean getHelp() {
        return help; // Return help status
    }
}