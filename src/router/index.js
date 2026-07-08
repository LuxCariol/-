import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Tasks from '../views/Tasks.vue'
import TaskDetail from '../views/TaskDetail.vue'
import TaskPublish from '../views/TaskPublish.vue'
import Profile from '../views/Profile.vue'
import Login from '../views/Login.vue'
import Guarantor from '../views/Guarantor.vue'

const routes = [
  { path: '/', name: 'home', component: Home },
  { path: '/tasks', name: 'tasks', component: Tasks },
  { path: '/tasks/:id', name: 'taskDetail', component: TaskDetail },
  { path: '/publish', name: 'taskPublish', component: TaskPublish },
  { path: '/profile', name: 'profile', component: Profile },
  { path: '/login', name: 'login', component: Login },
  { path: '/guarantor', name: 'guarantor', component: Guarantor },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
