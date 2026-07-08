<template>
  <div>
    <router-view />
    <div v-if="showTabBar" class="tab-bar">
      <div class="tab-item" :class="{ active: $route.path === '/' }" @click="go('/')">
        <span class="tab-icon">🏠</span>首页
      </div>
      <div class="tab-item" :class="{ active: $route.path === '/tasks' }" @click="go('/tasks')">
        <span class="tab-icon">📋</span>任务
      </div>
      <div class="tab-item" :class="{ active: $route.path === '/profile' }" @click="go('/profile')">
        <span class="tab-icon">👤</span>我的
      </div>
    </div>
    <div v-if="toastText" class="toast">{{ toastText }}</div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return { toastText: '' }
  },
  computed: {
    showTabBar() {
      return ['/', '/tasks', '/profile'].includes(this.$route.path)
    }
  },
  methods: {
    go(path) {
      this.$router.push(path)
    },
    toast(msg) {
      this.toastText = msg
      setTimeout(() => { this.toastText = '' }, 2000)
    }
  },
  provide() {
    return { toast: this.toast }
  }
}
</script>
