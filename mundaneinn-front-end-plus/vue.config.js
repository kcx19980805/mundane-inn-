const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
	transpileDependencies: true,
  // webpack-dev-server 相关配置
	devServer: { // 设置代理
		host: '0.0.0.0', //默认是 localhost。如果你希望服务器外部可访问，指定如下 host: '0.0.0.0'，设置之后之后可以访问ip地址
		port: 8887, //自定义端口
		https: false, //false关闭https，true为开启
		//open: true, //自动打开浏览器
		proxy: {
			[process.env.VUE_APP_BASE_API]: {
				target: process.env.VUE_APP_BASE_API,
				// 如果要代理 websockets
				//ws: true,
				//是否跨域
				changeOrigin: true,
/*				pathRewrite: {   //重写路径，这种是没有我们定义的前缀
					['^' + process.env.VUE_APP_BASE_API]: ''
				}*/
			}
		}
	},

})
