var OSS = require('ali-oss');
export function client() {
    var client = new OSS({
        region: 'oss-cn-shenzhen',
        accessKeyId: 'LTAI4GJ71dyqDAvPpVJG7FY6',
        accessKeySecret: 'IMRqUyUsokGMKud3KUVl7M2LdQ8fqI',
        bucket: 'kcxbucket'
    })
    return client
}