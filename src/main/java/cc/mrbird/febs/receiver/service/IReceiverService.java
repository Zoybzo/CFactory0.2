package cc.mrbird.febs.receiver.service;

import cc.mrbird.febs.receiver.entity.Receiver;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 收货人信息表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:54
 */
public interface IReceiverService extends IService<Receiver> {
    /**
     * 查询（分页）
     *
     * @param request  QueryRequest
     * @param receiver receiver
     * @return IPage
     */
    IPage<Receiver> findReceivers(QueryRequest request, Receiver receiver);

    /**
     * 查询（所有）
     *
     * @param receiver receiver
     * @return List
     */
    List<Receiver> findReceivers(Receiver receiver);

    /**
     * 新增
     *
     * @param receiver receiver
     */
    void createReceiver(Receiver receiver);

    /**
     * 修改
     *
     * @param receiver receiver
     */
    void updateReceiver(Receiver receiver);

    /**
     * 删除
     *
     * @param receiver receiver
     */
    void deleteReceiver(Receiver receiver);

    /**
     * 删除
     *
     * @param receiverIds receiverIds
     */
    void deleteReceivers(String[] receiverIds);
}
