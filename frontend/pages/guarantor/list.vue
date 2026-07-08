<template>
  <view class="guarantor">
    <view class="section">
      <text class="section-title">我的担保人</text>
      <view class="guarantor-item" v-for="item in guarantors" :key="item.id">
        <text>担保人ID: {{ item.guarantorId }}</text>
        <text class="status-tag active">有效</text>
      </view>
      <view class="empty" v-if="guarantors.length === 0">
        <text>暂无担保人</text>
      </view>
    </view>

    <view class="section">
      <text class="section-title">添加担保人</text>
      <view class="invite-form">
        <input class="input" v-model="guarantorId" placeholder="输入担保人的学号或用户ID" />
        <button class="btn-invite" @click="handleInvite">发送邀请</button>
      </view>
    </view>

    <view class="rules">
      <text class="rules-title">担保规则</text>
      <text class="rules-text">• 每人最多3个担保人</text>
      <text class="rules-text">• 每人最多为5人担保</text>
      <text class="rules-text">• 信用分≥720可免担保接单</text>
      <text class="rules-text">• ≤600分需要至少2名高信用(≥750)用户担保</text>
    </view>
  </view>
</template>

<script>
import { guarantorApi } from '../../api/index';

export default {
  data() { return { guarantors: [], guarantorId: '' }; },
  onShow() { this.loadGuarantors(); },
  methods: {
    async loadGuarantors() {
      try { this.guarantors = await guarantorApi.list() || []; }
      catch (e) { this.guarantors = []; }
    },
    async handleInvite() {
      if (!this.guarantorId) { uni.showToast({ title: '请输入担保人ID', icon: 'none' }); return; }
      try {
        const msg = await guarantorApi.invite(this.guarantorId);
        uni.showToast({ title: msg });
        this.guarantorId = '';
        this.loadGuarantors();
      } catch (e) {}
    }
  }
};
</script>

<style>
.guarantor { padding: 30rpx; background: #f5f5f5; min-height: 100vh; }
.section { background: #fff; border-radius: 16rpx; padding: 30rpx; margin-bottom: 20rpx; }
.section-title { font-size: 32rpx; font-weight: bold; display: block; margin-bottom: 20rpx; }
.guarantor-item { display: flex; justify-content: space-between; padding: 20rpx 0; border-bottom: 1px solid #f0f0f0; font-size: 28rpx; }
.status-tag { padding: 4rpx 16rpx; border-radius: 8rpx; font-size: 24rpx; }
.status-tag.active { background: #e8f5e9; color: #2e7d32; }
.invite-form { display: flex; gap: 20rpx; }
.input { flex: 1; height: 70rpx; border: 1px solid #e0e0e0; border-radius: 12rpx; padding: 0 20rpx; font-size: 28rpx; }
.btn-invite { height: 70rpx; line-height: 70rpx; background: #3cc51f; color: #fff; border-radius: 12rpx; font-size: 28rpx; padding: 0 40rpx; }
.empty { text-align: center; padding: 40rpx; color: #999; }
.rules { background: #fff8e1; border-radius: 16rpx; padding: 30rpx; }
.rules-title { font-size: 28rpx; font-weight: bold; display: block; margin-bottom: 16rpx; }
.rules-text { font-size: 26rpx; color: #666; line-height: 1.8; display: block; }
</style>
