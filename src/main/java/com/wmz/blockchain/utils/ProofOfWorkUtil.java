package com.wmz.blockchain.utils;

/**
 * Created by wmz on 2018/3/17.
 * 工作量证明
 * 规则:寻找一个数p,使得它与前一个区块的proof拼接成的字符串的hash值以3个6开头
 *
 * @author wmz
 */
public class ProofOfWorkUtil {

    private static final String SUCCESS_PREFIX = "666";

    /**
     * @param lastProof 上一个块的工作量证明
     * @return
     */
    public static long proofOfWork(long lastProof) {
        long proof = 0;
        while (!validProof(lastProof, proof)) {
            proof += 1;
        }
        return proof;
    }

    private static boolean validProof(long lastProof, long currentProof) {
        String guess = lastProof + "" + currentProof;
        String guessHash = EncryptUtil.getSHA256(guess);
        return guessHash.startsWith(SUCCESS_PREFIX);
    }

}
