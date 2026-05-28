const { getGames, searchGames } = require('../../utils/api.js')

Page({
  data: {
    games: [],
    platforms: [
      { name: '全部', value: '' },
      { name: 'Switch', value: 'switch' },
      { name: 'PS5', value: 'ps5' },
      { name: 'Xbox', value: 'xbox' },
      { name: 'PC', value: 'pc' }
    ],
    currentPlatform: '',
    searchKeyword: ''
  },

  onLoad() {
    this.loadGames()
  },

  async loadGames() {
    wx.showLoading({ title: '加载中...' })
    try {
      const res = await getGames({
        platform: this.data.currentPlatform,
        keyword: this.data.searchKeyword
      })
      this.setData({ games: res.data })
    } catch (e) {
      console.error(e)
      wx.showToast({ title: '加载失败', icon: 'none' })
    }
    wx.hideLoading()
  },

  onSearch(e) {
    this.setData({ searchKeyword: e.detail.value })
    this.loadGames()
  },

  onSelectPlatform(e) {
    this.setData({ currentPlatform: e.currentTarget.dataset.platform })
    this.loadGames()
  },

  goToDetail(e) {
    wx.navigateTo({
      url: `/pages/game-detail/game-detail?id=${e.currentTarget.dataset.id}`
    })
  }
})
