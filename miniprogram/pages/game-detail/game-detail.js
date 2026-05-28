const { getGameDetail } = require('../../utils/api.js')

Page({
  data: {
    game: null
  },

  onLoad(options) {
    this.loadDetail(options.id)
  },

  async loadDetail(id) {
    wx.showLoading({ title: '加载中...' })
    try {
      const res = await getGameDetail(id)
      this.setData({ game: res.data })
    } catch (e) {
      console.error(e)
      wx.showToast({ title: '加载失败', icon: 'none' })
    }
    wx.hideLoading()
  },

  copyLink(e) {
    wx.setClipboardData({
      data: e.currentTarget.dataset.url,
      success: () => {
        wx.showToast({ title: '链接已复制', icon: 'success' })
      }
    })
  }
})
