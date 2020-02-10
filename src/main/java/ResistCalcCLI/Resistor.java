package ResistCalcCLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.math.BigDecimal;

enum eBands {
/*
    Color Codes
    Color       Value       Multiplier      Tolerance       Temp. Coefficient
    -------------------------------------------------------------------------
    BLACK       0           1 ohms                          250ppm/K
    BROWN       1           10 ohms         +/-1%           100ppm/K
    RED         2           100 ohms        +/-2%           50ppm/K
    ORANGE      3           1K ohms                         15ppm/K
    YELLOW      4           10K ohms                        25ppm/K
    GREEN       5           100K ohms       +/-0.5%         20ppm/K
    BLUE        6           1M ohms         +/-0.25%        10ppm/K
    VIOLET      7           10M ohms        +/-0.10%        5ppm/K
    GREY        8           100M ohms       +/-0.05%        1ppm/K
    WHITE       9           1G ohms
    GOLD                    0.10 ohms       +/-5%
    SILVER                  0.01 ohms       +/-10%
*/
    
    BLACK ("0",     1.0,              0.0,    250),
    BROWN ("1",     10.0,             1.0,    100),
    RED   ("2",     100.0,            2.0,    50),
    ORANGE("3",     1000.0,           0.0,    15),
    YELLOW("4",     10000.0,          0.0,    25),
    GREEN ("5",     100000.0,         0.5,    20),
    BLUE  ("6",     1000000.0,        0.25,   10),
    VIOLET("7",     10000000.0,       0.10,   5),
    GREY  ("8",     100000000.0,      0.05,   1),
    WHITE ("9",     1000000000.0,     0.0,    0),
    GOLD  ("A",     0.10,             5,      0),
    SILVER("B",     0.01,             10,     0);
    
    private String value;
    private double multiplier;
    private double tolerance;
    private int tempCoef;
    
    public String getValue() {
        return this.value;
    }
    
    public double getMultiplier() {
        return this.multiplier;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public int getTempCoef() {
        return this.tempCoef;
    }
    
    private eBands(String value, double multiplier, double tolerance, int tempCoef) {
        this.value = value;
        this.multiplier = multiplier;
        this.tolerance = tolerance;
        this.tempCoef = tempCoef;
    }
}

public class Resistor {
    // HashMaps used for calculating resistance from color codes...
    HashMap<String, String> colorValue = new HashMap<>();
    HashMap<String, Double> colorMultiplier = new HashMap<>();
    HashMap<String, Double> colorTolerance = new HashMap<>();
    HashMap<String, Integer> colorTempCoef = new HashMap<>();
    
    // HashMaps used for calculating color codes form resistance...
    HashMap<Character, String> valueColor = new HashMap<>();
    HashMap<Double, String> multiplierColor = new HashMap<>();
    HashMap<Double, String> toleranceColor = new HashMap<>();
    HashMap<Integer, String> tempCoefColor = new HashMap<>();
    
    // Array of valid colors
    ArrayList<String> bandValues = new ArrayList<>();
    
    public Resistor() {
        bandValues.add("BLACK");
        bandValues.add("BROWN");
        bandValues.add("RED");
        bandValues.add("ORANGE");
        bandValues.add("YELLOW");
        bandValues.add("GREEN");
        bandValues.add("BLUE");
        bandValues.add("VIOLET");
        bandValues.add("GREY");
        bandValues.add("WHITE");
        bandValues.add("GOLD");
        bandValues.add("SILVER");
        
        for(eBands band : eBands.values()) {
            colorValue.put(band.toString(), band.getValue());
            colorMultiplier.put(band.toString(), band.getMultiplier());
            colorTolerance.put(band.toString(), band.getTolerance());
            colorTempCoef.put(band.toString(), band.getTempCoef());
            
            valueColor.put(band.getValue().charAt(0), band.toString()); // OMG that's so ugly...but imma do it!
            multiplierColor.put(band.getMultiplier(), band.toString());
            toleranceColor.put(band.getTolerance(), band.toString());
            tempCoefColor.put(band.getTempCoef(), band.toString());
        }
    }
    
