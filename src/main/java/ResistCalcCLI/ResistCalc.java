package ResistCalcCLI;

import com.beust.jcommander.JCommander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResistCalc {

    public static void main(String[] args) {
        ResistCalc rCalc = new ResistCalc();
        
        // Test cases...
        String[] testArgs = {"-c", "blue, green, grey", "-u", "M", "--debug"}; // TODO: replace testArgs with args
        //String[] testArgs = {"-c", "black, green, red", "--debug"};
        //String[] testArgs = {"-c", "white, white, white, red, red", "--debug"};
        //String[] testArgs = {"-c", "blue, red, orange, green, green, black", "-u", "K", "--debug"};
        //String[] testArgs = {"-c", "blue, red", "--debug"};
        //String[] testArgs = {"-c", "blue, glob", "--debug"};
        //String[] testArgs = {"-u", "K", "--debug"};
        //String[] testArgs = {"-c", ",", "-u", "K", "--debug"};
        //String[] testArgs = {"-v", "300M", "-t", ".5", "-p", "100"};
        //String[] testArgs = {"--help"};
        //String[] testArgs = {};
        //String[] testArgs = {""};
        //String[] testArgs = {" "};

        if(testArgs.length != 0) {  // TODO: replace testArgs with args

            try {
                // Arge (arguments) and JCommander class objects
                Args paramArgs = new Args();
                JCommander jc = new JCommander(paramArgs);
            
                // Parse argument for parameters
                jc.newBuilder().addObject(paramArgs).build().parse(testArgs);
            
                // Get band colors
                // Limit bands to the number specified in bandsVal
                String[] colors = paramArgs.getColors();
                
                // Get resistance value (if calculating color bands from resistance value)
                // Can include unit values
                // Ex. 100, 2300, 1K, 135M, etc.
                String resistance = paramArgs.getResistance();

                // Get resistance units
                // For displaying calculated value
                // O = ohms (default)
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
                }   // ...end debug arguments
                
                if(!helpInd) {
                    Resistor r1 = new Resistor();
                
                    if(colors.length > 0) { // Translate color bands to resistance value
                        System.out.println(r1.translateToValue(colors, units, debugInd));
                    
                    } else if(resistance.length() > 0) { // Translate resistance to color bands
                        System.out.println(r1.translateToColors(resistance, units, tolerance, tempCoef, debugInd));
                    
                    } else { // Something went wrong, display a short help text
                        rCalc.helpTextShort();
                    } // end of colors or resistnace if
                
                } else { // Help was requested, display short man page
                    rCalc.helpTextLong();
                } // end of if(!helpInd)
                
            } catch (Exception e) {
                rCalc.helpTextShort();
            } // end of main try

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
                         + "    ResistCalc-CLI v1.0.0 - Resistor Calculator Command Line Interface Application\n\n"
                         + "SYNOPSIS\n"
                         + "    resistcalc [OPTION]... <VALUE>...\n\n"
                         + "DESCRIPTION\n"
                         + "    ResistCalc blah balh blah...\n\n"
                         + "OPTIONS\n"
                         + "    -b, --bands\n"
                         + "        Blah blah blah...\n\n"
                         + "SOURCE\n"
                         + "    https://github.com/baphil8649/ResistCalc-CLI");
    } // end helpTextLong
}
