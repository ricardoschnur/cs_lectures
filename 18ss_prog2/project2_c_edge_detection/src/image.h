#ifndef IMAGE_H
#define IMAGE_H


/**
 * Initializes an one dimensional array of the given size.
 */
float *array_init(int size);

/**
 * Frees the given memory
 */
void array_destroy(float *m);

/**
 * Reads an Image from a portable graymap (P2) file.
 * Memory allocation is dealt with inside the function.
 * You are responsible to call array_destroy on the result.
 * w and h should be used to access the width and height of the image in the
 * scope calling this function. 
 */
float *read_image_from_file(const char *filename, int *w, int *h);

/**
 * Writes an image to a portable graymap (P2) file.
 * Each pixel value needs to be rounded to an integer value. 
 */
void write_image_to_file(float *img, int w, int h, const char *filename);

/**
 * Returns the gray value of the image at position (x,y). If the position is 
 * outside the image the value of the pixel mirrored at the image border is
 * returned.
 * w: width of the image
 * h: height of the image
 * x: x coordinate of the pixel
 * y: y coordinate of the pixel
 */
float get_pixel_value(float *img, int w, int h, int x, int y);

/**
 * Rescales the pixel values such that they range from 0 to 255. 
 * If all pixels have the same value a complete black image should be returned.
 */
void scale_image(float *result, float *img, int w, int h);

/**
 * All pixels with a value larger than the threshold T are assigned a value
 * of 255 and less or equal to T a value of 0.
 * There is no new image created. Instead the pixel values in the given image
 * are adapted.
 */
void apply_threshold(float *img, int w, int h, int T);

#endif
