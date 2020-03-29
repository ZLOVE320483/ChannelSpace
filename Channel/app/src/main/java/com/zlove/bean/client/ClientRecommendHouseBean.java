
package com.zlove.bean.client;

import java.io.Serializable;

public class ClientRecommendHouseBean implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5922099799085865039L;

    private int status;
    private String message;
    private ClientRecommendHouseData data;
    private long server_time;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ClientRecommendHouseData getData() {
        return data;
    }

    public long getServer_time() {
        return server_time;
    }

}
