#include <inttypes.h>
#include <stdlib.h>
#include <string.h>

/**
 * Stimulus
 */
behavior Stimulus()
{
	/**
	 * Pad input data in preparation
	 */
	uint8_t *padding(uint8_t* M, int32_t* S)
	{
  		int32_t i, j;
  		int32_t newS;
  		uint8_t *nM;
  		
  		i=*S;
  		newS=(*S+72-(*S%72));
  		
  		nM=(uint8_t*)malloc(*S+(72-(*S%72)));
  		/*Copy string*/
  		for(j=0;j<*S;j++)
  		{
    		*(nM+j)=*(M+j);
  		}
  		*(nM+i)=0x01;
  		i++;
  		while(i<(newS-1))
  		{
    		*(nM+i)=0x00;
    		i++;
  		}
  		*(nM+i)=0x80;
  		i++;
  		*S=i;
  		return nM;
	}

	/**
	 * Main behavior
	 * The starting point for the Stimulus behavior
	 */
	void main (void)
	{
		const char * dataStr = "helloworld";
		int32_t size;
		int32_t r;
  		int32_t w;
  		uint8_t* M;
	
		// Load inputs from file
		// todo
		
		size=strlen(dataStr);
		M = (uint8_t*)dataStr;
		
		// Pad the input if required
		r=72;
  		w=8;
  		/*Padding*/
  		if((size%r)!=0)
  		{
    		M=padding(M,&size);
  		}
		
		// Send to the design
		// todo
		
	}  // end void main void
	
};  // end behavior