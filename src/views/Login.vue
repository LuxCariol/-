<template>
  <div class="page">
    <div style="padding: 12px">
      <div class="card" style="text-align: center; padding: 40px">
        <div style="font-size: 48px; margin-bottom: 12px">🎓</div>
        <h2 style="margin-bottom: 8px">校园零工</h2>
        <p style="color: #999; font-size: 14px; margin-bottom: 24px">信用支撑型零工平台 · 学籍认证登录</p>

        <input class="input" v-model="studentNo" placeholder="学号" />
        <input class="input" v-model="name" placeholder="姓名" />

        <button class="btn btn-primary" style="margin-bottom: 12px" @click="register">学籍认证注册</button>
        <button class="btn" style="background: #fff; color: #3cc51f; border: 1px solid #3cc51f" @click="login">已有账号直接登录</button>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../api/index.js'
export default {
  data() { return { studentNo: '', name: '' } },
  methods: {
    async register() {
      if (!this.studentNo || !this.name) { alert('请填写学号和姓名'); return }
      try {
        const res = await api.user.register({ studentNo: this.studentNo, name: this.name, college: '信息工程学院' })
        localStorage.setItem('token', res.token)
        localStorage.setItem('userId', res.user.id)
        this.$router.push('/')
      } catch(e) { alert(e.message) }
    },
    async login() {
      if (!this.studentNo) { alert('请输入学号'); return }
      try {
        const res = await api.user.login({ studentNo: this.studentNo })
        localStorage.setItem('token', res.token)
        localStorage.setItem('userId', res.user.id)
        this.$router.push('/')
      } catch(e) { alert(e.message) }
    }
  }
}
</script>
