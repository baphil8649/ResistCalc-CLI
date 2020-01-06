package ResistCalcCLI;

import com.beust.jcommander.JCommander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class ResistCalc {

    public static void main(String[] args) {
        ResistCalc rCalc = new ResistCalc();
        
        String[] testParams = {"-c", "black, green", "-u", "M", "--debug"}; // TODO: replace testParams with args
        //String[] testParams = {"-b", "5", "-c", "blue, red, orange", "-u", "K", "--debug"}; // TODO: replace testParams with args
        //String[] testParams = {"-v", "300M", "-t", ".5", "-p", "100"};
        //String[] testParams = {"--help"};
        //String[] testParams = {};

        if(testParams.length != 0) {  // TODO: replace testParams with args
            Args paramArgs = new Args();
            JCommander jc = new JCommander(paramArgs);
            jc.newBuilder().addObject(paramArgs).build().parse(testParams); // TODO: replace testParams with args

            // Get number of color bands to calculate
            // 3 (default) - 6 (max)
            // Ex. 'java -jar ResistCalc -b 5' sets band value to 5
            int bandsVal = paramArgs.getBands();
            
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
                System.out.println("Help Ind        : " + debugInd);
                System.out.println("");
            }

            if(!helpInd) {
                Resistor r1 = new Resistor();
                /*
                if(colorsList.size() > 0) {
                    if(debugInd) {
                        System.out.println("Bands (pre-calc) ->" + bandsVal);
                        System.out.println("Colors (pre-calc) ->" + colorsList);
                        System.out.println("Units (pre-calc) ->" + unitsVal);
                        System.out.println("Debug Ind (pre-calc) ->" + debugInd);
                    }

                    //for(int idx = 0; idx < colorsList.size(); idx++) {
                    //    System.out.println(colorsList.get(idx));
                   //}
                    //String output = r1.translateToValue(bandsVal, colorsList, unitsVal, debugInd);
                    
                    //System.out.println("Hello There!");
                    
                } else if(resistanceVal.length() > 0) {
                    System.out.println("Resistance Value ->" + resistanceVal);
                    System.out.println("Tolerance Value ->" + toleranceVal);
                    System.out.println("PPM Value ->" + ppmVal);
                    
                } else {
                    rCalc.helpTextShort();
                }
                */
            } else {
                rCalc.helpTextLong();
            }
            
        } else {
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
    } // end helpText
}
