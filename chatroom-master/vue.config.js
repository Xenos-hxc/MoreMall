module.exports = {
  devServer: {
    port: 8080,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_API_TARGET || 'http://localhost:9088',
        changeOrigin: true,
        pathRewrite: { '^/api': '' }
      }
    }
  }
}
