package com.huachu.common.constants;

/**
 * @author Administrator
 * @DATE 2018/9/18
 */
public enum FileTypeEnum {

    ACCESSORY_IMAGE("配件图片", "/accessory/image")
    ;

    private String name;
    private String path;

    private FileTypeEnum(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
