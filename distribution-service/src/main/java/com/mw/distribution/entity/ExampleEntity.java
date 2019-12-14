package com.mw.distribution.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: ExampleEntity 实体类，和数据表一一对应
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Data
@EqualsAndHashCode
public class ExampleEntity implements Serializable {

    private Long id;

    private String name;

}