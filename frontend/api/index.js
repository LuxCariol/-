import request from '../utils/request';

export const userApi = {
  login: (data) => request.post('/api/user/login', data),
  register: (data) => request.post('/api/user/register', data),
  getProfile: (id) => request.get(`/api/user/${id}`),
  getCredit: (id) => request.get(`/api/user/${id}/credit`)
};

export const taskApi = {
  publish: (data) => request.post('/api/task/publish', data),
  accept: (taskId) => request.post(`/api/task/${taskId}/accept`),
  complete: (taskId) => request.post(`/api/task/${taskId}/complete`),
  list: () => request.get('/api/task/list'),
  detail: (id) => request.get(`/api/task/${id}`),
  myTasks: () => request.get('/api/task/my')
};

export const guarantorApi = {
  invite: (guarantorUserId) => request.post(`/api/guarantor/invite?guarantorUserId=${guarantorUserId}`),
  remove: (guarantorUserId) => request.post(`/api/guarantor/remove?guarantorUserId=${guarantorUserId}`),
  list: () => request.get('/api/guarantor/list'),
  count: () => request.get('/api/guarantor/count')
};

export const creditApi = {
  getLog: () => request.get('/api/credit/log')
};

export const disputeApi = {
  report: (orderId, evidence) => request.post(`/api/dispute/report?orderId=${orderId}&evidence=${evidence}`)
};
