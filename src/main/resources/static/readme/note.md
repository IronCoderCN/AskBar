## “知道” Web 开发纪要 ##
### 1、结构与介绍 ###
### 2、首页功能菜单 ###
### 3、待开发功能备注 ###
1. 首页菜单栏，用户头像部分，需要根据登录状态切换为用户头像
2. 首页菜单栏现在存在缩放浏览器，float元素换行问题，后续降float放置于固定div进行修正
3. 登录注册切换为独立modal，不再使用tabs
### 4、iview备注 ###

>组件使用规范 #
 使用:prop传递数据格式为 数字、布尔值或函数时，必须带:(兼容String除外，具体看组件文档)，比如：
 Correct usage:
 <Page :current="1" :total="100"></Page>
 Incorrect usage:
 <Page current="1" total="100"></Page>
 
>在非 template/render 模式下（例如使用 CDN 引用时），组件名要分隔，例如 DatePicker 必须要写成 date-picker。
 以下组件，在非 template/render 模式下，需要加前缀 i-：
 - Button: i-button
 - Col: i-col
 - Table: i-table
 - Input: i-input
 - Form: i-form
 - Menu: i-menu
 - Select: i-select
 - Option: i-option
 - Progress: i-progress
 - Time: i-time
>以下组件，在所有模式下，必须加前缀 i-，除非使用 iview-loader：
- Switch: i-switch
- Circle: i-circle

