const getters = {
  token: state => state.user.token,
  type: state => state.user.type,
  username: state => state.user.username,
  selected: state => state.indexNav.selected,
  appflg: state => state.indexNav.appflg,
}
export default getters