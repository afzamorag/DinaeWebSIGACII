/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.*;

import co.gov.policia.dinae.moodle.errorhandler.Error;
import co.gov.policia.dinae.siedu.modelo.LogMoodleSendData;
import co.gov.policia.dinae.siedu.modelo.LogMoodleSendHttp;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendDataService;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendHttpService;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase Servir para conectarse con moolde
 *
 * @author ferney
 *
 */
public class Server implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1883407726002668730L;

    private LogMoodleSendDataService serviceLog;    
    private LogMoodleSendHttpService serviceUri;
    /**
     * Token usado en la fase inicial de desarrollo del proyecto Se comenta a
     * modo de auditoria
     *
     * @lastmodby: Julio Lopez
     * @lastmoddt: 24/11/2017
     */
//	private String token = "8391b8583f4e91a2a377c5a163d67105";
    private String token = "b974c51b0b8272b1d9cd7bd88121abf1";
    private String urlMoodle = "https://educacion.policia.edu.co/dinae";
    private String pathService = "webservice/rest/server.php";
    private Error error;
    private SSLSocketFactory mDefaultSSLSocketFactory = null;
    private HostnameVerifier mDefaultHostnameVerifier = null;
    private String servicio;

    /**
     * Costructor de la Clase
     * @param serLog
     * @param serUri 
     */
    public Server(LogMoodleSendDataService serLog, LogMoodleSendHttpService serUri) {
        super();
        this.serviceLog = serLog;
        this.serviceUri = serUri;
        // TODO Auto-generated constructor stub
    }

    /**
     * Devuelve la ruata del servicio web con parametros
     *
     * @param service
     * @param parameterGet
     * @return
     */
    public String getUrlService(String service, String parameterGet) {
            String sUrl = this.urlMoodle;
        this.servicio = service;
        if (!sUrl.endsWith("/")) {
            sUrl += "/";
        }
        sUrl += this.pathService;
        sUrl += "?wstoken=";
        sUrl += this.token;
        sUrl += "&wsfunction=";
        sUrl += service;
        sUrl += "&moodlewsrestformat=json";
        if (parameterGet != null && !parameterGet.isEmpty()) {
            sUrl += "&";
            sUrl += parameterGet;
        }
        return sUrl;
    }

    /**
     * Devuelve la ruta del servicio web para pasar los parametros por pos
     *
     * @param service
     * @return
     */
    public String getUrlService(String service) {
        return getUrlService(service, null);
    }

    /**
     * Se conecta a un servicio de Moodle con datos como post
     *
     * @param uri
     * @param sParameters
     * @return
     */
    public String getData(String uri, String sParameters) {
        return this.sendService(uri, sParameters, true);
    }

    private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
            }
        }};

        try {
            mDefaultSSLSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            this.error = new Error("APIException", "E-NET", e.getMessage());
        }
    }

    /**
     * Se conecta a moodle con datos get
     *
     * @param uri
     * @return
     */
    public String getData(String uri) {
        return this.sendService(uri, null, true);
    }
    
    /**
     * Hace la petincion a moodle
     * @param sUrl
     * @param sParameters
     * @param bSave
     * @return 
     */
    public String sendService(String sUrl, String sParameters, boolean bSave) {
        LogMoodleSendHttp record = new LogMoodleSendHttp();
        record.setServicio(this.servicio);
        record.setFecha(new Date());
        record.setFechaRenvio(new Date());
        record.setUri(sUrl);
        record.setParameters(sParameters);
        record.setEstado(1l);
        
        this.error = null;
        try {
            java.security.Security.setProperty("ssl.SocketFactory.provider", "sun.security.ssl.SSLSocketFactoryImpl");
            java.security.Security.setProperty("ssl.ServerSocketFactory.provider", "sun.security.ssl.SSLSocketFactoryImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            trustAllHosts();
            URL url = new URL(null, sUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            mDefaultHostnameVerifier = connection.getHostnameVerifier();
            connection.setRequestMethod("POST");
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            if(sParameters != null && !sParameters.isEmpty()) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWriter.write(sParameters);
                outputStreamWriter.flush();
            }
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                stringBuilder.append(linea + "\n");
            }
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            record.setResult(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            this.error = new Error("APIException", "E-NET", e.getMessage());
            record.setError(e.getMessage());
        }
        if(bSave) {
            try {
                record = this.serviceUri.create(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Calendar cal = Calendar.getInstance(); 
                cal.add(Calendar.MONTH, -10);
                this.serviceUri.deleteHistory(cal.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return record.getResult();
    }
    

    /**
     * Devuelve un JSONArray del una cadena
     *
     * @param content
     * @return
     */
    public JSONArray getJson(String content) {
        try {
            JSONArray jsonArray = new JSONArray(content);
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Devuelve el JSON Object de una cadena
     *
     * @param content
     * @return
     */
    public JSONObject getJsonObject(String content) {
        try {
            JSONObject jsObject = new JSONObject(content);
            return jsObject;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Guarda el log del error
     *
     * @param modulo
     * @param item
     * @param tipo
     * @param pCode
     * @param pMessage
     */
    public void addLogError(String modulo, String item, String tipo, String pCode, String pMessage) {
        try {
            LogMoodleSendData lError = new LogMoodleSendData();
            lError.setFecha(new Date());
            lError.setModulo(modulo);
            lError.setItem(item);
            lError.setTipo(tipo);
            lError.setCodigo(pCode);
            lError.setDescripcion(pMessage);
            lError = this.serviceLog.create(lError);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlMoodle() {
        return urlMoodle;
    }

    public void setUrlMoodle(String urlMoodle) {
        this.urlMoodle = urlMoodle;
    }

    public String getPathService() {
        return pathService;
    }

    public void setPathService(String pathService) {
        this.pathService = pathService;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public LogMoodleSendDataService getServiceLog() {
        return serviceLog;
    }

    public void setServiceLog(LogMoodleSendDataService serviceLog) {
        this.serviceLog = serviceLog;
    }

    public LogMoodleSendHttpService getServiceUri() {
        return serviceUri;
    }

    public void setServiceUri(LogMoodleSendHttpService serviceUri) {
        this.serviceUri = serviceUri;
    }

    public SSLSocketFactory getmDefaultSSLSocketFactory() {
        return mDefaultSSLSocketFactory;
    }

    public void setmDefaultSSLSocketFactory(SSLSocketFactory mDefaultSSLSocketFactory) {
        this.mDefaultSSLSocketFactory = mDefaultSSLSocketFactory;
    }

    public HostnameVerifier getmDefaultHostnameVerifier() {
        return mDefaultHostnameVerifier;
    }

    public void setmDefaultHostnameVerifier(HostnameVerifier mDefaultHostnameVerifier) {
        this.mDefaultHostnameVerifier = mDefaultHostnameVerifier;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    
}
