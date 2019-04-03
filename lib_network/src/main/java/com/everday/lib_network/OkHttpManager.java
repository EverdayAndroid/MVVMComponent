package com.everday.lib_network;

import android.content.Context;
import android.text.TextUtils;

import com.everday.lib_base.utils.PreferencesUtils;
import com.everday.lib_network.constants.Constants;
import com.everday.lib_network.utils.FileUtils;
import com.everday.lib_network.utils.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpManager {
    private static OkHttpManager okhttpClient;
    private OkHttpClient okClient;
    private Context mContext;
    public static OkHttpManager getInstance(){
        if(okhttpClient == null){
            synchronized (OkHttpManager.class){
                if(okhttpClient == null){
                    okhttpClient = new OkHttpManager();
                }
            }
        }
        return okhttpClient;
    }
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String token = (String) PreferencesUtils.get("token","");
            if(!TextUtils.isEmpty(token)) {
                if(!request.url().url().toString().contains("login")) {
                    request = request.newBuilder().
                            addHeader("token", token).build();
                }
            }
            return chain.proceed(request);
        }
    };

    Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365,TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if(!NetUtils.isAvailable()){
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtils.isAvailable()) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale

                originalResponse = originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 10)
                        .build();
                return originalResponse;
            }
        }
    };
    public OkHttpManager(){
        Cache cache = new Cache(FileUtils.getInstance().getCacheDir(), Constants.CACHESIZE);
        okClient = new OkHttpClient().newBuilder()
                .connectTimeout(Constants.CONNECTTIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READTIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITETIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(cacheInterceptor)
                .cache(cache)
                .sslSocketFactory(SSLSockotFactoryUtils.createSSLSocketFactory())
//                .sslSocketFactory(getSocketFactory(AssistantApplication.mContext))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        //忽略所有https请求
                        return true;
                    }
                })
                .retryOnConnectionFailure(true)
                .build();
    }
    public OkHttpClient getOkClient(){
        return okClient;
    }

    /**
     * 自带证书文件
     * @param context
     * @return
     */
    public static SSLSocketFactory getSocketFactory(Context context) {
        //设置证书类型
        CertificateFactory factory = null;
        SSLSocketFactory sslSocketFactory = null;
        try {
            factory = CertificateFactory.getInstance("X.509", "BC");
            //打开放在main文件下的 assets 下的Http证书
            InputStream stream = context.getAssets().open("raw/client.crt");
            Certificate certificate = factory.generateCertificate(stream);
            //证书类型
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //授信证书 , 授信证书密码（应该是服务端证书密码）
            keyStore.load(null, null);
            keyStore.setCertificateEntry("certificate",certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //证书密码（应该是客户端证书密码）
            keyManagerFactory.init(keyStore, "Nn7&Cu!qp8jLf1qA".toCharArray());
//            keyManagerFactory.init(keyStore, null);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(),trustManagerFactory.getTrustManagers(),new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}
