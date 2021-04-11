package seu.talents.cloud.talent.model.dto.post;

public enum SearchType {

    /**
     *
     */
    name("name", "姓名"),
    company("company", "公司"),
    job("job", "职位"),
    college("college", "学院");

    private String value;
    private String desc;

    SearchType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

