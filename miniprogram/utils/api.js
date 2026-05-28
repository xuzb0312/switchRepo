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

function getGameDetail(id) {
  return request(`/games/${id}`)
}

function login(code) {
  return request('/auth/login', { code }, 'POST')
}

function getUserInfo() {
  return request('/user/info')
}

module.exports = {
  getBanners,
  getGames,
  getGameDetail,
  login,
  getUserInfo
}
