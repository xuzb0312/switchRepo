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
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      this.setData({ 
        userInfo,
        stats: {
          gameCount: userInfo.gameCount || 5,
          downloadCount: userInfo.downloadCount || 12,
          shareCount: userInfo.shareCount || 3
        }
      })
    }
  },

  onShow() {
    // 每次显示时刷新
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      this.setData({ userInfo })
    }
  },

  onLogin() {
    wx.login({
      success: async (res) => {
        if (res.code) {
          try {
            const result = await login(res.code)
            if (result.data) {
              // 保存用户信息
              wx.setStorageSync('userInfo', result.data)
              this.setData({ userInfo: result.data })
              wx.showToast({ title: '登录成功', icon: 'success' })
            }
          } catch (e) {
            // 模拟登录
            const mockUser = {
              id: 1,
              nickname: '游戏玩家',
              avatar: 'https://picsum.photos/100/100?random=100',
              level: 1,
              gameCount: 5,
              downloadCount: 12,
              shareCount: 3
            }
            wx.setStorageSync('userInfo', mockUser)
            this.setData({ 
              userInfo: mockUser,
              stats: {
                gameCount: 5,
                downloadCount: 12,
                shareCount: 3
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
          wx.removeStorageSync('userInfo')
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
