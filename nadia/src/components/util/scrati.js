class Scrati {
  getQueryParam(name) {
    let url = window.location.search.substr(1);
    let reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
    let result = url.match(reg);
    if (result && result[2]) return result[2];
    return null;
  }
  getContainerApp() {
    let result = {
      container: '',
      isWechat: false,
      isAlipay: false
    };
    let ua = navigator.userAgent.toLowerCase();
    if (ua.indexOf('micromessenger') !== -1) {
      result.container = 'wechat';
      result.isWechat = true;
    } else if (ua.indexOf('alipayclient') !== -1) {
      result.container = 'alipay';
      result.isAlipay = true;
    }
    return result;
  }
}

export default new Scrati();
