#include <assert.h>
#include <math.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>

#include "convolution.h"
#include "derivation.h"
#include "image.h"

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
	(void)result;
	(void)d_x;
	(void)d_y;
	(void)w;
	(void)h;

	// TODO: Implement me!
}
