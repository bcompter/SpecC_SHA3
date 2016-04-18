#include <inttypes.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

import "i_receiver";
import "i_sender";

/**
 * Absorb
 */
behavior Absorb(i_receiver dataFromStim, i_sender stateData)
{
	// State variable
	uint64_t S[5][5];

	/**
	 * r
	 */
	const uint64_t r[5][5]={ {0,36,3,41,18},
			{1,44,10,45,2},
			{62,6,43,15,61},
			{28,55,25,21,56},
			{27,20,39,8,14}
	};

	/**
	 * RC
	 */
	const uint64_t RC[24]={ 0x0000000000000001,
		       0x0000000000008082,
		       0x800000000000808A,
		       0x8000000080008000,
		       0x000000000000808B,
		       0x0000000080000001,
		       0x8000000080008081,
		       0x8000000000008009,
		       0x000000000000008A,
		       0x0000000000000088,
		       0x0000000080008009,
		       0x000000008000000A,
		       0x000000008000808B,
		       0x800000000000008B,
		       0x8000000000008089,
		       0x8000000000008003,
		       0x8000000000008002,
		       0x8000000000000080,
		       0x000000000000800A,
		       0x800000008000000A,
		       0x8000000080008081,
		       0x8000000000008080,
		       0x0000000080000001,
		       0x8000000080008008
	};
	
	/**
	 * Mod function that handles sha3 specific edge cases
	 */
	int mod (int a, int b)
	{
		int ret;
  		if(b < 0) //you can check for b == 0 separately and do what you want
    		return mod(-a, -b);
  		ret = a % b;
  		if(ret < 0)
    		ret += b;
  		return ret;
	}
	
	/**
	 * SHA3 Round
	 */
	void sha3_round(uint64_t RCi)
	{
		uint8_t x, y;
	
  		uint64_t C[5];
  		uint64_t D[5];
  		uint64_t B[5][5];

  		/* Theta step */
  		for(x = 0; x < 5; x++)
  		{
    		C[x] = S[x][0] ^ S[x][1] ^ S[x][2] ^ S[x][3] ^ S[x][4];
  		}
  		for(x=0; x<5; x++)
  		{
    		D[x] = C[(x + 4) % 5] ^ ((C[(x + 1) % 5] << 1) | (C[(x + 1) % 5] >> 63));
  		}
  		for(x=0; x<5; x++)
  		{
    		for(y=0; y<5; y++)
    		{
      			S[x][y] = S[x][y] ^ D[x];
    		}
  		}

  		/* Rho and pi steps */
  		for(x=0; x<5; x++)
  		{
    		for(y=0;y<5;y++)
    		{
      			B[y][mod((2*x+3*y),5)] = ((S[x][y] << r[x][y]) | (S[x][y] >> (64-r[x][y])));
    		}
  		}

  		/* Xi state */
  		for(x=0; x<5; x++)
  		{
    		for(y=0; y<5; y++)
    		{
      			S[x][y] = B[x][y]^((~B[mod((x+1),5)][y]) & B[mod((x+2),5)][y]);
    		}
  		}

  		/* Last step */
  		S[0][0] = S[0][0] ^ RCi;
  		
	}  // end sha3_round
	
	/**
	 * keccak function
	 */
	void keccak_f()
	{
		int32_t i;
  		for(i = 0; i < 24; i++)
  		{
    		sha3_round(RC[i]);
  		}
	}

	/**
	 * Absorb step of the SHA3 algorithm
	 */
	void main (void)
	{
		int32_t i, ii, x, y;
		const int32_t size = 72;
		const int32_t ri = 72;
		const int32_t w = 8;
		uint8_t M[size];
		uint64_t *nM;
		
		printf("ABSORB::Starting\n");
				
		// Get data from stimulus
		dataFromStim.receive(&M[0], size);
  		nM = (uint64_t *)&M[0];
  		printf("ABSORB::Got data from Stimulus...\n");
  		
  		/* Initialization */
  		for(i = 0; i < 5; i++)
  		{ 
  			for (ii = 0; ii < 5; ii++)
  			{
  				S[i][ii] = 0;
  			}
  		}
  		printf("ABSORB::Done with init\n");
  		
  		/* Each block has 72 bytes */
  		for(i = 0; i < size / ri; i++)
  		{
    		for(y = 0; y < 5; y++)
    		{
      			for(x = 0; x < 5; x++)
      			{
					if((x + 5 * y) < (ri / w))
					{
	  					S[x][y] = S[x][y] ^ *(nM + i * 9 + x + 5 * y);
					}
      			}  // end for x
      			
    		}  // end for y

    		keccak_f();
    		
  		}  // end for i
  		
  		// Send state, S to squeeze behavior
  		printf("ABSORB::Sending data to squeeze\n");
  		for(y = 0; y < 5; y++)
    	{
      		for(x = 0; x < 5; x++)
      		{
				stateData.send(&S[x][y], (int)sizeof(uint64_t));
      		}
      			
    	}  // end for y
  		
	}  // end void main void
	
};  // end behavior