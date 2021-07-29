'use strict'

const BundleAnaltzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin

const path = require('path')
const pkg = require('./package.json')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const name = pkg.name || process.env.VUE_APP_NAME
const port = process.env.VUE_APP_PORT // dev port
const rawArgv = process.argv.slice(2)

// All configuration item explanations can be find in https://cli.vuejs.org/config/
module.exports = {
  /**
   * You will need to set publicPath if you plan to deploy your site under a sub path,
   * for example GitHub Pages. If you plan to deploy your site to https://foo.github.io/bar/,
   * then publicPath should be set to "/bar/".
   * In most cases please use '/' !!!
   * Detail: https://cli.vuejs.org/config/#publicpath
   */
  publicPath: process.env.VUE_APP_PUBLIC_PATH,
  outputDir: process.env.VUE_APP_OUTPUT_DIR,
  assetsDir: process.env.VUE_APP_ASSETS_DIR,
  lintOnSave: process.env.NODE_ENV === 'development' ? 'error' : false,
  productionSourceMap: false,
  devServer: {
    port: port,
    // open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      '/dev-api': {
        target: `http://localhost:${port}/mock`,
        changeOrigin: true,
        pathRewrite: {
          ['^' + '/dev-api']: ''
        }
      }
    },
    after(app) {
      require('@babel/register')
      const bodyParser = require('body-parser')

      // parse app.body
      // http://expressjs.com/en/4x/api.html#req.body
      app.use(bodyParser.json())
      app.use(bodyParser.urlencoded({
        extended: true
      }))

      // const { default: mocks } = require('./mock')
      // for (const mock of mocks) {
      //   app[mock.type](mock.url, mock.response)
      // }
    }
  },
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  },
  chainWebpack(config) {
    config.plugins.delete('preload') // TODO: need test
    config.plugins.delete('prefetch') // TODO: need test

    // set svg-sprite-loader
    config.module
      .rule('svg')
      // .exclude.add(resolve('src/icons'))
      .exclude.add(resolve('src/assets/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      // .include.add(resolve('src/icons'))
      .include.add(resolve('src/assets/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()

    // set preserveWhitespace
    config.module
      .rule('vue')
      .use('vue-loader')
      .loader('vue-loader')
      .tap(options => {
        options.compilerOptions.preserveWhitespace = true
        return options
      })
      .end()

    config
      .when(process.env.NODE_ENV === 'development',
        config => config.devtool('source-map')
      )

    config
      .when(process.env.NODE_ENV !== 'development',
        config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
            // `runtime` must same as runtimeChunk name. default is `runtime`
              inline: /runtime\..*\.js$/
            }])
            .end()
          config
            .optimization.splitChunks({
              chunks: 'all',
              cacheGroups: {
                libs: {
                  name: 'chunk-libs',
                  test: /[\\/]node_modules[\\/]/,
                  priority: 10,
                  chunks: 'initial' // only package third parties that are initially dependent
                },
                commons: {
                  name: 'chunk-commons',
                  test: resolve('src/components'), // can customize your rules
                  minChunks: 3, //  minimum common number
                  priority: 5,
                  reuseExistingChunk: true
                }
              }
            })
            config
            .when(rawArgv.includes('--analyzer') === true,
            config => config
            .plugin('webpack-bundle-analyzer')
            .use(BundleAnaltzerPlugin)
          )        
          config.optimization.runtimeChunk('single')
        }
      )
  },
  css: {
    loaderOptions: {
      stylus: {
        'resolve url': true,
        'import': [
          './src/theme'
        ]
      },
      sass:{
        implementation:require('sass')
      },
      scss:{
          implementation:require('sass')
      }
    }
  },
  pluginOptions: {
    'cube-ui': {
      postCompile: true,
      theme: true
    }
  }
}
