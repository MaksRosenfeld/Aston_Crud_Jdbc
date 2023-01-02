package dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum Driver {
    H2_TEST("jdbc:h2:mem:playtest;" +
            "INIT=" +
            "runscript from 'src/test/resources/create_test_db.sql'\\;" +
            "runscript from 'src/test/resources/init_test_db.sql'"),
    POSTGRES("jdbc:postgresql://localhost:5432/aston_crud_jdbc");

    private String url;

    Driver(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
