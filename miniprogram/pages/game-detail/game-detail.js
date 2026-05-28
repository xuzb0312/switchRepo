const { getGameDetail } = require('../../utils/api.js')

Page({
  data: {
    game: null,
    loading: true
  },

  onLoad(options) {
    this.loadDetail(options.id)
  },

  async loadDetail(id) {
    try {
      const res = await getGameDetail(id)
      this.setData({ game: res.data })
    } catch (e) {
      // 使用模拟数据
      this.setData({
        game: {
          id: 1,
          name: '宝可梦 朱',
          platform: 'Switch',
          type: 'NSZ',
          coverImage: 'https://picsum.photos/750/400?random=1',
          description: '《宝可梦 朱》是Game Freak开发的角色扮演游戏，为《宝可梦 剑盾》的续作。游戏包含1.0.1补丁，下载后可直接游玩。',
          downloadLinks: [
            { id: 1, type: '阿里云盘', name: '百度教育分流', url: 'https://www.aliyundrive.com/xxx' },
            { id: 2, type: '磁力链', name: '教育网盘', url: 'magnet:?xt=urn:btih:xxx' }
          ],
          images: [
            'https://picsum.photos/400/300?random=20',
            'https://picsum.photos/400/300?random=21',
            'https://picsum.photos/400/300?random=22'
          ]
        }
      })
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
      urls: this.data.game.images
    })
  }
})
