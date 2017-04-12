<template>
  <div>
    <img class="bg" src="../../../static/images/bg/cashier_hd_bg.png" />
    <div class="info_box">
      <div class="shop_info">
        <div class="shop_icon">
          <img class="shop_icon" src="../../../static/images/icon/shop.png" />
        </div>
        <div class="shop_name">{{ displayName }}</div>
      </div>
      <div class="amount_box">
        <div class="title">金额:</div>
        <div class="amount" :class="{ placeholder: value === '' }">
          <span>{{ displayedValue }}<label class="symbol">&nbsp;￥&nbsp;</label></span>
        </div>
      </div>
      <span class="tips">关注慧钱助手,惊喜福利送不停</span>
    </div>
    <span style="display: none"> {{ calcPayState }}</span>
    <div class="copyright">由慧钱提供{{ channel }}业务受理</div>
    <div class="keyboard">
      <table>
        <tbody>
          <tr v-for="(r, row) of keys">
            <td v-for="(c, col) of r" :rowspan="c.rows" :colspan="c.cols">
              <div>
                <div class="key" :class="{
                      'active': c.active,
                      'del_key': c.value === 'b',
                      'confirm_normal': (c.value === 't' && canPay),
                      'confirm_disabled': (c.value === 't' && !canPay)
                    }"
                   @touchstart="onTouchStart(row, col, $event)"
                   @touchend="onTouchEnd(c.value, row, col)"
                   @touchcancel="onTouchCancel(row, col)" v-html="c.text">
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-if="showAlert">
      <div class="weui-mask"></div>
      <div class="weui-dialog">
        <div class="weui-dialog__hd"><strong class="weui-dialog__title">{{ alertTitle }}</strong></div>
        <div class="weui-dialog__bd">{{ alertContent }}</div>
        <div class="weui-dialog__ft">
          <a @click="closeWindow" style="color: #3485fb" class="weui-dialog__btn weui-dialog__btn_primary">我知道了</a>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
  img.bg {
    width: 100%;
    height: 160px;
    position: absolute;
    left: 0;
    top: 0;
    z-index: 1;
  }
  .shop_info {
    height: 48px;
    position: relative;
  }
  .shop_icon {
    width: 48px;
    height: 48px;
    position: absolute;
    left: 0; top: 0;
  }
  .shop_icon > img {
    width: 36px;
    height: 36px;
    position: absolute;
    left: 0; top: 6px;
  }
  div.shop_name {
    height: 48px;
    line-height: 48px;
    margin-left: 46px;
    color: #333333;
    font-size: 16px;
    font-weight: 300;
  }
  .info_box {
    width: 84%;
    margin: 30px auto 0;
    background-color: white;
    height: 180px;
    border-radius: 10px;
    padding: 10px 15px 10px 15px;
    z-index: 1000;
    position: relative;
    -webkit-box-shadow: 0 0 1px #d1e0f7,
                        0 0 1px #d1e0f7,
                        0 0 10px #d1e0f7;
    -moz-box-shadow: 0 0 1px #d1e0f7,
                     0 0 1px #d1e0f7,
                     0 0 10px #d1e0f7;
    box-shadow: 0 0 1px #d1e0f7,
                0 0 1px #d1e0f7,
                0 0 10px #d1e0f7;
  }
  .amount_box {
    width: 80%;
    height: 36px;
    margin: 30px auto 0;
    position: relative;
    border-bottom: 1px solid #e5e5e5;
  }
  .amount_box > table {
    width: 100%;
    height: 100%;
    border: none;
    border-collapse: collapse;
  }
  .amount_box .title {
    width: 34px;
    font-size: 14px;
    font-weight: 300;
    color: #a0a0a0;
    position: absolute;
    left: 0;
    bottom: 0;
   }
  .amount {
    width: 100%;
    height: 100%;
    font-size: 28px;
    color: #333333;
    padding-left: 34px;
    text-align: center;
  }
  .amount > span {
    position: relative;
  }
  .placeholder {
    color: #cbcbcb !important;
  }
  .symbol {
    font-size: 14px;
    color: #a0a0a0;
    font-weight: 300;
  }
  .tips {
    display: inline-block;
    text-align: center;
    width: 100%;
    position: absolute;
    left: 0;
    bottom: 8px;
    font-size: 10px;
    color: #b4b4b4;
  }
  .copyright {
    width: 100%;
    text-align: center;
    position: absolute;
    bottom: 266px;
    left: 0;
    color: #b4b4b4;
    font-size: 12px;
  }
  .confirm_normal {
    background-color: #3485fb;
  }
  .confirm_disabled {
    background-color: #ABCDFF;
  }
  .keyboard {
    width: 100%;
    height: 260px;
    position: absolute;
    bottom: 0;
    left: 0;
    background-color: #fff;
  }
  .keyboard table {
    width: 100%;
    height: 100%;
    border-collapse: collapse;
    border: 1px solid #dedfe2;
    box-sizing: border-box!important;
  }
  .keyboard td {
    width: 25%;
    border: 1px solid #dedfe2;
    text-align: center;
  }
  .keyboard td > div {
    width: 100%;
    height: 100%;
    line-height: 65px;
    background-color: #fff;
    position: relative;
  }
  .keyboard div.key {
    color: #424857;
    font-size: 32px;
    font-weight: 300;
    border-radius: inherit;
    width: 100%;
    height: 100%;
    top: 0px;
    left: 0px;
    position: absolute;
  }
  .keyboard div.active:after {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.1)
  }
  .keyboard div.del_key {
    background-image: url('../../../static/images/icon/c.svg');
    background-repeat: no-repeat;
    background-size: 35px auto;
    background-position: 50%;
  }
  .key span {
    display: block;
    line-height: 28px;
    font-size: 22px;
    color: white;
    font-weight: 400;
  }
  .key span.tp {
    margin-top: 70px;
  }
