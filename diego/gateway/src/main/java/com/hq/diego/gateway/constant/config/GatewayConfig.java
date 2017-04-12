package com.hq.diego.gateway.constant.config;

/**
 * Created by zhaoyang on 1/14/16.
 */
public class GatewayConfig {

    public static final String NOTIFY_BASE_URL = "http://m.hqast.com/guan";

    /**
     * 微信原生
     */
    // 微信公众号AppId
    public static final String WX_APP_ID = "wx79846bf233b5db05";
    // 微信公众号AppSecret
    public static final String WX_API_SECRET_KEY = "02cd8f986c364018aa19a8bc4ac0fa4b";
    // 微信支付分配的商户号
    public static final String WX_MERCHANT_NO = "1420425002";
    // 微信商户证书文件所在路
    // public static final String WX_API_CLIENT_CERT_PATH = "/Users/zhaoyang/Documents/wx-cert/apiclient_cert.p12";
    // 微信商户证书调用密码
    public static final String WX_API_CLIENT_CERT_PASSWORD = "1420425002";
    // 微信异步通知URL
    public static final String WX_NOTIFY_URL = NOTIFY_BASE_URL + "/wx_trade_orders";


    /**
     * 支付宝原生
     */
    // 支付宝服务商PID
    public static final String ALIPAY_ISV_PID = "2088521348003143";
    // 支付宝商户应用ID
    public static final String ALIPAY_APP_ID = "2016120403846959";
    // 支付宝商户应用私钥
    public static final String ALIPAY_APP_PKCS8_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCUaPKBMp4rTiHs+CLINbVqLcn+qJiOUOkwVoPO7EM8+9KVsKMLefsiCee4zruxQ2wJBjeDxGCBKkxIl6e/2QY/pslJuptJ+0bEnPRWqfLsXJ0/AiHYDeU1srA0N2ky2kl3EiN3by8br/iDiL69/tUBWtgU2iNJaOEtiPKPL3CGn4amsSOEyem4cy4+QcTaGKUYTShWRYANvwaf2f1MhZmbioikRU5wzjek7i1PUI7ZczDQuiH7FiJJY7EZL2JHPvY31p691Ke7Lr0ZwjRSoXKW4zV6JP5Wb2gn7uBBHrJ3qQ3xswl7/5gKyo3qPgLeB5h4aKdtK3UxHa2fP0ilXHBLAgMBAAECggEAFyCnsfSWa384kDo3CWY9SDesg8/4Pi1juZGx5rww2j8QGR22uuIefbZ3cvhcjYpJOrrKSfF/bIGP87wWezIx5Bd0xNWcv5G1/IvNE/hWD/l608fcSFQaBR08klaUQUV9L14XQtSDzdfFbZIQz4D35scmZ1F7MER02ui9jAzDh2b1HVk5Hj9quoK7D1N9cOWKWd48zSE9tsDh7OqUNv5ZpGRlpXeNoYDoZVxBWR2BMN85KUyBeMNUS5eq3dyacpmdUXihuBpnW7CFg/ax7I53p12pDCCauWy2Q8miGCYTNSrnUREweJbFkcpaJH7kfh0VUQtg141roWcSC7J43PfDQQKBgQDofUNcMVDBM/P3wCGEUcF4AsVMYZAnE2zo20vJxdZn09ZrGSCyqOHdXvwSUyp29IGQxrPbz9WTvUAjPgQV3zj7zS1kp/6NRjhib4RzztiOFHB2R9Xvofc7LcBmObHi8p0ndEpF3xQlI9PTFQ8hT5Wb+ZPle+u2T2mi7w2uSk0QoQKBgQCjawSwmrkG+x+L3cobEvtHEUE0x+oKWHqn/L2yv0tj8Zz0EANrJrwaWRR0CRYrL44jElRpLfbcbgKn6V6tGzG4dzIB/ni+41s9c2qZGsXni/cpIZLKLGzA2s0l4zmxIi7a/DshXZ8rMvdVRM0y5U9VjVOx87LrdB8wJJAJtpxdawKBgAmzyI7dZABcGa9aLkvmWYuXmgJBBBINZnslQ7ls7zCwD3+yscGi+KITZ7OgEuKZ+5o+IJ1oxAIBYN7XpLYA9dDUVDOiKyZcKtg4zS9ED5eGVNcq0kvI15d5plDd0YWmGFbgLIVT9PRD1YA2ncYHGzhCJyDplgGquL7a+Rgv851hAoGAK0AKqLb6W1ehPMJBWDMI4AE4EcELNxu9srIYfrNUP6pVkN6gGP7Sb+K9oAw7mshmRO/3+TJaEHLpLwl7rjLxUNHzIUX5LQQ9RKFESCsmz6Zijrc9A1cInW4LN4jQ3wmd/wUcnTMLCkLd5OplqJ+6BHl3+jP2WYyNKI+0s21qKusCgYA9ZTGTrC5OgWcuAp2OHHzGHzybhV5EvJTzeVPeOMfHPvHe9S9YlZQQ+O/yQFPJ4DIPhvOiyuJdyY0mfOIUt1OJWQg9QcIvnrARgufLJYzFUm76u4pn3GCxilD4Giuwgrdsw38dmET8FkuE2hfBQ+SixVKUiPmg0yhnz0/oGU/Q8A==";
    // 支付宝商户应用公钥
    public static final String ALIPAY_APP_PKCS8_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlGjygTKeK04h7PgiyDW1ai3J/qiYjlDpMFaDzuxDPPvSlbCjC3n7IgnnuM67sUNsCQY3g8RggSpMSJenv9kGP6bJSbqbSftGxJz0Vqny7FydPwIh2A3lNbKwNDdpMtpJdxIjd28vG6/4g4i+vf7VAVrYFNojSWjhLYjyjy9whp+GprEjhMnpuHMuPkHE2hilGE0oVkWADb8Gn9n9TIWZm4qIpEVOcM43pO4tT1CO2XMw0Loh+xYiSWOxGS9iRz72N9aevdSnuy69GcI0UqFyluM1eiT+Vm9oJ+7gQR6yd6kN8bMJe/+YCsqN6j4C3geYeGinbSt1MR2tnz9IpVxwSwIDAQAB";
    // 支付宝公钥
    public static final String ALIPAY_PKCS8_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAob/jQ8YObkRQuaDBa7or/dO/tlbASyqYByWeNn9mNRedzBH0Fk7L0HVwQFLoS/b+b4xNjOa63gF1O0b37o/hkq7C25QF+2NJWj/oRdRfPXq4NCWQ/IeMGUA7OR3C/+3lR1VR4I7LZu3MYKAIF9EGjnXdZColqEtX9M1D3rdXBu133Q2iHJrBxUre6sRfz2y6l2eTQjNcO/uUTBb5Lo4LKnBPwLaE4Ai0tZ8a2O0PuzoowVUkWUjJLSAw9rbHQeKR7BtRfmjNIUdpR47B8+fVR/+bKHSYV0ocdNtlClwA7ArH24Mvc9wVtyN5NCWz6WlM8tTRkWu4aSr/T2gauxGj/QIDAQAB";
    // 支付宝异步通知URL
    public static final String ALIPAY_NOTIFY_URL = NOTIFY_BASE_URL + "/ali_trade_orders";


