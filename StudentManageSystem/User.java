package StudentManageSystem;

public class User {
    private String name;
    private String key;
    private String idnum;
    private String tel;

    public User() {
    }

    public User(String name, String key, String idnum, String tel) {
        this.name = name;
        this.key = key;
        this.idnum = idnum;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
