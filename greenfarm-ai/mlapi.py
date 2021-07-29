"""
AI API FOR Green Farm BACKEND
"""
import json
import datetime
import requests
import pandas as pd
from flask import Flask, request

app = Flask(__name__)

aggregated_products = sorted(['Corn', 'Cucumber', 'Tomato', 'Onion', 'Spinach'])
aggregated_farm_location = sorted(
    ['Austin,Texas', 'Moorpark,California', 'Casa Grande,Arizona', 'Shropshire,West Midlands', 'Uphall,Scotland',
     'Norfolk,East Anglia', 'Changping district,Beijing', 'Jinan,Shandong Province', 'Zhangjiajie,Hunan Province',
     'Wyong Creek,New South Wales', 'Red Hill,Victoria'])
aggregated_user_location = sorted(['Cantwell city,Alaska,USA', 'Queens,New York,NY,USA'])
aggregated_category = sorted(['vegetables', 'fruit'])
aggregated_identification = sorted(['1', '2', '3', '4', '5', '6', '7'])


def aggregate_comment_products(comment_products):
    """
    This function applies one-hot-encoding on comment products
    return integer representations
    """
    # comment_products: ["Corn", "Tomato"]
    encoding = ''
    for product in aggregated_products:
        if product in comment_products:
            encoding = encoding + '1'
        else:
            encoding = encoding + '0'
    return int(encoding)


def aggregate_product_identification(product_identification):
    """
    This function applies one-hot-encoding on products category
    """
    encoding = ''
    category = product_identification.split(",")
    for identification in aggregated_identification:
        if identification in category:
            encoding = encoding + '1'
        else:
            encoding = encoding + '0'
    return int(encoding)


def preprocess_location(location):
    """
    get rid of possible spaces around farm location string
    """
    locations = location.split(',')
    res = ""
    for location in locations:
        res = res + location.strip() + ","
    res = res[:-1]
    return res


def combine_data(get_data):
    """
    combine user data with each farm
    preprocess attributes to generate the prediction set
    """
    try:
        values = []
        user_location = preprocess_location(get_data.get('location'))
        location = 1
        if user_location in aggregated_user_location:
            location = aggregated_user_location.index(user_location)
        community_id = int(get_data.get('community_id'))
        carbon_credit = int(get_data.get('carbon_credit'))
        carbon_medals = int(get_data.get('carbon_medals'))
        comment_products = aggregate_comment_products(get_data.get('comment_products'))
        farms = get_data.get('farm')
        farm_ids = []
        for i in range(0, len(farms)):
            value = []
            farm = farms[i]
            farm_ids.append(str(farm.get('farm_id')))
            value.append(community_id)
            value.append(location)
            value.append(carbon_credit)
            value.append(carbon_medals)
            value.append(comment_products)
            farm_location = farm.get('location')
            farm_location_processed = 0
            if preprocess_location(farm_location) in aggregated_farm_location:
                farm_location_processed = aggregated_farm_location.index(preprocess_location(farm_location))
            suited_crops = aggregate_comment_products(farm.get('suited_crops'))
            rent_period = 0
            total_area = int(farm.get('total_area'))
            idle_area = farm.get('idle_area')
            if idle_area is None:
                idle_area = 0
            idle_area = int(idle_area)
            value.append(farm_location_processed)
            value.append(suited_crops)
            value.append(rent_period)
            value.append(total_area)
            value.append(idle_area)
            values.append(value)
        return values, farm_ids
    except:
        return [], []


def make_predictions(predictions, ids):
    """
    sort id according to prediction scores
    from high to low
    """
    farm_score = {}
    p_text = json.loads(predictions.text)
    prediction = p_text['predictions'][0]['values']
    num_prediction = len(prediction)
    for i in range(0, num_prediction):
        pred_score = prediction[i][1]
        if pred_score[1] > pred_score[0]:
            farm_score[ids[i]] = pred_score[1]
    sorted_farm_score = sorted(farm_score, key=farm_score.get, reverse=True)
    return sorted_farm_score


