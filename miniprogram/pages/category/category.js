const { getGames } = require('../../utils/api.js')

Page({
  data: {
    games: [],
    platforms: [
      { name: '全部', value: '' },
      { name: 'Switch', value: 'switch' },
      { name: 'PS5', value: 'ps5' },
      { name: 'PS4', value: 'ps4' },
      { name: 'Xbox', value: 'xbox' },
      { name: 'PC', value: 'pc' },
      { name: '工具', value: 'tool' }
    ],
    currentPlatform: '',
    searchKeyword: '',
    loading: false
  },

  onLoad(query) {
    if (query.platform) {
      this.setData({ currentPlatform: query.platform })
    }
    this.loadGames()
  },

  async loadGames() {
    this.setData({ loading: true })
    try {
      const res = await getGames({ 
        platform: this.data.currentPlatform, 
        keyword: this.data.searchKeyword 
      })
      this.setData({ games: res.data || [] })
    } catch (e) {
      this.setData({ games: this.getMockGames() })
    }
    this.setData({ loading: false })
  },

  getMockGames() {
    const all = [
      { id: 1, name: '宝可梦 朱', platform: 'Switch', type: 'NSZ', description: '宝可梦 朱 中文版', coverImage: 'https://picsum.photos/200/200?random=10' },
      { id: 2, name: '塞尔达传说：王国之泪', platform: 'Switch', type: 'NSZ', description: '官方中文 NSZ版', coverImage: 'https://picsum.photos/200/200?random=11' },
      { id: 3, name: '霍格沃茨之遗', platform: 'PS5', type: 'pkg', description: 'PS5中文版', coverImage: 'https://picsum.photos/200/200?random=12' },
      { id: 4, name: '生化危机4 重制版', platform: 'PS5', type: 'pkg', description: 'PS5 高清重制版', coverImage: 'https://picsum.photos/200/200?random=13' },
      { id: 5, name: '铁拳8', platform: 'PS5', type: 'pkg', description: 'PS5 格斗游戏', coverImage: 'https://picsum.photos/200/200?random=14' },
      { id: 6, name: '最终幻想16', platform: 'PS5', type: 'pkg', description: 'PS5 中文版', coverImage: 'https://picsum.photos/200/200?random=15' },
      { id: 7, name: '星露谷物语', platform: 'Switch', type: 'NSZ', description: '温馨农场经营游戏', coverImage: 'https://picsum.photos/200/200?random=16' },
      { id: 8, name: '奥日与精灵意志', platform: 'Xbox', type: 'XBOX', description: 'Xbox中文版', coverImage: 'https://picsum.photos/200/200?random=17' }
    ]
    if (this.data.currentPlatform) {
      return all.filter(g => g.platform.toLowerCase() === this.data.currentPlatform)
    }
    return all
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
