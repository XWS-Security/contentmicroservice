package org.nistagram.contentmicroservice.data.dto;

import java.util.List;

public class ListDto<T> {
    private List<T> list;

    public ListDto(List<T> list) {
        this.list = list;
    }

    public ListDto() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