def recommend_top_three_farms(sorted_farm_score, username, rent_list, all_farms):
    """
    recommend three farms with highest prediction scores
    if not enough farm, recommend first three farms
    """
    count = 0
    recommend_farms = set()
    for farm in sorted_farm_score:
        recommend_farms.add(farm)
        count += 1
        if count == 3:
            return recommend_farms
    farm_ids = check_rent_history(rent_list, username)
    for i in range(0, len(farm_ids)):
        # check the history rent history
        recommend_farms.add(farm_ids[i])
    current = 0
    while len(recommend_farms) < 3 and current < len(all_farms):
        recommend_farms.add(all_farms[current])
        current = current + 1
    return recommend_farms


def check_rent_history(rent_list, username):
    """
    return farm ids that the given username has rented before
    """
    farm_rent_before = []
    for rent in rent_list:
        if rent.get('username') == username:
            farm_rent_before.append(str(rent.get('farm_id')))
    return farm_rent_before


def fill_answer(recommend_farms, result):
    """
    fill the return json with recommended farm ids
    """
    for farm in recommend_farms:
        result.append({'farm_id': farm, 'description': 'recommended farm'})
    return result


def combine_product_data(get_data):
    """
    combine user information with agricultural products
    """
    try:
        community_id = int(get_data['community_id'])
        user_location = preprocess_location(get_data['location'])
        location = 1
        if user_location in aggregated_user_location:
            location = aggregated_user_location.index(user_location)
        carbon_credit = int(get_data['carbon_credit'])
        carbon_medals = int(get_data['carbon_medals'])
        comment_products = aggregate_comment_products(get_data['comment_products'])
        orders = get_data['order_list']
        product_ids = ['1', '2', '3', '4', '5', '6']
        values = []
        for i in range(0, len(orders)):
            value = [community_id, location, carbon_credit, carbon_medals, comment_products]
            order = orders[i]
            product_id = int(order['product_id'])
            farm_id = int(order['farm_id'])
            product_name = aggregated_products.index(order['product_name'])
            category = aggregated_category.index(order['category'])
            price = int(float(order['price']))
            carbon_credit_needed = int(order['carbon_credit'])
            number = int(order['number'])
            sale_number = int(order['sale_number'])
            identifications = aggregate_product_identification(order['identifications'])
            carbon_emission = int(float(order['carbon_emission']))
            donate_amount = int(float(order['donate_amount']))
            if order['create_time'] is None:
                create_time_processed = 2021
            else:
                create_time = datetime.datetime.strptime(order['create_time'], '%Y-%m-%d %H:%M:%S')
                create_time_processed = 10000 * create_time.year + 100 * create_time.month
            if order['modify_time'] is None:
                modify_time_processed = 2021
            else:
                modify_time = datetime.datetime.strptime(order['modify_time'], '%Y-%m-%d %H:%M:%S')
                modify_time_processed = 10000 * modify_time.year + 100 * modify_time.month
            value.append(product_id)
            value.append(farm_id)
            value.append(product_name)
            value.append(category)
            value.append(price)
            value.append(carbon_credit_needed)
            value.append(number)
            value.append(sale_number)
            value.append(identifications)
            value.append(carbon_emission)
            value.append(donate_amount)
            value.append(create_time_processed)
            value.append(modify_time_processed)
            values.append(value)
        return values, product_ids
    except:
        return [], []


def recommend_top_three(sorted_product_score, username, order_list, all_products):
    """
    recommend three products with highest prediction scores
    if not enough products, recommend first three products
    """
    count = 0
    recommend_products = set()
    for product in sorted_product_score:
        recommend_products.add(product)
        count += 1
        if count == 3:
            return recommend_products
    product_ids = check_order_history_product(order_list, username)
    for product in product_ids:
        # check the history rent history
        recommend_products.add(product)
    current = 0
    while len(recommend_products) < 3 and current < len(all_products):
        recommend_products.add(all_products[current])
        current = current + 1
    return recommend_products


