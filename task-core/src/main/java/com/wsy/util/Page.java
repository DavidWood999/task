package com.wsy.util;

import java.io.Serializable;
import java.util.List;

/**
 * ��ҳʵ��
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ÿҳ��ʾ��¼�� */
	protected int pageSize;

	/* ��ҳ�� */
	protected int totalPage;

	/* �Զ����㵱ǰҳ�� */
	protected int currentPage;

	/* �������뵱ǰҳ�� */
	protected int paramCurrentPage;

	/* �ܼ�¼�� */
	protected int totalRow;

	/* ��ǰҳ�����ݿ��е���ʼ�� */
	protected int startRow;

	/* ��һҳ */
	protected int previousPage;

	/* ��һҳ */
	protected int nextPage;

	/* ��ǰҳ����ʾ�ļ�¼ */
	protected List<T> resultRows;

	/* ������Ҫ�����Ķ��� */
	protected Object attach;

	public Page(int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;

		ini();
	}

	public void ini() {
		// ҳ��С��ʼ��
		pageSize = pageSize <= 1 ? 1 : pageSize;

		// ��ҳ����ʼ��
		totalPage = totalRow / pageSize + (totalPage % pageSize == 0 ? 0 : 1);
		totalPage = totalPage == 0 ? 1 : totalPage;

		// ��ǰҳ��ʼ��
		if (paramCurrentPage < 1) {
			currentPage = 1;
		} else if (paramCurrentPage > totalPage) {
			currentPage = totalPage;
		} else {
			currentPage = paramCurrentPage;
		}

		// ��ʼ����ǰҳ�����������ݿ����ǵڼ���
		startRow = (currentPage - 1) * pageSize;
		// ��һҳ��ʼ��
		previousPage = currentPage == 1 ? 1 : currentPage - 1;
		// ��һҳ��ʼ��
		nextPage = currentPage == totalPage ? totalPage : currentPage + 1;

	}

	/**
	 * �жϵ�ǰҳ�Ƿ������һҳ
	 * 
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return currentPage != 1;
	}

	/**
	 * �жϵ�ǰҳ�Ƿ���� ��һҳ
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
