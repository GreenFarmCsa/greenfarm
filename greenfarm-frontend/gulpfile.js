
var path = require('path')
var gulp = require('gulp')
var cleanCSS = require('gulp-clean-css')
var cssWrap = require('gulp-css-wrap')
var cssmin = require('gulp-cssmin')
gulp.task('css-wrap', function() {
  return gulp.src(path.resolve('./theme/index.css'))
    .pipe(cssWrap({
      // selector: '.themedarkblue'
      // selector: '.themelightblue'
      selector: '.themelightgreen'
    }))
    .pipe(cleanCSS())
    .pipe(cssmin())
    // .pipe(gulp.dest('src/assets/custom-theme/themedarkblue'))
    // .pipe(gulp.dest('src/assets/custom-theme/themelightblue'))
    .pipe(gulp.dest('src/assets/custom-theme/themelightgreen'))
})

