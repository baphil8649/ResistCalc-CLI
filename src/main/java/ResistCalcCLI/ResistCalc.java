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
        
        // Test cases...
        //String[] testArgs = {"-c", "orange, grey, blue, green, blue, brown", "-u", "M", "--debug"};
        //String[] testArgs = {"-c", "brown, red, brown, blue, brown, yellow", "-u", "M", "--debug"};
        //String[] testArgs = {"-u", "M", "--debug"};
        
        String[] testArgs = {"-r", "300", "-u", "K", "--debug"};

        if(testArgs.length != 0) {  // TODO: replace testArgs with args
            try {
                // Args (arguments) and JCommander class objects
                JCommander jc = new JCommander(paramArgs);
            
                // Parse arguments for parameters
                jc.newBuilder().addObject(paramArgs).build().parse(testArgs);
                
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
                        System.out.println(r1.resistanceToColors(resistance, units, tolerance, tempCoef, debugInd));
                    
                    } else { // Something else went wrong, display a short help text
                        rCalc.helpTextError("Try 'resistcalc --help' for more information.");
                    } // end of colors or resistnace if
                
                } else { // Help was requested, display detailed help text (man-ish page)
                    rCalc.helpTextDetail();
                } // end of if(!helpInd)
                
            } else { // JCommander was not able to parse the arguments
                rCalc.helpTextError("Error: Unable to parse arguments.");
            }

        } else { // No parameters given
            rCalc.helpTextError("Error: No arguments given.");
        } // end main (testArgs.length != 0)
    } // end main method

    public void helpTextError(String error) {
        System.out.println("Usage: resistcalc [OPTION]... [VALUE]...\n"
                           + error);
    } // end helpTextShort
    
    public void helpTextDetail() {
        System.out.println("NAME\n"
                           + "    ResistCalc-CLI v1.0.0 - Resistor Calculator Command Line Interface Application\n\n"
                           + "SYNOPSIS\n"
                           + "    resistcalc [OPTION] VALUE(s)...\n\n"
                           + "DESCRIPTION\n"
                           + "    ResistCalc is a command-line application for calculating the electrical resistance\n"
                           + "    of axial-lead (through hole) resistors. ResistCalc will also attempt to calculate\n"
                           + "    the color band values based on a given resistance value, tolerance and/or temperature\n"
                           + "    coefficient.\n\n"
                           + "OPTIONS\n"
                           + "    --help Outputs a detailed usage message and exit.\n\n"
                           + "    -c,  --color-bands\n"
                           + "           Blah blah blah...\n\n"
                           + "    -r,  --resistance-value\n"
                           + "           Blah blah blah...\n\n"
                           + "    -t,  --tolerance\n"
                           + "           Blah blah blah...\n\n"
                           + "    -tc, --temp-coefficient\n"
                           + "           Blah blah blah...\n\n"
                           + "    -u,  --units\n"
                           + "           Blah blah blah...\n\n"
                           + "EXAMPLES\n"
                           + "    resistcalc -c red,green,blue\n"
                           + "        Outputs: 25000000 Ohms\n\n"
                           + "    resistcalc -c orange,grey,blue,green,blue,brown -u M\n"
                           + "        Outputs: 38.6M Ohms +/-0.25% 100ppm/K\n\n"
                           + "    resistcalc -r 300 -u K\n\n"
                           + "SOURCE\n"
                           + "    https://github.com/baphil8649/ResistCalc-CLI");
    } // end helpTextDetail
}
