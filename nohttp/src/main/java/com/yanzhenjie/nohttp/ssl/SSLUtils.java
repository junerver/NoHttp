/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.nohttp.ssl;

import android.os.Build;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by YanZhenjie on 2017/6/13.
 */
public class SSLUtils {

    private static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            if("college.sipedu.org".equals(hostname)){
                return true;
            } else {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify(hostname, session);
            }
        }
    };

    public static SSLSocketFactory defaultSSLSocketFactory() {
        return new TLSSocketFactory();
    }

    public static SSLSocketFactory fixSSLLowerThanLollipop(SSLSocketFactory socketFactory) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP &&
            !(socketFactory instanceof TLSSocketFactory)) {
            socketFactory = new TLSSocketFactory(socketFactory);
        }
        return socketFactory;
    }

    public static HostnameVerifier defaultHostnameVerifier() {
        return HOSTNAME_VERIFIER;
    }

}
