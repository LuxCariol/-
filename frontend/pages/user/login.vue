<template>
  <view class="login">
    <view class="logo">
      <text class="logo-text">校园零工</text>
      <text class="logo-sub">信用支撑型零工平台</text>
    </view>

    <view class="form">
      <input class="input" v-model="studentNo" placeholder="学号" />
      <input class="input" v-model="name" placeholder="姓名" />
      <input class="input" v-model="idCardSuffix" placeholder="身份证后六位" type="password" />

      <button class="btn-primary" @click="handleRegister">学籍认证注册</button>
      <button class="btn-secondary" @click="handleLogin">已有账号，直接登录</button>
    </view>
  </view>
</template>

<script>
import { userApi } from '../../api/index';

export default {
  data() {
    return { studentNo: '', name: '', idCardSuffix: '' };
  },
  methods: {
    async handleRegister() {
      if (!this.studentNo || !this.name) {
        uni.showToast({ title: '请填写学号和姓名', icon: 'none' });
        return;
      }
      try {
        const res = await userApi.register({
          studentNo: this.studentNo,
          name: this.name,
          idCardSuffix: this.idCardSuffix,
          college: '信息工程学院'
        });
        uni.setStorageSync('token', res.token);
        uni.setStorageSync('userId', res.user.id);
        uni.switchTab({ url: '/pages/index/index' });
      } catch (e) {}
    },
    async handleLogin() {
      if (!this.studentNo) {
        uni.showToast({ title: '请输入学号', icon: 'none' });
        return;
      }
      try {
        const res = await userApi.login({ studentNo: this.studentNo });
        uni.setStorageSync('token', res.token);
        uni.setStorageSync('userId', res.user.id);
        uni.switchTab({ url: '/pages/index/index' });
      } catch (e) {}
    }
  }
};
</script>

<style>
.login { padding: 120rpx 60rpx; background: #fff; min-height: 100vh; }
.logo { text-align: center; margin-bottom: 80rpx; }
.logo-text { font-size: 60rpx; font-weight: bold; color: #3cc51f; display: block; }
.logo-sub { font-size: 28rpx; color: #999; margin-top: 16rpx; display: block; }
.form { }
.input { height: 80rpx; border: 1px solid #e0e0e0; border-radius: 12rpx; padding: 0 24rpx; font-size: 28rpx; margin-bottom: 30rpx; }
.btn-primary { width: 100%; height: 88rpx; line-height: 88rpx; background: #3cc51f; color: #fff; border-radius: 16rpx; font-size: 32rpx; margin-bottom: 24rpx; }
.btn-secondary { width: 100%; height: 88rpx; line-height: 88rpx; background: #fff; color: #3cc51f; border: 1px solid #3cc51f; border-radius: 16rpx; font-size: 32rpx; }
</style>
