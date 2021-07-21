package cc.mrbird.febs.receiver.mapper;

import cc.mrbird.febs.receiver.entity.Receiver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 收货人信息表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:54
 */
public interface ReceiverMapper extends BaseMapper<Receiver> {

    /**
     * @param receiver receiver
     * @return countReceiverDetail
     */
    long countReceiverDetail(@Param("receiver") Receiver receiver);

    /**
     * @param page page
     * @param receiver receiver
     * @return IPage
     */
    <T> IPage<Receiver> findReceiverDetailPage(Page<T> page, @Param("receiver") Receiver receiver);
}
