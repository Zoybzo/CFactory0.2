package cc.mrbird.febs.product.entity;

import java.io.Serializable;
import java.util.Date;

import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 产品类型表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:21
 */
@Data
@TableName("t_product_type")
public class ProductType implements Serializable, Cloneable {

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 产品类型描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 上次修改时间
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * 父级类型ID
     */
    @TableField("PARENT_TYPE_ID")
    private Long parentTypeId;

    /**
     * 产品类型ID
     */
    @TableId(value = "PRODUCT_TYPE_ID", type = IdType.AUTO)
    private Long productTypeId;

    /**
     * 产品类型名
     */
    @TableField("PRODUCT_TYPE_NAME")
    private String productTypeName;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    @Override
    protected ProductType clone() throws CloneNotSupportedException {
        return (ProductType) super.clone();
    }
}
