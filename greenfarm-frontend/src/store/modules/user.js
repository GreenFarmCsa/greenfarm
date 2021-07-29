const state = {
  token: '',
  username: '',
  rolename: '',
  createTime: "",
  description: "",
  email: "",
  fullname: "",
  iconUrl: "",
  location: "",
  modifyTime: "",
  phone: "",
  remark: "",
  sex: "",
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
    localStorage.token = token
  },
  SET_USERNAME: (state, username) => {
    state.username = username
  },
  SET_ROLENAME: (state, rolename) => {
    state.rolename = rolename
  },
  SET_CREATETIME: (state, createTime) => {
    state.createTime = createTime
  },
  SET_DESCRIPTION: (state, description) => {
    state.description = description
  },
  SET_EMAIL: (state, email) => {
    state.email = email
  },
  SET_FULLNAME: (state, fullname) => {
    state.fullname = fullname
  },
  SET_ICONURL: (state, iconUrl) => {
    state.iconUrl = iconUrl
  },
  SET_LOCATION: (state, location) => {
    state.location = location
  },
  SET_MODIFYTIME: (state, modifyTime) => {
    state.modifyTime = modifyTime
  },
  SET_PHONE: (state, phone) => {
    state.phone = phone
  },
  SET_REMARK: (state, remark) => {
    state.remark = remark
  },
  SET_SEX: (state, sex) => {
    state.sex = sex
  }
}

const actions = {
  clearUserInfo({ commit }) {
    commit('SET_TOKEN', "")
    commit('SET_USERNAME', "")
    commit('SET_ROLENAME', "")
    commit('SET_CREATETIME', "")
    commit('SET_DESCRIPTION', "")
    commit('SET_EMAIL', "")
    commit('SET_FULLNAME', "")
    commit('SET_ICONURL', "")
    commit('SET_LOCATION', "")
    commit('SET_MODIFYTIME', "")
    commit('SET_PHONE', "")
    commit('SET_REMARK', "")
    commit('SET_SEX', "")
  },
  setToken({ commit }, token) {
    commit('SET_TOKEN', token)
  },
  setUserName({ commit }, username) {
    commit('SET_USERNAME', username)
  },
  setRoleName({ commit }, rolename) {
    commit('SET_ROLENAME', rolename)
  },
  setCreateTime({ commit }, createTime) {
    commit('SET_CREATETIME', createTime)
  },
  setDescription({ commit }, description) {
    commit('SET_DESCRIPTION', description)
  },
  setEmail({ commit }, email) {
    commit('SET_EMAIL', email)
  },
  setFullname({ commit }, fullname) {
    commit('SET_FULLNAME', fullname)
  },
  setIconUrl({ commit }, iconUrl) {
    commit('SET_ICONURL', iconUrl)
  },
  setLocation({ commit }, location) {
    commit('SET_LOCATION', location)
  },
  setModifyTime({ commit }, modifyTime) {
    commit('SET_MODIFYTIME', modifyTime)
  },
  setPhone({ commit }, phone) {
    commit('SET_PHONE', phone)
  },
  setRemark({ commit }, remark) {
    commit('SET_REMARK', remark)
  },
  setSex({ commit }, sex) {
    commit('SET_SEX', sex)
  },

}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
