<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'RuoYiDoc',
  data() {
    return {
      url: 'https://gitee.com/wsitm/RuoYi-SchemaX.git'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