def check_order_history_product(order_list, username):
    """
    check order history and returns products same as\
        or similar to the given username ordered
    """
    category_purchased = set()
    product_ids = set()
    for order in order_list:
        if order['username'] == username:
            category_purchased.add(order['category'])
    for order in order_list:
        if order['category'] in category_purchased:
            product_id = order['product_id']
            product_ids.add(product_id)
    return product_ids


def fill_answer_product(recommend_products, result):
    """
    fill the return json with recommended product ids
    """
    for farm in recommend_products:
        result.append({'product_id': farm, 'description': 'recommended product'})
    return result


def cal_period(period):
    """
    calculate period(month)
    """
    begin_end = period.split('-')
    begin = begin_end[0].split('/')
    end = begin_end[1].split('/')
    begin_month = begin[1]
    begin_year = begin[2]
    end_month = end[1]
    end_year = end[2]
    result = int(end_year) * 12 + int(end_month) - int(begin_year) * 12 - int(begin_month)
    return result


def normalization(value, maxvalue, minvalue):
    """
       normalization method
    """
    value = (value - minvalue) / (maxvalue - minvalue)
    return value


@app.route("/recommend-farm", methods=['POST'])
def recommend_farm():
    """
       recommend_farm
    """
    # default result
    return_dict = {'rescode': '200', 'recommend_farm_list': []}
    # check if the input is none
    if request.get_data() is None:
        return_dict['rescode'] = '5004'
        return json.dumps(return_dict, ensure_ascii=False)
    get_data = request.get_data()
    get_data = json.loads(get_data)
    username = get_data.get('username')
    rent_list = get_data.get('rent_list')

    values, all_farms = combine_data(get_data)
    if len(values) == 0:
        result_farm = []
        recommend_farms = ['1', '2', '3']
        result_farm = fill_answer(recommend_farms, result_farm)
        return_dict['recommend_farm_list'] = result_farm
        return json.dumps(return_dict, ensure_ascii=False)

    result = []

    api_key = "YOUR_API_KEY"
    token_response = requests.post('https://iam.cloud.ibm.com/identity/token',
                                   data={"apikey": api_key, "grant_type": 'urn:ibm:params:oauth:grant-type:apikey'})
    ml_token = token_response.json()["access_token"]

    payload_scoring = {"input_data": [
        {"fields": ["community_id", "location", "carbon_credit", "carbon_medals", "comment_products",
                    "farm_location", "suited_product", "rent_period", "total_area", "idle_area"],
         "values": values}
    ]}
    # You could obtain the url after model deployed on IBM Cloud platform.
    response_scoring = requests.post(
        'https://us-south.ml.cloud.ibm.com/ml/v4/deployments/XXXXX',
        json=payload_scoring, headers={'Authorization': 'Bearer ' + ml_token})
    prediction_score = make_predictions(response_scoring, all_farms)
    recommend_farms = recommend_top_three_farms(prediction_score, username, rent_list, all_farms)
    result = fill_answer(recommend_farms, result)
    return_dict['recommend_farm_list'] = result
    return json.dumps(return_dict, ensure_ascii=False)


