import "absorb";
import "squeeze";

/**
 * Design
 */
behavior Design()
{
	// Behaviors
	Absorb absorb;
	Squeeze squeeze;

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
		};
	
	}  // end void main void
	
};  // end behavior