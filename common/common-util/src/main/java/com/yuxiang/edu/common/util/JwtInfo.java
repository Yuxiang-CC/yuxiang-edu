package com.yuxiang.edu.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Yuxiang
 * @create: 2020-11-15 15:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {

    private String id;
    private String nickName;
    private String avatar;
}
