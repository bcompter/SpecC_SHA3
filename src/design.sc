import "absorb";
import "squeeze";

import "c_queue";
import "i_receiver";
import "i_sender";

/**
 * Design
 */
behavior Design(i_receiver dataFromStim, i_sender dataToMonitor)
{
	// Communications
	const unsigned long qSize = 1024;
	c_queue stateData(qSize);
	
	// Behaviors
	Absorb absorb(dataFromStim, stateData);
	Squeeze squeeze(stateData, dataToMonitor);

	/**
	 * Main behavior of our SHA3 algorithm
	 * The starting point for the Main behavior
	 */
	void main (void)
	{
		// Execute the absorb and squeeze behaviors in parallel
		par 
		{
			absorb;
			squeeze;
		}
	
	}  // end void main void
	
};  // end behavior