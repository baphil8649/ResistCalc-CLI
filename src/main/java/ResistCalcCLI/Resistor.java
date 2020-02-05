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
        Black       (0)         1 ohms                          250ppm/K
        Brown       (1)         10 ohms         +/-1%           100ppm/K
        Red         (2)         100 ohms        +/-2%           50ppm/K
        Orange      (3)         1K ohms                         15ppm/K
        Yellow      (4)         10K ohms                        25ppm/K
        Green       (5)         100K ohms       +/-0.5%         20ppm/K
        Blue        (6)         1M ohms         +/-0.25%        10ppm/K
        Violet      (7)         10M ohms        +/-0.10%        5ppm/K
        Grey        (8)         100M ohms       +/-0.05%        1ppm/K
        White       (9)         1G ohms
        Gold                    0.10 ohms       +/-5%
        Silver                  0.01 ohms       +/-10%
    */
    
    BLACK ("0",     1,              0.0,    250),
    BROWN ("1",     10,             1.0,    100),
    RED   ("2",     100,            2.0,    50),
    ORANGE("3",     1000,           0.0,    15),
    YELLOW("4",     10000,          0.0,    25),
    GREEN ("5",     100000,         0.5,    20),
    BLUE  ("6",     1000000,        0.25,   10),
    VIOLET("7",     10000000,       0.10,   5),
    GREY  ("8",     100000000,      0.05,   1),
    WHITE ("9",     1000000000,     0.0,    0),
    GOLD  ("A",     0.10,           5,      0),
    SILVER("B",     0.01,           10,     0);
    
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
                } else if(pColors.length > 4) {
                    multiplier = colorMultiplier.get(pColors[idx]);
                }
            }
            
            // Translate FIFTH color band...
            if(idx == 4) {
                tolerance = colorTolerance.get(pColors[idx]);
            }
            
            // Translate SIXTH color band...
            if(idx == 5) {
                tempCoef = colorTempCoef.get(pColors[idx]);
            }
        }
        
        // Resistance Value
        if(resistance.equals("")) {
            baseNum = Double.parseDouble(baseVal);
            
            res = baseNum * multiplier;
            
            if(pUnits.equals("K")) {
                res /= 1000.0;
                resistance = BigDecimal.valueOf(res).toPlainString() + "K Ohms";
                
            } else if(pUnits.equals("M")) {
                res /= 1000000.0;
                resistance = BigDecimal.valueOf(res).toPlainString() + "M Ohms";
                
            } else if(pUnits.equals("G")) {
                res /= 1000000000.0;
                resistance = BigDecimal.valueOf(res).toPlainString() + "G Ohms";
                
            } else {
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
            System.out.println("Band Colors (Param) : " + Arrays.toString(pColors));
            System.out.println("Units (Param)       : " + pUnits);
            System.out.println("Base Value          : " + baseVal);
            System.out.println("Base Number         : " + baseNum);
            System.out.println("Multiplier          : " + multiplier);
            System.out.println("Tolerance           : " + tolerance);
            System.out.println("Temp. Coefficient   : " + tempCoef);
            System.out.println("Resistance          : " + resistance);
            System.out.println("");
        } // ...end debug arguments
        
        return resistance;
    } // end of colorsToResistance

    public String resistanceToColors(String pResistance, String pUnits, double pTolerance, int pTempCoef, boolean pDebug) {
        String colors = pResistance;
        /*
        String band1 = "";
        String band2 = "";
        String band3 = "";
        String band4 = "";
        String band5 = "";
        String band6 = "";
        
        if(pResistance.length() <= 3) {
            
            for(int idx = 0; idx < pResistance.length(); idx++) {
                // First base value...
                switch(idx) {
                    case 0:
                        band1 = valueColor.get(pResistance.charAt(idx));
                        break;
                    case 1:
                        band2 = valueColor.get(pResistance.charAt(idx));
                        break;
                    case 2:
                        band3 = valueColor.get(pResistance.charAt(idx));
                }
            }
        }
        */

        if(pDebug) {
            System.out.println("- Resistance Value Translation -");
            System.out.println("--------------------------------");
            System.out.println("Base Resistance (Param)   : " + pResistance);
            System.out.println("Units (Param)             : " + pUnits);
            System.out.println("Tolerance (Param)         : " + pTolerance);
            System.out.println("Temp. Coefficient (Param) : " + pTempCoef);
            System.out.println("Band Char 1               : " + pResistance.charAt(0));
            System.out.println("Band Char 2               : " + pResistance.charAt(1));
            System.out.println("Band Char 3               : " + pResistance.charAt(2));
            //System.out.println("Band 1                    : " + band1);
            //System.out.println("Band 2                    : " + band2);
            //System.out.println("Band 3                    : " + band3);
            System.out.println("");
        } // ...end debug arguments

        return colors;
    } // end of resistanceToColors
}