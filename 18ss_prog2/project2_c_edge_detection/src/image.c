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
	(void)result;
	(void)img;
	(void)w;
	(void)h;

	// TODO: Implement me!
}

void apply_threshold(float *img, int w, int h, int T) {
	(void)img;
	(void)w;
	(void)h;
	(void)T;

	// TODO: Implement me!
}
