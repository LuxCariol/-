<template>
  <div class="page">
    <div style="padding: 12px">
      <div class="card">
        <h3 style="margin-bottom: 12px">🛡️ 我的担保人</h3>
        <div v-for="g in guarantors" :key="g.id" style="padding: 8px 0; border-bottom: 1px solid #f0f0f0; display: flex; justify-content: space-between; font-size: 14px">
          <span>担保人ID: {{ g.guarantorId }}</span>
          <span class="tag tag-active">有效</span>
        </div>
        <div v-if="!guarantors.length" class="empty">暂无担保人</div>
      </div>

      <div class="card">
        <h3 style="margin-bottom: 12px">➕ 添加担保人</h3>
        <div style="display: flex; gap: 8px">
          <input class="input" v-model="guarantorId" placeholder="输入担保人ID" style="margin-bottom: 0; flex: 1" />
          <button class="btn btn-primary" style="width: auto; padding: 0 16px" @click="invite">邀请</button>
        </div>
      </div>

      <div class="card" style="background: #fff8e1">
        <h4 style="margin-bottom: 8px">📌 担保规则</h4>
        <div style="font-size: 13px; color: #666; line-height: 1.8">
          <div>• 每人最多3个担保人</div>
          <div>• 每人最多为5人担保</div>
          <div>• 信用分≥720可免担保接单</div>
          <div>• ≤600分需2名≥750分用户担保</div>
          <div>• 违约按等级连带扣分</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../api/index.js'
export default {
  data() { return { guarantors: [], guarantorId: '' } },
  async mounted() {
    try { this.guarantors = await api.guarantor.list() || [] } catch(e) { this.guarantors = [] }
  },
  methods: {
    async invite() {
      if (!this.guarantorId) { alert('请输入担保人ID'); return }
      try {
        await api.guarantor.invite(this.guarantorId)
        alert('邀请成功')
        this.guarantors = await api.guarantor.list() || []
      } catch(e) { alert(e.message) }
    }
  }
}
</script>
