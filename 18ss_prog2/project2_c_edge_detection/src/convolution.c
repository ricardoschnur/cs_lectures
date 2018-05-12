#include <stdlib.h>
#include <stdio.h>

#include "convolution.h"
#include "image.h"

void convolve(float *result, float *img, int w, int h, const float *kernel, int w_k, int h_k) {
	float value;
	int w_mid, h_mid, x_new, y_new;
	w_mid = (w_k - 1) / 2;
	h_mid = (h_k - 1) / 2;



	for (int x = 0; x < w; ++x) {
		for (int y = 0; y < h; ++y) {
			value = 0;

			for (int i = 0; i < w_k; ++i) {
				// loop over rows of kernel
				for (int j = 0; j < h_k; ++j) {
					// loop over columns of kernel
					x_new = ( x + i - w_mid );
					y_new = ( y + j - h_mid );
					value += kernel[i + w_k*j] * get_pixel_value(img, w, h, x_new, y_new);
				}
			}

			result[x + w * y] = value;
		}
	}
}
