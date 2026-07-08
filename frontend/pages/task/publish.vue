<template>
  <view class="publish">
    <view class="form">
      <input class="input" v-model="title" placeholder="标题 (必填)" />
      <textarea class="textarea" v-model="description" placeholder="描述 (必填)" />
      <input class="input" v-model.number="reward" placeholder="报酬 (信用分) (必填)" type="number" />
      <view class="picker-row">
        <text class="label">品类</text>
        <text class="value">跑腿(首期)</text>
      </view>
      <view class="picker-row">
        <text class="label">信用分门槛</text>
        <input class="input-inline" v-model.number="minCreditScore" placeholder="默认600" type="number" />
      </view>
      <view class="tip">
        <text>门槛≤719需担保人，≥720免担保</text>
      </view>
      <button class="btn-publish" @click="handlePublish">发布任务</button>
    </view>
  </view>
</template>

<script>
import { taskApi } from '../../api/index';

export default {
  data() {
    return { title: '', description: '', reward: 0, minCreditScore: 600 };
  },
  methods: {
    async handlePublish() {
      if (!this.title || !this.description || !this.reward) {
        uni.showToast({ title: '请填写必填项', icon: 'none' });
        return;
      }
      try {
        await taskApi.publish({
          title: this.title,
          description: this.description,
          reward: this.reward,
          category: 'running',
          minCreditScore: this.minCreditScore || 600
        });
        uni.showToast({ title: '发布成功' });
        setTimeout(() => uni.navigateBack(), 1500);
      } catch (e) {}
    }
  }
};
</script>

<style>
.publish { padding: 30rpx; background: #f5f5f5; min-height: 100vh; }
.form { background: #fff; border-radius: 16rpx; padding: 30rpx; }
.input { height: 80rpx; border: 1px solid #e0e0e0; border-radius: 12rpx; padding: 0 24rpx; font-size: 28rpx; margin-bottom: 30rpx; }
.textarea { height: 200rpx; border: 1px solid #e0e0e0; border-radius: 12rpx; padding: 24rpx; font-size: 28rpx; margin-bottom: 30rpx; width: 100%; box-sizing: border-box; }
.picker-row { display: flex; align-items: center; padding: 20rpx 0; border-bottom: 1px solid #f0f0f0; margin-bottom: 30rpx; }
.label { width: 160rpx; font-size: 28rpx; color: #333; }
.value { font-size: 28rpx; color: #666; }
.input-inline { flex: 1; height: 60rpx; border: 1px solid #e0e0e0; border-radius: 8rpx; padding: 0 16rpx; font-size: 28rpx; }
.tip { font-size: 24rpx; color: #999; margin-bottom: 40rpx; }
.btn-publish { width: 100%; height: 88rpx; line-height: 88rpx; background: #3cc51f; color: #fff; border-radius: 16rpx; font-size: 32rpx; }
</style>
