<template>
  <div class="page">
    <div style="padding: 12px">
      <div class="card" v-if="task">
        <h2 style="margin-bottom: 12px">{{ task.title }}</h2>
        <div style="color: #f60; font-size: 18px; font-weight: bold; margin-bottom: 12px">{{ task.reward }} 信用分</div>
        <p style="font-size: 14px; color: #666; line-height: 1.6; margin-bottom: 16px">{{ task.description }}</p>
        <div style="background: #f9f9f9; padding: 12px; border-radius: 8px; margin-bottom: 16px; font-size: 13px">
          <div>信用分门槛: ≥{{ task.minCreditScore }}</div>
          <div v-if="task.minCreditScore < 720">需要担保人</div>
          <div v-else>免担保接单</div>
        </div>
        <button v-if="task.status === 'PENDING'" class="btn btn-primary" @click="accept">接单</button>
        <div v-if="task.status === 'PENDING'" style="margin-top: 8px; font-size: 12px; color: #999">
          <p>📌 路径A: 信用分600-719 + 至少1名担保人</p>
          <p>📌 路径B: 信用分≥720 免担保</p>
          <p>📌 路径C: 信用分&lt;600 需2名750分以上担保人</p>
        </div>
        <div v-else style="font-size: 14px; color: #999; text-align: center">状态: {{ statusMap[task.status] }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../api/index.js'
export default {
  data() {
    return { task: null, statusMap: { PENDING: '待接单', ACCEPTED: '已接单', IN_PROGRESS: '进行中', COMPLETED: '已完成', DISPUTED: '争议中' } }
  },
  async mounted() {
    try { this.task = await api.task.detail(this.$route.params.id) } catch(e) {}
  },
  methods: {
    async accept() {
      try {
        const msg = await api.task.accept(this.$route.params.id)
        alert(msg)
        this.task = await api.task.detail(this.$route.params.id)
      } catch(e) { alert(e.message) }
    },
    go(path) { this.$router.push(path) }
  }
}
</script>
