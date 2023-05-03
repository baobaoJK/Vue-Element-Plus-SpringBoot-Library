package com.ksamar.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksamar.library.entitys.Type;
import com.ksamar.library.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图书种类控制器
 * @author KSaMar
 */
@RestController
@RequestMapping("/type")
public class TypeHandler {
    @Autowired
    private TypeRepository typeRepository;

    /**
     * 添加图书种类
     * @param type 图书种类
     * @return 添加信息
     */
    @PostMapping("/add")
    public JSONObject addType(@RequestBody Type type) {
        JSONObject typeMessage = new JSONObject();
        typeMessage.put("message", "addType");
        typeMessage.put("statusCode", 0);

        // 判断添加种类表单是否为空
        if (type != null) {
            Type tempType = typeRepository.findByTypeName(type.getTypeName());
            if (tempType == null) {
                Type saveType = new Type();
                saveType.setTypeName(type.getTypeName());
                saveType.setTypeId(String.valueOf(typeRepository.findAll().size() + 10001));
                typeRepository.save(saveType);
                typeMessage.put("statusCode", 1);
            }
            else {
                typeMessage.put("statusCode", 2);
            }
        }

        return typeMessage;
    }

    /**
     * 删除图书种类
     * @param type 图书种类
     * @return 删除信息
     */
    @PostMapping("/delete")
    public JSONObject deleteType(@RequestBody Type type) {
        JSONObject typeMessage = new JSONObject();
        typeMessage.put("message", "deleteType");
        typeMessage.put("statusCode", 0);

        // 判断图书种类表单是否为空
        if (type != null) {
            Type tempType = typeRepository.findByTypeName(type.getTypeName());
            if (tempType != null) {
                typeRepository.delete(tempType);
                typeMessage.replace("statusCode", 1);
            }
            else {
                typeMessage.replace("statusCode", 2);
            }
        }

        return typeMessage;
    }
}
