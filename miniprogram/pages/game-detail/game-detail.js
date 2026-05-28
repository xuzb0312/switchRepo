const { getGameDetail, checkUnlock, unlockGame } = require('../../utils/api.js')

Page({
  data: {
    game: null,
    loading: true,
    unlocked: false,
    userId: null
  },

  onLoad(options) {
    // 获取本地存储的用户ID
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo && userInfo.id) {
      this.setData({ userId: userInfo.id })
    }
    this.loadDetail(options.id)
  },

  async loadDetail(id) {
    try {
      const res = await getGameDetail(id, this.data.userId)
      this.setData({ 
        game: res.data,
        unlocked: res.data.unlocked || false
      })
    } catch (e) {
      console.error(e)
      wx.showToast({ title: '加载失败', icon: 'none' })
    }
    this.setData({ loading: false })
  },

  copyLink(e) {
    wx.setClipboardData({
      data: e.currentTarget.dataset.url,
      success: () => {
        wx.showToast({ title: '链接已复制', icon: 'success' })
      }
    })
  },

  previewImage(e) {
    wx.previewImage({
      current: e.currentTarget.dataset.src,
      urls: this.data.game.images || []
    })
  },

  watchAd() {
    // 模拟观看广告
    wx.showLoading({ title: '请稍候...' })
    
    // 模拟广告播放（实际微信小程序需要接入真实的激励视频广告）
    setTimeout(() => {
      wx.hideLoading()
      this.doUnlock()
    }, 2000)
  },

  async doUnlock() {
    if (!this.data.userId) {
      wx.showToast({ title: '请先登录', icon: 'none' })
      return
    }
    
    try {
      await unlockGame(this.data.userId, this.data.game.id)
      this.setData({ unlocked: true })
      wx.showToast({ title: '解锁成功！', icon: 'success' })
    } catch (e) {
      console.error(e)
      wx.showToast({ title: '解锁失败', icon: 'none' })
    }
  }
})
