package com.yukaiji.kjblog.model.requestmodel;

/**
 * 基础分页查询
 * @author kaijiyu
 */
public class BasePageRequestModel {

    /** 请求页码  **/
    private Integer pageSize = 1;
    /** 请求条数 **/
    private Integer pageNum = 10;
    /** 开始条数 **/
    private Integer startNum;
    /** 结束条数 **/
    private Integer endNum;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }
}
