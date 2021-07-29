# Table of Contents
- [Background](#background)
- [AI Model](#ai-model)
- [Deploy](#deploy)
- [Usage](#usage)
# Background
This guide describes the AI models of Green Farm and an online serving interface demo based on python.
# AI Model
## Fate Introduction
FATE (Federated AI Technology Enabler) is an open-source project initiated by Webank's AI Department to provide a secure computing framework to support the federated AI ecosystem. It implements secure computation protocols based on homomorphic encryption and multi-party computation (MPC). It supports federated learning architectures and secure computation of various machine learning algorithms, including logistic regression, tree-based algorithms, deep learning and transfer learning.
## Federated Learning Algorithms 
We deploy KubeFATE on the IBM cloud,use the horizontal federated learning to build the model,then we publish and bind the model,provide the online
inference API to predict credit,the detail step as follow:
* Data preparation:
  * Prepare the processd train data which contained guest data and host data, guest data has the labels while host data has no label.
  * Edit the upload_data.json profile, configure data path and namespace.
  * Submit the fate flow job to upload data.
* Model training:
  * Edit .dsl profile and .conf profile,specify the classification algorithm with parameter.
  * Submit the fate flow job to train model.
* Online inference API publishing：
  * Publish and bind the trained model.
  * Develop the host-side custom adapter.
  * Develop the inference service interface.

More about FATE:https://github.com/FederatedAI/FATE/blob/master/README.md

## IBM Watson AutoAI and Jupyter Notebook
We use IBM Watson AutoAI and Jupyter Notebook to build recommended algorithm models,the detailed steps are as follows:
* Prepare the processed training data.
* Create the IBM Watson Studio project and upload the training data.
* Add AutoAI service to the project.
* Configure the AutoAI and start the model training.
* Select the best model and upgrade to the deploy space.
* Develop the predicitive API.


# Deploy
## Prerequisites
To run this python script must meet the following requirements:

1.Python (Version>=3.7\)  
2.Install Flask  
```bash
$ pip install flask
```

## Running
Running the python script
```bash
$ nohup python mlapi.py &
```
# Usage
##  Recommender System
According to users' attributes such as location, carbon credit, commented products, along with other attributes, we recommend farms, produce, financial products to users.   
### Recommend farms
```python
@app.route("/recommend-farm", methods=['POST'])
def  recommend_farm():
# default result
return_dict = {'rescode': '200', 'recommend_farm_list':[]}
# check if the input is none
if request.get_data() is  None:
return_dict['rescode'] = '5004'
return json.dumps(return_dict, ensure_ascii=False)
get_data = request.get_data()
get_data = json.loads(get_data)
username = get_data.get('username')
rent_list = get_data.get('rent_list')
values, all_farms = combine_data(get_data)
result = []
payload_scoring = {"input_data": [
{"fields": ["community_id", "location", "carbon_credit", "carbon_medals", "comment_products",
"farm_location", "suited_product", "rent_period", "total_area", "idle_area"],
"values": values}
]}
response_scoring = requests.post(model_address, 
json=payload_scoring, headers={'Authorization': 'Bearer ' + mltoken})
prediction_score = make_predictions(response_scoring, all_farms)
recommend_farm = recommend_top_three_farms(prediction_score, username, rent_list, all_farms)
result = fill_answer(recommend_farm, result)
return_dict['recommend_farm_list'] = result
return json.dumps(return_dict, ensure_ascii=False)
```
### Request Example
```json
{
    "username": "Jack",
    "community_id": "1",
    "location": "Queens, New York, NY, USA",
    "carbon_credit": "87",
    "carbon_medals": "medal1, medal2",
    "comment_products": [
        "Tomato",
        "Corn",
        "Cucumber"
    ],
    "rent_list": [
        {
            "rent_id": "1",
            "land_id": "2",
            "farm_id": "1",
            "username": "Jack",
            "rent_price": "1000",
            "area": "30",
            "rent_start_time": "2020-06-01 12:00:00",
            "rent_end_time": "2020-12-10 8:00:00",
            "confirm_crops": "Corn",
            "create_time": "2020-06-01 12:00:00",
            "modify_time": "2020-08-10 10:00:00"
        }
    ],
    "farm": [
        {
            "farm_id": "1",
            "location": "Austin, Texas",
            "suited_crops": "Onion,Corn",
            "rent_period": "365",
            "total_area": "150",
            "idle_area": "50"
        }
    ]
}
```
#### response example
```json
{
    "rescode": "200", 
    "farm_id": "1",
    "description": "description about the farm"
}
{
    "rescode": "200", 
    "farm_id": "2",
    "description": "description about the farm"
}
{
    "rescode": "200", 
    "farm_id": "6",
    "description": "description about the farm"
}
```
### Recommend produce
```python
@app.route("/recommend-product", methods=['POST'])
def  recommend_product():
return_dict = {'rescode': '200', 'recommend_product_list':[]}
if request.get_data() is  None:
return_dict['rescode'] = '5004'
return json.dumps(return_dict, ensure_ascii=False)
get_data = request.get_data()
get_data = json.loads(get_data)
username = get_data.get('username')
order_list = get_data.get('order_list')
values, all_products = combine_product_data(get_data)
result = []
mltoken = token_response.json()["access_token"]
payload_scoring = {"input_data": [

{"fields": ["community_id", "location", "carbon_credit", "carbon_medals", "comment_products",
"product_id", "farm_id", "plantername", "product_name", "category", "price", "carbon_credit_needed", "number", "sale_number", "identifications", "carbon_emission", "donate_amount", "create_time", "modify_time"],
"values": values}
]}
response_scoring = requests.post(model_space_address, 
json=payload_scoring, headers={'Authorization': 'Bearer ' + mltoken})
prediction_score = make_predictions(response_scoring, all_products)
recommend_product = recommend_top_three(prediction_score, username, order_list, all_products)
result = fill_answer_product(recommend_product, result)
return_dict['recommend_product_list'] = result
return json.dumps(return_dict, ensure_ascii=False)
```

### Request Example
```json
{
    "username": "Jack",
    "community_id": "1",
    "location": "Queens, New York, NY, USA",
    "carbon_credit": "87",
    "carbon_medals": "medal1, medal2",
    "comment_products": [
        "Tomato",
        "Corn",
        "Cucumber"
    ],
    "order_list": [
        [
            {
                "username": "Jack",
                "plantername": "Jack",
                "category": "vegetables",
                "price": "10.000",
                "number": "100",
                "identifications": "1, 2, 3, 4",
                "product_id": "1",
                "farm_id": "1",
                "product_name": "Corn",
                "carbon_credit": "100",
                "sale_number": "0",
                "carbon_emission": "10.0000",
                "donate_amount": "10.0000",
                "create_time": "2020-06-01 15:37:07",
                "modify_time": "2020-06-20 06:00:00",
                "order_id": "11"
            }
        ]
    ]
}
```
#### response example
```json
{
    "rescode": "200", 
    "product_id": "2",
    "description": "description about the product"
}
{
    "rescode": "200", 
    "product_id": "3",
    "description": "description about the product"
}
{
    "rescode": "200", 
    "product_id": "6",
    "description": "description about the product"
}

```
### Recommend financial products
```python
response_str = finance_product_recommend_api(value_list)
    response_str = json.loads(response_str.text)
    predictions = response_str.get('predictions')
    values = predictions[0].get('values')
    for i in range(0, len(product_id_list)):
        value = values[i][0]
        res_df = res_df.append([{'finance_product_id': product_id_list[i],
                                 'value': float(value)}], ignore_index=True)
    res_df = res_df.sort_values(by=['value'], ascending=[False])
```
#### request example
```json
{
    "username": "Jack",
    "sex" :"male",
    "rolename" :"farmer",
    "location" : "Queens, New York, NY, USA",
    "carbon_credit" :"87",
    "create_time": "2019-01-01 06:20:00",
    "modify_time": "2019-06-01 13:00:01",
    "finance_products": [
        {
            "finance_product_id":"0001",
            "finance_limit":"$30000.00",
            "valid_period":"06/01/2021-06/07/2022",
            "org_name":"org1"
        },
        {
            "finance_product_id":"0002",
            "finance_limit":"$40000.00",
            "valid_period":"06/01/2021-06/07/2023",
            "org_name":"org2"
        },
        {
            "finance_product_id":"0003",
            "finance_limit":"$20000.00",
            "valid_period":"06/01/2021-06/07/2024",
            "org_name":"org3"
        }
    ]
}
```
#### response example
```json
{
    "rescode": "200",
    "finance_product_list": [
        {
            "finance_product_id": "0001",
            "description": "recommended"
        },
        {
            "finance_product_id": "0003",
            "description": "recommended"
        },
        {
            "finance_product_id": "0002",
            "description": "recommended"
        }
    ]
}
```
## FederatedAI FATE
### Credit rating interface 
```python
@app.route("/credit-rating", methods=['POST'])
def credit_rating():
    """
       credit_rating http api
    """
    return_dict = {'rescode': '200', 'credit-rating': '1', 'description': 'Good credit'}
    if request.get_data() is None:
        return_dict['rescode'] = '5004'
        return json.dumps(return_dict, ensure_ascii=False)
...
x_predict_json = {
        'x0': sex_dict.get(sex),
        'x1': role_dict.get(role_name),
        'x2': location_dict.get(location),
        'x3': carbon_credit,
        'x4': footprint_count_dict['Seed'],
        'x5': footprint_count_dict['Buy'],
        'x6': footprint_count_dict['Fertilize']
    }
...
    response = requests.post(
        'http://ip:8059/federation/v1/inference',
        data=json.dumps(body_json))
    response_data = json.loads(response.text).get('data')
    prob = response_data.get('prob')
    flag = "0"
    if float(prob) > 0.5:
        flag = "1"
    return_dict['credit-rating'] = flag
    return_dict['description'] = description_dict[flag]
    return json.dumps(return_dict, ensure_ascii=False)        
```
#### request example
```json
{
    "username": "Jack",
    "sex" :"male",
    "rolename" :"farmer",
    "location" : "Queens, New York, NY, USA",
    "carbon_credit" :"87",
    "footprint_name" : [
        "Buy Apple","Fertilize","Fertilize","Fertilize","Seed","Seed"
    ]
}
```
#### response example
```json
{
    "rescode": "200", 
    "credit-rating": "1",
    "description": "Good credit"
}
```