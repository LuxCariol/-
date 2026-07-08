<template>
  <div class="page">
    <div style="padding: 12px">
      <div class="card">
        <h3 style="margin-bottom: 12px">发布任务</h3>
        <input class="input" v-model="title" placeholder="标题 (必填)" />
        <textarea class="textarea" v-model="description" placeholder="描述 (必填)"></textarea>
        <input class="input" v-model.number="reward" placeholder="报酬/信用分 (必填)" type="number" />
        <div style="background: #f9f9f9; padding: 12px; border-radius: 8px; margin-bottom: 12px; font-size: 13px">
          <div style="margin-bottom: 8px"><strong>品类:</strong> 跑腿(首期)</div>
          <div style="margin-bottom: 4px"><strong>信用分门槛:</strong></div>
          <input class="input" v-model.number="minCreditScore" placeholder="默认600" type="number" />
          <div style="color: #999; font-size: 12px; margin-top: 4px">门槛≤719需担保人，≥720免担保</div>
        </div>
        <button class="btn btn-primary" @click="publish">发布</button>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../api/index.js'
export default {
  data() {
    return { title: '', description: '', reward: 0, minCreditScore: 600 }
  },
  methods: {
    async publish() {
      if (!this.title || !this.description || !this.reward) { alert('请填写必填项'); return }
      try {
        await api.task.publish({ title: this.title, description: this.description, reward: this.reward, category: 'running', minCreditScore: this.minCreditScore || 600 })
        alert('发布成功')
        this.$router.push('/tasks')
      } catch(e) { alert(e.message) }
    }
  }
}
</script>
