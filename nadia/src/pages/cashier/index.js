import Vue from 'vue';
import VueResource from 'vue-resource';
import App from './app.vue';
import Scrati from '../../components/util/scrati';

Vue.use(VueResource);
let dataObj = {
  bridgeReady: false
};

function init() {
  let renderElement;
  dataObj.container = Scrati.getContainerApp();
  if (dataObj.container.isWechat || dataObj.container.isAlipay) {
    if (dataObj.container.isWechat) {
      if (typeof WeixinJSBridge === 'undefined') {
        if (document.addEventListener) {
          document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
          document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
          document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
      } else {
        onBridgeReady();
      }
    } else {
      if (typeof AlipayJSBridge === 'undefined') {
        document.addEventListener('AlipayJSBridgeReady', onBridgeReady, false);
      } else {
        onBridgeReady();
      }
    }
    let qrCodeId = Scrati.getQueryParam('state');
    let authCode;
    if (dataObj.container.isWechat) {
      authCode = Scrati.getQueryParam('code');
    } else {
      authCode = Scrati.getQueryParam('auth_code');
    }
    if (qrCodeId !== null && qrCodeId !== '' &&
      authCode !== null && authCode !== '') {
      dataObj.qrCodeId = qrCodeId;
      dataObj.authCode = authCode;
      renderElement = App;
    } else {
      // 参数错误
      renderElement = Error;
    }
  } else {
    // 需要在微信支付宝中打开
    renderElement = Error;
  }
  /* eslint-disable no-new */
  new Vue({
    el: '#app',
    data() {
      return dataObj;
    },
    render: h => h(renderElement)
  });
}

function onBridgeReady() {
  dataObj.bridgeReady = true;
  if (dataObj.container.isWechat) {
    // eslint-disable-next-line
    WeixinJSBridge.invoke('hideOptionMenu');
  } else {
    // eslint-disable-next-line
    AlipayJSBridge.call('hideOptionMenu');
  }
}

init();
