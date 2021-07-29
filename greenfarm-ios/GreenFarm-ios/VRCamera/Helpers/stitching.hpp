

#ifndef stitching_hpp
#define stitching_hpp

#include <stdio.h>
//#include "opencv2/core/core.hpp"

#ifndef CVOpenTemplate_Header_h
#define CVOpenTemplate_Header_h

cv::Mat stitch (std::vector <cv::Mat> & images);
int corpBoundingRect(cv::Mat &inputMat, cv::Mat &outputMat);

#endif

#endif /* stitching_hpp */
