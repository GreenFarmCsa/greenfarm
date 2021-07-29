var UMJBIframe = document.createElement('iframe');
UMJBIframe.style.display = 'none';
UMJBIframe.src = 'https://__bridge_loaded__';
document.documentElement.appendChild(UMJBIframe);
setTimeout(function() { document.documentElement.removeChild(UMJBIframe) }, 0)

