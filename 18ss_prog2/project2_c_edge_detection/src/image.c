#include <assert.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "image.h"

float *array_init(int size) {
	float *p = malloc( size * sizeof(p[0]) );
	return p;
}

void array_destroy(float *m) {
	free(m);
}

float *read_image_from_file(const char *filename, int *w, int *h) {
	FILE *fp;
	char format[2];
	int size, i;
	int max;

	fp = fopen(filename, "r");

	// Check if file starts with "P2"
	if( fscanf(fp, "%c%c", &(format[0]), &(format[1])) != 2 ) {
		return NULL;
	}
	if ( strcmp(format, "P2") != 0 ) {
		return NULL;
	}

	// Read width and hight
	if( fscanf(fp, "%d%d", w, h) != 2 ) {
		return NULL;
	}

	// Read maximal value; has to be 255
	if( fscanf(fp, "%d", &max) != 1 ) {
		return NULL;
	}
	if( max != 255 ) {
		return NULL;
	}

	size = (*w) * (*h);
	float *img = array_init(size);

	i = 0;
	while( i<size && fscanf( fp, "%f", &(img[i]) ) == 1 )  {
		++i;
	}


	// Check if too many ot too few points
	if ( i != size && fscanf( fp, "%f", &(img[i]) ) == 1 ) {
		return NULL;
	}

	fclose(fp);
	return img;
}

void write_image_to_file(float *img, int w, int h, const char *filename) {
	FILE *fp;
	int size, rounded;

	fp = fopen(filename, "w");

	// Print header
	fprintf(fp, "P2\n%d %d\n255\n", w, h);

	// Print points
	size = w * h;
	for (int i = 0; i < size; ++i) {
		rounded = (int)roundf(img[i]);
		fprintf(fp, "%d ", rounded);
	}

	fclose(fp);
}

float get_pixel_value(float *img, int w, int h, int x, int y) {
	// Consider nine cases, every combination of the following:
	// x < 0, 0 <= x < w, x >= w
	// y < 0, 0 <= y < h, y >= h
	int x_new, y_new;

	// x value
	if (x<0) {
		x_new = -x-1;
	}

	if (x>=w) {
		x_new = 2*w-x-1;
	}

	if (x >= 0 && x < w) {
		x_new = x;
	}

	// y value
	if (y<0) {
		y_new = -y-1;
	}

	if (y>=h) {
		y_new = 2*h-y-1;
	}

	if (y >= 0 && y < h){
		y_new = y;
	}

	return img[x_new + w * y_new];
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
