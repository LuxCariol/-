<template>
  <view class="profile">
    <view class="credit-section">
      <text class="score">{{ creditInfo.score || 600 }}</text>
      <text class="level">{{ creditInfo.level || '青铜小白' }}</text>
    </view>

    <view class="menu">
      <view class="menu-item" @click="goGuarantor">
        <text>我的担保人</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goMyTasks">
        <text>我的任务</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goCreditLog">
        <text>信用分记录</text>
        <text class="arrow">›</text>
      </view>
    </view>

    <button class="btn-logout" @click="handleLogout">退出登录</button>
  </view>
</template>

<script>
import { userApi } from '../../api/index';

export default {
  data() {
    return { creditInfo: {}, userId: '' };
  },
  onShow() {
    this.userId = uni.getStorageSync('userId');
    this.loadCredit();
  },
  methods: {
    async loadCredit() {
      try {
        if (this.userId) this.creditInfo = await userApi.getCredit(this.userId) || {};
      } catch (e) {}
    },
    goGuarantor() { uni.navigateTo({ url: '/pages/guarantor/list' }); },
    goMyTasks() { uni.navigateTo({ url: '/pages/task/list?my=1' }); },
    goCreditLog() { uni.navigateTo({ url: '/pages/user/creditLog' }); },
    handleLogout() {
      uni.removeStorageSync('token');
      uni.removeStorageSync('userId');
      uni.reLaunch({ url: '/pages/user/login' });
    }
  }
};
</script>

<style>
.profile { background: #f5f5f5; min-height: 100vh; }
.credit-section { background: linear-gradient(135deg, #667eea, #764ba2); padding: 80rpx 40rpx; text-align: center; color: #fff; }
.score { font-size: 100rpx; font-weight: bold; display: block; }
.level { font-size: 32rpx; opacity: 0.9; margin-top: 10rpx; display: block; }
.menu { background: #fff; margin-top: 20rpx; border-radius: 16rpx; margin: 20rpx; padding: 0 20rpx; }
.menu-item { display: flex; justify-content: space-between; align-items: center; padding: 30rpx 0; border-bottom: 1px solid #f0f0f0; font-size: 28rpx; }
.arrow { color: #ccc; font-size: 36rpx; }
.btn-logout { margin: 60rpx 40rpx; height: 88rpx; line-height: 88rpx; background: #fff; color: #f44336; border: 1px solid #f44336; border-radius: 16rpx; font-size: 32rpx; }
</style>
