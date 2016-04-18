#include <inttypes.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

import "i_receiver";
import "i_sender";

/**
 * Squeeze
 */
behavior Squeeze(i_receiver stateData, i_sender dataToMonitor)
{
	// State variable
	uint64_t S[5][5];

	/**
	 * Squeeze step of the SHA3 algorithm
	 */
	void main (void)
	{
		const int32_t r = 72;
		const int32_t w = 8;
		int32_t b, x, y;
		uint64_t Z [8];
  		b = 0;
  		
		while (1)
		{
			// Receive State from absorb
			for(y = 0; y < 5; y++)
			{
				for(x = 0; x < 5; x++)
				{
					stateData.receive(&S[x][y], (int)sizeof(uint64_t));
				}  // end for x
      			
			}  // end for y
			printf("SQUEEZE::Got data...\n");
  		
			while(b < 8)
			{
				for(y = 0; y < 5; y++)
				{
					for(x = 0; x < 5; x++)
					{
						if((x + 5 * y) < (r / w))
						{
							*(Z + b) ^= S [x][y];
							b++;
						}
					}  // end for x
    			
				}  // end for y
  			
			}  // end while
 		
			// Send result, Z to the monitor
			printf("SQUEEZE::Sending data to monitor...\n");
			for(x = 0; x < 8; x++)
			{
				dataToMonitor.send(&Z[x], (int)sizeof(uint64_t));
			}
			
		} // end while 1
		
	}  // end void main void
	
};  // end behavior