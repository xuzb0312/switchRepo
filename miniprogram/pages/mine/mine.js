const { login } = require('../../utils/api.js')

Page({
  data: {
    userInfo: null,
    stats: {
      gameCount: 0,
      downloadCount: 0,
      shareCount: 0
    }
  },

  onLoad() {
    // 模拟已登录状态
    this.setData({
      userInfo: {
        nickname: '游戏玩家',
        avatar: 'https://picsum.photos/100/100?random=100',
        level: 1
      },
      stats: {
        gameCount: 5,
        downloadCount: 12,
        shareCount: 3
      }
    })
  },

  onLogin() {
    wx.login({
      success: async (res) => {
        if (res.code) {
          try {
            const result = await login(res.code)
            if (result.data) {
              this.setData({ userInfo: result.data })
            }
          } catch (e) {
            // 模拟登录成功
            this.setData({
              userInfo: {
                nickname: '玩家' + Math.floor(Math.random() * 1000),
                avatar: 'https://picsum.photos/100/100?random=100',
                level: 1
              }
            })
            wx.showToast({ title: '登录成功', icon: 'success' })
          }
        }
      }
    })
  },

  onLogout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          this.setData({ userInfo: null })
          wx.showToast({ title: '已退出', icon: 'none' })
        }
      }
    })
  },

  onMenuTap(e) {
    const type = e.currentTarget.dataset.type
    wx.showToast({ title: '功能开发中', icon: 'none' })
  }
})
