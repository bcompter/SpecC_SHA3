#include <inttypes.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

import "i_receiver";

/**
 * Monitor
 */
behavior Monitor(i_receiver dataFromSqueeze)
{
	/**
	 * Main behavior
	 * The starting point for the Monitor behavior
	 */
	void main (void)
	{
		int x, i;
		uint8_t * p;
		uint64_t Z [8];
	
		// Receive output
		printf("MONITOR::Receiving data...\n");
      	for(x = 0; x < 8; x++)
      	{
			dataFromSqueeze.receive(&Z[x], (int)sizeof(uint64_t));
      	}
		
		// Write to file and check our answer
 		p = (uint8_t *) &Z;
 		printf("FINAL ANSWER\n");
 		for (i = 0; i < 64; i++)
 		{
 			printf("%02X", p[i]);
 		}
 		printf("\n\n");
		
	}  // end void main void
	
};  // end behavior