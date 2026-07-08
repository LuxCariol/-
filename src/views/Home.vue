<template>
  <div class="page">
    <div class="header" v-if="user">
      <div class="header-score">{{ credit.score || 600 }}</div>
      <div class="header-level">{{ credit.level || '青铜小白' }}</div>
    </div>

    <div style="padding: 12px">
      <div class="card" @click="go('/tasks')">
        <h3 style="margin-bottom: 8px">📋 附近任务</h3>
        <div v-for="t in tasks" :key="t.id" style="padding: 8px 0; border-bottom: 1px solid #f0f0f0; font-size: 14px; display: flex; justify-content: space-between">
          <span>{{ t.title }}</span>
          <span style="color: #f60">{{ t.reward }}分</span>
        </div>
        <div v-if="!tasks.length" class="empty">暂无任务</div>
      </div>

      <div class="card">
        <h3 style="margin-bottom: 8px">⚡ 快捷操作</h3>
        <div style="display: flex; gap: 8px">
          <button class="btn btn-primary" style="flex:1" @click="go('/publish')">发布任务</button>
          <button class="btn btn-primary" style="flex:1" @click="go('/tasks')">查看任务</button>
        </div>
      </div>

      <div class="card">
        <h3 style="margin-bottom: 8px">📌 品类</h3>
        <div style="display: flex; gap: 8px; font-size: 14px">
          <span class="tag tag-running">跑腿</span>
          <span class="tag tag-disabled" style="opacity:0.5">技能(筹备中)</span>
          <span class="tag tag-disabled" style="opacity:0.5">才艺(筹备中)</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../api/index.js'

export default {
  data() {
    return { user: null, credit: {}, tasks: [] }
  },
  async mounted() {
    const userId = localStorage.getItem('userId')
    if (userId) {
      try {
        this.credit = await api.user.getCredit(userId) || {}
      } catch (e) {}
    }
    try {
      this.tasks = await api.task.list() || []
    } catch (e) { this.tasks = [] }
  },
  methods: {
    go(path) { this.$router.push(path) }
  }
}
</script>
