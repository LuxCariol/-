<template>
  <view class="task-list">
    <view class="task-item" v-for="item in tasks" :key="item.id" @click="goDetail(item.id)">
      <view class="item-header">
        <text class="item-title">{{ item.title }}</text>
        <text class="item-reward">{{ item.reward }}分</text>
      </view>
      <view class="item-body">
        <text class="item-desc">{{ item.description }}</text>
      </view>
      <view class="item-footer">
        <text class="item-category">{{ categoryMap[item.category] }}</text>
        <text class="item-status">{{ statusMap[item.status] }}</text>
      </view>
    </view>
    <view class="empty" v-if="tasks.length === 0">
      <text>暂无任务</text>
    </view>
    <button class="publish-btn" @click="goPublish">发布任务</button>
  </view>
</template>

<script>
import { taskApi } from '../../api/index';

export default {
  data() {
    return {
      tasks: [],
      categoryMap: { running: '跑腿', skill: '技能', art: '才艺' },
      statusMap: { PENDING: '待接单', ACCEPTED: '已接单', IN_PROGRESS: '进行中', COMPLETED: '已完成', DISPUTED: '争议中' }
    };
  },
  onShow() { this.loadTasks(); },
  methods: {
    async loadTasks() {
      try { this.tasks = await taskApi.list() || []; }
      catch (e) { this.tasks = []; }
    },
    goDetail(id) { uni.navigateTo({ url: `/pages/task/detail?id=${id}` }); },
    goPublish() { uni.navigateTo({ url: '/pages/task/publish' }); }
  }
};
</script>

<style>
.task-list { padding: 20rpx; background: #f5f5f5; min-height: 100vh; }
.task-item { background: #fff; border-radius: 16rpx; padding: 30rpx; margin-bottom: 20rpx; }
.item-header { display: flex; justify-content: space-between; margin-bottom: 10rpx; }
.item-title { font-size: 32rpx; font-weight: bold; }
.item-reward { color: #f60; font-weight: bold; }
.item-body { margin-bottom: 10rpx; }
.item-desc { font-size: 26rpx; color: #666; }
.item-footer { display: flex; justify-content: space-between; }
.item-category { background: #e8f5e9; color: #2e7d32; padding: 4rpx 16rpx; border-radius: 8rpx; font-size: 24rpx; }
.item-status { font-size: 24rpx; color: #999; }
.empty { text-align: center; padding: 100rpx; color: #999; }
.publish-btn { position: fixed; bottom: 40rpx; left: 40rpx; right: 40rpx; height: 88rpx; line-height: 88rpx; background: #3cc51f; color: #fff; border-radius: 44rpx; font-size: 32rpx; }
</style>
