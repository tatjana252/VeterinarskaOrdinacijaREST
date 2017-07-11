/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.Map;
import javax.swing.SortOrder;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */

@XmlRootElement
public class Search implements Serializable{

    int first;
    int pageSize;
    String sortField;
    SortOrder sortOrder;
    Map<String, Object> filters;

    public Search(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.first = first;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        this.filters = filters;
    }

    public Search() {
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }
    
    
    
    
}
