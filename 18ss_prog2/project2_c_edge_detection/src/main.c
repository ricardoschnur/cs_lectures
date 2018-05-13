#include <stdlib.h>
#include <stdio.h>

#include "argparser.h"
#include "convolution.h"
#include "derivation.h"
#include "gaussian_kernel.h"
#include "image.h"

int main(int const argc, char **const argv) {
	/**
	 * Parse arguments. The parsed image file name and threshold are available
	 * in the image_file_name and threshold variables (see argparser.h).
	 */
	 parse_arguments(argc, argv);
	 printf("Computing edges for image file %s with threshold %i\n", image_file_name, threshold);

	/**
	 * Read Image from given file.
	 * If the input file is broken terminate with return value 1.
	 * Hint: The width and height of the image have to be accessible in the
	 * scope of this function.
	 */

	 float *img = NULL;
	 int w, h, size;

	 img = read_image_from_file(image_file_name, &w, &h);
	 if (img == NULL) {
		 fprintf(stderr, "Invalid file: %s\n", image_file_name);
		 return 1;
	 }
	 size = w*h;


	/**
	 * Blur image with the given Gaussian kernel (see gaussian_kernel.h).
	 * Write the blurred image to the file out_blur.pgm
	 */

	 float *result = array_init(size);

	 convolve(result, img, w, h, gaussian_k, 5, 5);
	 write_image_to_file(result, w, h, "out_blur.pgm");

	/**
	 * Compute the derivation of the blurred image in x and y direction.
	 * Then write the rescaled results to the files out_d_x.pgm and out_d_y.pgm.
	 */

	 float *d_x = array_init(size);
	 float *d_y = array_init(size);
	 float *d_x_scaled = array_init(size);
	 float *d_y_scaled = array_init(size);


	 derivation_x_direction(d_x, result, w, h);
	 scale_image(d_x_scaled, d_x, w, h);
	 write_image_to_file(d_x_scaled, w, h, "out_d_x.pgm");

	 derivation_y_direction(d_y, result, w, h);
	 scale_image(d_y_scaled, d_y, w, h);
	 write_image_to_file(d_y_scaled, w, h, "out_d_y.pgm");


	/**
	 * Compute the gradient magnitude.
	 * Then write the rescaled result to the file out_gm.pgm
	 */

	 gradient_magnitude(img, d_x, d_y, w, h);
	 scale_image(result, img, w, h);
	 write_image_to_file(result, w, h, "out_gm.pgm");

	/**
	 * Apply the threshold to the gradient magnitude.
	 * Then write the result to the file out_edges.pgm.
	 */

	 apply_threshold(img, w, h, threshold);
	 write_image_to_file(img, w, h, "out_edges.pgm");


	 array_destroy(img);
 	 array_destroy(result);
 	 array_destroy(d_x);
 	 array_destroy(d_y);
 	 array_destroy(d_x_scaled);
 	 array_destroy(d_y_scaled);
	 return 0;
}
