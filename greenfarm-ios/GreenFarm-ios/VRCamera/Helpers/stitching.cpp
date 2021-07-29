
#include "stitching.hpp"
#include <stdio.h>
#include <iostream>
#include <fstream>
#include "opencv2/highgui/highgui.hpp"
//#include "opencv2/stitching/stitcher.hpp"
#include "opencv2/stitching.hpp"

using namespace std;
using namespace cv;

vector<Mat> imgs;

void parseCmdArgs(int argc, char** argv);

const int scale = 2;
int getMaxContour(std::vector<vector<cv::Point>> contours);

cv::Mat stitch (vector<Mat>& images)
{
    imgs = images;
    Mat pano;
    Stitcher stitcher = Stitcher::createDefault(true);
    
    Stitcher::Status status = stitcher.stitch(imgs, pano);
    
    if (status != Stitcher::OK)
    {
        cout << "Can't stitch images, error code = " << int(status) << endl;
        //return 0;
    }
    return pano;
}

void parseCmdArgs(int argc, char** argv)
{
    for(int i=1;i<argc;i++)
    {
        Mat img = imread(argv[i]);
        if (img.empty())
        {
            cout << "Can't read image '" << argv[i] << "'\n";
        }
        imgs.push_back(img);
    }
}

int corpBoundingRect(cv::Mat &inputMat, cv::Mat &outputMat)
 {
 Mat stitched;
 copyMakeBorder(inputMat, stitched, 10, 10, 10, 10, cv::BORDER_CONSTANT, true);
 
 Mat gray;
 cv::cvtColor(stitched, gray, cv::COLOR_BGR2GRAY);
 cv::medianBlur(gray, gray, 7);
 Mat tresh;
 threshold(gray, tresh, 0, 255, THRESH_BINARY);

 resize(tresh, tresh,
        Size(tresh.cols / scale, tresh.rows / scale),
        tresh.cols / 2,
        tresh.rows / 2, INTER_LINEAR);

 vector<vector<Point>> contours;
 vector<Vec4i> hierarchy = vector<cv::Vec4i>();
 findContours(tresh.clone(), contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
 
 int index = getMaxContour(contours);
 if (index == -1) {
     return -1;
 }
 vector<Point> cnt = contours[index];
 drawContours(tresh, contours, index, Scalar(255,0,0));

 Mat mask = Mat::zeros(tresh.rows, tresh.cols, CV_8UC1);

 Rect cntRect = cv::boundingRect(cnt);
 rectangle(mask, cntRect, cv::Scalar(255, 0, 0), -1);

 Mat minRect = mask.clone();
 Mat sub = mask.clone();

 while (cv::countNonZero(sub) > 0) {
     cv::erode(minRect, minRect, Mat());
     cv::subtract(minRect, tresh, sub);
 }

 cv::Mat minRectClone = minRect.clone();

 cv::resize(minRectClone, minRectClone,
            cv::Size(minRectClone.cols * scale, minRectClone.rows * scale),
            (float)minRect.cols / 2, (float)minRect.rows / 2,INTER_LINEAR);

 std::vector<std::vector<Point> > cnts;
 vector<Vec4i> hierarchyA = vector<cv::Vec4i>();
 findContours(minRectClone, cnts, hierarchyA, RETR_TREE, CHAIN_APPROX_SIMPLE);
 int idx = getMaxContour(cnts);
 if (idx == -1) {
     return -1;
 }
 Rect finalRect = cv::boundingRect(cnts[idx]);
//
////    printf("finalRect {x = %d, y = %d, width = %d, height = %d \n", finalRect.x, finalRect.y, finalRect.width, finalRect.height);
 outputMat = Mat(stitched, finalRect).clone();

 return 0;
}

int getMaxContour(std::vector<vector<cv::Point>> contours){
  double max_area = 0;
  int index = -1;
  for (int i = 0; i < contours.size(); i++) {
     double tempArea = contourArea(contours[i]);
     if (tempArea > max_area) {
         max_area = tempArea;
         index = i;
     }
  }
  return index;
}

