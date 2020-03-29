package com.zlove.bean.client;

import com.zlove.bean.common.CommonPageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZLOVE on 2016/12/28.
 */
public class ClientRecommendHouseListBean implements Serializable {

    private int status;
    private String message;
    private PageInfoData data;
    private long server_time;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public PageInfoData getData() {
        return data;
    }



    public long getServer_time() {
        return server_time;
    }

    public static class PageInfoData {
        private CommonPageInfo page_info;
        private List<ClientRecommendHouseListItem> recommend_house_list;

        public CommonPageInfo getPage_info() {
            return page_info;
        }
        public List<ClientRecommendHouseListItem> getRecommend_house_list() {
            return recommend_house_list;
        }
    }
}
