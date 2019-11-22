package ResistCalcCLI;

import java.util.ArrayList;

public class Resistor {
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
    public String translateToValue(int bands, ArrayList colors, String units, boolean debug) {
        String resistance = "";
        
        switch (bands) {
            
        }
        
        return resistance;
    }
    
    public ArrayList translateToColors(String value, String units, double tolerance, int pmm, boolean debug) {
        ArrayList<String> colors = new ArrayList<String>();
        
        return colors;
    }
}
