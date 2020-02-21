# ResistorCalc-CLI

ResistCalc-CLI is a command-line application for calculating the electrical resistance of axial-lead (through hole) resistors from a given color code.  ResistCalc-CLI will also attempt to calculate the color band values based on a given resistance value, tolerance and/or temperature coefficient.

## If you aren't familiar with resistors...
[Resistors](https://en.wikipedia.org/wiki/Resistor) are commonly used in just about every electronic device you own. Resistors come in different forms but are generally meant to reduce the amount of current flow in a given electrical network or circuit.  As described above, this command-line application is meant for axial-lead (or through hole) resistors that are commonly made with color bands painted around the outer coating.  These color bands communicate to an engineer the resistance value that the resistor is rated for in [ohms](https://en.wikipedia.org/wiki/Ohm%27s_law).  Depending on the number of bands and the combination of colors will determine the resistance, tolerance and temperature coefficient for a resistor.

## How it works...
As noted above, a resistors resistance value can be calculated by reading the color bands painted on the outer coating. 

![enter image description here](https://github.com/baphil8649/ResistCalc-CLI/blob/master/images/resistor-band-color-reference.png)