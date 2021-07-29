const execSync = require('child_process').execSync
const chalk = require('chalk')
const config = require('../vue.config.js')
const rawArgv = process.argv.slice(2)
const path = require('path')
const args = rawArgv.join(' ')

if (rawArgv.includes('--analyzer') || rawArgv.includes('--preview')) {
  
  execSync(`vue-cli-service build ${args}`,{ stdio: 'inherit' })

  const port = 9526
  const publicPath = '/'

  const connect = require('connect')
  const serveStatic = require('serve-static')
  const app = connect()

  app.use(
    serveStatic(path.join(__dirname,'../dist_prod'),{
      maxAge:'30d' // http缓存的最大时间
    })
  )
  app.listen(port, function () {
    console.log(chalk.green(`> Preview at  http://localhost:${port}${publicPath}`))
  })
} else {
  execSync(`vue-cli-service build ${args}`,{ stdio: 'inherit' })
}
