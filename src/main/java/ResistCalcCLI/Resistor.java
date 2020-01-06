package ResistCalcCLI;

import java.util.ArrayList;

enum eBands {
    /*
    General Colors (colors, value, multiplier, tolerance, ppm)
    - Black   (0)     1 ohms                  250ppm/K
    - Brown   (1)     10 ohms     +/-1%       100ppm/K
    - Red     (2)     100 ohms    +/-2%       50ppm/K
    - Orange  (3)     1K ohms                 15ppm/K
    - Yellow  (4)     10K ohms                25ppm/K
    - Green   (5)     100K ohms   +/-0.5%     20ppm/K
    - Blue    (6)     1M ohms     +/-0.25%    10ppm/K
    - Violet  (7)     10M ohms    +/-0.10%    5ppm/K
    - Grey    (8)     100M ohms   +/-0.25%    1ppm/K
    - White   (9)     1G ohms
    - Gold            0.10 ohms   +/-5%
    - Silver          0.01 ohms   +/-10%
    */
    
    BLACK("0", 1, 0.0, 250),
    BROWN("1", 10, 1.0, 100),
    RED("2", 100, 2.0, 50),
    ORANGE("3", 1000, 0.0, 15),
    YELLOW("4", 10000, 0.0, 25),
    GREEN("5", 100000, 0.5, 20),
    BLUE("6", 1000000, 0.25, 10),
    VIOLET("7", 10000000, 0.10, 5),
    GREY("8", 100000000, 0.25, 1),
    WHITE("9", 1000000000, 0.0, 0),
    GOLD("0", 0.10, 5, 0),
    SILVER("0", 0.01, 10, 0);
    
    private String value;
    private double multiplier;
    private double tolerance;
    private int pmm;
    
    public String getValue() {
        return this.value;
    }
    
    public double getMultiplier() {
        return this.multiplier;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public int getPmm() {
        return this.pmm;
    }
    
    private eBands(String value, double multiplier, double tolerance, int pmm) {
        this.value = value;
        this.multiplier = multiplier;
        this.tolerance = tolerance;
        this.pmm = pmm;
    }
}

public class Resistor {
    public String translateToValue(int bands, String b1, String b2, String b3, String units, boolean debug) {
        String baseStrVal = "";
        double multiplier;
        String resistance = "";


        switch (bands) {
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }

        
        
        return resistance;
    }
    
    public ArrayList translateToColors(String value, String units, double tolerance, int pmm, boolean debug) {
        ArrayList<String> colors = new ArrayList<String>();
        
        return colors;
    }
}
