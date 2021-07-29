const state = {
    goodsList: [],
    totalPrice: 0,
    isChooseGCC: false,
    quickGoods: {},
    carbonCredit: 0,
    totalDonate: 0,
}

const mutations = {
    SET_GOODSLIST: (state, id) => {
        state.goodsList.push(id)
    },
    CLEAR_GOODSLIST: (state) => {
        state.goodsList = []
    },
    SET_TOTALPRICE: (state, price) => {
        state.totalPrice = price
    },
    SET_GCC: (state, choose) => {
        state.isChooseGCC = choose
    },
    SET_QUICKGOODS: (state, obj) => {
        state.quickGoods = obj
    },
    SET_CREDIT: (state, credit) => {
        state.carbonCredit = credit
    },
    CLEAR_CREDIT: (state) => {
        state.carbonCredit = 0
    },
    SET_TOTALDONATE: (state, donate) => {
        state.totalDonate = donate
    },

}
const actions = {
    setTotalDonate({ commit }, donate) {
        commit('SET_TOTALDONATE', donate)
    },
    setGoodsList({ commit }, id) {
        commit('SET_GOODSLIST', id)
    },
    clearGoodsList({ commit }) {
        commit('CLEAR_GOODSLIST')
    },
    setTotalPrice({ commit }, price) {
        commit('SET_TOTALPRICE', price)
    },
    setGreenCreditCard({ commit }, choose) {
        commit('SET_GCC', choose)
    },
    setQuickGoods({ commit }, obj) {
        commit('SET_QUICKGOODS', obj)
    },
    setCarbon({ commit }, credit) {
        commit('SET_CREDIT', credit)
    },
    clearCarbon({ commit }) {
        commit('CLEAR_CREDIT')
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}