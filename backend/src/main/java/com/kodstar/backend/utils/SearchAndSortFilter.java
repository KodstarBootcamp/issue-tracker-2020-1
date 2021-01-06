package com.kodstar.backend.utils;

import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SearchAndSortFilter<T> {

    public abstract Collection<T> filterAndSort(String field, String key, String sort);

    public List<Sort.Order> getOrders(String str){

        List<Sort.Order> orders = new ArrayList<>();

        switch (str){
            case "newest":
                orders.add(new Sort.Order(getSortDirection("desc"), "created"));
                break;

            case "oldest":
                orders.add(new Sort.Order(getSortDirection("asc"), "created"));
                break;

            case "recent":
                orders.add(new Sort.Order(getSortDirection("desc"), "modified"));
                break;

            case "latest":
                orders.add(new Sort.Order(getSortDirection("asc"), "modified"));
                break;

            default:
                orders.add(new Sort.Order(getSortDirection("desc"), "created"));
        }
        return orders;
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
