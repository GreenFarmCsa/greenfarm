import axios from 'axios'
import { Toast } from "vant";
import router from '@/router'
import mocks from '../../mock'
import store from '@/store'

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000,
  withCredentials: true
})

service.interceptors.request.use(
  config => {
    for (const mock of mocks) {
      if (mock.url != undefined) {
        let murl = (mock.url + "").replace(/\\\//g, "\/")
        let curl = config.url + ""
        if (murl.indexOf(curl) != -1) {
          config.url = "/dev-api" + curl
        }
      }
    }
    return config
  },
  error => {
    console.log(error) // for debug
    Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    if (response.data.resultCode == "OK") {
      const res = response.data
      if (res.data == null) {
        res.data = []
      }
      return res
    } else if (response.data.resultCode == "UNAUTHENTICATED") {
      store.dispatch("user/clearUserInfo", "");
      router.push({ path: "/login" });
    } else if (response.data.resultCode == "ERROR") {
      Toast({
        message: response.data.message,
        duration: 2 * 1000
      });
      return Promise.reject('error')
    } else if (response.data.resultCode == "FAULT") {
      Toast({
        message: "Operation failed, please try again.",
        duration: 2 * 1000
      });
      return Promise.reject('error')
    } else if (response.data.resultCode == "UNAUTHORIZED") {
      Toast({
        message: "Current user is a guest, please login and try again.",
        duration: 3 * 1000
      });
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    Toast({
      message: error.message,
      type: 'fail',
      duration: 2 * 1000
    });
    return Promise.reject(error)
  }
)

export default service
