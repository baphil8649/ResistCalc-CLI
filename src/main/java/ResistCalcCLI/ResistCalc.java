package ResistCalcCLI;

import com.beust.jcommander.JCommander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResistCalc {

    public static void main(String[] args) {
        ResistCalc rCalc = new ResistCalc();
        
        String[] testParams = {"-c", "blue, green, red", "-u", "M", "--debug"}; // TODO: replace testParams with args
        //String[] testParams = {"-b", "5", "-c", "blue, red, orange", "-u", "K", "--debug"}; // TODO: replace testParams with args
        //String[] testParams = {"-v", "300M", "-t", ".5", "-p", "100"};
        //String[] testParams = {"--help"};
        //String[] testParams = {};

        if(testParams.length != 0) {  // TODO: replace testParams with args
            // Arge (arguments) and JCommander class objects
            Args paramArgs = new Args();
            JCommander jc = new JCommander(paramArgs);
            
            // Parse argument for parameters
            jc.newBuilder().addObject(paramArgs).build().parse(testParams); // TODO: replace testParams with args

            // Get number of color bands to calculate
            // 3 (default) - 6 (max)
            // Ex. 'java -jar ResistCalc -b 5' sets band value to 5
            int bandsVal = paramArgs.getBands();
            if(bandsVal < 3 || bandsVal > 6) { // Set bands value to default of 3
                bandsVal = 3;                  // if less than 3 or greater than 6
            }
            
            // Get band colors
            // Limit bands to the number specified in bandsVal
            List<String> colors = new ArrayList<String>(paramArgs.getColors());
            String[] colorsList = new String[bandsVal];
            colorsList = colors.toArray(colorsList);

            // Get resistance value (if calculating color bands from resistance value)
            // Can include unit values
            // Ex. 100, 2300, 1K, 135M, etc.
            String resistanceVal = paramArgs.getResistance();

            // Get resistance units
            // For displaying calculated value
            // O = ohms (default)
            // K = Kiloohms
            // M = Megaohms
            // G = Gigaohms
            String unitsVal = paramArgs.getUnits();
            
            // Get tolerance value (if calculating color bands from resistance value)
            double toleranceVal = paramArgs.getTolerance();
            
            // Get temperature coefficent (parts per million) (if calculating color bands from resistance value)
            int ppmVal = paramArgs.getPPM();

            // Help Indicator (prints manual page)
            boolean helpInd = paramArgs.getHelp();
            
            // Debug Indicator (shows additional logging)
            boolean debugInd = paramArgs.getDebug();
            
            // Arguments at beginning...
            if(debugInd) {
                System.out.println("- Arguments -");
                System.out.println("-------------");
                System.out.println("Number of Bands : " + bandsVal);
                System.out.println("Band Colors     : " + Arrays.toString(colorsList).toUpperCase());
                System.out.println("Resistance      : " + resistanceVal);
                System.out.println("Units           : " + unitsVal);
                System.out.println("Tolerance       : " + toleranceVal);
                System.out.println("PPM             : " + toleranceVal);
                System.out.println("Debug Ind       : " + debugInd);
                System.out.println("Help Ind        : " + helpInd);
                System.out.println("");
            }

            if(!helpInd) {
                Resistor r1 = new Resistor();
                
                if(colorsList.length > 0) { // Translate color bands to resistance value
                    System.out.println(r1.translateToValue(colorsList, unitsVal, debugInd));
                    
                } else if(resistanceVal.length() > 0) { // Translate resistance to color bands
                    System.out.println(r1.translateToColors(resistanceVal, unitsVal, toleranceVal, ppmVal, debugInd));
                    
                } else { // Something went wrong, display a short help text
                    rCalc.helpTextShort();
                }
                
            } else { // Help was requested, display short man page
                rCalc.helpTextLong();
            }
            
        } else { // No parameters given, display a short help text
            rCalc.helpTextShort();
        } // end main if
    } // end main method

    public void helpTextShort() {
        System.out.println("Usage: resistcalc [OPTION]... [VALUE]...\n"
                             + "Try 'resistcalc --help' for more information.");
    } // end helpTextShort
    
    public void helpTextLong() {
        System.out.println("NAME\n"
                         + "    ResistCalc-CLI v1.0.0 - Resistor Calculator Command Line Interface Application\n"
                         + "SYNOPSIS\n"
                         + "    resistcalc [OPTION]... <VALUE>...\n"
                         + "DESCRIPTION\n"
                         + "    ResistCalc blah balh blah...\n"
                         + "OPTIONS\n"
                         + "    -b, --bands\n"
                         + "        Blah blah blah...\n"
                         + "SOURCE\n"
                         + "    https://github.com/baphil8649/ResistCalc-CLI");
    } // end helpTextLong
}
