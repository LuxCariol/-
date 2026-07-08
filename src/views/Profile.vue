<template>
  <div class="page">
    <div class="header" v-if="credit">
      <div class="header-score">{{ credit.score || 600 }}</div>
      <div class="header-level">{{ credit.level || '青铜小白' }}</div>
    </div>

    <div style="padding: 12px">
      <div class="card" style="padding: 0">
        <div class="menu-item" @click="go('/tasks')"><span>📋 我的任务</span><span class="menu-arrow">›</span></div>
        <div class="menu-item" @click="go('/guarantor')"><span>🛡️ 我的担保人</span><span class="menu-arrow">›</span></div>
      </div>

      <div class="card" style="margin-top: 12px">
        <h4 style="margin-bottom: 8px">💰 信用分规则</h4>
        <div style="font-size: 13px; color: #666; line-height: 1.8">
          <div><strong>履约质量</strong> 55% - 贝叶斯平均修正</div>
          <div><strong>担保人质量</strong> 20% - 取中位数</div>
          <div><strong>近期活跃度</strong> 15% - 不活跃不扣分</div>
          <div><strong>社交贡献</strong> 10% - 邀请+担保</div>
        </div>
      </div>

      <div class="card" style="margin-top: 12px">
        <h4 style="margin-bottom: 8px">🏆 信用等级</h4>
        <div style="font-size: 13px; line-height: 1.8">
          <div>600-649: 青铜小白</div>
          <div>650-699: 白银跑腿</div>
          <div>700-749: 黄金学长</div>
          <div>750+: 钻石担保人</div>
        </div>
      </div>

      <button class="btn btn-danger" style="margin-top: 24px" @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script>
import { api } from '../api/index.js'
export default {
  data() {
    return { userId: '', credit: {} }
  },
  async mounted() {
    this.userId = localStorage.getItem('userId')
    if (this.userId) {
      try { this.credit = await api.user.getCredit(this.userId) || {} } catch(e) {}
    }
  },
  methods: {
    go(path) { this.$router.push(path) },
    logout() {
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      this.$router.push('/login')
    }
  }
}
</script>
