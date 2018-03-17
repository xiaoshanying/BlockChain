package com.wmz.blockchain.utils;

import com.alibaba.fastjson.JSONObject;
import com.wmz.blockchain.model.BlockDO;

/**
 * Created by wmz on 2018/3/17.
 */
public class HashUtil {


    public static String hashBlock(BlockDO blockDO) {
        return EncryptUtil.getSHA256(JSONObject.toJSONString(blockDO));
    }

}