</style>

<script>
  import FastClick from 'fastclick';
  import Config from '../../config/config';
  // eslint-disable-next-line
  import weui from 'weui';
  let hasDot = false;
  let decimals = 0;
  export default {
    data() {
      return {
        keys: [
          [
            { text: '1', value: '1', active: false },
            { text: '2', value: '2', active: false },
            { text: '3', value: '3', active: false },
            { text: '', value: 'b', active: false }
          ],
          [
            { text: '4', value: '4', active: false },
            { text: '5', value: '5', active: false },
            { text: '6', value: '6', active: false },
            {
              text: '<span class="tp">确认</span><span>付款</span>',
              value: 't', rows: '3', active: false
            }
          ],
          [
            { text: '7', value: '7', active: false },
            { text: '8', value: '8', active: false },
            { text: '9', value: '9', active: false }
          ],
          [
            { text: '0', value: '0', cols: '2', active: false },
            { text: '.', value: '.', active: false }
          ]
        ],
        mchLoaded: false,
        displayName: '',
        userOpenId: '',
        value: '',
        logo: '',
        isPaying: false,
        canPay: false,
        orderId: '',
        showAlert: false,
        alertTitle: '',
        alertContent: ''
      };
    },
    watch: {
      isPaying: function(newVaue) {
        if (newVaue) {
          this.keys[1][3].text = '<span class="tp">正在</span><span>付款</span>';
        } else {
          this.keys[1][3].text = '<span class="tp">确认</span><span>付款</span>';
        }
      }
    },
    computed: {
      displayedValue: function() {
        if (this.value === '') return '0.00';
        return this.value;
      },
      calcPayState: function() {
        if (this.$parent.bridgeReady &&
            this.mchLoaded && !this.isPaying &&
            this.userOpenId && this.userOpenId !== '' &&
            this.value && this.value !== '') {
          let amount = (parseFloat(this.value) * 100).toFixed(0);
          if (amount > 0) {
            this.canPay = true;
            return true;
          }
        }
        this.canPay = false;
        return false;
      },
      channel: function() {
        if (this.$parent.container.isWechat) {
          return '微信';
        }
        return '支付宝';
      }
    },
    methods: {
      onTouchStart(row, col, e) {
        e.preventDefault();
        if (row === 1 && col === 3 && !this.canPay) {
          return;
        }
        this.keys[row][col].active = true;
      },
      onTouchCancel(row, col) {
        this.keys[row][col].active = false;
      },
      onTouchEnd(key, row, col) {
        this.keys[row][col].active = false;
        if (this.isPaying) return;
        if (key === 't') {
          let amount = (parseFloat(this.value) * 100).toFixed(0);
          this.doTradePay(amount);
          return;
        }
        if (key === 'b') {
          if (this.value === '') return;
          if (hasDot) {
            if (decimals > 0) {
              decimals--;
            } else {
              hasDot = false;
            }
          }
          this.value = this.value.substring(0, this.value.length - 1);
          return;
        }
        if (this.value === '0' && key !== '.') {
          if (key === '0') return;
          this.value = key;
        } else {
          if (decimals >= 2) return;
          if (key === '.') {
            if (hasDot) return;
            hasDot = true;
            if (this.value === '') {
              this.value = '0.';
            } else {
              this.value = this.value + key;
            }
            return;
          }
          if (hasDot) decimals++;
          let amount = (parseFloat(this.value + key) * 100).toFixed(0);
          if (amount > 999999999) return;
          this.value = this.value + key;
        }
      },
      doTradePay(amount) {
        if (!this.canPay) {
          return;
        }
        this.isPaying = true;
        this.$http.post(
          Config.baseUrl + '/prepay_orders',
          {
            codeId: this.$parent.qrCodeId,
            openId: this.userOpenId,
            totalAmount: amount
          })
          .then((response) => {
            let container = this.$parent.container;
            let order = response.data;
            if (order.tradeState === 'PRE_CREATE') {
              this.orderId = order.orderId;
              if (container.isWechat) {
                this.doWxPay(order);
              } else if (container.isAlipay) {
                this.doAlipay(order);
              }
            } else {
              this.isPaying = false;
              this.showAlertBox('付款失败', '交易失败了，请稍后再来吧!');
            }
          }).catch((response) => {
            this.isPaying = false;
            this.showAlertBox('付款失败', '交易失败了，请稍后再来吧!');
          });
      },
      loadBaseInfo() {
        this.$http.get(Config.baseUrl + '/qrcode/' + this.$parent.qrCodeId)
          .then((response) => {
            let data = response.data;
            if (data.logo && data.logo !== '') {
              this.logo = data.logo;
            }
            let display = data.mchShortName;
            if (data.storeName && data.storeName !== '') {
              display += ('-' + data.storeName);
            }
            this.displayName = display;
            this.mchLoaded = true;
          }).catch((response) => {
            this.showAlertBox('温馨提示', '商户信息加载失败了，请稍后再来吧!');
          });
      },
      loadUserInfo() {
        if (window.sessionStorage.getItem('userOpenId')) {
          this.userOpenId = window.sessionStorage.getItem('userOpenId');
          return;
        }
        this.$http.get(Config.baseUrl + '/user_info?authCode=' + this.$parent.authCode)
          .then((response) => {
            this.userOpenId = response.data.openId;
            window.sessionStorage.setItem('userOpenId', response.data.openId);
          }).catch((response) => {
            this.showAlertBox('温馨提示', '用户信息加载失败了，请稍后再来吧!');
          });
      },
      showAlertBox(title, content) {
        if (this.showAlert) return;
        this.alertTitle = title;
        this.alertContent = content;
        this.showAlert = true;
      },
      closeWindow() {
        let container = this.$parent.container;
        if (container.isWechat) {
          // eslint-disable-next-line
          WeixinJSBridge.call('closeWindow');
        } else if (container.isAlipay) {
          // eslint-disable-next-line
          AlipayJSBridge.call('closeWebview');
        }
      },
      doWxPay(order) {
        if (order.tradeType === 'ORDER_CODE') {
          location.replace(order.codeUrl);
        } else if (order.tradeType === 'H5_JSAPI') {
          let self = this;
          let orderInfo = order.orderInfo;
          // eslint-disable-next-line
          WeixinJSBridge.invoke(
            'getBrandWCPayRequest',
            {
              'appId': orderInfo.appId,
              'timeStamp': orderInfo.timeStamp,
              'nonceStr': orderInfo.nonceStr,
              'package': orderInfo.package,
              'signType': orderInfo.signType,
              'paySign': orderInfo.paySign
            },
            function(res) {
              if (res.err_msg === 'get_brand_wcpay_request:ok') {
                // go to success page
                let urlStr = 'm=' + encodeURI(self.displayName);
                urlStr += ('&a=' + self.displayedValue);
                location.replace('../ads/index.html?' + urlStr);
              } else if (res.err_msg === 'get_brand_wcpay_request:cancel') {
                // user cancel the payment
                self.isPaying = false;
              } else {
                // user doing the payment fail
                self.isPaying = false;
              }
            }
          );
        }
      },
      doAlipay(order) {
        if (order.tradeType === 'ORDER_CODE') {
          location.replace(order.codeUrl);
        } else if (order.tradeType === 'H5_JSAPI') {
        }
      }
    },
    created() {
      FastClick.attach(document.body);
      document.body.oncontextmenu = function() {
        return false;
      };
      this.loadBaseInfo();
      this.loadUserInfo();
    }
  };
</script>