<template>
  <div class="page">
    <div style="padding: 12px">
      <div class="card">
        <h3 style="margin-bottom: 12px">📋 任务列表</h3>
        <div v-for="t in tasks" :key="t.id" style="padding: 12px 0; border-bottom: 1px solid #f0f0f0; cursor: pointer" @click="go('/tasks/' + t.id)">
          <div style="display: flex; justify-content: space-between; margin-bottom: 4px">
            <strong>{{ t.title }}</strong>
            <span style="color: #f60; font-weight: bold">{{ t.reward }}分</span>
          </div>
          <div style="font-size: 13px; color: #666; margin-bottom: 4px">{{ t.description }}</div>
          <div style="display: flex; gap: 8px; font-size: 12px">
            <span class="tag" :class="'tag-' + t.category">{{ categoryMap[t.category] || t.category }}</span>
            <span style="color: #999">{{ statusMap[t.status] || t.status }}</span>
          </div>
        </div>
        <div v-if="!tasks.length" class="empty">暂无任务</div>
      </div>
      <button class="btn btn-primary" style="margin-top: 8px" @click="go('/publish')">发布任务</button>
    </div>
  </div>
</template>

<script>
import { api } from '../api/index.js'
export default {
  data() {
    return {
      tasks: [],
      categoryMap: { running: '跑腿', skill: '技能', art: '才艺' },
      statusMap: { PENDING: '待接单', ACCEPTED: '已接单', IN_PROGRESS: '进行中', COMPLETED: '已完成', DISPUTED: '争议中' }
    }
  },
  async mounted() {
    try { this.tasks = await api.task.list() || [] } catch(e) { this.tasks = [] }
  },
  methods: { go(path) { this.$router.push(path) } }
}
</script>
