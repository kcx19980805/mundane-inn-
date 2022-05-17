var OSS = require('ali-oss');
export function client() {
    var client = new OSS({
        region: 'oss-cn-shenzhen',
        accessKeyId: '...',
        accessKeySecret: '...',
        bucket: '...'
    })
    return client
}