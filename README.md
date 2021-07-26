# Green Farm

*IBM : Call for Code 2021*

**Green Farm is a tech-for-good, green and digital solution based on CSA which promotes the green agricultural production for small farmers, provides responsible produce for consumers. It is available for download on Apple Store.  **

## Contents

  - [Short description](#short-description)
    - [What's the problem?](#whats-the-problem)
    - [How can technology help?](#how-can-technology-help)
    - [The idea](#the-idea)
  - [Demo video](#demo-video)
  - [The architecture](#the-architecture)
  - [Long description](#long-description)
  - [Project roadmap](#project-roadmap)
  - [Getting started](#getting-started)
  - [Live demo](#live-demo)
  - [Built with](#built-with)
  - [Contributing](#contributing)
  - [License](#license)
  

## Short description

### What's the problem?

Food safety issues have become common nowadays. According to the 2020 FAO report, nearly 750 million people in the world have been affected by food insecurity. When people go to grocery stores, they do not know how the food is grown and if there are pesticides left. Community Supported Agriculture(CSA) is a farming philosophy that regularly provides consumers with fresh produce from local farms on a membership or subscription basis. For safety considerations, many consumers want to participate in CSA farms to get clean, fresh and credible food, it is even more desirable if they can do the planting by themselves. But people have neither purchasing channels nor lands for planting. 

Demand for CSA farms remained strong during the pandemic, since local produce in CSA boxes can be directly delivered to doors. Now small farmers care more about how to retain and attract more customers to join CSA programs. On the one hand, stable and higher revenues help small farmers resist financial risks; on the other hand, CSA-based farming is beneficial for realizing green agriculture and sustainable land management. 

### How can technology help?

 * Connect producers and consumers in real-time (Using live streaming technology).
 * Make customers feel in the middle of farms (Using AR（Augmented Reality）and VR(Virtual Realtiy) to show farms accurately and objectively).
 * Recommend suitable farms, produce and financial products to consumers (using IBM Waston to perform efficient matching and recommendations)
 * Ensure the credibility and traceability of the sustainable planting process (Using IBM Blockchain platform).
 * Enable consumers from different countries at multiple areas to efficiently find CSA farms nearby and get fresh produce (Using mapping technology).
 * Provide small farmers and consumers with targeted financial support (setting up a federated learning network with different financial institutions involved).

Green Farm's technology is built and deployed on IBM Cloud and our program is written in: Java, Python, Html5, Css, Javascript. In addition, we used IBM Blockchain Platform, IBM Cloud object storage, IBM Federated Learning, IBM Watson Machine Learning, IBM Mysql,Live Streaming ,AR,VR and so on to make our app more professional, thus, connect small farmers who practice sustainable planting with consumers who demand for clean, credible food.

### The idea

The SDGs(Sustainable Development Goals) adopted by the United Nations include "Clean water and sanitation, Zero hunger, Responsible production and green consumption". Based on the SDGs, Call for Code 2021 Global Challenge calls for taking immediate actions to combat climate change. In a study published by FAO, it points out that the world's food systems are responsible for more then one-third of global greenhouse gas emissions. To motivate sustainable, traceable agriculture,  we build Green Farm to help reduce carbon footprint of food production and enable customers to find CSA farms nearby, get fresh, clean food and support the local agriculture. 

## Demo video

[![Watch the video](https://github.com/GreenFarmCsa/greenfarm/blob/main/Youtube_video.jpg)](https://youtu.be/vOgCOoy_Bx0)

## The architecture

![Green Farm architecture](https://github.com/GreenFarmCsa/greenfarm/blob/main/architecture.jpg)

1. Farmers measure the unoccupied lands by AR and leased them.
2. Consumers browse the farm by VR and subscribe to the CSA box or even rent lands.
3. Farmers livestream with consumers in real-time.
4. Green Farm app stores the video and vr data within IBM Object Storage.
5. The app gets farms info from IBM Mysql and gets planting instructions from the FAO database.
6. Farmers use app to record planting info. The info will be stored on IBM Blockchain platform to ensure the credibility and traceability of the planting data.

### The Federated Learning Logic

![Green Farm Federated Learning](https://github.com/GreenFarmCsa/greenfarm/blob/main/FederatedLearning.jpg)

1. Train model through model parameters.
2. Green Farm has various data of users in one account.
3. Recommend financial products to farmers through the federal learning network.

## Long description

[More detail is available here](https://github.com/GreenFarmCsa/greenfarm/blob/main/Long%20Description_Green%20Farm.doc)

## Project roadmap

![Roadmap](https://github.com/GreenFarmCsa/greenfarm/blob/main/roadmap.jpg)

1. In July 2021, Green Farm iOS version has been released to help small farms better operate with CSA philosophy, support land subscription and lease, keep photos and videos of planting activities, and livestream agricultural production activities.  It supports online sales of produce and user reviews, post creation and share in the farm community; It also supports tracking planting activities from IBM blockchain and converting them into carbon credits. Green Farm recommends farms, produce and green financial products to users through machine learning and federated learning.

2. In December 2021, Android version will be available for download. It integrates real-time weather data and improves machine learning and federal learning models, to help small farmers better practice low-carbon and green planting. The updated version also cooperates with public welfare organizations to omplete green planting donations and carbon offset, thus encouraging CSA users to cultivate a low-carbon lifestyle.

3. In June 2022, Green Farm will be introduced to more farms in more regions, supporting the green planting data of more crops and the tracking of more carbon behaviors, to further improve the green planting guidance function. Moreover, it will connect with the local food safety certification, and help more farmers and consumers join CSA more conveniently and enjoy green, fresh and credible produce.

## Getting started

[Getting Started](https://github.com/GreenFarmCsa/greenfarm/blob/main/get%20started.md)

## Live demo

You can find a deployed app on App store. For more details, see [Getting started](#getting-started). 

## Built with

- [IBM Blockchain Platform](https://cloud.ibm.com/catalog?search=Blockchain%20Platform#search_results) - The backend application used
- [IBM Waston](https://www.ibm.com/watson) - The recommendation logic
- [IBM Cloud object storage](https://cloud.ibm.com/catalog?search=object%20storage#search_results) - The backend application used
- [Live Streaming]() - The ios app used
- [AR]() - The ios app used
- [VR]() - The ios app used
- [Maven](https://maven.apache.org/) - Dependency management

## Contributing

Please read [CONTRIBUTING.md](https://github.com/GreenFarmCsa/greenfarm/blob/main/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the Apache 2 License - see the [LICENSE](https://github.com/GreenFarmCsa/greenfarm/blob/main/LICENSE) file for details.
