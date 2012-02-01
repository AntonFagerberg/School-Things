/* This is my first C-app so it might contain weird stuff.
 * By Anton Fagerberg [ anton@antonfagerberg.com ]
 * No license since it is not useful.
 */
#include <stdio.h>
#include "Header.h"

int multiplication(int factor1, int factor2) {
	int result = 0;
	int shift_bit = 1;
	int bits = 16;

	int i;
	for (i = 0; i < bits - 1; i++) {
		if (factor2 & shift_bit) {
			result = result + factor1;
		}
		
		factor1 = factor1 << 1;
		shift_bit = shift_bit << 1;
	}
	
	if (factor2 & shift_bit) {
		result = result - factor1;
	}
	
	return result;
}

int division(int dividend, int divisor) {
	int result = 0;
	int bits = 16;
	
	divisor = divisor << bits;
	
	int i;
	for (i = 0; i < bits; i++) {
		divisor = divisor >> 1;
		dividend = dividend - divisor;
		
		if (dividend < 0) {
			dividend = dividend + divisor;
			result = result << 1;
		} else {
			result = result << 1;
			result = result + 1;
		}
	}
	
	return result;
}

int main (int argc, const char * argv[])
{
	int a = 37;
	int b = 13;
	printf("Multiplication %d * %d = %d (%d)\n", a, b, multiplication(a, b), a * b);
	printf("Division %d / %d = %d (%d)\n", a, b, division(a, b), a / b);
}

