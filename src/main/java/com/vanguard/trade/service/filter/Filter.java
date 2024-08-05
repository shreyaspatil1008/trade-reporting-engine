package com.vanguard.trade.service.filter;

import com.vanguard.trade.model.Event;

import java.util.List;

public interface Filter extends Cloneable {

    boolean isSecondaryAnd();

    boolean isSecondaryOr();

    List<Event> apply(List<Event> eventList);

    Object clone() throws CloneNotSupportedException;
}
