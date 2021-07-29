export function MapLoader (key) { 
    return new Promise((resolve, reject) => {
      if (window.AMap) {
        resolve(window.AMap)
      } else {
        let jsTag = document.createElement('script');
        let cssTag = document.createElement('link');
        jsTag.type = 'text/javascript';
        jsTag.async = true;
        jsTag.src = "https://webapi.amap.com/maps?v=1.4.15&callback=initAMap&key=" + key;   
        jsTag.onerror = reject;
        cssTag.href = "http://cache.amap.com/lbs/static/main.css?v=1.0?v=1.0";
        cssTag.rel="stylesheet";
        cssTag.type = "text/css";
        document.head.appendChild(jsTag);
        document.head.appendChild(cssTag);
      }
      window.initAMap = () => {
        resolve(window.AMap)
      }  
    })
  }