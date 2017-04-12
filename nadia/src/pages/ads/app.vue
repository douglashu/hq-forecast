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
      <div style="text-align: center">
        <img class="trade_img" src="../../../static/images/icon/success.png" />
        <div class="trade_state">支付成功</div>
        <div class="bill">
          <span class="title">消费金额:</span>
          <span class="content">{{ amount }}￥</span>
        </div>
      </div>
    </div>
    <div class="btn_close" @click="closeWindow">
      <span>完成</span>
    </div>
    <div class="weui-footer weui-footer_fixed-bottom">
      <p class="weui-footer__text">Copyright &copy; 2017 hqast.com</p>
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
  .trade_img {
    width: 40px;
    height: 40px;
    margin-top: 5px;
  }
  .trade_state {
    color: #3485fb;
    font-size: 15px;
  }
  .bill {
    margin-top: 15px;
  }
  .bill > div {
    height: 20px;
  }
  .bill span {
    font-size: 14px;
    display: inline-block;
    text-align: left;
    color: #7f7f7f;
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
    height: 182px;
    border-radius: 10px;
    padding: 10px 15px 10px 15px;
    z-index: 1000;
    position: relative;
    -webkit-box-shadow:
            0 0 1px #d1e0f7,
            0 0 1px #d1e0f7,
            0 0 10px #d1e0f7;
    -moz-box-shadow:
            0 0 1px #d1e0f7,
            0 0 1px #d1e0f7,
            0 0 10px #d1e0f7;
    box-shadow:
            0 0 1px #d1e0f7,
            0 0 1px #d1e0f7,
            0 0 10px #d1e0f7;
  }
  .btn_close {
    width: 90%;
    height: 40px;
    margin: 24px auto;
    text-align: center;
    border-radius: 6px;
    color: white;
    font-size: 16px;
    background-color: #3485fb;
  }
  .btn_close > span {
    font-size: 15px;
    height: 40px;
    line-height: 40px;
    display: inline-block;
  }
</style>

<script>
  // eslint-disable-next-line
  import weui from 'weui';
  import Scrati from '../../components/util/scrati';
  export default {
    name: 'app',
    data() {
      return {
        bridgeReady: false,
        displayName: '',
        amount: ''
      };
    },
    methods: {
      closeWindow: function() {
        if (this.bridgeReady) {
          // eslint-disable-next-line
          WeixinJSBridge.call('closeWindow');
        }
      },
      onBridgeReady: function() {
        // eslint-disable-next-line
        WeixinJSBridge.invoke('hideOptionMenu');
        this.bridgeReady = true;
      }
    },
    created() {
      if (typeof WeixinJSBridge === 'undefined') {
        if (document.addEventListener) {
          document.addEventListener('WeixinJSBridgeReady', this.onBridgeReady, false);
        } else if (document.attachEvent) {
          document.attachEvent('WeixinJSBridgeReady', this.onBridgeReady);
          document.attachEvent('onWeixinJSBridgeReady', this.onBridgeReady);
        }
      } else {
        this.onBridgeReady();
      }
      let mchName = Scrati.getQueryParam('m');
      if (mchName !== null) {
        this.displayName = decodeURI(mchName);
      }
      let amount = Scrati.getQueryParam('a');
      if (amount !== null) {
        this.amount = amount;
      }
    }
  };
</script>
