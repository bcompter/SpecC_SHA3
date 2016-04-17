/**
 * EECE 7390 Computer Hardware Security
 * SHA-3 implementation in SpecC
 * 
 * Brian Compter
 */
 
#include <stdio.h>
#include <sim.sh>
#include "main.sh"

import "stimulus";
import "design";
import "monitor";

/**
 * Main application behavior
 * The starting point for our simulation which includes our stimulus, design, and monitor
 */
behavior Main
{
	// Behaviors
	Stimulus stimulus;
	Design design;
	Monitor monitor;

	// Main application entry point
	int main(void)
	{
		// Execute all of the following behaviors in parallel
		par 
		{
			stimulus;
			design;
			monitor;
		};  // end par
		
		return 0;
		
	}  // end int main void
	
};  // end behavior