@app.route("/recommend-product", methods=['POST'])
def recommend_product():
    """
       recommend_product
    """
    # default result
    return_dict = {'rescode': '200', 'recommend_product_list': []}
    # check if the input is none
    if request.get_data() is None:
        return_dict['rescode'] = '5004'
        return json.dumps(return_dict, ensure_ascii=False)

    get_data = request.get_data()
    get_data = json.loads(get_data)
    username = get_data.get('username')
    order_list = get_data.get('order_list')

    values, all_products = combine_product_data(get_data)
    if len(values) == 0:
        result_products = []
        recommend_products = ['1', '2', '3']
        result_products = fill_answer_product(recommend_products, result_products)
        return_dict['recommend_product_list'] = result_products
        return json.dumps(return_dict, ensure_ascii=False)

    result = []

    api_key = "YOUR_API_KEY"
    token_response = requests.post('https://iam.cloud.ibm.com/identity/token',
                                   data={"apikey": api_key, "grant_type": 'urn:ibm:params:oauth:grant-type:apikey'})
    ml_token = token_response.json()["access_token"]

    payload_scoring = {"input_data": [
        {"fields": ["community_id", "location", "carbon_credit", "carbon_medals", "comment_products",
                    "product_id", "farm_id", "plantername", "product_name", "category", "price", "carbon_credit_needed",
                    "number", "sale_number", "identifications", "carbon_emission", "donate_amount", "create_time",
                    "modify_time"],
         "values": values}
    ]}
    # You could obtain the url after model deployed on IBM Cloud platform.
    response_scoring = requests.post(
        'https://us-south.ml.cloud.ibm.com/ml/v4/deployments/XXXXX',
        json=payload_scoring, headers={'Authorization': 'Bearer ' + ml_token})
    prediction_score = make_predictions(response_scoring, all_products)
    recommend_products = recommend_top_three(prediction_score, username, order_list, all_products)
    result = fill_answer_product(recommend_products, result)
    return_dict['recommend_product_list'] = result
    return json.dumps(return_dict, ensure_ascii=False)


@app.route("/recommend-financial-product", methods=['POST'])
def recommend_financial_product():
    """
       recommend_financial_product http api
    """
    return_dict = {'rescode': '200', 'finance_product_list': []}
    if request.get_data() is None:
        return_dict['rescode'] = '5004'
        return json.dumps(return_dict, ensure_ascii=False)
    role_dict = {'farmer': 1, 'consumer': 2}
    sex_dict = {'male': 1, 'female': 2}
    location_dict = {'Cantwell city, Alaska, USA': 1, 'Queens, New York, NY, USA': 2}
    org_dict = {'Agriculture Bank of China': 1, 'CHASE Bank': 2, 'PICC': 3}
    get_data = request.get_data()
    get_data = json.loads(get_data)
    role_name = get_data.get('rolename')
    sex = get_data.get('sex')
    location = get_data.get('location')
    carbon_credit = get_data.get('carbon_credit')
    finance_products = get_data.get('finance_products')
    user_info_array = [sex_dict.get(sex), role_dict.get(role_name),
                       location_dict.get(location), int(carbon_credit)]
    res_df = pd.DataFrame(columns=('finance_product_id', 'value'))
    product_id_list = []
    value_list = []
    for finance_product in finance_products:
        temp_list = []
        limit = finance_product.get('finance_limit').lstrip('$')
        period = finance_product.get('valid_period')
        product_id = finance_product.get('finance_product_id')
        product_id_list.append(product_id)
        org = finance_product.get('org_name')
        temp_list.append(sex_dict.get(sex))
        temp_list.append(role_dict.get(role_name))
        temp_list.append(location_dict.get(location))
        temp_list.append(int(carbon_credit))
        temp_list.append(int(float(limit)))
        temp_list.append(cal_period(period))
        temp_list.append(org_dict.get(org))
        value_list.append(temp_list)
    response_str = finance_product_recommend_api(value_list)
    response_str = json.loads(response_str.text)
    predictions = response_str.get('predictions')
    values = predictions[0].get('values')
    for i in range(0, len(product_id_list)):
        value = values[i][0]
        res_df = res_df.append([{'finance_product_id': product_id_list[i],
                                 'value': float(value)}], ignore_index=True)
    res_df = res_df.sort_values(by=['value'], ascending=[False])
    id_list = res_df.iloc[0:3, 0].tolist()
    recommended_result = [
        {
            'finance_product_id': id_list[0],
            'description': 'recommended'
        },
        {
            'finance_product_id': id_list[1],
            'description': 'recommended'
        },
        {
            'finance_product_id': id_list[2],
            'description': 'recommended'
        }
    ]
    return_dict['finance_product_list'] = recommended_result
    return json.dumps(return_dict, ensure_ascii=False)


