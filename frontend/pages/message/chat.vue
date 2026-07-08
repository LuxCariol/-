<template>
  <view class="chat">
    <view class="msg-list">
      <view class="msg-item" v-for="msg in messages" :key="msg.id"
            :class="{ mine: msg.fromUser === userId }">
        <text class="msg-content">{{ msg.content }}</text>
        <text class="msg-time">{{ msg.createTime }}</text>
      </view>
    </view>
    <view class="input-bar">
      <input class="msg-input" v-model="content" placeholder="输入消息" />
      <button class="btn-send" @click="handleSend">发送</button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return { messages: [], content: '', userId: '', taskId: '', toUser: '' };
  },
  onLoad(options) {
    this.taskId = options.taskId;
    this.toUser = options.toUser;
    this.userId = uni.getStorageSync('userId');
  },
  methods: {
    async handleSend() {
      if (!this.content) return;
      const msg = { from: this.userId, to: this.toUser, content: this.content, taskId: this.taskId };
      this.messages.push({ ...msg, fromUser: this.userId, createTime: new Date().toLocaleString() });
      this.content = '';
    }
  }
};
</script>

<style>
.chat { display: flex; flex-direction: column; height: 100vh; background: #f5f5f5; }
.msg-list { flex: 1; padding: 20rpx; overflow-y: auto; }
.msg-item { margin-bottom: 20rpx; max-width: 70%; padding: 20rpx; border-radius: 12rpx; }
.msg-item.mine { align-self: flex-end; background: #3cc51f; color: #fff; margin-left: auto; }
.msg-item:not(.mine) { background: #fff; }
.msg-content { font-size: 28rpx; display: block; }
.msg-time { font-size: 20rpx; color: #999; margin-top: 8rpx; display: block; }
.input-bar { display: flex; padding: 20rpx; background: #fff; border-top: 1px solid #e0e0e0; }
.msg-input { flex: 1; height: 70rpx; border: 1px solid #e0e0e0; border-radius: 12rpx; padding: 0 20rpx; font-size: 28rpx; }
.btn-send { height: 70rpx; line-height: 70rpx; background: #3cc51f; color: #fff; border-radius: 12rpx; font-size: 28rpx; margin-left: 20rpx; padding: 0 40rpx; }
</style>
