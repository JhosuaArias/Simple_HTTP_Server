public enum Method {
    POST ("POST"),
    GET ("GET"),
    HEAD ("HEAD"),
    PUT ("PUT"),
    UNKWOWN ("UNKNOWN");

    private String name;

    Method(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
