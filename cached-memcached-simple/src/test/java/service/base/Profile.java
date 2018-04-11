package service.base;

import lombok.Getter;

/**
 * Created by l_yy on 2017/1/5.
 */
public enum Profile {
    REMOTE("remote"),
    MOCK("default"),
    LOCAL("local");

    @Getter
    private String value;

    Profile(String value) {
        this.value = value;
    }
}
