const app = getApp()

const API_BASE = 'http://localhost:8080/api'

function request(url, data = {}, method = 'GET') {
  return new Promise((resolve, reject) => {
    wx.request({
      url: API_BASE + url,
      data,
      method,
      header: { 'Content-Type': 'application/json' },
      success: resolve,
      fail: reject
    })
  })
}

function getBanners() {
  return request('/banners')
}

function getGames(params = {}) {
  return request('/games', params)
}

function getGameDetail(id, userId) {
  let url = `/games/${id}`
  if (userId) url += `?userId=${userId}`
  return request(url)
}

function login(code) {
  return request('/auth/login', { code }, 'POST')
}

function getUserInfo(userId) {
  return request('/auth/userinfo', { userId })
}

function checkUnlock(userId, gameId) {
  return request('/unlock/check', { userId, gameId })
}

function unlockGame(userId, gameId, adType = 'rewarded') {
  return request('/unlock', { userId, gameId, adType }, 'POST')
}

module.exports = {
  getBanners,
  getGames,
  getGameDetail,
  login,
  getUserInfo,
  checkUnlock,
  unlockGame
}
