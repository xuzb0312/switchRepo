const app = getApp()

function request(url, data = {}, method = 'GET') {
  return new Promise((resolve, reject) => {
    wx.request({
      url: app.globalData.apiBase + url,
      data,
      method,
      header: {
        'Content-Type': 'application/json'
      },
      success: resolve,
      fail: reject
    })
  })
}

function getGames(params) {
  return request('/games', params)
}

function getGameDetail(id) {
  return request(`/games/${id}`)
}

function login(code) {
  return request('/auth/login', { code }, 'POST')
}

module.exports = {
  getGames,
  getGameDetail,
  login
}
