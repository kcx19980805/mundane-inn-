import dialogDrag from "@/directive/dialogDrag";

const install = function(Vue) {
  Vue.directive('dialogDrag', dialogDrag)
}

if (window.Vue) {
  window['dialogDrag'] = dialogDrag
  Vue.use(install); // eslint-disable-line
}

export default install
