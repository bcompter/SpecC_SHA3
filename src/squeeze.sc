#include <inttypes.h>
#include <stdlib.h>
#include <string.h>

/**
 * Squeeze
 */
behavior Squeeze()
{
	/**
	 * Squeeze step of the SHA3 algorithm
	 */
	void main (void)
	{
		// Input State S, fix later
		uint64_t **S;
		const int32_t r = 72;
		const int32_t w = 8;
	
		int32_t b, x, y;
		uint64_t *Z;
  		b = 0;
  		Z = (uint64_t *)calloc(8, sizeof(uint64_t));
  		while(b < 8)
  		{
  			for(y = 0; y < 5; y++)
  			{
    			for(x = 0; x < 5; x++)
    			{
      				if((x+5*y) < (r/w))
      				{
						*(Z+b) ^= S [x][y];
						b++;
      				}
    			}  // end for x
    			
  			}  // end for y
  			
 		}  // end while
 		
 		// Send result, Z to the monitor
 		// todo
 		
	}  // end void main void
	
};  // end behavior