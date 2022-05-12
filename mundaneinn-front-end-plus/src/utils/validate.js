export default {
  /**
   * 验证手机号
   * @param phone
   * @returns {boolean}
   */
  validPhone(phone) {
    const reg = /^1[0-9]{10}$/;
    return reg.test(phone)
  },
  /**
   * 验证身份证号(15位、18位数字)
   * @param idCard
   * @returns {boolean}
   */
  validIdCard(idCard) {
    const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
    return reg.test(idCard)
  },
  /**
   * 验证密码(以数字开头，长度在6~18之间，只能包含字母、数字和下划线)
   * @param idCard
   * @returns {boolean}
   */
  validPassword(password) {
    const reg = /^[0-9]\w{5,17}$/
    return reg.test(password)
  },
  /**
   * 验证金额格式(不能为0) 小数点后两位 0.01 0.1 0.10 1.0 1.01 1
   * @param money
   * @returns {boolean}
   */
  validMoney(money){
    let reg = /^((0(\.[0][1-9]))|(0(\.[1-9][0-9]?))|(([1-9][0-9]*)+(\.[0-9]{1,2})?))$/;
    return reg.test(money)
  },
  /**
   * 验证日期
   * @param date
   * @returns {boolean}
   */
  validDate(date) {
    const reg = /^\d{4}-\d{1,2}-\d{1,2}/
    return reg.test(date)
  },
  /**
   * 验证url
   * @param url
   * @returns {boolean}
   */
  validURL(url) {
    const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
    return reg.test(url)
  },
  /**
   * 验证email
   * @param email
   * @returns {boolean}
   */
  validEmail(email) {
    const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return reg.test(email)
  },
  /**
   * 是否是字符串
   * @param str
   * @returns {boolean}
   */
  isString(str) {
    if (typeof str === 'string' || str instanceof String) {
      return true
    }
    return false
  },
  /**
   * 是否是数组
   * @param arg
   * @returns {arg is any[]|boolean}
   */
  isArray(arg) {
    if (typeof Array.isArray === 'undefined') {
      return Object.prototype.toString.call(arg) === '[object Array]'
    }
    return Array.isArray(arg)
  },


  /*//金额格式(不能为0) 小数点后两位 0.01 0.1 0.10 1.0 1.01 1
var regMoney = /^((0(\.[0][1-9]))|(0(\.[1-9][0-9]?))|(([1-9][0-9]*)+(\.[0-9]{1,2})?))$/;
//金额格式(可以为0) 小数点后两位 0 0.01 0.1 0.10 1.0 1.01 1
var regMoney2 = /^(0|(0(\.[0][1-9]))|(0(\.[1-9][0-9]?))|(([1-9][0-9]*)+(\.[0-9]{1,2})?))$/;
//金额格式 小数点后两位 0.01 0.1 0.10 1.0 1.01 1（正负数）
var regMoney3 = /(^([-]?)[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^([-]?)(0){1}$)|(^([-]?)[0-9]\.[0-9]([0-9])?$)/;
//非零开头的最多带两位小数的数字 1 1.0 1.01
var renNumber = /^([1-9][0-9?]*)+(\.[0-9]{1,2})?$/;
//汉字匹配
var regChinese = /^[\u4e00-\u9fa5]{0,}$/;
//文件名称格式  数字 字母 _ . -  不能有空格
var regFileName = /^[-\u4E00-\u9FA5A-Za-z0-9_.]+$/;
//邮箱匹配
var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//6-12位字母数字
var regStr = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/;
//电话号码
var regPhone = /^1[0-9]{10}$/;
//身份证号(15位、18位数字)，最后一位是校验位，可能为数字或字符X
var regIDCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
//非零正整数
var regNumber = /^[1-9]\d*$/;
//0到1，小数点后两位的小数
var regDecimal = /^(1|0(\.\d{1,2})?)$/;
//零和非零开头的数字
var regNumber2 = /^(0|[1-9][0-9]*)$/;
//有1-3位小数的正实数(可以以0开头)
var regNumber3 = /^[0-9]+(\.[0-9]{1,3})?$/;
//1到2位非零正整数
var regNumber4 = /^([1-9]|[1-9][0-9])$/;
// ipv4地址，例如255.255.255.255
var regIpv4=/^((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}$/;*/
}
