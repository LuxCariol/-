const BASE_URL = '/api'

function getToken() {
  return localStorage.getItem('token') || ''
}

async function request(url, options = {}) {
  const token = getToken()
  const headers = { 'Content-Type': 'application/json' }
  if (token) headers['userId'] = token

  const res = await fetch(BASE_URL + url, {
    headers,
    ...options
  })
  const data = await res.json()
  if (data.code !== 200) {
    throw new Error(data.message || '请求失败')
  }
  return data.data
}

export const api = {
  get(url) { return request(url) },
  post(url, body) { return request(url, { method: 'POST', body: JSON.stringify(body || {}) }) },

  user: {
    register(data) { return this.post('/user/register', data) },
    login(data) { return this.post('/user/login', data) },
    get(id) { return this.get(`/user/${id}`) },
    getCredit(id) { return this.get(`/user/${id}/credit`) }
  },

  task: {
    list() { return this.get('/task/list') },
    detail(id) { return this.get(`/task/${id}`) },
    publish(data) { return this.post('/task/publish', data) },
    accept(id) { return this.post(`/task/${id}/accept`) },
    complete(id) { return this.post(`/task/${id}/complete`) },
    my() { return this.get('/task/my') }
  },

  guarantor: {
    invite(userId) { return this.post(`/guarantor/invite?guarantorUserId=${userId}`) },
    remove(userId) { return this.post(`/guarantor/remove?guarantorUserId=${userId}`) },
    list() { return this.get('/guarantor/list') },
    count() { return this.get('/guarantor/count') }
  },

  credit: {
    log() { return this.get('/credit/log') }
  }
}