@app.route("/credit-rating", methods=['POST'])
def credit_rating():
    """
       credit_rating http api
    """
    return_dict = {'rescode': '200', 'credit-rating': '1', 'description': 'Good credit'}
    if request.get_data() is None:
        return_dict['rescode'] = '5004'
        return json.dumps(return_dict, ensure_ascii=False)
    role_dict = {'farmer': 1, 'consumer': 2}
    sex_dict = {'male': 1, 'female': 2}
    location_dict = {'Cantwell city, Alaska, USA': 1, 'Queens, New York, NY, USA': 2}
    description_dict = {'0': 'Bad credit', '1': 'Good credit'}
    get_data = request.get_data()
    get_data = json.loads(get_data)
    role_name = get_data.get('rolename')
    sex = get_data.get('sex')
    user_name = get_data.get('username')
    location = get_data.get('location')
    carbon_credit = get_data.get('carbon_credit')
    footprint_names = get_data.get('footprint_name')
    carbon_credit = int(carbon_credit)
    footprint_count_dict = {'Buy': 0, 'Fertilize': 0, 'Seed': 0}
    for ftn in footprint_names:
        if ftn.startswith('Buy'):
            footprint_count_dict['Buy'] = footprint_count_dict['Buy'] + 1
        elif ftn.startswith('Fertilize'):
            footprint_count_dict['Fertilize'] = footprint_count_dict['Fertilize'] + 1
        elif ftn.startswith('Seed'):
            footprint_count_dict['Seed'] = footprint_count_dict['Seed'] + 1
    x_predict_json = {
        'x0': sex_dict.get(sex),
        'x1': role_dict.get(role_name),
        'x2': location_dict.get(location),
        'x3': carbon_credit,
        'x4': footprint_count_dict['Seed'],
        'x5': footprint_count_dict['Buy'],
        'x6': footprint_count_dict['Fertilize']
    }
    value_dict = {'max_x0': 2, 'min_x0': 1, 'max_x1': 2, 'min_x1': 1, 'max_x2': 2, 'min_x2': 1, 'max_x3': 99,
                  'min_x3': 0, 'max_x4': 30, 'min_x4': 0, 'max_x5': 30, 'min_x5': 0, 'max_x6': 30, 'min_x6': 0}

    for i in range(7):
        x_predict_json['x' + str(i)] = normalization(x_predict_json['x' + str(i)], value_dict['max_x' + str(i)],
                                                     value_dict['min_x' + str(i)])
    body_json = {
        "head": {
            "serviceId": "cfc"
        },
        "body": {
            'featureData': x_predict_json,
            'sendToRemoteFeatureData': {
                'device_id': user_name
            }
        }
    }
    # guest node ip
    response = requests.post(
        'http://IP:8059/federation/v1/inference',
        data=json.dumps(body_json))
    response_data = json.loads(response.text).get('data')
    prob = response_data.get('prob')
    flag = "0"
    if float(prob) > 0.4:
        flag = "1"
    return_dict['credit-rating'] = flag
    return_dict['description'] = description_dict[flag]
    return json.dumps(return_dict, ensure_ascii=False)


def finance_product_recommend_api(value_list):
    """
      Ibm Watson ML API
    """
    api_key = "YOUR_API_KEY"
    token_response = requests.post('https://iam.cloud.ibm.com/identity/token',
                                   data={"apikey": api_key, "grant_type": 'urn:ibm:params:oauth:grant-type:apikey'})
    ml_token = token_response.json()["access_token"]

    payload_scoring = {"input_data": [{"fields": ['sex', 'rolename', 'location', 'carbon_credit', 'limit_0', 'period',
                                                  'organization'],
                                       "values": value_list}]}
    # You could obtain the url after model deployed on IBM Cloud platform.
    response_scoring = requests.post(
        'https://us-south.ml.cloud.ibm.com/ml/v4/deployments/XXXXX',
        json=payload_scoring, headers={'Authorization': 'Bearer ' + ml_token})
    return response_scoring


if __name__ == '__main__':
    app.debug = False
    app.run(host='0.0.0.0', port=5001)
