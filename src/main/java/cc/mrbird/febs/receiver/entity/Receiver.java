package cc.mrbird.febs.receiver.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 收货人信息表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:54
 */
@Data
@TableName("t_receiver")
public class Receiver {

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 上次修改时间
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * 收货人地址
     */
    @TableField("RECEIVER_ADDRESS")
    private String receiverAddress;

    /**
     * 收货人信息ID
     */
    @TableId(value = "RECEIVER_ID", type = IdType.AUTO)
    private Long receiverId;

    /**
     * 收货人姓名
     */
    @TableField("RECEIVER_NAME")
    private String receiverName;

    /**
     * 收货人电话
     */
    @TableField("RECEIVER_PHONE")
    private String receiverPhone;

    /**
     * 信息标签
     */
    @TableField("TAG")
    private String tag;

    /**
     * 信息所属用户
     */
    @TableField("USER_ID")
    private Long userId;

}
