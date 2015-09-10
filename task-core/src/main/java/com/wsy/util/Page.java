package com.wsy.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 每页显示记录数 */
	protected int pageSize;

	/* 总页数 */
	protected int totalPage;

	/* 自动计算当前页码 */
	protected int currentPage;

	/* 参数传入当前页码 */
	protected int paramCurrentPage;

	/* 总记录数 */
	protected int totalRow;

	/* 当前页在数据库中的起始行 */
	protected int startRow;

	/* 上一页 */
	protected int previousPage;

	/* 下一页 */
	protected int nextPage;

	/* 当前页码显示的记录 */
	protected List<T> resultRows;

	/* 分类需要附带的对象 */
	protected Object attach;

	public Page(int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;

		ini();
	}

	public void ini() {
		// 页大小初始化
		pageSize = pageSize <= 1 ? 1 : pageSize;

		// 总页数初始化
		totalPage = totalRow / pageSize + (totalPage % pageSize == 0 ? 0 : 1);
		totalPage = totalPage == 0 ? 1 : totalPage;

		// 当前页初始化
		if (paramCurrentPage < 1) {
			currentPage = 1;
		} else if (paramCurrentPage > totalPage) {
			currentPage = totalPage;
		} else {
			currentPage = paramCurrentPage;
		}

		// 初始化当前页面数据在数据库中是第几行
		startRow = (currentPage - 1) * pageSize;
		// 上一页初始化
		previousPage = currentPage == 1 ? 1 : currentPage - 1;
		// 下一页初始化
		nextPage = currentPage == totalPage ? totalPage : currentPage + 1;

	}

	/**
	 * 判断当前页是否存在上一页
	 * 
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return currentPage != 1;
	}

	/**
	 * 判断当前页是否存在 下一页
	 * 
	 * @return
	 */
	public boolean isHasNextPage() {
		return currentPage != totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getParamCurrentPage() {
		return paramCurrentPage;
	}

	public void setParamCurrentPage(int paramCurrentPage) {
		this.paramCurrentPage = paramCurrentPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public List<T> getResultRows() {
		return resultRows;
	}

	public void setResultRows(List<T> resultRows) {
		this.resultRows = resultRows;
	}

	public Object getAttach() {
		return attach;
	}

	public void setAttach(Object attach) {
		this.attach = attach;
	}
	
}
