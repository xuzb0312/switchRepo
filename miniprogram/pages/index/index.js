const { getGames, getBanners } = require('../../utils/api.js')

Page({
  data: {
    banners: [],
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

  onLoad() {
    this.loadData()
  },

  async loadData() {
    this.setData({ loading: true })
    try {
      const [bannerRes, gameRes] = await Promise.all([
        getBanners(),
        getGames({ platform: this.data.currentPlatform, keyword: this.data.searchKeyword })
      ])
      this.setData({ 
        banners: bannerRes.data || [],
        games: gameRes.data || []
      })
    } catch (e) {
      console.error(e)
      // 使用模拟数据
      this.setData({ 
        banners: this.getMockBanners(),
        games: this.getMockGames()
      })
    }
    this.setData({ loading: false })
  },

  getMockBanners() {
    return [
      { id: 1, image: 'https://picsum.photos/750/400?random=1', title: '宝可梦 朱', subtitle: 'Switch中文版' },
      { id: 2, image: 'https://picsum.photos/750/400?random=2', title: '塞尔达传说', subtitle: '王国之泪' },
      { id: 3, image: 'https://picsum.photos/750/400?random=3', title: '霍格沃茨之遗', subtitle: 'PS5中文版' }
    ]
  },

  getMockGames() {
    return [
      { id: 1, name: '宝可梦 朱', platform: 'Switch', type: 'NSZ', description: '宝可梦 朱 中文版 包含1.0.1补丁', coverImage: 'https://picsum.photos/200/200?random=10' },
      { id: 2, name: '塞尔达传说：王国之泪', platform: 'Switch', type: 'NSZ', description: '官方中文 NSZ版', coverImage: 'https://picsum.photos/200/200?random=11' },
      { id: 3, name: '霍格沃茨之遗', platform: 'PS5', type: ' pkg', description: 'PS5中文版 含全部DLC', coverImage: 'https://picsum.photos/200/200?random=12' },
      { id: 4, name: '生化危机4 重制版', platform: 'PS5', type: 'pkg', description: 'PS5 高清重制版', coverImage: 'https://picsum.photos/200/200?random=13' },
      { id: 5, name: '铁拳8', platform: 'PS5', type: 'pkg', description: 'PS5 格斗游戏', coverImage: 'https://picsum.photos/200/200?random=14' },
      { id: 6, name: '最终幻想16', platform: 'PS5', type: 'pkg', description: 'PS5 中文版', coverImage: 'https://picsum.photos/200/200?random=15' }
    ]
  },

  onSearch(e) {
    this.setData({ searchKeyword: e.detail.value })
    this.loadData()
  },

  onSearchTap() {
    wx.showToast({ title: '搜索功能', icon: 'none' })
  },

  onSelectPlatform(e) {
    this.setData({ currentPlatform: e.currentTarget.dataset.platform })
    this.loadData()
  },

  onViewMore() {
    wx.switchTab({ url: '/pages/category/category' })
  },

  goToDetail(e) {
    wx.navigateTo({
      url: `/pages/game-detail/game-detail?id=${e.currentTarget.dataset.id}`
    })
  }
})
