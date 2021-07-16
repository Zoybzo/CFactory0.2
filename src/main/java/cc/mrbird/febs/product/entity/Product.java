package cc.mrbird.febs.product.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 产品表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:25
 */
@Data
@TableName("t_product")
public class Product {

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 产品描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 上次修改时间
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * 产品ID
     */
    @TableId(value = "PRODUCT_ID", type = IdType.AUTO)
    private Long productId;

    /**
     * 产品名
     */
    @TableField("PRODUCT_NAME")
    private String productName;

    /**
     * 所属产品类型ID
     */
    @TableField("PRODUCT_TYPE_ID")
    private Long productTypeId;

}
