package cc.mrbird.febs.receiver.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.Size;

/**
 * 收货人信息表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:54
 */
@Data
@Excel("联系信息表")
@TableName("t_receiver")
public class Receiver implements Serializable, Cloneable {

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
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
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "地址")
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
    @ExcelField(value = "姓名")
    private String receiverName;

    /**
     * 收货人电话
     */
    @TableField("RECEIVER_PHONE")
    @ExcelField(value = "手机")
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
    @ExcelField(value = "所属用户")
    private Long userId;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    @Override
    protected Receiver clone() throws CloneNotSupportedException {
        return (Receiver) super.clone();
    }
}
