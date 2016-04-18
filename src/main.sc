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

import "c_queue";

/**
 * Main application behavior
 * The starting point for our simulation which includes our stimulus, design, and monitor
 */
behavior Main
{
	// Communications
	const unsigned long qSize = 1024;
	c_queue dataToDesign(qSize);
	c_queue dataToMonitor(qSize);
	bool isDone;

	// Behaviors
	Stimulus stimulus(dataToDesign, isDone);
	Design design(dataToDesign, dataToMonitor);
	Monitor monitor(dataToMonitor, isDone);

	// Main application entry point
	int main(void)
	{
		isDone = false;
		
		// Execute all of the following behaviors in parallel
		par 
		{
			stimulus;
			design;
			monitor;
		};
		
		return 0;
		
	}  // end int main void
	
};  // end behavior