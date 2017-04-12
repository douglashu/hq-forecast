<template>
  <div>
    <div class="weui-msg">
      <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
      <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">{{ title }}</h2>
        <p class="weui-msg__desc">{{ content }}</p>
      </div>
      <div class="weui-msg__extra-area">
        <div class="weui-footer">
          <p class="weui-footer__text">Copyright &copy; 2017 hqast.com</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style>

</style>

<script>
  // eslint-disable-next-line
  import weui from 'weui';
  import Scrati from '../../components/util/scrati';
  export default {
    name: 'app',
    data() {
      return {
        title: '',
        content: ''
      };
    },
    methods: {
    },
    created() {
      let title = Scrati.getQueryParam('t');
      if (title !== null && title !== '') {
        this.title = decodeURI(title);
      } else {
        this.title = '慧钱提醒您';
      }
      let content = Scrati.getQueryParam('c');
      if (content !== null && content !== '') {
        this.content = decodeURI(content);
      } else {
        this.content = '出了点小问题，请稍后再试吧!';
      }
    }
  };
</script>
