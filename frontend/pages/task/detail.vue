<template>
  <view class="detail">
    <view class="card" v-if="task">
      <text class="title">{{ task.title }}</text>
      <text class="reward">{{ task.reward }} 信用分</text>
      <text class="desc">{{ task.description }}</text>
      <view class="info">
        <text>信用分门槛: ≥{{ task.minCreditScore }}</text>
        <text v-if="task.minCreditScore < 720">需要担保人</text>
        <text v-if="task.minCreditScore >= 720">免担保接单</text>
      </view>
      <button class="btn-accept" v-if="task.status === 'PENDING'" @click="handleAccept">接单</button>
      <button class="btn-complete" v-if="task.executorId === userId && task.status === 'ACCEPTED'" @click="handleComplete">完成任务</button>
      <text class="status">{{ statusMap[task.status] }}</text>
    </view>
  </view>
</template>

<script>
import { taskApi } from '../../api/index';

export default {
  data() {
    return {
      task: null,
      userId: '',
      statusMap: { PENDING: '待接单', ACCEPTED: '已接单', IN_PROGRESS: '进行中', COMPLETED: '已完成', DISPUTED: '争议中' }
    };
  },
  onLoad(options) {
    this.taskId = options.id;
    this.userId = uni.getStorageSync('userId');
    this.loadTask();
  },
  methods: {
    async loadTask() {
      try { this.task = await taskApi.detail(this.taskId); }
      catch (e) { uni.showToast({ title: '任务不存在', icon: 'none' }); }
    },
    async handleAccept() {
      try {
        const msg = await taskApi.accept(this.taskId);
        uni.showToast({ title: '接单成功' });
        this.loadTask();
      } catch (e) {}
    },
    async handleComplete() {
      try {
        await taskApi.complete(this.taskId);
        uni.showToast({ title: '任务已完成' });
        this.loadTask();
      } catch (e) {}
    }
  }
};
</script>

<style>
.detail { padding: 30rpx; background: #f5f5f5; min-height: 100vh; }
.card { background: #fff; border-radius: 16rpx; padding: 40rpx; }
.title { font-size: 36rpx; font-weight: bold; display: block; margin-bottom: 20rpx; }
.reward { color: #f60; font-size: 32rpx; font-weight: bold; display: block; margin-bottom: 30rpx; }
.desc { font-size: 28rpx; color: #666; line-height: 1.6; margin-bottom: 30rpx; }
.info { background: #f9f9f9; padding: 20rpx; border-radius: 12rpx; margin-bottom: 40rpx; }
.info text { display: block; font-size: 26rpx; color: #333; margin-bottom: 8rpx; }
.btn-accept, .btn-complete { width: 100%; height: 88rpx; line-height: 88rpx; border-radius: 16rpx; font-size: 32rpx; color: #fff; }
.btn-accept { background: #3cc51f; }
.btn-complete { background: #667eea; }
.status { display: block; text-align: center; margin-top: 20rpx; color: #999; }
</style>
