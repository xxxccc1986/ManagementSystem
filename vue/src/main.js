import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 导入element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

//引入自定义组件


//导入css样式
import '@/assets/css/global.css'
//导入element-plus全局图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//导入国际化配置
import zhCn from 'element-plus/es/locale/lang/zh-cn'


const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(store).use(router).use(ElementPlus, {
    locale: zhCn,size:'size'
}).mount('#app');


