const BASE_URL = 'http://localhost:8080';

function getToken() {
  try {
    return uni.getStorageSync('token') || '';
  } catch (e) {
    return '';
  }
}

function request(options) {
  return new Promise((resolve, reject) => {
    const token = getToken();
    const header = { 'Content-Type': 'application/json' };
    if (token) header['userId'] = token;

    uni.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header,
      success: (res) => {
        if (res.statusCode === 200) {
          if (res.data.code === 200) {
            resolve(res.data.data);
          } else {
            uni.showToast({ title: res.data.message || '请求失败', icon: 'none' });
            reject(res.data);
          }
        } else {
          uni.showToast({ title: '网络错误', icon: 'none' });
          reject(res);
        }
      },
      fail: (err) => {
        uni.showToast({ title: '网络请求失败', icon: 'none' });
        reject(err);
      }
    });
  });
}

export default {
  get: (url, data) => request({ url, data, method: 'GET' }),
  post: (url, data) => request({ url, data, method: 'POST' }),
  put: (url, data) => request({ url, data, method: 'PUT' }),
  delete: (url) => request({ url, method: 'DELETE' })
};
