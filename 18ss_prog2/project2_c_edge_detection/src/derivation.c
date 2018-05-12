#include <assert.h>
#include <math.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "convolution.h"
#include "derivation.h"
#include "image.h"
#include "math.h"

const float sobel_x[9] = {
	1, 0, -1,
	2, 0, -2,
	1, 0, -1
};

const float sobel_y[9] = {
	1, 2, 1,
	0, 0, 0,
	-1, -2, -1
};

void derivation_x_direction(float *result, float *img, int w, int h) {
	convolve(result, img, w, h, sobel_x, 3, 3);
}

void derivation_y_direction(float *result, float *img, int w, int h) {
	convolve(result, img, w, h, sobel_y, 3, 3);
}

void gradient_magnitude(float *result, float *d_x, float *d_y, int w, int h) {
	int size;
	size = w * h;

	for(int i = 0; i < size; ++i){
		result[i] = sqrtf( d_x[i] * d_x[i] + d_y[i] * d_y[i] );
	}
}
