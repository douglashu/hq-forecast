var path = require('path');
var cooking = require('cooking');
var app = require('./app.json');
var config = require('./src/config/config')

var entry = {}
app.pages.forEach(p => {
  entry[p.entry] = path.resolve(app.basePath, p.entry)
})

var template = app.pages.map(p => {
  return {
    title: p.title,
    filename: p.entry + '/index.html',
    template: path.resolve(__dirname, 'index.tpl'),
    chunks: ['vendor', 'manifest', p.entry]
  }
})

cooking.set({
  entry: entry,
  dist: './dist',
  template: template,
  devServer: {
    port: 8090,
    publicPath: '/',
    proxy: {
      '/api/*': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite:{
            '^/api': ''
        },
        secure: false
      }
    }
  },
  // production
  clean: true,
  hash: true,
  sourceMap: true,
  minimize: true,
  chunk: true, // see https://cookingjs.github.io/zh-cn/configuration.html#chunk
  postcss: [
    // require('...')
  ],
  publicPath: config.baseUrl + '/dist/',
  assetsPath: 'static',
  urlLoaderLimit: 10000,
  static: true,
  extractCSS: '[name].[contenthash:7].css',
  alias: {
    'src': path.join(__dirname, 'src')
  },
  extends: ['vue2', 'lint', 'autoprefixer']
});

module.exports = cooking.resolve();
