#include <inttypes.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

import "i_sender";

/**
 * Stimulus
 */
behavior Stimulus(i_sender dataToDesign, int finalCount)
{
	/**
	 * Pad input data in preparation
	 */
	uint8_t *padding(uint8_t* M, int32_t* S)
	{
  		int32_t i, j;
  		int32_t newS;
  		uint8_t *nM;
  		
  		i = *S;
  		newS=(*S+72-(*S%72));
  		nM=(uint8_t*)malloc(*S+(72-(*S%72)));
  		
  		/* Copy string */
  		for(j=0; j<*S; j++)
  		{
    		*(nM+j)=*(M+j);
  		}
  		*(nM+i) = 0x01;
  		i++;
  		while(i < (newS-1))
  		{
    		*(nM+i) = 0x00;
    		i++;
  		}
  		*(nM+i) = 0x80;
  		i++;
  		*S = i;
  		return nM;
	}

	/**
	 * Main behavior
	 * The starting point for the Stimulus behavior
	 */
	void main (void)
	{
		int32_t size, size2;
		int32_t r;
  		int32_t w;
  		uint8_t* M;
		FILE* fp;
		char strInput [72];
		int count;
	
		count = 0;
		
		// Load inputs from file
		fp = fopen("input.txt", "r");
		if (fp == NULL)
		{
			printf("STIMULUS::Input file open failed!\n");
			exit(0);
		}
		
		// Loop through all input strings to hash
		while((fgets(strInput, 72, fp)) != NULL)
		{
			// Remove newlines and carriage returns
			strtok(strInput, "\n");
			strtok(strInput, "\r");
			
			size = strlen(strInput);
			M = (uint8_t*)strInput;
		
			// Pad the input if required
			r = 72;
			w = 8;
			if((size % r) != 0)
			{
				M = padding(M, &size);
			}
		
			// Send M and size to the design
			printf("STIMULUS::Sending data to design...\n");
			dataToDesign.send(M, size);
			count++;
		}
		
		// Send end signal to monitor
		finalCount = count;

	}  // end void main void
	
};  // end behavior