    public String colorsToResistance(String[] pColors, String pUnits, boolean pDebug) {    
        String baseVal = "";
        double baseNum = 0.0;
        double res = 0.0;
        double multiplier = 1.0;
        double tolerance = 0.0;
        int tempCoef = 0;
        String resistance = ""; // Final returned value
        
        for(int idx = 0; idx < pColors.length; idx++) {

            // Breakout if one of the bands is NULL
            if(pColors[idx] == null) {
                resistance = "ERROR: Color band (" + (idx+1) + ") is missing.";
                break;
            }
            
            // Breakout if one of the bands is not a valid color code...
            //if(!validateColor(pColors[idx])) {
            if(!bandValues.contains(pColors[idx])) {
                resistance = "ERROR: Color band (" + (idx+1) + ") has invalid color code: " + pColors[idx] + ".";
                break;
            }

            // Translate FIRST color band...
            if(idx == 0) {
                if((pColors[idx].equals("GOLD")) || (pColors[idx].equals("SILVER"))) {
                    resistance = "ERROR: Color band (" + (idx+1) + ") cannot be GOLD or SILVER.";
                    break;
                } else {
                    baseVal = colorValue.get(pColors[idx]);
                }
            }
            
            // Translate SECOND color band...
            if(idx == 1) {
                if((pColors[idx].equals("GOLD")) || (pColors[idx].equals("SILVER"))) {
                    resistance = "ERROR: Color band (" + (idx+1) + ") cannot be GOLD or SILVER.";
                    break;
                } else {
                    baseVal = baseVal + colorValue.get(pColors[idx]);
                }
            }

            // Translate THIRD color band...
            if(idx == 2) {
                if(pColors.length <= 4) {
                    multiplier = colorMultiplier.get(pColors[idx]);
                } else if(pColors.length > 4) {
                    baseVal = baseVal + colorValue.get(pColors[idx]);
                }
            }
            
            // Translate FOURTH color band...
            if(idx == 3) {
                if(pColors.length == 4) {
                    tolerance = colorTolerance.get(pColors[idx]);
                    
                    if(tolerance == 0.0) {
                        resistance = "ERROR: Color band (" + (idx+1) + ") is not a valid tolerance value: " +  pColors[idx];
                    }
                } else if(pColors.length > 4) {
                    multiplier = colorMultiplier.get(pColors[idx]);
                }
            }
            
            // Translate FIFTH color band...
            if(idx == 4) {
                tolerance = colorTolerance.get(pColors[idx]);
                
                if(tolerance == 0.0) {
                    resistance = "ERROR: Color band (" + (idx+1) + ") is not a valid tolerance value: " +  pColors[idx];
                }
            }
            
            // Translate SIXTH color band...
            if(idx == 5) {
                tempCoef = colorTempCoef.get(pColors[idx]);
                
                if(tempCoef == 0) {
                    resistance = "ERROR: Color band (" + (idx+1) + ") is not a valid temp. coefficient value: " +  pColors[idx];
                }
            }
        }
        
        // Resistance Value
        if(resistance.equals("")) {
            baseNum = Double.parseDouble(baseVal);
            
            res = baseNum * multiplier;
            
            if(pUnits.equals("K")) { // Return in Kiloohms
                res /= 1000.0;
                resistance = BigDecimal.valueOf(res).toPlainString() + "K Ohms";
                
            } else if(pUnits.equals("M")) { // Return in Megaohms
                res /= 1000000.0;
                resistance = BigDecimal.valueOf(res).toPlainString() + "M Ohms";
                
            } else if(pUnits.equals("G")) { // Return in Gigaohms
                res /= 1000000000.0;
                resistance = BigDecimal.valueOf(res).toPlainString() + "G Ohms";
                
            } else { // Return in Ohms
                resistance = BigDecimal.valueOf(res).toPlainString() + " Ohms";
            }
            
            // Tolerance
            if(tolerance != 0.0) {
                resistance = resistance + " +/-" + tolerance + "%";
            }
          
            // Temp. Coefficient
            if(tempCoef != 0) {
                resistance = resistance + " " + tempCoef + "ppm/K";
            }
        }

        if(pDebug) {
            System.out.println("- Color Bands Translation -");
            System.out.println("---------------------------");
            System.out.println("Band Colors (Param)  : " + Arrays.toString(pColors));
            System.out.println("Units (Param)        : " + pUnits);
            System.out.println("Base Value (String)  : " + baseVal);
            System.out.println("Base Number (Double) : " + baseNum);
            System.out.println("Multiplier           : " + multiplier);
            System.out.println("Tolerance            : " + tolerance);
            System.out.println("Temp. Coefficient    : " + tempCoef);
            System.out.println("Resistance           : " + resistance);
            System.out.println("");
        } // ...end debug arguments
        
        return resistance;
    } // end of colorsToResistance

