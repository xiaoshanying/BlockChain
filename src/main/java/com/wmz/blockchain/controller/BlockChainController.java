package com.wmz.blockchain.controller;

import com.wmz.blockchain.model.BlockChainDO;
import com.wmz.blockchain.model.BlockDO;
import com.wmz.blockchain.model.BlockResult;
import com.wmz.blockchain.model.TransactionDO;
import com.wmz.blockchain.service.BlockChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wmz on 2018/3/17.
 *
 * @author wmz
 */
@RestController
@RequestMapping("/block-chain")
public class BlockChainController {

    private static final Logger logger = LoggerFactory.getLogger(BlockChainController.class);

    @Autowired
    private BlockChainService blockChainService;


    /**
     * 挖矿
     * 原理:通过上一个区块的proof计算新的proof,从而产生新的区块,用新的区块去记录交易信息
     * 然后会得到相应的奖励
     *
     * @return
     */
    @RequestMapping(value = "/mine.json", method = RequestMethod.POST)
    public BlockResult<BlockDO> mine(HttpServletRequest request) {
        try {
            String uuid = (String) request.getServletContext().getAttribute("uuid");
            logger.info("id为:" + uuid + "的矿工开始挖矿.....");
            return blockChainService.mine(uuid);
        } catch (Exception e) {
            logger.error("挖矿出现异常:", e);
            return new BlockResult<>("9999", "挖矿失败");
        }
    }

    /**
     * 接收并处理新的交易信息
     *
     * @return
     */
    @RequestMapping(value = "/transaction/new.json", method = RequestMethod.POST)
    public BlockResult newTransaction(TransactionDO req) {
        try {
            logger.info("开始处理此比交易...,req is:{}", req.toString());
            return blockChainService.newTransaction(req);
        } catch (Exception e) {
            logger.error("此比交易处理异常,交易信息是:" + req.toString() + ",异常信息:" + e);
            return new BlockResult<>("9999", "交易处理异常");
        }
    }

    /**
     * 获取整个区块链的数据
     *
     * @return
     */
    @RequestMapping(value = "/get-chain.json", method = RequestMethod.GET)
    public BlockResult<BlockChainDO> getChain() {
        logger.info("开始获取区块链信息....");
        return blockChainService.getChain();
    }

    /**
     * 注册节点
     *
     * @return
     */
    @RequestMapping(value = "/register.json", method = RequestMethod.GET)
    public BlockResult register(@RequestParam String nodes) {
        try {
            logger.info("开始注册节点...host is:" + nodes);
            return blockChainService.register(nodes);
        } catch (Exception e) {
            logger.error("注册节点出现异常");
            return new BlockResult("9999", "节点注册出现异常");
        }
    }

    /**
     * 解决冲突
     *
     * @return
     */
    @RequestMapping(value = "/resolve-conflict.json", method = RequestMethod.GET)
    public BlockResult<BlockChainDO> resolveConflict() {
        try {
            logger.info("冲突解决,启用共识机制....");
            return blockChainService.resolveConflict();
        } catch (Exception e) {
            logger.error("启用共识机制解决冲突异常,", e);
            return new BlockResult<>("9999", "冲突解决失败");
        }
    }

}
