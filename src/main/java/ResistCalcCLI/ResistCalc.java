package ResistCalcCLI;

import com.beust.jcommander.JCommander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResistCalc {

    public static void main(String[] args) {
        ResistCalc rCalc = new ResistCalc();
        Args paramArgs = new Args();
        
        boolean except = false;

        if(args.length != 0) {
            try {
                // Args (arguments) and JCommander class objects
                JCommander jc = new JCommander(paramArgs);
            
                // Parse arguments for parameters
                jc.newBuilder().addObject(paramArgs).build().parse(args);
                
            } catch (Exception e) {
                except = true;
            } // end JCommander try


            if(except == false) { // Ensure JCommander could parse arguments
                // Get band colors
                // Limit bands to the number specified in bandsVal
                String[] colors = paramArgs.getColors();
                
                // Get resistance value (if calculating color bands from resistance value)
                // Can include unit values
                // Ex. 100, 2300, 1K, 135M, etc.
                String resistance = paramArgs.getResistance();

                // Get resistance units
                // For displaying calculated value
                // O = Ohms (default)
                // K = Kiloohms
                // M = Megaohms
                // G = Gigaohms
                String units = paramArgs.getUnits();
            
                // Get tolerance value (if calculating color bands from resistance value)
                double tolerance = paramArgs.getTolerance();
            
                // Get temperature coefficent (parts per million) (if calculating color bands from resistance value)
                int tempCoef = paramArgs.getTempCoef();

                // Help Indicator (prints manual page)
                boolean helpInd = paramArgs.getHelp();
            
                // Debug Indicator (shows additional logging)
                boolean debugInd = paramArgs.getDebug();
                
                // Debug arguments...
                if(debugInd) {
                    System.out.println("- Arguments -");
                    System.out.println("-------------");
                    System.out.println("Band Colors       : " + Arrays.toString(colors).toUpperCase());
                    System.out.println("Resistance        : " + resistance);
                    System.out.println("Units             : " + units);
                    System.out.println("Tolerance         : " + tolerance);
                    System.out.println("Temp. Coefficient : " + tempCoef);
                    System.out.println("Debug Ind         : " + debugInd);
                    System.out.println("Help Ind          : " + helpInd);
                    System.out.println("");
                } // ...end debug arguments
                
                if(!helpInd) {
                    Resistor r1 = new Resistor();
                
                    if(colors.length > 0) { // Translate color bands to resistance value
                        System.out.println(r1.colorsToResistance(colors, units, debugInd));
                    
                    } else if(resistance.length() > 0) { // Translate resistance to color bands
                        System.out.println(r1.resistanceToColors(resistance, tolerance, tempCoef, debugInd));
                    
                    } else { // Something else went wrong, display a short help text
                        rCalc.helpTextError("Try 'ResistCalc-CLI --help' for more information.");
                    } // end of colors or resistnace if
                
                } else { // Help was requested, display detailed help text (man-ish page)
                    rCalc.helpTextDetail();
                } // end of if(!helpInd)
                
            } else { // JCommander was not able to parse the arguments
                rCalc.helpTextError("ERROR:Invalid argument(s). Unable to parse.");
            }

        } else { // No parameters given
            rCalc.helpTextError("ERROR:No arguments given.");
        } // end main (testArgs.length != 0)
    } // end main method

    public void helpTextError(String error) {
        System.out.println("Usage: ResistCalc-CLI [OPTION]... [VALUE]...\n"
                           + error);
    } // end helpTextShort
    
    public void helpTextDetail() {
        System.out.println("NAME\n"
                           + "    ResistCalc-CLI - Resistor Calculator Command Line Interface Application\n\n"
                           + "SYNOPSIS\n"
                           + "    ResistCalc-CLI [OPTION] VALUE(s)...\n\n"
                           + "DESCRIPTION\n"
                           + "    ResistCalc-CLI is a command-line application for calculating the electrical resistance\n"
                           + "    of axial-lead (through hole) resistors from a given color code. ResistCalc-CLI will also\n"
                           + "    attempt to calculate the color band values based on a given resistance value, tolerance\n"
                           + "    and/or temperature coefficient.\n\n"
                           + "OPTIONS\n"
                           + "    --debug   Displays debug information while executing a given command.\n"
                           + "    --help    Outputs a detailed usage message and exits. Overrides all other options listed.\n\n"
                           + "    -c,  --color-bands\n"
                           + "           Translate resistance from color bands. Allows up to six bands in a comma delimited list.\n"
                           + "           Overrides translating color codes from resistance (-r) if both commands given.\n"
                           + "           See COLOR CODE AND BAND REFERENCE for all acceptable values.\n\n"
                           + "    -r,  --resistance-value\n"
                           + "           Translate color band codes from resistance in ohms. As a disclaimer, this assumes a five band\n"
                           + "           resistor with a default tolerance of +/-1% (BROWN) and may not be a pratical for real-world\n"
                           + "           application. A sixth band can be applied by defining a temp. coefficient value (-tc).\n\n"
                           + "    -t,  --tolerance\n"
                           + "           Tolerance value of resistor when translating color bands from resistance (-r).\n"
                           + "           See COLOR CODE AND BAND REFERENCE for all acceptable values.\n\n"
                           + "    -tc, --temp-coefficient\n"
                           + "           Temperature coefficient value of resistor when translating color bands from resistance (-r).\n"
                           + "           See COLOR CODE AND BAND REFERENCE for all acceptable values.\n\n"
                           + "    -u,  --units\n"
                           + "           Formats the the resistance to a standard unit of measure when translating resistance from color bands (-c).\n"
                           + "           Acceptable values are:\n"
                           + "             O = Ohms (default if not specified)\n"
                           + "             K = Kilo-ohms\n"
                           + "             M = Mega-ohms\n"
                           + "             G = Giga-ohms\n\n"
                           + "RESISTOR BAND AND COLOR CODE REFERENCE\n"
                           + "    Color       Value       Multiplier      Tolerance       Temp. Coefficient\n"
                           + "    -------------------------------------------------------------------------\n"
                           + "    BLACK       0           1 ohms                          250ppm/K\n"
                           + "    BROWN       1           10 ohms         +/-1%           100ppm/K\n"
                           + "    RED         2           100 ohms        +/-2%           50ppm/K\n"
                           + "    ORANGE      3           1K ohms                         15ppm/K\n"
                           + "    YELLOW      4           10K ohms                        25ppm/K\n"
                           + "    GREEN       5           100K ohms       +/-0.5%         20ppm/K\n"
                           + "    BLUE        6           1M ohms         +/-0.25%        10ppm/K\n"
                           + "    VIOLET      7           10M ohms        +/-0.10%        5ppm/K\n"
                           + "    GREY        8           100M ohms       +/-0.05%        1ppm/K\n"
                           + "    WHITE       9           1G ohms\n"
                           + "    GOLD                    0.10 ohms       +/-5%\n"
                           + "    SILVER                  0.01 ohms       +/-10%\n\n"
                           + "    # of Bands    Band 1      Band 2      Band 3      Band 4      Band 5      Band 6\n"
                           + "    --------------------------------------------------------------------------------\n"
                           + "    3             Value       Value       Multiplier  N/A         N/A         N/A\n"
                           + "    4             Value       Value       Multiplier  Tolerance   N/A         N/A\n"
                           + "    5             Value       Value       Value       Multiplier  Tolerance   N/A\n"
                           + "    6             Value       Value       Value       Multiplier  Tolerance   Temp. Coefficient\n\n"
                           + "EXAMPLES\n"
                           + "    ResistCalc-CLI -c red,green,blue\n"
                           + "        Outputs: 25000000 Ohms\n\n"
                           + "    ResistCalc-CLI -c orange,grey,blue,green,blue,brown -u M\n"
                           + "        Outputs: 38.6M Ohms +/-0.25% 100ppm/K\n\n"
                           + "    ResistCalc-CLI -r 38600000 -t .25 -tc 100\n"
                           + "        Outputs: ORANGE,GREY,BLUE,GREEN,BLUE,BROWN\n\n"
                           + "VERSION\n"
                           + "    1.0-0\n\n"
                           + "SOURCE\n"
                           + "    https://github.com/baphil8649/ResistCalc-CLI");
    } // end helpTextDetail
}