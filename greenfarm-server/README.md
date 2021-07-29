##Call for Code 2021 - Green Farm

## Overview
This project is the backend code for Green Farm, built on SpringBoot and embedded Tomcat. The backend provides RESTful APIs for front-end H5 pages to call. It includes the following features:
- Farm
- Land
- Produce
- Community
- PlantTask
- Topic
- BlockChain
- Recommend
- CarbonFootprint
- Live

## Getting Started

### Package
FirstLy, copy the static resources (HTML, CSS, JS, picture) build by `greenfarm-grontend` to resources/static directory.

```bash
./mvnw clean install -P${build.profile.name}
```

supported ${build.profile.name} includes: 
- dev
- ibm
- aws

Artifact:

```
green-farm/target/greenfarm-1.0.0-aws.tar.gz

```

#### Start
```bash
tar -xvf greenfarm-1.0.0-aws.tar.gz
greenfarm-bin/bin/start.sh (linux)
greenfarm-bin/bin/start.sh (windows)
```

#### Stop
```bash
greenfarm-bin/bin/stop.sh 
```

