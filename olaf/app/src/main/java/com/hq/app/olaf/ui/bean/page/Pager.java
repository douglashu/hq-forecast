package com.hq.app.olaf.ui.bean.page;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.bean.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class Pager<T> implements Parcelable {

    private  int lastPage;
    private  Integer[]  navigatepageNums;
    private  int startRow ;
    private  int prePage ;
    private  boolean hasNextPage;
    private int nextPage;
    private int pageSize;
    private int endRow;
    private List<T> list;
    private int pageNum;
    private int navigatePages;
    private int total;
    private int pages;
    private int size;
    private int firstPage;
    private  boolean isLastPage;
    private  boolean hasPreviousPage;
    private  boolean isFirstPage;




    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public  List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(Integer[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.lastPage);
        dest.writeArray(this.navigatepageNums);
        dest.writeInt(this.startRow);
        dest.writeInt(this.prePage);
        dest.writeByte(this.hasNextPage ? (byte) 1 : (byte) 0);
        dest.writeInt(this.nextPage);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.endRow);
        dest.writeList(this.list);
        dest.writeInt(this.pageNum);
        dest.writeInt(this.navigatePages);
        dest.writeInt(this.total);
        dest.writeInt(this.pages);
        dest.writeInt(this.size);
        dest.writeInt(this.firstPage);
        dest.writeByte(this.isLastPage ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasPreviousPage ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFirstPage ? (byte) 1 : (byte) 0);
    }

    public Pager() {
    }

    protected Pager(Parcel in) {
        this.lastPage = in.readInt();
        this.navigatepageNums = (Integer[]) in.readArray(Integer[].class.getClassLoader());
        this.startRow = in.readInt();
        this.prePage = in.readInt();
        this.hasNextPage = in.readByte() != 0;
        this.nextPage = in.readInt();
        this.pageSize = in.readInt();
        this.endRow = in.readInt();
        this.list = new ArrayList<T>();
        in.readList(this.list, BaseEntity.class.getClassLoader());
        this.pageNum = in.readInt();
        this.navigatePages = in.readInt();
        this.total = in.readInt();
        this.pages = in.readInt();
        this.size = in.readInt();
        this.firstPage = in.readInt();
        this.isLastPage = in.readByte() != 0;
        this.hasPreviousPage = in.readByte() != 0;
        this.isFirstPage = in.readByte() != 0;
    }

    public static final Creator<Pager> CREATOR = new Creator<Pager>() {
        @Override
        public Pager createFromParcel(Parcel source) {
            return new Pager(source);
        }

        @Override
        public Pager[] newArray(int size) {
            return new Pager[size];
        }
    };
}
