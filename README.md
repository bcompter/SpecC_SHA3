# SpecC_SHA3

This project implements a SHA3-512 algorithm in SpecC.

Credit to Fackelmann/SHA3 for the reference implementation used to convert.

## Compiling ##
The reference code can be compiled simply by using make inside the ref directory.
The code may be used by executing the program with the message to be hashed as an argument.

The src directory holds the SpecC code and requires a properly configured SpecC development system.  Most (but not all) computers on the NEU COE server list should be compatible.  Ergs is confirmed working.  Some workstations are missing a 32 bit gnu header file which throws an error when compiling.

* ssh username@gateway.coe.neu.edu
* ssh servername -p 27
* source /ECEnet/Apps1/sce/20121009/bin/setup.sh

After this, then simple invoke make within the src directory to compile the SpecC implementation.
