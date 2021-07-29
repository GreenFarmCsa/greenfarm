
#import "MapViewController.h"


@interface MapViewController() <MKMapViewDelegate>
@property (nonatomic,retain) MKMapView *mapView;

@end

@implementation MapViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.frame = [UIScreen mainScreen].bounds;
    MKMapView *map = [[MKMapView alloc]initWithFrame:[UIScreen mainScreen].bounds];
    [map setMapType:MKMapTypeStandard];
    map.delegate = self;
    [self.view addSubview:map];
  
    UIButton *backBtn = [[UIButton alloc]initWithFrame:CGRectMake(20, 40, 28, 28)];
    [backBtn setBackgroundImage:[UIImage imageNamed:@"gf_fanhui"] forState:UIControlStateNormal];
    [backBtn addTarget:self action:@selector(backCliked) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:backBtn];

    NSMutableArray *arr1  = [[NSMutableArray alloc]init];
    for (int i = 0; i < self.infos.count; i++) {
        NSDictionary *info = self.infos[i];
        NSArray *arr = info[@"loc"];
        double lati = [arr[0] doubleValue];
        double longi = [arr[1] doubleValue];
        CLLocationCoordinate2D location = CLLocationCoordinate2DMake(lati , longi);
        MKPointAnnotation *annotation = [[MKPointAnnotation alloc]init];
        annotation.coordinate = location;
        
        annotation.title = info[@"name"];
    
        [map addAnnotation:annotation];
        [arr1 addObject:annotation];
    }
    MKCoordinateRegion region = [self regionForAnnotations:arr1];
    [map setRegion:region];
    self.mapView = map;
}

-(MKCoordinateRegion) regionForAnnotations:(NSArray*) annotations
{
    NSAssert(annotations!=nil, @"annotations was nil");
    NSAssert([annotations count]!=0, @"annotations was empty");

    double minLat=360.0f, maxLat=-360.0f;
    double minLon=360.0f, maxLon=-360.0f;

    for (id<MKAnnotation> vu in annotations) {
        if ( vu.coordinate.latitude  < minLat ) minLat = vu.coordinate.latitude;
        if ( vu.coordinate.latitude  > maxLat ) maxLat = vu.coordinate.latitude;
        if ( vu.coordinate.longitude < minLon ) minLon = vu.coordinate.longitude;
        if ( vu.coordinate.longitude > maxLon ) maxLon = vu.coordinate.longitude;
    }
    CLLocationCoordinate2D center = CLLocationCoordinate2DMake((minLat+maxLat)/2.0, (minLon+maxLon)/2.0);
    MKCoordinateSpan span = MKCoordinateSpanMake(maxLat-minLat+10, maxLon-minLon+10);
    MKCoordinateRegion region = MKCoordinateRegionMake (center, span);
    return region;
}


- (void)backCliked {
    [self dismissViewControllerAnimated:YES completion:nil];
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

- (void)mapView:(MKMapView *)mapView didAddAnnotationViews:(NSArray<MKAnnotationView *> *)views {
    for (int i = 0; i< views.count; i++) {
        MKAnnotationView *view = views[i];
        view.selected = YES;
    }

}

- (void)mapView:(MKMapView *)mapView didSelectAnnotationView:(MKAnnotationView *)view {
    NSDictionary *selectFarm;
    for (int i = 0; i < self.infos.count; i++) {
        NSString *name = self.infos[i][@"name"];
        if ([name isEqualToString:view.annotation.title]) {
            selectFarm = self.infos[i];
        }
        
    }
    [[NSNotificationCenter defaultCenter]postNotificationName:@"MapValueNotify" object:@{@"name":view.annotation.title,@"loc":@[[NSNumber numberWithDouble:view.annotation.coordinate.latitude],[NSNumber numberWithDouble:view.annotation.coordinate.longitude]],@"farmId":selectFarm[@"farmId"]}];
    [self dismissViewControllerAnimated:YES completion:nil];
    
}

@end

