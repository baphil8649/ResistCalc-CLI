/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResistCalcCLI;

import com.beust.jcommander.JCommander;

import java.util.ArrayList;
/**
 *
 * @author baphil8649 (Blake Phillips)
 */
public class ResistCalc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] testParams = {"-c", "black green"};
        Args paramArgs = new Args();
        
        JCommander jc = new JCommander(paramArgs);
        
        jc.newBuilder().addObject(paramArgs).build().parse(testParams);

        //System.out.println("Color Bands --> " + paramArgs.getColors());
        
        ArrayList<String> newArray = new ArrayList<String>(paramArgs.getColors());

        System.out.println("Color Bands --> " + newArray);
        jc.usage();
    }
}
