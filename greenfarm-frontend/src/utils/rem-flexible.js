
function setRem() {
  var scale = document.documentElement.clientWidth / 7.5
  //document.documentElement.style.fontSize = (baseSize * Math.min(scale, 2)) + 'px';
  document.documentElement.style.fontSize = scale + 'px';
}
setRem();
window.onresize = function () {
  setRem();
}