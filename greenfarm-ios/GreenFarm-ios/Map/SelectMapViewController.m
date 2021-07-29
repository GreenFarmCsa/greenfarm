
#import "SelectMapViewController.h"
#import <MapKit/MapKit.h>

@interface SelectMapViewController () <MKMapViewDelegate,CLLocationManagerDelegate>{
    BOOL didUpdate;
}

@property (nonatomic,retain) MKMapView *mapView;
@property (nonatomic, strong) MKPointAnnotation *selectAnnotation;
@property (nonatomic, strong) UIButton *doneBtn;
@property (nonatomic, strong) CLLocationManager *locationManager;


@end

@implementation SelectMapViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.frame = [UIScreen mainScreen].bounds;
    MKMapView *map = [[MKMapView alloc]initWithFrame:[UIScreen mainScreen].bounds];
    [map setMapType:MKMapTypeStandard];
    map.delegate = self;
    [self.view addSubview:map];
    self.mapView = map;
    CLLocationManager *locationManager = [[CLLocationManager alloc] init];
    self.locationManager = locationManager;
    self.locationManager.delegate=self;
    
    if (self.locationManager.authorizationStatus==kCLAuthorizationStatusNotDetermined){
           [_locationManager requestWhenInUseAuthorization];
    } else if(self.locationManager.authorizationStatus==kCLAuthorizationStatusAuthorizedWhenInUse) {
        locationManager.delegate=self;
        locationManager.desiredAccuracy=kCLLocationAccuracyBest;
        locationManager.distanceFilter=10.0f;
        [locationManager startUpdatingLocation];
       
    }
  
    UIButton *backBtn = [[UIButton alloc]initWithFrame:CGRectMake(20, 40, 28, 28)];
    [backBtn setBackgroundImage:[UIImage imageNamed:@"gf_fanhui"] forState:UIControlStateNormal];
    [backBtn addTarget:self action:@selector(backCliked) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:backBtn];
    
    UIButton *doneBtn = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.size.width-20-48, 40, 48, 28)];
    [doneBtn setBackgroundImage:[UIImage imageNamed:@"none"] forState:UIControlStateNormal];
    [doneBtn setTitle:@"Done" forState:UIControlStateNormal];
    doneBtn.titleLabel.font = [UIFont systemFontOfSize:14];
    doneBtn.userInteractionEnabled = NO;
    [doneBtn addTarget:self action:@selector(doneCliked) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:doneBtn];
    self.doneBtn = doneBtn;
    
    UITapGestureRecognizer *tapRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(foundTap:)];
    tapRecognizer.numberOfTapsRequired = 1;
    tapRecognizer.numberOfTouchesRequired = 1;
    [self.mapView addGestureRecognizer:tapRecognizer];
    
}

- (void)locationManagerDidChangeAuthorization:(CLLocationManager *)manager{
    if (manager.authorizationStatus == kCLAuthorizationStatusAuthorizedWhenInUse) {
        self.locationManager.desiredAccuracy=kCLLocationAccuracyBest;
        self.locationManager.distanceFilter=10.0f;
        [self.locationManager startUpdatingLocation];
    }
}

- (void)locationManager:(CLLocationManager *)manager
     didUpdateLocations:(NSArray<CLLocation *> *)locations{
    if (didUpdate) {
        return;
    }
    if (!locations&&locations.count == 0) {
        return;
    }
    didUpdate = YES;
    
    if (self.selectAnnotation) {
        [self.mapView removeAnnotation:self.selectAnnotation];
    }
    
    CLLocation *loc = locations[0];
    MKPointAnnotation *point1 = [[MKPointAnnotation alloc] init];
    point1.coordinate = loc.coordinate;
    self.selectAnnotation = point1;
    [self.mapView setCenterCoordinate:loc.coordinate];
    [self.mapView addAnnotation:point1];

    MKCoordinateSpan theSpan;
    theSpan.latitudeDelta=2;
    theSpan.longitudeDelta=2;
    MKCoordinateRegion theRegion;
    theRegion.center=[[self.locationManager location] coordinate];
    theRegion.span=theSpan;
    [self.mapView setRegion:theRegion];
    self.doneBtn.userInteractionEnabled = YES;
    [self.doneBtn setBackgroundImage:[UIImage imageNamed:@"gf_done2"] forState:UIControlStateNormal];
    
}



-(void)foundTap:(UITapGestureRecognizer *)recognizer {
    if (self.selectAnnotation) {
        [self.mapView removeAnnotation:self.selectAnnotation];
    }
    CGPoint point = [recognizer locationInView:self.mapView];
    CLLocationCoordinate2D tapPoint = [self.mapView convertPoint:point toCoordinateFromView:self.mapView];
    
    MKPointAnnotation *point1 = [[MKPointAnnotation alloc] init];
    point1.coordinate = tapPoint;
    self.selectAnnotation = point1;
    
    [self.mapView addAnnotation:point1];
    self.doneBtn.userInteractionEnabled = YES;
    [self.doneBtn setBackgroundImage:[UIImage imageNamed:@"gf_done2"] forState:UIControlStateNormal];
}

-(MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id<MKAnnotation>)annotation
{
    MKAnnotationView * result = nil;
    NSString * identifier = @"annotation";
    MKPinAnnotationView * annotationView = (MKPinAnnotationView *)[mapView dequeueReusableAnnotationViewWithIdentifier:identifier];
    if(annotationView == nil)
    {
        annotationView = [[MKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:identifier];
        
        [annotationView setCanShowCallout:YES];
    }
    
    
    annotationView.canShowCallout = YES;
    result = annotationView;
    return result;
}

- (void)backCliked {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)doneCliked {
    [[NSNotificationCenter defaultCenter]postNotificationName:@"MapSelectValueNotify" object:@{@"loc":@[[NSNumber numberWithDouble:self.selectAnnotation.coordinate.latitude],[NSNumber numberWithDouble:self.selectAnnotation.coordinate.longitude]]}];
    [self dismissViewControllerAnimated:YES completion:nil];
}

@end
