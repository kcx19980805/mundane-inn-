import Cookies from 'js-cookie'

const TokenKey = 'User-Token'
const UserId = 'User-Id'
const NikeName = 'User-NikeName'
const Avatar = 'User-Avatar'
export default {
  getToken() {
    return Cookies.get(TokenKey)
  },
  setToken(token) {
    return Cookies.set(TokenKey, token)
  },
  removeToken() {
    return Cookies.remove(TokenKey)
  },
  getUserId() {
    return Cookies.get(UserId)
  },
  setUserId(userId) {
    return Cookies.set(UserId, userId)
  },
  removeUserId() {
    return Cookies.remove(UserId)
  },
  getNikeName() {
    return Cookies.get(NikeName)
  },
  setNikeName(nikeName) {
    return Cookies.set(NikeName, nikeName)
  },
  removeNikeName() {
    return Cookies.remove(NikeName)
  },
  getAvatar() {
    return Cookies.get(Avatar)
  },
  setAvatar(avatar) {
    return Cookies.set(Avatar, avatar)
  },
  removeAvatar() {
    return Cookies.remove(Avatar)
  }
}
