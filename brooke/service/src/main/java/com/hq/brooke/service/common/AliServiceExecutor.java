package com.hq.brooke.service.common;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 24/01/2017.
 */
@Service
public class AliServiceExecutor {

    // 支付宝商户应用ID
    public static final String ALIPAY_APP_ID = "2016120403846959";
    // 支付宝商户应用私钥
    public static final String ALIPAY_APP_PKCS8_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCUaPKBMp4rTiHs+CLINbVqLcn+qJiOUOkwVoPO7EM8+9KVsKMLefsiCee4zruxQ2wJBjeDxGCBKkxIl6e/2QY/pslJuptJ+0bEnPRWqfLsXJ0/AiHYDeU1srA0N2ky2kl3EiN3by8br/iDiL69/tUBWtgU2iNJaOEtiPKPL3CGn4amsSOEyem4cy4+QcTaGKUYTShWRYANvwaf2f1MhZmbioikRU5wzjek7i1PUI7ZczDQuiH7FiJJY7EZL2JHPvY31p691Ke7Lr0ZwjRSoXKW4zV6JP5Wb2gn7uBBHrJ3qQ3xswl7/5gKyo3qPgLeB5h4aKdtK3UxHa2fP0ilXHBLAgMBAAECggEAFyCnsfSWa384kDo3CWY9SDesg8/4Pi1juZGx5rww2j8QGR22uuIefbZ3cvhcjYpJOrrKSfF/bIGP87wWezIx5Bd0xNWcv5G1/IvNE/hWD/l608fcSFQaBR08klaUQUV9L14XQtSDzdfFbZIQz4D35scmZ1F7MER02ui9jAzDh2b1HVk5Hj9quoK7D1N9cOWKWd48zSE9tsDh7OqUNv5ZpGRlpXeNoYDoZVxBWR2BMN85KUyBeMNUS5eq3dyacpmdUXihuBpnW7CFg/ax7I53p12pDCCauWy2Q8miGCYTNSrnUREweJbFkcpaJH7kfh0VUQtg141roWcSC7J43PfDQQKBgQDofUNcMVDBM/P3wCGEUcF4AsVMYZAnE2zo20vJxdZn09ZrGSCyqOHdXvwSUyp29IGQxrPbz9WTvUAjPgQV3zj7zS1kp/6NRjhib4RzztiOFHB2R9Xvofc7LcBmObHi8p0ndEpF3xQlI9PTFQ8hT5Wb+ZPle+u2T2mi7w2uSk0QoQKBgQCjawSwmrkG+x+L3cobEvtHEUE0x+oKWHqn/L2yv0tj8Zz0EANrJrwaWRR0CRYrL44jElRpLfbcbgKn6V6tGzG4dzIB/ni+41s9c2qZGsXni/cpIZLKLGzA2s0l4zmxIi7a/DshXZ8rMvdVRM0y5U9VjVOx87LrdB8wJJAJtpxdawKBgAmzyI7dZABcGa9aLkvmWYuXmgJBBBINZnslQ7ls7zCwD3+yscGi+KITZ7OgEuKZ+5o+IJ1oxAIBYN7XpLYA9dDUVDOiKyZcKtg4zS9ED5eGVNcq0kvI15d5plDd0YWmGFbgLIVT9PRD1YA2ncYHGzhCJyDplgGquL7a+Rgv851hAoGAK0AKqLb6W1ehPMJBWDMI4AE4EcELNxu9srIYfrNUP6pVkN6gGP7Sb+K9oAw7mshmRO/3+TJaEHLpLwl7rjLxUNHzIUX5LQQ9RKFESCsmz6Zijrc9A1cInW4LN4jQ3wmd/wUcnTMLCkLd5OplqJ+6BHl3+jP2WYyNKI+0s21qKusCgYA9ZTGTrC5OgWcuAp2OHHzGHzybhV5EvJTzeVPeOMfHPvHe9S9YlZQQ+O/yQFPJ4DIPhvOiyuJdyY0mfOIUt1OJWQg9QcIvnrARgufLJYzFUm76u4pn3GCxilD4Giuwgrdsw38dmET8FkuE2hfBQ+SixVKUiPmg0yhnz0/oGU/Q8A==";
    // 支付宝商户应用公钥
    public static final String ALIPAY_APP_PKCS8_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlGjygTKeK04h7PgiyDW1ai3J/qiYjlDpMFaDzuxDPPvSlbCjC3n7IgnnuM67sUNsCQY3g8RggSpMSJenv9kGP6bJSbqbSftGxJz0Vqny7FydPwIh2A3lNbKwNDdpMtpJdxIjd28vG6/4g4i+vf7VAVrYFNojSWjhLYjyjy9whp+GprEjhMnpuHMuPkHE2hilGE0oVkWADb8Gn9n9TIWZm4qIpEVOcM43pO4tT1CO2XMw0Loh+xYiSWOxGS9iRz72N9aevdSnuy69GcI0UqFyluM1eiT+Vm9oJ+7gQR6yd6kN8bMJe/+YCsqN6j4C3geYeGinbSt1MR2tnz9IpVxwSwIDAQAB";
    // 支付宝公钥
    public static final String ALIPAY_PKCS8_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAob/jQ8YObkRQuaDBa7or/dO/tlbASyqYByWeNn9mNRedzBH0Fk7L0HVwQFLoS/b+b4xNjOa63gF1O0b37o/hkq7C25QF+2NJWj/oRdRfPXq4NCWQ/IeMGUA7OR3C/+3lR1VR4I7LZu3MYKAIF9EGjnXdZColqEtX9M1D3rdXBu133Q2iHJrBxUre6sRfz2y6l2eTQjNcO/uUTBb5Lo4LKnBPwLaE4Ai0tZ8a2O0PuzoowVUkWUjJLSAw9rbHQeKR7BtRfmjNIUdpR47B8+fVR/+bKHSYV0ocdNtlClwA7ArH24Mvc9wVtyN5NCWz6WlM8tTRkWu4aSr/T2gauxGj/QIDAQAB";

    private AlipayClient client;

    public AliServiceExecutor() {
        this.client = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                ALIPAY_APP_ID, ALIPAY_APP_PKCS8_PRIVATE_KEY, "JSON", "utf-8", ALIPAY_PKCS8_PUBLIC_KEY, "RSA2");
    }

    public AlipayClient getClient() {
        return client;
    }

}
