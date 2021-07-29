const zipdir = require('zip-dir')

const configs = {
  dir: {
    distFolder: 'dist_stage', 
    fileName: process.env.npm_package_name,
    fileExtension: 'war'
  }
}

zipdir(
  `${__dirname}\\${configs.dir.distFolder}`,
  {
    saveTo: `${__dirname}\\${configs.dir.fileName}.${configs.dir.fileExtension}`
  },
  function(err) {
    if (err) {
      console.log(err)
    } else {
      console.log(`Compressed file saved at ${__dirname}\\${configs.dir.fileName}.${configs.dir.fileExtension}`)
    }
  }
)
