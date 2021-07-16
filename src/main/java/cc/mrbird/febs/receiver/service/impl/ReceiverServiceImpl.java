package cc.mrbird.febs.receiver.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.receiver.entity.Receiver;
import cc.mrbird.febs.receiver.mapper.ReceiverMapper;
import cc.mrbird.febs.receiver.service.IReceiverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
        LambdaQueryWrapper<Receiver> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<Receiver> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
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
}