    /**
     * 天付宝
     */
    public static final String TFB_NOTIFY_URL = NOTIFY_BASE_URL + "/tfb_trade_orders";


    /**
     * 厦门民生银行
     */
    public static final String CMBC_PKCS8_PRIVATE_KEY = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCMdBAInsXfxYcxRHaVm9Qx8+EjRUdBzlyN0mK+OFLRSYwIVDiljlwDMBqPzMh6KknOIF8jrhVrV3tQg3ZkWCxZgpfJSntiKv8Ak0HZDnNHz29dVQKZdnc5hHdbBWSfIJvTqmu4ICHKf39U5dReyl42zfwhEbIM99kXBhEI7PQOlPrk0QL9Q69OZmYTwGljjZvKVAbNc1PHcsxggTL46Ipa5s1sbn6sm5/ZbIAN1d8/BlPP/Aws9bHICJM7BHW4PMj5zF+kJsgG08Ph++Pxnc1Cv5PKd14jKn/ab7ZduCDihM43tIrzy4leH0ebLWh9lOT4QqKXS0Dgo+7xoCNhUCDRAgMBAAECggEBAIalQTWyWAmEiG0fr/sOrybQIJW6dWxNk4bWGJk4z5QZSKg3bTKxPd5EgEI41DvhTG3RtMM0wQ3tiKcP0DC0IpgQzMhIoe3jQCDiCq813igKYT19IIfT/MnkBrP4gJTYIuqQpsGV9C73eS7/QU85+4XYhIuDNkieVbxvsWb4GaPbjmqPxi/cWRsrrevVA3pQUKv79obvxpDyZXUAqO/JKjfrZP8fmYvnn58rYsGCxE7d5tr1LVzp+RMgWAnyQ08yfRpJesp5PJpZE53vB2nX/c4A3iw19TrQtboMT4GlbwTj+KuROcSI1+bEsvKB5ZsymGz/WXfbfJbQuIlsE+2Rzj0CgYEA+1+IXzN/mkCtcGBFpaCXEYfUSPE6zIBWSNaaojzRQGhKiqHnw4lAidk4VuTjXQG+YIBPgUx4F2W8wSbNkKVtYlI4vBJBHLgVzi1vE3RMy3gjgUPCQaroomsiOnEmT0SHWACtv/ehDjLns49lwFccA41pO3H0T81weDs7zZCIPrMCgYEAjwngjqJGqedXAhT7nO20Bmj2HgC0S3mNUM6pEQMUZEk9XKaolKq/pZ0gupsqqJmUcxskoEjwjMQXRM7/ILAyD14g0n+KZFUYhAFdAkMj+7MnLmekPYAC+3dWeYILowi46krSXurUawKdUfdQPFFJwiVXFJqsoFifyrDpvjCgZGsCfw2/K7YJGvFUmCeNHEHyhlBvHSzgBFcqrY1OLyawLMFY8rqiIAqRdvYS6wHVgpmTftXILpDjna2p4qitdpBye4jk9jVSyjzH1GlyEqD9uzVlXIu1KOpubkpzkWugxHeRR9WIM/hLkoM0RXeMYAQkv2NyWE7n7HRwx7XLh3m2bb0CgYAU20fXBGpxU74oG48krrRVSvC4Jzoi1zJ32eplBKHwjY1Y+SQTqWIQb/zZuM5bjylKL2cDyB7rJ5yM9/HTfl2OSBdZGie/wXi1ps09CzUv+kt4RLx6CzRkHl6JkSDJQPhVkVtjY7JdeCAgV4NniCLNIEy2QGdzafjCwVET3ltEyQKBgGtM/brtzwZ8ivgGNohKs81Bj8j00gJb4NDXy5TMF4kynuOYXMa3FVP1L7UmGuYbnSXK50SLD+jO/4cqvj9nJMoumSU8D3syC4Zdn50gAdi+2Ce3F0okb/ZqIzL0ys830RaY9ilFSw4s0pAzgGQ7/GwjfMKUsdam/e38yvEhGp5I";
    public static final String CMBC_PKCS8_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjHQQCJ7F38WHMUR2lZvUMfPhI0VHQc5cjdJivjhS0UmMCFQ4pY5cAzAaj8zIeipJziBfI64Va1d7UIN2ZFgsWYKXyUp7Yir/AJNB2Q5zR89vXVUCmXZ3OYR3WwVknyCb06pruCAhyn9/VOXUXspeNs38IRGyDPfZFwYRCOz0DpT65NEC/UOvTmZmE8BpY42bylQGzXNTx3LMYIEy+OiKWubNbG5+rJuf2WyADdXfPwZTz/wMLPWxyAiTOwR1uDzI+cxfpCbIBtPD4fvj8Z3NQr+TyndeIyp/2m+2Xbgg4oTON7SK88uJXh9Hmy1ofZTk+EKil0tA4KPu8aAjYVAg0QIDAQAB";


    /**
     * 兴业银行
     */
    // 兴业银行大商户号
    public static final String CIB_GROUP_NO = "";
    // 兴业银行大商户密钥
    public static final String CIB_API_SECRET_KEY = "";
    // 兴业银行支付异步通知URL
    public static final String CIB_NOTIFY_URL = NOTIFY_BASE_URL + "/cib_trade_orders";



    // 订单失效时间(多少分钟后失效)
    public static final Integer ORDER_DEFAULT_EXPIRE_MINUTES = 30;

}
