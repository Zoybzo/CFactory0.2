package cc.mrbird.febs.receiver.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.receiver.entity.Receiver;
import cc.mrbird.febs.receiver.mapper.ReceiverMapper;
import cc.mrbird.febs.receiver.service.IReceiverService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 收货人信息表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:54
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReceiverServiceImpl extends ServiceImpl<ReceiverMapper, Receiver> implements IReceiverService {

    private final ReceiverMapper receiverMapper;

    @Override
    public IPage<Receiver> findReceivers(QueryRequest request, Receiver receiver) {
        if (StringUtils.isNotBlank(receiver.getCreateTimeFrom()) &&
                StringUtils.equals(receiver.getCreateTimeFrom(), receiver.getCreateTimeTo())) {
            receiver.setCreateTimeFrom(receiver.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            receiver.setCreateTimeTo(receiver.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countReceiverDetail(receiver));
        SortUtil.handlePageSort(request, page, "receiverId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findReceiverDetailPage(page, receiver);
    }

    @Override
    public List<Receiver> findReceivers(Receiver receiver) {
        LambdaQueryWrapper<Receiver> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createReceiver(Receiver receiver) {
        receiver.setCreateTime(new Date());
        this.save(receiver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReceiver(Receiver receiver) {
        this.saveOrUpdate(receiver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReceiver(Receiver receiver) {
        LambdaQueryWrapper<Receiver> wrapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    @Override
    public void deleteReceivers(String[] receiverIds) {
        List<String> list = Arrays.asList(receiverIds);
        // 删除
        removeByIds(list);
    }
}
