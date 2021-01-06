package com.kodstar.backend.model.enums;

import lombok.Getter;

@Getter
public enum State {

  OPEN,
  CLOSED;

  public static State fromString(String str) {
    for (State state : State.values()) {
      if (state.name().equalsIgnoreCase(str)) {
        return state;
      }
    }
    return OPEN;
  }
}
