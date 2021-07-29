import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const InfoKey = 'Admin-Info'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setInfos(info){
    return Cookies.set(InfoKey,info)
}
export function getInfos(info){
  const infos = Cookies.get(InfoKey)
  if(infos){
    return JSON.parse(infos)[info]
  }else{
    return ''
  }
}
export function removeInfos() {
    return Cookies.remove(InfoKey)
}