package ResistCalcCLI;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author baphil8649 (Blake Phillips)
 */
public class Args {
    // Number of Bands (resistor type)
    @Parameter(names = {"-b", "--bands"}, 
            description = "Number of Bands (resistor type). Accepts 3 (default), 4, 5 and 6")
    private int bands = 3;

    // Color (band) Values (from left to right)
    @Parameter(names = {"-c", "--colors"}, 
            description = "Color (band) Values (from left to right)\n"
                        + "Color - Value - Multiplier - Tolerance - PPM\n"
                        + "--------------------------------------------\n"
                        + "BLACK   0    1 ohms                  250ppm/K\n"
                        + "RED     2    10 ohms     +/-1%       100ppm/K\n"
                        + "ORANGE  3    1K ohms                 15ppm/K\n"
                        + "YELLOW  4    10K ohms                25ppm/K\n"
                        + "GREEN   5    100K ohms   +/-0.5%     20ppm/K\n"
                        + "BLUE    6    1M ohms     +/-0.25%    10ppm/K\n"
                        + "VIOLET  7    10M ohms    +/-0.10%    5ppm/K\n"
                        + "GREY    8    100M ohms   +/-0.25%    1ppm/K\n"
                        + "WHITE   9    1G ohms\n"
                        + "GOLD         0.10 ohms   +/-5%\n"
                        + "SILVER       0.01 ohms   +/-10%")
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
    private String units = "O";
    
    // Resistor Value (translate to color code)
    @Parameter(names = {"-v", "--value"}) 
    private String value;


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

    public String getValue() {
        return value; // Return Resistor Value
    }
}