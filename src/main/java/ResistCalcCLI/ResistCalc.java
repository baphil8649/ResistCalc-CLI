package ResistCalcCLI;

import com.beust.jcommander.JCommander;

import java.util.ArrayList;

public class ResistCalc {

    public static void main(String[] args) {
        ResistCalc rCalc = new ResistCalc();
        
        String[] testParams = {"-c", "black green", "-u", "M"};
        //String[] testParams = {"-b", "5", "-c", "blue red orange", "-u", "K"};
        //String[] testParams = {"-v", "300M", "-t", ".5", "-p", "100"};
        //String[] testParams = {"--help"};
        //String[] testParams = {};

        if(testParams.length != 0) {
            Args paramArgs = new Args();
            JCommander jc = new JCommander(paramArgs);
            jc.newBuilder().addObject(paramArgs).build().parse(testParams);

            int bandsVal = paramArgs.getBands();
            ArrayList<String> colorsList = new ArrayList<String>(paramArgs.getColors());
            boolean debugInd = paramArgs.getDebug();
            boolean helpInd = paramArgs.getHelp();
            int ppmVal = paramArgs.getPPM();
            double toleranceVal = paramArgs.getTolerance();
            String unitsVal = paramArgs.getUnits();
            String resistanceVal = paramArgs.getResistance();
            
            if(!helpInd) {
                Resistor r1 = new Resistor();
                
                if(colorsList.size() > 0) {
                    if(debugInd) {
                        System.out.println("Bands (pre-calc) ->" + bandsVal);
                        System.out.println("Colors (pre-calc) ->" + colorsList);
                        System.out.println("Units (pre-calc) ->" + unitsVal);
                        System.out.println("Debug Ind (pre-calc) ->" + debugInd);
                    }

                    String output = r1.translateToValue(bandsVal, colorsList, unitsVal, debugInd);
                    System.out.println("Hello There!");
                    
                } else if(resistanceVal.length() > 0) {
                    System.out.println("Resistance Value ->" + resistanceVal);
                    System.out.println("Tolerance Value ->" + toleranceVal);
                    System.out.println("PPM Value ->" + ppmVal);
                    
                    
                } else {
                    rCalc.helpText();
                }
                
            } else {
                rCalc.helpText();
            }
            
        } else {
            System.out.println("Usage: resistcalc [OPTION]... [VALUE]...\n"
                             + "Try 'resistcalc --help' for more information.");
        } // end main if
    } // end main method
    
    public void helpText() {
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
