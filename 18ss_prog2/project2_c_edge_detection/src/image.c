#include <assert.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "image.h"

float *array_init(int size) {
	(void)size;

	// TODO: Implement me!

	return NULL;
}

void array_destroy(float *m) {
	(void)m;

	// TODO: Implement me!
}

float *read_image_from_file(const char *filename, int *w, int *h) {
	(void)filename;
	(void)w;
	(void)h;

	// TODO: Implement me!

	return NULL;
}

void write_image_to_file(float *img, int w, int h, const char *filename) {
	(void)img;
	(void)w;
	(void)h;
	(void)filename;

	// TODO: Implement me!
}

float get_pixel_value(float *img, int w, int h, int x, int y) {
	(void)img;
	(void)w;
	(void)h;
	(void)x;
	(void)y;

	// TODO: Implement me!

	return 0;
}

void scale_image(float *result, float *img, int w, int h) {
	float min, max, coeff;
	int size;
	size = w * h;

	if (size == 0)
		return;

	// Determine min and max values
	min = img[0];
	max = img[0];
	for(int i = 1; i < size; ++i){
		min = (img[i] < min) ? img[i] : min;
		max = (img[i] > max) ? img[i] : max;
	}

	// All values are equal
	if ( fabs(max-min) < 1E-20 ){
		for(int i = 0; i < size; ++i){
			result[i] = 0;
		}
		return;
	}

	// Not all values are equal
	coeff = 255.0 / (max - min);
	for(int i = 0; i < size; ++i){
		result[i] = coeff * ( img[i] - min );
	}
}

void apply_threshold(float *img, int w, int h, int T) {
	int size;
	size = w * h;

	for(int i = 0; i < size; ++i){
		img[i] = (img[i] > T) ? 255 : 0;
	}
}
