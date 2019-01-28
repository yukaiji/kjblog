package com.yukaiji.kjblog.model.responsemodel;

/**
 * 基础分页返回
 * @author kaijiyu
 */
public class BasePageResponse {

    /** 总页码 **/
    private Integer pageSize;
    /** 总条数 **/
    private Integer totalCount;
    /** 下一页页码 **/
    private Integer nextPageNum;
    /** 上一页页码 **/
    private Integer prevPageNum;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(Integer nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public Integer getPrevPageNum() {
        return prevPageNum;
    }

    public void setPrevPageNum(Integer prevPageNum) {
        this.prevPageNum = prevPageNum;
    }
}