    public String resistanceToColors(String pResistance, double pTolerance, int pTempCoef, boolean pDebug) {
        String colors = "";
        double resistanceNum = 0.0;
        double multiplierNum = 1000000000.0;
        double baseNum = 0.0;
        String baseVal = "";
        String band1 = "";
        String band2 = "";
        String band3 = "";
        String multiplierBand = "";
        String toleranceBand = "";
        String tempCoefBand = "";
        
        if(!pResistance.equals("NOTNUMERIC")) {
            
            try { // Translate resistnace string to double value...
                resistanceNum = Double.parseDouble(pResistance);

            } catch (Exception e) {
                colors = "ERROR:Unable to read resistance value as numeric.";
            } // end JCommander try
        
            // Check if the resistance value is zero; single BLACK band resistor
            if(resistanceNum == 0.0) {
                colors = "BLACK";
                multiplierNum = resistanceNum;
            }
        
            // Find multiplier value...
            while(multiplierNum >= 1.0) {
                if((resistanceNum % multiplierNum) == 0.0) {
                    baseNum = resistanceNum / multiplierNum;
                    break;
                
                } else {
                    multiplierNum /= 10.0;
                }
            } // end of multiplier while
        
            if(baseNum > 999.0) { // Ensure that base number is not over three digits long
                colors = "ERROR:Non-practical resistor value for calculating band colors.";
            
            } else if(baseNum == 1.0) { // Where resistance value is eqaul to a multiplier (10000, 1000, 10, etc)
                baseVal = BigDecimal.valueOf(resistanceNum).toPlainString();
            
            } else if((baseNum > 10.0) && (baseNum < 100.0)) { // Adjust base number and multiplier for values less than 100
                baseNum *= 10.0;
                multiplierNum /= 10.0;
                baseVal = BigDecimal.valueOf(baseNum).toPlainString();
                
            } else if((baseNum > 1.0) && (baseNum < 10.0)) { // Adjust base number and multiplier for values less than 10
                baseNum *= 100.0;
                multiplierNum /= 100.0;
                baseVal = BigDecimal.valueOf(baseNum).toPlainString();
                
            } else { // All other standard resistance values
                baseVal = BigDecimal.valueOf(baseNum).toPlainString();
            }

            if(resistanceNum == multiplierNum) { // Same as baseNum == 1.0 scenario
                multiplierBand = multiplierColor.getOrDefault((multiplierNum / 100.0), "");
            } else {
                multiplierBand = multiplierColor.get(multiplierNum);
            }

            // Translate to color bands if no errors have been found
            if(colors.equals("")) {
                // Translate bands 1, 2, and 3
                band1 = valueColor.getOrDefault(baseVal.charAt(0), "BLACK");
                band2 = valueColor.getOrDefault(baseVal.charAt(1), "BLACK");
                band3 = valueColor.getOrDefault(baseVal.charAt(2), "BLACK");

                // Translate tolerance band (defaults to BROWN or +/-1%)
                if(pTolerance != 0.0) {
                    toleranceBand = toleranceColor.getOrDefault(pTolerance, "BROWN");
                } else {
                    toleranceBand = "BROWN";
                }
            
                // Translate temp. coefficient band
                if(pTempCoef != 0) {
                    tempCoefBand = tempCoefColor.getOrDefault(pTempCoef, "");
                }
            
                // Build color bands
                colors = band1 + "," + band2 + "," + band3 + "," + multiplierBand + "," + toleranceBand;
            
                // Add temp. coefficient band if valid
                if(!tempCoefBand.equals("")) {
                    colors += "," + tempCoefBand;
                }
            }
        } else {
            colors = "ERROR:Non-numeric resistance value passed.";
        }
                
        if(pDebug) {
            System.out.println("- Resistance Value Translation -");
            System.out.println("--------------------------------");
            System.out.println("Base Resistance (Param)    : " + pResistance);
            System.out.println("Tolerance (Param)          : " + pTolerance);
            System.out.println("Temp. Coefficient (Param)  : " + pTempCoef);
            System.out.println("Resistance Number          : " + resistanceNum);
            System.out.println("Base Number (Double)       : " + baseNum);
            System.out.println("Base Value (String)        : " + baseVal);
            System.out.println("Multiplier Number (Double) : " + multiplierNum);
            System.out.println("Band 1                     : " + band1);
            System.out.println("Band 2                     : " + band2);
            System.out.println("Band 3                     : " + band3);
            System.out.println("Multiplier Band            : " + multiplierBand);
            System.out.println("Tolerance Band             : " + toleranceBand);
            System.out.println("Temp. Coefficient Band     : " + tempCoefBand);
            System.out.println("Colors                     : " + colors);
            System.out.println("");
        } // ...end debug arguments

        return colors;
    } // end of resistanceToColors
}