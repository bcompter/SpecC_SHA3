#include <inttypes.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

import "i_receiver";

/**
 * Monitor
 */
behavior Monitor(i_receiver dataFromSqueeze, int finalCount)
{
	/**
	 * Main behavior
	 * The starting point for the Monitor behavior
	 */
	void main (void)
	{
		FILE* fp;
		int x, i, count;
		uint8_t * p;
		uint64_t Z [8];

		count = 0;
		
		// Open output file to hold results
		fp = fopen("output.txt", "w");
		if (fp == NULL)
		{
			printf("MONITOR::Output file open failed!\n");
			exit(0);
		}
		
		while (1)
		{
			// Receive output
			printf("MONITOR::Receiving data...\n");
			for(x = 0; x < 8; x++)
			{
				dataFromSqueeze.receive(&Z[x], (int)sizeof(uint64_t));
			}
		
			// Write to file to check our answer
			printf("Answer %d: ", count);
			p = (uint8_t *) &Z;
			for (i = 0; i < 64; i++)
			{
				fprintf(fp, "%02X", p[i]);
				printf("%02X", p[i]);
			}
			fprintf(fp, "\n");
			printf("\n");
			count++;
			
			if (finalCount != 0 && count == finalCount)
			{
				exit(0);
			}
		}  // end while 1
		
	}  // end void main void
	
};  // end behavior