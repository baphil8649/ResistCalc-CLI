# ResistorCalc-CLI

ResistCalc-CLI is a command-line application for calculating the electrical resistance of axial-lead (through-hole) resistors from a given color code.  ResistCalc-CLI will also attempt to calculate the color band values based on a given resistance value, tolerance and/or temperature coefficient.

## If you aren't familiar with resistors...
[Resistors](https://en.wikipedia.org/wiki/Resistor) are commonly used in just about every electronic device you own. Resistors come in different forms but are generally meant to reduce the amount of current flow in a given electrical network or circuit.  As described above, this command-line application is meant for axial-lead resistors that are commonly made with color bands painted around the outer coating.  These color bands communicate to an engineer the resistance value that the resistor is rated for in [ohms](https://en.wikipedia.org/wiki/Ohm%27s_law).  Depending on the number of bands and the combination of colors will determine the resistance, [tolerance](https://en.wikipedia.org/wiki/Engineering_tolerance#Electrical_component_tolerance) and [temperature coefficient](https://en.wikipedia.org/wiki/Temperature_coefficient) for a resistor.

## How it works...
As noted above, a resistor's resistance value can be calculated by reading the color bands painted on the outer coating. Each color can represent a number, multiplier, tolerance and/or temperature coefficient value. The table below defines each possible band color and the different values corresponding to them.

<p align="center">
  <img src="https://github.com/baphil8649/ResistCalc-CLI/blob/master/images/resistor-band-color-reference.png">
</p>

When reading resistor bands, the type of value represented will change depending on the number of color bands applied to the resistor.  The most common resistors usually have between three and six bands.  Below is another table that defines how each color band should be defined based on how many bands are given and ultimately tells us how to translate the band colors into an actual resistance value.

<p align="center">
  <img src="https://github.com/baphil8649/ResistCalc-CLI/blob/master/images/resistor-band-number-reference.png">
</p>

This is how ResistCalc-CLI will calculate the resistor value for a given number of color bands.

For example, if given a set of resistance bands with the values `Orange, Grey, Blue, Green, Blue and Brown` you would translate them the following way:
|Orange|Grey|Blue|Green|Blue|Brown|=|
|------|----|----|-----|----|-----|-|
|3     |8   |6   |x100k|+/-0.25|100ppm/K|38600000 Ohms +/-0.25 100ppm/K|


ResistCalc-CLI will calculate a resistance if only one or two bands are given, but such a resistor is not considered practical or may not be found "on the shelf" for purchase.

As a bonus for this project, ResistCalc-CLI can also translate a resistance value to color bands.  This feature is less practical since it assumes a five band resistor for all cases with the exception of a 0 ohm resistor (which is a [thing](https://en.wikipedia.org/wiki/Zero-ohm_link) believe it or not).  In reality, a resistance value could have more than one set of color bands to represent that value but was outside the scope of this project (for now).

## How to install...
ResistCalc-CLI will run on Windows, macOS and Linux operating systems as long as the Java Runtime Environment of at least 1.8 (or Java 8) is installed. Start by downloading the zip file from the distributions directory [here](https://github.com/baphil8649/ResistCalc-CLI/tree/master/build/distributions) to any folder and unzip the files.  Once unzipped, navigate down to the `\ResistCalc-CLI\bin` directory from Windows PowerShell or terminal emulator in macOS and Linux.  From here you can execute ResistCalc-CLI executable batch file (.bat) or shell script (no file extension).

![enter image description here](https://github.com/baphil8649/ResistCalc-CLI/blob/master/images/bash-example.png)

> If running from a Unix or Linux shell only (with no GUI) you will need to clone the repository locally using Git in order to download the zip file. Git can be installed using your distributions repository (apt, yum, pacman, etc)

## Usage...
#### SYNOPSIS
    ResistCalc-CLI [OPTION] VALUE(s)...
#### OPTIONS
`--debug`	Displays debug information while executing a given command.

`--help`	Outputs a detailed usage message and exits.  Overrides all other options listed.

`-c,  --color-bands`	Translate resistance from color bands.  Allows up to six bands in a comma delimited list.  Overrides translating color codes from resistance (`-r`) if both commands given.

`-r,  --resistance-value`	Translate color band codes from resistance in ohms.  As a disclaimer, this assumes a five band resistor with a default tolerance of +/-1% (BROWN) and may not be a piratical for real-world application.  A sixth band can be applied by defining a temp. coefficient value (`-tc`).

`-t,  --tolerance`	Tolerance value of resistor when translating color bands from resistance (`-r`).

`-tc, --temp-coefficient`	Temperature coefficient value of resistor when translating color bands from resistance (`-r`).

`-u,  --units`	Formats the the resistance to a standard unit of measure when translating resistance from color bands (`-c`). Acceptable values are:
|Value|Description|
|-----|-----------|
|O    |Ohms (default)|
|K    |Kilo-ohms  |
|M    |Mega-ohms  |
|G    |Giga-ohms  |
#### EXAMPLES
Entering: `ResistCalc-CLI -c red,green,blue`
Outputs: `25000000 Ohms`

Entering:`ResistCalc-CLI -c orange,grey,blue,green,blue,brown -u M`
Outputs:`38.6M Ohms +/-0.25% 100ppm/K`

Entering:`ResistCalc-CLI -r 38600000 -t .25 -tc 100`
Outputs:`ORANGE,GREY,BLUE,GREEN,BLUE,BROWN`

## Credit where credit is due...
This project was challenging and educational and a big part of it's success was finding and using the [JCommander](http://jcommander.org/) library by C�dric Beust.

This project was created using [Apache Netbeans](https://netbeans.apache.org/) (v11.1)