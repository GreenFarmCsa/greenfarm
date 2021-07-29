const state = {
    selected: 'dashboard',
    appflg: false
}
const mutations = {
    SET_SELECTED: (state, id) => {
        state.selected = id
    },
    APP_FLG: (state, flg) => {
        state.appflg = flg
    }
}
const actions = {
    // handle nav
    handleClick({ commit }, id) {
        commit('SET_SELECTED', id)
    }
}
export default {
    namespaced: true,
    state,
    mutations,
    actions
}