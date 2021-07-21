package cc.mrbird.febs.factory.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 用户工厂表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 14:05:51
 */
@Data
@TableName("t_user_factory")
public class UserFactory implements Serializable, Cloneable {

    /**
     * 工厂ID
     */
    @TableField("FACTORY_ID")
    private Long factoryId;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 用户ID
     */
    @TableId(value = "USER_FACTORY_ID", type = IdType.AUTO)
    private Long userFactoryId;

}
